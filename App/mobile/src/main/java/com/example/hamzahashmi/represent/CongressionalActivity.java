package com.example.hamzahashmi.represent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import java.util.Arrays;

public class CongressionalActivity extends AppCompatActivity implements MessageApi.MessageListener,GoogleApiClient.ConnectionCallbacks{

    ViewPager viewPager;
    CustomSwipeAdapter adapter;
    Button moreInfo;
    ImageView img;
    private GoogleApiClient mApiClient;
    private static final String START_ACTIVITY = "/start_activity";
    private static final String WEAR_MESSAGE_PATH = "/message";
    private static final String LEANING_PATH = "/leaning";
    private static final String DETAIL_ACTIVITY = "/SHOW_DETAILS";


    private static final String TAG = "Congressional";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congressional);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        adapter = new CustomSwipeAdapter(this);
        viewPager.setAdapter(adapter);
        moreInfo = (Button) findViewById(R.id.moreInfo);
        img = (ImageView) findViewById(R.id.web);
        initGoogleApiClient();
        //endMessage(WEAR_MESSAGE_PATH, Arrays.toString(adapter.names));
        //sendMessage(LEANING_PATH, Arrays.toString(adapter.leanings));
        String[] both = both(adapter.names, adapter.leanings);
        Log.v(TAG, "viewpageritem = " + viewPager.getCurrentItem());
        Wearable.MessageApi.addListener(mApiClient, this);


        sendMessage(WEAR_MESSAGE_PATH, Arrays.toString(both));







    }

    @Override
    public void onConnected(Bundle bundle) {
        Wearable.MessageApi.addListener( mApiClient, this );
        Log.v(TAG, "connected " );




    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        Log.v(TAG, "connected " + messageEvent.getPath() );

        if (messageEvent.getPath().equalsIgnoreCase( DETAIL_ACTIVITY )){

            String s = new String(messageEvent.getData());
            int position = Integer.parseInt(s);
            Intent intent = new Intent(getBaseContext(), DetailActivity.class);
            intent.putExtra("pos", position);
            startActivity(intent);



        }

    }


    private void initGoogleApiClient(){
        mApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .build();

        if( mApiClient != null && !( mApiClient.isConnected() || mApiClient.isConnecting() ) ) {
            mApiClient.connect();
        }



    }


    private void sendMessage( final String path, final String text ) {
        new Thread( new Runnable() {
            @Override
            public void run() {
                NodeApi.GetConnectedNodesResult nodes = Wearable.NodeApi.getConnectedNodes( mApiClient ).await();
                for(Node node : nodes.getNodes()) {
                    MessageApi.SendMessageResult result = Wearable.MessageApi.sendMessage(
                            mApiClient, node.getId(), path, text.getBytes() ).await();
                }

            }
        }).start();
    }





    @Override
    protected void onDestroy(){
        super.onDestroy();
        mApiClient.disconnect();
    }


    //source = http://stackoverflow.com/questions/80476/how-can-i-concatenate-two-arrays-in-java

    private String[] both(String[] a, String[] b){
            int aLen = a.length;
            int bLen = b.length;
            String[] c= new String[aLen+bLen];
            System.arraycopy(a, 0, c, 0, aLen);
            System.arraycopy(b, 0, c, aLen, bLen);
            return c;
    }




}
