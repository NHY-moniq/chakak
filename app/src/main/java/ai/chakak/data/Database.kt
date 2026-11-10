package ai.chakak.data

import androidx.room.*
import ai.chakak.core.*
import kotlinx.coroutines.flow.map

@Entity(tableName = "records")
data class RecordRow(@PrimaryKey val id: String, val createdAt: Long, val category: String, val source: String,
    val title: String = "", val memo: String = "", val collecting: Boolean = true,
    val memoToken: String? = null, val memoExpiresAt: Long? = null)
@Entity(tableName = "observations", primaryKeys = ["recordId", "key"],
    foreignKeys = [ForeignKey(entity = RecordRow::class, parentColumns = ["id"], childColumns = ["recordId"], onDelete = ForeignKey.CASCADE)])
data class ObservationRow(val recordId: String, val key: String, val status: String, val value: String? = null, val observedAt: Long? = null)
// Tombstones contain only request IDs, preventing a repeated broadcast from resurrecting a deleted record.
@Entity(tableName = "requests") data class RequestRow(@PrimaryKey val id: String)
data class RecordWithObservations(@Embedded val record: RecordRow,
    @Relation(parentColumn = "id", entityColumn = "recordId") val observations: List<ObservationRow>)
data class PendingMemo(val id: String, val memoToken: String)
@Dao interface RecordDao {
    @Transaction @Query("SELECT * FROM records ORDER BY createdAt DESC") fun watch(): kotlinx.coroutines.flow.Flow<List<RecordWithObservations>>
    @Insert(onConflict = OnConflictStrategy.IGNORE) suspend fun request(row: RequestRow): Long
    @Insert suspend fun insert(row: RecordRow)
    @Insert suspend fun insertObservations(rows: List<ObservationRow>)
    @Query("UPDATE observations SET status=:status, value=:value, observedAt=:at WHERE recordId=:id AND `key`=:key AND status='PENDING' AND EXISTS(SELECT 1 FROM records WHERE id=:id AND collecting=1)")
    suspend fun observe(id: String, key: String, status: String, value: String?, at: Long?)
    @Query("UPDATE observations SET status=:status WHERE recordId=:id AND status='PENDING'") suspend fun pending(id: String, status: String)
    @Query("UPDATE records SET collecting=0 WHERE id=:id") suspend fun finish(id: String)
    @Query("SELECT id FROM records WHERE collecting=1") suspend fun collecting(): List<String>
    @Query("UPDATE records SET title=:title, category=:category, memo=:memo, memoToken=NULL, memoExpiresAt=NULL WHERE id=:id") suspend fun edit(id: String, title: String, category: String, memo: String)
    @Query("DELETE FROM records WHERE id=:id") suspend fun delete(id: String)
    @Query("UPDATE records SET memoToken=NULL, memoExpiresAt=NULL WHERE memoToken IS NOT NULL") suspend fun clearTokens()
    @Query("UPDATE records SET memoToken=:token, memoExpiresAt=:expires WHERE id=:id") suspend fun token(id: String, token: String, expires: Long)
    @Query("SELECT id, memoToken FROM records WHERE memoToken IS NOT NULL AND memoExpiresAt>:now LIMIT 1") suspend fun pendingMemo(now: Long): PendingMemo?
    @Query("UPDATE records SET memo=:memo, memoToken=NULL, memoExpiresAt=NULL WHERE id=:id AND memoToken=:token AND memoExpiresAt>:now")
    suspend fun memo(id: String, token: String, now: Long, memo: String): Int
}
@Database(entities = [RecordRow::class, ObservationRow::class, RequestRow::class], version = 1, exportSchema = true)
abstract class ChakakDatabase : RoomDatabase() { abstract fun records(): RecordDao }
class RoomRecordStore(private val db: ChakakDatabase) : RecordStore {
    private val dao = db.records()
    override val records = dao.watch().map { rows -> rows.map { (r, observations) ->
        Record(r.id, r.createdAt, Category.valueOf(r.category), Source.valueOf(r.source), r.title, r.memo, r.collecting,
            observations.map { Observation(it.key, Status.valueOf(it.status), it.value, it.observedAt) }) } }
    override suspend fun begin(id: String, at: Long, category: Category, source: Source, keys: List<String>) = db.withTransaction {
        if (dao.request(RequestRow(id)) == -1L) false else {
            dao.insert(RecordRow(id, at, category.name, source.name))
            dao.insertObservations(keys.distinct().map { ObservationRow(id, it, Status.PENDING.name) }); true
        }
    }
    override suspend fun observe(id: String, observation: Observation) {
        dao.observe(id, observation.key, observation.status.name, observation.value, observation.observedAt)
    }
    override suspend fun finish(id: String, pendingStatus: Status) = db.withTransaction { dao.pending(id, pendingStatus.name); dao.finish(id) }
    override suspend fun recover() = db.withTransaction { dao.collecting().forEach { dao.pending(it, Status.INTERRUPTED.name); dao.finish(it) } }
    override suspend fun edit(id: String, title: String, category: Category, memo: String) { dao.edit(id, title.take(120), category.name, memo.take(2000)) }
    override suspend fun delete(id: String) = dao.delete(id)
    override suspend fun deleteMany(ids: List<String>) = db.withTransaction { ids.distinct().forEach { dao.delete(it) } }
    override suspend fun issueMemoToken(id: String, token: String, expiresAt: Long) = db.withTransaction { dao.clearTokens(); dao.token(id, token, expiresAt) }
    override suspend fun addMemo(id: String, token: String, now: Long, memo: String) = dao.memo(id, token, now, memo.take(2000)) == 1
    override suspend fun addPendingMemo(now: Long, memo: String) = db.withTransaction {
        val pending = dao.pendingMemo(now)
        pending != null && dao.memo(pending.id, pending.memoToken, now, memo.take(2000)) == 1
    }
}
