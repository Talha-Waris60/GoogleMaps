package com.devdroid.maps;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.devdroid.maps.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.model.PolygonOptions;

import java.io.IOException;
import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapsActivity.this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        LatLng fsdLatLng = new LatLng(31.450365, 73.134964);
        // MarkerOptions markerOptions = new MarkerOptions().position(fsdLatLng).title("Faisalabad");
        mMap.addMarker(new MarkerOptions().position(fsdLatLng).title("Faisalabad"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(fsdLatLng));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(fsdLatLng, 16f));


        // Map Overlay as Circle
        mMap.addCircle(new CircleOptions()
                .center(fsdLatLng)
                .radius(1000)
                .fillColor(Color.GREEN)
                .strokeColor(Color.BLACK));

        // Polygon
        mMap.addPolygon(new PolygonOptions().add(new LatLng(31.450365, 73.134964),
                        new LatLng(31.450365, 74.134964),
                        new LatLng(32.450365, 75.134964),
                        new LatLng(32.450365, 76.134964),
                        new LatLng(31.450365, 77.134964))
                .fillColor(Color.GRAY)
                .strokeColor(Color.RED));

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull LatLng latLng) {
                mMap.addMarker(new MarkerOptions().position(latLng).title(""));
                // GeoCoder On map
                Geocoder geocoder = new Geocoder(MapsActivity.this);
                try {
                    ArrayList<Address> arrAdr = (ArrayList<Address>) geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                    Log.d("Addr", arrAdr.get(0).getAddressLine(0));
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}