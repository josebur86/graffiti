package com.tinydino.graffiti;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient mGoogleApiClient;
    private String DEBUG_TAG = "DEBUGTAG";
    private Toast _toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        _toast = Toast.makeText(this, "", Toast.LENGTH_LONG);

        MapFragment mapFragment = (MapFragment)getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        mGoogleApiClient.connect();

        PendingResult<PlaceLikelihoodBuffer> result = Places.PlaceDetectionApi.getCurrentPlace(mGoogleApiClient, null);
        result.setResultCallback(new ResultCallback<PlaceLikelihoodBuffer>() {
            @Override
            public void onResult(PlaceLikelihoodBuffer likelyPlaces) {
                Log.d(DEBUG_TAG, "Entering result callback" + likelyPlaces.getCount());
                //_toast.setText(String.format("Number of likely places: %s", likelyPlaces.getCount()));
                //_toast.show();

                ArrayList<PlaceLikelihood> likelyPlacesList = new ArrayList<PlaceLikelihood>();
                for (PlaceLikelihood placeLikelihood : likelyPlaces) {
                    Log.d(DEBUG_TAG, String.format("Place '%s' has likelihood: %g",
                            placeLikelihood.getPlace().getName(),
                            placeLikelihood.getLikelihood()));

                    likelyPlacesList.add(placeLikelihood);
                }

                _toast.setText(String.format("%s", likelyPlacesList.get(0).getPlace().getName() + "..." + likelyPlacesList.get(0).getLikelihood()));
                _toast.show();
                likelyPlaces.release();
            }
        });
        Log.d(DEBUG_TAG, "End of onCreate()");
    }

    @Override
    public void onMapReady(GoogleMap map) {
        map.addMarker(new MarkerOptions()
            .position(new LatLng(0, 0))
            .title("Marker"));
        map.setMyLocationEnabled(true);
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        // Connected to Google Play services!
        // The good stuff goes here.
        Log.d(DEBUG_TAG, "End of onConnected()");
    }

    @Override
    public void onConnectionSuspended(int cause) {
        // The connection has been interrupted.
        // Disable any UI components that depend on Google APIs
        // until onConnected() is called.
        Log.d(DEBUG_TAG, "End of onConnectionSuspended()");
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        // This callback is important for handling errors that
        // may occur while attempting to connect with Google.
        //
        // More about this in the next section.
        //...
        Log.d(DEBUG_TAG, "End of onConnectionFailed()");
    }
}
