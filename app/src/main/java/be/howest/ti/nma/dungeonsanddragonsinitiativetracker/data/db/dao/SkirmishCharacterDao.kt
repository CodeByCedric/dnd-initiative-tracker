package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities.SkirmishCharacter
import kotlinx.coroutines.flow.Flow

@Dao
interface SkirmishCharacterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSkirmishCharacter(skirmishCharacter: SkirmishCharacter)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListOfSkirmishCharacters(skirmishCharacters: List<SkirmishCharacter>)

    @Delete
    suspend fun deleteSkirmishCharacter(skirmishCharacter: SkirmishCharacter)

    @Query("DELETE FROM skirmish_characters")
    suspend fun deleteAllSkirmishCharacters()

    @Query("SELECT * FROM skirmish_characters")
    fun getAllSkirmishCharacters(): Flow<List<SkirmishCharacter>>
}