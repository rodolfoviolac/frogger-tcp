package com.frogger.game.fileHandler;

import com.google.gson.Gson;

import java.io.*;
import java.util.*;


public class WriteJson {

    public static void main(String[] args) {
        addPlayerToDB("jaunzin", 199);

    }

    public static void addPlayerToDB(String novoPlayer, Integer novoPlayerScore) {
        String pathDB = new File("froggerDB.json").getAbsolutePath();
        File f = new File(pathDB);
        try
        {
            if(f.exists()) {
                Gson gson = new Gson();
                String path = new File("froggerDB.json").getAbsolutePath();
                BufferedReader br = new BufferedReader(new FileReader(path));
                PlayerScore DB = gson.fromJson(br, PlayerScore.class);


                DB.getHighScorePlayersName().add(novoPlayer);
                DB.getHighScorePlayersScore().add(novoPlayerScore);

                multiSort(DB.getHighScorePlayersScore(), DB.getHighScorePlayersName());

                Collections.sort(DB.getHighScorePlayersScore());

                Collections.reverse(DB.getHighScorePlayersScore());
                Collections.reverse(DB.getHighScorePlayersName());


                Gson gsonWriter = new Gson();

                String jsonString = gsonWriter.toJson(DB);
                FileWriter fileWriter = new FileWriter(path);
                fileWriter.write(jsonString);
                fileWriter.close();
            } else {


                PlayerScore firstPlayer = new PlayerScore();

                List playerName = new ArrayList();
                List playerScore = new ArrayList();

                playerName.add(novoPlayer);
                playerScore.add(novoPlayerScore);


                firstPlayer.setHighScorePlayersName(playerName);
                firstPlayer.setHighScorePlayersScore(playerScore);


                Gson gsonFirstWriter = new Gson();

                String jsonString = gsonFirstWriter.toJson(firstPlayer);
                String path = new File("froggerDB.json").getAbsolutePath();
                FileWriter fileWriter = new FileWriter(path);
                fileWriter.write(jsonString);
                fileWriter.close();


            }

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    public static <T extends Comparable<T>> void multiSort(
            final List<T> key, List<?>... lists){
        // Create a List of indices
        List<Integer> indices = new ArrayList<Integer>();
        for(int i = 0; i < key.size(); i++) {
            indices.add(i);
        }

        // Sort the indices list based on the key
        Collections.sort(indices, new Comparator<Integer>() {
            @Override public int compare(Integer i, Integer j) {
                return key.get(i).compareTo(key.get(j));
            }
        });

        // Create a mapping that allows sorting of the List by N swaps.
        // Only swaps can be used since we do not know the type of the lists
        Map<Integer,Integer> swapMap = new HashMap<Integer, Integer>(indices.size());
        List<Integer> swapFrom = new ArrayList<Integer>(indices.size()),
                swapTo   = new ArrayList<Integer>(indices.size());
        for (int i = 0; i < key.size(); i++) {
            int k = indices.get(i);
            while (i != k && swapMap.containsKey(k)) {
                k = swapMap.get(k);
            }

            swapFrom.add(i);
            swapTo.add(k);
            swapMap.put(i, k);
        }

        // use the swap order to sort each list by swapping elements
        for (List<?> list : lists)
            for (int i = 0; i < list.size(); i++)
                Collections.swap(list, swapFrom.get(i), swapTo.get(i));
    }



}
