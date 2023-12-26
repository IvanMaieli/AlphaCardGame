import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Collections;
import java.util.LinkedList;

public class Board extends JFrame {
    private JPanel buttonField;
    private JButton attackButton;
    private JButton deleteButton;
    private LinkedList<Card> cards;
    private int cardWidth;
    private int cardHeight;
    private Player p1;
    private Player p2;

    public Board() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();

        int height = (int) (screenHeight - (screenHeight * 0.1));
        int width = (int) (screenWidth - (screenWidth * 0.11));

        int panelWidth = (int)(width - 20 - screenWidth / 100 * 0.8);
        int panelHeight = (height - 85) / 2;

        cardHeight = (panelHeight / 2) - 20;
        cardWidth = (int) (panelWidth - 80) / 7;

        this.setTitle("CyberAttack");
        this.setBounds((screenWidth - width) / 2, (screenHeight - height) / 2, width, height);
        this.setLayout(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        int buttonFieldHeight = 200;
        int buttonFieldWidth = 200;

        buttonField = new JPanel();
        buttonField.setBounds((int)((width - screenWidth / 100 * 0.8) / 7 * 5.5), (height - buttonFieldHeight - 32) / 2, buttonFieldWidth, buttonFieldHeight);
        buttonField.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        buttonField.setLayout(null);
        buttonField.setVisible(true);
        this.add(buttonField);

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

        p1 = new Player(panelWidth, panelHeight, 1, Color.RED, true);
        p1.setBounds(10, 10, panelWidth, panelHeight);
//        p1.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));
        p1.setLayout(null);
        p1.setVisible(true);
        this.add(p1);

        p2 = new Player(panelWidth, panelHeight, 2, Color.BLUE, false);
        p2.setBounds(10, panelHeight + 20, panelWidth, panelHeight);
//        p2.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));
        p2.setLayout(null);
        p2.setVisible(true);
        this.add(p2);

        mix();
        p1.revalidate();
        p1.repaint();
        p2.revalidate();
        p2.repaint();
        validate();
        repaint();

        this.setVisible(true);
    }

    private void mix() {
        cards = new LinkedList<>();

        for (int i = 0; i < 40; i++) {
            if(i < 20) cards.add(new CyberCrab(cardWidth, cardHeight));
            else cards.add(new Gigatron(cardWidth, cardHeight));
        }

        Collections.shuffle(cards);

        LinkedList<Card> deckP1 = new LinkedList<>();
        LinkedList<Card> deckP2 = new LinkedList<>();

        for(int i = 0; i < 14; i++) {
            if(i % 2 == 0) deckP1.add(cards.removeFirst());
            else deckP2.add(cards.removeFirst());
        }

        p1.giveCards(deckP1);
        p2.giveCards(deckP2);
        p1.revalidate();
        p1.repaint();
        p2.revalidate();
        p2.repaint();
        repaint();
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
