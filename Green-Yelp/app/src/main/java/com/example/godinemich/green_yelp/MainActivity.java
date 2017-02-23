package com.example.godinemich.green_yelp;

import android.Manifest;
import android.content.Context;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.pm.PackageManager;

import android.widget.Button;
import android.widget.TextView;
import android.view.View;

import android.location.*;
import android.util.Log;

import static android.location.LocationManager.GPS_PROVIDER;


public class MainActivity extends AppCompatActivity {

    LocationManager locationManager;
    Location userLocation;
    double testLat = -41.29473679909909;
    double testLong = 174.77428436529294;
    static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;

    LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
//            Log.i("Test","location changed");
//            // Called when a new location is found by the network location provider.
//            userLocation = location;
//            Log.i("Latitude",userLocation.getLatitude() + "");
//            Log.i("Longitude",userLocation.getLongitude() + "");
//            TextView txt = (TextView)findViewById(R.id.txt);
//            txt.setText(userLocation.getLatitude() + ", " + userLocation.getLongitude());
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {}

        public void onProviderEnabled(String provider) {}

        public void onProviderDisabled(String provider) {}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainMenu();
    }

    /**Display main menu screen.*/
    public void mainMenu(){
        setContentView(R.layout.activity_main);     //main menu screen

        //connect main menu screen buttons
        Button btn1=(Button)findViewById(R.id.button);
        btn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                onSearch();
            }
        });

    }

    protected void onSearch(){
        Log.i("Search","1");
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            Log.i("Permission", "1");
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            }
        else{
            getLocation();
        }
}

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                Log.i("Permission", "5");
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLocation();
                } else {
                    Log.i("Test", "Permission denied");
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private void getLocation(){
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        Log.i("is enabled",String.valueOf(locationManager.isProviderEnabled(GPS_PROVIDER)));
        try{
            locationManager.requestLocationUpdates(GPS_PROVIDER, 0, 0, locationListener);
            TextView txt = (TextView) findViewById(R.id.txt);
            userLocation = locationManager.getLastKnownLocation(GPS_PROVIDER);
            Log.i("Latitude",userLocation.getLatitude() + "");
            Log.i("Longitude",userLocation.getLongitude() + "");
            txt.setText("Lat: " + userLocation.getLatitude() + ", Long: " + userLocation.getLongitude());
            //run search method
        }
        catch(SecurityException ex){
            Log.i("Test", ex.getMessage());
            Log.i("Test","Permission failed");
            TextView txt = (TextView)findViewById(R.id.txt);
            txt.setText("Failed");
        }
    }
}
