package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.repositories.offlinerepositories

import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.dao.CampaignPlayerCharacterDao
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities.CampaignPlayerCharacter
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.repositories.interfaces.CampaignPlayerCharacterRepository

class OfflineCampaignPlayerCharacterRepository(
    private val campaignPlayerCharacterDao: CampaignPlayerCharacterDao
) : CampaignPlayerCharacterRepository {
    override suspend fun insertCampaignPlayerCharacter(campaignPlayerCharacter: CampaignPlayerCharacter): Long {
        return campaignPlayerCharacterDao.insert(campaignPlayerCharacter)
    }
}