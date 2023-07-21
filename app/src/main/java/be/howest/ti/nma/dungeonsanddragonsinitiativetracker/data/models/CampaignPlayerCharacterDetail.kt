package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.models

data class CampaignPlayerCharacterDetail(
    val name: String,
    val armorClass: Int,
    val initiativeModifier: Int,
    var initiative: Int? = null,
    val isPrimaryCharacter: Boolean,
    val isSecondaryCharacter: Boolean,
)