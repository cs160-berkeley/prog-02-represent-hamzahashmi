package com.example.hamzahashmi.represent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class Main2Activity extends Activity {

    private TextView mTextView;
    ViewPager viewPager;
    CustomSwipeAdapter adapter;
    private static final String TAG = "My2Activity";

    private SensorManager mSensorManager;
    private float mAccel; // acceleration apart from gravity
    private float mAccelCurrent; // current acceleration including gravity
    private float mAccelLast; // last acceleration including gravity

    private final SensorEventListener mSensorListener = new SensorEventListener() {

        public void onSensorChanged(SensorEvent se) {
            float x = se.values[0];
            float y = se.values[1];
            float z = se.values[2];
            mAccelLast = mAccelCurrent;
            mAccelCurrent = (float) Math.sqrt((double) (x*x + y*y + z*z));
            float delta = mAccelCurrent - mAccelLast;
            mAccel = mAccel * 0.9f + delta; // perform low-cut filter

            if (mAccel > 2) {
                Toast toast = Toast.makeText(getApplicationContext(), "Device has shaken.", Toast.LENGTH_LONG);
                toast.show();
                mAccel = 0.2f;
                Intent intent = new Intent(getBaseContext(), VoteActivity.class);
                intent.putExtra("pos", 3);
                startActivity(intent);

            }


        }

        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        mSensorManager.unregisterListener(mSensorListener);
        super.onPause();
    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        adapter = new CustomSwipeAdapter(this);

        Intent intent = getIntent();
        String ext = intent.getStringExtra("extras");
        Log.v(TAG,ext);

        String[] strngs = ext.split("[\\W]");
        int blanks = 0;
        for (int i = 0; i < strngs.length; i++){
            Log.v(TAG, "blanks- " + strngs[i]);
            if( "".equals(strngs[i]) || " ".equals(strngs[i])){
                blanks++;

            }
        }

        String[] passed = new String[strngs.length - blanks];
        int a =0;
        int j =0;
        while (a < passed.length){
            if ("".equals(strngs[j]) || " ".equals(strngs[j])){
            }
            else{
                passed[a] = strngs[j];
                a++;
            }
            j++;
        }

        adapter.sources = passed;
        viewPager.setAdapter(adapter);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        mAccel = 0.00f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;

    }
}
