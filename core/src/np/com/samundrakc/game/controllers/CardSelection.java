package np.com.samundrakc.game.controllers;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import java.util.ArrayList;
import java.util.Collections;
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

    public CardSelection(FormCtrl form) {
        this.form = form;
    }

    public boolean start(final int i, final Actor actor) {
        if (selectedCards.size() == Const.TOTAL_NUMBER_OF_PLAYERS) return true;
        final Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                ArrayList<Integer> duplicate = new ArrayList<Integer>();
                ArrayList<Integer> dupl = new ArrayList<Integer>();
                if (selectedCards.size() == Const.TOTAL_NUMBER_OF_PLAYERS) {
                    timer.cancel();
                    for (int i = 0; i < selectCardsNumber.size(); i++) {
                        int num = selectCardsNumber.get(i);
                        if (num == 1) {
                            selectCardsNumber.set(i, 14);
                        }
                    }
                    int min = selectCardsNumber.indexOf(Collections.min(selectCardsNumber));
                    ArrayList<CardSelectedPlayer> dups = new ArrayList<CardSelectedPlayer>();
                    for (CardSelectedPlayer csp : cardSelectedPlayers) {
//                        System.out.println(csp.getCard());
//                        System.out.println(csp.getCardNumber());
//                        System.out.println(csp.getIndex());
//                        System.out.println(csp.getPlayer().getName());
                        if (csp.getCardNumber() == selectCardsNumber.get(min)) {
                            dups.add(csp);
                            System.out.println(csp.getPlayer().getName() + " has lowest number - " + csp.getCardNumber());
                        }
                    }

                    if (dups.size() < 2) {
                        //Lowest card chooser has been choosen
                        System.out.println(dups.get(0).getPlayer().getName() + " will distribute cards");
                        form.getGame().turn = dups.get(0).getPlayer().getId();
                        selectedCards.get(min).addAction(Actions.sequence(Animation.sizeActionPlus(110,160,1f)));
                        return;
                    }


                    for (int i = 0; i < cardSelectedPlayers.size(); i++) {
                        form.getView().getStacks().getChildren().get(selectedCardsIndex.get(i)).setVisible(true);
                    }
                    form.getView().getStacksChild().clearChildren();
                    for (int i = 0; i < form.getView().getStacks().getChildren().size; i++) {
                        if (i < Const.TOTAL_NUMBER_OF_CARDS) {
                            form.getView().getStacks().getChildren().get(i).addAction(Animation.simpleAnimation(3, 3));
                        } else {
                            System.out.println("removed others");
                            form.getView().getStacks().getChildren().get(i).remove();
                        }
                    }

                    form.setIsCardSelected(true);
                    form.cardShareProcess();
                    return;
                }
                if (selectedCards.size() >= 1) {
                    int random = Utils.getRandom(Const.TOTAL_NUMBER_OF_CARDS, selectedCardsIndex);
                    Actor actor1 = form.getView().getStacks().getChildren().get(random);
                    cardSelectionProcess(actor1, random);
                }
            }
        }, 1000, 1000);
        cardSelectionProcess(actor, i);
        form.setIsCardSelected(true);
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
        if (selectedCards.size() < 2) {
            x = getXDiffToPin(50, actor.getX());
            y = getXDiffToPin(300, actor.getY()) - 200;
            image.addAction(Actions.sequence(Animation.moveBy(x, y, 0.5f), captionsLabelsOfSelectedCards(form.getGame().players.get(Game.mineId).getName(), image, x, y)));

        } else {
            x = getXDiffToPin(selectedCards.get(selectedCards.size() - 2).getX() + 160, actor.getX());
            y = getXDiffToPin(300, actor.getY()) - 200;
            image.addAction(Actions.sequence(Animation.moveBy(x, y, 0.5f), captionsLabelsOfSelectedCards(form.getGame().players.get(selectedCards.size() - 1).getName(), image, x, y)));

        }
        CardSelectedPlayer csp = new CardSelectedPlayer();
        csp.setCard(Game.cards.get(index));
        csp.setCardNumber(csp.getCard().getNumber());
        csp.setPlayer(form.getGame().players.get(selectedCardsIndex.size() - 1));
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
