package blackJack;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.*;

public class BlackJackApp extends JFrame {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private CardLabel playerCard1 = playerCard1();
    private CardLabel playerCard2 = playerCard2();
    private CardLabel playerCard3 = playerCard3();
    private CardLabel playerCard4 = playerCard4();
    private CardLabel playerCard5 = playerCard5();
    private CardLabel dealerCard1 = dealerCard1();
    private CardLabel dealerCard2 = dealerCard2();
    private CardLabel dealerCard3 = dealerCard3();
    private CardLabel dealerCard4 = dealerCard4();
    private CardLabel dealerCard5 = dealerCard5();
    private JLabel lblPlayerScore = playerScore();
    private JLabel lblDealerScore = dealerScore();
    private JLabel lblWinOrLose = winOrLose();
    private JLabel playArea = playArea();
    private Deck deck = new Deck();
    private Hand player = new Hand();
    private Hand dealer = new Hand();

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    BlackJackApp frame = new BlackJackApp();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public BlackJackApp() {
        setTitle("Blackjack");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 500);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JButton btnPlay = new JButton("Play");
        JButton btnHit = new JButton("Hit");
        JButton btnStand = new JButton("Stay");


        //Play Button Action Listener
        btnPlay.addActionListener(e -> {
            deck.shuffle();
            resetGame(player, dealer);
            lblWinOrLose.setText("Win Or Lose");
            btnHit.setEnabled(true);
            btnStand.setEnabled(true);
            btnPlay.setEnabled(false);

            player.drawing(deck.draw());
            player.drawing(deck.draw());
            lblPlayerScore.setText("Player Score: " + Integer.toString(player.returnTotal()));

            dealer.drawing(deck.draw());
            //The hidden hole card must not be displayed in the score.
            lblDealerScore.setText("Dealer Score: " + Integer.toString(dealer.returnTotal()));
            dealer.drawing(deck.draw());

            playerCard1.setFace(player.cards.get(0).face.toString());
            playerCard2.setFace(player.cards.get(1).face.toString());
            redrawCard(1, player.cards.get(0), false);
            redrawCard(2, player.cards.get(1), false);


            dealerCard1.setFace(dealer.cards.get(0).face.toString());
            redrawCard(1, dealer.cards.get(0), true);
            redrawCard(2, true);

            //Player has a natural 21
            if (player.returnTotal() == 21) {
                btnHit.setEnabled(false);
                btnPlay.setEnabled(false);
            }

        });
        btnPlay.setBounds(50, 11, 89, 23);

        //Hit Button Action Listener
        btnHit.addActionListener(e -> {

            player.drawing(deck.draw());
            lblPlayerScore.setText("Player Score: " + Integer.toString(player.returnTotal()));
            if (player.returnTotal() < 21) {
                if (playerCard3.getFace().equals("")) {
                    playerCard3.setFace(player.cards.get(2).face.toString());
                    redrawCard(3, player.cards.get(2), false);
                    lblPlayerScore.setText("Player Score: " + Integer.toString(player.returnTotal()));
                } else if (playerCard4.getFace().equals("")) {
                    playerCard4.setFace(player.cards.get(3).face.toString());
                    redrawCard(4, player.cards.get(3), false);
                    lblPlayerScore.setText("Player Score: " + Integer.toString(player.returnTotal()));
                } else {
                    playerCard5.setFace(player.cards.get(4).face.toString());
                    redrawCard(5, player.cards.get(4), false);
                    lblPlayerScore.setText("Player Score: " + Integer.toString(player.returnTotal()));
                }
            } else if (player.returnTotal() == 21) {
                if (playerCard3.getFace().equals("")) {
                    playerCard3.setFace(player.cards.get(2).face.toString());
                    redrawCard(3, player.cards.get(2), false);
                } else if (playerCard4.getFace().equals("")) {
                    playerCard4.setFace(player.cards.get(3).face.toString());
                    redrawCard(4, player.cards.get(3), false);
                } else {
                    playerCard5.setFace(player.cards.get(4).face.toString());
                    redrawCard(5, player.cards.get(4), false);
                }
                //Only allow a stay if the player hits a 21
                btnHit.setEnabled(false);
                btnPlay.setEnabled(false);
            } else {
                lblWinOrLose.setText("Player Busts. You Lose.");
                lblPlayerScore.setText("Player Score: " + Integer.toString(player.returnTotal()));
                if (playerCard3.getFace().equals("")) {
                    playerCard3.setFace(player.cards.get(2).face.toString());
                    redrawCard(3, player.cards.get(2), false);
                } else if (playerCard4.getFace().equals("")) {
                    playerCard4.setFace(player.cards.get(3).face.toString());
                    redrawCard(4, player.cards.get(3), false);
                } else {
                    playerCard5.setFace(player.cards.get(4).face.toString());
                    redrawCard(5, player.cards.get(4), false);
                }
                btnHit.setEnabled(false);
                btnStand.setEnabled(false);
                btnPlay.setEnabled(true);

            }

        });
        btnHit.setBounds(50, 175, 89, 23);

