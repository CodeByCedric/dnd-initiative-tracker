package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.models

data class CampaignPlayerCharacterDetail(
    val playerCharacterId: Long = -1L,
    val name: String,
    val armorClass: Int,
    val initiativeModifier: Int,
    var initiative: Int? = null,
    val isPrimaryCharacter: Boolean = false,
    val isSecondaryCharacter: Boolean = false,
    val isEnemy: Boolean = false,
)