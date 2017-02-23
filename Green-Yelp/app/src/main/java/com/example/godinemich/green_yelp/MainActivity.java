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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.View;

import android.location.*;
import android.util.Log;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;

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
        loadRestaurants();
        mainMenu();
    }


    //list of restaurants
    List<Restaurant> restaurants=new ArrayList<Restaurant>();
    List<Restaurant> sortedRestaurants = new ArrayList<Restaurant>();

    /**load dummy restaurant data into field "restaurants"*/
    public void loadRestaurants(){
        //load dummy data
        restaurants.add(new Restaurant("Cafe Delicious","ABC St, Wellington","Vegetarian Restaurant",-41.291003,174.776764));
        restaurants.add(new Restaurant("Cafe Appetit","DEF St, Wellington","Gluten-Free Restaurant",-41.284111,174.776051));
        restaurants.add(new Restaurant("Cafe Yum","GHI St, Wellington","All Organic Restaurant",-41.297101,174.782653));
    }

    /**Display main menu screen.*/
    public void mainMenu(){
        setContentView(R.layout.activity_main);     //main menu screen

        //connect main menu screen buttons
        Button btn1=(Button)findViewById(R.id.button);
        btn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                onSearch(); //stores sorted list of nearby restaurants in global field
                showResults();//shows sorted list of restaurants made by onsearch()
            }
        });
    }

    /**Display results screen*/
    public void showResults(){
        setContentView(R.layout.restresults);     //results screen
        LinearLayout layout = (LinearLayout)findViewById(R.id.linLayout);
        layout.removeAllViews();

        for(int i = 0; i < sortedRestaurants.size(); i++){
            Restaurant r = sortedRestaurants.get(i);
            LinearLayout innerLayout = new LinearLayout(this);
            innerLayout.setOrientation(LinearLayout.HORIZONTAL);

//            ImageView pic = new ImageView(this);
//            pic.setImageBitmap();
            TextView txt = new TextView(this);
            txt.setText(r.getName() + "\n" + r.getDescription() + "\n" + r.getAddress());
            innerLayout.addView(txt);
            layout.addView(innerLayout);
       }
//
//        //connect UI elements
//        TextView txt1=(TextView)findViewById(R.id.textView1);
//        TextView txt2=(TextView)findViewById(R.id.textView2);
//        TextView txt3=(TextView)findViewById(R.id.textView3);
//
//        txt1.setText("Result1");
//        txt2.setText("Result2");
//        txt3.setText("Result3");

    }

    protected void onSearch(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
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
        try{
            locationManager.requestLocationUpdates(GPS_PROVIDER, 0, 0, locationListener);
            TextView txt = (TextView) findViewById(R.id.txt);
            userLocation = locationManager.getLastKnownLocation(GPS_PROVIDER);
            searchRestaurants();
        }
        catch(SecurityException ex){
            Log.i("Test", ex.getMessage());
            Log.i("Test","Permission failed");
            TextView txt = (TextView)findViewById(R.id.txt);
            txt.setText("Failed");
        }
    }

    private void searchRestaurants(){
        Log.i("user lat",String.valueOf(userLocation.getLatitude()));
        Log.i("user long",String.valueOf(userLocation.getLongitude()));
        sortedRestaurants = new ArrayList<Restaurant>(restaurants);
        for (Restaurant r: sortedRestaurants){
            r.setDistance(Math.sqrt(Math.pow(r.getLatitude() - userLocation.getLatitude(),2) +
                    Math.pow(r.getLongitude() - userLocation.getLongitude(),2)));
        }

        Collections.sort(sortedRestaurants, new Comparator<Restaurant>(){
            @Override
            public int compare(Restaurant r1, Restaurant r2){
                return (int)Math.signum(r1.getDistance() - r2.getDistance());
            }
        });

        for(int i = 0; i < sortedRestaurants.size(); i++){
            Log.i("Restaurant",sortedRestaurants.get(i).getName() + ", " + sortedRestaurants.get(i).getDistance());
            Log.i("R",sortedRestaurants.get(i).getLatitude() + ", " + sortedRestaurants.get(i).getLongitude());
        }
    }
}
