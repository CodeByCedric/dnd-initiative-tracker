package be.howest.ti.nma.dungeonsanddragonsinitiativetracker

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.AppContainer
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.AppDataContainer
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.DnDInitiativeTrackerDatabase
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.worker.EnemySyncWorker
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.worker.RefreshDatabaseWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class DnDInitiativeTrackerApplication : Application() {

    /**
     * AppContainer instance used by the rest of classes to obtain dependencies
     */
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()

        val notificationChannel = NotificationChannel(
            getString(R.string.notification_channel_id),
            getString(R.string.notification_channel_name),
            NotificationManager.IMPORTANCE_HIGH,
        )

        notificationChannel.description = getString(R.string.notification_channel_description)
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(notificationChannel)


        container = AppDataContainer(this)

        CoroutineScope(Dispatchers.Default).launch {
            setupUniqueWork()
        }
    }

    private suspend fun setupUniqueWork() {
        val database = DnDInitiativeTrackerDatabase.getDatabase(applicationContext)
        val workManager = WorkManager.getInstance(applicationContext)

        if (database.CampaignDao().getRowCount() == 0) {
            val refreshDataRequest = OneTimeWorkRequestBuilder<RefreshDatabaseWorker>().build()
            workManager.enqueue(refreshDataRequest)
        }
        val enemySyncRequest = PeriodicWorkRequestBuilder<EnemySyncWorker>(
            repeatIntervalTimeUnit
            = TimeUnit.DAYS,
            repeatInterval = 28
        ).build()
        workManager.enqueue(enemySyncRequest)

    }
}