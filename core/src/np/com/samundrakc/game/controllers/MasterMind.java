package np.com.samundrakc.game.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;


import np.com.samundrakc.game.anchors.Card;
import np.com.samundrakc.game.anchors.Const;
import np.com.samundrakc.game.anchors.Game;
import np.com.samundrakc.game.anchors.Player;
import np.com.samundrakc.game.anchors.Utils;
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
        cycle = player.getGame().getHistory().size() - 1;
        Collections.shuffle(player.getCards());
        query = new Query(player);

    }

    public Card getCard() {


        switch (player.getGame().getTHROWN()) {
            case 0:
                if (player.getGame().getHistory().size() < 1) {
                    firstThrower(true);
                } else {
                    player.getCardToThrow();
                }
                break;
            case 1:
                secondThrower();
                break;
            case 2:
                thridCardThrower();
                break;
            case 3:
                fourthCardThrower();
                break;
        }
        if (card == null) {
            card = player.getCardToThrow();
        }
        return card;
    }

    private void secondThrower() {

        //First checking if cards available
        Utils.log(player.getName(), "First checking if cards available");
        if (player.getSortedCards().get(player.getGame().getCARD_PLAYED()).size() > 0) {
            //if we have only on card of this type then send this
            if (player.getSortedCards().get(player.getGame().getCARD_PLAYED()).size() < 2) {
                setCard(player.getSortedCards().get(player.getGame().getCARD_PLAYED()).get(0));
                return;
            }

            //If We have card greater then thrown card then get it
            Utils.log(player.getName(), "If We have card greater then thrown card then get it");
            if (player.getSortedCards().get(player.getGame().getCARD_PLAYED()).size() == 2) {
                if (player.getSortedCards().get(player.getGame().getCARD_PLAYED()).get(0).getNumber() == 10) {
                    setCard(player.getSortedCards().get(player.getGame().getCARD_PLAYED()).get(1));
                    return;
                }
            }

            for (int i = player.getSortedCards().get(player.getGame().getCARD_PLAYED()).size() - 1; i > -1; i--) {
                Card c = player.getSortedCards().get(player.getGame().getCARD_PLAYED()).get(i);
                if (c.getNumber() != 10) {
                    setCard(c);
                    return;
                }
            }
            setCard(player.getSortedCards().get(player.getGame().getCARD_PLAYED()).get(0));
        } else {
            if (player.getSortedCards().get(player.getGame().getTURUP()).size() > 0) {
                //We have Turups
                setCard(player.getSortedCards().get(player.getGame().getTURUP()).get(0));
                return;
            } else {
                if (player.getCards().size() == 1) {
                    setCard(player.getCards().get(0));
                    return;
                }
                if (player.getCards().size() == 2) {
                    if (player.getCards().get(0).getNumber() == 10) {
                        setCard(player.getCards().get(1));
                        return;
                    }
                }
                for (int i = player.getCards().size() - 1; i > -1; i--) {
                    if (player.getCards().get(i).getNumber() < 10) {
                        setCard(player.getCards().get(i));
                        return;
                    }
                }
            }
            setCard(player.getCards().get(0));
        }
    }

    private void thridCardThrower() {
        if (player.getSortedCards().get(player.getGame().getCARD_PLAYED()).size() > 0) {
            //if we have current played cards
            Utils.log(player.getName(), "We have this type of cards");
            if (player.getFriend().getThrownCard().getNumber() == 10) {
                //if my friend has thrown 10
                Utils.log(player.getName(), "Friend thrown card 10");
                int biggerCards = 0;
                for (Card c : player.getSortedCards().get(player.getGame().getCARD_PLAYED())) {
                    if (c.getNumber() > 10) {
                        biggerCards++;
                    }
                }

                if (biggerCards == 4) {
                    Utils.log(player.getName(), "We have all bigger cards");
                    //We have all bigger cards then 10
                    for (int i = 0; i < player.getSortedCards().get(player.getGame().getCARD_PLAYED()).size(); i++) {
                        if (player.getSortedCards().get(player.getGame().getCARD_PLAYED()).get(i).getNumber() > 10) {
                            //send big card then 10
                            setCard(player.getSortedCards().get(player.getGame().getCARD_PLAYED()).get(i));
                            return;
                        }
                    }
                }

                Utils.log(player.getName(), "We dont have all biggers cards");
                //we dont have all biggers cards so throw any bigger Cards
                for (int i = player.getSortedCards().get(player.getGame().getCARD_PLAYED()).size() - 1; i > -1; i--) {
                    setCard(player.getSortedCards().get(player.getGame().getCARD_PLAYED()).get(i));
                    return;
                }
                setCard(player.getSortedCards().get(player.getGame().getCARD_PLAYED()).get(0));
                return;
            } else {
                //I have 10 so think about it
                if (query.isNumberAvailable(player.getGame().getCARD_PLAYED(), 10)) {
                    Utils.log(player.getName(), "I have card 10");
                    //my friend has throw card greater then 10
                    if (player.getFriend().getThrownCard().getNumber() > 10) {
                        if (player.getGame().getHistory().get(cycle).get(player.getGame().getHistory().get(cycle).size() - 1).getNumber() > player.getFriend().getThrownCard().getNumber()) {
                            //Opponent thrown biggers cards then mine friend
                            Utils.log(player.getName(), "Opponent thrown biggers cards then mine friend");
                            setCard(player.getSortedCards().get(player.getGame().getCARD_PLAYED()).get(0));
                            return;
                        } else {
                            //Opponent thrown small cards then mine friend
                            Utils.log(player.getName(), "Opponent thrown small cards then mine friend");
                            if (player.getFriend().getThrownCard().getNumber() == 14 && query.isNumberAvailable(player.getGame().getCARD_PLAYED(), 13)) {
                                //My friend have 14 and i have 13
                                Utils.log(player.getName(), "My friend have 14 and i have 13");
                                query.isNumberAvailable(player.getGame().getCARD_PLAYED(), 10);
                                setCard(query.getCard());
                                return;
                            }

                            if (player.getFriend().getThrownCard().getNumber() == 13 && query.isNumberAvailable(player.getGame().getCARD_PLAYED(), 14)) {
                                //My friend have 13 and i have 15
                                Utils.log(player.getName(), "My friend have 13 and i have 15");
                                query.isNumberAvailable(player.getGame().getCARD_PLAYED(), 10);
                                setCard(query.getCard());
                                return;
                            }
                            //I have all bigger Cards
                            Utils.log(player.getName(), "I have all bigger Cards");
                            if (query.isNumberAvailable(player.getGame().getCARD_PLAYED(), 14) && query.isNumberAvailable(player.getGame().getCARD_PLAYED(), 13)
                                    && query.isNumberAvailable(player.getGame().getCARD_PLAYED(), 12) && query.isNumberAvailable(player.getGame().getCARD_PLAYED(), 11)
                                    ) {
                                query.isNumberAvailable(player.getGame().getCARD_PLAYED(), 10);
                                setCard(query.getCard());
                                return;
                            }

                            //if my friend thrown 14
                            Utils.log(player.getName(), "if my friend thrown 14");
                            if (player.getFriend().getThrownCard().getNumber() == 14) {
                                query.isNumberAvailable(player.getGame().getCARD_PLAYED(), 10);
                                setCard(query.getCard());
                                return;
                            }
                        }
                    } else {
                        Utils.log(player.getName(), "my friend didnt thrown card greater then 10 but i have 10");
                        //my friend didnt thrown card greater then 10 but i have 10
                        setCard(player.getSortedCards().get(player.getGame().getCARD_PLAYED()).get(0));
                        return;
                    }
                } else {
                    //I dont have Card 10
                    Utils.log(player.getName(), "I dont have Card 10");
                    int num = player.getGame().getHistory().get(cycle).get(player.getGame().getHistory().get(cycle).size() - 1).getNumber();
                    query.isNumberAvailable(player.getGame().getCARD_PLAYED(), num + 1);
                    if (query.isNumberAvailable(player.getGame().getCARD_PLAYED(), num + 1)) {
                        setCard(query.getCard());
                        return;
                    }
                }
            }
        }
        Utils.log(player.getName(), "No way now for secondThrower action");
        secondThrower();
    }

    private void fourthCardThrower() {
        thridCardThrower();
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
            if (player.getGame().getTURUP() == card.getCardType()) {
                card = null;
            }
        }
        return card;
    }
}
