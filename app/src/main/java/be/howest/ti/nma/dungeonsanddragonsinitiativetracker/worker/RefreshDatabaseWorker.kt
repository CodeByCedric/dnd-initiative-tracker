package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.DataSource
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.DnDInitiativeTrackerDatabase
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities.CampaignParticipant
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities.CampaignPlayerCharacter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RefreshDatabaseWorker(
    ctx: Context,
    params: WorkerParameters
) : CoroutineWorker(
    ctx,
    params
) {
    companion object {
        const val WORK_NAME =
            "be.howest.ti.nma.dungeonsanddragonsinitiativetracker.worker.RefreshDatabaseWorker"
    }

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            val database = DnDInitiativeTrackerDatabase.getDatabase(applicationContext)
            val campaigns = DataSource.campaigns
            val participants = DataSource.participants
            val playerCharacters = DataSource.playerCharacters

            //Insert Campaigns
            val listOfCampaignIds = database.CampaignDao().insertAll(campaigns)
            //Insert Participants
            val listOfParticipantIds = database.ParticipantDao().insertAll(participants)

            //InsertCampaignParticipants
            val campaignParticipants = createCampaignParticipants(
                listOfCampaignIds,
                listOfParticipantIds
            )
            database.CampaignParticipantDao().insertAll(campaignParticipants)

            //Insert PlayerCharacters
            val listOfPlayerCharacterIds = database.PlayerCharacterDao().insertAll(playerCharacters)

            //InsertCampaignPlayerCharacter
            listOfCampaignIds.forEach { campaignId ->
                listOfPlayerCharacterIds.forEach { playerCharacterId ->
                    database.CampaignPlayerCharacterDao().insert(
                        CampaignPlayerCharacter(
                            campaignId = campaignId,
                            playerCharacterId = playerCharacterId
                        )
                    )
                }
            }

            Result.success()
        } catch (ex: Exception) {
            Log.e(
                "Database worker",
                "Error seeding database",
                ex
            )
            Result.failure()
        }
    }


    private fun createCampaignParticipants(
        listOfCampaignIds: List<Long>,
        listOfParticipantIds: List<Long>
    ): MutableList<CampaignParticipant> {
        val campaignParticipants = mutableListOf<CampaignParticipant>()
        listOfCampaignIds.forEach { campaignId ->
            listOfParticipantIds.forEach { participantId ->
                val campaignParticipant = CampaignParticipant(
                    participantId = participantId,
                    campaignId = campaignId
                )
                campaignParticipants.add(campaignParticipant)
            }
        }
        return campaignParticipants

    }
}