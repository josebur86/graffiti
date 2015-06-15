package com.tinydino.graffiti.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tinydino.graffiti.R;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.overridePendingTransition(R.anim.slide_in_right, android.R.anim.slide_out_right);

        setContentView(R.layout.activity_map);

        MapFragment mapFragment = (MapFragment)getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        map.addMarker(new MarkerOptions()
            .position(new LatLng(0, 0))
            .title("Marker"));
        map.setMyLocationEnabled(true);
    }
}
