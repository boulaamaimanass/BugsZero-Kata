package com.adaptionsoft.games.uglytrivia;

public class Player {

    private final String playerName;
    private int purses;
    private boolean inPenaltyBox;

    Player(String playerName){
        this.playerName =  playerName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getPurses() {
        return purses;
    }

    public void setPurses(int purses) {
        this.purses = purses;
    }

    public boolean isInPenaltyBox() {
        return inPenaltyBox;
    }

    public void setInPenaltyBox(boolean inPenaltyBox) {
        this.inPenaltyBox = inPenaltyBox;
    }
}