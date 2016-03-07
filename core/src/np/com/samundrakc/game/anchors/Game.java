/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package np.com.samundrakc.game.anchors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Timer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

import javax.rmi.CORBA.Util;

import np.com.samundrakc.game.*;
import np.com.samundrakc.game.controllers.Audio;
import np.com.samundrakc.game.controllers.Callback;
import np.com.samundrakc.game.controllers.History;
import np.com.samundrakc.game.controllers.Music;
import np.com.samundrakc.game.controllers.Sound;
import np.com.samundrakc.game.misc.Animation;
import np.com.samundrakc.game.misc.Context;
import np.com.samundrakc.game.misc.MessageBox;
import np.com.samundrakc.game.screens.*;
import np.com.samundrakc.game.screens.DailaMaara;

/**
 * @author samundra
 */
public class Game {

    public int THROWN_CARDS;
    public ArrayList<Card> cards;
    public boolean STARTED;
    public Player PLAY_TURN;
    public int THROWN;
    public Const.STATE STATE;
    public ArrayList<ArrayList<Card>> history;
    public ArrayList<Player> historyOfPlayerWon;
    public ArrayList<History> itihaas;

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Card> cards) {
        cards = cards;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public final ArrayList<Player> players;

    public ArrayList<Group> getGroup() {
        return group;
    }

    final ArrayList<Group> group;
    HashMap<String, Group> winner;
    public static int turn;
    public int mineId;
    public HashMap<Const.CARDS, Image> COLORS;
    public Player TALK_TURN;
    public Player CURRENT_TURN;
    public Player PLAYER;
    public Const.CARDS TURUP;
    public String TURUP_STRING;
    public Const.CARDS CARD_PLAYED;
    public ArrayList<Player> PLAYER_ORDER;
    public Stage GAME_STAGE;
    private DailaMaara view;
    private int name = 1;

    public DailaMaara getView() {
        return view;
    }

    public void setView(DailaMaara view) {
        this.view = view;
    }

    public Game() {
        cards = new ArrayList();
        winner = new HashMap();
        players = new ArrayList(Const.TOTAL_NUMBER_OF_PLAYERS);
        group = new ArrayList(Const.TOTAL_NUMBER_GROUPS);
        turn = -1;
        THROWN_CARDS = 0;
        STARTED = false;
        PLAY_TURN = null;
        THROWN = 0;
        STATE = Const.STATE.PLAY;
        history = new ArrayList<ArrayList<Card>>();
        historyOfPlayerWon = new ArrayList<Player>();
        itihaas = new ArrayList<History>();
        COLORS = new HashMap<Const.CARDS, Image>();
        COLORS.put(Const.CARDS.CLUBS, new Image(Const.COLORS_ACTOR.get(Const.CARDS.CLUBS)));
        COLORS.put(Const.CARDS.DIAMONDS, new Image(Const.COLORS_ACTOR.get(Const.CARDS.DIAMONDS)));
        COLORS.put(Const.CARDS.SPADES, new Image(Const.COLORS_ACTOR.get(Const.CARDS.SPADES)));
        COLORS.put(Const.CARDS.HEARTS, new Image(Const.COLORS_ACTOR.get(Const.CARDS.HEARTS)));
        TALK_TURN = null;
        CURRENT_TURN = null;
        PLAYER = null;
        TURUP = null;
        TURUP_STRING = null;
        CARD_PLAYED = null;
        PLAYER_ORDER = new ArrayList<Player>();
        GAME_STAGE = null;
    }

    /**
     * This function will help us to create cards and store it in a arraylist of
     * cards
     */

    public void createCards() {
        for (int i = 0; i < Const.TOTAL_NUMBER_OF_COLORS; i++) {
            for (int j = 0; j < Const.TOTAL_NUMBER_OF_CARDS_IN_COLORS; j++) {
                Card card = new Card(Const.COLORS_NAME[i], i, (j + 1));
                switch (i) {
                    case 0:
                        card.setCardType(Const.CARDS.CLUBS);
                        break;
                    case 1:
                        card.setCardType(Const.CARDS.HEARTS);
                        break;
                    case 2:
                        card.setCardType(Const.CARDS.DIAMONDS);
                        break;
                    case 3:
                        card.setCardType(Const.CARDS.SPADES);
                        break;
                }
                card.setActor();
                cards.add(card);
            }
        }
    }

    /**
     * We will make group of player each group has 2 players no more then 2
     * player can be add as per now
     */
    public void createGroups(String playername, String groupname, int frenId) {
        Player me = new Player(playername);
        Group myGroup = new Group(groupname);
        Group computerGroup = new Group("Computer");
        me.setGroup(myGroup);
        myGroup.addPlayer(me);
        this.players.clear();
        this.players.add(me);
        this.players.add(new Player("Computer 1"));
        this.players.add(new Player("Computer 2"));
        this.players.add(new Player("Computer 3"));

        System.out.println("Enter number to select friend");
        for (int i = 0; i < this.players.size(); i++) {
            if (this.players.get(i) == me) {
                mineId = i;
            }
            this.players.get(i).setId(i);
            System.out.println(i + " - " + this.players.get(i).getName());
        }
        int id = frenId;
        if (id == mineId) {
            System.out.println("You cant play with yourself...");
        } else if (id < this.players.size()) {
            System.out.println("You selected: " + this.players.get(id).getName());
            this.players.get(mineId).setFriend(this.players.get(id));
            this.players.get(id).setFriend(this.players.get(mineId));
            myGroup.addPlayer(this.players.get(id));
            this.players.get(id).setGroup(myGroup);
            for (int i = 0; i < this.players.size(); i++) {
                if (i != mineId && i != id) {
                    computerGroup.addPlayer(this.players.get(i));
                    this.players.get(i).setGroup(computerGroup);
                }
            }
            this.players.get(computerGroup.getPlayerList().get(0).getId()).setFriend(computerGroup.getPlayerList().get(1));
            this.players.get(computerGroup.getPlayerList().get(1).getId()).setFriend(computerGroup.getPlayerList().get(0));
        } else {
            System.out.println("You selected some who is not in game");
        }
        this.group.add(myGroup);
        this.group.add(computerGroup);
    }

    public void chooseNextPlayerToBePlayed(Player player) {
        if (CARD_PLAYED == null) return;
        for (int i = 0; i < PLAYER_ORDER.size(); i++) {
            if (player.getId() == PLAYER_ORDER.get(i).getId()) {
                int index = i + 1;
                if (index >= PLAYER_ORDER.size()) {
                    index = 0;
                }
                PLAY_TURN = PLAYER_ORDER.get(index);
                DailaMaara.TURN_LABEL.setText(PLAY_TURN.getName());
                break;
            }
        }
    }

    public void updateThrownCardsStacks(final Player player) {
        if (THROWN == Const.TOTAL_NUMBER_OF_PLAYERS) {

            if (history.size() > 0) {
                if (history.get(history.size() - 1).size() >= 4) {
                    STATE = Const.STATE.WAIT;
                    //Four Cards Per Play has been done
                    new Timer().scheduleTask(new Timer.Task() {
                        @Override
                        public void run() {

                            if (STATE != Const.STATE.GAME_OVER) {
                                //Game is note over still
                                if (PLAYER_ORDER.get(PLAYER_ORDER.size() - 1).getId() == player.getId()) {

                                    int size = 0;
                                    int indexOfWinner = 0;
                                    Player turn = null;
                                    for (int i = 0; i < PLAYER_ORDER.size(); i++) {
                                        if (PLAYER_ORDER.get(i).getThrownCard().getNumber() > size) {
                                            size = PLAYER_ORDER.get(i).getThrownCard().getNumber();
                                            turn = PLAYER_ORDER.get(i);
                                            indexOfWinner = i;
                                        }
                                    }
                                    size = 0;
                                    for (int i = 0; i < PLAYER_ORDER.size(); i++) {
                                        if (PLAYER_ORDER.get(i).getThrownCard().getCardType() == TURUP) {
                                            if (PLAYER_ORDER.get(i).getThrownCard().getNumber() > size) {
                                                size = PLAYER_ORDER.get(i).getThrownCard().getNumber();
                                                turn = PLAYER_ORDER.get(i);
                                                indexOfWinner = i;
                                            }
                                        }
                                    }

                                    ArrayList<Player> pl = new ArrayList<Player>();
                                    pl.add(turn);
                                    for (int i = indexOfWinner + 1; i < PLAYER_ORDER.size(); i++) {
                                        pl.add(PLAYER_ORDER.get(i));
                                    }
                                    for (int i = 0; i < indexOfWinner; i++) {
                                        pl.add(PLAYER_ORDER.get(i));
                                    }
                                    PLAYER_ORDER.clear();
                                    PLAYER_ORDER = pl;
                                    PLAY_TURN = turn;
                                    DailaMaara.TURN_LABEL.setText(PLAY_TURN.getName());
                                    historyOfPlayerWon.add(turn);
                                    turn.getGroup().setHands(turn.getGroup().getHands() + 1);
                                    itihaas.get(itihaas.size() - 1).setWinner(turn);
                                    turn.getView().getGroupHandsStatusLabel().get(turn.getGroup().getName()).addAction(Actions.sequence(
                                            Animation.moveBy(5, 0, 0.2f),
                                            Animation.moveByAnime(-5, 0, 0.5f)
                                    ));
                                    turn.getView().getGroupHandsStatusLabel().get(turn.getGroup().getName()).setText(turn.getGroup().getHands() + "");
                                    float x = Context.WIDTH / 2, y = Context.HEIGHT + 100;
                                    switch (turn.DIRECTION) {
                                        case EAST:
                                        case WEST:
                                            x = Context.WIDTH / 2;
                                            y = Context.HEIGHT + 100;
                                            break;
                                        case NORTH:
                                        case SOUTH:
                                            x = Context.WIDTH;
                                            y = Context.HEIGHT / 2;
                                            break;
                                    }
                                    int totalTens = 0;
                                    for (Group group : PLAYER.getGame().group) {
                                        totalTens += group.getTens();
                                    }
                                    boolean isThereTen = false;
                                    float tenX = 0, tenY = 0;
                                    for (final Card c : history.get(history.size() - 1)) {
                                        Sound.getInstance().play(Sound.AUDIO.COLLAPSED);
                                        if (c.getNumber() == 10) {
                                            isThereTen = true;
                                            tenY = c.getActor().getY() + c.getActor().getHeight() / 2;
                                            tenX = c.getActor().getX() + c.getActor().getWidth() / 2;
                                            turn.getGroup().setTens(turn.getGroup().getTens() + 1);
                                            turn.getView().getGroupTensStatusLabel().get(turn.getGroup().getName()).addAction(Actions.sequence(
                                                    Animation.moveBy(5, 0, 0.2f),
                                                    Animation.moveByAnime(-5, 0, 0.5f)
                                            ));
                                            turn.getView().getGroupTensStatusLabel().get(turn.getGroup().getName()).setText(turn.getGroup().getTens() + "");
                                        }
                                        c.getActor().addAction(Animation.sizeActionPlus(30, 50, 1));
                                        c.getActor().addAction(Actions.sequence(Animation.moveBy(
                                                x - c.getActor().getX(),
                                                y - c.getActor().getY(),
                                                1), new RunnableAction() {
                                            @Override
                                            public void run() {
//                                                c.getActor().clear();
//                                                c.getActor().remove();
                                                c.getActor().setVisible(false);

                                            }
                                        }));
                                    }
                                    if (isThereTen) {
                                        if (turn.getGroup().getName() == PLAYER.getGroup().getName()) {
                                            view.getTensEffects().setPosition(tenX, tenY);
                                            view.getTensEffects().start();
                                            Sound.getInstance().play(Audio.AUDIO.SPALSH_TEN);
                                        } else {
                                            Sound.getInstance().play(Audio.AUDIO.TEN_GONE);
                                        }
                                    }
                                    if (turn.getGroup().getName() == PLAYER.getGroup().getName()) {
                                        Sound.getInstance().play(Audio.AUDIO.CARD_WON);
                                    }
                                    if (totalTens == 4) {
                                        if (PLAYER.getGroup().getTens() != 2) {
                                            if (Const.STATE.GAME_OVER != STATE) {
                                                gameOver();
                                                return;
                                            }
                                        }
                                    }
                                    if (history.size() >= 13) {
                                        //When game is over work is done in this block
                                        if (Const.STATE.GAME_OVER != STATE) {
                                            gameOver();
                                        }
                                    }
                                    history.add(new ArrayList<Card>());
                                    itihaas.add(new History());
                                    THROWN = 0;
                                    CARD_PLAYED = null;
                                }

                                STATE = Const.STATE.PLAY;
                            }
                        }
                    }, 1);
                }
            }

        }
    }

    public void gameOver() {
        STATE = Const.STATE.GAME_OVER;
        DailaMaara.TURN_LABEL.setText("Game Over");
        int won = 0;
        Group g = null;
        if (!getView().getGoUp().isVisible()) {
            getView().setToggle();
        }
        np.com.samundrakc.game.DailaMaara.GAME_MUSIC.setVolume(0.1f);
        np.com.samundrakc.game.DailaMaara.GAME_MUSIC.stop();
        np.com.samundrakc.game.DailaMaara.GAME_MUSIC.dispose();
        np.com.samundrakc.game.DailaMaara.GAME_MUSIC = Music.getInstance().playMusic(Audio.AUDIO.GAME_LOST);
        np.com.samundrakc.game.DailaMaara.GAME_MUSIC.setLooping(true);
        np.com.samundrakc.game.DailaMaara.GAME_MUSIC.setVolume(0.2f);
        String type = "Hands";
        if (PLAYER.getGroup().getTens() == 2) {
            for (Group group : getGroup()) {
                if (group.getHands() > won) {
                    won = group.getHands();
                    g = group;
                }
            }
        }
        if (g == null) {
            won = 0;
            for (Group group : getGroup()) {
                if (group.getTens() > won) {
                    won = group.getTens();
                    g = group;
                    type = "Tens";
                }
            }
        }
        if (g.getTens() == 4) {
            g.setCoat(g.getCoat() + 1);
            getView().getGroupCoatStatusLabel().get(g.getName()).setText(g.getCoat() + "");
        }
        g.setWon(g.getWon() + 1);
        final com.badlogic.gdx.audio.Sound fw;
        if (g.getName() == PLAYER.getGroup().getName()) {
            fw = Sound.getInstance().playWithInstance(Audio.AUDIO.FIRE_WORKS);
            fw.loop();
            getView().getPfx().getEffectHashMap().get("win").setPosition(Context.WIDTH / 2, 0);
            getView().getPfx().getEffectHashMap().get("win").start();

        } else {
            fw = null;
        }
        final Timer timer = new Timer();
        getView().getGroupWonStatusLabel().get(g.getName()).setText(g.getWon() + "");
        new MessageBox(GAME_STAGE, g.getName() + " won the game with " + won + " " + type + " ! Play Again?", new MessageBox.OnOkButtonClicked() {
            @Override
            public void run() {
                try {
//                    continueCurrentGame();
                    if (timer != null) timer.clear();
                    if (fw != null) fw.stop();
                    getView().getPfx().getEffectHashMap().get("win").dispose();

                } catch (NullPointerException ne) {

                } catch (Exception e) {

                } finally {
                    continueCurrentGame();
                }
            }
        }).setInMiddle(true).setOkButtonText("Continue").show();
        timer.scheduleTask(new Timer.Task() {

            @Override
            public void run() {
                if (getView().getPfx().getEffectHashMap().get("win").isComplete()) {
                    if (fw != null) fw.stop();
                    timer.clear();
                }
            }
        }, 1, 1);
    }

    private ArrayList<Actor> indexOfPlayOfTurups = new ArrayList<Actor>();

    public void continueCurrentGame() {
//        getView().getParentGame()
//                .setScreen(
//                        new LoadingScreen(
//                                getView().getParentGame()
//                        )
//                                .otherScreen(
//                                        new DailaMaara(
//                                                getView().getParentGame(),
//                                                this).setCardsStacks(getView().getCardsStacks()
//                                        )
//                                )
//                );
        getView().getParentGame().setScreen(new LoadingScreen(getView().getParentGame()).otherScreen(new Form(getView().getParentGame())));
//        getView().getParentGame().setScreen(new DailaMaara());
        np.com.samundrakc.game.DailaMaara.GAME_MUSIC.setVolume(0.1f);
        np.com.samundrakc.game.DailaMaara.GAME_MUSIC.stop();
        np.com.samundrakc.game.DailaMaara.GAME_MUSIC.dispose();
        np.com.samundrakc.game.DailaMaara.GAME_MUSIC = Music.getInstance().playMusic(Audio.AUDIO.GAME_MUSIC);
        np.com.samundrakc.game.DailaMaara.GAME_MUSIC.setLooping(true);
        np.com.samundrakc.game.DailaMaara.GAME_MUSIC.setVolume(0.2f);
        setView(null);
        setGAME_STAGE(null);
//        for (int i = 0; i < Const.TOTAL_NUMBER_OF_CARDS; i++) {
//            getView().getCardsStacks().getChildren().get(i).addAction(Animation.simpleAnimation(3, 3));
//            getView().getCardsStacks().getChildren().get(i).clearListeners();
//            Game.getCards().get(i).getActor().setVisible(true);
//            Game.getCards().get(i).getActor().setSize(50, 70);
//        }
//        for (Group g : getGroup()) {
//            getView().getGroupThrownStatusLabel().get(g.getName()).setText("0");
//            getView().getGroupTensStatusLabel().get(g.getName()).setText("0");
//            getView().getGroupHandsStatusLabel().get(g.getName()).setText("0");
//        }
//        getView().setPositionOfCards();
//        Game.history.clear();
//        Game.itihaas.clear();
//        Game.THROWN = 0;
//        Game.CARD_PLAYED = null;
//        Game.STATE = Const.STATE.PLAY;
    }

    public void selectPlayOfTurup(Player player, Card card) {
        if (THROWN == 0) {
            if (indexOfPlayOfTurups.size() > 0) {
                for (int i = 0; i < indexOfPlayOfTurups.size(); i++) {
                    indexOfPlayOfTurups.get(i).clearActions();
                    indexOfPlayOfTurups.get(i).clearListeners();
                    indexOfPlayOfTurups.get(i).clear();
                    indexOfPlayOfTurups.get(i).remove();
                }
            } else {
                if (player.getView().getPlayOfTable().getCells().size > 1) {
                    player.getView().getPlayOfTable().getCells().get(1).clearActor();
                }
            }
            Actor c = new Image(Const.COLORS_ACTOR.get(card.getCardType()));
            c.setSize(10, 10);
            c.setPosition(player.getView().getPlayOfTable().getX() + 10, player.getView().getPlayOfTable().getY() + 8);
            c.addAction(Animation.sizeActionPlusWithAnime(50, 60, 0.6f));
            indexOfPlayOfTurups.add(c);
            CARD_PLAYED = card.getCardType();
            GAME_STAGE.addActor(c);
            itihaas.get(itihaas.size() - 1).setPlayOf(card.getCardType());
        }
    }

    public ArrayList shuffleCardsOFGame(ArrayList<Card> cards) {
        ArrayList<Card> card = cards;
        Collections.shuffle(card);
        return card;
    }

    public void turupMove() {
        if (TURUP == null) return;
        Image turup = COLORS.get(TURUP);
        turup.setPosition(Context.WIDTH / 2, (Context.HEIGHT / 2) + 50);
        Sound.getInstance().play(Audio.AUDIO.SPLASH_TURUP);
        getView().getPfx().getEffectHashMap().get(TURUP.toString()).setPosition(turup.getX(), turup.getY());
        getView().getPfx().getEffectHashMap().get(TURUP.toString()).start();
        turup.addAction(Actions.sequence(Animation.sizeActionPlusWithAnime(50, 60, 1),
                Animation.simpleAnimation(getView().getTurupTable().getX() + 10, getView().getTurupTable().getY() + 8),
                new RunnableAction() {
                    /**
                     * Called to run the runnable.
                     */
                    @Override
                    public void run() {
                        getView().getNonTururp().remove();

                    }
                }));
        getView().getStage().addActor(turup);
    }

    public int getTHROWN_CARDS() {
        return THROWN_CARDS;
    }

    public void setTHROWN_CARDS(int THROWN_CARDS) {
        this.THROWN_CARDS = THROWN_CARDS;
    }

    public boolean isSTARTED() {
        return STARTED;
    }

    public void setSTARTED(boolean STARTED) {
        this.STARTED = STARTED;
    }

    public Player getPLAY_TURN() {
        return PLAY_TURN;
    }

    public void setPLAY_TURN(Player PLAY_TURN) {
        this.PLAY_TURN = PLAY_TURN;
    }

    public int getTHROWN() {
        return THROWN;
    }

    public void setTHROWN(int THROWN) {
        this.THROWN = THROWN;
    }

    public Const.STATE getSTATE() {
        return STATE;
    }

    public void setSTATE(Const.STATE STATE) {
        this.STATE = STATE;
    }

    public ArrayList<ArrayList<Card>> getHistory() {
        return history;
    }

    public void setHistory(ArrayList<ArrayList<Card>> history) {
        this.history = history;
    }

    public ArrayList<Player> getHistoryOfPlayerWon() {
        return historyOfPlayerWon;
    }

    public void setHistoryOfPlayerWon(ArrayList<Player> historyOfPlayerWon) {
        this.historyOfPlayerWon = historyOfPlayerWon;
    }

    public ArrayList<History> getItihaas() {
        return itihaas;
    }

    public void setItihaas(ArrayList<History> itihaas) {
        this.itihaas = itihaas;
    }

    public HashMap<String, Group> getWinner() {
        return winner;
    }

    public void setWinner(HashMap<String, Group> winner) {
        this.winner = winner;
    }

    public static int getTurn() {
        return turn;
    }

    public static void setTurn(int turn) {
        Game.turn = turn;
    }

    public int getMineId() {
        return mineId;
    }

    public void setMineId(int mineId) {
        this.mineId = mineId;
    }

    public HashMap<Const.CARDS, Image> getCOLORS() {
        return COLORS;
    }

    public void setCOLORS(HashMap<Const.CARDS, Image> COLORS) {
        this.COLORS = COLORS;
    }

    public Player getTALK_TURN() {
        return TALK_TURN;
    }

    public void setTALK_TURN(Player TALK_TURN) {
        this.TALK_TURN = TALK_TURN;
    }

    public Player getCURRENT_TURN() {
        return CURRENT_TURN;
    }

    public void setCURRENT_TURN(Player CURRENT_TURN) {
        this.CURRENT_TURN = CURRENT_TURN;
    }

    public Player getPLAYER() {
        return PLAYER;
    }

    public void setPLAYER(Player PLAYER) {
        this.PLAYER = PLAYER;
    }

    public Const.CARDS getTURUP() {
        return TURUP;
    }

    public void setTURUP(Const.CARDS TURUP) {
        this.TURUP = TURUP;
    }

    public String getTURUP_STRING() {
        return TURUP_STRING;
    }

    public void setTURUP_STRING(String TURUP_STRING) {
        this.TURUP_STRING = TURUP_STRING;
    }

    public Const.CARDS getCARD_PLAYED() {
        return CARD_PLAYED;
    }

    public void setCARD_PLAYED(Const.CARDS CARD_PLAYED) {
        this.CARD_PLAYED = CARD_PLAYED;
    }

    public ArrayList<Player> getPLAYER_ORDER() {
        return PLAYER_ORDER;
    }

    public void setPLAYER_ORDER(ArrayList<Player> PLAYER_ORDER) {
        this.PLAYER_ORDER = PLAYER_ORDER;
    }

    public Stage getGAME_STAGE() {
        return GAME_STAGE;
    }

    public void setGAME_STAGE(Stage GAME_STAGE) {
        this.GAME_STAGE = GAME_STAGE;
    }
}
