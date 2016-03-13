package np.com.samundrakc.game.controllers.subControllers;


import com.badlogic.gdx.audio.Music;

import java.util.HashMap;

import np.com.samundrakc.game.misc.Prefs;

/**
 * Created by samundra on 3/6/2016.
 */
public abstract class Audio {
    public static enum AUDIO {
        BUTTON_TOUCH, CARD_SHARE, CARD_TOUCHED, INVALID, SELECTED, COLLAPSED,
        GAME_LOST, GAME_MUSIC, SPALSH_TEN, SPLASH_TURUP,FIRE_WORKS,SMALL_CARD,
        TEN_GONE,CARD_WON,ROTATE_CARDS,NEW_MESSAGE ;
    }

    public boolean isAssetsLoaded() {
        return assetsLoaded;
    }

    public void setAssetsLoaded(boolean assetsLoaded) {
        this.assetsLoaded = assetsLoaded;
    }

    protected boolean assetsLoaded = false;

    protected HashMap<AUDIO, com.badlogic.gdx.audio.Sound> sounds = new HashMap<AUDIO, com.badlogic.gdx.audio.Sound>();

    public com.badlogic.gdx.audio.Sound playWithInstance(AUDIO audio) {
        return null;
    }

    public abstract Audio loadAudio();

    public abstract void dispose();

    public Music playMusic(AUDIO audio) {
        return null;
    }

    public long play(AUDIO audio) {
        return -1;
    }
    Prefs prefs;

    protected Audio() {
        prefs = new Prefs("settings");
    }
}
