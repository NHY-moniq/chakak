package ai.chakak.vehicle

import android.content.Context
import android.content.pm.PackageManager
import ai.chakak.platform.GuidanceResult
import ai.chakak.platform.GuidanceTarget
import ai.pleos.playground.navi.constants.NaviErrorCode
import ai.pleos.playground.navi.data.NavigationScreenRequest
import ai.pleos.playground.navi.data.RouteInfo
import ai.pleos.playground.navi.data.RouteStartInfo
import ai.pleos.playground.navi.helper.NaviHelper
import ai.pleos.playground.navi.helper.listener.NaviHelperEventListener
import kotlinx.coroutines.*

private const val GUIDANCE_TIMEOUT_MS = 10_000L

/**
 * Sends the recorded coordinates to Pleos navigation as the destination, then brings navigation to the foreground.
 * STARTED is returned only after the navigation reports onRouteStarted. Coordinates are never logged.
 * [parked] is re-checked right before the route is sent (fail-closed, like capture). It is not checked again for the
 * screen switch: navigation usually comes forward by itself once the route starts, which stops this activity and closes the gate.
 * A timeout after the route was sent is TIMEOUT (the route may still apply); before that it is FAILED.
 */
internal suspend fun startNaviGuidance(context: Context, target: GuidanceTarget, parked: () -> Boolean): GuidanceResult {
    if (listOf("NAVI_ROUTE", "NAVI_ROUTE_SEARCH").any {
            context.checkSelfPermission("pleos.car.permission.$it") != PackageManager.PERMISSION_GRANTED }) return GuidanceResult.DENIED
    var sent = false
    return try {
        withTimeoutOrNull(GUIDANCE_TIMEOUT_MS) {
            withContext(Dispatchers.Main.immediate) {
                val sdk = NaviHelper(context)
                val initialized = CompletableDeferred<Unit>()
                val result = CompletableDeferred<GuidanceResult>()
                val listener = object : NaviHelperEventListener {
                    override fun onRouteStarted(info: RouteStartInfo) { result.complete(GuidanceResult.STARTED) }
                    override fun onError(errorCode: NaviErrorCode) {
                        android.util.Log.w("ChakakNavi", "guidance error: $errorCode")
                        result.complete(GuidanceResult.FAILED)
                    }
                }
                try {
                    sdk.initialize({ initialized.complete(Unit); Unit }, { initialized.completeExceptionally(it); Unit })
                    initialized.await()
                    sdk.addListener(listener)
                    if (!parked()) return@withContext GuidanceResult.RESTRICTED
                    sent = true
                    sdk.requestRoute(RouteInfo(latitude = target.latitude, longitude = target.longitude,
                        poiName = target.name, poiId = "", address = target.address.orEmpty(), poiSubId = ""))
                    val outcome = result.await()
                    // The route is already set at this point, so a failed screen switch must not be reported as a failed guidance.
                    if (outcome == GuidanceResult.STARTED) runCatching { sdk.requestNavigationScreen(NavigationScreenRequest.Foreground) }
                        .onFailure { android.util.Log.w("ChakakNavi", "foreground request failed: ${it.javaClass.simpleName}") }
                    outcome
                } finally {
                    runCatching { sdk.removeListener(listener) }
                    runCatching { sdk.release() }
                }
            }
        } ?: if (sent) GuidanceResult.TIMEOUT else GuidanceResult.FAILED
    } catch (cancelled: CancellationException) { throw cancelled }
    catch (denied: SecurityException) {
        android.util.Log.w("ChakakNavi", "guidance denied: ${denied.message}")
        GuidanceResult.DENIED
    }
    catch (error: Exception) {
        android.util.Log.w("ChakakNavi", "guidance failed: ${error.javaClass.simpleName}")
        GuidanceResult.FAILED
    }
}
