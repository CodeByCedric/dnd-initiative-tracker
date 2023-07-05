package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.repositories.interfaces

import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities.Participant

interface ParticipantRepository {
    suspend fun insertParticipant(participant: Participant)
    suspend fun deleteParticipant(participant: Participant)
    fun getParticipantStream(participantId: Int)
}