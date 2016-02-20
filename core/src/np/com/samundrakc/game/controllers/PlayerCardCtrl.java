package np.com.samundrakc.game.controllers;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import np.com.samundrakc.game.anchors.Card;
import np.com.samundrakc.game.anchors.Game;
import np.com.samundrakc.game.misc.Context;

/**
 * Created by samundra on 2/20/2016.
 */
public class PlayerCardCtrl extends InputListener {
    private Card card;
    private CardDistribution context;

    public PlayerCardCtrl(Card card, CardDistribution context) {
        this.card = card;
        this.context = context;
    }

    /**
     * Called when a mouse button or a finger touch goes down on the actor. If true is returned, this listener will receive all
     * touchDragged and touchUp events, even those not over this actor, until touchUp is received. Also when true is returned, the
     * event is {@link Event#handle() handled}.
     *
     * @param event
     * @param x
     * @param y
     * @param pointer
     * @param button
     * @see InputEvent
     */
    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        if (Game.TURUP == null) {
            if (Game.TALK_TURN.getId() == Game.PLAYER.getId()) {
                Game.TURUP = card.getCardType();
                Game.TURUP_STRING = card.getType();
                context.turupMove();

            }
        }
        System.out.println("youu have touched " + card.getType());
        System.out.println("youu have touched " + card.getCardType().toString());
        System.out.println("youu have touched " + card.getNumber());
        return super.touchDown(event, x, y, pointer, button);
    }

    /**
     * Called when a mouse button or a finger touch is moved anywhere, but only if touchDown previously returned true for the mouse
     * button or touch. The touchDragged event is always {@link Event#handle() handled}.
     *
     * @param event
     * @param x
     * @param y
     * @param pointer
     * @see InputEvent
     */
    @Override
    public void touchDragged(InputEvent event, float x, float y, int pointer) {
        super.touchDragged(event, x, y, pointer);
    }
}
