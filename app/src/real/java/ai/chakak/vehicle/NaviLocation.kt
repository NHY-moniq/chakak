package ai.chakak.vehicle

import android.content.Context
import android.content.pm.PackageManager
import ai.chakak.core.*
import ai.pleos.playground.navi.helper.NaviHelper
import ai.pleos.playground.navi.helper.listener.NaviHelperEventListener
import ai.pleos.playground.navi.data.CurrentLocationInfo
import ai.pleos.playground.navi.constants.NaviErrorCode
import kotlinx.coroutines.*
import org.json.JSONObject

internal data class NaviLocationResult(val observation: Observation? = null, val reason: String)

internal suspend fun readNaviLocation(context: Context): NaviLocationResult {
    if (context.checkSelfPermission("pleos.car.permission.NAVI_ROUTE") != PackageManager.PERMISSION_GRANTED)
        return NaviLocationResult(reason = "Pleos 주소 조회 권한이 없습니다. 차량·위치 조회 권한을 허용한 후 새로 기록해 주세요.")
    android.util.Log.i("ChakakNavi", "capture lookup started; permission granted; timeoutMs=3500")
    var connected = false
    return try {
        withTimeoutOrNull(3500) {
            withContext(Dispatchers.Main.immediate) {
                val sdk = NaviHelper(context)
                val initialized = CompletableDeferred<Unit>()
                val result = CompletableDeferred<CurrentLocationInfo>()
                val listener = object : NaviHelperEventListener {
                    override fun onCurrentLocationInfo(info: CurrentLocationInfo) {
                        android.util.Log.i("ChakakNavi", "location received; addressPresent=${!info.address.isNullOrBlank()}")
                        result.complete(info)
                    }
                    override fun onError(errorCode: NaviErrorCode) {
                        android.util.Log.w("ChakakNavi", "SDK error: $errorCode")
                        result.completeExceptionally(IllegalStateException(errorCode.toString()))
                    }
                }
                try {
                    sdk.initialize({ initialized.complete(Unit); Unit }, { initialized.completeExceptionally(it); Unit })
                    initialized.await()
                    connected = true
                    android.util.Log.i("ChakakNavi", "connected; requesting current location")
                    sdk.addListener(listener)
                    sdk.getCurrentLocationInfo()
                    val info = result.await()
                    require(info.latitude.isFinite() && info.latitude in -90.0..90.0 &&
                        info.longitude.isFinite() && info.longitude in -180.0..180.0)
                    val address = info.address?.trim().orEmpty()
                    val json = JSONObject().put("latitude", info.latitude).put("longitude", info.longitude)
                        .put("addressSource", "Pleos NaviHelper")
                    if (address.isNotEmpty()) json.put("address", address)
                    else json.put("addressMessage", "Pleos 내비게이션이 주소를 반환하지 않았습니다.")
                    NaviLocationResult(Observation("location", Status.OK, json.toString(), System.currentTimeMillis()), "")
                } finally {
                    runCatching { sdk.removeListener(listener) }
                    runCatching { sdk.release() }
                }
            }
        } ?: run {
            android.util.Log.w("ChakakNavi", "timeout; connected=$connected")
            NaviLocationResult(reason = if (connected)
                "Pleos 내비게이션에 연결됐지만 위치·주소 응답이 없습니다. 내비게이션 앱의 동작 상태를 확인해 주세요."
            else "Pleos 내비게이션 서비스 연결이 지연되고 있습니다. 내비게이션 앱의 실행 상태를 확인해 주세요.")
        }
    } catch (cancelled: CancellationException) { throw cancelled }
    catch (error: Exception) {
        android.util.Log.w("ChakakNavi", "lookup failed: ${error.javaClass.simpleName}")
        NaviLocationResult(reason = "Pleos 내비게이션에서 위치·주소를 조회하지 못했습니다.") }
}
