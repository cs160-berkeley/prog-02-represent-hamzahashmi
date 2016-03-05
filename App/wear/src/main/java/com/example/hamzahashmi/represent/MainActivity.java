package com.example.hamzahashmi.represent;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Wearable;

public class MainActivity extends Activity implements MessageApi.MessageListener, GoogleApiClient.ConnectionCallbacks {

    private TextView mTextView;
    private static final String START_ACTIVITY = "/start_activity";
    private static final String WEAR_MESSAGE_PATH = "/message";
    private static final String LEANING_PATH = "/leaning";
    private static final String TAG = "MAIN-ACTIVITY";


    private GoogleApiClient mApiClient;
    ViewPager viewPager;
    CustomSwipeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showNotifications();
        initGoogleApiClient();

    }

    public void showNotifications(){

    int notificationId = 001;
        Intent viewIntent = new Intent(this, MainActivity.class);

        PendingIntent viewPendingIntent = PendingIntent.getActivity(this, 0, viewIntent, 0);

    }

    @Override
    public void onConnected(Bundle bundle) {
        Wearable.MessageApi.addListener( mApiClient, this );
        Log.v(TAG, "connected");



    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onMessageReceived(final MessageEvent messageEvent) {


        runOnUiThread( new Runnable() {
            @Override
            public void run() {
                if( messageEvent.getPath().equalsIgnoreCase( WEAR_MESSAGE_PATH ) ) {
                    //mAdapter.add( new String( messageEvent.getData() ) );
                    //mAdapter.notifyDataSetChanged();
                    //mTextView.setText(new String(messageEvent.getData()));
                    Intent intent = new Intent(getBaseContext(), Main2Activity.class);
                    intent.putExtra("extras", new String(messageEvent.getData()));
                    startActivity(intent);

                }


            }
        });




    }


    private void initGoogleApiClient() {
        mApiClient = new GoogleApiClient.Builder( this )
                .addApi( Wearable.API )
                .addConnectionCallbacks( this )
                .build();

        if( mApiClient != null && !( mApiClient.isConnected() || mApiClient.isConnecting() ) ) {
            mApiClient.connect();
        }
        Log.v(TAG, " " + mApiClient.isConnecting());

    }

}
