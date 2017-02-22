package com.example.godinemich.green_yelp;

import android.content.Context;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {}

        public void onProviderEnabled(String provider) {}

        public void onProviderDisabled(String provider) {}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i("Test","test");

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

    /***/
    protected void onSearch(){
        try{
            locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
            //run search algorithm to display restaurants
        }
        catch(SecurityException ex){
            //do something
            Log.i("Test","Permission failed");
        }

    }
}
