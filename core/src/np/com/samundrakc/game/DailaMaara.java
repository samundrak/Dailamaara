package np.com.samundrakc.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class DailaMaara extends Game {
	public SpriteBatch batch;

	@Override
	public void create () {
			batch = new SpriteBatch();
		 setScreen(new np.com.samundrakc.game.screens.MenuScreen(this));
	}

	@Override
	public void render () {
		 super.render();
	}

	public void clearView(){
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}
}
