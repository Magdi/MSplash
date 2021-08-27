package com.magdi.msplash

import android.app.Notification
import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.work.*
import com.magdi.msplash.download.DownloadWorker
import com.magdi.msplash.setWallpaper.UnlockBroadCastReceiver
import com.magdi.msplash.setWallpaper.WallpaperService
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startWallpaperService()
    }

    private fun startWallpaperService() {
        startForegroundService(Intent(this, WallpaperService::class.java))
    }

    override fun onStart() {
        super.onStart()
        val workRequest = PeriodicWorkRequestBuilder<DownloadWorker>(1, TimeUnit.HOURS)
            .build()
        WorkManager.getInstance(this).enqueue(workRequest)
    }
}