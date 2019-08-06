package com.example.soccerstandingapp;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class GetGameData extends AsyncTask<String, Integer, String> {

    private final String TAG = GetGameData.class.getSimpleName();
    //Constant String to check if the connection is over 5 seconds
    private final String TIMEOUT = "Network timeout";
    //Create database
    private DataBase dataBase;
    //Get FragmentTransaction for Fragment transaction
    private FragmentTransaction fragmentTransaction;

    //Constructor
    public GetGameData(DataBase dataBase, FragmentTransaction fragmentTransaction) {
        this.dataBase = dataBase;
        this.fragmentTransaction = fragmentTransaction;
    }

    @Override
    protected String doInBackground(String... urls) {

        try {
            //Create URL from input string
            URL link = new URL(urls[0]);
            HttpURLConnection urlConnection = (HttpURLConnection) link.openConnection();

            //check if the connection is timeout
            urlConnection.setConnectTimeout(5000);

            //Use inputstream & bufferedReader to get data
            InputStream stream = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder stringBuilder = new StringBuilder();

            //Store all data into a StringBuilder
            String inputString = bufferedReader.readLine();
            while(inputString!=null){
                stringBuilder.append(inputString);
                stringBuilder.append("\n");
                inputString = bufferedReader.readLine();
            }
//            Log.e(TAG, "String: " + stringBuilder.toString());

            //Disconnect after loading data
            urlConnection.disconnect();

            //return all data string
            return stringBuilder.toString();

        } catch (java.net.SocketTimeoutException e) {
            return TIMEOUT;
        }catch (IOException e) {
            e.printStackTrace();
        }


        return "";
    }

    @Override
    protected void onPostExecute(String input) {
        super.onPostExecute(input);

        //If the connection is not timeout
        if(!input.equals(TIMEOUT)){
            try {
                //Using input string to create JSON array
                JSONArray data = new JSONArray(input);
//            Log.e(TAG, "data length = " + data.length());

                for(int i=0;i<data.length();i++){
                    //Get Json object
                    JSONObject currentObject = data.getJSONObject(i);
                    //Use GameInfo & TeamInfo to store the data
                    GameInfo tmpGame = new GameInfo();
                    TeamInfo tmpAwayTeam = new TeamInfo();
                    TeamInfo tmpHomeTeam = new TeamInfo();

                    tmpGame.setGameId(currentObject.getString("gameId"));
                    tmpAwayTeam.setTeamId(currentObject.getString("awayTeamId"));
                    tmpAwayTeam.setTeamName(currentObject.getString("awayTeamName"));
                    tmpGame.setAwayTeam(tmpAwayTeam);
                    tmpGame.setAwayTeamScore(Integer.valueOf(currentObject.getString("awayScore")));

                    tmpHomeTeam.setTeamId(currentObject.getString("homeTeamId"));
                    tmpHomeTeam.setTeamName(currentObject.getString("homeTeamName"));
                    tmpGame.setHomeTeam(tmpHomeTeam);
                    tmpGame.setHomeTeamScore(Integer.valueOf(currentObject.getString("homeScore")));

                    //Add to the database for offline use
                    dataBase.addGameData(tmpGame);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //After reading data from the internet go to standing fragment
        Fragment fragment_standing = new FragmentStanding();
        fragmentTransaction.add(R.id.mainActivity, fragment_standing);
        fragmentTransaction.commit();
    }


}
