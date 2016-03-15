package np.com.samundrakc.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import np.com.samundrakc.game.DailaMaara;
import np.com.samundrakc.game.controllers.subControllers.Music;
import np.com.samundrakc.game.controllers.subControllers.Sound;
import np.com.samundrakc.game.misc.Context;

/**
 * Created by samundra on 1/29/2016.
 */
public abstract class ScreenRules extends ScreenAdapter {
    protected DailaMaara game;
    protected Stage stage;
    StretchViewport vp;
    OrthographicCamera cam;
    public boolean isScreenReady;

    public Stage getStage() {
        return stage;
    }

    public boolean isScreenReady() {
        return isScreenReady;
    }

    public void setIsScreenReady(boolean isScreenReady) {
        this.isScreenReady = isScreenReady;
    }

    public ScreenRules(DailaMaara game, Texture background) {
        isScreenReady = false;
        this.game = game;
        cam = new OrthographicCamera(Context.WIDTH, Context.HEIGHT);
        vp = new StretchViewport(Context.WIDTH, Context.HEIGHT, cam);
        stage = new Stage(vp);
        Gdx.input.setInputProcessor(stage);
        Image img;
        if (background == null) {
            img = new Image(new Texture("bg.png"));
        } else {
            img = new Image(new Texture("table.png"));
        }
        stage.addActor(img);
    }

    public ScreenRules() {
    }

    @Override
    public void render(float delta) {
        game.clearView();
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        vp.update(width, height, true);
    }

    public ScreenRules nextScreenAssestsChecker(ScreenRules Screen) {
        //Overridden if Loading is done
        return this;
    }

    @Override
    public void pause() {
        super.pause();
        if (game.getGAME_MUSIC() != null) {
            game.getGAME_MUSIC().pause();
        }
        Music.getInstance().setAssetsLoaded(false);
        Sound.getInstance().setAssetsLoaded(false);

    }

    public void loadAssets() {

    }

    @Override
    public void resume() {
        super.resume();
        if (game.getGAME_MUSIC() != null) {
            game.getGAME_MUSIC().play();
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        Music.getInstance().dispose();
        Sound.getInstance().dispose();
    }
}
