/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package np.com.samundrakc.game.anchors;

import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;

/**
 * @author samundra
 */
public class Const {

    public static final int TOTAL_PLAYER_IN_GAME = 2;
    public static final int TOTAL_NUMBER_OF_CARDS = 52;
    public static final int TOTAL_NUMBER_OF_COLORS = 4;
    public static final int TOTAL_NUMBER_OF_CARDS_IN_COLORS = 13;
    public static int TOTAL_NUMBER_OF_PLAYERS = 4;
    public static final String[] COLORS_NAME = {"clubs", "hearts", "diamonds", "spades"};
    public static final CARDS[] COLORS_NAME_TYPE = {CARDS.CLUBS, CARDS.HEARTS, CARDS.DIAMONDS, CARDS.SPADES};

    public static enum STATE {PLAY, PAUSE, GAME_OVER, STOP, WAIT}

    static int TOTAL_NUMBER_GROUPS = 2;
    public static final int PLAYER_CARD_DISTANCE = 1;

    public enum DIRECTION {EAST, WEST, NORTH, SOUTH}

    public enum CARDS {CLUBS, HEARTS, DIAMONDS, SPADES}

    public static final float CARD_DISTRUBUTION_SECONDS = 0.1f;
    public static final float CARD_ROTATE_ANIMATION = 0.5f;
    public static final float CARD_DISTRUBUTION_SECONDS_PER_PLAYER = 0.3f;
    public static final HashMap<Const.CARDS, Texture> COLORS_ACTOR = new HashMap<CARDS, Texture>();

    public static final void loadColorsActor() {
        Const.COLORS_ACTOR.put(CARDS.CLUBS, new Texture("cards/clubs.png"));
        Const.COLORS_ACTOR.put(CARDS.DIAMONDS, new Texture("cards/diamonds.png"));
        Const.COLORS_ACTOR.put(CARDS.SPADES, new Texture("cards/spades.png"));
        Const.COLORS_ACTOR.put(CARDS.HEARTS, new Texture("cards/hearts.png"));
    }

    public static enum MISC {WON}
}
