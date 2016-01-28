package np.com.samundrakc.game.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import np.com.samundrakc.game.DailaMaara;
import np.com.samundrakc.game.misc.Context;
import sun.font.GlyphLayout;

/**
 * Created by samundra on 1/28/2016.
 */
public class MenuScreen implements Screen {
    DailaMaara game;
    private Texture playButtn;
    private OrthographicCamera gameCam;
    private Viewport vp;
    private BitmapFont font;
    public MenuScreen(DailaMaara dailaMaara) {
        game =  dailaMaara;
        playButtn =   new Texture("playbtn.png");
        gameCam =  new OrthographicCamera();
        vp =  new FitViewport(Context.WIDTH,Context.HEIGHT,gameCam);
        font = new BitmapFont();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
      game.clearView();
        game.batch.begin();
        game.batch.setProjectionMatrix(gameCam.combined);
        font.draw(game.batch,"Let's Play DailaMaara",0,-playButtn.getHeight());
        game.batch.draw(playButtn,  -(playButtn.getWidth()/2),0);
        game.batch.end();
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
