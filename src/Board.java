import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Collections;
import java.util.LinkedList;

public class Board extends JFrame {
    private JPanel buttonField;
    private JPanel turnField;
    public JButton attackButton;
    private JButton positioningButton;
    private JButton changeTurnButton;
    private LinkedList<Card> cards;
    private int cardWidth;
    private int cardHeight;
    private int attOrderCont;
    private int defOrderCont;
    private int[] attackOrder;
    private int[] defenseOrder;
    private Player p1;
    private Player p2;
    private final Color stdColorCard = new Color(42, 45, 52);
    private final Color epicColorCard = new Color(217, 114, 255);
    private final Color legendaryColorCard = new Color(250, 199, 72);
    private boolean phasePositioning = true;
    private int actualTurn = 1;
    private Player actualTurnPlayer;
    private Player actualWaitPlayer;
    private Font buttonFont = new Font("Helvetica", Font.BOLD, 12);


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
        this.getContentPane().setBackground(stdColorCard);
        this.setBounds((screenWidth - width) / 2, (screenHeight - height) / 2, width, height - 25);
        this.setLayout(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        int buttonFieldHeight = 200;
        int buttonFieldWidth = 200;

        buttonField = new JPanel();
        buttonField.setBackground(new Color(170, 70, 1));
        buttonField.setBounds((int)((width - screenWidth / 100 * 0.8) / 7 * 5.5), (height - buttonFieldHeight - 45) / 2, buttonFieldWidth, buttonFieldHeight);
        buttonField.setBorder(BorderFactory.createLoweredBevelBorder());
        buttonField.setLayout(null);
        buttonField.setVisible(true);
        this.add(buttonField);

        attackButton = new JButton("ATTACCA");
        attackButton.setBackground(new Color(42, 45, 52));
        attackButton.setForeground(new Color(235, 212, 203));
        attackButton.setFont(buttonFont);
        attackButton.setBounds(10, 10, buttonFieldWidth - 20, (buttonFieldHeight - 40) / 3);
        attackButton.addActionListener(new BoardListener());
        attackButton.setFocusPainted(false);
        attackButton.setEnabled(false);
        buttonField.add(attackButton);
        attackButton.setVisible(true);

        positioningButton = new JButton("SCHIERA");
        positioningButton.setBackground(new Color(42, 45, 52));
        positioningButton.setForeground(new Color(235, 212, 203));
        positioningButton.setFont(buttonFont);
        positioningButton.setBounds(10, 20 + (buttonFieldHeight - 40) / 3, 180, (buttonFieldHeight - 40) / 3);
        positioningButton.addActionListener(new BoardListener());
        positioningButton.setFocusPainted(false);
        buttonField.add(positioningButton);
        positioningButton.setVisible(true);

        changeTurnButton = new JButton("CAMBIA TURNO");
        changeTurnButton.setBackground(new Color(42, 45, 52));
        changeTurnButton.setForeground(new Color(235, 212, 203));
        changeTurnButton.setFont(buttonFont);
        changeTurnButton.setBounds(10, 30 + ((buttonFieldHeight - 40) / 3) * 2, buttonFieldWidth - 20, (buttonFieldHeight - 40) / 3);
        changeTurnButton.addActionListener(new BoardListener());
        changeTurnButton.setFocusPainted(false);
        changeTurnButton.setEnabled(false);
        buttonField.add(changeTurnButton);
        changeTurnButton.setVisible(true);

        turnField = new JPanel();
        turnField.setBackground(new Color(170, 70, 1));
        turnField.setBounds((int)((width - screenWidth / 100 * 0.8) / 7 * 0.1), (height - (height / 12) - 45) / 2, (int)(buttonFieldWidth / 1.5), height / 12);
        turnField.setBorder(BorderFactory.createLoweredBevelBorder());
        turnField.setLayout(null);
        turnField.setVisible(true);
        this.add(turnField);

        attackOrder = new int[3];
        attOrderCont = 0;
        defenseOrder = new int[3];
        defOrderCont = 0;

        p1 = new Player(panelWidth, panelHeight, 1, new Color(85, 87, 93), false, this);
        p1.setBounds(10, 10, panelWidth, panelHeight);
        p1.setLayout(null);
        p1.setVisible(true);
        this.add(p1);

        p2 = new Player(panelWidth, panelHeight, 2, new Color(85, 87, 93), true, this);
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
        p1.changeTurn();
        p2.changeTurn();
        this.actualTurnPlayer = p1;
        this.actualWaitPlayer = p2;
        p2.waitTurn();
        p1.chooseCards(cards);
    }


