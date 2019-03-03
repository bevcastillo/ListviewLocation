package com.example.listviewlocation;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class Add extends AppCompatActivity implements LocationListener {

    TextView lblLat, lblLng;
    EditText txtname;
    ImageButton btnOK;
    ///
    LocationManager locman;
    String provider;
    Criteria criteria;
    Location location;
    double lat, lng;
    private String slat, slng;

    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        //
        db = new DatabaseHelper(this);
        ////
        lblLat = (TextView)findViewById(R.id.addtextLatitude);
        lblLng = (TextView)findViewById(R.id.addtextLongitude);

        txtname = (EditText)findViewById(R.id.txtloc);

       btnOK = (ImageButton)findViewById(R.id.imageButton5);

        locman = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        criteria = new Criteria();

        //use for choosing the recommended location provider (gps, passive)
        criteria.setAccuracy(Criteria.ACCURACY_MEDIUM);
        criteria.setPowerRequirement(Criteria.ACCURACY_MEDIUM);
        provider = locman.getBestProvider(criteria, false);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Log.d("DEBUG:: PERMISSION", "LOCATION NOT GRANTED");
            return;
        }

        locman.requestLocationUpdates(provider, 0, 0, (LocationListener) this);
        location = locman.getLastKnownLocation(provider);
        ///

        if(location!=null){
            lat = location.getLatitude();
            lng = location.getLongitude();

            slat = String.format("%.4f",lat);
            slng = String.format("%.4f",lng);

            lblLat.setText(slat);
            lblLng.setText(slng);
        }
        //

//        btnOK.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Toast.makeText(Add.this, "Add new item", Toast.LENGTH_SHORT).show();
//                Snackbar.make(v, "Will add new location", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = txtname.getText().toString();


                if (!name.equals("") && location != null) {
                    //saving data
                    lat = location.getLatitude();
                    lng = location.getLongitude();

                    slat = String.format("%.4f", lat);
                    slng = String.format("%.4f", lng);

                    lblLat.setText(slat);
                    lblLng.setText(slng);

                    String latitude = lblLat.getText().toString();
                    String longitude = lblLng.getText().toString();

                    long result = db.addLocation(name, latitude, longitude);

                    Toast.makeText(Add.this, latitude+ " : " +longitude, Toast.LENGTH_SHORT).show();
                    Toast.makeText(Add.this, "RESULT = "+result, Toast.LENGTH_SHORT).show();
                    Log.d("DEBUG :: RESULT VAL", "Result: "+result);


                    if(result > 0){
                            Intent intent = new Intent(Add.this,MainActivity.class);
                            startActivity(intent);
                            Toast.makeText(getApplicationContext(),"New Location Added!",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Location Field can not be empty!",Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }

    @Override
    public void onLocationChanged(Location location) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }


    /*
    private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            String locationLat;
            String locationLng;
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationLat = bundle.getString("coordLat");
                    locationLng = bundle.getString("coordLng");
                    break;
                default:
                    locationLat = null;
                    locationLng = null;
            }
            lblLat.setText(locationLat);
            lblLng.setText(locationLng);
        }
    }*/

}
