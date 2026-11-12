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
        assertEquals(1, actions.length())
        val capture = actions.getJSONObject(0).getJSONObject("function")
        assertEquals(CAPTURE_RECORD, capture.getString("name"))
        assertEquals("service", capture.getString("component"))
        val properties = capture.getJSONObject("parameters").getJSONObject("properties")
        assertEquals(setOf("category"), properties.keys().asSequence().toSet())
        val categories = capture.getJSONObject("parameters").getJSONObject("properties").getJSONObject("category").getJSONArray("enum")
        assertEquals(Category.entries.map { it.name }.toSet(), (0 until categories.length()).map { categories.getString(it) }.toSet())
    }
    @Test fun configuredFlagsAndCallbackTarget() {
        assertTrue(context.resources.getBoolean(R.bool.gleo_in_motion_approved))
        assertTrue(context.resources.getBoolean(R.bool.gleo_receiver_enabled))
        assertTrue(context.resources.getBoolean(R.bool.gleo_emulator_in_motion_enabled))
        assertEquals("ai.umos.ambientai", context.getString(R.string.gleo_package))
    }
    @Test fun acceptsObservedPrefixedAndDocumentedBareActions() {
        assertEquals(CAPTURE_RECORD, gleoFunction("ai.chakak", "ai.chakak.CAPTURE_RECORD"))
        assertNull(gleoFunction("ai.chakak", "ai.chakak.ADD_RECORD_MEMO"))
        assertEquals(CAPTURE_RECORD, gleoFunction("ai.chakak", "CAPTURE_RECORD"))
        assertNull(gleoFunction("ai.chakak", "other.app.CAPTURE_RECORD"))
        assertNull(gleoFunction("ai.chakak", "ai.chakak.DELETE_RECORD"))
        assertNull(gleoFunction("ai.chakak", null))
    }
    @Test fun unknownActionIsIgnoredBeforeTouchingStorage() {
        // A plain Application is intentional: an unrelated action must return before accessing ChakakApp/storage.
        GleoReceiver().onReceive(context, Intent("ai.chakak.DELETE_RECORD").putExtra("ACTION_PARAM", "{}"))
    }
}
