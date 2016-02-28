package np.com.samundrakc.game.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import java.awt.Menu;

import np.com.samundrakc.game.DailaMaara;
import np.com.samundrakc.game.misc.Animation;
import np.com.samundrakc.game.misc.Context;
import np.com.samundrakc.game.misc.MessageBox;
import np.com.samundrakc.game.screens.Form;
import np.com.samundrakc.game.screens.MenuScreen;
import np.com.samundrakc.game.screens.Settings;

/**
 * Created by samundra on 1/29/2016.
 */
public class MenuCtrl {
    DailaMaara game;
    MenuScreen view;

    public MenuCtrl(DailaMaara game, MenuScreen view) {
        this.game = game;
        this.view = view;
    }

    public InputListener settingsController() {

        return new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new Settings(game));
                return super.touchDown(event, x, y, pointer, button);
            }
        };
    }

    public InputListener exit() {
        return new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.exit();
                return super.touchDown(event, x, y, pointer, button);
            }
        };
    }

    public static class PlayButtonController extends InputListener {
        private static DailaMaara game;
        private static MenuScreen view;

        public PlayButtonController(DailaMaara game, MenuScreen view) {
            this.game = game;
            this.view = view;
        }

        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            Sound.getInstance().play(Sound.AUDIO.BUTTON_TOUCH);
            game.setScreen(new Form(game));
            view.dispose();
//            Sound.getInstance().dispose();
            return super.touchDown(event, x, y, pointer, button);
        }
    }
}
