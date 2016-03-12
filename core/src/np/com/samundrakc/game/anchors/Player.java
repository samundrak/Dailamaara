/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package np.com.samundrakc.game.anchors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Timer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import np.com.samundrakc.game.controllers.History;
import np.com.samundrakc.game.controllers.MasterMind;
import np.com.samundrakc.game.controllers.Sound;
import np.com.samundrakc.game.misc.Animation;
import np.com.samundrakc.game.screens.DailaMaara;

/**
 * @author samundra
 */
public class Player {

    private String name = null;
    private Group group = null;
    private int id;
    private ArrayList<Card> cards = new ArrayList();
    private Game game;
    private DailaMaara view;
    private Card thrownCard = null;

    public Card getThrownCard() {
        return thrownCard;
    }

    public void setThrownCard(Card thrownCard) {
        this.thrownCard = thrownCard;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public DailaMaara getView() {
        return view;
    }

    public void setView(DailaMaara view) {
        this.view = view;
    }

    public float[] getCardToThrowLocations() {
        return cardToThrowLocations;
    }

    public void setCardToThrowLocations(float[] cardToThrowLocations) {
        this.cardToThrowLocations = cardToThrowLocations;
    }

    private float[] cardToThrowLocations = new float[2];
    private ArrayList<Actor> backCards = new ArrayList<Actor>();
    private HashMap<Const.CARDS, ArrayList<Card>> sortedCards = new HashMap<Const.CARDS, ArrayList<Card>>();

    public HashMap<Const.CARDS, ArrayList<Card>> getSortedCards() {
        return sortedCards;
    }

    public void setSortedCards(HashMap<Const.CARDS, ArrayList<Card>> sortedCards) {
        this.sortedCards = sortedCards;
    }

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

    public Const.DIRECTION getDIRECTION() {
        return DIRECTION;
    }

    public void setDIRECTION(Const.DIRECTION DIRECTION) {
        this.DIRECTION = DIRECTION;
    }

    private Const.DIRECTION DIRECTION;
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

    public Timer active = new Timer();

    public void play() {
        final Player currentPlayer = this;
        active.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                if (getGame().getSTATE() == Const.STATE.GAME_OVER) {
                    active.clear();
                    System.out.println(currentPlayer.getName() + " : Player game over state");
                    return;
                }
                if (!getGame().isSTARTED()) return;
                if (game.getPLAY_TURN() == null) return;
                if (getGame().getSTATE() == Const.STATE.WAIT || getGame().getSTATE() == Const.STATE.PAUSE || getGame().getSTATE() == Const.STATE.STOP) {
                    return;
                }
                if (getGame().getPLAY_TURN().getId() == getId()) {
                    if (getGame().getHistory().size() < 1) {
                        getGame().getHistory().add(new ArrayList<Card>());
                        getGame().getItihaas().add(new History());
                    }
                    //Do the Playing Task Here
                    final Card c = removeCardFromMyIndex(new MasterMind(currentPlayer).getCard());
                    if (c == null) {
                        System.out.println("Card not found");
                        getGame().setSTATE(Const.STATE.GAME_OVER);
                        active.clear();
                        return;
                    }
                    Sound.getInstance().play(Sound.AUDIO.SELECTED);
                    setThrownCard(c);
                    getGame().selectPlayOfTurup(Player.this, c);
                    c.getActor().setPosition(getActor().getX(), getActor().getY());
                    c.getActor().addAction(Actions.sequence(
                            Animation.moveBy(getCardToThrowLocations()[0] - getActor().getX(), getCardToThrowLocations()[1] - getActor().getY(), 0.3f), new RunnableAction() {
                                @Override
                                public void run() {
                                    getGame().getHistory().get(getGame().getHistory().size() - 1).add(c);
                                    getGame().getItihaas().get(getGame().getItihaas().size() - 1).addPlayerWithCards(new History.PlayerHistory(c, Player.this));
                                    //End
                                    doExtraStuff();
                                    //Choose next player to be played
                                    getGame().chooseNextPlayerToBePlayed(Player.this);
                                }
                            }));
                    c.getActor().setSize(100, 120);
                    game.getGAME_STAGE().addActor(c.getActor());

                }
            }
        }, 0, 1);
        active.start();
    }

    public void stopPlay() {
        if (!active.isEmpty()) {
            active.clear();
        }
    }

    public void updateThrown() {
        getGame().setTHROWN(getGame().getTHROWN() + 1);
    }


    public void doExtraStuff() {
        getGroup().setThrown(getGroup().getThrown() + 1);
        getView().getGroupThrownStatusLabel().get(getGroup().getName()).setText(getGroup().getThrown() + "");
        updateThrown();
        getGame().updateThrownCardsStacks(this);
    }

    public Card getCardToThrow() {
        //This is just Random
        System.out.println(getName() + " card size is " + cards.size());
        if (this.cards.size() > 0) {
            int index = this.cards.size() - 1;
            Card c = this.cards.get(index);
            return c;
        }
        return null;
    }

    public Card removeCardFromMyIndex(Card c) {
        //Removing Cards From my locations
        //Providing Cards to Game
        if (c == null) return c;
        int indexOf = cards.indexOf(c);
        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i).getCardType() == c.getCardType() && cards.get(i).getNumber() == c.getNumber()) {
                indexOf = i;
                break;
            }
        }
        for (Card cards : sortedCards.get(c.getCardType())) {
            if (cards.getNumber() == c.getNumber()) {
                sortedCards.get(c.getCardType()).remove(c);
                break;
            }
        }
        if (this.getBackCards().size() > 0) {
            int index = this.getBackCards().size() - 1;
            Actor a = this.getBackCards().get(index);
            a.setVisible(false);
//            a.clear();
//            a.remove();
            this.getBackCards().remove(index);
        }
        this.cards.remove(indexOf);
        return c;
    }


}
