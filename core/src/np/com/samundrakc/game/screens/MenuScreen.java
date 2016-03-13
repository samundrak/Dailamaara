package np.com.samundrakc.game.screens;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;


import np.com.samundrakc.game.DailaMaara;
import np.com.samundrakc.game.controllers.subControllers.Audio;
import np.com.samundrakc.game.controllers.menu.MenuCtrl;
import np.com.samundrakc.game.controllers.subControllers.Music;
import np.com.samundrakc.game.controllers.subControllers.PauseScreen;
import np.com.samundrakc.game.controllers.subControllers.Sound;
import np.com.samundrakc.game.misc.Context;
import np.com.samundrakc.game.screens.subScreens.Windows;

/**
 * Created by samundra on 1/28/2016.
 */
public class MenuScreen extends ScreenRules {


    private Image playButton;
    private Image exit;
    private Image settings;
    private Image howToPlay;
    private MenuCtrl controller;
    private DailaMaara dailaMaara;

    public MenuScreen(DailaMaara dailaMaara) {
        super(dailaMaara, null);
        this.dailaMaara = dailaMaara;
        setIsScreenReady(false);
    }

    TextureAtlas buttonsAtlas;

    @Override
    public void loadAssets() {
        super.loadAssets();
        Context.getInstance().setSkin();
        if (dailaMaara.getGAME_MUSIC() != null) {
            dailaMaara.getGAME_MUSIC().setVolume(0.5f);
            dailaMaara.getGAME_MUSIC().setLooping(true);
        } else {
            System.out.println("Played Again");
            Music.getInstance().loadAudio();
            dailaMaara.setGAME_MUSIC(Music.getInstance().playMusic(Audio.AUDIO.GAME_MUSIC));

            Sound.getInstance().loadAudio();
        }
        buttonsAtlas = new TextureAtlas("texture/buttons.atlas");
//        TextureRegion  tr = buttonsAtlas.g
        playButton = new Image(buttonsAtlas.findRegion("playButton"));
        settings = new Image(buttonsAtlas.findRegion("settings"));
        howToPlay = new Image(buttonsAtlas.findRegion("howtoplay"));
        exit = new Image(buttonsAtlas.findRegion("exit"));
        controller = new MenuCtrl(dailaMaara, this);
        logo = new LoadingScreen().logoFile();
        setIsScreenReady(true);
    }

    Image logo;
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
        exit.addListener(controller.exit());
        howToPlay.addListener(controller.howToPlay());
        table = new Table();
        table.add(playButton).padTop(5).row();
        table.add(settings).padTop(5).row();
        table.add(howToPlay).padTop(5).row();
        table.add(exit).padTop(5).row();
        table.left();
        table.setFillParent(true);
        table.padLeft(20);
        logo.addAction(np.com.samundrakc.game.misc.Animation.simpleAnimation(Context.WIDTH - logo.getWidth(), (Context.HEIGHT / 2) - (logo.getHeight() / 2)));
        stage.addActor(logo);
        stage.addActor(table);
    }


    @Override
    public void dispose() {
        playButton = null;
        stage = null;
        buttonsAtlas.dispose();
        buttonsAtlas = null;
    }


}
