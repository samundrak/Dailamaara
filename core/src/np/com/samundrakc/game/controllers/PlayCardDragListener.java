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
        super.drag(event, x, y, pointer);
        if (!Game.STARTED) return;
        if (Game.PLAYER.getCardsActor().size() < 13) return;
        card.getActor().moveBy(x - card.getActor().getWidth() / 2, y - card.getActor().getHeight() / 2);
    }

    @Override
    public void dragStop(InputEvent event, float x, float y, int pointer) {
        super.dragStop(event, x, y, pointer);
        if (!Game.STARTED) return;
        if (Game.PLAYER.getCardsActor().size() < 13) return;
        if (card.getActor().getY() < 150 || card.getActor().getY() > 270) {
            card.getActor().addAction(Animation.simpleAnimation(this.x, this.y));
            return;
        }
        if (card.getActor().getX() < 0 || card.getActor().getX() > Context.WIDTH - card.getActor().getWidth()) {
            card.getActor().addAction(Animation.simpleAnimation(this.x, this.y));
            return;
        }

        System.out.println("you selected " + card.getNumber());
        System.out.println("X " + card.getActor().getX());
        System.out.println("Y " + card.getActor().getY());
        if (Game.PLAY_TURN.getId() == Game.PLAYER.getId()) {
            for (int i = 0; i < Game.PLAYER_ORDER.size(); i++) {
                if (Game.PLAYER.getId() == Game.PLAYER_ORDER.get(i).getId()) {
                    int index = i + 1;
                    if (index >= Game.PLAYER_ORDER.size()) {
                        index = 0;
                    }
                    card.getActor().clearListeners();
                    card.getActor().addAction(Animation.simpleAnimation(Game.PLAYER.getCardToThrowLocations()[0], Game.PLAYER.getCardToThrowLocations()[1]));
                    Game.PLAY_TURN = Game.PLAYER_ORDER.get(index);
                    return;
                }
            }
        }
        card.getActor().addAction(Animation.simpleAnimation(this.x, this.y));

    }
}
