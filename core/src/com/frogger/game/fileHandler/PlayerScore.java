package com.frogger.game.fileHandler;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PlayerScore {
    private List<String> highScorePlayersName;
    private List<Integer> highScorePlayersScore;

    public PlayerScore(String novoPlayer, Integer novoPlayerScore)
    {
        super();
    }
    public PlayerScore()
    {
        super();
        this.highScorePlayersName = highScorePlayersName;
        this.highScorePlayersScore = highScorePlayersScore;
    }
    public List<String> getHighScorePlayersName()
    {
        return this.highScorePlayersName;
    }
    public void setHighScorePlayersName(List<String> highScorePlayersName)
    {
        this.highScorePlayersName = highScorePlayersName;
    }
    public List<Integer> getHighScorePlayersScore()
    {
        return this.highScorePlayersScore;
    }
    public void setHighScorePlayersScore(List<Integer> highScorePlayersScore)
    {
        this.highScorePlayersScore = highScorePlayersScore;
    }
}
