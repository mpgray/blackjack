package blackJack;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;


public class Card {

    public int value;
    public Face face;
    public Suit suit;

    //Suits for Cards
    enum Suit {
        HEARTS, DIAMONDS, CLUBS, SPADES
    }

    //Face value for Cards
    enum Face {
        TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(9), TEN(10),
        JACK(10), QUEEN(10), KING(10), ACE(11);

        int value;

        private Face(int value) {
            this.value = value;
        }
    }

    private static HashMap<String, Image> cardImages;
    private static int instantiateCount = 0;

    //Creates a card
    public Card(Face face, Suit suit) {

        this.suit = suit;
        this.face = face;
        this.value = face.value;
    }

    private static void instantiateCardImages() {
        cardImages = new HashMap<>();
        char[] prefixes = {'c', 'd', 'h', 's', 'b'};
        for (char prefix :
                prefixes) {
            int i = 1;

            while (i < 14) {
                String imgName = String.format("%c%d", prefix, i);
                // only get the back card once.
                if (prefix == 'b') {
                    i = 13;
                    imgName = "back";
                }
                InputStream card = Card.class.getResourceAsStream("/" + imgName + ".png");
                try {
                    Image img = ImageIO.read(card);
                    img = img.getScaledInstance(100, 123, Image.SCALE_DEFAULT);
                    cardImages.putIfAbsent(imgName, img);
                } catch (IOException | NullPointerException e) {
                    if(e instanceof NullPointerException) {
                        System.out.println(imgName);
                    }
                }
                i++;
            }
        }
    }

    public static ImageIcon getCardAsImage(String key) {
        try {
            Image card = cardImages.get(key);
            return new ImageIcon(card);
        } catch (NullPointerException e) {
            if (instantiateCount > 3) {
                //throw an error we won't try more than 3 times
                throw e;
            }

            instantiateCount++;
            instantiateCardImages();
            //get the card again
            return getCardAsImage(key);
        }
    }


    //Returns the Face Value for the card as a string
    public String toString() {
        return face.toString();
    }

}
