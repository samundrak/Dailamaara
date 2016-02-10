package np.com.samundrakc.game.screens;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import java.util.ArrayList;
import java.util.HashMap;

import np.com.samundrakc.game.anchors.Game;
import np.com.samundrakc.game.anchors.Group;
import np.com.samundrakc.game.anchors.Player;
import np.com.samundrakc.game.misc.Animation;
import np.com.samundrakc.game.misc.Context;

/**
 * Created by samundra on 2/10/2016.
 */
public class DailaMaara extends ScreenRules {
    Game mainGame;
    private com.badlogic.gdx.scenes.scene2d.Group cards;

    public DailaMaara(np.com.samundrakc.game.DailaMaara game, Game mainGame) {
        super(game);
        this.mainGame = mainGame;
    }

    @Override
    public void show() {
        HUD();
        playerSeats();
        setPositionOfCards();
    }

    public DailaMaara setCardsStacks(com.badlogic.gdx.scenes.scene2d.Group cards) {
        this.cards = cards;
        return this;
    }

    @Override
    public void render(float delta) {
        game.clearView();
        stage.act();
        stage.draw();
    }

    Table hudTable;
    HashMap<String, Label> groupWonStatusLabel = new HashMap<String, Label>();
    HashMap<String, Label> groupCoatStatusLabel = new HashMap<String, Label>();

    private void HUD() {
        hudTable = new Table();
        int width = 0;
        Label group_lb = new Label("Group", Context.skin);
        Label won_lb = new Label("Won", Context.skin);
        Label coat_lb = new Label("Coat", Context.skin);
        width += group_lb.getWidth() + won_lb.getWidth() + coat_lb.getWidth();
        hudTable.add(group_lb);
        hudTable.add(won_lb);
        hudTable.add(coat_lb);
        hudTable.row();

        for (Group g : mainGame.getGroup()) {
            Label l = new Label(g.getName(), Context.skin);
            groupWonStatusLabel.put(g.getName(), new Label(g.getWon() + "", Context.skin));
            groupCoatStatusLabel.put(g.getName(), new Label(g.getCoat() + "", Context.skin));
            hudTable.add(l).pad(1).expandX();
            hudTable.add(groupWonStatusLabel.get(g.getName())).pad(1).expandX();
            hudTable.add(groupCoatStatusLabel.get(g.getName())).pad(1).expandX();
            hudTable.row();
        }
        hudTable.setWidth(200);
//        hudTable.setFillParent(true);
        hudTable.top();
        hudTable.setPosition(0, Context.HEIGHT - 2);
        stage.addActor(hudTable);
    }

    HashMap<Player, Actor> playerPosition = new HashMap<Player, Actor>();
    boolean hasToRotate = false;

    private void playerSeats() {
        Player my = mainGame.getPlayers().get(Game.mineId);
        System.out.println(my.getGroup().getName());
        for (Group g : mainGame.getGroup()) {
            if (g.getName().equals(my.getGroup().getName())) {
                TextButton me = new TextButton(my.getName(), Context.skin);
                me.setPosition(Context.WIDTH / 2, 0);
                playerPosition.put(my, me);
                TextButton friend = new TextButton(my.getFriend().getName(), Context.skin);
                friend.setPosition(Context.WIDTH / 2, Context.HEIGHT - (friend.getHeight()));
                playerPosition.put(my.getFriend(), friend);
                stage.addActor(me);
                stage.addActor(friend);
                if (Game.turn == my.getId()) {
                    cards.setPosition(Context.WIDTH / 2, me.getHeight() + 10);
                } else if (Game.turn == my.getFriend().getId()) {
                    cards.setPosition(Context.WIDTH / 2, Context.HEIGHT - (friend.getHeight() + cards.getHeight() + 10));
                }
                stage.addActor(cards);

            }
            if (!g.getName().equals(my.getGroup().getName())) {
                System.out.println(g.getName());
                com.badlogic.gdx.scenes.scene2d.Group g1 = new com.badlogic.gdx.scenes.scene2d.Group();
                com.badlogic.gdx.scenes.scene2d.Group g2 = new com.badlogic.gdx.scenes.scene2d.Group();
                TextButton leftPlayer = new TextButton(g.getPlayerList().get(0).getName(), Context.skin);
                TextButton rightPlayer = new TextButton(g.getPlayerList().get(1).getName(), Context.skin);
                g1.setPosition(leftPlayer.getHeight(), Context.HEIGHT / 2);
                g2.setPosition(Context.WIDTH, Context.HEIGHT / 2);
                g1.addActor(leftPlayer);
                g2.addActor(rightPlayer);
                g1.setRotation(90);
                g2.setRotation(90);
                playerPosition.put(g.getPlayerList().get(0), g1);
                playerPosition.put(g.getPlayerList().get(1), g2);
                stage.addActor(g1);
                stage.addActor(g2);
                if (Game.turn == g.getPlayerList().get(0).getId()) {
                    cards.setPosition(g1.getX() + leftPlayer.getHeight() + cards.getHeight(), g1.getY());
                } else if (Game.turn == g.getPlayerList().get(1).getId()) {
                    cards.setPosition(g2.getX() - cards.getHeight(), g2.getY());
                }
                stage.addActor(cards);
            }
        }
    }

    private void setPositionOfCards() {
        hasToRotate = true;
        Group mine = mainGame.getPlayers().get(Game.mineId).getGroup();
        for (Player p : mine.getPlayerList()) {
            if (p.getId() == Game.turn) {
                hasToRotate = false;
            }
        }
        if (hasToRotate) {
            cards.setRotation(90);
        } else {
            cards.setRotation(0);
        }
    }
}
