/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package np.com.samundrakc.game.anchors;

import java.util.ArrayList;

/**
 *
 * @author samundra
 */
public class Player {

    private String name = null;
    private Group group = null;
    private int id;
    private ArrayList<Card> cards = null;

    public Player(String name) {
        this.name = name;
        this.id = -1;
        cards = new ArrayList();
    }

    public void addCards(Card c) {
        cards.add(c);
    }

    public void removeCards(Card c) {
        cards.remove(c);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

}
