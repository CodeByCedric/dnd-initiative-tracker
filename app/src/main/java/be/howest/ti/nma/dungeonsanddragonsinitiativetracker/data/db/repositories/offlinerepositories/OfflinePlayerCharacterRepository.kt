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

    override suspend fun insertAllPlayerCharacters(playerCharacters: List<PlayerCharacter>):
            List<Long> {
        return playerCharacterDao.insertAll(playerCharacters)
    }

    override suspend fun getPlayerCharacterIdByName(playerCharacterName: String): Long {
        return playerCharacterDao.getPlayerCharacterIdByName(playerCharacterName)
    }
}