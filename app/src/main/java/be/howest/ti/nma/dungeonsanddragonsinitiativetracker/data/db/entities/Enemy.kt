package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "enemies")
data class Enemy(
    @PrimaryKey val index: String,
    val name: String,
    val url: String
)