package np.com.samundrakc.game.controllers;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
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

    public CardDistribution(DailaMaara game) {
        this.game = game;
    }


    public CardDistribution shareProcessFirst() {
        game.getCards().setZIndex(1);
        int count = 0;
        for (int i = 0; i < 20; i++) {
            if (game.getSortPlayer().get(count).getCards().size() == 5) {
                count++;
            }
            Player p = game.getSortPlayer().get(count);
            p.addCards(Game.cards.get(i));
        }
        return this;

    }

    //0,19,5
    public final CardDistribution startShare(int from, final int numberOfCardsToThrow, final int sum) {
        final int[] cardCounter = {from};
        final int[] player = {0};
        final Timer timer = new Timer();
        final boolean[] proceed = {true};
        System.out.println(from);
        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
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
                        proceed[0] = true;
                    }
                }));
                int counter = sum;
                if (p.getCardsActor().size() >= counter) {
                    if (p.getId() != Game.PLAYER.getId()) {
                        Iterator<Actor> a = p.getBackCards().iterator();
                        int valueOfRotation = 90;
                        while (a.hasNext()) {
                            a.next().addAction(Animation.rotate(valueOfRotation, 0.5f));
                            valueOfRotation -= 10;
                        }
                    }
                    player[0]++;
                    if (player[0] >= game.getMainGame().players.size()) {
                        timer.clear();
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
                x = p.getLocationX() - (game.getCards().getX() + cards.getHeight() - 10);
                p.setLocationX(p.getLocationX() - (cards.getWidth() + 5));
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
                y = cards.getHeight();
                break;
            case WEST:
                cards.addAction(Animation.rotate(360, 0.5f));
                x = p.getLocationX() - (game.getCards().getX() + cards.getHeight() - 10);
                p.setLocationX(p.getLocationX() - (cards.getWidth() + 5));
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
                    p.setLocationY(p.getLocationX() - game.getCards().getX());
                    p.setLocationX(480);
                    System.out.println(p.getLocationY());
                }
                x = p.getActor().getX() - p.getLocationX();
                y = -p.getLocationY();
                p.setLocationY(p.getLocationY() - cards.getWidth() - 5);
                break;
            case NORTH:
                cards.addAction(Animation.rotate(360, 0.5f));
                x = 0;
                y = 15;
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
                    p.setLocationY(-cards.getHeight());
                }
                x = p.getActor().getX() - p.getLocationX();
                y = p.getLocationY();
                p.setLocationY(p.getLocationY() + cards.getWidth() + 5);
                break;
            case NORTH:
                cards.addAction(Animation.rotate(360, 0.5f));
                x = 0;
                y = game.getCards().getX() - (cards.getHeight() + (cards.getHeight() / 2 - 5));
                break;
            case SOUTH:
                cards.addAction(Animation.rotate(360, 0.5f));
                x = 0;
                y = -(cards.getHeight() / 2 + 10);
                break;
        }
        return new float[]{x, y};
    }
}
