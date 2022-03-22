package practica_1_Agustin;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Player{

    private String playerName;
    private ArrayList<String> teams;
    private ArrayList<String> positions;
    private int score;

    public Player(String playerName, String teams, String positions, int score){

        this.playerName = playerName;
        this.teams = new ArrayList<String>();
        this.teams.add(teams);
        this.positions = new ArrayList<String>();
        this.positions.add(positions);
        this.score = score;
    }

    public void add(String teams, String positions, int score) {
        if(score <= 0) return;
        this.teams.add(teams);
        this.positions.add(positions);
        this.score += score;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public ArrayList<String> getPositions() {
        Set<String> hashSet = new HashSet<String>(positions);
        positions.clear();
        positions.addAll(hashSet);
        return positions;
    }

    public void setPositions(ArrayList<String> positions) {
        this.positions = positions;
    }

    public ArrayList<String> getTeams() {
        Set<String> hashSet = new HashSet<String>(teams);
        teams.clear();
        teams.addAll(hashSet);
        return teams;
    }

    public void setTeams(ArrayList<String> teams) {
        this.teams = teams;
    }

    public int getScore() {
        return this.score / this.teams.size();
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "[Name = " + getPlayerName() + " | Score = " + getScore() + " | " + "Teams = "  + getTeams() + " | Positions = " + getPositions() + "]";
    }
}
