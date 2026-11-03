package ai.chakak.gleo

import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import ai.chakak.R
import ai.chakak.core.Category
import org.json.JSONObject
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34], application = Application::class)
class GleoContractTest {
    private val context get() = ApplicationProvider.getApplicationContext<Context>()
    @Test fun packagedSchemaMatchesReceiverAndCategoryContract() {
        val schema = JSONObject(context.assets.open("schema.json").bufferedReader().use { it.readText() })
        assertEquals("ai.chakak", schema.getString("packageName"))
        assertEquals("차칵", schema.getString("invokeWord"))
        val actions = schema.getJSONArray("actionCalls")
        assertEquals(2, actions.length())
        val capture = actions.getJSONObject(0).getJSONObject("function")
        val memo = actions.getJSONObject(1).getJSONObject("function")
        assertEquals("CAPTURE_RECORD", capture.getString("name"))
        assertEquals("ADD_RECORD_MEMO", memo.getString("name"))
        listOf(capture, memo).forEach { assertEquals("service", it.getString("component")) }
        val categories = capture.getJSONObject("parameters").getJSONObject("properties").getJSONObject("category").getJSONArray("enum")
        assertEquals(Category.entries.map { it.name }.toSet(), (0 until categories.length()).map { categories.getString(it) }.toSet())
        val required = memo.getJSONObject("parameters").getJSONArray("required")
        assertEquals(setOf("recordId", "memoToken", "memo"), (0 until required.length()).map { required.getString(it) }.toSet())
    }
    @Test fun configuredReceiverStillRejectsUntrustedBroadcast() {
        assertFalse(context.resources.getBoolean(R.bool.gleo_in_motion_approved))
        assertTrue(context.resources.getBoolean(R.bool.gleo_receiver_enabled))
        assertTrue(context.resources.getBoolean(R.bool.gleo_emulator_in_motion_enabled))
        assertEquals("ai.umos.ambientai", context.getString(R.string.gleo_sender_package))
        assertEquals(64, context.getString(R.string.gleo_sender_sha256).length)
        // A plain Application is intentional: rejection must occur before accessing ChakakApp/storage.
        GleoReceiver().onReceive(context, Intent("CAPTURE_RECORD").putExtra("ACTION_PARAM", "{}"))
    }
}
