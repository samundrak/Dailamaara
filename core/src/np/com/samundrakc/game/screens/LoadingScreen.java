package np.com.samundrakc.game.screens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Timer;

import np.com.samundrakc.game.DailaMaara;
import np.com.samundrakc.game.controllers.Callback;
import np.com.samundrakc.game.misc.Animation;
import np.com.samundrakc.game.misc.Context;

/**
 * Created by samundra on 3/7/2016.
 */
public class LoadingScreen extends ScreenRules {
    DailaMaara game;
    Image splashScreen;

    public LoadingScreen(np.com.samundrakc.game.DailaMaara game) {
        super(game);
        this.game = game;
        splashScreen = new Image(new Texture("splash.png"));
        splashScreen.setPosition(0, 0);
        splashScreen.addAction(Animation.repeatAction(Actions.sequence(
                Actions.fadeIn(2),
                Actions.fadeOut(1)

        )));

        stage.addActor(splashScreen);

    }

    public LoadingScreen nextScreenAssestsChecker(final ScreenRules Screen) {
        OnComplete(Screen, new Callback() {
            @Override
            public void run() {
                game.setScreen(Screen);
            }
        });
        return this;
    }

    public LoadingScreen otherScreen(final ScreenRules Screen) {
        Label label = new Label("Loading...", Context.getInstance().getSkin());
        label.setPosition(Context.WIDTH / 2 - (label.getWidth() / 2), 80);
        label.addAction(Animation.repeatAction(
                Actions.sequence(
                        Animation.moveByAnime(20, 0, 2f),
                        Animation.moveByAnime(-20, 0, 2f)
                )
        ));
        stage.addActor(label);
        OnComplete(Screen, new Callback() {
            @Override
            public void run() {
                game.setScreen(Screen);
            }
        });
        return this;
    }

    public void OnComplete(final ScreenRules screen, final Callback cb) {
        final Timer assetsLoadingTimer = new Timer();
        final int[] counter = {0};
        final boolean[] loadOnce = {false};
        assetsLoadingTimer.scheduleTask(new Timer.Task() {

            @Override
            public void run() {
                if (!loadOnce[0]) {
                    screen.loadAssets();
                    loadOnce[0] = true;
                }
                if (screen.isScreenReady() && counter[0] > 2) {
                    if (cb != null) {
                        cb.run();
                        splashScreen = null;
                        stage = null;
                    }
                    assetsLoadingTimer.clear();
                    return;
                }
                counter[0]++;
            }
        }, 2, 1);
    }
}
