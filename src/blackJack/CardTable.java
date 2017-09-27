package blackJack;

import java.awt.*;

/*****************************************
 * Author : Rich Baird
 * Date : 12/5/16
 * Assignment: CardTable
 * Class GroupProject
 ******************************************/
public class CardTable extends javax.swing.JPanel {
    CardTable() {
        super();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        for (Component component :
                getComponents()) {
            if (component instanceof CardLabel) {

            }
        }
    }
}
