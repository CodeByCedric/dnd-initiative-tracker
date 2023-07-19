package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.models

data class CampaignPlayerCharacterDetails(
    val campaignId: Long,
    val playerCharacterId: Long,
    val name: String,
    val armorClass: Int,
    val initiativeModifier: Int,
    val isPrimaryCharacter: Boolean,
    val isSecondaryCharacter: Boolean,
)