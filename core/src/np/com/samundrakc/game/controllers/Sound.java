package np.com.samundrakc.game.controllers;

import com.badlogic.gdx.Gdx;

import java.util.HashMap;

import np.com.samundrakc.game.misc.Prefs;

/**
 * Created by samundra on 2/28/2016.
 */
public class Sound {

    public static enum AUDIO {BUTTON_TOUCH, CARD_SHARE, CARD_TOUCHED, INVALID, SELECTED, COLLAPSED}

    private HashMap<AUDIO, com.badlogic.gdx.audio.Sound> sounds = new HashMap<AUDIO, com.badlogic.gdx.audio.Sound>();
    private static Sound INSTANCE = null;
    Prefs prefs;

    private Sound() {
        prefs = new Prefs("settings");
    }

    public static Sound getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Sound();
        }
        return INSTANCE;
    }

    public Sound loadAudio() {
        System.out.println(prefs.getInt("sound"));
        if (prefs.getInt("sound") == 0) return this;
        sounds.put(AUDIO.BUTTON_TOUCH, Gdx.audio.newSound(Gdx.files.internal("sounds/touched.wav")));
        sounds.put(AUDIO.CARD_TOUCHED, Gdx.audio.newSound(Gdx.files.internal("sounds/card_touched.wav")));
        sounds.put(AUDIO.CARD_SHARE, Gdx.audio.newSound(Gdx.files.internal("sounds/card_share.wav")));
        sounds.put(AUDIO.INVALID, Gdx.audio.newSound(Gdx.files.internal("sounds/invalid.wav")));
        sounds.put(AUDIO.SELECTED, Gdx.audio.newSound(Gdx.files.internal("sounds/selected.wav")));
        sounds.put(AUDIO.COLLAPSED, Gdx.audio.newSound(Gdx.files.internal("sounds/collapsed.wav")));
        return INSTANCE;
    }

    public long play(AUDIO audio) {
        if (prefs.getInt("sound") == 0) return 0;
        return sounds.get(audio).play();
    }

    public com.badlogic.gdx.audio.Sound playWithInstance(AUDIO audio) {
        return sounds.get(audio);
    }

    public void dispose() {
        sounds.get(AUDIO.BUTTON_TOUCH).dispose();
        sounds.get(AUDIO.CARD_SHARE).dispose();
    }
}
