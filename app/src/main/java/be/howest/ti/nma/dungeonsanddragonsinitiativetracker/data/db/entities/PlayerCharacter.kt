package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "player_characters"
)
data class PlayerCharacter(
    @PrimaryKey(autoGenerate = true) val playerCharacterId: Long = 0,
    val name: String,
    val armorClass: Int,
    val initiativeModifier: Int,
    val isPrimaryCharacter: Boolean = false,
    val isSecondaryCharacter: Boolean = false,
    val isEnemy: Boolean = false,
)