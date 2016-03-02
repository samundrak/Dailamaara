package np.com.samundrakc.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;


import np.com.samundrakc.game.DailaMaara;
import np.com.samundrakc.game.anchors.Const;
import np.com.samundrakc.game.controllers.MenuCtrl;
import np.com.samundrakc.game.controllers.PFX;
import np.com.samundrakc.game.controllers.Sound;
import np.com.samundrakc.game.misc.Context;
import np.com.samundrakc.game.misc.SpriteAnimation;

/**
 * Created by samundra on 1/28/2016.
 */
public class MenuScreen extends ScreenRules {


    public TextButton getPlayButton() {
        return playButton;
    }

    private TextButton playButton;
    private TextButton settings;
    private TextButton howToPlay;
    private MenuCtrl controller;

    public MenuScreen(DailaMaara dailaMaara) {
        super(dailaMaara);
        Context.getInstance().setSkin();
        Sound.getInstance().loadAudio();
        playButton = new TextButton("Play", Context.getInstance().getSkin());
        settings = new TextButton("Settings", Context.getInstance().getSkin());
        howToPlay = new TextButton("How To Play", Context.getInstance().getSkin());
        controller = new MenuCtrl(dailaMaara, this);

    }

    SpriteBatch sb = new SpriteBatch();

    @Override
    public void render(float delta) {
        super.render(delta);

    }

    @Override
    public void show() {

        Table table;
        playButton.addListener(new MenuCtrl.PlayButtonController(game, this));
        settings.addListener(controller.settingsController());
        TextButton exit = new TextButton("Exit", Context.getInstance().getSkin());
        exit.addListener(controller.exit());
        table = new Table();
        table.add(playButton).padTop(5).row();
        table.add(settings).padTop(5).row();
        table.add(howToPlay).padTop(5).row();
        table.add(exit).padTop(5).row();
        table.center();
        table.setFillParent(true);
        stage.addActor(table);
    }


    @Override
    public void dispose() {
        playButton = null;
        stage = null;
    }


}
