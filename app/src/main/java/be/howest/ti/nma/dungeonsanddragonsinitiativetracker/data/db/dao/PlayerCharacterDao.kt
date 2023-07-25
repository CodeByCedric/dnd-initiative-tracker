package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities.PlayerCharacter

@Dao
interface PlayerCharacterDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(playerCharacter: PlayerCharacter): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(playerCharacter: List<PlayerCharacter>): List<Long>

    @Query("SELECT playerCharacterId FROM player_characters WHERE name = :playerCharacterName")
    suspend fun getPlayerCharacterIdByName(playerCharacterName: String): Long

    @Delete
    suspend fun delete(playerCharacter: PlayerCharacter)

}