package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.DnDInitiativeTrackerDatabase
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities.Enemy
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.network.EnemiesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class EnemySyncWorker(
    appContext: Context,
    workerParams: WorkerParameters,
) : CoroutineWorker(
    appContext,
    workerParams
) {

    companion object {
        const val WORK_NAME =
            "be.howest.ti.nma.dungeonsanddragonsinitiativetracker.worker.EnemySyncWorker"
    }

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            val database = DnDInitiativeTrackerDatabase.getDatabase(applicationContext)
            val enemyApiResponse = EnemiesApi.retrofitService.getEnemies()
            if (enemyApiResponse.isSuccessful) {
                val enemyApiResponseBody = enemyApiResponse.body()?.results ?: emptyList()
                val enemies = enemyApiResponseBody.map { enemy ->
                    Enemy(
                        enemy.index,
                        enemy.name,
                        enemy.url
                    )
                }
                database.EnemyDao().insertAllEnemies(enemies)
            }
            Result.success()
        } catch (ex: Exception) {
            Log.e(
                "Fetch API and insert in DB worker",
                "Error fetching from API and/or inserting in database",
                ex
            )
            Result.failure()
        }
    }

}