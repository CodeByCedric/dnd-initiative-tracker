package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.dao.CampaignDao
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.dao.CampaignParticipantDao
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.dao.ParticipantDao
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities.Campaign
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities.CampaignParticipant
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities.Participant

@Database(
    entities = [
        Campaign::class,
        Participant::class,
        CampaignParticipant::class],
    version = 1,
    exportSchema = false
)
abstract class DnDInitiativeTrackerDatabase : RoomDatabase() {

    abstract fun CampaignDao(): CampaignDao
    abstract fun ParticipantDao(): ParticipantDao
    abstract fun CampaignParticipantDao(): CampaignParticipantDao

    companion object {
        @Volatile
        private var Instance: DnDInitiativeTrackerDatabase? = null

        fun getDatabase(context: Context): DnDInitiativeTrackerDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    DnDInitiativeTrackerDatabase::class.java,
                    "dnd_initiative_tracker_database"
                )
                    .build()
                    .also { Instance = it }
            }
        }
    }
}

/* The value of a volatile variable is never cached, and all reads and writes are to and from the
main memory. These features help ensure the value of Instance is always up to date and is the same
for all execution threads. It means that changes made by one thread to Instance are immediately
visible to all other threads.

Multiple threads can potentially ask for a database instance at the same time, which results in two
databases instead of one. This issue is known as a race condition. Wrapping the code to get the
database inside a synchronized block means that only one thread of execution at a time can enter
this block of code, which makes sure the database only gets initialized once.

Note: Normally, you would provide a migration object with a migration strategy for when the schema
changes. A migration object is an object that defines how you take all rows with the old schema and
convert them to rows in the new schema, so that no data is lost. Migration is beyond the scope of
this codelab, but the term refers to when the schema is changed and you need to move your date
without losing the data. Since this is a sample app, a simple alternative is to destroy and rebuild
the database, which means that the inventory data is lost. For example, if you change something in
the entity class, like adding a new parameter, you can allow the app to delete and re-initialize the
database.
 */