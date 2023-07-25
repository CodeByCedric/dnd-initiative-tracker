package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities.CampaignPlayerCharacter
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.models.CampaignPlayerCharacterDetail
import kotlinx.coroutines.flow.Flow

@Dao
interface CampaignPlayerCharacterDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(campaignPlayerCharacter: CampaignPlayerCharacter): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(campaignPlayerCharacters: List<CampaignPlayerCharacter>): List<Long>

    @Delete
    suspend fun delete(campaignPlayerCharacter: CampaignPlayerCharacter)

    @Query(
        "SELECT pc.playerCharacterId, pc.name, pc.armorClass, pc.initiativeModifier, pc.isPrimaryCharacter, pc" +
                ".isSecondaryCharacter, pc.isEnemy " +
                "FROM campaign_player_characters as cpc " +
                "INNER JOIN player_characters as pc on cpc.playerCharacterId = pc.playerCharacterId " +
                "WHERE cpc.campaignId = :campaignId AND isPrimaryCharacter = 1"
    )
    fun getCampaignPrimaryCharactersWithDetails(campaignId: Long): Flow<List<CampaignPlayerCharacterDetail>>

    @Query(
        "SELECT pc.playerCharacterId, pc.name, pc.armorClass, pc.initiativeModifier, pc.isPrimaryCharacter, pc" +
                ".isSecondaryCharacter, pc.isEnemy " +
                "FROM campaign_player_characters as cpc " +
                "INNER JOIN player_characters as pc on cpc.playerCharacterId = pc.playerCharacterId " +
                "WHERE cpc.campaignId = :campaignId AND isSecondaryCharacter = 1"
    )
    fun getCampaignSecondaryCharactersWithDetails(campaignId: Long):
            Flow<List<CampaignPlayerCharacterDetail>>

    @Query(
        "SELECT  pc.playerCharacterId, pc.name, pc.armorClass, pc.initiativeModifier, pc" +
                ".isPrimaryCharacter, pc" +
                ".isSecondaryCharacter, pc.isEnemy " +
                "FROM campaign_player_characters as cpc " +
                "INNER JOIN player_characters as pc on cpc.playerCharacterId = pc.playerCharacterId " +
                "WHERE cpc.campaignId = :campaignId AND isEnemy = 1"
    )
    fun getCampaignEnemyCharactersWithDetails(campaignId: Long):
            Flow<List<CampaignPlayerCharacterDetail>>
}