//package com.plum.networkk.awmapplication.services
//
//import android.app.Notification
//import android.app.Service
//import android.content.Intent
//import android.os.IBinder
//import android.os.PowerManager
//
//// ScreenTimeRecordingService.kt
//// ... (previous code)
//
// class ScreenTimeRecordingService : Service() {
//
//
//    private val screenStateReceiver: ScreenStateReceiver by lazy {
//        ScreenStateReceiver(this)
//    }
//
//    override fun onCreate() {
//        super.onCreate()
//        val powerManager = getSystemService(POWER_SERVICE) as PowerManager
//        var wakeLock = powerManager.newWakeLock(
//            PowerManager.PARTIAL_WAKE_LOCK,
//            "ScreenTimeRecordingService::WakeLock"
//        )
//    }
//
//    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
////        startForeground(1, createNotification())
//        screenStateReceiver.register(this)
//        return START_STICKY
//    }
//
//
//    override fun onDestroy() {
//        super.onDestroy()
//        screenStateReceiver.unregister(this)
//        stopForeground(true)
//    }
//
//    override fun onBind(p0: Intent?): IBinder? {
//        TODO("Not yet implemented")
//    }
//
//    // ... (previous code)
//}
