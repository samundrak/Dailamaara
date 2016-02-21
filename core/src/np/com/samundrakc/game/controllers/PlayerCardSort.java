package np.com.samundrakc.game.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import np.com.samundrakc.game.anchors.Card;
import np.com.samundrakc.game.anchors.Const;
import np.com.samundrakc.game.anchors.Player;

/**
 * Created by samundra on 2/21/2016.
 */
public class PlayerCardSort {
    private Player player;

    public HashMap<Const.CARDS, ArrayList<Card>> getCards() {
        return cards;
    }

    public void setCards(HashMap<Const.CARDS, ArrayList<Card>> cards) {
        this.cards = cards;
    }

    private HashMap<Const.CARDS, ArrayList<Card>> cards = new HashMap<Const.CARDS, ArrayList<Card>>();

    public ArrayList<Card> getSortedCards() {
        return sortedCards;
    }

    public void setSortedCards(ArrayList<Card> sortedCards) {
        this.sortedCards = sortedCards;
    }

    private ArrayList<Card> sortedCards = new ArrayList<Card>();

    public PlayerCardSort(Player player) {
        this.player = player;
    }

    public void divideCards() {
        cards.put(Const.CARDS.CLUBS, new ArrayList<Card>());
        cards.put(Const.CARDS.DIAMONDS, new ArrayList<Card>());
        cards.put(Const.CARDS.SPADES, new ArrayList<Card>());
        cards.put(Const.CARDS.HEARTS, new ArrayList<Card>());
        for (Card c : this.player.getCards()) {
            switch (c.getCardType()) {
                case CLUBS:
                    cards.get(Const.CARDS.CLUBS).add(c);
                    break;
                case HEARTS:
                    cards.get(Const.CARDS.HEARTS).add(c);
                    break;
                case DIAMONDS:
                    cards.get(Const.CARDS.DIAMONDS).add(c);
                    break;
                case SPADES:
                    cards.get(Const.CARDS.SPADES).add(c);
                    break;
            }
        }
    }

    public void sortCards() {
        for (Const.CARDS c : Const.COLORS_NAME_TYPE) {
            Collections.sort(cards.get(c), new CardCompare());
        }
    }

    public void arrangeCards() {
        for (Const.CARDS c : Const.COLORS_NAME_TYPE) {
            for (Card card : cards.get(c)) {
                sortedCards.add(card);
            }
        }
    }
}
