package com.example.hamzahashmi.represent;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class BillListActivity extends AppCompatActivity {

    ListView bills;
    ArrayList<String> listOfBills;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_list);
        bills = (ListView) findViewById(R.id.bills);
        listOfBills = new ArrayList<String>();

        //reference=  https://github.com/android/platform_frameworks_base/blob/master/core/res/res/layout/simple_list_item_1.xml
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item_1,listOfBills);
        populateBills();
        bills.setAdapter(adapter);




    }


    void populateBills(){
        listOfBills.add("Deport everyone, introduced on 6/2/14");
        listOfBills.add("Marijuana Illegalization, introduced on 3/5/15");
        listOfBills.add("Anti Gay, introduced on 4/4/14");
        listOfBills.add("Anti choice, introduced on 4/5/16");



    }
}
