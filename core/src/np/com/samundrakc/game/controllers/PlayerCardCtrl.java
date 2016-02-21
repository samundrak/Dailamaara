package np.com.samundrakc.game.controllers;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import np.com.samundrakc.game.anchors.Card;
import np.com.samundrakc.game.anchors.Game;
import np.com.samundrakc.game.misc.Animation;
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
            return super.touchDown(event, x, y, pointer, button);
        }

        if (!Game.STARTED) return false;
        System.out.println("youu have touched " + card.getType());
        System.out.println("youu have touched " + card.getCardType().toString());
        System.out.println("youu have touched " + card.getNumber());
        return super.touchDown(event, x, y, pointer, button);
    }

    /**
     * Called any time the mouse is moved when a button is not down. This event only occurs on the desktop. When true is returned,
     * the event is {@link Event#handle() handled}.
     *
     * @param event
     * @param x
     * @param y
     * @see InputEvent
     */
    @Override
    public boolean mouseMoved(InputEvent event, float x, float y) {
//        card.getActor().addAction(Actions.sequence(Animation.sizeActionPlus(120, 130, 0.5f), Animation.sizeActionPlus(100, 120, 0.5f)));
        return super.mouseMoved(event, x, y);
    }

    /**
     * Called any time the mouse cursor or a finger touch is moved over an actor. On the desktop, this event occurs even when no
     * mouse buttons are pressed (pointer will be -1).
     *
     * @param event
     * @param x
     * @param y
     * @param pointer
     * @param fromActor May be null.  @see InputEvent
     */
    @Override
    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
//        card.getActor().addAction(Actions.sequence(Animation.sizeActionPlus(120, 130, 0.5f), Animation.sizeActionPlus(100, 120, 0.5f)));
        super.enter(event, x, y, pointer, fromActor);
    }


}