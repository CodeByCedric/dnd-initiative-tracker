package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "campaign_participants",
    primaryKeys = ["participantId", "campaignId"],
    foreignKeys = [ForeignKey(
        entity = Campaign::class,
        parentColumns = ["campaignId"],
        childColumns = ["campaignId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class CampaignParticipant(
    val participantId: Long,
    val campaignId: Long
)