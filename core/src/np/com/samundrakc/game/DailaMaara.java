package np.com.samundrakc.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

import np.com.samundrakc.game.screens.LoadingScreen;
import np.com.samundrakc.game.screens.MenuScreen;

public class DailaMaara extends Game {
    public static com.badlogic.gdx.audio.Music GAME_MUSIC;

    @Override
    public void create() {
        LoadingScreen loadingScreen = new LoadingScreen(this);
        setScreen(loadingScreen);
        loadingScreen.nextScreenAssestsChecker(new MenuScreen(this));
        Gdx.app.setLogLevel(Application.LOG_INFO);
    }

    @Override
    public void render() {
        super.render();

    }

    public void clearView() {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
