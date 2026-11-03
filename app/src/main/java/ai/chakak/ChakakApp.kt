package ai.chakak
import android.app.Application
import androidx.room.Room
import ai.chakak.core.*
import ai.chakak.data.*
import ai.chakak.platform.Platform
import kotlinx.coroutines.*

class ChakakApp : Application() {
    val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
    lateinit var store: RoomRecordStore; private set
    lateinit var platform: Platform; private set
    lateinit var engine: CaptureEngine; private set
    lateinit var voiceEngine: CaptureEngine; private set
    lateinit var ready: Deferred<Unit>; private set
    override fun onCreate() {
        super.onCreate()
        val db = Room.databaseBuilder(this, ChakakDatabase::class.java, "chakak.db").build()
        store = RoomRecordStore(db)
        platform = createPlatform(this)
        val providers = platform.providers.map { provider -> object : CaptureProvider {
            override val key = provider.key
            override suspend fun read() = withContext(Dispatchers.IO) { provider.read() }
        } }
        engine = CaptureEngine(store, providers, platform.source, platform.touchAllowed)
        voiceEngine = CaptureEngine(store, providers, platform.source, platform.voiceAllowed)
        ready = scope.async { store.recover() }
    }
}
