package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.skirmishScreen

import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities.SkirmishCharacter

data class SkirmishUiState(
    val sortedListOfSkirmishCharacters: List<SkirmishCharacter> = emptyList()
)