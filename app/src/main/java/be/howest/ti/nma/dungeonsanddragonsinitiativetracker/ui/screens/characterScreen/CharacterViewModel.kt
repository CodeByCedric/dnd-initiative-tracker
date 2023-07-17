package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.characterScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.network.EnemiesApi
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.network.EnemyResponse
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

sealed interface EnemiesUiState {
    data class Success(val enemies: Response<EnemyResponse>) : EnemiesUiState
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
                val enemyResponse = EnemiesApi.retrofitService.getEnemies()
                EnemiesUiState.Success(enemyResponse)
            } catch (e: IOException) {
                EnemiesUiState.Error
            }

        }
    }


}