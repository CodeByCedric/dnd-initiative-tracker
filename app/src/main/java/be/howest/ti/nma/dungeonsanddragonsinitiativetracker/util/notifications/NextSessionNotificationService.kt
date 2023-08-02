package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.util.notifications

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.R
import kotlin.random.Random

class NextSessionNotificationService(
    private val context: Context,
) {
    private val notificationManager = context.getSystemService(NotificationManager::class.java)
    fun showBasicNotification() {
        val notification = NotificationCompat.Builder(
            context,
            "Next Session Notification"
        )
            .setSmallIcon(R.drawable.ic_dragon_icon)
            .setContentTitle("Next Session")
            .setContentText("The Next Session is coming up!")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            // Dissapears after clicking the notification
            .setAutoCancel(true)
            .build()

        notificationManager.notify(
            Random.nextInt(),
            notification
        )
    }

}