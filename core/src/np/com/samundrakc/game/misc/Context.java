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

/**
 * Created by samundra on 1/28/2016.
 */
public class Context {

    public static final String GAME_NAME = "DailMaara";
    public static final int WIDTH = 700,
            HEIGHT = 400;
    public static final Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
    public static final Texture CARDS_BACK_COVER = new Texture("cards/back.png") ;
    public static final Actor toast(String message) {
        Label label = new Label(message, new Label.LabelStyle(new BitmapFont(), Color.RED));
        label.setPosition((Context.WIDTH / 2) - (label.getWidth() / 2), 0);
        MoveToAction action = Actions.action(MoveToAction.class);
        action.setPosition((Context.WIDTH / 2) - (label.getWidth() / 2) , Context.HEIGHT - (Context.HEIGHT + label.getHeight()));
        action.setInterpolation(Interpolation.bounceOut);
        action.setDuration(2);
        label.addAction(action);
        return label;
    }
}
