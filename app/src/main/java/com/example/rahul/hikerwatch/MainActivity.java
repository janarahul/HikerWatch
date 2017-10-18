package com.example.rahul.hikerwatch;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    LocationManager locationManager;
    LocationListener locationListener;
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,10,10,locationListener);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView editText = (TextView)findViewById(R.id.editText);
        final TextView editText2 = (TextView)findViewById(R.id.editText2);
        final TextView editText3 = (TextView)findViewById(R.id.editText3);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.ACCESS_FINE_LOCATION},1);
        }
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                try {
                    List<Address> addressList = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                    editText.setText("Latitude: "+location.getLatitude());
                    editText2.setText("Longitude: "+location.getLongitude());
                    editText3.setText("PostalCode: "+addressList.get(0).getPostalCode());
                    Log.i("loc: ",location.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,10,10,locationListener);
    }
}
