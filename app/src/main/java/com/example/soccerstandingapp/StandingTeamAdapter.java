package com.example.soccerstandingapp;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class StandingTeamAdapter extends RecyclerView.Adapter<StandingTeamAdapter.ViewHolder> {

    private final String TAG = StandingTeamAdapter.class.getSimpleName();
    //Store all team information
    private ArrayList<TeamInfo> teamArray;
    //Get context for fragment transaction
    private Context mContext;

    public StandingTeamAdapter(ArrayList<TeamInfo> teamArray, Context mContext) {
        this.teamArray = teamArray;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        //Layout for recyclerView item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_standing_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {

        //Get team data at specific position
        TeamInfo tmp = teamArray.get(position);

        //Setup all textView
        viewHolder.teamName.setText(tmp.getTeamName());
        viewHolder.teamWin.setText(String.format("%2d",tmp.getWin()));
        viewHolder.teamLose.setText(String.format("%2d",tmp.getLose()));
        viewHolder.teamDraw.setText(String.format("%2d",tmp.getDraw()));

        float winRate = (float)tmp.getWin()/(tmp.getWin()+tmp.getLose()+tmp.getDraw());
        viewHolder.teamWinRate.setText(String.format("%.2f", winRate));

        //Set OnClickListener for fragment Transaction
        viewHolder.team.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = ((AppCompatActivity)mContext).getSupportFragmentManager().beginTransaction();
                Fragment fragment_TeamInfo = FragmentTeamInfo.newInstance(position);
                fragmentTransaction.replace(R.id.mainActivity, fragment_TeamInfo);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return teamArray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        //Bind all textView for the item in the recyclerView
        private TextView teamName;
        private TextView teamWin;
        private TextView teamLose;
        private TextView teamDraw;
        private TextView teamWinRate;
        private ConstraintLayout team;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            team = (ConstraintLayout)itemView.findViewById(R.id.item);
            teamName = (TextView)itemView.findViewById(R.id.item_teamName);
            teamWin = (TextView)itemView.findViewById(R.id.item_win);
            teamLose = (TextView)itemView.findViewById(R.id.item_lose);
            teamDraw = (TextView)itemView.findViewById(R.id.item_draw);
            teamWinRate = (TextView)itemView.findViewById(R.id.item_winRate);
        }
    }


}
