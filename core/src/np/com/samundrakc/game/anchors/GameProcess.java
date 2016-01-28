/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package np.com.samundrakc.game.anchors;

import java.util.ArrayList;

/**
 *
 * @author samundra
 */
interface GameProcess {
    public   void createCards();
    public   void createGroups();
    public   void assignCardsToPlayers();
    public   void startGame();
    public   void endGame();
    public boolean selectPlayerToThrowCardsFirstTime(ArrayList<Player> palyers);
}
