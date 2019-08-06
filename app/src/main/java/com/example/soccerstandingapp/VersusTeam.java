package com.example.soccerstandingapp;


//Use to store all opponent data
public class VersusTeam {

    private String team;
    private int win;
    private int lose;
    private int draw;

    public VersusTeam(String team, int win, int lose, int draw) {
        this.team = team;
        this.win = win;
        this.lose = lose;
        this.draw = draw;
    }

    public void setTeam(String team) {
        this.team = team;
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

    public String getTeam() {
        return team;
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

    public int getTotal(){
        return win+lose+draw;
    }

    @Override
    public int hashCode() {
        return team.hashCode();
    }
}
