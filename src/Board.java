import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Collections;
import java.util.LinkedList;

public class Board extends JFrame {
    private JPanel buttonField;
    public JButton attackButton;
    private JButton deleteButton;
    private JButton positioningButton;
    private LinkedList<Card> cards;
    private int cardWidth;
    private int cardHeight;
    private Player p1;
    private Player p2;
    private final Color stdColorCard = new Color(28, 49, 68);
    private final Color epicColorCard = new Color(241, 233, 219);
    private final Color legendaryColorCard = new Color(250, 199, 72);
    private boolean phasePositioning = true;
    private int actualTurn = 1;

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
        this.getContentPane().setBackground(new Color(232, 241, 242));
        this.setBounds((screenWidth - width) / 2, (screenHeight - height) / 2, width, height);
        this.setLayout(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        int buttonFieldHeight = 200;
        int buttonFieldWidth = 200;

        Font buttonFont = new Font("Monospaced", Font.BOLD, 16);

        buttonField = new JPanel();
        buttonField.setBackground(new Color(199, 81, 70));
        buttonField.setBounds((int)((width - screenWidth / 100 * 0.8) / 7 * 5.5), (height - buttonFieldHeight + 14) / 2, buttonFieldWidth, buttonFieldHeight - 65);
        buttonField.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        buttonField.setLayout(null);
        buttonField.setVisible(true);
        this.add(buttonField);

        attackButton = new JButton("ATTACCA");
        attackButton.setBackground(new Color(28, 49, 68));
        attackButton.setForeground(new Color(235, 212, 203));
        attackButton.setFont(buttonFont);
        attackButton.setBounds(10, 10, buttonFieldWidth - 20, (buttonFieldHeight - 40) / 3);
        attackButton.addMouseListener(new BoardListener());
        attackButton.setFocusPainted(false);
        attackButton.setEnabled(false);
        buttonField.add(attackButton);
        attackButton.setVisible(true);

        positioningButton = new JButton("SCHIERA");
        positioningButton.setBackground(new Color(28, 49, 68));
        positioningButton.setForeground(new Color(235, 212, 203));
        positioningButton.setFont(buttonFont);
        positioningButton.setBounds(10, 20 + (buttonFieldHeight - 40) / 3, 180, (buttonFieldHeight - 40) / 3);
        positioningButton.addMouseListener(new BoardListener());
        positioningButton.setFocusPainted(false);
        buttonField.add(positioningButton);
        positioningButton.setVisible(true);


        p1 = new Player(panelWidth, panelHeight, 1, new Color(199, 81, 70), true, this);
        p1.setBounds(10, 10, panelWidth, panelHeight);
        p1.setLayout(null);
        p1.setVisible(true);
        this.add(p1);

        p2 = new Player(panelWidth, panelHeight, 2, new Color(245, 105, 96), false, this);
        p2.setBounds(10, panelHeight + 20, panelWidth, panelHeight);
        p2.setLayout(null);
        p2.setVisible(true);
        this.add(p2);

        mix();

        chooseCards(p1, p2);

        repaint();

        this.setVisible(true);
    }


    private void chooseCards(Player p1, Player p2) {
        p2.waitTurn();
        p1.chooseCards(cards);
    }

    private void mix() {
        cards = new LinkedList<>();

        for (int i = 0; i < 40; i++) {
            if(i < 2) cards.add(new Nebula(i, cardWidth, cardHeight, legendaryColorCard));
            else if(i < 7) cards.add(new Gigatron(i, cardWidth, cardHeight, epicColorCard));
            else if(i < 15) cards.add(new Dragon(i, cardWidth, cardHeight, stdColorCard));
            else if(i < 22) cards.add(new NanoMech(i, cardWidth, cardHeight, stdColorCard));
            else if(i < 30) cards.add(new Wolf(i, cardWidth, cardHeight, stdColorCard));
            else cards.add(new Spaceman(i, cardWidth, cardHeight, stdColorCard));
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

        repaint();
    }

    public boolean isPhasePositioning() {
        return phasePositioning;
    }

    public void setPhasePositioning(boolean phasePositioning) {
        this.phasePositioning = phasePositioning;
    }

    public int getActualTurn() {
        return actualTurn;
    }

    public void setActualTurn(int actualTurn) {
        this.actualTurn = actualTurn;
    }

    public class BoardListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent mouseEvent) {
            if(mouseEvent.getSource() == positioningButton) {
                if(actualTurn < 3) {
                    if (actualTurn % 2 == 0) {
                        if(p2.checkSelectedCards()) {
                            actualTurn++;
                            p1.changeTurn();
                            p2.changeTurn();
                            chooseCards(p1, p2);
                        }
                    } else {
                        if(p1.checkSelectedCards()) {
                            actualTurn++;
                            p1.changeTurn();
                            p2.changeTurn();
                            chooseCards(p2, p1);
                        }
                    }
                } else {
                    phasePositioning = !phasePositioning;
                }
            }
        }

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
