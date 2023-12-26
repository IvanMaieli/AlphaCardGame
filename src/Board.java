import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Collections;
import java.util.LinkedList;

public class Board extends JFrame {

    private JPanel deckPanelP1;
    private JPanel deckPanelP2;
    private JPanel fieldP1;
    private JPanel fieldP2;
    private JPanel buttonField;
    private JButton attackButton;
    private JButton deleteButton;
    private LinkedList<Card> cards;
    private LinkedList<Card> deckP1;
    private JPanel[] cardPanelsP1;
    private LinkedList<Card> deckP2;
    private JPanel[] cardPanelsP2;
    private int cardWidth;
    private int cardHeight;

    public Board() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();

        int height = (int) (screenHeight - (screenHeight * 0.1));
        int width = (int) (screenWidth - (screenWidth * 0.11));

        this.setTitle("CyberAttack");
        this.setBounds((screenWidth - width) / 2, (screenHeight - height) / 2, width, height);
        this.setVisible(true);
        this.setLayout(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        int panelWidth = (int)(width - 20 - screenWidth / 100 * 0.8);
        int panelHeight = (height - 85) / 4;
        int fieldWidth = width / 7 * 3;

        deckPanelP1 = new JPanel();
        deckPanelP1.setBounds(10, 10, panelWidth, panelHeight);
        deckPanelP1.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        deckPanelP1.setLayout(null);
        deckPanelP1.setVisible(true);
        this.getContentPane().add(deckPanelP1);

        deckPanelP2 = new JPanel();
        deckPanelP2.setBounds(10, (10 * 4) + (panelHeight * 3), panelWidth, panelHeight);
        deckPanelP2.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        deckPanelP2.setLayout(null);
        deckPanelP2.setVisible(true);
        this.getContentPane().add(deckPanelP2);
        System.out.println("width: " + width);
        System.out.println("height: " + height);
        System.out.println("panelWidth: " + panelWidth);
        System.out.println("panelHeight: " + panelHeight);

        fieldP1 = new JPanel();
        fieldP1.setBounds((width - fieldWidth) / 2, (10 * 2) + (panelHeight), fieldWidth, panelHeight);
        fieldP1.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        fieldP1.setLayout(null);
        fieldP1.setVisible(true);
        this.getContentPane().add(fieldP1);

        fieldP2 = new JPanel();
        fieldP2.setBounds((width - fieldWidth) / 2, (10 * 3) + (panelHeight * 2), fieldWidth, panelHeight);
        fieldP2.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        fieldP2.setLayout(null);
        fieldP2.setVisible(true);
        this.getContentPane().add(fieldP2);

        int buttonFieldHeight = 200;
        int buttonFieldWidth = 200;

        buttonField = new JPanel();
        buttonField.setBounds((int)((width - screenWidth / 100 * 0.8) / 7 * 5.5), (height - buttonFieldHeight - 32) / 2, buttonFieldWidth, buttonFieldHeight);
        buttonField.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        buttonField.setLayout(null);
        buttonField.setVisible(true);
        this.getContentPane().add(buttonField);

        attackButton = new JButton("Attacca");
        attackButton.setBounds(10, 10, 180, 85);
        attackButton.addMouseListener(new BoardListener());
        attackButton.setFocusPainted(false);
        buttonField.add(attackButton);
        attackButton.setVisible(true);

        deleteButton = new JButton("Cancella tutto");
        deleteButton.setBounds(10, 105, 180, 85);
        deleteButton.addMouseListener(new BoardListener());
        deleteButton.setFocusPainted(false);
        buttonField.add(deleteButton);
        deleteButton.setVisible(true);

        cardHeight = panelHeight - 20;
        cardWidth = (int) (panelWidth - 80) / 7;
        cardPanelsP1 = new JPanel[7];
        cardPanelsP2 = new JPanel[7];
        for(int i = 0; i < 7; i++) {
            cardPanelsP1[i] = new JPanel();
            cardPanelsP1[i].setBounds((10 * (i + 1)) + (cardWidth * i), 10, cardWidth, cardHeight);
            cardPanelsP1[i].setLayout(null);
            cardPanelsP1[i].setVisible(true);
            deckPanelP1.add(cardPanelsP1[i]);

            cardPanelsP2[i] = new JPanel();
            cardPanelsP2[i].setBounds((10 * (i + 1)) + (cardWidth * i), 10, cardWidth, cardHeight);
            cardPanelsP2[i].setLayout(null);
            cardPanelsP2[i].setVisible(true);
            deckPanelP2.add(cardPanelsP2[i]);
        }


        mix();

        positioning();

        repaint();
    }

    private void positioning() {

    }

    private void mix() {
        cards = new LinkedList<>();
        deckP1 = new LinkedList<>();
        deckP2 = new LinkedList<>();

        for (int i = 0; i < 40; i++) {
            if(i < 20) cards.add(new CyberCrab(cardWidth, cardHeight));
            else if (i < 30) cards.add(new Gigatron(cardWidth, cardHeight));
            else cards.add(new NanoMech(cardWidth, cardHeight));
        }

        Collections.shuffle(cards);

        for(int i = 0; i < 14; i++) {
            if(i % 2 == 0) deckP1.add(cards.removeFirst());
            else deckP2.add(cards.removeFirst());
        }

        updateDeckView(deckP1, 1);
        updateDeckView(deckP2, 2);
    }

    private void updateDeckView(LinkedList<Card> deck, int p) {
        for(int i = 0; i < 7; i++) {
            if(p == 1) {
                cardPanelsP1[i].removeAll();
                cardPanelsP1[i].add(deckP1.get(i));
            } else {
                cardPanelsP2[i].removeAll();
                cardPanelsP2[i].add(deckP2.get(i));
            }
        }
    }

    private class BoardListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent mouseEvent) {}

        @Override
        public void mousePressed(MouseEvent mouseEvent) {}

        @Override
        public void mouseReleased(MouseEvent mouseEvent) {}

        @Override
        public void mouseEntered(MouseEvent mouseEvent) {}

        @Override
        public void mouseExited(MouseEvent mouseEvent) {}

    }
}
