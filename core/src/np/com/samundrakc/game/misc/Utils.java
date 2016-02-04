package np.com.samundrakc.game.misc;

import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by samundra on 1/28/2016.
 */
public class Utils {

    public static float inCenter(Actor a, char axis) {
        if (axis == 'x') {
            return Context.WIDTH / 2 - (a.getWidth() / 2);
        }
        if (axis == 'y') {
            return Context.HEIGHT / 2 - (a.getHeight() / 2);
        }
        return 0;
    }

    public static boolean isEmpty(String value) {
        if (value.trim().length() < 1) return !false;
        if (value.equals(" ")) return !false;
        if (value.equals("")) return !false;
        return !true;
    }
}
