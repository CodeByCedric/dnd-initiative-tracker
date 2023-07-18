package be.howest.ti.nma.dungeonsanddragonsinitiativetracker

import android.app.Application
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.AppContainer
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.AppDataContainer
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.worker.RefreshDatabaseWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DnDInitiativeTrackerApplication : Application() {

    /**
     * AppContainer instance used by the rest of classes to obtain dependencies
     */
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)

        CoroutineScope(Dispatchers.Default).launch {
            setupUniqueWork()
        }
    }

    private fun setupUniqueWork() {
        val workManager = WorkManager.getInstance(applicationContext)
        val refreshDataRequest = OneTimeWorkRequestBuilder<RefreshDatabaseWorker>().build()
        workManager.enqueue(refreshDataRequest)
    }
}