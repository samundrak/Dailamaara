package np.com.samundrakc.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

import java.io.Serializable;
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
    MessageBox msg;

    public Image getNonTururp() {
        return nonTururp;
    }

    public void setNonTururp(Image nonTururp) {
        this.nonTururp = nonTururp;
    }

    private Image nonTururp;

    public DailaMaara(np.com.samundrakc.game.DailaMaara game, Game mainGame) {
        super(game);
        this.mainGame = mainGame;
        msg = new MessageBox(stage, "Pop up message");
        msg.setInMiddle(true);
        mainGame.setView(this);
    }

    @Override
    public void show() {
        HUD();
        playerSeats();
        setPositionOfCards();

    }

    public MessageBox autoHideMessage(String message) {
        return msg.setMessage(message).show();
    }

    public com.badlogic.gdx.scenes.scene2d.Group getCardsGroup() {
        return this.cards;
    }

    public DailaMaara setCardsStacks(com.badlogic.gdx.scenes.scene2d.Group cards) {
        this.cards = cards;
        return this;
    }

    Table hudTable;
    HashMap<String, Label> groupWonStatusLabel = new HashMap<String, Label>();
    HashMap<String, Label> groupCoatStatusLabel = new HashMap<String, Label>();
    HashMap<String, Label> groupThrownStatusLabel = new HashMap<String, Label>();
    HashMap<String, Label> groupHandsStatusLabel = new HashMap<String, Label>();
    HashMap<String, Label> groupTensStatusLabel = new HashMap<String, Label>();
    Table turupTable;

    public Table getTurupTable() {
        return turupTable;
    }

    public void setTurupTable(Table turupTable) {
        this.turupTable = turupTable;
    }

    public Game getMainGame() {
        return mainGame;
    }

    public static Label TURN_LABEL = new Label("None", Context.getInstance().getSkin());


    public HashMap<String, Label> getGroupThrownStatusLabel() {
        return groupThrownStatusLabel;
    }

    public void setGroupThrownStatusLabel(HashMap<String, Label> groupThrownStatusLabel) {
        this.groupThrownStatusLabel = groupThrownStatusLabel;
    }

    public HashMap<String, Label> getGroupHandsStatusLabel() {
        return groupHandsStatusLabel;
    }

    public void setGroupHandsStatusLabel(HashMap<String, Label> groupHandsStatusLabel) {
        this.groupHandsStatusLabel = groupHandsStatusLabel;
    }

    public HashMap<String, Label> getGroupTensStatusLabel() {
        return groupTensStatusLabel;
    }

    public void setGroupTensStatusLabel(HashMap<String, Label> groupTensStatusLabel) {
        this.groupTensStatusLabel = groupTensStatusLabel;
    }

    private void HUD() {
        hudTable = new Table();
        int width = 0;
        int height = 0;
        Label group_lb = new Label("Group", Context.getInstance().getSkin());
        Label won_lb = new Label("Won", Context.getInstance().getSkin());
        Label coat_lb = new Label("Coat", Context.getInstance().getSkin());
        Label turup_lb = new Label("Turup", Context.getInstance().getSkin());
        Label thrown = new Label("Thrown", Context.getInstance().getSkin());
        Label hands_lb = new Label("Hands", Context.getInstance().getSkin());
        Label tens_lb = new Label("Ten's", Context.getInstance().getSkin());
        nonTururp = new Image(Context.getInstance().getCARDS_BACK_COVER());

        nonTururp.setSize(70, 100);
        hudTable.add(group_lb);
        hudTable.add(won_lb);
        hudTable.add(coat_lb);
        hudTable.add(thrown);
        hudTable.add(hands_lb);
        hudTable.add(tens_lb);
        hudTable.add(turup_lb);
        hudTable.row();
        height += group_lb.getHeight();
        for (Group g : mainGame.getGroup()) {
            Label l = new Label(g.getName(), Context.getInstance().getSkin());
            groupWonStatusLabel.put(g.getName(), new Label(g.getWon() + "", Context.getInstance().getSkin()));
            groupCoatStatusLabel.put(g.getName(), new Label(g.getCoat() + "", Context.getInstance().getSkin()));
            groupThrownStatusLabel.put(g.getName(), new Label(g.getThrown() + "", Context.getInstance().getSkin()));
            groupHandsStatusLabel.put(g.getName(), new Label(g.getHands() + "", Context.getInstance().getSkin()));
            groupTensStatusLabel.put(g.getName(), new Label(g.getTens() + "", Context.getInstance().getSkin()));
            hudTable.add(l).pad(1).expandX();
            hudTable.add(groupWonStatusLabel.get(g.getName())).pad(1).expandX();
            hudTable.add(groupCoatStatusLabel.get(g.getName())).pad(1).expandX();
            hudTable.add(groupThrownStatusLabel.get(g.getName())).pad(1).expandX();
            hudTable.add(groupHandsStatusLabel.get(g.getName())).pad(1).expandX();
            hudTable.add(groupTensStatusLabel.get(g.getName())).pad(1).expandX();
            hudTable.row();
            height += l.getHeight();
        }
        hudTable.setWidth(270);
        Label turnLabel = new Label("Turn", Context.getInstance().getSkin());
        hudTable.add(turnLabel);
        hudTable.add(TURN_LABEL).colspan(2).row();
        height += turnLabel.getHeight();
        hudTable.setHeight(height + 20);
        hudTable.top().setBackground(new NinePatchDrawable(MessageBox.getNinePatch("ng.9.png")));
        hudTable.setPosition(0, Context.HEIGHT - (hudTable.getHeight() - 10));
        turupTable = new Table();
        turupTable.add(turup_lb);
        turupTable.row();
        turupTable.add(nonTururp);
        turupTable.setWidth(70);
        turupTable.setHeight(nonTururp.getHeight());
        turupTable.top().setBackground(new NinePatchDrawable(MessageBox.getNinePatch("ng.9.png")));
        turupTable.setPosition(Context.WIDTH - turupTable.getWidth(), Context.HEIGHT - (turupTable.getHeight() - 10));


        stage.addActor(hudTable);
        stage.addActor(turupTable);
        stage.addActor(playOfTable());
    }


    public Table getPlayOfTable() {
        return playOfTable;
    }

    private Table playOfTable;

    private Actor playOfTable() {
        Label playOf = new Label("Play Of", Context.getInstance().getSkin());
        Image playOfActor = new Image(Context.getInstance().getCARDS_BACK_COVER());
        playOfActor.setSize(70, 100);
        playOfTable = new Table();
        playOfTable.add(playOf);
        playOfTable.row();
        playOfTable.add(playOfActor);
        playOfTable.setWidth(70);
        playOfTable.setHeight(playOfActor.getHeight());
        playOfTable.top().setBackground(new NinePatchDrawable(MessageBox.getNinePatch("ng.9.png")));
        playOfTable.setPosition(turupTable.getX() - playOfTable.getWidth(), Context.HEIGHT - (playOfTable.getHeight() - 10));
        return playOfTable;
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
                TextButton me = new TextButton(my.getName(), Context.getInstance().getSkin());
                me.setPosition(Context.WIDTH / 2, 0);
                playerPosition.put(my, me);
                TextButton friend = new TextButton(my.getFriend().getName(), Context.getInstance().getSkin());
                friend.setPosition(Context.WIDTH / 2, Context.HEIGHT - (friend.getHeight()));
                playerPosition.put(my.getFriend(), friend);
                my.DIRECTION = Const.DIRECTION.WEST;
                my.setActor(me);
                my.getFriend().setActor(friend);
                my.getFriend().DIRECTION = Const.DIRECTION.EAST;
                my.setCardToThrowLocations(new float[]{245, 155});
                stage.addActor(me);
                stage.addActor(friend);
                my.setLocationX(0);
                my.setLocationY(0);
                my.setGame(mainGame);
                my.setView(this);
                my.getFriend().setLocationX(570);
                my.getFriend().setLocationY(390);
                my.getFriend().setGame(mainGame);
                my.getFriend().setView(this);
                my.getFriend().setCardToThrowLocations(new float[]{355, 155});
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
                TextButton leftPlayer = new TextButton(c1.getName(), Context.getInstance().getSkin());
                TextButton rightPlayer = new TextButton(c2.getName(), Context.getInstance().getSkin());
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
                c1.setGame(mainGame);
                c1.setView(this);
                c1.setCardToThrowLocations(new float[]{135, 155});
                c2.DIRECTION = Const.DIRECTION.SOUTH;
                c2.setLocationX(700);
                c2.setLocationY(1);
                c2.setActor(g2);
                c2.setGame(mainGame);
                c2.setView(this);
                c2.setCardToThrowLocations(new float[]{460, 155});
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
        Game.PLAYER_ORDER = sortPlayer;
        Game.CURRENT_TURN = sortPlayer.get(sortPlayer.size() - 1);
        Game.PLAYER = mainGame.getPlayers().get(Game.mineId);
        Game.TALK_TURN = sortPlayer.get(0);
        Game.PLAY_TURN = sortPlayer.get(0);
        DailaMaara.TURN_LABEL.setText(Game.PLAY_TURN.getName());
        new CardDistribution(this).shareProcessFirst().startShare(0, 51, 13);
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
