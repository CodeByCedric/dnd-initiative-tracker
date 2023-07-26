package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "skirmish_characters"
)
data class SkirmishCharacter(
    @PrimaryKey(autoGenerate = true) val skirmishCharacterId: Long = 0,
    val name: String,
    val armorClass: Int,
    val initiativeModifier: Int,
    var initiative: Int,
    val isPrimaryCharacter: Boolean,
    val isSecondaryCharacter: Boolean,
    val isEnemy: Boolean,
)