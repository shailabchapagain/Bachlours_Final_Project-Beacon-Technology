package com.example.ipbeacons_mobile_app.ui.BeaconDetection;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import com.example.ipbeacons_mobile_app.R;
import com.example.ipbeacons_mobile_app.ui.Home.HomeActivity;
import com.example.ipbeacons_mobile_app.ui.Login.LoginActivity;
import com.example.ipbeacons_mobile_app.ui.Storage.SharedPrefManager;

import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.Region;

public class BeaconDetectionActivity extends Application implements MonitorNotifier {
    private static final String TAG = "BeaconDetection";
    public static final Region wildcardRegion = new Region("wildcardRegion", null, null, null);
    public static boolean insideRegion = false;
    SharedPrefManager sharedPrefManager;


    public  void onCreate(){
        super.onCreate();
        sharedPrefManager=new SharedPrefManager(getApplicationContext());

        BeaconManager beaconManager = org.altbeacon.beacon.BeaconManager.getInstanceForApplication(this);

        beaconManager.getBeaconParsers().clear();

        beaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"));

        beaconManager.setDebug(true);

        Notification.Builder builder = new Notification.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher_round);
        builder.setContentTitle("Scanning for Beacons");
        Intent intent = new Intent(this, HomeActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT
        );
        builder.setContentIntent(pendingIntent);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("Notification ChannelID",
                    "NotificationName", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("Notification Channel Description");
            NotificationManager notificationManager = (NotificationManager) getSystemService(
                    Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
            builder.setChannelId(channel.getId());
        }
        beaconManager.enableForegroundServiceScanning(builder.build(), 456);

        beaconManager.setEnableScheduledScanJobs(false);
        beaconManager.setBackgroundBetweenScanPeriod(0);
        beaconManager.setBackgroundScanPeriod(1100);

        Log.d(TAG, "Background Monitoring");
        beaconManager.addMonitorNotifier(this);

        for (Region region: beaconManager.getMonitoredRegions()) {
            beaconManager.stopMonitoring(region);
        }

        beaconManager.startMonitoring(wildcardRegion);

        }


    @Override
    public void didEnterRegion(Region region) {
        Log.d(TAG, "did enter region.");
        insideRegion = true;
        // Send a notification to the user whenever a Beacon
        // matching a Region (defined above) are first seen.
        Log.d(TAG, "Sending notification.");
        sendNotification();

    }


    @Override
    public void didExitRegion(Region region) {
        insideRegion = false;


    }

    @Override
    public void didDetermineStateForRegion(int state, Region region) {


    }

    private void sendNotification() {
        NotificationManager notificationManager =
                (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("IPBeacon Notifications",
                    "IPBeacon Notifications", NotificationManager.IMPORTANCE_HIGH);
            channel.enableLights(true);
            channel.enableVibration(true);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            notificationManager.createNotificationChannel(channel);
            builder = new Notification.Builder(this, channel.getId());
        }
        else {
            builder = new Notification.Builder(this);
            builder.setPriority(Notification.PRIORITY_HIGH);
        }
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntent(new Intent(this, HomeActivity.class));
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        TaskStackBuilder stackBuilder1 = TaskStackBuilder.create(this);
        stackBuilder1.addNextIntent(new Intent(this, LoginActivity.class));
        PendingIntent resultPending = stackBuilder1.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setSmallIcon(R.mipmap.ic_launcher_round);
        builder.setContentTitle("Beacon Detected");
        builder.setContentText("You have important notification incoming. Tap here to view");
        if(sharedPrefManager.isLoggedIn()){
            builder.setContentIntent(resultPendingIntent);

        }
        else{
            builder.setContentIntent(resultPending);

        }

        notificationManager.notify(1, builder.build());
    }
}
