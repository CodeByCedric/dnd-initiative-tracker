package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.repositories.offlinerepositories

import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.dao.PlayerCharacterDao
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities.PlayerCharacter
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.repositories.interfaces.PlayerCharacterRepository

class OfflinePlayerCharacterRepository(
    private val playerCharacterDao: PlayerCharacterDao
) : PlayerCharacterRepository {
    override suspend fun insertPlayerCharacter(playerCharacter: PlayerCharacter): Long {
        return playerCharacterDao.insert(playerCharacter)
    }
}