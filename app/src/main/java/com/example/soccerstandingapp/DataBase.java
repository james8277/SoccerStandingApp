package com.example.soccerstandingapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.database.CursorWindowCompat;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DataBase extends SQLiteOpenHelper {
    private static final String TAG = DataBase.class.getSimpleName();

    //Constant String for Database name & version
    private static final String DATABASE_NAME = "soccer_game";
    private static final int DATABASE_VERSION = 1;

    //Constant String for Table Column
    private static final String KEY_ID = "_ID";
    private static final String KEY_GAME_ID = "gameId";
    private static final String KEY_AWAY_TEAM_ID = "awayTeamId";
    private static final String KEY_AWAY_TEAM_NAME = "awayTeamName";
    private static final String KEY_AWAY_SCORE = "awayScore";
    private static final String KEY_HOME_TEAM_ID = "homeTeamId";
    private static final String KEY_HOME_TEAM_NAME = "homeTeamName";
    private static final String KEY_HOME_SCORE = "homeScore";

    //Constant String for Table name
    private static final String TABLE_SOCCER = "soccerGame";


    //Initial database
    public DataBase(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        //Create Table if not exists
//        Log.e(TAG, "Create table");
        String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS soccerGame" + "(" +
                KEY_ID + " INTEGER PRIMARY KEY, " +
                KEY_GAME_ID + " TEXT, " +
                KEY_AWAY_TEAM_ID + " TEXT, " +
                KEY_AWAY_TEAM_NAME + " TEXT, " +
                KEY_AWAY_SCORE + " INTEGER, " +
                KEY_HOME_TEAM_ID + " TEXT, " +
                KEY_HOME_TEAM_NAME + " TEXT, " +
                KEY_HOME_SCORE + " INTEGER" + ");";

        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //Add Game information into Database
    public void addGameData(GameInfo gameInfo){
//        Log.e(TAG, "Add Game " + gameInfo.toString());

        //Get readable database to get current data number
        SQLiteDatabase readableDatabase = this.getReadableDatabase();

        Cursor cursor = readableDatabase.rawQuery("SELECT " +
                KEY_ID + "," +
                KEY_GAME_ID +
                " FROM " +
                TABLE_SOCCER,null);

        //Get current data number
        int number = 0;
        if(cursor.moveToLast()){
            number = cursor.getInt(0)+1;
        }
        //Check if the game is already in the database
        cursor.moveToFirst();
        for(int i=0;i<number-1;i++){
            if(gameInfo.getGameId().equals(cursor.getString(1))){
                return;
            }
            cursor.moveToNext();
        }

//        Log.e(TAG, "Add game " + number);

        //Get writable database to write new game information
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, number);
        values.put(KEY_GAME_ID, gameInfo.getGameId());
        values.put(KEY_AWAY_TEAM_ID, gameInfo.getAwayTeam().getTeamId());
        values.put(KEY_AWAY_TEAM_NAME, gameInfo.getAwayTeam().getTeamName());
        values.put(KEY_AWAY_SCORE, gameInfo.getAwayTeamScore());
        values.put(KEY_HOME_TEAM_ID, gameInfo.getHomeTeam().getTeamId());
        values.put(KEY_HOME_TEAM_NAME, gameInfo.getHomeTeam().getTeamName());
        values.put(KEY_HOME_SCORE, gameInfo.getHomeTeamScore());

        database.insert(TABLE_SOCCER, null, values);
    }

    //Get all data number
    public int getDataNumber(String string){
        //Get readable database to get current data number
        SQLiteDatabase database = this.getReadableDatabase();

        //Use ID to get number of data
        Cursor cursor = database.rawQuery("SELECT " +
                KEY_ID +
                " FROM " +
                TABLE_SOCCER, null);

        if(cursor.moveToLast()){
            return cursor.getInt(0)+1;
        }
        else{
            return 0;
        }
    }

    //Retrieve all game information
    public ArrayList<GameInfo> getAllGame(){

//        Log.e(TAG, "Get all game data");

        //Get readable database to get all data
        SQLiteDatabase database = this.getReadableDatabase();
        ArrayList<GameInfo> allGame = new ArrayList<>();

        //Select all column in the table
        Cursor cursor =database.rawQuery("SELECT " +
                KEY_GAME_ID + "," +
                KEY_AWAY_TEAM_ID + "," +
                KEY_AWAY_TEAM_NAME + "," +
                KEY_AWAY_SCORE + "," +
                KEY_HOME_TEAM_ID + "," +
                KEY_HOME_TEAM_NAME + "," +
                KEY_HOME_SCORE + " " +
                "FROM " + TABLE_SOCCER,null);

        //Get data number
        int gameNumber = getDataNumber(KEY_ID);
//        Log.e(TAG, "Game num    " + gameNumber);

        //Iterate all data and construct GameInfo
        cursor.moveToFirst();
        for(int i=0;i<gameNumber;i++) {
//            Log.e(TAG, "Get game " + i);
            GameInfo tmpGame = new GameInfo();
            TeamInfo tmpAwayTeam = new TeamInfo();
            TeamInfo tmpHomeTeam = new TeamInfo();

            tmpGame.setGameId(cursor.getString(0));
            tmpAwayTeam.setTeamId(cursor.getString(1));
            tmpAwayTeam.setTeamName(cursor.getString(2));
            tmpGame.setAwayTeam(tmpAwayTeam);
            tmpGame.setAwayTeamScore(cursor.getInt(3));

            tmpHomeTeam.setTeamId(cursor.getString(4));
            tmpHomeTeam.setTeamName(cursor.getString(5));
            tmpGame.setHomeTeam(tmpHomeTeam);
            tmpGame.setHomeTeamScore(cursor.getInt(6));


            allGame.add(tmpGame);

            cursor.moveToNext();
        }


        return allGame;
    }

    //Delete all store data
    public void deleteTable() {
        SQLiteDatabase database = this.getReadableDatabase();
        Log.e(TAG,"delete");
        database.delete(TABLE_SOCCER, null, null);
    }
}
