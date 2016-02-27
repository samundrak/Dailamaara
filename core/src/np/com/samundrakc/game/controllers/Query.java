package np.com.samundrakc.game.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import np.com.samundrakc.game.anchors.Card;
import np.com.samundrakc.game.anchors.Const;
import np.com.samundrakc.game.anchors.Player;

/**
 * Created by samundra on 2/23/2016.
 */
public class Query {
    private Player player;
    private Card card = null;

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public Query(Player player) {
        this.player = player;
    }

    public boolean greaterThen(int number, Const.CARDS type) {
        for (Card c : player.getSortedCards().get(type)) {
            if (number > c.getNumber()) {
                setCard(c);
                return true;
            }
        }
        return false;
    }

    public boolean lessThen(int number, Const.CARDS type) {
        for (Card c : player.getSortedCards().get(type)) {
            if (number < c.getNumber()) {
                setCard(c);
                return true;
            }
        }
        return false;
    }

    public boolean inRange(int from, int to, Const.CARDS type) {
        for (Card c : player.getSortedCards().get(type)) {
            if (from < c.getNumber() &&  c.getNumber() < to) {
                setCard(c);
                return true;
            }
        }
        return false;
    }

    public boolean max(Const.CARDS type) {
        if (player.getSortedCards().get(type).size() > 0) {
            //Return the last index which always bigger
            setCard(player.getSortedCards().get(type).get(player.getSortedCards().get(type).size() - 1));
            return true;
        }
        return false;
    }

    public boolean min(Const.CARDS type) {
        if (player.getSortedCards().get(type).size() > 0) {
            //Return the last index which always smaller
            setCard(player.getSortedCards().get(type).get(0));
            return true;
        }
        return false;
    }


    public boolean random(Const.CARDS type) {
        if (player.getCards().size() < 1) return false;
        if (type != null) {
            if (player.getSortedCards().get(type).size() > 0) {
                setCard(player.getSortedCards().get(type).get(new Random().nextInt(player.getSortedCards().get(type).size())));
                return true;
            } else {
                type = null;
            }
        }
        if (type == null) {
            setCard(player.getCards().get(new Random().nextInt(player.getCards().size())));
            return true;
        }
        return false;
    }

    public Const.CARDS getRandomCardsType() {
        return Const.COLORS_NAME_TYPE[new Random().nextInt(Const.COLORS_NAME_TYPE.length)];
    }

    public boolean isNumberAvailable(Const.CARDS type, int number) {
        if (type != null) {
            for (Card c : player.getSortedCards().get(type)) {
                if (c.getNumber() == number) {
                    setCard(c);
                    return true;
                }
            }
        }

        for (Card c : player.getCards()) {
            if (c.getNumber() == number) {
                setCard(c);
                return true;
            }
        }
        return false;
    }

    public Const.CARDS getHighestCardType() {
        int size = 0;
        Const.CARDS cardType = null;
        for (Const.CARDS type : Const.COLORS_NAME_TYPE) {
            if (player.getSortedCards().get(type).size() > size) {
                size = player.getSortedCards().get(type).size();
                cardType = type;
            }
        }
        return cardType;
    }


    public Const.CARDS getLowestCardType() {
        int size = Const.TOTAL_NUMBER_OF_CARDS + 1;
        Const.CARDS cardType = null;
        for (Const.CARDS type : Const.COLORS_NAME_TYPE) {
            if (player.getSortedCards().get(type).size() < size) {
                size = player.getSortedCards().get(type).size();
                cardType = type;
            }
        }
        return cardType;
    }

    public boolean isThisCardTypeAvailable(Const.CARDS type) {
        if (player.getSortedCards().get(type).size() > 0)
            return true;
        else
            return false;
    }
}
