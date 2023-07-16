package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "participants")
data class Participant(
    @PrimaryKey(autoGenerate = true) val participantId: Int = 0,
    var participantName: String,
    var email: String,
    val isDungeonMaster: Boolean = false,
)