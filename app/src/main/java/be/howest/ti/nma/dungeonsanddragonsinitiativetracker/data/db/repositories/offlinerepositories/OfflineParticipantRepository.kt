package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.repositories.offlinerepositories

import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.dao.ParticipantDao
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities.Participant
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.repositories.interfaces.ParticipantRepository

class OfflineParticipantRepository(private val participantDao: ParticipantDao) :
    ParticipantRepository {
    override suspend fun insertParticipant(participant: Participant) {
        participantDao.insert(participant)
    }

    override suspend fun deleteParticipant(participant: Participant) {
        participantDao.delete(participant)
    }

    override fun getParticipantStream(participantId: Int) {
        participantDao.getAllParticipants()
    }
}