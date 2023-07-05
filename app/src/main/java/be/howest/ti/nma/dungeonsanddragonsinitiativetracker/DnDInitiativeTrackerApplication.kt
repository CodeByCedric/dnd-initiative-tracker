package be.howest.ti.nma.dungeonsanddragonsinitiativetracker

import android.app.Application
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.AppContainer
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.AppDataContainer

class DnDInitiativeTrackerApplication : Application() {

    /**
     * AppContainer instance used by the rest of classes to obtain dependencies
     */
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}