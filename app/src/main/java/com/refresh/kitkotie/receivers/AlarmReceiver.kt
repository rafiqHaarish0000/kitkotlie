package com.refresh.kitkotie.receivers

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import androidx.core.app.NotificationCompat
import com.refresh.kitkotie.R

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val uri = intent.getParcelableExtra<Uri>("RINGTONE_URI")
        val ringtone = RingtoneManager.getRingtone(context, uri)
        ringtone.play()
    }
}