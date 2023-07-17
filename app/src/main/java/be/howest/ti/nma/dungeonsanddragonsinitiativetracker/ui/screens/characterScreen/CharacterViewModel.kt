package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.characterScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.network.EnemiesApi
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface EnemiesUiState {
    data class Success(val enemies: String) : EnemiesUiState
    object Error : EnemiesUiState
    object Loading : EnemiesUiState
}

class CharacterViewModel : ViewModel() {
    var enemiesUiState: EnemiesUiState by mutableStateOf(EnemiesUiState.Loading)
        private set

    init {
        getEnemies()
    }

    private fun getEnemies() {
        viewModelScope.launch {
            enemiesUiState = try {
                val listResult = EnemiesApi.retrofitService.getEnemies()
                EnemiesUiState.Success(listResult)
            } catch (e: IOException) {
                EnemiesUiState.Error
            }

        }
    }


}