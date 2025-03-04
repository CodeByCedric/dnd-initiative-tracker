package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities.Enemy
import kotlinx.coroutines.flow.Flow

@Dao
interface EnemyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEnemy(enemy: Enemy)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllEnemies(enemies: List<Enemy>)

    @Query("SELECT * from enemies")
    fun getAllEnemies(): Flow<List<Enemy>>

    @Query("SELECT COUNT(*) FROM enemies")
    suspend fun getRowCount(): Int

}