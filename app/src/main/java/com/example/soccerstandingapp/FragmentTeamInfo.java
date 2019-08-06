package com.example.soccerstandingapp;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class FragmentTeamInfo extends Fragment {

    //Which team is selected
    private TeamInfo selectedTeam;
    //Use a hashmap to store opponent team
    private HashMap<String, int[]> versusTeam;
    //All opponent team without duplication
    private ArrayList<VersusTeam> allOpponent;
    //Team adapter for recyclerView
    private TeamAdapter teamAdapter;
    //Sorting type
    private TextView sortType;

    //Create Fragment using different team position in the arraylist
    public static FragmentTeamInfo newInstance(int position){
        FragmentTeamInfo fragmentTeamInfo = new FragmentTeamInfo();

        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        fragmentTeamInfo.setArguments(bundle);

        return fragmentTeamInfo;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Get selected team from the activity
        selectedTeam = ((MainActivity)getActivity()).getAllTeam().get(getArguments().getInt("position"));
        //Get all game information
        ArrayList<GameInfo> gameData = selectedTeam.getGames();
        //Initialize
        versusTeam = new HashMap<>();
        allOpponent = new ArrayList<>();

        //
        for(GameInfo game : gameData){
            if(selectedTeam.getTeamName().equals(game.getAwayTeam().getTeamName())){
                String opponentName = game.getHomeTeam().getTeamName();
                versusTeam.putIfAbsent(opponentName, new int[3]);
                if(game.getAwayTeamScore()==game.getHomeTeamScore()){
                    versusTeam.get(opponentName)[2]++;
                }
                else if(game.getAwayTeamScore()>game.getHomeTeamScore()){
                    versusTeam.get(opponentName)[0]++;
                }
                else{
                    versusTeam.get(opponentName)[1]++;
                }
            }
            else {
                String opponentName = game.getAwayTeam().getTeamName();
                versusTeam.putIfAbsent(opponentName, new int[3]);
                if(game.getAwayTeamScore()==game.getHomeTeamScore()){
                    versusTeam.get(opponentName)[2]++;
                }
                else if(game.getAwayTeamScore()>game.getHomeTeamScore()){
                    versusTeam.get(opponentName)[0]++;
                }
                else{
                    versusTeam.get(opponentName)[1]++;
                }
            }
        }

        for(HashMap.Entry team : versusTeam.entrySet()){
            int[] tmp = versusTeam.get(team.getKey());

            VersusTeam tmpTeam = new VersusTeam((String)team.getKey(), tmp[0], tmp[1], tmp[2]);

            allOpponent.add(tmpTeam);
        }

        Collections.sort(allOpponent, new Comparator<VersusTeam>() {
            @Override
            public int compare(VersusTeam o1, VersusTeam o2) {
                return o1.getTeam().compareTo(o2.getTeam());
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_teaminfo, parent, false);

        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.fragment_teamInfo_recyclerView);
        teamAdapter = new TeamAdapter(allOpponent);
        recyclerView.setAdapter(teamAdapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        TextView textView_title = (TextView)view.findViewById(R.id.fragment_teamInfo_title);
        textView_title.setText(selectedTeam.getTeamName());

        final TextView textView_name = (TextView)view.findViewById(R.id.fragment_teamInfo_title_teamName);
        textView_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(allOpponent, new Comparator<VersusTeam>() {
                    @Override
                    public int compare(VersusTeam o1, VersusTeam o2) {
                        return o1.getTeam().compareTo(o2.getTeam());
                    }
                });
                sortType.setTypeface(null, Typeface.NORMAL);
                sortType = textView_name;
                sortType.setTypeface(null, Typeface.BOLD);
                teamAdapter.notifyDataSetChanged();
            }
        });

        final TextView textView_win = (TextView)view.findViewById(R.id.fragment_teamInfo_title_win);
        sortType = textView_win;
        sortType.setTypeface(null, Typeface.BOLD);
        textView_win.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(allOpponent, new Comparator<VersusTeam>() {
                    @Override
                    public int compare(VersusTeam o1, VersusTeam o2) {
                        return Integer.compare(o2.getWin(), o1.getWin());
                    }
                });
                sortType.setTypeface(null, Typeface.NORMAL);
                sortType = textView_win;
                sortType.setTypeface(null, Typeface.BOLD);
                teamAdapter.notifyDataSetChanged();
            }
        });

        final TextView textView_lose = (TextView)view.findViewById(R.id.fragment_teamInfo_title_lose);
        textView_lose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(allOpponent, new Comparator<VersusTeam>() {
                    @Override
                    public int compare(VersusTeam o1, VersusTeam o2) {
                        return Integer.compare(o2.getLose(), o1.getLose());
                    }
                });
                sortType.setTypeface(null, Typeface.NORMAL);
                sortType = textView_lose;
                sortType.setTypeface(null, Typeface.BOLD);
                teamAdapter.notifyDataSetChanged();
            }
        });

        final TextView textView_draw = (TextView)view.findViewById(R.id.fragment_teamInfo_title_draw);
        textView_draw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(allOpponent, new Comparator<VersusTeam>() {
                    @Override
                    public int compare(VersusTeam o1, VersusTeam o2) {
                        return Integer.compare(o2.getDraw(), o1.getDraw());
                    }
                });
                sortType.setTypeface(null, Typeface.NORMAL);
                sortType = textView_draw;
                sortType.setTypeface(null, Typeface.BOLD);
                teamAdapter.notifyDataSetChanged();
            }
        });

        final TextView textView_total = (TextView)view.findViewById(R.id.fragment_standing_teamInfo_title_total);
        textView_total.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(allOpponent, new Comparator<VersusTeam>() {
                    @Override
                    public int compare(VersusTeam o1, VersusTeam o2) {
                        return Integer.compare(o2.getTotal(), o1.getTotal());
                    }
                });
                sortType.setTypeface(null, Typeface.NORMAL);
                sortType = textView_total;
                sortType.setTypeface(null, Typeface.BOLD);
                teamAdapter.notifyDataSetChanged();
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        ActionBar actionBar = ((MainActivity) getActivity()).getSupportActionBar();
        actionBar.hide();
//        actionBar.setTitle(selectedTeam.getTeamName());
    }
}
