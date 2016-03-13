package np.com.samundrakc.game.controllers.dailamaara;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import np.com.samundrakc.game.screens.DailaMaara;

/**
 * Created by samundra on 3/3/2016.
 */
public class DailamaaraListener {
    private DailaMaara view;

    public DailamaaraListener(DailaMaara view) {
        this.view = view;
    }

    public InputListener upDownToggleButton() {
        return new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                view.setToggle();
                return super.touchDown(event, x, y, pointer, button);
            }
        };
    }
}
