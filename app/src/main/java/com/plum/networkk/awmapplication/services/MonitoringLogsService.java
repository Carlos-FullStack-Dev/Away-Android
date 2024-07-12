package com.plum.networkk.awmapplication.services;



import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.plum.networkk.awmapplication.R;
import com.plum.networkk.awmapplication.activities.SplashActivity;
import com.plum.networkk.awmapplication.database.msharedpreference.MySharedPreferences;

import java.security.Provider;

public class MonitoringLogsService extends Service {

    public static final String CHANNEL_ID = "MonitoringLogsServiceChanel";
    private final LocalBinder mBinder = new LocalBinder();
    protected Handler handler;
    protected Toast mToast;

    public class LocalBinder extends Binder {
        public MonitoringLogsService getService() {
            return MonitoringLogsService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e("MonitoringLogsService","MonitoringService: OnBind");
        return mBinder;
    }

    @Override
    public void onCreate() {
        Log.e("MonitoringLogsService","MonitoringService: onCreate");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("MonitoringLogsService","MonitoringService: onStartCommand");
        String input = intent.getStringExtra("inputExtra");
        createNotificationChannel();
        Intent notificationIntent = new Intent(this, SplashActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Logs Monitoring Service")
                .setContentText(input)
                .setSmallIcon(R.drawable.app_icon_a)
                .setContentIntent(pendingIntent)
                .build();
        startForeground(1, notification);
        //do heavy work on a background thread
        stopSelf();
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.e("MonitoringLogsService","MonitoringService: onDestroy");
        super.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e("MonitoringLogsService","MonitoringService: onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        Log.e("MonitoringLogsService","MonitoringService: onRebind");
        super.onRebind(intent);
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Log.e("MonitoringLogsService","MonitoringService: onTaskRemoved");
        super.onTaskRemoved(rootIntent);
    }


    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Logs Monitoring Service Channel",
                    NotificationManager.IMPORTANCE_HIGH
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }
}
