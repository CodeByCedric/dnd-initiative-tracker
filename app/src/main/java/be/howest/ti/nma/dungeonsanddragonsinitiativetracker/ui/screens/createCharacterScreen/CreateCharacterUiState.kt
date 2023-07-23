package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.createCharacterScreen

data class CreateCharacterUiState(
    val characterName: String = "",
    val armorClass: Int = 0,
    val initiativeModifier: Int = 0,
    val isPrimaryCharacter: Boolean = false,
    val isSecondaryCharacter: Boolean = false,
    val isEnemy: Boolean = false
)