package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.characterScreen

import androidx.lifecycle.ViewModel
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.repositories.interfaces.EnemyRepository

class CharacterViewModel(
    private val enemyRepository: EnemyRepository
) : ViewModel() {}

//sealed interface EnemiesUiState {
//    data class Success(val enemies: Response<EnemyResponse>) : EnemiesUiState
//    object Error : EnemiesUiState
//    object Loading : EnemiesUiState
//}
//
//class CharacterViewModel(
//    private val enemyRepository: EnemyRepository
//) : ViewModel() {
//    var enemiesUiState: EnemiesUiState by mutableStateOf(EnemiesUiState.Loading)
//        private set
//
//    init {
//        getEnemiesFromApi()
//    }
//
//    private fun getEnemiesFromApi() {
//        viewModelScope.launch {
//            enemiesUiState = try {
//                val enemyResponse = EnemiesApi.retrofitService.getEnemies()
//                if (enemyResponse.isSuccessful) {
//                    enemyResponse.body()?.results?.forEach { enemy ->
//                        val enemyEntity = Enemy(
//                            enemy.index,
//                            enemy.name,
//                            enemy.url
//                        )
//                        insertEnemyInDB(enemyEntity)
//                    }
//                    EnemiesUiState.Success(enemyResponse)
//                } else {
//                    EnemiesUiState.Error
//                }
//            } catch (e: IOException) {
//                EnemiesUiState.Error
//            }
//        }
//    }
//
//    private suspend fun insertEnemyInDB(enemyEntity: Enemy) {
//        enemyRepository.insertEnemy(enemyEntity)
//    }
//
//    private fun getAllEnemiesFromDB() {
//        viewModelScope.launch {
//            enemyRepository.getAllEnemies()
//        }
//    }
//
//
//}