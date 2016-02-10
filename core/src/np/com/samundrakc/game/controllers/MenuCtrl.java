package np.com.samundrakc.game.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import java.awt.Menu;

import np.com.samundrakc.game.DailaMaara;
import np.com.samundrakc.game.misc.Animation;
import np.com.samundrakc.game.misc.MessageBox;
import np.com.samundrakc.game.screens.Form;
import np.com.samundrakc.game.screens.MenuScreen;

/**
 * Created by samundra on 1/29/2016.
 */
public class MenuCtrl {
    public static class PlayButtonController extends InputListener {
        private static DailaMaara game;
        private static MenuScreen view;
        public PlayButtonController(DailaMaara game,MenuScreen view) {
            this.game = game;
            this.view =  view;
        }

        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            game.setScreen(new Form(game));

            return super.touchDown(event, x, y, pointer, button);
        }
    }
}
