package np.com.samundrakc.game.screens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import np.com.samundrakc.game.DailaMaara;
import np.com.samundrakc.game.controllers.MenuCtrl;

/**
 * Created by samundra on 1/28/2016.
 */
public class MenuScreen extends ScreenRules {
    private Texture playButton;
    private BitmapFont font;
    public MenuScreen(DailaMaara dailaMaara) {
        super(dailaMaara);
        playButton =   new Texture("playbtn.png");
        font = new BitmapFont();
    }


    @Override
    public void render(float delta) {
      game.clearView();
        canvas.begin();
        canvas.setProjectionMatrix(gameCam.combined);
        font.draw(game.batch, "Let's Play DailaMaara....", 0,-10);
        canvas.draw(playButton,-playButton.getWidth()/2,0);
        new MenuCtrl().detectTouch(playButton);
        game.batch.end();
    }



    @Override
    public void dispose() {
        System.out.println("gone");
    }
}
