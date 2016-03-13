package np.com.samundrakc.game.controllers.subControllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import np.com.samundrakc.game.anchors.Game;
import np.com.samundrakc.game.screens.Form;
import np.com.samundrakc.game.screens.LoadingScreen;
import np.com.samundrakc.game.screens.MenuScreen;

/**
 * Created by samundra on 3/13/2016.
 */
public class PauseScreen {

    public static class GamePauseScreen {
        Game game;

        public Game getGame() {
            return game;
        }

        public GamePauseScreen(Game game) {
            this.game = game;
        }

        public ClickListener restartGame(final Label label) {
            return new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    label.setColor(Color.GREEN);
                    Sound.getInstance().play(Sound.AUDIO.BUTTON_TOUCH);
                    game.getView().getParentGame().setScreen(new LoadingScreen(game.getView().getParentGame()).otherScreen(new Form(game.getView().getParentGame())));
                    game.setView(null);
                    game = null;
                }
            };
        }

        public ClickListener exitGame(final Label label) {
            return new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    label.setColor(Color.GREEN);
                    Sound.getInstance().play(Sound.AUDIO.BUTTON_TOUCH);
                    Gdx.app.exit();
                    game.setView(null);
                    game = null;
                }

            };
        }

        public ClickListener gotoMenu(final Label label) {
            return new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    label.setColor(Color.GREEN);
                    Sound.getInstance().play(Sound.AUDIO.BUTTON_TOUCH);
                    game.
                            getView().
                            getParentGame().
                            setScreen(new LoadingScreen(
                                    game.
                                            getView().
                                            getParentGame())
                                    .otherScreen(new MenuScreen(
                                            game.
                                                    getView().
                                                    getParentGame())));
                    game.setView(null);
                    game = null;

                }
            };
        }
    }
}
