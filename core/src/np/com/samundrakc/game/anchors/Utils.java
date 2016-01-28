/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package np.com.samundrakc.game.anchors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 *
 * @author samundra
 */
public class Utils {

    public ArrayList shuffleCardsOFGame(ArrayList<Card> cards) {
        ArrayList<Card> card = cards;
        Collections.shuffle(card);
        return card;
    }

    public String getStringInput(String message) {
        if (message != null) {
            System.out.println(message);
        }
        Scanner sc = new Scanner(System.in);
        String name = sc.nextLine();
        while (name.isEmpty() && name.length() < 1) {
            System.out.println(message);
            name = sc.nextLine();
        }
        return name;
    }

    public int getDupliCateCard(int smallId, int[] cardsList) {
        int v = -1;
        int repeat = 0;
        for (int i = 0; i < cardsList.length; i++) {
            if (smallId == cardsList[i]) {
                repeat++;
            }
        }
        if (repeat == 2) {
            return 1;
        }
        return v;
    }

    public int getIntInput(String message) {
        boolean nextStep = true;
        int i = -1;
        while (nextStep) {
            String in = this.getStringInput(message);
            try {
                i = Integer.parseInt(in);
                nextStep = !nextStep;
            } catch (Exception e) {
                System.out.println("You entered wrong format");
            }
        }
        return i;
    }
}
