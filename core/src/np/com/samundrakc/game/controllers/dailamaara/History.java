package np.com.samundrakc.game.controllers.dailamaara;

import java.util.ArrayList;

import np.com.samundrakc.game.anchors.Card;
import np.com.samundrakc.game.anchors.Const;
import np.com.samundrakc.game.anchors.Player;

/**
 * Created by samundra on 2/28/2016.
 */
public class History {
    private ArrayList<PlayerHistory> Player = new ArrayList<PlayerHistory>();
    private Player Winner;
    private Const.CARDS playOf;
    private Card isTen = null;

    public void addPlayerWithCards(PlayerHistory pl) {
        Player.add(pl);
    }

    public ArrayList<PlayerHistory> getPlayer() {
        return Player;
    }

    public void setPlayer(ArrayList<PlayerHistory> player) {
        Player = player;
    }

    public Player getWinner() {
        return Winner;
    }

    public void setWinner(Player winner) {
        Winner = winner;
    }

    public Const.CARDS getPlayOf() {
        return playOf;
    }

    public void setPlayOf(Const.CARDS playOf) {
        this.playOf = playOf;
    }

    public Card getIsTen() {
        return isTen;
    }

    public void setIsTen(Card isTen) {
        this.isTen = isTen;
    }

    public static class PlayerHistory {
        Card card;
        Player player;

        public PlayerHistory(Card card, Player player) {
            this.card = card;
            this.player = player;
        }
    }
}
