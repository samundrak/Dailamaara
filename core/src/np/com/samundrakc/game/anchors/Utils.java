/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package np.com.samundrakc.game.anchors;

import com.badlogic.gdx.Gdx;

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


    public static void log(String tag,String message){
        Gdx.app.log(tag,message);
    }
}
