package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data

import android.content.Context
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.DnDInitiativeTrackerDatabase
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.repositories.interfaces.CampaignParticipantRepository
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.repositories.interfaces.CampaignPlayerCharacterRepository
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.repositories.interfaces.CampaignRepository
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.repositories.interfaces.EnemyRepository
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.repositories.interfaces.ParticipantRepository
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.repositories.interfaces.PlayerCharacterRepository
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.repositories.interfaces.SkirmishCharacterRepository
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.repositories.offlinerepositories.OfflineCampaignParticipantRepository
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.repositories.offlinerepositories.OfflineCampaignPlayerCharacterRepository
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.repositories.offlinerepositories.OfflineCampaignRepository
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.repositories.offlinerepositories.OfflineEnemyRepository
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.repositories.offlinerepositories.OfflineParticipantRepository
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.repositories.offlinerepositories.OfflinePlayerCharacterRepository
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.repositories.offlinerepositories.OfflineSkirmishCharacterRepository

interface AppContainer {
    val campaignRepository: CampaignRepository
    val participantRepository: ParticipantRepository
    val campaignParticipantRepository: CampaignParticipantRepository
    val playerCharacterRepository: PlayerCharacterRepository
    val campaignPlayerCharacterRepository: CampaignPlayerCharacterRepository
    val enemyRepository: EnemyRepository
    val skirmishCharacterRepository: SkirmishCharacterRepository
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
    override val playerCharacterRepository: PlayerCharacterRepository by lazy {
        OfflinePlayerCharacterRepository(
            DnDInitiativeTrackerDatabase.getDatabase(context).PlayerCharacterDao()
        )
    }
    override val campaignPlayerCharacterRepository: CampaignPlayerCharacterRepository by lazy {
        OfflineCampaignPlayerCharacterRepository(
            DnDInitiativeTrackerDatabase.getDatabase(context).CampaignPlayerCharacterDao()
        )
    }
    override val enemyRepository: EnemyRepository by lazy {
        OfflineEnemyRepository(DnDInitiativeTrackerDatabase.getDatabase(context).EnemyDao())
    }
    override val skirmishCharacterRepository: SkirmishCharacterRepository by lazy {
        OfflineSkirmishCharacterRepository(
            DnDInitiativeTrackerDatabase.getDatabase(context).SkirmishCharacterDao()
        )
    }

}
