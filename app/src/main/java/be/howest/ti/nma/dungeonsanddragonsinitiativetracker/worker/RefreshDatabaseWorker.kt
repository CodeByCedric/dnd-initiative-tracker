package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.DataSource
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.DnDInitiativeTrackerDatabase
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
            val campaigns = DataSource.campaigns
            val database = DnDInitiativeTrackerDatabase.getDatabase(applicationContext)
            if (database.isCampaignTableEmpty()) {
                database.CampaignDao().insertAll(campaigns)
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
}