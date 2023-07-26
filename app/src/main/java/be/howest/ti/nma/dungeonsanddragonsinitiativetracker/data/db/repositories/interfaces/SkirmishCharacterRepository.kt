package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.repositories.interfaces

import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities.SkirmishCharacter
import kotlinx.coroutines.flow.Flow

interface SkirmishCharacterRepository {
    suspend fun insertSkirmishCharacter(skirmishCharacter: SkirmishCharacter)

    suspend fun insertListOfSkirmishCharacters(skirmishCharacters: List<SkirmishCharacter>)

    suspend fun deleteSkirmishCharacter(skirmishCharacter: SkirmishCharacter)

    suspend fun deleteAllSkirmishCharacters()

    suspend fun getAllSkirmishCharacters(): Flow<List<SkirmishCharacter>>
}