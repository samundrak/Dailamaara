/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package np.com.samundrakc.game.anchors;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author samundra
 */
public class Group {

    public String name;
    public ArrayList<Player> playerList;
    public final int limit;
    private int won = 0;
    private int thrown = 0;
    private int tens = 0;

    public int getThrown() {
        return thrown;
    }

    public void setThrown(int thrown) {
        this.thrown = thrown;
    }

    public int getWon() {
        return won;
    }

    public void setWon(int won) {
        this.won = won;
    }

    public Group(String name) {
        this.name = name;
        this.playerList = new ArrayList();
        this.limit = Const.TOTAL_PLAYER_IN_GAME;
    }

    public void addPlayer(Player p) {
        this.playerList.add(p);
    }

    public void removePlayer(Player p) {
        this.playerList.remove(p);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Player> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(ArrayList<Player> playerList) {
        if (this.playerList.size() <= this.limit) {
            this.playerList = playerList;
        } else {
            System.out.println("Limit exceeds. No more friend then " + this.limit + " can be added");
        }
    }

    private int coat = 0;

    public int getCoat() {
        return this.coat;
    }

    public void setCoat(int coat) {
        this.coat = coat;
    }

    int hands = 0;

    public int getHands() {
        return hands;
    }

    public void setHands(int hands) {
        this.hands = hands;
    }

    public int getTens() {
        return tens;
    }

    public void setTens(int tens) {
        this.tens = tens;
    }
}
