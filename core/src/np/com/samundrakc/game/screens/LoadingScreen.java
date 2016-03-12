package np.com.samundrakc.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
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
        super(game,null);
        this.game = game;
        splashScreen = new Image(new Texture("splash-logo.png"));
        splashScreen.setPosition(Context.WIDTH / 2 - (splashScreen.getWidth() / 2), Context.HEIGHT);
        splashScreen.addAction(Animation.simpleAnimation(splashScreen.getX(), Context.HEIGHT / 2 - (splashScreen.getHeight() / 2)));
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
        label.setPosition(Context.WIDTH / 2 - (label.getWidth() / 2), Context.HEIGHT / 2 - ((splashScreen.getHeight() / 2) + 10));
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
                Gdx.app.log("Assets Ready", screen.isScreenReady() + " +  " + counter[0]);
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
