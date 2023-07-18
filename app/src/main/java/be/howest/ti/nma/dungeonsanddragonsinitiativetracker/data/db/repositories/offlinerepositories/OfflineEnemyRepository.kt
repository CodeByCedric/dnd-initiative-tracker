package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.repositories.offlinerepositories

import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.dao.EnemyDao
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities.Enemy
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.repositories.interfaces.EnemyRepository
import kotlinx.coroutines.flow.Flow

class OfflineEnemyRepository(
    private val enemyDao: EnemyDao
) : EnemyRepository {
    override suspend fun insertEnemy(enemy: Enemy) {
        enemyDao.insertEnemy(enemy)
    }

    override suspend fun insertAllEnemies(enemies: List<Enemy>) {
        enemyDao.insertAllEnemies(enemies)
    }

    override suspend fun getRowCount(): Int {
        return enemyDao.getRowCount()
    }

    override fun getAllEnemies(): Flow<List<Enemy>> {
        return enemyDao.getAllEnemies()
    }
}