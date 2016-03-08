package np.com.samundrakc.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import np.com.samundrakc.game.DailaMaara;
import np.com.samundrakc.game.anchors.Game;
import np.com.samundrakc.game.controllers.Music;
import np.com.samundrakc.game.controllers.Sound;
import np.com.samundrakc.game.misc.Context;

/**
 * Created by samundra on 1/29/2016.
 */
public abstract class ScreenRules extends ScreenAdapter {
    protected final DailaMaara game;
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

    public ScreenRules(DailaMaara game) {
        isScreenReady = false;

        this.game = game;
        cam = new OrthographicCamera(Context.WIDTH, Context.HEIGHT);
        vp = new StretchViewport(Context.WIDTH, Context.HEIGHT, cam);
        stage = new Stage(vp);
        Gdx.input.setInputProcessor(stage);
        Image img = new Image(new Texture("bg.png"));
        stage.addActor(img);
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
        System.out.println("On Poz mode");
        DailaMaara.GAME_MUSIC = null;
        Music.getInstance().setAssetsLoaded(false);
        Sound.getInstance().setAssetsLoaded(false);}

    public void loadAssets() {

    }


}
