package np.com.samundrakc.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import np.com.samundrakc.game.anchors.Card;
import np.com.samundrakc.game.anchors.Const;
import np.com.samundrakc.game.anchors.Game;
import np.com.samundrakc.game.anchors.Group;
import np.com.samundrakc.game.anchors.Player;
import np.com.samundrakc.game.controllers.CardDistribution;
import np.com.samundrakc.game.misc.Animation;
import np.com.samundrakc.game.misc.Context;
import np.com.samundrakc.game.misc.MessageBox;

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

    public com.badlogic.gdx.scenes.scene2d.Group getCardsGroup() {
        return this.cards;
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

    public Game getMainGame() {
        return mainGame;
    }

    private void HUD() {
        hudTable = new Table();
        int width = 0;
        int height = 0;
        Label group_lb = new Label("Group", Context.skin);
        Label won_lb = new Label("Won", Context.skin);
        Label coat_lb = new Label("Coat", Context.skin);
        width += group_lb.getWidth() + won_lb.getWidth() + coat_lb.getWidth();
        hudTable.add(group_lb);
        hudTable.add(won_lb);
        hudTable.add(coat_lb);
        hudTable.row();
        height += group_lb.getHeight();
        for (Group g : mainGame.getGroup()) {
            Label l = new Label(g.getName(), Context.skin);
            groupWonStatusLabel.put(g.getName(), new Label(g.getWon() + "", Context.skin));
            groupCoatStatusLabel.put(g.getName(), new Label(g.getCoat() + "", Context.skin));
            hudTable.add(l).pad(1).expandX();
            hudTable.add(groupWonStatusLabel.get(g.getName())).pad(1).expandX();
            hudTable.add(groupCoatStatusLabel.get(g.getName())).pad(1).expandX();
            hudTable.row();
            height += l.getHeight();
        }
        hudTable.setWidth(200);
        hudTable.setHeight(height + 20);
        hudTable.top().setBackground(new NinePatchDrawable(MessageBox.getNinePatch("ng.9.png")));
        ;
        hudTable.setPosition(0, Context.HEIGHT - hudTable.getHeight());
        stage.addActor(hudTable);
    }

    HashMap<Player, Actor> playerPosition = new HashMap<Player, Actor>();
    ArrayList<Player> PlayerOnSide = new ArrayList<Player>();
    boolean hasToRotate = false;

    private void playerSeats() {
        Player[] temp = new Player[4];

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
                my.DIRECTION = Const.DIRECTION.WEST;
                my.setActor(me);
                my.getFriend().setActor(friend);
                my.getFriend().DIRECTION = Const.DIRECTION.EAST;
                stage.addActor(me);
                stage.addActor(friend);
                my.setLocationX(0);
                my.setLocationY(0);
                my.getFriend().setLocationX(570);
                my.getFriend().setLocationY(390);
                temp[0] = my;
                temp[2] = my.getFriend();
                if (Game.turn == my.getId()) {
                    cards.setPosition(Context.WIDTH / 2, me.getHeight() + 10);
                    my.setMyCardPosition(cards.getX(), cards.getY());
                } else if (Game.turn == my.getFriend().getId()) {
                    my.getFriend().setMyCardPosition(cards.getX(), cards.getY());
                    cards.setPosition(Context.WIDTH / 2, Context.HEIGHT - (friend.getHeight() + cards.getHeight() + 10));
                }
                stage.addActor(cards);

            }
            if (!g.getName().equals(my.getGroup().getName())) {
                System.out.println(g.getName());
                com.badlogic.gdx.scenes.scene2d.Group g1 = new com.badlogic.gdx.scenes.scene2d.Group();
                com.badlogic.gdx.scenes.scene2d.Group g2 = new com.badlogic.gdx.scenes.scene2d.Group();
                Player c1 = g.getPlayerList().get(0);
                Player c2 = g.getPlayerList().get(1);
                TextButton leftPlayer = new TextButton(c1.getName(), Context.skin);
                TextButton rightPlayer = new TextButton(c2.getName(), Context.skin);
                g1.setPosition(leftPlayer.getHeight(), Context.HEIGHT / 2);
                g2.setPosition(Context.WIDTH, Context.HEIGHT / 2);
                g1.addActor(leftPlayer);
                g2.addActor(rightPlayer);
                g1.setRotation(90);
                g2.setRotation(90);
                c1.DIRECTION = Const.DIRECTION.NORTH;
                c1.setLocationX(70);
                c1.setLocationY(1);
                c1.setActor(g1);
                c2.DIRECTION = Const.DIRECTION.SOUTH;
                c2.setLocationX(700);
                c2.setLocationY(1);
                c2.setActor(g2);
                playerPosition.put(c1, g1);
                playerPosition.put(c2, g2);
                stage.addActor(g1);
                stage.addActor(g2);
                temp[3] = c1;
                temp[1] = c2;
                if (Game.turn == c1.getId()) {
                    cards.setPosition(g1.getX() + leftPlayer.getHeight() + cards.getHeight(), g1.getY());
                    c1.setMyCardPosition(cards.getX(), cards.getY());
                } else if (Game.turn == c2.getId()) {
                    cards.setPosition(g2.getX() - cards.getHeight(), g2.getY());
                    c2.setMyCardPosition(cards.getX(), cards.getY());
                }
                stage.addActor(cards);
            }
        }
        for (Player p : temp) {
            PlayerOnSide.add(p);
        }
    }

    public HashMap<String, Label> getGroupWonStatusLabel() {
        return groupWonStatusLabel;
    }

    public HashMap<Player, Actor> getPlayerPosition() {
        return playerPosition;
    }

    public HashMap<String, Label> getGroupCoatStatusLabel() {
        return groupCoatStatusLabel;
    }

    public ArrayList<Player> getPlayerOnSide() {
        return PlayerOnSide;
    }

    public boolean isHasToRotate() {
        return hasToRotate;
    }

    public com.badlogic.gdx.scenes.scene2d.Group getCards() {
        return cards;
    }

    public void setMainGame(Game mainGame) {
        this.mainGame = mainGame;
    }

    public void setCards(com.badlogic.gdx.scenes.scene2d.Group cards) {
        this.cards = cards;
    }

    public Table getHudTable() {
        return hudTable;
    }

    public void setHudTable(Table hudTable) {
        this.hudTable = hudTable;
    }

    public void setGroupWonStatusLabel(HashMap<String, Label> groupWonStatusLabel) {
        this.groupWonStatusLabel = groupWonStatusLabel;
    }

    public void setGroupCoatStatusLabel(HashMap<String, Label> groupCoatStatusLabel) {
        this.groupCoatStatusLabel = groupCoatStatusLabel;
    }

    public void setPlayerPosition(HashMap<Player, Actor> playerPosition) {
        this.playerPosition = playerPosition;
    }

    public void setPlayerOnSide(ArrayList<Player> playerOnSide) {
        PlayerOnSide = playerOnSide;
    }

    public void setHasToRotate(boolean hasToRotate) {
        this.hasToRotate = hasToRotate;
    }

    public ArrayList<Player> getSortPlayer() {
        return sortPlayer;
    }

    public void setSortPlayer(ArrayList<Player> sortPlayer) {
        this.sortPlayer = sortPlayer;
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


        for (int i = 0; i < PlayerOnSide.size(); i++) {
            if (PlayerOnSide.get(i).getId() == Game.turn) {
                int index = i + 1;
                if (i == PlayerOnSide.size() - 1) {
                    index = 0;
                }
                sortPlayer.add(PlayerOnSide.get(index));
                inIndex(PlayerOnSide, index);
                break;
            }
        }
        Game.CURRENT_TURN = sortPlayer.get(sortPlayer.size() - 1);
        Game.PLAYER = mainGame.getPlayers().get(Game.mineId);
        Game.TALK_TURN = sortPlayer.get(0);
        new CardDistribution(this).shareProcessFirst().startShare(0,19,5);
    }

    private int inIndex(ArrayList<Player> playerOnSide, int index) {
        for (int i = index + 1; i < playerOnSide.size(); i++) {
            sortPlayer.add(playerOnSide.get(i));
        }
        for (int i = 0; i < index; i++) {
            sortPlayer.add(playerOnSide.get(i));
        }
        return index;
    }

    ArrayList<Player> sortPlayer = new ArrayList<Player>();
}
