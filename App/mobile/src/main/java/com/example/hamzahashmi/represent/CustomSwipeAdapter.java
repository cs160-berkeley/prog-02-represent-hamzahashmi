package com.example.hamzahashmi.represent;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Wearable;

/**
 * Created by hamzahashmi on 2/28/16.
 */
public class CustomSwipeAdapter extends PagerAdapter implements MessageApi.MessageListener, GoogleApiClient.ConnectionCallbacks{
    private int[] image_resources = {R.drawable.cruz, R.drawable.jharden, R.drawable.tduncan};
    public String[] names = {"Ted Cruz", "James Harden","Timothy Duncan"};
    public String[] leanings = {"Republican", "Democrat","Republican"};
    private String[] tweets = {"Donald Trump has small hands!", "Bruh, we are awesome","I am really good at my job."};
    private String[] webs = {"http://www.TedCruz.com", "http://www.JamesHardenillustrated.com","http://www.slamduncan.com"};
    private String[] mails = {"Ted@TedCruz.com", "James@jamesharden.com","dunc@slamduncan.com"};



    private int[] contact = {R.drawable.mail, R.drawable.web};
    private GoogleApiClient mApiClient;


    private Context ctx;
    private LayoutInflater layoutInflater;
    private int currentPage;
    private static final String TAG = "ADAPTandER";



    public CustomSwipeAdapter(Context ctx){


        this.ctx = ctx;
    }

    public int getCurrentPage(){
        return currentPage;
    }

    public void setCurrentPage(int page){
        currentPage = page;
    }


    @Override
    public int getCount() {
        return image_resources.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == (RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position){
        layoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item_view = layoutInflater.inflate(R.layout.content_congressional, container, false);
        ImageView imageView = (ImageView) item_view.findViewById(R.id.repPic);
        TextView leaning = (TextView) item_view.findViewById(R.id.repLeaning);
        final TextView tweet = (TextView) item_view.findViewById(R.id.tweet);
        TextView locAndPos = (TextView) item_view.findViewById(R.id.repLocPos);
        TextView name = (TextView) item_view.findViewById(R.id.repName);
        final ImageView web = (ImageView) item_view.findViewById(R.id.web);
        final ImageView mail = (ImageView) item_view.findViewById(R.id.mail);
        Button btn = (Button) item_view.findViewById(R.id.moreInfo);
        initGoogleApiClient();
        btn.setVisibility(View.VISIBLE);
        name.setText(names[position]);
        locAndPos.setVisibility(View.VISIBLE);
        leaning.setText(leanings[position]);
        tweet.setText(tweets[position]);
        web.setImageResource(contact[1]);
        mail.setImageResource(contact[0]);

        setCurrentPage(position);

        //TextView textView = (TextView) item_view.findViewById(R.id.image_count);
                imageView.setImageResource(image_resources[position]);
        //textView.setText("Image " + position);
        container.addView(item_view);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ctx, DetailActivity.class);
                intent.putExtra("pos", position);
                ctx.startActivity(intent);


            }
        });
        web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(webs[position]));
                ctx.startActivity(browserIntent);
            }
        });

        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //http://stackoverflow.com/questions/5333119/android-opening-the-email-application
                final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

/* Fill it with Data */
                emailIntent.setType("plain/text");
                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, mails[position]);
                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject");
                emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Text");

/* Send it off to the Activity-Chooser */
                ctx.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
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
                Wearable.MessageApi.addListener(mApiClient, this);
                Log.v(TAG, "connected");





    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        Log.v(TAG, messageEvent.getPath());


    }


    private void initGoogleApiClient(){
//        mApiClient = new GoogleApiClient.Builder(ctx)
//                .addApi(Wearable.API)
//                .build();
//        if( mApiClient != null && !( mApiClient.isConnected() || mApiClient.isConnecting() ) ) {
//            mApiClient.connect();
//        }
//        Log.v(TAG, "custom-swipe-mobile-conecting " + mApiClient.isConnecting());




    }


}
