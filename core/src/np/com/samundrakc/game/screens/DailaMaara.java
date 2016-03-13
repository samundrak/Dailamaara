package np.com.samundrakc.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

import java.util.ArrayList;
import java.util.HashMap;

import np.com.samundrakc.game.anchors.Const;
import np.com.samundrakc.game.anchors.Game;
import np.com.samundrakc.game.anchors.Group;
import np.com.samundrakc.game.anchors.Player;
import np.com.samundrakc.game.controllers.dailamaara.CardDistribution;
import np.com.samundrakc.game.controllers.dailamaara.DailamaaraListener;
import np.com.samundrakc.game.controllers.form.FormCtrl;
import np.com.samundrakc.game.controllers.subControllers.PFX;
import np.com.samundrakc.game.controllers.subControllers.PauseScreen;
import np.com.samundrakc.game.misc.Animation;
import np.com.samundrakc.game.misc.Context;
import np.com.samundrakc.game.misc.MessageBox;
import np.com.samundrakc.game.misc.SpriteAnimation;
import np.com.samundrakc.game.screens.subScreens.Windows;

/**
 * Created by samundra on 2/10/2016.
 */
public class DailaMaara extends ScreenRules {
    private Image goUp;
    private Image goDown;
    private DailamaaraListener controller;
    Game mainGame;
    private com.badlogic.gdx.scenes.scene2d.Group cards;
    MessageBox msg;
    PFX pfx;
    private SpriteAnimation birdAnimation;
    Rectangle bounds;
    PauseScreen.GamePauseScreen gamePauseScreenCtrl;
    Windows gamePauseScreen;

    public Image getOnCoat() {
        return onCoat;
    }

    public void setOnCoat(Image onCoat) {
        this.onCoat = onCoat;
    }

    Image onCoat;

    public Image getNonTururp() {
        return nonTururp;
    }

    public void setNonTururp(Image nonTururp) {
        this.nonTururp = nonTururp;
    }

    private Image nonTururp;
    private np.com.samundrakc.game.DailaMaara parentGame;

    public np.com.samundrakc.game.DailaMaara getParentGame() {
        return parentGame;
    }

    private ParticleEffect tensEffects = new ParticleEffect();

    public void setParentGame(np.com.samundrakc.game.DailaMaara parentGame) {
        this.parentGame = parentGame;
    }

    public ParticleEffect getTensEffects() {
        return tensEffects;
    }

    public void setTensEffects(ParticleEffect tensEffects) {
        this.tensEffects = tensEffects;
    }

    SpriteBatch sb = new SpriteBatch();

    @Override
    public void render(float delta) {
        super.render(delta);
        sb.begin();
        tensEffects.draw(sb, delta);
        pfx.getEffectHashMap().get(Const.CARDS.CLUBS.toString()).draw(sb, delta);
        pfx.getEffectHashMap().get(Const.CARDS.SPADES.toString()).draw(sb, delta);
        pfx.getEffectHashMap().get(Const.CARDS.DIAMONDS.toString()).draw(sb, delta);
        pfx.getEffectHashMap().get(Const.CARDS.HEARTS.toString()).draw(sb, delta);
        pfx.getEffectHashMap().get("win").draw(sb, delta);
        pfx.getEffectHashMap().get("onCoat").draw(sb, delta);
//        birdAnimation.update(delta);
//        sb.draw(birdAnimation.getFrame(), 0, 0);
        if (Gdx.input.isKeyPressed(Input.Keys.BACK)) {
            gamePauseScreen.show();
        }

        sb.end();

    }

    public PFX getPfx() {
        return pfx;
    }

    public DailaMaara(np.com.samundrakc.game.DailaMaara game, Game mainGame) {
        super(game, new Texture("table.png"));
        this.mainGame = mainGame;
        Gdx.input.setCatchBackKey(true);
    }

