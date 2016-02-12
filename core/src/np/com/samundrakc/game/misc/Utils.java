package np.com.samundrakc.game.misc;

import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.ArrayList;
import java.util.Random;

import np.com.samundrakc.game.anchors.Const;

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

    public static int getRandom(int max, ArrayList<Integer> al) {
        Random r = new Random();
        int random = r.nextInt(max - 1);
        if (al != null) {
            while (al.contains(random)) {
                random = r.nextInt(max - 1);
            }
        }
        return random;
    }

    public static float getXDiffToPin(float xA, float xB) {
        if (xB > xA) {
            return -(xB - xA);
        } else {
            return (xA - xB);
        }
    }

    public static float getDiffPoint(float from, float to) {
        if (to > from) {
            return (to - from);
        } else {
            return -(from - to);
        }
    }

}
