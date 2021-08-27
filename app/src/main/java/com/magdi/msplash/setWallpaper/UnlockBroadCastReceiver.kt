package com.magdi.msplash.setWallpaper

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager

class UnlockBroadCastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Log.i(TAG, "onReceive: ${intent.action}")
        WorkManager.getInstance(context).enqueueUniqueWork(
            TAG,
            ExistingWorkPolicy.KEEP,
            OneTimeWorkRequestBuilder<SetWallpaperWorker>().build()
        )
    }

    companion object {
        private const val TAG = "UnlockBroadCastReceiver"
    }
}