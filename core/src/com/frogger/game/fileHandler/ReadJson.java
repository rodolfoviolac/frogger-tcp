package com.frogger.game.fileHandler;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ReadJson {
    public static void main(String[] args) {
        get10BestPlayers();
    }

    public static Integer getBestScore() {

        int bestScore = 0;

        try
        {
            Gson gson = new Gson();
            BufferedReader br = new BufferedReader(new FileReader("froggerDB.json"));
            PlayerScore DB = gson.fromJson(br, PlayerScore.class);

            bestScore = DB.getHighScorePlayersScore().get(0);

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return bestScore;
    }

    public static String getBestPlayer() {

        String bestPlayer = "";

        try
        {
            Gson gson = new Gson();
            String path = new File("froggerDB.json").getAbsolutePath();
            BufferedReader br = new BufferedReader(new FileReader(path));
            PlayerScore DB = gson.fromJson(br, PlayerScore.class);

            bestPlayer = DB.getHighScorePlayersName().get(0);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return bestPlayer;
    }

    public static int[] get10BestScores() {
        int[] anArray = new int[10];

        try
        {
            Gson gson = new Gson();
            String path = new File("froggerDB.json").getAbsolutePath();
            BufferedReader br = new BufferedReader(new FileReader(path));
            PlayerScore DB = gson.fromJson(br, PlayerScore.class);

            for (int i=0; i<=9 && i<DB.getHighScorePlayersScore().size(); i++){
                anArray[i]=DB.getHighScorePlayersScore().get(i);
            }

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return anArray;
    }

    public static String[] get10BestPlayers() {
        String[] anArray = new String[10];

        try
        {
            Gson gson = new Gson();
            String path = new File("froggerDB.json").getAbsolutePath();



            BufferedReader br = new BufferedReader(new FileReader(path));
            PlayerScore DB = gson.fromJson(br, PlayerScore.class);

            for (int i=0; i<=9 && i<DB.getHighScorePlayersName().size(); i++){
                anArray[i]=DB.getHighScorePlayersName().get(i);
            }

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return anArray;
    }
}
