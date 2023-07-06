package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.util

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter

@ProvidedTypeConverter
class DrawableTypeConverter(private val context: Context) {
    @TypeConverter
    fun fromDrawableResource(@DrawableRes drawableResId: Int): String {
        return context.resources.getResourceEntryName(drawableResId)
    }

    @TypeConverter
    fun toDrawableResource(drawableResName: String): Int {
        return context.resources.getIdentifier(
            drawableResName,
            "drawable",
            context.packageName
        )
    }

    @TypeConverter
    fun toDrawable(@DrawableRes drawableResId: Int): Drawable? {
        return ContextCompat.getDrawable(
            context,
            drawableResId
        )
    }
}