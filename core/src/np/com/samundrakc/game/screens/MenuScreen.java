package np.com.samundrakc.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import java.awt.Color;

import np.com.samundrakc.game.DailaMaara;
import np.com.samundrakc.game.controllers.MenuCtrl;
import np.com.samundrakc.game.misc.Animation;
import np.com.samundrakc.game.misc.Context;

/**
 * Created by samundra on 1/28/2016.
 */
public class MenuScreen extends ScreenRules {
    public Image getPlayButton() {
        return playButton;
    }

    private Image playButton;

    public MenuScreen(DailaMaara dailaMaara) {
        super(dailaMaara);
        playButton = new Image(new Texture("playBtn.png"));
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        playButton.setBounds(0, 0, playButton.getWidth(), playButton.getHeight());
        playButton.setX(Context.WIDTH / 2 - (playButton.getWidth() / 2));
        playButton.setY(Context.HEIGHT / 2);
        playButton.addListener(new MenuCtrl.PlayButtonController(game, this));
        stage.addActor(playButton);
    }



    @Override
    public void dispose() {
        System.out.println("gone");
    }


}
