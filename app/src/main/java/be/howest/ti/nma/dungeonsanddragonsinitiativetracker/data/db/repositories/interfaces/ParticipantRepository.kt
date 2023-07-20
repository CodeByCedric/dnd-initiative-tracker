package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.repositories.interfaces

import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities.Participant
import kotlinx.coroutines.flow.Flow

interface ParticipantRepository {
    suspend fun insertParticipant(participant: Participant): Long

    suspend fun insertAllParticipants(participants: List<Participant>): List<Long>
    suspend fun deleteParticipant(participant: Participant)
    fun getAllParticipants(): Flow<List<Participant>>
    fun getParticipantById(participantId: Long): Flow<Participant>

    fun getParticipantByName(participantName: String): Flow<Participant>

    suspend fun getParticipantIdByName(participantName: String): Long
    fun getParticipantsByIds(participantsIds: List<Long>): Flow<List<Participant>>


}