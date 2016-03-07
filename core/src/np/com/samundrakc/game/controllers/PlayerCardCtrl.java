package np.com.samundrakc.game.controllers;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
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
    private float x, y;
    private Game mainGame;

    public PlayerCardCtrl(Card card, CardDistribution context, Game mainGame) {
        this.card = card;
        this.mainGame = mainGame;
        this.context = context;
        this.x = card.getActor().getX();
        this.y = card.getActor().getY();
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

        if (mainGame.getTURUP() == null) {
            if (mainGame.getTALK_TURN().getId() == mainGame.getPLAYER().getId()) {
                Sound.getInstance().play(Sound.AUDIO.CARD_TOUCHED);
                mainGame.setTURUP(card.getCardType());
                mainGame.setTURUP_STRING(card.getType());
                context.getGame().getMainGame().turupMove();
            }
            return super.touchDown(event, x, y, pointer, button);
        }

        if (!mainGame.isSTARTED()) return false;
        Sound.getInstance().play(Sound.AUDIO.CARD_TOUCHED);
        card.getActor().moveBy(x - card.getActor().getWidth() / 2, y - card.getActor().getHeight() / 2);
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

    @Override
    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        super.touchUp(event, x, y, pointer, button);
        System.out.println("relesed1");
        if (!mainGame.isSTARTED()) return;
        card.getActor().addAction(Animation.simpleAnimation(this.x, this.y));
    }

    @Override
    public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
        super.exit(event, x, y, pointer, toActor);
        if (!mainGame.isSTARTED()) return;
        card.getActor().addAction(Animation.simpleAnimation(this.x, this.y));
    }


}
