package np.com.samundrakc.game.controllers.subControllers;

import com.badlogic.gdx.Gdx;

/**
 * Created by samundra on 2/28/2016.
 */
public class Sound extends np.com.samundrakc.game.controllers.subControllers.Audio {
    private static Sound INSTANCE = null;

    private Sound() {
        super();
    }

    public static Sound getInstance() {
        if (SingletonHolder.INSTANCE == null) {
            return SingletonHolder.INSTANCE;
        }
        return SingletonHolder.INSTANCE;
    }

    @Override
    public com.badlogic.gdx.audio.Sound playWithInstance(np.com.samundrakc.game.controllers.subControllers.Audio.AUDIO audio) {
        if (!assetsLoaded) return null;
        if (prefs.getInt("sound") == 0) return null;
        return sounds.get(audio);
    }

    public Sound loadAudio() {
        if (isAssetsLoaded()) return this;
        if (prefs.getInt("sound") == 0) return this;
        sounds.put(AUDIO.BUTTON_TOUCH, Gdx.audio.newSound(Gdx.files.internal("sounds/touched.wav")));
        sounds.put(AUDIO.CARD_TOUCHED, Gdx.audio.newSound(Gdx.files.internal("sounds/card_touched.wav")));
        sounds.put(AUDIO.CARD_SHARE, Gdx.audio.newSound(Gdx.files.internal("sounds/card_share.wav")));
        sounds.put(AUDIO.INVALID, Gdx.audio.newSound(Gdx.files.internal("sounds/invalid.wav")));
        sounds.put(AUDIO.SELECTED, Gdx.audio.newSound(Gdx.files.internal("sounds/selected.wav")));
        sounds.put(AUDIO.COLLAPSED, Gdx.audio.newSound(Gdx.files.internal("sounds/collapsed.wav")));
        sounds.put(AUDIO.SPLASH_TURUP, Gdx.audio.newSound(Gdx.files.internal("sounds/magic-turning-spells-casting_MJtx4SEu.mp3")));
        sounds.put(AUDIO.SPALSH_TEN, Gdx.audio.newSound(Gdx.files.internal("sounds/xp_gain_06.ogg")));
        sounds.put(AUDIO.FIRE_WORKS, Gdx.audio.newSound(Gdx.files.internal("sounds/fireworks.mp3")));
        sounds.put(AUDIO.SMALL_CARD, Gdx.audio.newSound(Gdx.files.internal("sounds/coins_collect_01.ogg")));
        sounds.put(AUDIO.TEN_GONE, Gdx.audio.newSound(Gdx.files.internal("sounds/small-magic-string-spells_f1kAdSNO.mp3")));
        sounds.put(AUDIO.CARD_WON, Gdx.audio.newSound(Gdx.files.internal("sounds/sell_item_01.ogg")));
        sounds.put(AUDIO.ROTATE_CARDS, Gdx.audio.newSound(Gdx.files.internal("sounds/cardOpenPackage1.wav")));
        sounds.put(AUDIO.NEW_MESSAGE, Gdx.audio.newSound(Gdx.files.internal("sounds/achievestaronhill_ozzy.ogg")));
        assetsLoaded = true;
        return INSTANCE;
    }


    public void dispose() {
        if (!assetsLoaded) return;
        sounds.get(AUDIO.BUTTON_TOUCH).dispose();
        sounds.get(AUDIO.CARD_SHARE).dispose();
    }

    @Override
    public long play(AUDIO audio) {
        if (!assetsLoaded) return 0;
        if (prefs.getInt("sound") == 0) return 0;
        return sounds.get(audio).play();
    }

    private static class SingletonHolder {
        private static final Sound INSTANCE = new Sound();
    }
}
