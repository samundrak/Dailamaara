package np.com.samundrakc.game.controllers;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;

import java.util.ArrayList;

import np.com.samundrakc.game.anchors.Card;
import np.com.samundrakc.game.anchors.Game;
import np.com.samundrakc.game.anchors.Player;
import np.com.samundrakc.game.misc.Animation;
import np.com.samundrakc.game.misc.Context;

/**
 * Created by samundra on 2/21/2016.
 */
public class PlayCardDragListener extends DragListener {
    private Card card;
    float x, y;
    private Player player;

    public PlayCardDragListener(Card card, Player p) {
        this.card = card;
        this.player = p;
        x = card.getActor().getX();
        y = card.getActor().getY();
    }

    @Override
    public void drag(InputEvent event, float x, float y, int pointer) {
        super.drag(event, x, y, pointer);
        if (!Game.STARTED) return;
        switch (Game.STATE) {
            case GAME_OVER:
                player.active.clear();
                return;
            case PAUSE:
            case STOP:
            case WAIT:
                return;
        }
        if (player.getCardsActor().size() < 13) return;
        card.getActor().moveBy(x - card.getActor().getWidth() / 2, y - card.getActor().getHeight() / 2);
    }

    @Override
    public void dragStop(InputEvent event, float x, float y, int pointer) {
        super.dragStop(event, x, y, pointer);
        if (!Game.STARTED) return;
        switch (Game.STATE) {
            case GAME_OVER:
                player.active.clear();
                return;
            case PAUSE:
            case STOP:
            case WAIT:
                return;
        }
        if (player.getCardsActor().size() < 13) return;
        if (card.getActor().getY() < 70 || card.getActor().getY() > 270) {
            card.getActor().addAction(Animation.simpleAnimation(this.x, this.y));
            return;
        }
        if (card.getActor().getX() < 0 || card.getActor().getX() > Context.WIDTH - card.getActor().getWidth()) {
            card.getActor().addAction(Animation.simpleAnimation(this.x, this.y));
            return;
        }

        if (Game.CARD_PLAYED != null) {
            if (card.getCardType() != Game.CARD_PLAYED) {
                Query query = new Query(player);
                if (query.isThisCardTypeAvailable(Game.CARD_PLAYED)) {
                    card.getActor().addAction(Animation.simpleAnimation(this.x, this.y));
                    return;
                }

            }
        }
        if (Game.PLAY_TURN.getId() == player.getId() && (Game.PLAY_TURN != null)) {
            if (Game.history.size() < 1) {
                Game.history.add(new ArrayList<Card>());
            }
            Game.history.get(Game.history.size() - 1).add(player.removeCardFromMyIndex(card));
            player.getGame().selectPlayOfTurup(player, card);
            player.doExtraStuff();
            card.getActor().clearListeners();
            card.getActor().addAction(Animation.simpleAnimation(player.getCardToThrowLocations()[0], player.getCardToThrowLocations()[1]));
            Game.chooseNextPlayerToBePlayed(player);
            return;
        }
        card.getActor().addAction(Animation.simpleAnimation(this.x, this.y));

    }
}
