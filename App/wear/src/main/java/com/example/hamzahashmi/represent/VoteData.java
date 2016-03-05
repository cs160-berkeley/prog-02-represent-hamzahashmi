package com.example.hamzahashmi.represent;

/**
 * Created by hamzahashmi on 3/4/16.
 */
public class VoteData {

    String[] obamaVotes  = {"Obama 50%","Obama 60%", "Obama 45%", "Obama 30%"};
    String[] romneyVotes = {"Romney 50%", "Romney 40%", "Romney 55%", "Romney 70%"};
    String[] counties = {"randomcounty1", "randomcounty2", "randomcounty3","randomcounty4" };
    int pos; //index between 0-2 into the arrays


    public VoteData(int position){
        pos = position;
    }



}
