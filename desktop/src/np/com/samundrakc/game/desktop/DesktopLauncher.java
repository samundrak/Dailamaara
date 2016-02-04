package np.com.samundrakc.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import np.com.samundrakc.game.DailaMaara;
import np.com.samundrakc.game.misc.Context;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = Context.WIDTH;
        config.height = Context.HEIGHT;
        config.title = Context.GAME_NAME;
		new LwjglApplication(new DailaMaara(), config);
	}
}
