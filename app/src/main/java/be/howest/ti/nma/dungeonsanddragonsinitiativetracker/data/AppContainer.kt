package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data

import android.content.Context
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.DnDInitiativeTrackerDatabase
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.repositories.interfaces.CampaignRepository
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.repositories.offlinerepositories.OfflineCampaignRepository

interface AppContainer {
    val campaignRepository: CampaignRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val campaignRepository: CampaignRepository by lazy {
        OfflineCampaignRepository(DnDInitiativeTrackerDatabase.getDatabase(context).CampaignDao())
    }


}
