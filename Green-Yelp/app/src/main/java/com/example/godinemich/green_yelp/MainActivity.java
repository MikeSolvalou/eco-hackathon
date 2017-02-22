package com.example.godinemich.green_yelp;

import android.Manifest;
import android.content.Context;
import android.location.LocationManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.pm.PackageManager;

import android.widget.Button;
import android.widget.TextView;
import android.view.View;

import android.location.*;
import android.util.Log;


public class MainActivity extends AppCompatActivity {

    LocationManager locationManager;
    Location userLocation;

    LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            // Called when a new location is found by the network location provider.
            userLocation = location;
            Log.i("Latitude",userLocation.getLatitude() + "");
            Log.i("Longitude",userLocation.getLongitude() + "");
            TextView txt = (TextView)findViewById(R.id.txt);
            txt.setText(userLocation.getLatitude() + ", " + userLocation.getLongitude());
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {}

        public void onProviderEnabled(String provider) {}

        public void onProviderDisabled(String provider) {}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Log.i("Test","test");
        //test comment

        mainMenu();
    }

    protected void onSearch(){
        try{
            if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED){
            locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            TextView txt = (TextView) findViewById(R.id.txt);
            txt.setText("Worked");}
            else Log.i("Test","Not working");
            //run search algorithm to display restaurants
        }
        catch(SecurityException ex){
            //do something
            Log.i("Test", ex.getMessage());
            Log.i("Test","Permission failed");
            TextView txt = (TextView)findViewById(R.id.txt);
            txt.setText("Failed");
        }

    }


    public void mainMenu(){
        setContentView(R.layout.activity_main);     //splash screen

        //connect splash screen buttons
        Button btn1=(Button)findViewById(R.id.button);
        btn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                onSearch();
            }
        });

    }

}
