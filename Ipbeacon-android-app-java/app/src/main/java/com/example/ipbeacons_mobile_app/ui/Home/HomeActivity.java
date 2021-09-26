package com.example.ipbeacons_mobile_app.ui.Home;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ipbeacons_mobile_app.MainActivity;
import com.example.ipbeacons_mobile_app.R;
import com.example.ipbeacons_mobile_app.api.ApiClient;
import com.example.ipbeacons_mobile_app.api.Info;
import com.example.ipbeacons_mobile_app.api.InfoAdapter;
import com.example.ipbeacons_mobile_app.ui.BeaconDetection.BeaconDetectionActivity;
import com.example.ipbeacons_mobile_app.ui.Login.LoginActivity;
import com.pluscubed.recyclerfastscroll.RecyclerFastScroller;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends Activity implements MonitorNotifier {
    protected static final String TAG = "HomeActivity";
    private static final int PERMISSION_REQUEST_FINE_LOCATION = 1;
    private static final int PERMISSION_REQUEST_BACKGROUND_LOCATION = 2;
    private BeaconManager beaconManager = BeaconManager.getInstanceForApplication(this);


    RecyclerView recyclerView;
    ProgressBar progressBar;
    LinearLayoutManager linearLayoutManager;
    InfoAdapter adapter;
    TextView textView;
    List<Info>infoList=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);



        verifyBluetooth();
        requestPermissions();
        BeaconManager.getInstanceForApplication(this).addMonitorNotifier(this);
    }


    @Override
    protected void onResume() {
        super.onResume();

        setContentView(R.layout.activity_home);
        recyclerView = findViewById(R.id.recycleview);
        progressBar = findViewById(R.id.progress);
        linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new InfoAdapter(infoList,getApplicationContext());
        textView= findViewById(R.id.text);
        recyclerView.setAdapter(adapter);
        progressBar.setVisibility(View.VISIBLE);



        RangeNotifier rangeNotifier = new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
                if(beacons.size()>0){
                    for(Beacon b:beacons){
                        Call<List<Info>> infoCall = ApiClient.getApi().getInfos(b.getId1().toString());

                        infoCall.enqueue(new Callback<List<Info>>() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onResponse(Call<List<Info>> call, Response<List<Info>> response) {
                                if(response.isSuccessful() && response.body()!=null){

                                    infoList.clear();
                                    infoList.addAll(response.body());
                                    adapter.notifyDataSetChanged();
                                    progressBar.setVisibility(View.GONE);
                                    textView.setVisibility(textView.GONE);
                                }else{
                                    textView.setVisibility(textView.VISIBLE);


                                }
                              /*  if(!response.isSuccessful()){
                                    textView.setText("Code: "+ response.code());
                                    return;
                                }
                                List<Info> infos =response.body();
                                for(Info beacon : infos){
                                    String content ="";
                                 *//*   content +="Beacon_ID: " + beacon.getBeaconID() + "\n";
                                    content +="Information_ID: " + beacon.getInforamtionID() + "\n";
                                    content +="Datetime: " + beacon.getDatetime() + "\n";*//*
                                    content +="Information_Text: " + beacon.getInformationText() + "\n";

                                    //
                                    //     content +="NameOfBeacon: " + beacon.getBeaconName() + "\n";

                                    textView.append(content);

                                }*/
                            }


                            @Override
                            public void onFailure(Call<List<Info>> call, Throwable t) {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(HomeActivity.this,"Error: "+ t.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                            });


                      /*  if(b.getId1().toString().equals(beaconIDl))
                            Display("specific beacon found");*/
                    }
                }
                else if(beacons.size()==0){
                    progressBar.setVisibility(View.GONE);
                }
                    /*for(Beacon b:beacons){
                        if(b.getId1().toString().equals(beaconIDl))
                        Display("specific beacon found");
                    }*/

            }
        };

        beaconManager.addRangeNotifier(rangeNotifier);
        beaconManager.startRangingBeacons(BeaconDetectionActivity.wildcardRegion);


    }

    @Override
    public void didEnterRegion(Region region) {

    }

    @Override
    public void didExitRegion(Region region) {

    }

    @Override
    public void didDetermineStateForRegion(int state, Region region) {

    }

    private void verifyBluetooth() {
        try {
            if (!BeaconManager.getInstanceForApplication(this).checkAvailability()) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Bluetooth not enabled");
                builder.setMessage("Please enable bluetooth in settings and restart this application.");
                builder.setPositiveButton(android.R.string.ok, null);
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        finishAffinity();
                    }
                });
                builder.show();
            }
        }
        catch (RuntimeException e) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Bluetooth LE not available");
            builder.setMessage("Sorry, this device does not support Bluetooth LE.");
            builder.setPositiveButton(android.R.string.ok, null);
            builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

                @Override
                public void onDismiss(DialogInterface dialog) {
                    finishAffinity();
                }

            });
            builder.show();

        }

    }

    private void requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (this.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    if (this.checkSelfPermission(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {
                        if (!this.shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_BACKGROUND_LOCATION)) {
                            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                            builder.setTitle("This app needs background location access");
                            builder.setMessage("Please grant location access so this app can detect beacons in the background.");
                            builder.setPositiveButton(android.R.string.ok, null);
                            builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

                                @TargetApi(23)
                                @Override
                                public void onDismiss(DialogInterface dialog) {
                                    requestPermissions(new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION},
                                            PERMISSION_REQUEST_BACKGROUND_LOCATION);
                                }

                            });
                            builder.show();
                        }
                        else {
                            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                            builder.setTitle("Functionality limited");
                            builder.setMessage("Since background location access has not been granted, this app will not be able to discover beacons in the background.  Please go to Settings -> Applications -> Permissions and grant background location access to this app.");
                            builder.setPositiveButton(android.R.string.ok, null);
                            builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

                                @Override
                                public void onDismiss(DialogInterface dialog) {
                                }

                            });
                            builder.show();
                        }
                    }
                }
            } else {
                if (!this.shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                    Manifest.permission.ACCESS_BACKGROUND_LOCATION},
                            PERMISSION_REQUEST_FINE_LOCATION);
                }
                else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Functionality limited");
                    builder.setMessage("Since location access has not been granted, this app will not be able to discover beacons.  Please go to Settings -> Applications -> Permissions and grant location access to this app.");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

                        @Override
                        public void onDismiss(DialogInterface dialog) {
                        }

                    });
                    builder.show();
                }

            }
        }
    }

    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_FINE_LOCATION: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "fine location permission granted");
                } else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Functionality limited");
                    builder.setMessage("Since location access has not been granted, this app will not be able to discover beacons.");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

                        @Override
                        public void onDismiss(DialogInterface dialog) {
                        }

                    });
                    builder.show();
                }
                return;
            }
            case PERMISSION_REQUEST_BACKGROUND_LOCATION: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "background location permission granted");
                } else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Functionality limited");
                    builder.setMessage("Since background location access has not been granted, this app will not be able to discover beacons when in the background.");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

                        @Override
                        public void onDismiss(DialogInterface dialog) {
                        }

                    });
                    builder.show();
                }
                return;
            }
        }
    }


    private String notificationText = "";
    private void Display(String line) {
        notificationText += line+"\n";
        runOnUiThread(new Runnable() {
            public void run() {
                TextView textView = (TextView) HomeActivity.this
                        .findViewById(R.id.nonotification);
                textView.setText(notificationText);
            }
        });
    }
}
