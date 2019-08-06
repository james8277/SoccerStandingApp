package com.example.soccerstandingapp;


//Game Information class to store all game information
public class GameInfo {

    //Game id
    private String gameId;
    private TeamInfo awayTeam;
    private int awayTeamScore;

    private TeamInfo homeTeam;
    private int homeTeamScore;

    public GameInfo() {
        gameId = "";
        awayTeam = null;
        awayTeamScore = 0;
        homeTeam = null;
        homeTeamScore = 0;
    }

    public GameInfo(String gameId, TeamInfo awayTeam, int awayTeamScore, TeamInfo homeTeam, int homeTeamScore) {
        this.gameId = gameId;
        this.awayTeam = awayTeam;
        this.awayTeamScore = awayTeamScore;
        this.homeTeam = homeTeam;
        this.homeTeamScore = homeTeamScore;
    }

    public String getGameId() {
        return gameId;
    }

    public TeamInfo getAwayTeam() {
        return awayTeam;
    }

    public int getAwayTeamScore() {
        return awayTeamScore;
    }

    public TeamInfo getHomeTeam() {
        return homeTeam;
    }

    public int getHomeTeamScore() {
        return homeTeamScore;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public void setAwayTeam(TeamInfo awayTeam) {
        this.awayTeam = awayTeam;
    }

    public void setAwayTeamScore(int awayTeamScore) {
        this.awayTeamScore = awayTeamScore;
    }

    public void setHomeTeam(TeamInfo homeTeam) {
        this.homeTeam = homeTeam;
    }

    public void setHomeTeamScore(int homeTeamScore) {
        this.homeTeamScore = homeTeamScore;
    }

}
