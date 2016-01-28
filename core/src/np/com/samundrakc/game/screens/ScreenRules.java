package np.com.samundrakc.game.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import np.com.samundrakc.game.DailaMaara;
import np.com.samundrakc.game.misc.Context;

/**
 * Created by samundra on 1/29/2016.
 */
public class ScreenRules implements Screen {
    protected final DailaMaara game;
    protected OrthographicCamera gameCam;
    protected Viewport vp;
    protected final SpriteBatch canvas;
    public ScreenRules(DailaMaara game) {
        this.game =  game;
        gameCam =  new OrthographicCamera();
        vp =  new FitViewport(Context.WIDTH,Context.HEIGHT,gameCam);
        canvas = this.game.batch;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {
        vp.update(width,height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

}
