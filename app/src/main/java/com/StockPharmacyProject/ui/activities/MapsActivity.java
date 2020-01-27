package com.StockPharmacyProject.ui.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.ZoomControls;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.StockPharmacyProject.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.StockPharmacyProject.helper.HelperMethod.getAddress;

public class MapsActivity extends FragmentActivity
        implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    @BindView(R.id.key_word_search)
    TextInputEditText keyWordSearch;
    @BindView(R.id.searchMedication)
    ImageButton searchMedication;
    @BindView(R.id.BtnMyLocation)
    Button BtnMyLocation;
    //    @BindView(R.id.location)
//    ImageButton current_location;
    @BindView(R.id.zoom)
    ZoomControls zoom;
    private GoogleMap mMap;
    private final static int MY_PERMISSION_FINE_LOCATION = 101;
    public static double myLatitude;
    public static double myLongitude;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    protected static final String TAG = "MapsActvity";
    private double pharmacy_lat, pharmacy_lng;
    private String sLocation;
    public static String MyLocation;
    private SupportMapFragment mapFragment;
    private View mapView;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        ButterKnife.bind(this);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapView = mapFragment.getView();
        mapFragment.getMapAsync(this);

        pharmacy_lat = getIntent().getDoubleExtra("lat", 30.7865);
        pharmacy_lng = getIntent().getDoubleExtra("lng", 31.0004);

        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        locationRequest = new LocationRequest();
        locationRequest.setInterval(15 * 1000);
        locationRequest.setFastestInterval(5 * 1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        //set to balanced power accuracy on real device

        zoom.setOnZoomOutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.animateCamera(CameraUpdateFactory.zoomOut());

            }
        });
        zoom.setOnZoomInClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.animateCamera(CameraUpdateFactory.zoomIn());

            }
        });

//        current_location.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mMap.setMyLocationEnabled(true);
////                LatLng myLocation = new LatLng(myLatitude, myLongitude);
////                MyLocation = getAddress(MapsActivity.this, myLatitude, myLongitude);
////                mMap.addMarker(new MarkerOptions().position(myLocation).title(MyLocation));
//            }
//        });
        BtnMyLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();


            }
        });


        searchMedication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String search = keyWordSearch.getText().toString();
                if (!search.isEmpty() && !search.equals("")) {
                    List<Address> addressList = new ArrayList<>();
                    Geocoder geocoder = new Geocoder(MapsActivity.this);
                    try {
                        addressList = geocoder.getFromLocationName(search, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (addressList.size() != 0) {
                        Address address = addressList.get(0);
                        LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                        String searchAddress = getAddress(MapsActivity.this, address.getLatitude(), address.getLongitude());
                        mMap.addMarker(new MarkerOptions().position(latLng).title(searchAddress));
                        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                    } else {
                        Toast.makeText(MapsActivity.this, "Please Add Valid Word", Toast.LENGTH_SHORT).show();

                    }
                }
                else{
                    Toast.makeText(MapsActivity.this, "Please Add Word", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);


        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(pharmacy_lat, pharmacy_lng);
        sLocation = getAddress(this, pharmacy_lat, pharmacy_lng);
        mMap.addMarker(new MarkerOptions().position(sydney).title(sLocation));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.setMinZoomPreference(10);
        mMap.getMaxZoomLevel();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            mMap.setMyLocationEnabled(true);
            // Get the button view
            View locationButton = ((View) mapView.findViewById(Integer.parseInt("1"))
                    .getParent()).findViewById(Integer.parseInt("2"));
            // and next place it, on bottom right (as Google Maps app)
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)
                    locationButton.getLayoutParams();
            // position on right bottom
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            layoutParams.setMargins(20, 0, 0, 1200);

        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSION_FINE_LOCATION);
            }
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSION_FINE_LOCATION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission
                            (this, Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        mMap.setMyLocationEnabled(true);
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "This app requires location permissions to be granted", Toast.LENGTH_LONG).show();
                    finish();
                }
                break;
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        requestLocationUpdates();
    }

    private void requestLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
        }

    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Connection Suspended");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i(TAG, "Connection Failed: ConnectionResult.getErrorCode() = " + connectionResult.getErrorCode());
    }

    @Override
    public void onLocationChanged(Location location) {
        myLatitude = location.getLatitude();
        myLongitude = location.getLongitude();
    }

    @Override
    protected void onStart() {
        super.onStart();
        googleApiClient.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (googleApiClient.isConnected()) {
            requestLocationUpdates();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        googleApiClient.disconnect();
    }
}


