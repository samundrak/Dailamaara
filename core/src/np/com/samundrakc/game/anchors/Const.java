/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package np.com.samundrakc.game.anchors;

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
    static int TOTAL_NUMBER_GROUPS = 2;
    public static final int PLAYER_CARD_DISTANCE = 1;

    public enum DIRECTION {EAST, WEST, NORTH, SOUTH}

    public enum CARDS {CLUBS, HEARTS, DIAMONDS, SPADES}
}
