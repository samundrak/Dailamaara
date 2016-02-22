/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package np.com.samundrakc.game.anchors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * @author samundra
 */
public class Card {

    private String type;

    public Const.CARDS getCardType() {
        return cardType;
    }

    public void setCardType(Const.CARDS cardType) {
        this.cardType = cardType;
    }

    private Const.CARDS cardType;
    private int id;
    private int number = 0;

    public int getNumber() {
        return this.number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Card(String type) {
        this.type = type;
    }

    public Card(String type, int id) {
        this.type = type;
        this.id = id;
    }

    public Card(String type, int id, int number) {
        this.type = type;
        this.id = id;
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private Image image;

    public void setActor() {
        String name = "red_joker";
        switch (this.number) {
            case 1:
                name = "ace_of_" + this.type;
                break;
            case 11:
                name = "jack_of_" + this.type;
                break;
            case 12:
                name = "queen_of_" + this.type;
                //
                break;
            case 13:
                name = "king_of_" + this.type;
                break;
            default:
                name = this.number + "_of_" + this.type;
                break;
        }
        Image image = new Image(new Texture("cards/" + name + ".png"));
        image.setSize(50, 70);
        this.image = image;
    }

    public Image getActor(float x, float y) {
        image.setPosition(x, y);
        return this.image;
    }

    public Image getActor() {
        return this.image;
    }
}