//
//import android.Manifest;
//import android.content.Context;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.location.Address;
//import android.location.Geocoder;
//import android.location.Location;
//import android.os.Build;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageButton;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.core.app.ActivityCompat;
//import androidx.fragment.app.FragmentActivity;
//
//import com.StockPharmacyProject.R;
//import com.google.android.gms.common.ConnectionResult;
//import com.google.android.gms.common.api.GoogleApiClient;
//import com.google.android.gms.location.LocationListener;
//import com.google.android.gms.location.LocationRequest;
//import com.google.android.gms.location.LocationServices;
//import com.google.android.gms.maps.CameraUpdateFactory;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.OnMapReadyCallback;
//import com.google.android.gms.maps.SupportMapFragment;
//import com.google.android.gms.maps.model.BitmapDescriptorFactory;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.MarkerOptions;
//
//import java.io.IOException;
//import java.util.List;
//import java.util.Locale;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import butterknife.OnClick;
//
//import static com.StockPharmacyProject.helper.HelperMethod.bitmapDescriptorFromVector;
//import static com.StockPharmacyProject.helper.HelperMethod.getAddress;
//
//public class MapsActivity extends FragmentActivity implements
//        OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
//        GoogleApiClient.OnConnectionFailedListener, LocationListener {
//
//    @BindView(R.id.BtnMyLocation)
//    Button BtnMyLocation;
//    @BindView(R.id.location)
//    ImageButton location;
//    private GoogleMap mMap;
//    private final static int MY_PERMISSION_FINE_LOCATION = 101;
//    public double lat, lng;
//    public String address;
//    private GoogleApiClient googleApiClient;
//    private LocationRequest locationRequest;
//    protected static final String TAG = "MapsActvity";
//    SupportMapFragment mapFragment;
//    private Location mLocation;
//    double pharmacy_lat, pharmacy_lng;
//    private String Ph_Address;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_maps);
//        ButterKnife.bind(this);
//
//        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//        mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
//
//        googleApiClient = new GoogleApiClient.Builder(this)
//                .addApi(LocationServices.API)
//                .addConnectionCallbacks(this)
//                .addOnConnectionFailedListener(this)
//                .build();
//
//        locationRequest = new LocationRequest();
//        locationRequest.setInterval(15 * 1000);
//        locationRequest.setFastestInterval(5 * 1000);
//        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//        pharmacy_lat = getIntent().getDoubleExtra("lat", 30.7865);
//        pharmacy_lng = getIntent().getDoubleExtra("lng", 31.0004);
//
//
//    }
//
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        mMap = googleMap;
//        drawMap(pharmacy_lat,pharmacy_lng);
//
//    }
//
//    private void drawMap(double pharmacyLat, double pharmacyLng) {
//        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(pharmacyLat, pharmacyLng);
//        Ph_Address = getAddress(this, pharmacyLat, pharmacyLng);
//        mMap.addMarker(new MarkerOptions().position(sydney).title(Ph_Address));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
//        mMap.setMinZoomPreference(10);
//        mMap.getMaxZoomLevel();
//
//        if (ActivityCompat.checkSelfPermission(this,
//                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//            mMap.setMyLocationEnabled(true);
//        } else {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSION_FINE_LOCATION);
//            }
//        }
//
//
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        switch (requestCode) {
//            case MY_PERMISSION_FINE_LOCATION:
//                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//                        mMap.setMyLocationEnabled(true);
//                    }
//
//                } else {
//                    Toast.makeText(getApplicationContext(), "This app requires location permissions to be granted", Toast.LENGTH_LONG).show();
//                    finish();
//                }
//                break;
//        }
//    }
//
//    @Override
//    public void onConnected(@Nullable Bundle bundle) {
//        requestLocationUpdates();
//    }
//
//    private void requestLocationUpdates() {
//        if (ActivityCompat.checkSelfPermission(this,
//                Manifest.permission.ACCESS_FINE_LOCATION)
//                == PackageManager.PERMISSION_GRANTED) {
//            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
//
////             mLocation = LocationServices.FusedLocationApi.getLastLocation
////                    (googleApiClient);
////            if (mLocation != null) {
////                myLatitude = mLocation.getLatitude();
////                myLongitude = mLocation.getLatitude();
////                mapFragment.getMapAsync(this);
////            }
////            if (googleApiClient.isConnected()) {
////                startLocationUpdates();
////            }
//
//        }
//
//
//    }
//
//    @Override
//    public void onConnectionSuspended(int i) {
//        Log.i(TAG, "Connection Suspended");
//    }
//
//    @Override
//    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//        Log.i(TAG, "Connection Failed: ConnectionResult.getErrorCode() = " + connectionResult.getErrorCode());
//    }
//
//    @Override
//    public void onLocationChanged(Location location) {
//        if ((location != null)) {
//            lat = location.getLatitude();
//            lng = location.getLongitude();
//
//
//        }
//
//
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        googleApiClient.connect();
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
////        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
////                == PackageManager.PERMISSION_GRANTED)
////            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
////        if (googleApiClient.isConnected()) {
////            requestLocationUpdates();
////        }
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        googleApiClient.disconnect();
//    }
//
//    @OnClick({R.id.BtnMyLocation, R.id.location})
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.BtnMyLocation:
//                onBackPressed();
//                break;
//            case R.id.location:
//                drawMap(lat,lng);
//                break;
//        }
//    }
//
//
//}
