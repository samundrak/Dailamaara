/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package np.com.samundrakc.game.anchors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

/**
 *
 * @author samundra
 */
public class Game extends Utils implements GameProcess {

    public static ArrayList<Card> cards;
    public final ArrayList<Player> players;
    final ArrayList<Group> group;
    HashMap<String, Group> winner;
    static int turn;
    static int mineId;

    public Game() {
        Game.cards = new ArrayList();
        winner = new HashMap();
        players = new ArrayList(Const.TOTAL_NUMBER_OF_PLAYERS);
        group = new ArrayList(Const.TOTAL_NUMBER_GROUPS);
        turn = -1;
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
                card.setNumber(j);
                Game.cards.add(card);
            }
        }
    }

    /**
     * We will make group of player each group has 2 players no more then 2
     * player can be add as per now
     */
    public void createGroups(String playername,String groupname) {
        Player me = new Player(playername);
        Group myGroup = new Group(groupname);
        Group computerGroup = new Group("Computer");
        myGroup.addPlayer(me);
        this.players.clear();
        this.players.add(me);
        this.players.add(new Player("Computer A"));
        this.players.add(new Player("Computer B"));
        this.players.add(new Player("Computer C"));
        Collections.shuffle(this.players);
        System.out.println("Enter number to select friend");
        for (int i = 0; i < this.players.size(); i++) {
            if (this.players.get(i) == me) {
                Game.mineId = i;
            }
            this.players.get(i).setId(i);
            System.out.println(i + " - " + this.players.get(i).getName());
        }
        boolean nextStep = true;
        while (nextStep) {
            try {
                int id = Integer.parseInt(super.getStringInput("Enter number..."));
                if (id == mineId) {
                    System.out.println("You cant play with yourself...");
                } else if (id < this.players.size()) {
                    System.out.println("You selected: " + this.players.get(id).getName());
                    myGroup.addPlayer(this.players.get(id));
                    for (int i = 0; i < this.players.size(); i++) {
                        if (i != mineId && i != id) {
                            computerGroup.addPlayer(this.players.get(i));
                        }
                    }
                    nextStep = !nextStep;
                } else {
                    System.out.println("You selected some who is not in game");
                }

            } catch (Exception e) {
                System.out.println("You enter wrong format");
            }
        }
        this.group.add(myGroup);
        this.group.add(computerGroup);



        for(Group g : group){
            System.out.println("Group is " + g.getName());

            for(Player p : g.getPlayerList()){
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

        for(Player e : players){
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
