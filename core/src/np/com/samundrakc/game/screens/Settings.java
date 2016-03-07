package np.com.samundrakc.game.screens;

import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import np.com.samundrakc.game.*;
import np.com.samundrakc.game.anchors.Const;
import np.com.samundrakc.game.controllers.SettingsController;
import np.com.samundrakc.game.misc.Context;
import np.com.samundrakc.game.misc.Prefs;

/**
 * Created by samundra on 2/28/2016.
 */
public class Settings extends ScreenRules {
    SettingsController controller;
    private CheckBox music;
    private CheckBox sound;

    public Prefs getPrefs() {
        return prefs;
    }

    private Prefs prefs;

    public Settings(np.com.samundrakc.game.DailaMaara game) {
        super(game);
        prefs = new Prefs("settings");
        controller = new SettingsController(game, this);
        if (prefs.getInt("sound") == -1)
            prefs.setInt("sound", 1);

        if (prefs.getInt("music") == -1)
            prefs.setInt("music", 1);

    }

    @Override
    public void show() {
        super.show();
        init();
    }

    private void init() {
        Table table = new Table();
        sound = new CheckBox(" Sound", Context.getInstance().getSkin());
        music = new CheckBox(" Music", Context.getInstance().getSkin());
        if (prefs.getInt("sound") == 1) {
            sound.setChecked(true);
        }
        if (prefs.getInt("music") == 1) {
            music.setChecked(true);
        }
        sound.addListener(controller.soundCheck());
        music.addListener(controller.musicCheck());
        TextButton back = new TextButton("Back", Context.getInstance().getSkin());
        back.addListener(controller.backButton());
        table.add(sound).pad(10).row();
        table.add(music).pad(10).row();
        table.add(back).pad(10).row();
        table.center();
        table.setFillParent(true);
        stage.addActor(table);
    }

    public CheckBox getMusic() {
        return music;
    }

    public void setMusic(CheckBox music) {
        this.music = music;
    }

    public CheckBox getSound() {
        return sound;
    }

    public void setSound(CheckBox sound) {
        this.sound = sound;
    }

    @Override
    public void dispose() {
        super.dispose();
        stage = null;
        music = null;
        sound = null;
        prefs = null;
    }
}