    public void initTheCardsAgain() {
        this.cards = new com.badlogic.gdx.scenes.scene2d.Group();
        final int[] gap = {3};
        cards.setPosition(gap[0], (50));
        for (int i = 0; i < Const.TOTAL_NUMBER_OF_CARDS; i++) {
            cards.addActor(new FormCtrl.BackCover(i));
            cards.getChildren().get(i).setPosition(gap[0], gap[0]);
            cards.getChildren().get(i).setZIndex(0);
        }
        cards.setZIndex(0);
        cards.setSize(40, 60);
        cards.setTouchable(Touchable.disabled);
    }

    @Override
    public void loadAssets() {
        super.loadAssets();
        initTheCardsAgain();
        TURN_LABEL = new Label("None", Context.getInstance().getSkin());
        sortPlayer = new ArrayList<Player>();
        if (np.com.samundrakc.game.DailaMaara.GAME_MUSIC != null) {

            np.com.samundrakc.game.DailaMaara.GAME_MUSIC.setVolume(0.2f);
        }
        parentGame = game;
        gamePauseScreenCtrl = new PauseScreen.GamePauseScreen(getMainGame());
        gamePauseScreen = new Windows("Pause", getStage()).getPauseScreen(gamePauseScreenCtrl);
        tensEffects.load(Gdx.files.internal("particle/tens.pfx"), Gdx.files.internal("particle/images"));
        mainGame.setGAME_STAGE(stage);
        msg = new MessageBox(stage, "Pop up message");
        msg.setInMiddle(true);
        mainGame.setView(this);
        pfx = new PFX();
        pfx.loadEffects();
//        Texture texture = new Texture(Gdx.files.internal("texture/turup_anime.png"));
//        birdAnimation = new SpriteAnimation(new TextureRegion(texture), 4, 0.5f);
//        bounds = new Rectangle(0, 0, texture.getWidth() / 4, 100);
        goUp = new Image(new Texture("up.png"));
        goDown = new Image(new Texture("down.png"));
        onCoat = new Image(new Texture("coats.png"));
        controller = new DailamaaraListener(this);
        setIsScreenReady(true);
    }

    @Override
    public void show() {
        HUD();
        playerSeats();
        setPositionOfCards();

        stage.addActor(goUp);
        stage.addActor(goDown);
    }

    public MessageBox autoHideMessage(String message) {
        return msg.setMessage(message).show();
    }

    public com.badlogic.gdx.scenes.scene2d.Group getCardsGroup() {
        return this.cards;
    }

    public DailaMaara setCardsStacks(com.badlogic.gdx.scenes.scene2d.Group cards) {

        return this;
    }

    public com.badlogic.gdx.scenes.scene2d.Group getCardsStacks() {
        return this.cards;
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

    public Label getTURN_LABEL() {
        return TURN_LABEL;
    }

    private Label TURN_LABEL;


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
        hudTable.add(group_lb).pad(2);
        hudTable.add(won_lb).pad(2);
        hudTable.add(coat_lb).pad(2);
        hudTable.add(thrown).pad(2);
        hudTable.add(hands_lb).pad(2);
        hudTable.add(tens_lb).pad(2);
        hudTable.add(turup_lb).pad(2);
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
        hudTable.setWidth(350);
        Label turnLabel = new Label("Turn", Context.getInstance().getSkin());
        hudTable.add(turnLabel);
        hudTable.add(TURN_LABEL).colspan(2).row();
        height += turnLabel.getHeight();
        hudTable.setHeight(height + 20);
        hudTable.top().setBackground(new NinePatchDrawable(MessageBox.getNinePatch("ng.9.png")));
//        hudTable.setPosition(0, Context.HEIGHT - (hudTable.getHeight() - 10));
        hudTable.setPosition(0, Context.HEIGHT - 25);
        turupTable = new Table();
        turupTable.add(turup_lb);
        turupTable.row();
        turupTable.add(nonTururp);
        turupTable.setWidth(70);
        turupTable.setHeight(nonTururp.getHeight());
        turupTable.top().setBackground(new NinePatchDrawable(MessageBox.getNinePatch("ng.9.png")));
        turupTable.setPosition(Context.WIDTH - turupTable.getWidth(), Context.HEIGHT - (turupTable.getHeight() - 10));
        TextButton button = new TextButton("<<", Context.getInstance().getSkin());
        goUp.setPosition(hudTable.getX() + hudTable.getWidth() / 2, hudTable.getY() - (goUp.getHeight() / 2));
        goDown.setPosition(hudTable.getX() + hudTable.getWidth() / 2, hudTable.getY() - (goUp.getHeight() / 2));
        goUp.setSize(32, 32);
        goDown.setSize(32, 32);
        goUp.setVisible(false);
        goUp.addListener(controller.upDownToggleButton());
        goDown.addListener(controller.upDownToggleButton());
        stage.addActor(hudTable);
        stage.addActor(turupTable);
        stage.addActor(playOfTable());
    }

