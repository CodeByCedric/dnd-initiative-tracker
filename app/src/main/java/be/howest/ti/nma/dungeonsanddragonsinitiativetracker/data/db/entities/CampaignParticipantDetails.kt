package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities

data class CampaignParticipantDetails(
    val participantId: Long,
    val campaignId: Long,
    val participantName: String,
    val email: String,
    val isDungeonMaster: Boolean = false,
)