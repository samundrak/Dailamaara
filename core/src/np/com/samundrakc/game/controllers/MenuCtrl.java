package np.com.samundrakc.game.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import np.com.samundrakc.game.DailaMaara;
import np.com.samundrakc.game.screens.Form;

/**
 * Created by samundra on 1/29/2016.
 */
public class MenuCtrl {
    public static class PlayButtonController extends InputListener {
        private static DailaMaara game;

        public PlayButtonController(DailaMaara game) {
            this.game = game;
        }

        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            game.setScreen(new Form(game));
            return super.touchDown(event, x, y, pointer, button);
        }
    }
}
