package np.com.samundrakc.game.misc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.util.Random;

/**
 * Created by samundra on 1/28/2016.
 */
public class Context {
    public static final String GAME_NAME = "DailaMaara";
    public static final int WIDTH = 700, HEIGHT = 400;
    private Skin skin;
    private static Context INSTANCE;

    public void setSkin() {
        this.skin = new Skin(Gdx.files.internal("uiskins/uiskin.json"));
    }

    public void setCARDS_BACK_COVER() {
        try {
            this.CARDS_BACK_COVER = new Texture("cards/backs/back_" + new Random().nextInt(10) + ".png");
        } catch (Exception e) {
            this.CARDS_BACK_COVER = new Texture("cards/backs/back_" + 1 + ".png");
        }
    }

    private Texture CARDS_BACK_COVER;
    private static Context context = null;

    public Skin getSkin() {
        return skin;
    }

    public Texture getCARDS_BACK_COVER() {
        return CARDS_BACK_COVER;
    }

    public static Context getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Context();
        }
        return INSTANCE;
    }

    public static enum RESOLUTION_TYPES {SMALL, MEDIUM, LARGE}

    public static Context.RESOLUTION_TYPES RESOLUTION = RESOLUTION_TYPES.MEDIUM;

    public static void defineResolution() {
        switch (Context.WIDTH) {
            case 400:
                Context.RESOLUTION = RESOLUTION_TYPES.SMALL;
                break;
            case 700:
                Context.RESOLUTION = RESOLUTION_TYPES.MEDIUM;
                break;
            case 1000:
                Context.RESOLUTION = RESOLUTION_TYPES.LARGE;
                break;
            default:
                Context.RESOLUTION = RESOLUTION_TYPES.MEDIUM;
                break;
        }
    }


}
