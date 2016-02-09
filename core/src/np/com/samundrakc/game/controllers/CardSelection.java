package np.com.samundrakc.game.controllers;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import np.com.samundrakc.game.anchors.Card;
import np.com.samundrakc.game.anchors.Const;
import np.com.samundrakc.game.anchors.Game;
import np.com.samundrakc.game.anchors.Player;
import np.com.samundrakc.game.misc.Animation;
import np.com.samundrakc.game.misc.Context;
import np.com.samundrakc.game.misc.Utils;

/**
 * Created by samundra on 2/7/2016.
 */
public class CardSelection {

    private final FormCtrl form;
    private ArrayList<Actor> selectedCards = new ArrayList<Actor>();
    static ArrayList<CardSelectedPlayer> dups = new ArrayList<CardSelectedPlayer>();
    private boolean playerHasPlayed = false;

    public CardSelection(FormCtrl form) {
        this.form = form;
    }

    public boolean start(final int i, final Actor actor) {
        if (selectedCards.size() == Const.TOTAL_NUMBER_OF_PLAYERS) return true;
        final Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                System.err.println(dups.size());

                if (selectedCards.size() == Const.TOTAL_NUMBER_OF_PLAYERS) {
                    dups.clear();
                    timer.cancel();
                    for (int i = 0; i < selectCardsNumber.size(); i++) {
                        int num = selectCardsNumber.get(i);
                        if (num == 1) {
                            selectCardsNumber.set(i, 14);
                        }
                    }
                    int min = selectCardsNumber.indexOf(Collections.min(selectCardsNumber));

                    for (CardSelectedPlayer csp : cardSelectedPlayers) {
                        if (csp.getCardNumber() == selectCardsNumber.get(min)) {
                            System.out.println("added" + csp.getPlayer().getName());
                            dups.add(csp);
                            if (csp.getPlayer().getId() == Game.mineId) {
                                playerHasPlayed = false;
                                form.setIsCardSelected(false);
                            }
                            System.out.println(csp.getPlayer().getName() + " has lowest number - " + csp.getCardNumber());
                        }
                    }
                    if (dups.size() < 2) {
                        //Lowest card chooser has been choosen
                        System.out.println(dups.get(0).getPlayer().getName() + " will distribute cards");
                        form.getGame().turn = dups.get(0).getPlayer().getId();
                        selectedCards.get(min).addAction(Actions.sequence(Animation.sizeActionPlus(110, 160, 1f)));
                        return;
                    }

                    //Card drawer has to be choosen as two or many player choosed same cards
                    Const.TOTAL_NUMBER_OF_PLAYERS = dups.size();
                    for (int i = 0; i < cardSelectedPlayers.size(); i++) {
                        form.getView().getStacks().getChildren().get(selectedCardsIndex.get(i)).setVisible(true);
                    }
                    form.getView().getStacksChild().clearChildren();
                    for (int i = 0; i < form.getView().getStacks().getChildren().size; i++) {
                        if (i < Const.TOTAL_NUMBER_OF_CARDS) {
                            form.getView().getStacks().getChildren().get(i).addAction(Animation.simpleAnimation(3, 3));
                        } else {
                            form.getView().getStacks().getChildren().get(i).remove();
                        }
                    }

                    selectedCards.clear();
                    selectCardsNumber.clear();
                    selectedCardsIndex.clear();
                    cardSelectedPlayers.clear();
                    form.getView().getStacks().setTouchable(Touchable.disabled);
                    form.cardShareProcess(new FormCtrl.Callback() {
                        @Override
                        public void run() {
                            if (playerHasPlayed) {
                                form.setIsCardSelected(true);
                                start(i, actor);
                            }
                            form.getView().getStacks().setTouchable(Touchable.enabled);
                        }
                    });
                    return;
                }
                if (playerHasPlayed) {
                    int random = Utils.getRandom(Const.TOTAL_NUMBER_OF_CARDS, selectedCardsIndex);
//                    if (selectedCards.size() == 2) {
//                        random = 14; //Utils.getRandom(Const.TOTAL_NUMBER_OF_CARDS, selectedCardsIndex);
//                    }
//                    if (selectedCards.size() == 3) {
//                        random = 1; //Utils.getRandom(Const.TOTAL_NUMBER_OF_CARDS, selectedCardsIndex);
//                    }
//                    if (selectedCards.size() == 1) {
//                        random = 27; //Utils.getRandom(Const.TOTAL_NUMBER_OF_CARDS, selectedCardsIndex);
//                    }
                    Actor computerSelectedCard = form.getView().getStacks().getChildren().get(random);
                    cardSelectionProcess(computerSelectedCard, random);
                }
            }
        }, 1000, 1000);
        if (!playerHasPlayed) {
            cardSelectionProcess(actor, i);
            form.setIsCardSelected(true);
            playerHasPlayed = true;
        }
        return true;
    }

    private ArrayList<TextButton> selectedCardsLabel = new ArrayList<TextButton>();

    private RunnableAction captionsLabelsOfSelectedCards(final String name, final Actor image, final float x, final float y) {
        return new RunnableAction() {
            @Override
            public void run() {
                TextButton label = new TextButton(name, Context.skin);
                label.setPosition(image.getX() + ((image.getWidth() / 2) - (label.getWidth() / 2)), 60);
                selectedCardsLabel.add(label);
                form.getView().getStacksChild().addActor(label);
            }
        };
    }

    private ArrayList<Integer> selectedCardsIndex = new ArrayList<Integer>();
    private ArrayList<Integer> selectCardsNumber = new ArrayList<Integer>();
    private ArrayList<CardSelectedPlayer> cardSelectedPlayers = new ArrayList<CardSelectedPlayer>();

    private void cardSelectionProcess(Actor actor, int index) {
        actor.setVisible(false);
        final Image image = Game.cards.get(index).getActor(actor.getX(), actor.getY());
        image.addAction(Actions.sequence(Animation.sizeActionPlus(150, 200, 0.5f)));
        selectedCards.add(image);
        selectCardsNumber.add(Game.cards.get(index).getNumber());
        selectedCardsIndex.add(index);
        float x, y;
        if (selectedCards.size() < 2 && !playerHasPlayed) {
            x = getXDiffToPin(50, actor.getX());
            y = getXDiffToPin(300, actor.getY()) - 200;
            image.addAction(Actions.sequence(Animation.moveBy(x, y, 0.5f), captionsLabelsOfSelectedCards(form.getGame().players.get(Game.mineId).getName(), image, x, y)));
        } else {
            String name;
            if (selectedCards.size() < 2) {
                x = getXDiffToPin(50, actor.getX());
                name = dups.get(selectedCards.size() - 1).getPlayer().getName();
            } else {
                x = getXDiffToPin(selectedCards.get(selectedCards.size() - 2).getX() + 160, actor.getX());
                name = form.getGame().players.get(selectedCards.size() - 1).getName();
                System.out.println(dups.size());
                if (dups.size() > 0) {
                    name = dups.get(selectedCards.size() - 1).getPlayer().getName();
                    System.out.println(name + "  dups name");
                }
                System.out.println(name);
            }
            y = getXDiffToPin(300, actor.getY()) - 200;
            image.addAction(Actions.sequence(Animation.moveBy(x, y, 0.5f), captionsLabelsOfSelectedCards(name, image, x, y)));

        }
        CardSelectedPlayer csp = new CardSelectedPlayer();
        csp.setCard(Game.cards.get(index));
        csp.setCardNumber(csp.getCard().getNumber());
        if (dups.size() > 0) {
            csp.setPlayer(dups.get(selectedCards.size() - 1).getPlayer());
        } else {
            csp.setPlayer(form.getGame().players.get(selectedCards.size() - 1));
        }
        csp.setIndex(index);
        cardSelectedPlayers.add(csp);
        form.getView().getStacksChild().addActor(image);

    }

    private float getXDiffToPin(float xA, float xB) {
        if (xB > xA) {
            return -(xB - xA);
        } else {
            return (xA - xB);
        }
    }

    private class CardSelectedPlayer {
        Player player;
        int index;
        int cardNumber;
        Card card;

        public CardSelectedPlayer() {
        }

        public CardSelectedPlayer(Player player, int index, int card, Card card1) {
            this.player = player;
            this.index = index;
            this.cardNumber = card;
            this.card = card1;
        }

        public Player getPlayer() {
            return player;
        }

        public void setPlayer(Player player) {
            this.player = player;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public int getCardNumber() {
            return cardNumber;
        }

        public void setCardNumber(int cardNumber) {
            this.cardNumber = cardNumber;
        }

        public Card getCard() {
            return card;
        }

        public void setCard(Card card) {
            this.card = card;
        }
    }
}
