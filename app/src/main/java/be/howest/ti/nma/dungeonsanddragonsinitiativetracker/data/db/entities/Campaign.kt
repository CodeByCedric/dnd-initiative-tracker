package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities

import android.net.Uri
import androidx.annotation.DrawableRes
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "campaigns"
)
data class Campaign(
    @PrimaryKey(autoGenerate = true) val campaignId: Long = 0,
    @DrawableRes val campaignImageDrawable: Int? = null,
    var campaignImageUri: Uri? = null,
    val campaignName: String,
)