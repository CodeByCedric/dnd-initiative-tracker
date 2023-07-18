package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities

import androidx.room.Entity

@Entity(
    tableName = "campaign_player_characters",
    primaryKeys = ["playerCharacterId", "campaignId"]
)
data class CampaignPlayerCharacter(
    val playerCharacterId: Long,
    val campaignId: Long,
)