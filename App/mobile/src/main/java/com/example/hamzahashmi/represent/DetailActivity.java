package com.example.hamzahashmi.represent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    Button comm;
    Button bill;
    private int[] image_resources = {R.drawable.cruz, R.drawable.jharden, R.drawable.tduncan};
    public String[] names = {"Ted Cruz", "James Harden","Timothy Duncan"};
    public String[] leanings = {"Republican", "Democrat","Republican"};
    public String[] dates = {"Ends term on 10/13/16", "Ends term on 12/15/16","Ends term on 12/12/16"};
    ImageView img;
    TextView lean;
    TextView name;
    TextView dat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        comm = (Button)findViewById(R.id.com);
        bill = (Button)findViewById(R.id.bill);
        img = (ImageView) findViewById(R.id.img);
        name = (TextView) findViewById(R.id.name);
        dat = (TextView) findViewById(R.id.end_date);
        lean = (TextView) findViewById(R.id.leaning);
        int pos = getIntent().getIntExtra("pos", 0);
        img.setImageResource(image_resources[pos]);
        lean.setText(leanings[pos]);
        name.setText(names[pos]);
        dat.setText(dates[pos]);





        comm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ComListActivity.class);
                startActivity(intent);

            }
        });
        bill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BillListActivity.class);
                startActivity(intent);
            }
        });
    }

}
