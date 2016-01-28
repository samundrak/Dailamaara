package np.com.samundrakc.game.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * Created by samundra on 1/29/2016.
 */
public class MenuCtrl {

    public boolean detectTouch(Texture item){
            if(Gdx.input.justTouched()){
                int userX =  Gdx.input.getX();
                int userY =  Gdx.input.getY();
                System.out.println(userX+"    "+userY);
            }
        return false;
    }
}