        //Action Listener for Stand Button
        //Action Listener for Stand Button
        btnStand.addActionListener(e -> {
            btnHit.setEnabled(false);
            btnStand.setEnabled(false);


            // Dealer has a hidden card in the hole, we need to reveal it first.
            if (dealerCard2.getFace().equals("")) {
                dealerCard2.setFace(dealer.cards.get(1).face.toString());
                redrawCard(2, dealer.cards.get(1), true);
                lblDealerScore.setText("Dealer Score: " + Integer.toString(dealer.returnTotal()));

            }
            while (17 > dealer.returnTotal()) {
                dealer.drawing(deck.draw());
                if (dealerCard3.getFace().equals("")) {
                    dealerCard3.setFace(dealer.cards.get(2).face.toString());
                    redrawCard(3, dealer.cards.get(2), true);
                    lblDealerScore.setText("Dealer Score: " + Integer.toString(dealer.returnTotal()));
                } else if (dealerCard4.getFace().equals("")) {
                    dealerCard4.setFace(dealer.cards.get(3).face.toString());
                    redrawCard(4, dealer.cards.get(3), true);
                    lblDealerScore.setText("Dealer Score: " + Integer.toString(dealer.returnTotal()));
                } else {
                    dealerCard5.setFace(dealer.cards.get(4).face.toString());
                    redrawCard(5, dealer.cards.get(4), true);
                    lblDealerScore.setText("Dealer Score: " + Integer.toString(dealer.returnTotal()));
                }
            }
            if (21 < dealer.returnTotal()) {
                lblWinOrLose.setText("Dealer Busts. You Win.");
                btnPlay.setEnabled(true);
            }

            if (dealer.returnTotal() < 22 && dealer.returnTotal() > 16) {
                if (dealer.returnTotal() < player.returnTotal()) {
                    lblWinOrLose.setText("You Win.");
                } else if (dealer.returnTotal() > player.returnTotal()) {
                    lblWinOrLose.setText("You Lose.");
                } else {
                    lblWinOrLose.setText("You Tie.");
                }

                try {
                    writeToFile();
                } catch (IOException io) {
                    // TODO Auto-generated catch block
                    io.printStackTrace();
                } finally {
                    btnPlay.setEnabled(true);
                }
            }


        });
        btnStand.setBounds(50, 230, 89, 23);

