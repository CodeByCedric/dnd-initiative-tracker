package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.repositories.offlinerepositories

import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.dao.SkirmishCharacterDao
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities.SkirmishCharacter
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.repositories.interfaces.SkirmishCharacterRepository
import kotlinx.coroutines.flow.Flow

class OfflineSkirmishCharacterRepository(
    private val skirmishCharacterDao: SkirmishCharacterDao
) : SkirmishCharacterRepository {
    override suspend fun insertSkirmishCharacter(skirmishCharacter: SkirmishCharacter) {
        skirmishCharacterDao.insertSkirmishCharacter(skirmishCharacter)
    }

    override suspend fun insertListOfSkirmishCharacters(skirmishCharacters: List<SkirmishCharacter>) {
        skirmishCharacterDao.insertListOfSkirmishCharacters(skirmishCharacters)
    }

    override suspend fun deleteSkirmishCharacter(skirmishCharacter: SkirmishCharacter) {
        skirmishCharacterDao.deleteSkirmishCharacter(skirmishCharacter)
    }

    override suspend fun deleteAllSkirmishCharacters() {
        skirmishCharacterDao.deleteAllSkirmishCharacters()
    }

    override suspend fun getAllSkirmishCharacters(): Flow<List<SkirmishCharacter>> {
        return skirmishCharacterDao.getAllSkirmishCharacters()
    }

}