package blackJack;

import javax.swing.*;

/*****************************************
 * Author : Rich Baird
 * Date : 12/5/16
 * Assignment: CardLabel
 * Class GroupProject
 ******************************************/
public class CardLabel extends JLabel {
    private String face = "";
    CardLabel(String text) {
        super(text);
    }

    CardLabel(ImageIcon imageIcon) {
        super(imageIcon);
    }

    CardLabel(ImageIcon imageIcon, String text) {
        super(imageIcon);
        setText(text);
    }

    public void setFace(String face) {
        this.face = face;
    }

    public String getFace() {
        return face;
    }
}
