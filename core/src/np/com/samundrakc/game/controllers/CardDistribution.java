package np.com.samundrakc.game.controllers;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Timer;


import java.util.Iterator;

import np.com.samundrakc.game.anchors.Card;
import np.com.samundrakc.game.anchors.Const;
import np.com.samundrakc.game.anchors.Game;
import np.com.samundrakc.game.anchors.Player;
import np.com.samundrakc.game.misc.Animation;
import np.com.samundrakc.game.misc.Context;
import np.com.samundrakc.game.misc.Utils;
import np.com.samundrakc.game.screens.DailaMaara;

/**
 * Created by samundra on 2/10/2016.
 */
public class CardDistribution {
    private final DailaMaara game;
    private boolean cardShareDone = false;

    public CardDistribution(DailaMaara game) {
        this.game = game;
    }


    public CardDistribution shareProcessFirst() {
        game.getCards().setZIndex(1);
        int count = 0;
        for (int i = 0; i < 52; i++) {
            Card c = Game.cards.get(i);
            if (game.getSortPlayer().get(count).getCards().size() == 13) {

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
                if (Game.TALK_TURN.getCardsActor().size() >= 5) {
                    if (Game.TURUP == null) {
                        return;
                    }
                }
                if (!cardShareDone) return;
                if (!proceed[0]) return;
                proceed[0] = false;
                if (cardCounter[0] == numberOfCardsToThrow) {
                    Game.THROWN_CARDS = numberOfCardsToThrow;
                }


                final Actor cards = game.getCards().getChildren().get(cardCounter[0]);
                cards.clearActions();
                final Player p = game.getSortPlayer().get(player[0]);
                final Player turn = game.getMainGame().getPlayers().get(Game.turn);
                p.setBackCards(cards);
                p.setCardsActor(Game.cards.get(cardCounter[0]).getActor());
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
                cards.addAction(Actions.sequence(Animation.moveBy(x, y, 0.5f), new RunnableAction() {
                    @Override
                    public void run() {
                        if (p.DIRECTION == Const.DIRECTION.WEST) {
                            int index = 0;
                            if (p.getCardsActor().size() < 2) {
                                p.getCards().get(index).getActor().clearActions();
                                p.getCards().get(index).getActor().clearListeners();
                                p.getCards().get(index).getActor().setVisible(true);
                                p.getCards().get(index).getActor().addAction(Animation.sizeActionPlusWithAnime(100, 120, 0.2f));
                                p.getCards().get(index).getActor().setPosition(0, 0);
                                p.getCards().get(index).getActor().addListener(new PlayCardDragListener(p.getCards().get(index)));
                                p.getCards().get(index).getActor().addListener(new PlayerCardCtrl(p.getCards().get(0), CardDistribution.this));
                                game.getStage().addActor(p.getCards().get(index).getActor());
                            } else {
                                index = p.getCardsActor().size() - 1;
                                p.getCards().get(index).getActor().setVisible(true);
                                p.getCards().get(index).getActor().clearActions();
                                p.getCards().get(index).getActor().clearListeners();
                                p.getCards().get(index).getActor().addAction(Animation.sizeActionPlusWithAnime(100, 120, 0.2f));
                                p.getCards().get(index).getActor().setPosition(p.getCards().get(index - 1).getActor().getX() + 50, 0);
                                p.getCards().get(index).getActor().addListener(new PlayerCardCtrl(p.getCards().get(index), CardDistribution.this));
                                p.getCards().get(index).getActor().addListener(new PlayCardDragListener(p.getCards().get(index)));
                                game.getStage().addActor(p.getCards().get(index).getActor());
                            }
                            cards.setVisible(false);
                        }
                        proceed[0] = true;
                    }
                }));
                int counter = sum;
                if (p.getCardsActor().size() == 5) {
                    if (Game.TURUP == null) {
                        if (p.getId() == Game.TALK_TURN.getId()) {
                            if (p.getId() == Game.PLAYER.getId()) {
                                //Player will himself select
                                game.autoHideMessage("Please select turup from your cards").autoHide(5, null);
                            } else {
                                Turup turup = new Turup(Game.TALK_TURN);
                                turup.sortFromFewCards();
                                turup.viewMax();
                                Game.TURUP_STRING = turup.getTurupString();
                                Game.TURUP = turup.getTurup();
                                game.autoHideMessage(p.getName() + " has selected " + Game.TURUP_STRING + " as Turup").autoHide(5, null);
                            }
                            turupMove();
                        }
                    }
                }
                if (p.getCardsActor().size() >= counter) {

                    if (p.getId() != Game.PLAYER.getId()) {
                        Iterator<Actor> a = p.getBackCards().iterator();
                        int valueOfRotation = 90;
                        while (a.hasNext()) {
                            a.next().addAction(Animation.rotateWithAnime(valueOfRotation, 0.5f));
                            valueOfRotation -= 10;
                        }
                    }
                    if (p.getId() == Game.PLAYER.getId()) {
                        PlayerCardSort ps = new PlayerCardSort(Game.PLAYER);
                        ps.divideCards();
                        ps.sortCards();
                        ps.arrangeCards();
                        p.setCards(ps.getSortedCards());
                        for (int i = 0; i < p.getCards().size(); i++) {
                            p.getCards().get(i).getActor().clearActions();
                            p.getCards().get(i).getActor().clearListeners();
                            p.getCards().get(i).getActor().clear();
                            p.getCards().get(i).getActor().remove();
                            if (i == 0) {
                                p.getCards().get(i).getActor().setPosition(0, 0);
                            } else {
                                p.getCards().get(i).getActor().setPosition(p.getCards().get(i - 1).getActor().getX() + 50, 0);
                            }
                            p.getCards().get(i).getActor().addAction(Animation.sizeActionPlusWithAnime(100, 120, 0.2f));
                            p.getCards().get(i).getActor().addListener(new PlayerCardCtrl(p.getCards().get(i), CardDistribution.this));
                            p.getCards().get(i).getActor().addListener(new PlayCardDragListener(p.getCards().get(i)));
                            game.getStage().addActor(p.getCards().get(i).getActor());
                        }
                    }
                    player[0]++;
                    if (player[0] >= game.getMainGame().players.size()) {
                        timer.clear();
                        game.autoHideMessage("Game is started. Let's Play").autoHide(3, null);
                        Game.STARTED = true;
                        return;
                    }
                }
                cardCounter[0]++;
            }
        }, 0, 0.5f);
        timer.start();
        return this;
    }

    private float[] ifCardForEast(Player p, Actor cards, float x, float y) {
        switch (p.DIRECTION) {
            case EAST:
                cards.addAction(Animation.rotate(180, 0.5f));
                x = 0;
                y = cards.getHeight();
                break;
            case WEST:
                cards.addAction(Animation.rotate(360, 0.5f));
                x = p.getLocationX() - game.getCards().getX();
                p.setLocationX(p.getLocationX() + (cards.getWidth() + Const.PLAYER_CARD_DISTANCE));
                break;
            case NORTH:
                cards.addAction(Animation.rotate(270, 0.5f));
                x += p.getActor().getHeight();
                y = (p.getActor().getY() - p.getActor().getWidth() / 2) - (game.getCards().getY());
                break;
            case SOUTH:
                cards.addAction(Animation.rotate(90, 0.5f));
                x -= cards.getWidth() / 2 + 5;
                y = (p.getActor().getY() - p.getActor().getWidth() / 2) - game.getCards().getY();
                break;
        }
        return new float[]{x, y};
    }

    private float[] ifCardForWest(Player p, Actor cards, float x, float y) {
        switch (p.DIRECTION) {
            case EAST:
                cards.addAction(Animation.rotate(180, 0.5f));
                x = 0;
                break;
            case WEST:
                cards.addAction(Animation.rotate(360, 0.5f));
                x = p.getLocationX() - game.getCards().getX();
                p.setLocationX(p.getLocationX() + (cards.getWidth() + Const.PLAYER_CARD_DISTANCE));
                break;
            case NORTH:
                cards.addAction(Animation.rotate(270, 0.5f));
                x += p.getActor().getHeight();
                y = (p.getActor().getY() - p.getActor().getWidth() / 2) - (game.getCards().getY());
                break;
            case SOUTH:
                cards.addAction(Animation.rotate(90, 0.5f));
                x -= cards.getWidth() / 2 + 5;
                y = (p.getActor().getY() - p.getActor().getWidth() / 2) - game.getCards().getY();
                break;
        }
        return new float[]{x, y};
    }

    private float[] ifCardFromNorth(Player p, Actor cards, float x, float y) {
        switch (p.DIRECTION) {
            case EAST:
                cards.addAction(Animation.rotate(90, 0.5f));
                x = p.getActor().getY() - (game.getCards().getY() + 7);
                y = -p.getActor().getX() + (p.getActor().getWidth() / 2 + cards.getWidth() / 2);
                break;
            case WEST:

                cards.addAction(Animation.rotate(90, 0.5f));
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
                cards.addAction(Animation.rotate(180, 0.5f));
                x = 0;
                y = cards.getHeight() + 20;
                break;
            case SOUTH:

                cards.addAction(Animation.rotate(360, 0.5f));
                x = 0;
                y = -(p.getActor().getX() - (p.getActor().getY() - cards.getHeight() + 10));
                break;
        }
        return new float[]{x, y};
    }

    private float[] ifCardFromSouth(Player p, Actor cards, float x, float y) {
        switch (p.DIRECTION) {
            case EAST:
                cards.addAction(Animation.rotate(90, 0.5f));
                x = p.getActor().getY() - (game.getCards().getY() + 7);
                y = p.getActor().getX() - game.getCards().getY() + cards.getHeight();
                break;
            case WEST:
                cards.addAction(Animation.rotate(90, 0.5f));
                if (p.getCardsActor().size() < 2) {
                    p.setLocationX(480);
                    p.setLocationY(game.getCards().getX() - cards.getWidth());
                }
                x = p.getActor().getX() - p.getLocationX();
                y = p.getLocationY();
                p.setLocationY(p.getLocationY() - cards.getWidth() + Const.PLAYER_CARD_DISTANCE);
                break;
            case NORTH:
                cards.addAction(Animation.rotate(180, 0.5f));
                x = 0;
                y = game.getCards().getX() - ((cards.getHeight() / 2 - 5));
                break;
            case SOUTH:
                cards.addAction(Animation.rotate(360, 0.5f));
                x = 0;
                y = -(cards.getHeight() / 2 + 10);
                break;
        }
        return new float[]{x, y};
    }

    public void turupMove() {
        if (Game.TURUP == null) return;
        Image turup = Game.COLORS.get(Game.TURUP);
        turup.setPosition(Context.WIDTH / 2, Context.HEIGHT / 2);
        turup.addAction(Actions.sequence(Animation.sizeActionPlusWithAnime(50, 60, 1),
                Animation.simpleAnimation(game.getTurupTable().getX() + 10, game.getTurupTable().getY() + 8),
                new RunnableAction() {
                    /**
                     * Called to run the runnable.
                     */
                    @Override
                    public void run() {
                        game.getNonTururp().remove();

                    }
                }));
        game.getStage().addActor(turup);
    }
}
