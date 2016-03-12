package np.com.samundrakc.game.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;

import java.util.HashMap;

import np.com.samundrakc.game.anchors.Const;

/**
 * Created by samundra on 3/2/2016.
 */
public class PFX {

    HashMap<String, ParticleEffect> effectHashMap;

    public PFX() {
        this.effectHashMap = new HashMap<String, ParticleEffect>();
    }

    public void loadEffects() {
        effectHashMap.put(Const.CARDS.CLUBS.toString(), new ParticleEffect());
        effectHashMap.get(Const.CARDS.CLUBS.toString()).load(Gdx.files.internal("particle/clubs.pfx"), Gdx.files.internal("particle/images"));

        effectHashMap.put(Const.CARDS.HEARTS.toString(), new ParticleEffect());
        effectHashMap.get(Const.CARDS.HEARTS.toString()).load(Gdx.files.internal("particle/hearts.pfx"), Gdx.files.internal("particle/images"));

        effectHashMap.put(Const.CARDS.DIAMONDS.toString(), new ParticleEffect());
        effectHashMap.get(Const.CARDS.DIAMONDS.toString()).load(Gdx.files.internal("particle/diamonds.pfx"), Gdx.files.internal("particle/images"));

        effectHashMap.put(Const.CARDS.SPADES.toString(), new ParticleEffect());
        effectHashMap.get(Const.CARDS.SPADES.toString()).load(Gdx.files.internal("particle/spades.pfx"), Gdx.files.internal("particle/images"));

        effectHashMap.put("win", new ParticleEffect());
        effectHashMap.get("win").load(Gdx.files.internal("particle/won.pfx"), Gdx.files.internal("particle/images"));

        effectHashMap.put("onCoat", new ParticleEffect());
        effectHashMap.get("onCoat").load(Gdx.files.internal("particle/flower.pfx"), Gdx.files.internal("particle/images"));
    }

    public HashMap<String, ParticleEffect> getEffectHashMap() {
        return effectHashMap;
    }
}
