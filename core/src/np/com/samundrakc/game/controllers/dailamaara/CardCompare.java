package np.com.samundrakc.game.controllers.dailamaara;

import java.util.Comparator;

import np.com.samundrakc.game.anchors.Card;

/**
 * Created by samundra on 2/21/2016.
 */
public class CardCompare implements Comparator<Card> {

    @Override
    public int compare(Card o1, Card o2) {
        return Integer.compare(o1.getNumber(), o2.getNumber());
    }

}
