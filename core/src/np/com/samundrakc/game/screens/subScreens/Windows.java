package np.com.samundrakc.game.screens.subScreens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import np.com.samundrakc.game.anchors.Const;
import np.com.samundrakc.game.controllers.subControllers.Audio;
import np.com.samundrakc.game.controllers.subControllers.PauseScreen;
import np.com.samundrakc.game.controllers.subControllers.Sound;
import np.com.samundrakc.game.misc.Context;

/**
 * Created by samundra on 3/13/2016.
 */
public class Windows {
    Window window;
    Image close;
    Stage stage;

    public Windows(String title, Stage stage) {
        window = new Window(title, Context.getInstance().getSkin());
        this.stage = stage;
        window.center();
        close = new Image(new Texture("close_button_normal.png"));
//        window.padLeft(window.getWidth());
    }

    PauseScreen.GamePauseScreen controller;

    public Windows getPauseScreen(PauseScreen.GamePauseScreen controller) {
        window.setWidth(350);
        window.setHeight(200);
        this.controller = controller;
        window.setPosition(((Context.WIDTH / 2) - (window.getWidth() / 2)), ((Context.HEIGHT / 2) - (window.getHeight() / 2)));
        Table table = new Table();
        close.setPosition((window.getX() + window.getWidth()) - (close.getWidth() / 2), (window.getY() + window.getHeight()) - (close.getHeight() / 2));
        Label continueGame = new Label("Continue", Context.getInstance().getSkin());
        Label restartGame = new Label("Restart", Context.getInstance().getSkin());
        Label menu = new Label("Menu", Context.getInstance().getSkin());
        Label exit = new Label("Exit", Context.getInstance().getSkin());
        continueGame.addListener(selfHide());
        restartGame.addListener(controller.restartGame(restartGame));
        menu.addListener(controller.gotoMenu(menu));
        exit.addListener(controller.exitGame(exit));
        table.add(continueGame).padTop(10).row();
        table.add(restartGame).pad(1).row();
        table.add(menu).pad(2).row();
        table.add(exit).pad(1).row();
        window.add(table);
        close.addListener(selfHide());
        stage.addActor(window);
//        stage.addActor(close);
        window.setVisible(false);
        close.setVisible(false);
        window.setMovable(false);
        window.setModal(true);

        return this;
    }

    public Windows show() {
        if (window.isVisible()) return this;
        controller.getGame().setSTATE(Const.STATE.PAUSE);
        System.out.println("Showing..window");
        Sound.getInstance().play(Audio.AUDIO.BUTTON_TOUCH);
        window.setVisible(true);
        close.setVisible(true);
        window.addAction(Actions.fadeOut(0));
        window.addAction(Actions.fadeIn(0.4f));
        close.addAction(Actions.fadeOut(0));
        close.addAction(Actions.fadeIn(0.4f));
        window.setZIndex(1);
        return this;
    }

    public ClickListener selfHide() {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                hide();
            }
        };
    }

    public void hide() {
        if (!window.isVisible()) return;
        controller.getGame().setSTATE(Const.STATE.PLAY);
        window.addAction(Actions.sequence(Actions.fadeOut(0.2f), new RunnableAction() {
                    @Override
                    public void run() {
                        window.setVisible(false);
                    }
                })
        );
        close.addAction(
                Actions.sequence(
                        Actions.fadeOut(0.2f),
                        new RunnableAction() {
                            @Override
                            public void run() {
                                close.setVisible(false);
                            }
                        }
                ));
    }

    public void toggle() {
        if (window.isVisible()) {
            hide();
        } else {
            show();
        }
    }
}
