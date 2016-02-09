package np.com.samundrakc.game.misc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * Created by samundra on 2/10/2016.
 */
public class Prefs {
    Preferences pref;

    public Prefs(String name) {
        pref = Gdx.app.getPreferences(name+".prefs");

    }

    public   String getString(String key,String def) {
        String x = pref.getString(key,def);
        pref.flush();
        return x;
    }

    public   int getInt(String key) {
        int x = pref.getInteger(key,-1);
        pref.flush();
        return x;
    }

    public  void setString(String key, String value) {
        pref.putString(key, value);
        pref.flush();
    }

    public  void setInt(String key, int value) {
        pref.putInteger(key, value);
        pref.flush();
    }
}
