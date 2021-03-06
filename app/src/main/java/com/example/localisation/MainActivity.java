package com.example.localisation;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    Button btnShowLocation,btnShowLocation2,btnShowLocation3, btnShowLocation4;
    TextView outputText;
    TextView providersList;
    EditText delayInput;
    int delayTime;

    private static final String[] INITIAL_PERMS={
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    // GPSTracker class
    GPSTracker gps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(INITIAL_PERMS,1337);
        }

        btnShowLocation = (Button) findViewById(R.id.btnShowLocation);
        btnShowLocation2 = (Button) findViewById(R.id.btnShowLocation2);
        btnShowLocation3 = (Button) findViewById(R.id.btnShowLocation3);
        btnShowLocation4 = (Button) findViewById(R.id.btnShowLocation4);
        outputText = (TextView) findViewById(R.id.textView);
        providersList = (TextView) findViewById(R.id.textView3);
        delayInput = (EditText) findViewById(R.id.editTextNumber);

        LocationManager locationManager = (LocationManager) MainActivity.this
                .getSystemService(LOCATION_SERVICE);
        providersList.setText(locationManager.getAllProviders().toString());

        // Show location button click event
        btnShowLocation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Runnable r = new Runnable() {
                    @Override
                    public void run() {
                        // Create class object
                        gps = new GPSTracker(MainActivity.this, GPSTracker.PROVIDER_TYPE.GPS);

                        // Check if GPS enabled
                        if(gps.canGetLocation()) {

                            double latitude = gps.getLatitude();
                            double longitude = gps.getLongitude();

                            // \n is for new line
                            String isMock = "Mocked: " + isMockSettingsON(MainActivity.this);
                            outputText.setText( "Your gps Location is - \nLat: " + latitude + "\nLong: " + longitude+ "\n" + isMock);
                        } else {
                            // Can't get location.
                            // GPS or network is not enabled.
                            // Ask user to enable GPS/network in settings.
                            gps.showSettingsAlert();
                        }
                    }
                };

                setDelay(r);

            }
        });
        btnShowLocation2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Runnable r = new Runnable() {
                    @Override
                    public void run() {
                        gps = new GPSTracker(MainActivity.this, GPSTracker.PROVIDER_TYPE.NETWORK);

                        // Check if GPS enabled
                        if(gps.canGetLocation()) {

                            double latitude = gps.getLatitude();
                            double longitude = gps.getLongitude();

                            // \n is for new line
                            String isMock = "Mocked: " + isMockSettingsON(MainActivity.this);
                            outputText.setText("Your network Location is - \nLat: " + latitude + "\nLong: " + longitude + "\n" + isMock);
                        } else {
                            // Can't get location.
                            // GPS or network is not enabled.
                            // Ask user to enable GPS/network in settings.
                            gps.showSettingsAlert();
                        }
                    }
                };
                setDelay(r);
                // Create class object

            }
        });
        btnShowLocation3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Runnable r = new Runnable() {
                    @Override
                    public void run() {
                        gps = new GPSTracker(MainActivity.this, GPSTracker.PROVIDER_TYPE.FUSED);

                        // Check if GPS enabled
                        if(gps.canGetLocation()) {

                            double latitude = gps.getLatitude();
                            double longitude = gps.getLongitude();

                            // \n is for new line
                            String isMock = "Mocked: " + isMockSettingsON(MainActivity.this);
                            outputText.setText("Your fused Location is - \nLat: " + latitude + "\nLong: " + longitude + "\n" + isMock);
                        } else {
                            // Can't get location.
                            // GPS or network is not enabled.
                            // Ask user to enable GPS/network in settings.
                            gps.showSettingsAlert();
                        }
                    }
                };
                setDelay(r);
                // Create class object

            }
        });

        btnShowLocation4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Runnable r = new Runnable() {
                    @Override
                    public void run() {
                        gps = new GPSTracker(MainActivity.this, GPSTracker.PROVIDER_TYPE.PASSIVE);

                        // Check if GPS enabled
                        if(gps.canGetLocation()) {

                            double latitude = gps.getLatitude();
                            double longitude = gps.getLongitude();

                            // \n is for new line
                            String isMock = "Mocked: " + isMockSettingsON(MainActivity.this);
                            outputText.setText("Your passive Location is - \nLat: " + latitude + "\nLong: " + longitude + "\n" + isMock);
                        } else {
                            // Can't get location.
                            // GPS or network is not enabled.
                            // Ask user to enable GPS/network in settings.
                            gps.showSettingsAlert();
                        }
                    }
                };
                setDelay(r);
                // Create class object

            }
        });
    }

    private void setDelay(Runnable r){
        try {
            delayTime = Integer.parseInt(delayInput.getText().toString());
        }catch(Exception ex){
            delayTime = 0;
        }
        outputText.setText("Waiting for " + delayTime + " seconds");

        Handler handler = new Handler();
        handler.postDelayed(r, delayTime *1000);
    }

    public static boolean isMockSettingsON(Context context) {
        // returns true if mock location enabled, false if not enabled.
        if (Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ALLOW_MOCK_LOCATION).equals("0"))
            return false;
        else
            return true;
    }
}