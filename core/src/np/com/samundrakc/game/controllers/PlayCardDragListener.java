package np.com.samundrakc.game.controllers;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;

import np.com.samundrakc.game.anchors.Card;
import np.com.samundrakc.game.anchors.Game;
import np.com.samundrakc.game.misc.Animation;
import np.com.samundrakc.game.misc.Context;

/**
 * Created by samundra on 2/21/2016.
 */
public class PlayCardDragListener extends DragListener {
    private Card card;
    float x, y;

    public PlayCardDragListener(Card card) {
        this.card = card;
        x = card.getActor().getX();
        y = card.getActor().getY();
    }

    @Override
    public void drag(InputEvent event, float x, float y, int pointer) {
        if (Game.PLAYER.getCardsActor().size() < 13) return;
        super.drag(event, x, y, pointer);
        card.getActor().moveBy(x - card.getActor().getWidth() / 2, y - card.getActor().getHeight() / 2);
    }

    @Override
    public void dragStop(InputEvent event, float x, float y, int pointer) {
        if (Game.PLAYER.getCardsActor().size() < 13) return;
        super.dragStop(event, x, y, pointer);
        if (card.getActor().getY() < 150 || card.getActor().getY() > 300) {
            card.getActor().addAction(Animation.simpleAnimation(this.x, this.y));
        }
    }
}
