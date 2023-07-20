package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities.Participant
import kotlinx.coroutines.flow.Flow

@Dao
interface ParticipantDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(participant: Participant): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(participant: List<Participant>): List<Long>

    @Delete
    suspend fun delete(participant: Participant)

    @Query("SELECT * from participants")
    fun getAllParticipants(): Flow<List<Participant>>

    @Query("SELECT * FROM participants WHERE participantId = :participantId")
    fun getParticipantById(participantId: Long): Flow<Participant>

    @Query("SELECT * FROM participants WHERE participantId = :participantName")
    fun getParticipantByName(participantName: String): Flow<Participant>

    @Query("SELECT participantId FROM participants WHERE participantId = :participantName")
    suspend fun getParticipantIdByName(participantName: String): Long

    @Query("SELECT * FROM participants WHERE participantId IN (:participantIds)")
    fun getParticipantsByIds(participantIds: List<Long>): Flow<List<Participant>>
}