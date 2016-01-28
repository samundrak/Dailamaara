/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package np.com.samundrakc.game.anchors;

/**
 *
 * @author samundra
 */
public class Card {

    private String type;
    private int id;
    private int number;

    public int getNumber() {
        return number;
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
}
