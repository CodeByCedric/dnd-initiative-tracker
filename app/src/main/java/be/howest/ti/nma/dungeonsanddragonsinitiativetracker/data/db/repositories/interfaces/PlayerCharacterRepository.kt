package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.repositories.interfaces

import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities.PlayerCharacter

interface PlayerCharacterRepository {
    suspend fun insertPlayerCharacter(playerCharacter: PlayerCharacter): Long
    suspend fun insertAllPlayerCharacters(playerCharacters: List<PlayerCharacter>): List<Long>

    suspend fun getPlayerCharacterIdByName(playerCharacterName: String): Long
}