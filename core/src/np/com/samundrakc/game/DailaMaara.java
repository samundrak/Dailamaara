package np.com.samundrakc.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import np.com.samundrakc.game.controllers.Audio;
import np.com.samundrakc.game.controllers.Music;

public class DailaMaara extends Game {
    public static com.badlogic.gdx.audio.Music GAME_MUSIC;

    @Override
    public void create() {
        Music.getInstance().loadAudio();
        GAME_MUSIC = Music.getInstance().playMusic(Audio.AUDIO.GAME_MUSIC);
        if (GAME_MUSIC != null) {
            GAME_MUSIC.setVolume(0.5f);
            GAME_MUSIC.setLooping(true);
        }
        Gdx.app.setLogLevel(Application.LOG_INFO);
        setScreen(new np.com.samundrakc.game.screens.MenuScreen(this));

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