    public Image getGoDown() {
        return goDown;
    }

    public Image getGoUp() {
        return goUp;
    }

    public void setToggle() {
        if (goUp.isVisible()) {
            //Table is open
            goUp.setVisible(false);
            goDown.setVisible(true);
            hudTable.addAction(Animation.moveByAnime(0, hudTable.getHeight() - 42, 0.5f));
            goDown.addAction(Animation.moveByAnime(0, hudTable.getHeight() - 42, 0.5f));
            goUp.addAction(Animation.moveByAnime(0, hudTable.getHeight() - 42, 0.5f));
        } else {
            //Table is hidden
            hudTable.addAction(Animation.moveByAnime(0, -(hudTable.getHeight() - 42), 0.5f));
            goUp.setVisible(true);
            goDown.setVisible(false);
            goUp.addAction(Animation.moveByAnime(0, -(hudTable.getHeight() - 42), 0.5f));
            goDown.addAction(Animation.moveByAnime(0, -(hudTable.getHeight() - 42), 0.5f));
        }
    }

    public Table getPlayOfTable() {
        return playOfTable;
    }

    private Table playOfTable;
    Image playOfActor;

    public Image getPlayOfActor() {
        return playOfActor;
    }

    private Actor playOfTable() {
        Label playOf = new Label("Play Of", Context.getInstance().getSkin());
        playOfActor = new Image(Context.getInstance().getCARDS_BACK_COVER());
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
        cards.setZIndex(0);
        Player[] temp = new Player[4];

        Player my = mainGame.getPlayers().get(getMainGame().getMineId());

        for (Group g : mainGame.getGroup()) {
            if (g.getName().equals(my.getGroup().getName())) {
                TextButton me = new TextButton(my.getName(), Context.getInstance().getSkin());
                me.setZIndex(1);
                me.setPosition(Context.WIDTH / 2, 0);
                playerPosition.put(my, me);
                TextButton friend = new TextButton(my.getFriend().getName(), Context.getInstance().getSkin());
                friend.setPosition(Context.WIDTH / 2, Context.HEIGHT - (friend.getHeight()));
                friend.setZIndex(1);
                playerPosition.put(my.getFriend(), friend);
                my.setDIRECTION(Const.DIRECTION.WEST);
                Gdx.app.log(my.getName() + " Direction", my.getDIRECTION().toString());
                my.setActor(me);
                my.getFriend().setActor(friend);
                my.getFriend().setDIRECTION(Const.DIRECTION.EAST);
                Gdx.app.log(my.getFriend().getName() + " Direction", my.getFriend().getDIRECTION().toString());
                my.setCardToThrowLocations(new float[]{245, 155});

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
                if (mainGame.getTurn() == my.getId()) {
                    cards.setPosition(Context.WIDTH / 2, me.getHeight() + 10);
                    my.setMyCardPosition(cards.getX(), cards.getY());
                } else if (mainGame.getTurn() == my.getFriend().getId()) {
                    my.getFriend().setMyCardPosition(cards.getX(), cards.getY());
                    cards.setPosition(Context.WIDTH / 2, Context.HEIGHT - (friend.getHeight() + cards.getHeight() + 10));
                }
                stage.addActor(cards);
                stage.addActor(me);
                stage.addActor(friend);

            }
            if (!g.getName().equals(my.getGroup().getName())) {
                System.out.println(g.getName());
                com.badlogic.gdx.scenes.scene2d.Group g1 = new com.badlogic.gdx.scenes.scene2d.Group();
                com.badlogic.gdx.scenes.scene2d.Group g2 = new com.badlogic.gdx.scenes.scene2d.Group();
                Player c1 = g.getPlayerList().get(0);
                Player c2 = g.getPlayerList().get(1);
                TextButton leftPlayer = new TextButton(c1.getName(), Context.getInstance().getSkin());
                TextButton rightPlayer = new TextButton(c2.getName(), Context.getInstance().getSkin());
                leftPlayer.setZIndex(1);
                rightPlayer.setZIndex(1);
                g1.setPosition(leftPlayer.getHeight(), Context.HEIGHT / 2);
                g2.setPosition(Context.WIDTH, Context.HEIGHT / 2);
                g1.addActor(leftPlayer);
                g2.addActor(rightPlayer);
                g1.setRotation(90);
                g2.setRotation(90);
                c1.setDIRECTION(Const.DIRECTION.NORTH);
                Gdx.app.log(c1.getName() + " Direction", c1.getDIRECTION().toString());

                c1.setLocationX(70);
                c1.setLocationY(1);
                c1.setActor(g1);
                c1.setGame(mainGame);
                c1.setView(this);
                c1.setCardToThrowLocations(new float[]{135, 155});
                c2.setDIRECTION(Const.DIRECTION.SOUTH);
                Gdx.app.log(c2.getName() + " Direction", c2.getDIRECTION().toString());
                c2.setLocationX(700);
                c2.setLocationY(1);
                c2.setActor(g2);
                c2.setGame(mainGame);
                c2.setView(this);
                c2.setCardToThrowLocations(new float[]{460, 155});
                playerPosition.put(c1, g1);
                playerPosition.put(c2, g2);

                temp[3] = c1;
                temp[1] = c2;
                if (mainGame.getTurn() == c1.getId()) {
                    cards.setPosition(g1.getX() + leftPlayer.getHeight() + cards.getHeight(), g1.getY());
                    c1.setMyCardPosition(cards.getX(), cards.getY());
                } else if (mainGame.getTurn() == c2.getId()) {
                    cards.setPosition(g2.getX() - cards.getHeight(), g2.getY());
                    c2.setMyCardPosition(cards.getX(), cards.getY());
                }
                stage.addActor(cards);
                stage.addActor(g1);
                stage.addActor(g2);
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

    public void setPositionOfCards() {
        hasToRotate = true;
        Group mine = mainGame.getPlayers().get(getMainGame().getMineId()).getGroup();
        for (Player p : mine.getPlayerList()) {
            if (p.getId() == mainGame.getTurn()) {
                hasToRotate = false;
            }
        }
        if (hasToRotate) {
            cards.setRotation(90);
        } else {
            cards.setRotation(0);
        }

        getSortPlayer().clear();
        for (int i = 0; i < PlayerOnSide.size(); i++) {
            if (PlayerOnSide.get(i).getId() == mainGame.getTurn()) {
                int index = i + 1;
                if (i == PlayerOnSide.size() - 1) {
                    index = 0;
                }
                sortPlayer.add(PlayerOnSide.get(index));
                inIndex(PlayerOnSide, index);
                break;
            }
        }
        getMainGame().setPLAYER_ORDER(sortPlayer);
        getMainGame().setCURRENT_TURN(sortPlayer.get(sortPlayer.size() - 1));
        getMainGame().setPLAYER(mainGame.getPlayers().get(getMainGame().getMineId()));
        getMainGame().setTALK_TURN(sortPlayer.get(0));
        getMainGame().setPLAY_TURN(sortPlayer.get(0));
        getTURN_LABEL().setText(getMainGame().getPLAY_TURN().getName());
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

    ArrayList<Player> sortPlayer;
}