        contentPane.add(playerCard1);
        contentPane.add(playerCard2);
        contentPane.add(playerCard3);
        contentPane.add(playerCard4);
        contentPane.add(playerCard5);
        contentPane.add(dealerCard1);
        contentPane.add(dealerCard2);
        contentPane.add(dealerCard3);
        contentPane.add(dealerCard4);
        contentPane.add(dealerCard5);
        contentPane.add(lblPlayerScore);
        contentPane.add(lblDealerScore);
        contentPane.add(lblWinOrLose);
        contentPane.add(playArea);
        contentPane.add(btnPlay);
        contentPane.add(btnHit);
        contentPane.add(btnStand);

    }

    private void resetGame(Hand... hands) {
        for (Component component :
                contentPane.getComponents()) {
            if (component instanceof CardLabel) {
                ((CardLabel) component).setFace("");
                ((CardLabel) component).setIcon(null);
            }
        }

        for (Hand hand :
                hands) {
            hand.resetGame();
        }
    }

    private CardLabel getCard(int whichCard, boolean dealer) {
        CardLabel cardLabel = null;
        switch (whichCard) {
            case 1:
                cardLabel = dealer ? dealerCard1 : playerCard1;
                break;
            case 2:
                cardLabel = dealer ? dealerCard2 : playerCard2;
                break;
            case 3:
                cardLabel = dealer ? dealerCard3 : playerCard3;
                break;
            case 4:
                cardLabel = dealer ? dealerCard4 : playerCard4;
                break;
            case 5:
                cardLabel = dealer ? dealerCard5 : playerCard5;
                break;
            default:
                break;

        }

        return cardLabel;
    }

    //draw a card face down. Useful for the dealer
    private void redrawCard(int whichCard, boolean dealer) {
        CardLabel cardLabel = getCard(whichCard, dealer);

        try {
            ImageIcon cardImage = Card.getCardAsImage("back");
            cardLabel.setIcon(cardImage);
        } catch (NullPointerException e) {
            // print a warning
            e.printStackTrace();
        }
    }


    private void redrawCard(int whichCard, Card card, boolean dealer) {
        CardLabel cardLabel = getCard(whichCard, dealer);
        try {
            String key = getKeyFromCard(card);
            ImageIcon cardImage = Card.getCardAsImage(key);
            cardLabel.setIcon(cardImage);
        } catch (NullPointerException e) {
            // print a warning
            e.printStackTrace();
        }
    }

    private JLabel playArea() {
        JLabel playArea = new JLabel("");
        playArea.setOpaque(true);
        playArea.setBackground(new Color(0, 128, 0));
        playArea.setBounds(201, 0, 583, 461);
        return playArea;
    }

    private JLabel winOrLose() {
        JLabel lblWinOrLose = new JLabel("Win Or Lose");
        lblWinOrLose.setBounds(400, 265, 400, 14);
        return lblWinOrLose;
    }

    private JLabel dealerScore() {
        JLabel lblDealerScore = new JLabel("Dealer Score: ");
        lblDealerScore.setBounds(400, 215, 200, 14);
        return lblDealerScore;
    }

    private JLabel playerScore() {
        JLabel lblPlayerScore = new JLabel("Player Score: ");
        lblPlayerScore.setBounds(400, 240, 100, 14);
        return lblPlayerScore;
    }

    private CardLabel dealerCard5() {
        CardLabel dealerCard5 = new CardLabel("");
        dealerCard5.setBackground(new Color(0, 128, 0));
        dealerCard5.setHorizontalAlignment(SwingConstants.CENTER);
        dealerCard5.setHorizontalTextPosition(JLabel.CENTER);
        dealerCard5.setOpaque(true);
        dealerCard5.setBounds(660, 25, 100, 125);
        return dealerCard5;
    }

    private CardLabel dealerCard4() {
        CardLabel dealerCard4 = new CardLabel("");
        dealerCard4.setBackground(new Color(0, 128, 0));
        dealerCard4.setHorizontalAlignment(SwingConstants.CENTER);
        dealerCard4.setHorizontalTextPosition(JLabel.CENTER);
        dealerCard4.setOpaque(true);
        dealerCard4.setBounds(550, 25, 100, 125);
        return dealerCard4;
    }

    private CardLabel dealerCard3() {
        CardLabel dealerCard3 = new CardLabel("");
        dealerCard3.setBackground(new Color(0, 128, 0));
        dealerCard3.setHorizontalAlignment(SwingConstants.CENTER);
        dealerCard3.setHorizontalTextPosition(JLabel.CENTER);
        dealerCard3.setOpaque(true);
        dealerCard3.setBounds(440, 25, 100, 125);
        return dealerCard3;
    }

    private CardLabel dealerCard2() {
        CardLabel dealerCard2 = new CardLabel("");
        dealerCard2.setBackground(new Color(0, 128, 0));
        dealerCard2.setHorizontalAlignment(SwingConstants.CENTER);
        dealerCard2.setHorizontalTextPosition(JLabel.CENTER);
        dealerCard2.setOpaque(true);
        dealerCard2.setBounds(330, 25, 100, 125);
        return dealerCard2;
    }

    private CardLabel dealerCard1() {
        CardLabel dealerCard1 = new CardLabel("");
        dealerCard1.setBackground(new Color(0, 128, 0));
        dealerCard1.setHorizontalAlignment(SwingConstants.CENTER);
        dealerCard1.setHorizontalTextPosition(JLabel.CENTER);
        dealerCard1.setOpaque(true);
        dealerCard1.setBounds(220, 25, 100, 125);
        return dealerCard1;
    }

    private CardLabel playerCard5() {
        CardLabel playerCard5 = new CardLabel("");
        playerCard5.setBackground(new Color(0, 128, 0));
        playerCard5.setHorizontalAlignment(SwingConstants.CENTER);
        playerCard5.setHorizontalTextPosition(JLabel.CENTER);
        playerCard5.setOpaque(true);
        playerCard5.setBounds(660, 325, 100, 125);
        return playerCard5;
    }

    private CardLabel playerCard4() {
        CardLabel playerCard4 = new CardLabel("");
        playerCard4.setBackground(new Color(0, 128, 0));
        playerCard4.setHorizontalAlignment(SwingConstants.CENTER);
        playerCard4.setHorizontalTextPosition(JLabel.CENTER);
        playerCard4.setOpaque(true);
        playerCard4.setBounds(550, 325, 100, 125);
        return playerCard4;
    }

    private CardLabel playerCard3() {
        CardLabel playerCard3 = new CardLabel("");
        playerCard3.setBackground(new Color(0, 128, 0));
        playerCard3.setHorizontalAlignment(SwingConstants.CENTER);
        playerCard3.setHorizontalTextPosition(JLabel.CENTER);
        playerCard3.setOpaque(true);
        playerCard3.setBounds(440, 325, 100, 125);
        return playerCard3;
    }

    private CardLabel playerCard2() {
        CardLabel playerCard2 = new CardLabel("");
        playerCard2.setBackground(new Color(0, 128, 0));
        playerCard2.setHorizontalAlignment(SwingConstants.CENTER);
        playerCard2.setHorizontalTextPosition(JLabel.CENTER);
        playerCard2.setOpaque(true);
        playerCard2.setBounds(330, 325, 100, 125);
        return playerCard2;
    }

    private CardLabel playerCard1() {
        CardLabel playerCard1 = new CardLabel("");
        playerCard1.setBackground(new Color(0, 128, 0));
        playerCard1.setHorizontalAlignment(SwingConstants.CENTER);
        playerCard1.setHorizontalTextPosition(JLabel.CENTER);
        playerCard1.setOpaque(true);
        playerCard1.setBounds(220, 325, 100, 125);
        return playerCard1;
    }

    private String getKeyFromCard(Card card) {
        int value = 0;
        if (card.face.value >= 10) {
            switch (card.face) {
                case TEN:
                    value = 10;
                    break;
                case JACK:
                    value = 11;
                    break;
                case QUEEN:
                    value = 12;
                    break;
                case KING:
                    value = 13;
                    break;
                case ACE:
                    value = 1;
                    break;
                default:
                    break;
            }
        } else {
            value = card.face.value;
        }
        return String.format("%c%d", card.suit.toString().toLowerCase().charAt(0), value);
    }

    private void writeToFile() throws IOException {


        String path = String.format("%s%c%s", System.getProperty("user.dir"), File.separatorChar, "gameFile.txt");

        try (FileWriter fw = new FileWriter(path, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter pt = new PrintWriter(bw)) {

            pt.println("Game Result");
            pt.printf("%10s\t%10s\t%10s%n", "result", "you", "dealer");

            pt.printf("%10s\t%10d\t%10d%n", lblWinOrLose.getText(), dealer.returnTotal(), player.returnTotal());
            System.out.println("Writing to File: done");
        } catch (FileNotFoundException e) {
            System.out.println("something s wrong");
        }
    }

}
