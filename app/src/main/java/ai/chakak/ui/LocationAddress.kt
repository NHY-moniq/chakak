package ai.chakak.ui

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Build
import ai.chakak.core.Observation
import ai.chakak.core.Status
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runInterruptible
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withTimeoutOrNull
import org.json.JSONObject
import java.util.Locale
import kotlin.coroutines.resume

internal data class Coordinates(val latitude: Double, val longitude: Double)

internal fun Observation.coordinates(): Coordinates? = if (status != Status.OK) null else runCatching {
    val json = JSONObject(value ?: "{}")
    Coordinates(json.getDouble("latitude"), json.getDouble("longitude")).takeIf {
        it.latitude in -90.0..90.0 && it.longitude in -180.0..180.0
    }
}.getOrNull()

// Prefer the service's full localized address: it supports roads, parcels and foreign addresses.
internal fun Address.localizedAddress(): String? {
    val lines = (0..maxAddressLineIndex).mapNotNull { index ->
        getAddressLine(index)?.trim()?.takeIf { it.isNotEmpty() }
    }.distinct()
    if (lines.isNotEmpty()) return lines.joinToString(" ")
    val country = countryCode?.takeIf { it.length == 2 }?.let {
        Locale.Builder().setRegion(it).build().getDisplayCountry(Locale.KOREA)
    } ?: countryName
    return listOf(country, adminArea, subAdminArea, locality, subLocality,
        thoroughfare, subThoroughfare, featureName, postalCode)
        .mapNotNull { it?.trim()?.takeIf(String::isNotEmpty) }.distinct()
        .joinToString(" ").takeIf { it.isNotEmpty() }
}

internal data class AddressResult(val address: String? = null, val message: String? = null, val retryable: Boolean = true)

internal suspend fun resolveAddress(context: Context, coordinates: Coordinates, roadOnly: Boolean = false): AddressResult {
    return try {
        if (!Geocoder.isPresent()) return AddressResult(message = "위치 좌표는 기록되었습니다. 이 기기는 주소 변환 서비스를 지원하지 않아 주소를 표시할 수 없습니다.", retryable = false)
        withTimeoutOrNull(5000) {
            val geocoder = Geocoder(context, Locale.KOREA)
            val addresses = if (Build.VERSION.SDK_INT >= 33) {
                suspendCancellableCoroutine<List<Address>> { continuation ->
                    geocoder.getFromLocation(coordinates.latitude, coordinates.longitude, 1, object : Geocoder.GeocodeListener {
                        override fun onGeocode(addresses: MutableList<Address>) { if (continuation.isActive) continuation.resume(addresses) }
                        override fun onError(errorMessage: String?) {
                            if (continuation.isActive) continuation.resumeWith(Result.failure(java.io.IOException("Geocoding failed")))
                        }
                    })
                }
            } else {
                @Suppress("DEPRECATION")
                runInterruptible(Dispatchers.IO) { geocoder.getFromLocation(coordinates.latitude, coordinates.longitude, 1).orEmpty() }
            }
            val address = addresses.firstNotNullOfOrNull { if (roadOnly) it.roadAddress() else it.localizedAddress() }
            AddressResult(address, if (address == null) "이 좌표에 해당하는 주소를 찾지 못했습니다." else null)
        } ?: AddressResult(message = "주소 조회 시간이 초과되었습니다.")
    } catch (cancelled: CancellationException) { throw cancelled }
    catch (_: Exception) { AddressResult(message = "주소 조회에 실패했습니다. 네트워크 상태를 확인하고 다시 시도해 주세요.") }
}

/** A district/dong label alone is not a road-name address. */
internal fun Address.roadAddress(): String? {
    val road = thoroughfare?.trim()?.takeIf { it.isNotEmpty() } ?: return null
    return listOf(adminArea, locality, subLocality, road, subThoroughfare)
        .mapNotNull { it?.trim()?.takeIf(String::isNotEmpty) }.distinct().joinToString(" ")
}
