package np.com.samundrakc.game.controllers;

import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import np.com.samundrakc.game.DailaMaara;
import np.com.samundrakc.game.misc.Prefs;
import np.com.samundrakc.game.screens.MenuScreen;
import np.com.samundrakc.game.screens.Settings;

/**
 * Created by samundra on 2/28/2016.
 */
public class SettingsController {
    private final Prefs prefs;
    DailaMaara game;
    Settings view;

    public SettingsController(DailaMaara game, Settings view) {
        this.game = game;
        this.view = view;
        this.prefs = view.getPrefs();
    }

    public InputListener backButton() {
        return new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new MenuScreen(game));
                return super.touchDown(event, x, y, pointer, button);
            }
        };
    }

    public InputListener soundCheck() {
        return new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (!view.getSound().isChecked()) {
                    prefs.setInt("sound", 1);
                } else {
                    prefs.setInt("sound", 0);
                }
                return super.touchDown(event, x, y, pointer, button);
            }
        };
    }

    public InputListener musicCheck() {
        return new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (!view.getMusic().isChecked()) {
                    prefs.setInt("music", 1);
                } else {
                    prefs.setInt("music", 0);
                }
                return super.touchDown(event, x, y, pointer, button);
            }
        };
    }
}
