package com.example.soccerstandingapp;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


//Team information class to store team information
public class TeamInfo{

    private final String TAG = TeamInfo.class.getSimpleName();

    private String teamName;
    private String teamId;
    private int win;
    private int lose;
    private int draw;
    private float winRate;
    //Store all game information
    private ArrayList<GameInfo> games;

    public TeamInfo() {
        teamName = "";
        teamId = "";
        win = 0;
        lose = 0;
        draw = 0;
        games = null;
        winRate = 0;
    }

    public TeamInfo(String name) {
        this.teamName = name;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getTeamId() {
        return teamId;
    }

    public int getWin() {
        return win;
    }

    public int getLose() {
        return lose;
    }

    public int getDraw() {
        return draw;
    }

    public ArrayList<GameInfo> getGames() {
        return games;
    }

    public float getWinRate() {
        return winRate;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public void setWin(int win) {
        this.win = win;
    }

    public void setLose(int lose) {
        this.lose = lose;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public void setGames(ArrayList<GameInfo> games) {
        this.games = games;
    }

    public void setWinRate(float winRate) {
        this.winRate = winRate;
    }

    @Override
    public int hashCode() {

        return teamName.hashCode();
    }

    @Override
    public boolean equals(Object obj) {

        if(this.teamName.equals(((TeamInfo)obj).teamName)){
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public String toString() {

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(teamName + "\t" +
                teamId + "\t" +
                win + "\t" +
                lose + "\t" +
                draw + "\t" +
                games + "\t");

        return stringBuilder.toString();
    }

}
