package com.example.hamzahashmi.represent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

/**
 * Created by hamzahashmi on 2/29/16.
 */
public class CustomSwipeAdapter extends PagerAdapter implements  MessageApi.MessageListener, GoogleApiClient.ConnectionCallbacks {

    //private int[] image_resources = {R.drawable.cruz, R.drawable.jharden, R.drawable.tduncan};
    private Context ctx;
    private LayoutInflater layoutInflater;
    public String[] sources = {"America", "Cruz", "San Francisco"};
    public String[] leanings = {"republican", "democrat", "republican"};
    private GoogleApiClient mApiClient;
    private static final String DETAIL_ACTIVITY = "/SHOW_DETAILS";
    private static final String TAG = "custom_swipe";




    public CustomSwipeAdapter(Context ctx){
        this.ctx = ctx;
    }

    @Override
    public int getCount() {
        return sources.length/3;
        //return image_resources.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view==(RelativeLayout)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position){
        layoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item_view = layoutInflater.inflate(R.layout.swipe_layout, container, false);
        // ImageView imageView = (ImageView) item_view.findViewById(R.id.image_view);
        TextView textView = (TextView) item_view.findViewById(R.id.image_count);
        TextView leaningView = (TextView) item_view.findViewById(R.id.leaning);
        Button votes = (Button) item_view.findViewById(R.id.votes);

        //imageView.setImageResource(image_resources[position]);
        textView.setText(sources[position*2] + " " +  sources[position*2 + 1]);
        leaningView.setText(sources[((sources.length/3) *2) + position]);
        container.addView(item_view);
        initGoogleApiClient();
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage(DETAIL_ACTIVITY, Integer.toString(position));

            }
        });
        votes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx,VoteActivity.class );
                intent.putExtra("pos", position);
                ctx.startActivity(intent);
            }
        });


        return item_view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object){
        container.removeView((RelativeLayout) object);


    }

    @Override
    public void onConnected(Bundle bundle) {
        Wearable.MessageApi.addListener( mApiClient, this );


    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        Log.v(TAG, messageEvent.getPath());


    }

    private void initGoogleApiClient(){
        mApiClient = new GoogleApiClient.Builder(ctx)
                .addApi(Wearable.API)
                .build();

        mApiClient.connect();
        Log.v(TAG, "connected-status " + mApiClient.isConnected());



    }

    private void sendMessage( final String path, final String text ) {


        Log.v(TAG, path);

        new Thread( new Runnable() {
            @Override
            public void run() {
                NodeApi.GetConnectedNodesResult nodes = Wearable.NodeApi.getConnectedNodes( mApiClient ).await();
                for(Node node : nodes.getNodes()) {
                    Log.v(TAG, "sending " + path);
                    MessageApi.SendMessageResult result = Wearable.MessageApi.sendMessage(
                            mApiClient, node.getId(), path, text.getBytes() ).await();
                }

            }
        }).start();
    }





}

