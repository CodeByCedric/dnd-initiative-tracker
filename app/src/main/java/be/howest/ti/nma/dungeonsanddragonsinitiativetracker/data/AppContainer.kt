package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data

import android.content.Context
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.DnDInitiativeTrackerDatabase
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.repositories.interfaces.CampaignParticipantRepository
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.repositories.interfaces.CampaignRepository
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.repositories.interfaces.EnemyRepository
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.repositories.interfaces.ParticipantRepository
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.repositories.offlinerepositories.OfflineCampaignParticipantRepository
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.repositories.offlinerepositories.OfflineCampaignRepository
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.repositories.offlinerepositories.OfflineEnemyRepository
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.repositories.offlinerepositories.OfflineParticipantRepository

interface AppContainer {
    val campaignRepository: CampaignRepository
    val participantRepository: ParticipantRepository
    val campaignParticipantRepository: CampaignParticipantRepository
    val enemyRepository: EnemyRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val campaignRepository: CampaignRepository by lazy {
        OfflineCampaignRepository(DnDInitiativeTrackerDatabase.getDatabase(context).CampaignDao())
    }
    override val participantRepository: ParticipantRepository by lazy {
        OfflineParticipantRepository(
            DnDInitiativeTrackerDatabase.getDatabase(context).ParticipantDao()
        )
    }
    override val campaignParticipantRepository: CampaignParticipantRepository by lazy {
        OfflineCampaignParticipantRepository(
            DnDInitiativeTrackerDatabase.getDatabase(context).CampaignParticipantDao()
        )
    }
    override val enemyRepository: EnemyRepository by lazy {
        OfflineEnemyRepository(DnDInitiativeTrackerDatabase.getDatabase(context).EnemyDao())
    }

}
