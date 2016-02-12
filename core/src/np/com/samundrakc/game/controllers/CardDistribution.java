package np.com.samundrakc.game.controllers;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;

import java.util.Timer;
import java.util.TimerTask;

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


    public void shareProcessFirst() {
        game.getCards().setZIndex(1);
        int count = 0;
        for (int i = 0; i < 52; i++) {
            if (game.getSortPlayer().get(count).getCards().size() == 13) {
                count++;
            }
            game.getSortPlayer().get(count).addCards(Game.cards.get(i));

        }

        final Timer timer = new Timer();
        final int[] finalCount = {0};
        final int perPlayer[] = {0};
        final int[] player = {0};

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (finalCount[0] >= 52) {
                    System.out.println("==========");
                    System.out.println(game.getMainGame().getPlayers().get(Game.turn).getName());
                    System.out.println(game.getCards().getX());
                    System.out.println(game.getCards().getY());
                    System.out.println("==========");
                    timer.cancel();
                    return;
                }

                Actor actor = game.getCards().getChildren().get(finalCount[0]);
                final Player gamer = game.getSortPlayer().get(player[0]);
//                switch (game.getMainGame().getPlayers().get(Game.turn).DIRECTION) {
//                    case EAST:
//                        break;
//                    case WEST:
                switch (gamer.DIRECTION) {
                    case EAST:
                        actor.addAction(Animation.moveBy(
                                gamer.getLocationX() - game.getCards().getX(),
                                gamer.getActor().getY() - (actor.getHeight()), 0.5f
                        ));
                        gamer.setLocationX(gamer.getLocationX() - (actor.getWidth() + 1));
                        break;
                    case WEST:
                        actor.addAction(
                                Actions.sequence(Animation.moveBy(
                                        gamer.getLocationX() - game.getCards().getX(),
                                        -actor.getHeight() / 2, 0.5f
                                ), new RunnableAction() {
                                    @Override
                                    public void run() {
                                        try {
                                            game.getStage().addActor(Game.cards.get(finalCount[0]).getActor(actor.getX(), actor.getY()));
                                        } catch (Exception e) {
                                            System.out.println(e.getMessage());
                                        }
                                    }
                                }));
                        gamer.setLocationX(gamer.getLocationX() - (actor.getWidth() + 1));
                        break;
                    case NORTH:
                        actor.setRotation(90);
                        System.out.println("norTH" + gamer.getLocationY());
                        actor.addAction(Animation.moveBy(
                                gamer.getLocationX() - game.getCards().getX(),
                                gamer.getLocationY(), 0.5f
                        ));
                        gamer.setLocationY(gamer.getLocationY() + (actor.getWidth() + 1));
                        break;
                    case SOUTH:
                        actor.setRotation(90);
                        System.out.println("SOUTH" + gamer.getLocationX());
                        actor.addAction(Actions.sequence(Animation.moveBy(
                                gamer.getLocationX() - game.getCards().getX(),
                                gamer.getLocationY(), 0.5f
                        ), new RunnableAction() {
                            @Override
                            public void run() {
                                gamer.setLocationY(gamer.getLocationY() + (actor.getWidth() + 1));
                            }
                        }));
                        break;
                }

//                        break;
//                    case NORTH:
//                        break;
//                    case SOUTH:
//                        break;
//                }

                if (perPlayer[0] >= 13) {
                    System.out.println(gamer.getName());
                    System.out.println(gamer.getLocationX());
                    System.out.println(gamer.getLocationY());
                    perPlayer[0] = 0;
                    player[0]++;
                }

                perPlayer[0]++;
                finalCount[0]++;
            }
        }, 0, 500);
    }

    private float getCoordinates(Player p, char axis) {
//        switch (p.DIRECTION) {
//            case EAST:
        return axis == 'x' ? p.getActor().getX() : p.getActor().getY();
//            case WEST:
//                return axis == 'x' ? p.getActor().getX() : p.getActor().getY();
//            case NORTH:
//                return axis == 'x' ? p.getActor().getX() : p.getActor().getY();
//            case SOUTH:
//                return axis == 'x' ? p.getActor().getX() : p.getActor().getY();
//        }
    }
}
