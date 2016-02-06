package np.com.samundrakc.game.controllers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SizeToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import java.util.Timer;
import java.util.TimerTask;

import np.com.samundrakc.game.anchors.Const;
import np.com.samundrakc.game.anchors.Game;
import np.com.samundrakc.game.misc.Animation;
import np.com.samundrakc.game.misc.Context;
import np.com.samundrakc.game.misc.Utils;
import np.com.samundrakc.game.screens.Form;

/**
 * Created by samundra on 2/3/2016.
 */
public class FormCtrl {
    private Form view;

    public FormCtrl(Form view) {
        this.view = view;
    }

    public InputListener playButton() {
        return new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("play button clicked");
//                if (Utils.isEmpty(view.getName().getText())) {
//                    return false;
//                }
//                if (Utils.isEmpty(view.getGroup().getText())) {
//                    return false;
//                }

                view.getTable().addAction(Animation.simpleAnimation(-Context.WIDTH, Utils.inCenter(view.getTable(), 'y')));
                view.getSelectPlayerTable().addAction(Animation.simpleAnimation(0, 0));

//            Game game = new Game();
//            game.createGroups(view.getName().getText(),view.getGroup().getText());
//            game.createCards();
//            game.shuffleCardsOFGame(Game.cards);
//            game.selectPlayerToThrowCardsFirstTime(game.players);
                return super.touchDown(event, x, y, pointer, button);
            }
        };
    }


    public InputListener computerCtrl(Button button, final int i) {
        return new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                view.getSelectPlayerTable().addAction(Animation.simpleAnimation(-(Context.WIDTH + 500), 0));
                view.getStacks().addAction(Animation.simpleAnimation(3,(Context.HEIGHT -(Context.HEIGHT-50))));
                if(isCardSelected) return  true;
                final int[] xx = {3};
                final int[] yy = {3};
                final int[] gap = {3};
                final int[] i = {0};
                final Timer timer = new Timer();
                timer.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        if (i[0] == Const.TOTAL_NUMBER_OF_CARDS) {
                            timer.cancel();
                            view.getStacks().setTouchable(Touchable.enabled);
                            Button button = new TextButton("Back",Context.skin);
                            button.setPosition((Context.WIDTH/2 - button.getWidth()),-button.getHeight());
                            button.addListener(new InputListener() {
                                @Override
                                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                                    view.getStacks().setPosition((Context.WIDTH + 500), 50);
                                    view.getSelectPlayerTable().addAction(Animation.simpleAnimation((0), 0));
                                    return super.touchDown(event, x, y, pointer, button);
                                }
                            });
                             view.getStacks().addActor(button);
                            return;
                        }
                        if (xx[0] >= (Context.WIDTH - 50)) {
                            yy[0] += 70 + gap[0];
                            xx[0] = gap[0];
                        }
                        view.getStacks().getChildren().get(i[0]).addAction(Actions.sequence(Animation.simpleAnimation(xx[0], yy[0]), Actions.sequence(Animation.sizeActionPlus(60, 80, 0.2f), Animation.sizeActionPlus(50, 70, 0.2f))));
                        xx[0] += view.getStacks().getChildren().get(i[0]).getWidth() + gap[0];
                        i[0]++;
                    }
                }, 100, 100);
                return true;
            }
        };
    }

    public InputListener backButton() {
        return new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                view.getTable().addAction(Animation.simpleAnimation(Utils.inCenter(view.getStacks(), 'x'), Utils.inCenter(view.getTable(), 'y')));
                view.getSelectPlayerTable().addAction(Animation.simpleAnimation((Context.WIDTH), 0));
                return super.touchDown(event, x, y, pointer, button);
            }
        };
    }
    private boolean isCardSelected = false;
    public EventListener cardsListener(final int i, final Actor actor) {
        return new InputListener() {
            @Override
            public boolean mouseMoved(InputEvent event, float x, float y) {
                actor.addAction(Actions.sequence(Animation.sizeActionPlus(60, 80, 0.5f), Animation.sizeActionPlus(50, 70, 0.5f)));
                return super.mouseMoved(event, x, y);
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(isCardSelected) return  true;
                actor.setSize(50,70);
                Image image = Game.cards.get(i).getActor(actor.getX(),actor.getY());
                view.getStacks().addActor(image);
                isCardSelected = true;
                return super.touchDown(event, x, y, pointer, button);
            }
        };
    }

    public static class BackCover extends Image {
        public void setIsCardFlipped(boolean isCardFlipped) {
            this.isCardFlipped = isCardFlipped;
        }

        private boolean isCardFlipped = false;

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        private int index;
        private int width = 50, height = 70;

        public BackCover(int index) {
            super(new Texture("cards/back.png"));
            super.setSize(width, height);
            this.index = index;
        }
    }

}