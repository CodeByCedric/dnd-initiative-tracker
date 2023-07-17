package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities.Enemy

@Dao
interface EnemyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEnemy(enemy: Enemy)

}