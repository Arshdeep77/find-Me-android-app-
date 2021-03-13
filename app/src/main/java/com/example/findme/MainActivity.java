package com.example.findme;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements LocationListener {
    LocationManager manager;
    TextView locationDetail;
    String myAdr;
    TextView tvLong,tvlant,tvaccur,tvAd;
    Button pressButton;
    ProgressBar pb;
    Location loc;
    speak mySpeaker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
pb=findViewById(R.id.progressBar);
        tvLong=findViewById(R.id.tvLongs);
        tvlant=findViewById(R.id.tvLats);
        tvaccur=findViewById(R.id.tvAccur);
        tvAd=findViewById(R.id.tvadress);
        pressButton=findViewById(R.id.but);

       mySpeaker=new speak(getApplicationContext());
        permissions();
        getLocation();



    }

    @SuppressLint("MissingPermission")
    private void getLocation() {

        try {
            manager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);

            manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 10, MainActivity.this);
     } catch (Exception e) {
         e.printStackTrace();
     }


    }

    private void permissions() {




        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)!=
        PackageManager.PERMISSION_GRANTED){


            ActivityCompat.requestPermissions(MainActivity.this,new String[]{

                    Manifest.permission.ACCESS_FINE_LOCATION

            },100);
        }


    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
double lat=location.getLatitude();
double longs=location.getAltitude();
double acur=location.getAccuracy();
loc=location;
String adress=(ReverseGeocode(location));
setInfo(tvlant,lat,"lattitude");
setInfo(tvLong,longs,"Longitude");
setInfo(tvaccur,acur,"Accuraacy");
setInfo(tvAd,adress,"Address");
myAdr=adress;
pressButton.setVisibility(View.VISIBLE);
pb.setVisibility(View.GONE);





    }

    private String ReverseGeocode(Location location) {

        Geocoder geocoder=new Geocoder(getApplicationContext(), Locale.getDefault());
        String adress="";
        try {
            List<Address> AdressList=geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);

            if(AdressList!=null && AdressList.size()>0){

Address myAddress =AdressList.get(0);
if(myAddress.getAddressLine(0)!=null){
    adress+=myAddress.getAddressLine(0).toString();

}else{
    if(myAddress.getAdminArea()!=null){
    adress=myAddress.getAdminArea();
    }else{

        adress=myAddress.getLocality();

    }

}



if(myAddress.getPhone()!=null){
    adress+="\n"+myAddress.getPhone();
}
return adress;
            }

        } catch (IOException e) {

return "not found";


        }

        return "not found";}

    private void setInfo(TextView tv,Object lat, String s) {
        tv.setVisibility(View.VISIBLE);
        tv.setText(s+" : "+lat);



    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public void move(View view) {

        Intent intent=new Intent(getApplicationContext(),mapToFind.class);

        intent.putExtra("longitude",loc.getLongitude());
        intent.putExtra("latitude",loc.getLatitude());
        intent.putExtra("Address",myAdr);


        startActivity(intent);


    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    public void speakText(View view) {
        String arr[]={"Sir you are at ","Hey you are at ","We are at"};
       Random ran=new Random();
       int i=ran.nextInt(3);
       if(myAdr=="" || myAdr==null){
           mySpeaker.speakit("we are trying to find your location");
       }else{
           String speakText=arr[i]+myAdr;
           mySpeaker.speakit(speakText);
       }

    }

    @Override
    public void onBackPressed() {
        mySpeaker.stopit();
        super.onBackPressed();
    }
}
