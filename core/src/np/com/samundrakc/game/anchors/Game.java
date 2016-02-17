/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package np.com.samundrakc.game.anchors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

/**
 * @author samundra
 */
public class Game extends Utils implements GameProcess {

    public static int THROWN_CARDS = 0;
    public static ArrayList<Card> cards;

    public static ArrayList<Card> getCards() {
        return cards;
    }

    public static void setCards(ArrayList<Card> cards) {
        Game.cards = cards;
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
    public static int mineId;
    public static HashMap<String, Image> COLORS = new HashMap<String, Image>();
    public static Player TALK_TURN = null;
    public static Player CURRENT_TURN = null;
    public static Player PLAYER = null;
    public static Card TURUP = null;

    public Game() {
        Game.cards = new ArrayList();
        winner = new HashMap();
        players = new ArrayList(Const.TOTAL_NUMBER_OF_PLAYERS);
        group = new ArrayList(Const.TOTAL_NUMBER_GROUPS);
        turn = -1;
        Game.COLORS.put(Const.COLORS_NAME[0], new Image(new Texture("cards/clubs.png")));
        Game.COLORS.put(Const.COLORS_NAME[2], new Image(new Texture("cards/diamonds.png")));
        Game.COLORS.put(Const.COLORS_NAME[3], new Image(new Texture("cards/spades.png")));
        Game.COLORS.put(Const.COLORS_NAME[1], new Image(new Texture("cards/hearts.png")));
    }

    /**
     * This function will help us to create cards and store it in a arraylist of
     * cards
     */
    @Override
    public void createCards() {
        for (int i = 0; i < Const.TOTAL_NUMBER_OF_COLORS; i++) {
            for (int j = 0; j < Const.TOTAL_NUMBER_OF_CARDS_IN_COLORS; j++) {
                Card card = new Card(Const.COLORS_NAME[i]);
                card.setId(i);
                card.setNumber((j + 1));
                card.setActor();
                Game.cards.add(card);
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
                Game.mineId = i;
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


        for (Group g : group) {
            System.out.println("Group is " + g.getName());

            for (Player p : g.getPlayerList()) {
                System.out.println("Player is " + p.getName());

            }
        }
    }

    /**
     * Simply we will assign to players now each player will get random cards
     */
    @Override
    public void assignCardsToPlayers() {

    }

    /**
     * We are going too start the game now
     */
    @Override
    public void startGame() {

    }

    /**
     * Simply end the game
     */
    @Override
    public void endGame() {
    }

    @Override
    public boolean selectPlayerToThrowCardsFirstTime(ArrayList<Player> players) {
        if (Game.turn != -1) {
            return false;
        }
        int i = super.getIntInput("Enter any  number from " + 0 + " to " + (Const.TOTAL_NUMBER_OF_CARDS - 1));
        int[] computerSelectedCards = new int[players.size()];

        for (Player e : players) {
            if (e.getId() != Game.mineId) {
                int selected = Game.cards.get(new Random().ints(1, Const.TOTAL_NUMBER_OF_CARDS).findFirst().getAsInt()).getNumber();
                computerSelectedCards[e.getId()] = selected;
                System.out.println(e.getName() + " Selected " + selected);
            }
        }
        if (players.contains(this.players.get(Game.mineId))) {
            while (i < 0 && i > 52) {
                i = super.getIntInput("Incorrect number enter again");
            }
        }

        computerSelectedCards[Game.mineId] = Game.cards.get(i).getNumber();
        System.out.println("You selected " + Game.cards.get(i).getNumber());
        Arrays.sort(computerSelectedCards);
        System.out.println(Arrays.toString(computerSelectedCards));
        return true;
    }
}
