package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.repositories.offlinerepositories

import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.dao.ParticipantDao
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities.Participant
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.repositories.interfaces.ParticipantRepository
import kotlinx.coroutines.flow.Flow

class OfflineParticipantRepository(private val participantDao: ParticipantDao) :
    ParticipantRepository {
    override suspend fun insertParticipant(participant: Participant): Long {
        return participantDao.insert(participant)
    }

    override suspend fun insertAllParticipants(participants: List<Participant>): List<Long> {
        return participantDao.insertAll(participants)
    }

    override suspend fun deleteParticipant(participant: Participant) {
        participantDao.delete(participant)
    }

    override fun getAllParticipants(): Flow<List<Participant>> {
        return participantDao.getAllParticipants()
    }

    override fun getParticipantById(participantId: Long): Flow<Participant> {
        return participantDao.getParticipant(participantId)
    }

    override fun getParticipantsByIds(participantsIds: List<Long>): Flow<List<Participant>> {
        return participantDao.getParticipantsByIds(participantsIds)
    }


}