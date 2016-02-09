/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package np.com.samundrakc.game.anchors;

import java.util.Arrays;

/**
 *
 * @author samundra
 */
public class DailaMaara {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Game game = new Game();
//        game.createGroups("sa,","ra");
        game.createCards();
        game.shuffleCardsOFGame(Game.cards);
        game.selectPlayerToThrowCardsFirstTime(game.players);
//        for(Card c : Game.cards){
//            System.out.println(c.getNumber());
//            System.out.println(c.getType());
//        }
    }
    
}
