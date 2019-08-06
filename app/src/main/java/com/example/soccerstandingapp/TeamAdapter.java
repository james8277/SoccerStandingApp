package com.example.soccerstandingapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.ViewHolder> {

    //Get all opponent information
    ArrayList<VersusTeam> allOpponent;

    public TeamAdapter(ArrayList<VersusTeam> allOpponent) {
        this.allOpponent = allOpponent;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        //Layout for recyclerView item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_teaminfo_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

        //Get all opponent team data at specific position
        VersusTeam tmp = allOpponent.get(position);

        viewHolder.textView_name.setText(tmp.getTeam());
        viewHolder.textView_win.setText(String.valueOf(tmp.getWin()));
        viewHolder.textView_lose.setText(String.valueOf(tmp.getLose()));
        viewHolder.textView_draw.setText(String.valueOf(tmp.getDraw()));
        viewHolder.textView_total.setText(String.valueOf(tmp.getTotal()));

    }

    @Override
    public int getItemCount() {
        return allOpponent.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        //Bind all textView for the item in the recyclerView
        private TextView textView_name;
        private TextView textView_win;
        private TextView textView_lose;
        private TextView textView_draw;
        private TextView textView_total;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView_name = (TextView)itemView.findViewById(R.id.teamInfo_title_teamName);
            textView_win = (TextView)itemView.findViewById(R.id.teamInfo_title_win);
            textView_lose = (TextView)itemView.findViewById(R.id.teamInfo_title_lose);
            textView_draw = (TextView)itemView.findViewById(R.id.teamInfo_title_draw);
            textView_total = (TextView)itemView.findViewById(R.id.teamInfo_title_total);

        }
    }


}
