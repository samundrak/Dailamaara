package np.com.samundrakc.game.controllers;

import com.badlogic.gdx.Gdx;

import java.util.HashMap;

/**
 * Created by samundra on 3/6/2016.
 */
public class Music extends Audio {
    private HashMap<AUDIO, com.badlogic.gdx.audio.Music> music = new HashMap<AUDIO, com.badlogic.gdx.audio.Music>();

    private Music() {
        super();
    }


    public static Music getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Music();
        }
        return INSTANCE;
    }

    private static Music INSTANCE = null;

    public com.badlogic.gdx.audio.Music playWithInstanceMusic(AUDIO audio) {
        return music.get(audio);
    }

    @Override
    public Audio loadAudio() {
        if (prefs.getInt("music") == 0) return this;
        music.put(AUDIO.GAME_MUSIC, Gdx.audio.newMusic(Gdx.files.internal("musics/music.mp3")));
        music.put(AUDIO.GAME_LOST, Gdx.audio.newMusic(Gdx.files.internal("musics/gameLost.mp3")));
        assetsLoaded = true;
        return this;
    }

    @Override
    public void dispose() {
        if (!assetsLoaded) return;
        music.get(AUDIO.GAME_MUSIC).dispose();
        music.get(AUDIO.GAME_LOST).dispose();
    }

    public com.badlogic.gdx.audio.Music playMusic(AUDIO audio) {
        if (!assetsLoaded) return null;
        if (music.get(audio) == null) return null;
        if (prefs.getInt("music") == 0) return null;
        music.get(audio).play();
        return music.get(audio);
    }
}
