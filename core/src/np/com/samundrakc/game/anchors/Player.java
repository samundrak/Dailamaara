/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package np.com.samundrakc.game.anchors;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.util.ArrayList;

/**
 * @author samundra
 */
public class Player {

    private String name = null;
    private Group group = null;
    private int id;
    private ArrayList<Card> cards = null;
    private ArrayList<Actor> backCards = new ArrayList<Actor>();

    public ArrayList<Actor> getBackCards() {
        return backCards;
    }

    public void setBackCards(Actor backCards) {
        this.backCards.add(backCards);
    }

    public float getLocationX() {
        return locationX;
    }

    public void setLocationX(float locationX) {
        this.locationX = locationX;
    }

    public float getLocationY() {
        return locationY;
    }

    public void setLocationY(float locationY) {
        this.locationY = locationY;
    }

    public Const.DIRECTION DIRECTION;
    private float locationX = 0;
    private float locationY = 0;

    public Actor getActor() {
        return actor;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }

    private Actor actor;

    public Player getFriend() {
        return friend;
    }

    private ArrayList<Actor> cardsActor = new ArrayList<Actor>();

    public void setCardsActor(Actor a) {
        this.cardsActor.add(a);
    }

    private float myCardsPositionX = 0;
    private float myCardsPositionY = 0;

    public float getMyCardsPositionX() {
        return myCardsPositionX;
    }

    public void setMyCardsPositionX(float myCardsPositionX) {
        this.myCardsPositionX = myCardsPositionX;
    }

    public float getMyCardsPositionY() {
        return myCardsPositionY;
    }

    public void setMyCardsPositionY(float myCardsPositionY) {
        this.myCardsPositionY = myCardsPositionY;
    }

    public void setMyCardPosition(float x, float y) {
        this.myCardsPositionX = x;
        this.myCardsPositionY = y;
    }

    public ArrayList<Actor> getCardsActor() {
        return this.cardsActor;
    }

    public void setFriend(Player friend) {
        this.friend = friend;
    }

    private Player friend;

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