    private void mix() {
        cards = new LinkedList<>();

        for (int i = 0; i < 50; i++) {
            if(i < 2) cards.add(new Nebula(i, cardWidth, cardHeight, legendaryColorCard));
            else if (i < 4) cards.add(new Nightmare(i, cardWidth, cardHeight, legendaryColorCard));
            else if (i < 8) cards.add(new Gigaorso(i, cardWidth, cardHeight, epicColorCard));
            else if (i < 12) cards.add(new Gigaworm(i, cardWidth, cardHeight, epicColorCard));
            else if (i < 15) cards.add(new AntsHorde(i, cardWidth, cardHeight, stdColorCard));
            else if (i < 22) cards.add(new ElRaton(i, cardWidth, cardHeight, stdColorCard));
            else if (i < 30) cards.add(new CyberWolf(i, cardWidth, cardHeight, stdColorCard));
            else cards.add(new Spaceman(i, cardWidth, cardHeight, stdColorCard));
        }

        Collections.shuffle(cards);

        LinkedList<Card> deckP1 = new LinkedList<>();
        LinkedList<Card> deckP2 = new LinkedList<>();

        for(int i = 0; i < 14; i++) {
            if (i % 2 == 0) deckP1.add(cards.removeFirst());
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


    public void placeAttack(Card card, Player player) {
        if(attOrderCont < 3) {
            int i = 0;
            for (Card c : player.getSelectedCards()) {
                c.setEnabled(false);
                if (c.equals(card)) {
                    c.setEnabled(false);
                    c.changeBkg(attOrderCont);
                    attackOrder[attOrderCont] = i;
                    attOrderCont++;
                }
                i++;
            }
            this.actualWaitPlayer.enableField(defenseOrder, defOrderCont);
        }
    }


    public void placeDefense(Card card, Player player) {
        if(defOrderCont < 3) {
            int i = 0;
            for (Card c : player.getSelectedCards()) {
                c.setEnabled(false);
                if (c.equals(card)) {
                    c.changeBkg(defOrderCont);
                    defenseOrder[defOrderCont] = i;
                    defOrderCont++;
                }
                i++;
            }
            this.actualTurnPlayer.enableField();
        }
        if (defOrderCont == 3) {
            this.actualTurnPlayer.disableField();
            this.attackButton.setEnabled(true);
        }
    }


    private void attack() {
        for (int i = 0; i < attackOrder.length; i++) {
            int j = 0;
            Card attacker;
            Card victim;
            int damage = 0;
            for (Card c : actualTurnPlayer.getSelectedCards()) {
                if (attackOrder[i] == j) {
                    attacker = c;
                    damage = c.getAttack();
                    //System.out.println(attacker.getName() + "\n" + damage);
                    break;
                }
                j++;
            }
            j = 0;
            for (Card c : actualWaitPlayer.getSelectedCards()) {
                if (defenseOrder[i] == j) {
                    victim = c;
                    victim.setDefense(victim.getDefense() - damage);
                    break;
                }
                j++;
            }
        }
        actualWaitPlayer.updateCards(actualTurnPlayer);
        actualTurnPlayer.updateCards(actualWaitPlayer);
        actualTurnPlayer.waitTurn();
        actualTurnPlayer.updateView();
        actualWaitPlayer.updateView();

        this.repaint();

        this.attOrderCont = 0;
        this.defOrderCont = 0;
        for (int i = 0; i < attackOrder.length; i++) {
            attackOrder[i] = -1;
            defenseOrder[i] = -1;
        }
    }


    public class BoardListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == changeTurnButton) {
                if (actualTurn < 3) {
                    actualTurn++;
                    chooseCards(actualWaitPlayer, actualTurnPlayer);
                    positioningButton.setEnabled(true);
                    changeTurnButton.setEnabled(false);
                } else {
                    if (actualTurnPlayer.checkSelectedCards()) {
                        actualTurn++;
                        changeTurnButton.setEnabled(false);
                        positioningButton.setEnabled(true);
                        actualTurnPlayer.disableDeck();
                        phasePositioning = !phasePositioning;
                        chooseCards(actualWaitPlayer, actualTurnPlayer);
                    }
                }
            }

            if (e.getSource() == positioningButton) {
                if (actualTurnPlayer.checkSelectedCards()) {
                    if (actualTurn > 2) {
                        phasePositioning = !phasePositioning;
                        actualTurnPlayer.disableDeck();
                        positioningButton.setEnabled(false);
                    } else {
                        actualTurnPlayer.waitTurn();
                        actualTurnPlayer.disableField();
                        actualTurnPlayer.disableDeck();
                        changeTurnButton.setEnabled(true);
                        positioningButton.setEnabled(false);
                    }

                }
            }

            if (e.getSource() == attackButton) {
                attack();
                attackButton.setEnabled(false);
                changeTurnButton.setEnabled(true);
            }
        }
    }
}
