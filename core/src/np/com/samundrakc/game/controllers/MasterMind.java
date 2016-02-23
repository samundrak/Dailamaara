package np.com.samundrakc.game.controllers;

import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;


import np.com.samundrakc.game.anchors.Card;
import np.com.samundrakc.game.anchors.Const;
import np.com.samundrakc.game.anchors.Game;
import np.com.samundrakc.game.anchors.Player;
import np.com.samundrakc.game.misc.Animation;
import np.com.samundrakc.game.misc.Context;

/**
 * Created by samundra on 2/21/2016.
 */
public class MasterMind {
    private final Query query;
    private Player player;
    private int cycle = 0;
    private int[] cards = new int[]{1, 13, 12, 11, 9, 8, 7, 6, 5, 4, 3, 2, 1};
    private Card card = null;

    public void setCard(Card card) {
        this.card = card;
    }

    public MasterMind(Player player) {
        this.player = player;
        cycle = Game.history.size() - 1;
        Collections.shuffle(player.getCards());
        query = new Query(player);

    }

    public Card getCard() {


        switch (Game.THROWN) {
            case 0:
                if (Game.history.size() < 1) {
                    firstThrower(true);
                } else {
                    player.getCardToThrow();
                }
                break;
            case 1:
                secondThrower();
                break;
            case 2:
                player.getCardToThrow();
                break;
            case 3:
                player.getCardToThrow();
                break;
        }
        if (card == null) {
            card = player.getCardToThrow();
        }
        return card;
    }

    private void secondThrower() {
        //we will check if this type of cards exists with us
        if (query.isThisCardTypeAvailable(Game.CARD_PLAYED)) {
            //ok we have card greater the first player
            if (query.greaterThen(Game.history.get(cycle).get(0).getNumber(), Game.CARD_PLAYED)) {
                setCard(query.getCard());
                return;
            } else {
                //we dnt have bigger cards so let get minimum value
                if (query.min(Game.CARD_PLAYED)) {
                    //is the minimum value we got is 10
                    if (query.getCard().getNumber() == 10) {
                        //if value is 10 and we have only 1 card then throw it
                        if (player.getSortedCards().get(Game.CARD_PLAYED).size() == 1) {
                            setCard(query.getCard());
                            return;
                        } else {
                            //get cards less then 10
                            if (query.lessThen(10, Game.CARD_PLAYED)) {
                                setCard(query.getCard());
                                return;
                            } else {
                                //no less then 10 then get greater 10
                                if (query.greaterThen(10, Game.CARD_PLAYED)) {
                                    setCard(query.getCard());
                                    return;
                                }
                            }
                        }
                    }
                    setCard(query.getCard());
                    return;
                } else {
                    query.random(Game.CARD_PLAYED);
                    setCard(query.getCard());
                    return;
                }
            }
        } else {
            //We can use Turup Here
            if (query.isThisCardTypeAvailable(Game.TURUP)) {
                query.min(Game.TURUP);
                setCard(query.getCard());
                return;
            } else {
                if (query.min(query.getLowestCardType())) {
                    if (query.getCard().getNumber() == 10) {
                        if (query.lessThen(10, query.getLowestCardType())) {
                            setCard(query.getCard());
                            return;
                        }
                    }
                }

                query.max(query.getHighestCardType());
                setCard(query.getCard());
                return;
                
            }
        }
    }

    private void cardLooper(Const.CARDS type, boolean withTurup) {
        for (int i : cards) {
            card = getThisCard(i, type, withTurup);
            if (card != null) break;
        }
    }

    private Card firstThrower(boolean veryFirstTime) {
        if (veryFirstTime) {
            cardLooper(null, false);
        }
        if (card == null) {
            System.out.println("Card is null");
        } else {
            System.out.println("Big Card is" + card.getNumber());
            System.out.println("Big Card type is" + card.getCardType());
        }
        return card;
    }


    /**
     * Will provide Card from the players Stacks if Card Type is provided then it will give matching card
     * if turup is enabled then it will provide card which is turup if not then turup is not provided
     *
     * @param cardToGet
     * @param typeOfCard
     * @param getWithTurup
     * @return
     */
    private Card getThisCard(int cardToGet, Const.CARDS typeOfCard, boolean getWithTurup) {
        HashMap<Const.CARDS, ArrayList<Card>> mycards = player.getSortedCards();
        Card card = null;
        for (int i = 0; i < Const.TOTAL_NUMBER_OF_PLAYERS; i++) {
            for (Card c : mycards.get(Const.COLORS_NAME_TYPE[i])) {
                if (typeOfCard == null) {
                    if (c.getNumber() == cardToGet) {
                        card = c;
                    }
                } else {
                    if (typeOfCard == c.getCardType() && c.getNumber() == cardToGet) {
                        card = c;
                    }
                }
            }
        }
        if (!getWithTurup) {
            if (Game.TURUP == card.getCardType()) {
                card = null;
            }
        }
        return card;
    }
}
