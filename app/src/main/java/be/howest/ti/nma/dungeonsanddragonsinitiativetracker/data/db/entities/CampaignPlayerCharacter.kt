package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "campaign_player_characters",
    primaryKeys = ["playerCharacterId", "campaignId"],
    foreignKeys = [ForeignKey(
        entity = Campaign::class,
        parentColumns = ["campaignId"],
        childColumns = ["campaignId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class CampaignPlayerCharacter(
    val playerCharacterId: Long,
    val campaignId: Long,
)