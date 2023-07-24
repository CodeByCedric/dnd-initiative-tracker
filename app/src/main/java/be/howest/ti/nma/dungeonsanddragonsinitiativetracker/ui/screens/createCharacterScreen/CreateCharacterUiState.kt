package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.createCharacterScreen

import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities.Enemy
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

data class CreateCharacterUiState(
    val characterName: String = "",
    val armorClass: Int = 0,
    val initiativeModifier: Int = 0,
    val isPrimaryCharacter: Boolean = false,
    val isSecondaryCharacter: Boolean = false,
    val isEnemy: Boolean = false,
    val enemyFlow: Flow<List<Enemy>> = MutableStateFlow(emptyList())
)