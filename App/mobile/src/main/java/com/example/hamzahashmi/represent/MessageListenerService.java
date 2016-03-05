package com.example.hamzahashmi.represent;

/**
 * Created by hamzahashmi on 3/3/16.
 */
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

/**
 * Created by hamzahashmi on 2/29/16.
 */
public class MessageListenerService extends WearableListenerService {

    private static final String START_ACTIVITY = "/start_activity";
    private static final String DETAIL_ACTIVITY = "/SHOW_DETAILS";
    private static final String TAG = "/LISTENER-SERVICE";



    @Override
    public void onMessageReceived(MessageEvent messageEvent) {

        Log.v(TAG, "sending "  + messageEvent.getPath());

        if( messageEvent.getPath().equalsIgnoreCase( DETAIL_ACTIVITY ) ) {
            Intent intent = new Intent( this, DetailActivity.class );
            intent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
            startActivity( intent );
        } else {
            super.onMessageReceived( messageEvent );
        }
    }



}

