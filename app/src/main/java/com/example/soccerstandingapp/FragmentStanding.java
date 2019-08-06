package com.example.soccerstandingapp;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class FragmentStanding extends Fragment {

    private final String TAG = FragmentStanding.class.getSimpleName();

    //Adapter for all teamInfo
    private StandingTeamAdapter teamAdapter;
    //Read data from database
    private DataBase dataBase;
    //Array to store all game information
    private ArrayList<GameInfo> allGame;
    //Use a hashmap to check team is duplicated and store team information
    private HashMap<TeamInfo, ArrayList<GameInfo>> allHashTeam;
    //Get team information from hashmap
    private ArrayList<TeamInfo> allTeam;
    //Current sorting type
    private TextView sortType;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Initial the database
        dataBase = new DataBase(getActivity());
        //Get all game data from database
        allGame = dataBase.getAllGame();
        //Initialize
        allHashTeam = new HashMap<>();
        allTeam = new ArrayList<>();

        //Iterate all game & store into team information
        for(int i=0;i<allGame.size();i++){
//            Log.e(TAG, "Game " + i);
            GameInfo tmpGame = allGame.get(i);
            TeamInfo tmpHomeTeam = tmpGame.getHomeTeam();
            TeamInfo tmpAwayTeam = tmpGame.getAwayTeam();

            allHashTeam.putIfAbsent(tmpHomeTeam, new ArrayList<GameInfo>());
            allHashTeam.putIfAbsent(tmpAwayTeam, new ArrayList<GameInfo>());

            allHashTeam.get(tmpHomeTeam).add(tmpGame);
            allHashTeam.get(tmpAwayTeam).add(tmpGame);
        }

        //Get all team information from hashmap and store in arraylist
        for(HashMap.Entry team : allHashTeam.entrySet()){
            TeamInfo tmp = (TeamInfo)team.getKey();
            ArrayList<GameInfo> tmpGameList = allHashTeam.get(team.getKey());
            tmp.setGames(tmpGameList);

            allTeam.add(tmp);
            calculateTeamInfo(tmp);
//            Log.e(TAG, tmp.toString() + "\n");
        }

        //Sort the all team by default using winrate
        Collections.sort(allTeam, new Comparator<TeamInfo>() {
            @Override
            public int compare(TeamInfo o1, TeamInfo o2) {
                return Float.compare(o2.getWinRate(), o1.getWinRate());
            }
        });


        //Set all team information back to the activity
        ((MainActivity)getActivity()).setAllTeam(allTeam);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //Setup fragment layout
        View view = inflater.inflate(R.layout.fragment_standing, container, false);

//        Log.e(TAG, "Adapter size = " + allTeam.size());

        //bind the recyclerVuew
        RecyclerView recyclerView_team = (RecyclerView)view.findViewById(R.id.fragment_standing_recyclerView);
        //Setup adapter
        teamAdapter = new StandingTeamAdapter(allTeam, getActivity());
        recyclerView_team.setAdapter(teamAdapter);

        //Set recycler layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView_team.setLayoutManager(layoutManager);

        //Set name setOnClickListener to sort by team name
        final TextView textView_team = (TextView)view.findViewById(R.id.fragment_standing_title_teamName);
        textView_team.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(allTeam, new Comparator<TeamInfo>() {
                    @Override
                    public int compare(TeamInfo o1, TeamInfo o2) {
                        return o1.getTeamName().compareTo(o2.getTeamName());
                    }
                });
                sortType.setTypeface(null, Typeface.NORMAL);
                sortType = textView_team;
                sortType.setTypeface(null, Typeface.BOLD);
                teamAdapter.notifyDataSetChanged();
            }
        });

        //Set win setOnClickListener to sort by team name
        final TextView textView_win = (TextView)view.findViewById(R.id.fragment_standing_title_win);
        textView_win.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(allTeam, new Comparator<TeamInfo>() {
                    @Override
                    public int compare(TeamInfo o1, TeamInfo o2) {
                        return Integer.compare(o2.getWin(), o1.getWin());
                    }
                });
                sortType.setTypeface(null, Typeface.NORMAL);
                sortType = textView_win;
                sortType.setTypeface(null, Typeface.BOLD);
                teamAdapter.notifyDataSetChanged();
            }
        });


        //Set lose setOnClickListener to sort by team name
        final TextView textView_lose = (TextView)view.findViewById(R.id.fragment_standing_title_lose);
        textView_lose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(allTeam, new Comparator<TeamInfo>() {
                    @Override
                    public int compare(TeamInfo o1, TeamInfo o2) {
                        return Integer.compare(o2.getLose(), o1.getLose());
                    }
                });
                sortType.setTypeface(null, Typeface.NORMAL);
                sortType = textView_lose;
                sortType.setTypeface(null, Typeface.BOLD);
                teamAdapter.notifyDataSetChanged();
            }
        });

        //Set draw setOnClickListener to sort by team name
        final TextView textView_draw = (TextView)view.findViewById(R.id.fragment_standing_title_draw);
        textView_draw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(allTeam, new Comparator<TeamInfo>() {
                    @Override
                    public int compare(TeamInfo o1, TeamInfo o2) {
                        return Integer.compare(o2.getDraw(), o1.getDraw());
                    }
                });
                sortType.setTypeface(null, Typeface.NORMAL);
                sortType = textView_draw;
                sortType.setTypeface(null, Typeface.BOLD);
                teamAdapter.notifyDataSetChanged();
            }
        });

        //Set winRate setOnClickListener to sort by team name
        final TextView textView_winRate = (TextView)view.findViewById(R.id.fragment_standing_title_winRate);
        sortType = textView_winRate;
        sortType.setTypeface(null, Typeface.BOLD);
        textView_winRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(allTeam, new Comparator<TeamInfo>() {
                    @Override
                    public int compare(TeamInfo o1, TeamInfo o2) {
                        return Float.compare(o2.getWinRate(), o1.getWinRate());
                    }
                });
                sortType.setTypeface(null, Typeface.NORMAL);
                sortType = textView_winRate;
                sortType.setTypeface(null, Typeface.BOLD);
                teamAdapter.notifyDataSetChanged();
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        //Show the actionBar if it is hidden
        ActionBar actionBar = ((MainActivity)getActivity()).getSupportActionBar();
        actionBar.show();
        actionBar.setTitle(R.string.title);
    }

    //Calculate Team Information
    public void calculateTeamInfo(TeamInfo inputTeam){

        ArrayList<GameInfo> tmpGameList = inputTeam.getGames();

        //Check which team win and store in team information
        for(int i=0;i<tmpGameList.size();i++){
            GameInfo tmpGame = tmpGameList.get(i);
            if(inputTeam.equals(tmpGame.getAwayTeam())){
                if(tmpGame.getAwayTeamScore()==tmpGame.getHomeTeamScore()){
                    inputTeam.setDraw(inputTeam.getDraw()+1);
                }
                else if(tmpGame.getAwayTeamScore()>tmpGame.getHomeTeamScore()){
                    inputTeam.setWin(inputTeam.getWin()+1);
                }
                else{
                    inputTeam.setLose(inputTeam.getLose()+1);
                }
            }
            else{
                if(tmpGame.getAwayTeamScore()==tmpGame.getHomeTeamScore()){
                    inputTeam.setDraw(inputTeam.getDraw()+1);
                }
                else if(tmpGame.getAwayTeamScore()>tmpGame.getHomeTeamScore()){
                    inputTeam.setLose(inputTeam.getLose()+1);
                }
                else{
                    inputTeam.setWin(inputTeam.getWin()+1);
                }
            }
        }
        inputTeam.setWinRate((float)inputTeam.getWin()/(inputTeam.getWin()+inputTeam.getDraw()+inputTeam.getLose()));

    }

}
