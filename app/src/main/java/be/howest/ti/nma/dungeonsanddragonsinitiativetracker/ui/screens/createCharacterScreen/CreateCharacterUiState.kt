package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.createCharacterScreen

import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities.Enemy
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

data class CreateCharacterUiState(
    val characterName: String = "",
    val armorClass: String = "10",
    val initiativeModifier: String = "0",
    val isPrimaryCharacter: Boolean = false,
    val isSecondaryCharacter: Boolean = false,
    val isEnemy: Boolean = false,
    val enemyFlow: Flow<List<Enemy>> = MutableStateFlow(emptyList())
)