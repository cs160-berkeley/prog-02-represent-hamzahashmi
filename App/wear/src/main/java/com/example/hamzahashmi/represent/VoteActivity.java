package com.example.hamzahashmi.represent;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class VoteActivity extends Activity {

    TextView obama;
    TextView romney;
    TextView county;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);
        obama = (TextView) findViewById(R.id.obama);
        romney = (TextView) findViewById(R.id.romney);
        county = (TextView) findViewById(R.id.county);
        int position = getIntent().getIntExtra("pos", 0);
        VoteData data = new VoteData(position);
        obama.setText(data.obamaVotes[position]);
        romney.setText(data.romneyVotes[position]);
        county.setText(data.counties[position]);


    }
}
