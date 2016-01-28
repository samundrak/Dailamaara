package np.com.samundrakc.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import np.com.samundrakc.game.DailaMaara;

/**
 * Created by samundra on 1/28/2016.
 */
public class PlayScreen extends ScreenRules {
    public PlayScreen(DailaMaara game) {
        super(game);
    }

    @Override
    public void render(float delta) {
        this.game.clearView();
        canvas.begin();
        canvas.draw(new Texture("bird.png"), 0, 0);
        canvas.end();
        if (Gdx.input.justTouched()){
            game.setScreen(new MenuScreen(game));
        }  }

    @Override
    public void dispose() {
        System.out.println("done");
    }
}
