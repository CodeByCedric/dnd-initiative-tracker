package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.repositories.interfaces

import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities.Enemy
import kotlinx.coroutines.flow.Flow

interface EnemyRepository {
    suspend fun insertEnemy(enemy: Enemy)
    suspend fun insertAllEnemies(enemies: List<Enemy>)
    suspend fun getRowCount(): Int
    fun getAllEnemies(): Flow<List<Enemy>>
}