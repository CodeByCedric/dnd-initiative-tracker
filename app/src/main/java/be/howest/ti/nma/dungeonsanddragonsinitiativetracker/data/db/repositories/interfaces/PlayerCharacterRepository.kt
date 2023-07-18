package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.repositories.interfaces

import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities.PlayerCharacter

interface PlayerCharacterRepository {
    suspend fun insertPlayerCharacter(playerCharacter: PlayerCharacter): Long
}