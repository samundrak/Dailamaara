package np.com.samundrakc.game.controllers;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.utils.Timer;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import java.util.logging.Logger;

import np.com.samundrakc.game.anchors.Card;
import np.com.samundrakc.game.anchors.Const;
import np.com.samundrakc.game.anchors.Game;
import np.com.samundrakc.game.anchors.Player;
import np.com.samundrakc.game.misc.Animation;
import np.com.samundrakc.game.screens.DailaMaara;

/**
 * Created by samundra on 2/10/2016.
 */
public class CardDistribution {
    public DailaMaara getGame() {
        return game;
    }

    private final DailaMaara game;
    private boolean cardShareDone = false;

    public CardDistribution(DailaMaara game) {
        this.game = game;
    }


    public CardDistribution shareProcessFirst() {
        game.getCards().setZIndex(1);
        int count = 0;
        for (int i = 0; i < 52; i++) {
            Card c = game.getMainGame().getCards().get(i);
            if (game.getSortPlayer().get(count).getCards().size() >= 13) {

                count++;
            }
            game.getSortPlayer().get(count).addCards(c);
        }
        cardShareDone = true;
        return this;
    }

    //0,19,5
    public CardDistribution startShare(int from, final int numberOfCardsToThrow, final int sum) {
        final int[] cardCounter = {from};
        final int[] player = {0};
        final Timer timer = new Timer();
        final boolean[] proceed = {true};
        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                if (game.getMainGame().getTALK_TURN().getCardsActor().size() >= 5) {
                    if (game.getMainGame().getTURUP() == null) {
                        return;
                    }
                }
                if (!cardShareDone) return;
                if (!proceed[0]) return;
                proceed[0] = false;
                if (cardCounter[0] == numberOfCardsToThrow) {
                    game.getMainGame().setTHROWN_CARDS(numberOfCardsToThrow);
                }


                final Actor cards = game.getCards().getChildren().get(cardCounter[0]);
                cards.clearActions();
                final Player p = game.getSortPlayer().get(player[0]);
                final Player turn = game.getMainGame().getPlayers().get(game.getMainGame().getTurn());
                p.setBackCards(cards);
                p.setCardsActor(game.getMainGame().getCards().get(cardCounter[0]).getActor());
                float x = p.getActor().getX() - (game.getCards().getX());
                float y = p.getActor().getY() - (game.getCards().getY());
                float[] cords;
                switch (turn.DIRECTION) {
                    case EAST:
                        cords = ifCardForEast(p, cards, x, y);
                        x = cords[0];
                        y = cords[1];
                        break;
                    case WEST:
                        cords = ifCardForWest(p, cards, x, y);
                        x = cords[0];
                        y = cords[1];
                        break;
                    case NORTH:
                        cords = ifCardFromNorth(p, cards, x, y);
                        x = cords[0];
                        y = cords[1];
                        break;
                    case SOUTH:
                        cords = ifCardFromSouth(p, cards, x, y);
                        x = cords[0];
                        y = cords[1];
                        break;
                }
                Sound.getInstance().play(Sound.AUDIO.CARD_SHARE);
                cards.addAction(Actions.sequence(Animation.moveBy(x, y, Const.CARD_DISTRUBUTION_SECONDS_PER_PLAYER), new RunnableAction() {
                    @Override
                    public void run() {
                        if (p.DIRECTION == Const.DIRECTION.WEST) {
                            int index = 0;
                            if (p.getCardsActor().size() < 2) {
                                p.getCards().get(index).getActor().clearActions();
                                p.getCards().get(index).getActor().clearListeners();
                                p.getCards().get(index).getActor().setVisible(true);
                                p.getCards().get(index).getActor().addAction(Animation.sizeActionPlusWithAnime(70, 100, 0.2f));
                                p.getCards().get(index).getActor().setPosition(0, 0);
                                p.getCards().get(index).getActor().addListener(new PlayCardDragListener(p.getCards().get(index), p));
                                p.getCards().get(index).getActor().addListener(new PlayerCardCtrl(p.getCards().get(0), CardDistribution.this, game.getMainGame()));
                                game.getStage().addActor(p.getCards().get(index).getActor());
                            } else {
                                index = p.getCardsActor().size() - 1;
                                p.getCards().get(index).getActor().setVisible(true);
                                p.getCards().get(index).getActor().clearActions();
                                p.getCards().get(index).getActor().clearListeners();
                                p.getCards().get(index).getActor().addAction(Animation.sizeActionPlusWithAnime(70, 100, 0.2f));
                                p.getCards().get(index).getActor().setPosition(p.getCards().get(index - 1).getActor().getX() + 55, 0);
                                p.getCards().get(index).getActor().addListener(new PlayerCardCtrl(p.getCards().get(index), CardDistribution.this, game.getMainGame()));
                                p.getCards().get(index).getActor().addListener(new PlayCardDragListener(p.getCards().get(index), p));
                                game.getStage().addActor(p.getCards().get(index).getActor());
                            }
                            cards.setVisible(false);
                        }
                        proceed[0] = true;
                    }
                }));
                int counter = sum;
                if (p.getCardsActor().size() == 5) {
                    if (game.getMainGame().getTURUP() == null) {
                        if (p.getId() == game.getMainGame().getTALK_TURN().getId()) {
                            if (p.getId() == game.getMainGame().getPLAYER().getId()) {
                                //Player will himself select
                                game.autoHideMessage("Please select turup from your cards").autoHide(5, null);
                            } else {
                                Turup turup = new Turup(game.getMainGame().getTALK_TURN());
                                turup.sortFromFewCards();
                                turup.viewMax();
                                game.getMainGame().setTURUP_STRING(turup.getTurupString());
                                game.getMainGame().setTURUP(turup.getTurup());
                                game.autoHideMessage(p.getName() + " has selected " + game.getMainGame().getTURUP_STRING() + " as Turup").autoHide(5, null);
                            }
                            game.getMainGame().turupMove();
                        }
                    }
                }
                if (p.getCardsActor().size() >= counter) {
                    Sort ps = new Sort(p);
                    ps.divideCards();
                    ps.sortCards();
                    p.setSortedCards(ps.getCards());
                    ps.arrangeCards();
                    p.setCards(ps.getSortedCards());
                    if (p.getId() != game.getMainGame().getPLAYER().getId()) {
                        System.out.println("--------------------");
                        System.out.println("Player is " + p.getName());
                        for (int i = 0; i < Const.COLORS_NAME.length; i++) {
                            System.out.println("card type " + Const.COLORS_NAME[i]);
                            for (Card c : p.getSortedCards().get(Const.COLORS_NAME_TYPE[i])) {
                                System.out.println("Card Number " + c.getNumber());
                            }
                        }
                        System.out.println("--------------------");
                        Iterator<Actor> a = p.getBackCards().iterator();
                        int valueOfRotation = 90;
                        Sound.getInstance().play(Sound.AUDIO.CARD_TOUCHED);
                        while (a.hasNext()) {
                            a.next().addAction(Animation.rotateWithAnime(valueOfRotation, 0.5f));
                            valueOfRotation -= 10;
                        }

                    }
                    if (p.getId() == game.getMainGame().getPLAYER().getId()) {

                        for (int i = 0; i < p.getCards().size(); i++) {
                            p.getCards().get(i).getActor().clearActions();
                            p.getCards().get(i).getActor().clearListeners();
                            p.getCards().get(i).getActor().clear();
                            p.getCards().get(i).getActor().remove();
                            if (i == 0) {
                                p.getCards().get(i).getActor().setPosition(0, 0);
                            } else {
                                p.getCards().get(i).getActor().setPosition(p.getCards().get(i - 1).getActor().getX() + 55, 0);
                            }
                            p.getCards().get(i).getActor().addAction(Animation.sizeActionPlusWithAnime(70, 100, 0.2f));
                            p.getCards().get(i).getActor().addListener(new PlayerCardCtrl(p.getCards().get(i), CardDistribution.this, game.getMainGame()));
                            p.getCards().get(i).getActor().addListener(new PlayCardDragListener(p.getCards().get(i), p));
                            game.getStage().addActor(p.getCards().get(i).getActor());
                        }
                    }
                    player[0]++;
                    if (player[0] >= game.getMainGame().players.size()) {
                        timer.clear();
                        game.autoHideMessage("Game is started. Let's Play").autoHide(3, null);
                        game.getMainGame().setSTARTED(true);
                        for (Player players : game.getMainGame().getPlayers()) {
                            if (players.getId() != game.getMainGame().getPLAYER().getId())
                                players.play();
                        }


                        return;
                    }
                }
                cardCounter[0]++;
            }
        }, 0, Const.CARD_DISTRUBUTION_SECONDS);
        timer.start();
        return this;
    }

    private float[] ifCardForEast(Player p, Actor cards, float x, float y) {
        switch (p.DIRECTION) {
            case EAST:
                cards.addAction(Animation.rotate(180, Const.CARD_ROTATE_ANIMATION));
                x = 0;
                y = cards.getHeight();
                break;
            case WEST:
                cards.addAction(Animation.rotate(360, Const.CARD_ROTATE_ANIMATION));
                x = p.getLocationX() - game.getCards().getX();
                p.setLocationX(p.getLocationX() + (cards.getWidth() + Const.PLAYER_CARD_DISTANCE));
                break;
            case NORTH:
                cards.addAction(Animation.rotate(270, Const.CARD_ROTATE_ANIMATION));
                x += p.getActor().getHeight();
                y = (p.getActor().getY() - p.getActor().getWidth() / 2) - (game.getCards().getY());
                break;
            case SOUTH:
                cards.addAction(Animation.rotate(90, Const.CARD_ROTATE_ANIMATION));
                x -= cards.getWidth() / 2 + 5;
                y = (p.getActor().getY() - p.getActor().getWidth() / 2) - game.getCards().getY();
                break;
        }
        return new float[]{x, y};
    }

    private float[] ifCardForWest(Player p, Actor cards, float x, float y) {
        switch (p.DIRECTION) {
            case EAST:
                cards.addAction(Animation.rotate(180, Const.CARD_ROTATE_ANIMATION));
                x = 0;
                break;
            case WEST:
                cards.addAction(Animation.rotate(360, Const.CARD_ROTATE_ANIMATION));
                x = p.getLocationX() - game.getCards().getX();
                p.setLocationX(p.getLocationX() + (cards.getWidth() + Const.PLAYER_CARD_DISTANCE));
                break;
            case NORTH:
                cards.addAction(Animation.rotate(270, Const.CARD_ROTATE_ANIMATION));
                x += p.getActor().getHeight();
                y = (p.getActor().getY() - p.getActor().getWidth() / 2) - (game.getCards().getY());
                break;
            case SOUTH:
                cards.addAction(Animation.rotate(90, Const.CARD_ROTATE_ANIMATION));
                x -= cards.getWidth() / 2 + 5;
                y = (p.getActor().getY() - p.getActor().getWidth() / 2) - game.getCards().getY();
                break;
        }
        return new float[]{x, y};
    }

    private float[] ifCardFromNorth(Player p, Actor cards, float x, float y) {
        switch (p.DIRECTION) {
            case EAST:
                cards.addAction(Animation.rotate(90, Const.CARD_ROTATE_ANIMATION));
                x = p.getActor().getY() - (game.getCards().getY() + 7);
                y = -p.getActor().getX() + (p.getActor().getWidth() / 2 + cards.getWidth() / 2);
                break;
            case WEST:

                cards.addAction(Animation.rotate(90, Const.CARD_ROTATE_ANIMATION));
                if (p.getCardsActor().size() < 2) {
                    p.setLocationY(-cards.getHeight());
                    p.setLocationX(480);
                    System.out.println(p.getLocationY());
                }
                x = p.getActor().getX() - p.getLocationX();
                y = -p.getLocationY();
                p.setLocationY(p.getLocationY() + cards.getWidth() + Const.PLAYER_CARD_DISTANCE);
                break;
            case NORTH:
                cards.addAction(Animation.rotate(180, Const.CARD_ROTATE_ANIMATION));
                x = 0;
                y = cards.getHeight() + 20;
                break;
            case SOUTH:

                cards.addAction(Animation.rotate(360, Const.CARD_ROTATE_ANIMATION));
                x = 0;
                y = -(p.getActor().getX() - (p.getActor().getY() - cards.getHeight() + 10));
                break;
        }
        return new float[]{x, y};
    }

    private float[] ifCardFromSouth(Player p, Actor cards, float x, float y) {
        switch (p.DIRECTION) {
            case EAST:
                cards.addAction(Animation.rotate(90, Const.CARD_ROTATE_ANIMATION));
                x = p.getActor().getY() - (game.getCards().getY() + 7);
                y = p.getActor().getX() - game.getCards().getY() + cards.getHeight();
                break;
            case WEST:
                cards.addAction(Animation.rotate(90, Const.CARD_ROTATE_ANIMATION));
                if (p.getCardsActor().size() < 2) {
                    p.setLocationX(480);
                    p.setLocationY(game.getCards().getX() - cards.getWidth());
                }
                x = p.getActor().getX() - p.getLocationX();
                y = p.getLocationY();
                p.setLocationY(p.getLocationY() - cards.getWidth() + Const.PLAYER_CARD_DISTANCE);
                break;
            case NORTH:
                cards.addAction(Animation.rotate(180, Const.CARD_ROTATE_ANIMATION));
                x = 0;
                y = game.getCards().getX() - ((cards.getHeight() / 2 - 5));
                break;
            case SOUTH:
                cards.addAction(Animation.rotate(360, Const.CARD_ROTATE_ANIMATION));
                x = 0;
                y = -(cards.getHeight() / 2 + 10);
                break;
        }
        return new float[]{x, y};
    }


}
