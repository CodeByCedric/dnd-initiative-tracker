package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.characterScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.network.EnemiesApi
import kotlinx.coroutines.launch

class CharacterViewModel : ViewModel() {
    var enemiesUiState: String by mutableStateOf("")
        private set

    init {
        getEnemies()
    }

    private fun getEnemies() {
        viewModelScope.launch {
            val listResult = EnemiesApi.retrofitService.getEnemies()
            enemiesUiState = listResult
        }
    }


}