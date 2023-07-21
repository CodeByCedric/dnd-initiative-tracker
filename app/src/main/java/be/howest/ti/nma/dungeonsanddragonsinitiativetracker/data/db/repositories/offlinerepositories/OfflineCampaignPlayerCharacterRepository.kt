package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.repositories.offlinerepositories

import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.dao.CampaignPlayerCharacterDao
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities.CampaignPlayerCharacter
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.repositories.interfaces.CampaignPlayerCharacterRepository
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.models.CampaignPlayerCharacterDetail
import kotlinx.coroutines.flow.Flow

class OfflineCampaignPlayerCharacterRepository(
    private val campaignPlayerCharacterDao: CampaignPlayerCharacterDao
) : CampaignPlayerCharacterRepository {
    override suspend fun insertCampaignPlayerCharacter(campaignPlayerCharacter: CampaignPlayerCharacter): Long {
        return campaignPlayerCharacterDao.insert(campaignPlayerCharacter)
    }

    override suspend fun insertAllCampaignPlayerCharacters(campaignPlayerCharacters: List<CampaignPlayerCharacter>): List<Long> {
        return campaignPlayerCharacterDao.insertAll(campaignPlayerCharacters)
    }

    override fun getCampaignPrimaryCharactersWithDetails(campaignId: Long): Flow<List<CampaignPlayerCharacterDetail>> {
        return campaignPlayerCharacterDao.getCampaignPrimaryCharactersWithDetails(campaignId)
    }

    override fun getCampaignSecondaryCharactersWithDetails(campaignId: Long): Flow<List<CampaignPlayerCharacterDetail>> {
        return campaignPlayerCharacterDao.getCampaignSecondaryCharactersWithDetails(campaignId)
    }
}