package np.com.samundrakc.game.controllers;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import np.com.samundrakc.game.DailaMaara;
import np.com.samundrakc.game.misc.Prefs;
import np.com.samundrakc.game.screens.LoadingScreen;
import np.com.samundrakc.game.screens.MenuScreen;
import np.com.samundrakc.game.screens.Settings;

/**
 * Created by samundra on 2/28/2016.
 */
public class SettingsController {
    private Prefs prefs;
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
                game.setScreen(new LoadingScreen(game).otherScreen(new MenuScreen(game)));
                view.dispose();
                prefs = null;
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
                    if (game.getGAME_MUSIC() == null) {
                        np.com.samundrakc.game.controllers.subControllers.Music.getInstance().loadAudio();
                        game.setGAME_MUSIC( np.com.samundrakc.game.controllers.subControllers.Music.getInstance().playMusic(np.com.samundrakc.game.controllers.subControllers.Audio.AUDIO.GAME_MUSIC));
                    }
                    game.getGAME_MUSIC().play();
                    game.getGAME_MUSIC().setVolume(0.5f);
                     game.getGAME_MUSIC().setLooping(true);
                } else {
                    if (game.getGAME_MUSIC() != null) {
                        game.getGAME_MUSIC().stop();
                        game.setGAME_MUSIC(null);
                        np.com.samundrakc.game.controllers.subControllers.Music.getInstance().dispose();
                    }
                    prefs.setInt("music", 0);
                }
                return super.touchDown(event, x, y, pointer, button);
            }
        };
    }


}
