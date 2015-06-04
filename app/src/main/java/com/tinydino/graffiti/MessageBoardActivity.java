package com.tinydino.graffiti;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.PlaceLikelihoodBuffer;
import com.google.android.gms.location.places.Places;
import com.tinydino.graffiti.presenter.MessageBoardPresenter;

import java.util.List;

public class MessageBoardActivity extends ListActivity implements MessageBoardView, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private ListView _mainMessageList;
    private TextView _messageEdit;

    private MessageBoardPresenter _presenter;
    private ChatMessageAdapter _chatAdapter;

    private static int kRequestImageCapture = 1;

    private String currentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_board);

        _messageEdit = (TextView) findViewById(R.id.editMessage);
        _messageEdit.setOnKeyListener(new ReturnKeyListener());

        ImageButton pictureButton = (ImageButton) findViewById(R.id.sendPicture);
        pictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPicture();
            }
        });

        _mainMessageList = (ListView) findViewById(android.R.id.list);

        String username = getIntent().getStringExtra("userName");
        currentLocation = getMostProbableCurrentLocation(); //TODO_DR: This is garbage.
        _presenter = new MessageBoardPresenter(this, username);
        _presenter.create();
    }

    private void onSend() {
        String message = _messageEdit.getText().toString().trim();
        _presenter.sendMessage(message);
        _messageEdit.setText("");
    }

    private void onPicture() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, kRequestImageCapture);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == kRequestImageCapture && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            _presenter.sendPicture(imageBitmap);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        _presenter.destroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setMessages(List<ChatMessage> messages) {
        _chatAdapter = new ChatMessageAdapter(this, R.layout.list_item_style, messages);
        _mainMessageList.setAdapter(_chatAdapter);
    }

    @Override
    public void onAddMessage() {
        _chatAdapter.notifyDataSetChanged();
    }

    @Override
    public void playNotificationSound() {
        try {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public MessageListener getMessageListener() {
        return new MessageListener(this, _presenter);
    }

    private class ReturnKeyListener implements View.OnKeyListener {

        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                onSend();
                return true;
            }
            return false;
        }
    }

    public void onClickUser(View view) {
        Intent mapIntent = new Intent();
        mapIntent.setClass(this, MapActivity.class);

        startActivity(mapIntent);
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        // Connected to Google Play services!
        // The good stuff goes here.
    }

    @Override
    public void onConnectionSuspended(int cause) {
        // The connection has been interrupted.
        // Disable any UI components that depend on Google APIs
        // until onConnected() is called.
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        // This callback is important for handling errors that
        // may occur while attempting to connect with Google.
    }

    private String getMostProbableCurrentLocation()
    {
        GoogleApiClient mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        mGoogleApiClient.connect();

        final String[] mostLikelyCurrentLocation = new String[1];

        PendingResult<PlaceLikelihoodBuffer> result = Places.PlaceDetectionApi.getCurrentPlace(mGoogleApiClient, null);
        result.setResultCallback(new ResultCallback<PlaceLikelihoodBuffer>() {

            @Override
            public void onResult(PlaceLikelihoodBuffer likelyPlaces) {
                mostLikelyCurrentLocation[0] = String.format("%s", likelyPlaces.get(0).getPlace().getName());
                /*ArrayList<PlaceLikelihood> likelyPlacesList = new ArrayList<PlaceLikelihood>();
                for (PlaceLikelihood placeLikelihood : likelyPlaces) {
                    Log.d("PLACE_API_RESULT", String.format("Place '%s' has likelihood: %g",
                            placeLikelihood.getPlace().getName(),
                            placeLikelihood.getLikelihood()));

                    likelyPlacesList.add(placeLikelihood);
                }*/

                likelyPlaces.release();
                //mostLikelyCurrentLocation[0] = String.format("%s", likelyPlacesList.get(0).getPlace().getName() + "..." + likelyPlacesList.get(0).getLikelihood());

                _presenter.setLocation(mostLikelyCurrentLocation[0]);
            }
        });

        return mostLikelyCurrentLocation[0];
    }
}

