package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities

import androidx.room.Entity

@Entity(
    tableName = "campaign_participants",
    primaryKeys = ["participantId", "campaignId"]
)
data class CampaignParticipant(
    val participantId: Long,
    val campaignId: Long
)