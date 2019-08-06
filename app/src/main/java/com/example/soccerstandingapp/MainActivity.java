package com.example.soccerstandingapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    //Link to all game information
    final private String LINK = "https://s.yimg.com/cv/ae/default/171221/soccer_game_results.json";
    //Create database
    private DataBase dataBase;
    //Store all Team information for different fragment
    private ArrayList<TeamInfo> allTeam;
    //Check if press back button
    private boolean exitPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initial database
        dataBase = new DataBase(this);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        //Use AsyncTask to get data from the internet
        new GetGameData(dataBase, fragmentTransaction).execute(LINK);

    }

    //Get all team information from activity
    public ArrayList<TeamInfo> getAllTeam() {
        return allTeam;
    }

    //Set all team information from fragment
    public void setAllTeam(ArrayList<TeamInfo> allTeam) {
        this.allTeam = allTeam;
    }


    //Override onBackPressed to check if there is only one fragment
    @Override
    public void onBackPressed() {
        //If there is only one fragment
        if(getSupportFragmentManager().getBackStackEntryCount()==0){
            //If user click backpress within 2 seconds
            if(exitPressed){
                super.onBackPressed();
                return;
            }
            exitPressed = true;

            Toast.makeText(this,R.string.exitToast,Toast.LENGTH_SHORT).show();

            //Handler to check back is pressed in 2 sec
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exitPressed = false;
                }
            },2000);
        }
        else{
            //pop the fragment
            getSupportFragmentManager().popBackStack();
        }
    }
}
