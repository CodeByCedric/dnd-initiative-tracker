package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.repositories.offlinerepositories

import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.dao.EnemyDao
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities.Enemy
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.repositories.interfaces.EnemyRepository

class OfflineEnemyRepository(
    private val enemyDao: EnemyDao
) : EnemyRepository {
    override suspend fun insertEnemy(enemy: Enemy) {
        enemyDao.insertEnemy(enemy)
    }
}