package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.characterScreen

import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities.Enemy
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class CharacterUiState(
    val enemies: Flow<List<Enemy>> = MutableStateFlow(emptyList()),
)