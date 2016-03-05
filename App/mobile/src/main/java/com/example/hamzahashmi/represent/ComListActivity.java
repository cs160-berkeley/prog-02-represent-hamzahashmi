package com.example.hamzahashmi.represent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ComListActivity extends AppCompatActivity {

    ListView comm;
    ArrayList<String> listofComm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_com_list);
        comm = (ListView) findViewById(R.id.com);
        listofComm = new ArrayList<String>();

        //reference=  https://github.com/android/platform_frameworks_base/blob/master/core/res/res/layout/simple_list_item_1.xml
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item_1,listofComm);
        populateBills();
        comm.setAdapter(adapter);




    }


    void populateBills(){
        listofComm.add("Committee of Chiraq");
        listofComm.add("Committee of Iraw");
        listofComm.add("Chiraq Crew");
        listofComm.add("Hatian Club");



    }
}
