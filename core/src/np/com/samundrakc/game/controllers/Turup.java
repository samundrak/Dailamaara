package np.com.samundrakc.game.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

import np.com.samundrakc.game.anchors.Card;
import np.com.samundrakc.game.anchors.Const;
import np.com.samundrakc.game.anchors.Game;
import np.com.samundrakc.game.anchors.Player;
import np.com.samundrakc.game.misc.Utils;

/**
 * Created by samundra on 2/20/2016.
 */
public class Turup {

    private Player player;
    private Const.CARDS turup = null;

    public String getTurupString() {
        return turupString;
    }

    public void setTurupString(String turupString) {
        this.turupString = turupString;
    }

    private String turupString = null;

    public Const.CARDS getTurup() {
        return turup;
    }

    public void setTurup(Const.CARDS turup) {
        this.turup = turup;
    }


    private int sortNumber = 5;
    HashMap<Const.CARDS, ArrayList<Integer>> hisCards = new HashMap<Const.CARDS, ArrayList<Integer>>();

    public Turup(Player player) {
        this.player = player;
        hisCards.put(Const.CARDS.CLUBS, new ArrayList<Integer>());
        hisCards.put(Const.CARDS.DIAMONDS, new ArrayList<Integer>());
        hisCards.put(Const.CARDS.HEARTS, new ArrayList<Integer>());
        hisCards.put(Const.CARDS.SPADES, new ArrayList<Integer>());
    }

    public void sortFromFewCards() {
        for (int i = 0; i < sortNumber; i++) {
            switch (this.player.getCards().get(i).getCardType()) {
                case CLUBS:
                    hisCards.get(Const.CARDS.CLUBS).add(this.player.getCards().get(i).getNumber());
                    break;
                case DIAMONDS:
                    hisCards.get(Const.CARDS.DIAMONDS).add(this.player.getCards().get(i).getNumber());
                    break;
                case SPADES:
                    hisCards.get(Const.CARDS.SPADES).add(this.player.getCards().get(i).getNumber());
                    break;
                case HEARTS:
                    hisCards.get(Const.CARDS.HEARTS).add(this.player.getCards().get(i).getNumber());
                    break;
            }
        }
    }

    public void viewMax() {
        ArrayList<Integer> index = new ArrayList<Integer>();
        index.add(hisCards.get(Const.CARDS.CLUBS).size());
        index.add(hisCards.get(Const.CARDS.HEARTS).size());
        index.add(hisCards.get(Const.CARDS.DIAMONDS).size());
        index.add(hisCards.get(Const.CARDS.SPADES).size());

        int max = Collections.max(index);
        ArrayList<Integer> dups = new ArrayList<Integer>();
        for (int i = 0; i < index.size(); i++) {
            if (index.get(i) == max) {
                dups.add(i);
            }
        }
        boolean allHaveEqual = false;
        for (int i : index) {
            if (i == 1) {
                allHaveEqual = true;
            } else {
                allHaveEqual = false;
            }
        }

        if (dups.size() < 2 && !allHaveEqual) {
            setTurup(Const.COLORS_NAME_TYPE[dups.get(0)]);
            setTurupString(Const.COLORS_NAME[dups.get(0)]);
            return;
        }

        ArrayList<Integer> sum = new ArrayList<Integer>();
        for (int i = 0; i < dups.size(); i++) {
            sum.add(0);
            for (int s : hisCards.get(Const.COLORS_NAME_TYPE[dups.get(i)])) {
                sum.set(i, sum.get(i) + ((s == 1) ? 14 : s));
            }
        }

        max = Collections.max(sum);
        int highIndex = 0;
        try {
            try {
                highIndex = dups.get(sum.indexOf(max));
            } catch (Exception e) {
                highIndex = 0;
            } finally {
                if (highIndex >= dups.size()) {
                    if (dups.size() < 1) {
                        setTurup(Const.COLORS_NAME_TYPE[0]);
                        setTurupString(Const.COLORS_NAME[0]);
                    } else {
                        setTurup(Const.COLORS_NAME_TYPE[dups.size() - 1]);
                        setTurupString(Const.COLORS_NAME[dups.size() - 1]);
                    }
                    return;
                }
                setTurup(Const.COLORS_NAME_TYPE[dups.get(highIndex)]);
                setTurupString(Const.COLORS_NAME[dups.get(highIndex)]);
            }
        } catch (Exception e) {
            setTurup(Const.COLORS_NAME_TYPE[0]);
            setTurupString(Const.COLORS_NAME[0]);
        }
    }
}
