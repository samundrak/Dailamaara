package np.com.samundrakc.game.controllers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

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

                view.getTable().addAction(Animation.simpleAnimation(-500, 0));
                view.getSelectPlayerTable().addAction(Animation.simpleAnimation(-(Context.WIDTH), 0));

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
                System.out.println(i);
                return super.touchDown(event, x, y, pointer, button);
            }
        };
    }

    public InputListener backButton() {
        return new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                view.getTable().addAction(Animation.simpleAnimation(500, 0));
                view.getSelectPlayerTable().addAction(Animation.simpleAnimation((Context.WIDTH), 0));
                return super.touchDown(event, x, y, pointer, button);
            }
        };
    }

    public EventListener cardsListener(final int i,final Actor actor) {
         return new InputListener(){
             @Override
             public boolean mouseMoved(InputEvent event, float x, float y) {
                 actor.addAction(Actions.sequence(Animation.sizeActionPlus(60,80,0.5f),Animation.sizeActionPlus(50,70,0.5f)));
                 return super.mouseMoved(event, x, y);
             }

             @Override
             public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                 System.out.println(event.getType());
                 return super.touchDown(event, x, y, pointer, button);
             }
         };
    }

    public static class BackCover extends Image{
        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        private int index;
        private int width =50, height =70;
        public BackCover(int index) {
            super(new Texture("cards/back.png"));
            super.setSize(width,height);
            this.index =  index;
        }


    }
}
