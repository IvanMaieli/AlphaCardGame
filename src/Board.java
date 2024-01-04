import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.LinkedList;

public class Board extends JFrame {
    private JPanel buttonField;
    private JPanel panelCards;
    private JPanel turnField;
    private JLabel turnLabel;
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
    private Font buttonFont = new Font("Helvetica", Font.BOLD, 14);


    public Board() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();

        int height = (int) (screenHeight - (screenHeight * 0.1));
        int width = (int) (screenWidth - (screenWidth * 0.11));

        int panelWidth = (int)(width - 20 - screenWidth / 100 * 0.8);
        int panelHeight = (height - 85) / 2;

        this.cardHeight = (panelHeight / 2) - 20;
        this.cardWidth = (int) (panelWidth - 80) / 7;

        this.setTitle("CyberAttack");
        this.getContentPane().setBackground(this.stdColorCard);
        this.setBounds((screenWidth - width) / 2, (screenHeight - height) / 2, width, height - 25);
        this.setLayout(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        int buttonFieldHeight = 200;
        int buttonFieldWidth = 200;

        this.buttonField = new JPanel();
        this.buttonField.setBackground(new Color(170, 70, 1));
        this.buttonField.setBounds((int)((width - screenWidth / 100 * 0.8) / 7 * 5.5), (height - buttonFieldHeight - 45) / 2, buttonFieldWidth, buttonFieldHeight);
        this.buttonField.setBorder(BorderFactory.createLoweredBevelBorder());
        this.buttonField.setLayout(null);
        this.buttonField.setVisible(true);
        this.add(this.buttonField);

        this.attackButton = new JButton("ATTACCA");
        this.attackButton.setBackground(new Color(42, 45, 52));
        this.attackButton.setForeground(new Color(235, 212, 203));
        this.attackButton.setFont(buttonFont);
        this.attackButton.setBounds(10, 10, buttonFieldWidth - 20, (buttonFieldHeight - 40) / 3);
        this.attackButton.addActionListener(new BoardListener());
        this.attackButton.setFocusPainted(false);
        this.attackButton.setEnabled(false);
        this.buttonField.add(this.attackButton);
        this.attackButton.setVisible(true);

        this.positioningButton = new JButton("SCHIERA");
        this.positioningButton.setBackground(new Color(42, 45, 52));
        this.positioningButton.setForeground(new Color(235, 212, 203));
        this.positioningButton.setFont(this.buttonFont);
        this.positioningButton.setBounds(10, 20 + (buttonFieldHeight - 40) / 3, 180, (buttonFieldHeight - 40) / 3);
        this.positioningButton.addActionListener(new BoardListener());
        this.positioningButton.setFocusPainted(false);
        this.buttonField.add(this.positioningButton);
        this.positioningButton.setVisible(true);

        this.changeTurnButton = new JButton("CAMBIA TURNO");
        this.changeTurnButton.setBackground(new Color(42, 45, 52));
        this.changeTurnButton.setForeground(new Color(235, 212, 203));
        this.changeTurnButton.setFont(this.buttonFont);
        this.changeTurnButton.setBounds(10, 30 + ((buttonFieldHeight - 40) / 3) * 2, buttonFieldWidth - 20, (buttonFieldHeight - 40) / 3);
        this.changeTurnButton.addActionListener(new BoardListener());
        this.changeTurnButton.setFocusPainted(false);
        this.changeTurnButton.setEnabled(false);
        this.buttonField.add(this.changeTurnButton);
        this.changeTurnButton.setVisible(true);

        this.turnField = new JPanel();
        this.turnField.setBackground(new Color(170, 70, 1));
        this.turnField.setBounds(this.buttonField.getX(),this.buttonField.getY() - (height / 16) - 10, this.buttonField.getWidth(), height / 16);
        this.turnField.setBorder(BorderFactory.createLoweredBevelBorder());
        this.turnField.setLayout(null);
        this.turnField.setVisible(true);
        this.add(this.turnField);

        this.panelCards = new JPanel();
        this.panelCards.setBorder(BorderFactory.createLoweredBevelBorder());
        this.panelCards.setBackground(new Color(85, 87, 93));
        this.panelCards.setBounds(20, this.getHeight() / 4 + 6 , cardWidth + 15, (this.getHeight() / 4) * 2 - 40);
        this.panelCards.setVisible(true);
        this.panelCards.setLayout(null);
        this.add(this.panelCards);

        this.attackOrder = new int[3];
        this.attOrderCont = 0;
        this.defenseOrder = new int[3];
        this.defOrderCont = 0;

        this.p1 = new Player(panelWidth, panelHeight, 1, new Color(85, 87, 93), false, this);
        this.p1.setBounds(10, 10, panelWidth, panelHeight);
        this.p1.setLayout(null);
        this.p1.setVisible(true);
        this.add(this.p1);

        this.p2 = new Player(panelWidth, panelHeight, 2, new Color(85, 87, 93), true, this);
        this.p2.setBounds(10, panelHeight + 20, panelWidth, panelHeight);
        this.p2.setLayout(null);
        this.p2.setVisible(true);
        this.add(this.p2);

        this.actualTurnPlayer = p1;
        this.actualWaitPlayer = p2;

        this.turnLabel = new JLabel("Turno: " + this.actualTurn + " / 16 - "
                + "Player " + this.actualTurnPlayer.getId());
        this.turnLabel.setFont(this.buttonFont);
        this.turnLabel.setForeground(new Color(235, 212, 203));
        this.turnLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.turnLabel.setBounds(0, 0,
                this.turnField.getWidth(), this.turnField.getHeight());
        this.turnLabel.setLayout(null);
        this.turnLabel.setVisible(true);
        this.turnField.add(this.turnLabel);


        this.mix();
        printDeck();

        chooseCards(p1, p2);

        this.repaint();
        this.setVisible(true);
    }


    public void updateTurnField() {
        this.turnLabel.setText("Turno: " + this.actualTurn + " / 16 - "
        + "Player " + this.actualTurnPlayer.getId());
    }


    private void chooseCards(Player p1, Player p2) {
        p1.changeTurn();
        p2.changeTurn();
        this.actualTurnPlayer = p1;
        this.actualWaitPlayer = p2;
        updateTurnField();
        p2.waitTurn();
        p1.chooseCards(this.cards);
    }


    private void mix() {
        this.cards = new LinkedList<>();

        for (int i = 0; i < 45; i++) {
            if (i < 1) this.cards.add(new Gigatron(i, this.cardWidth, this.cardHeight, this.legendaryColorCard));
            if(i < 3) this.cards.add(new Nebula(i, this.cardWidth, this.cardHeight, this.legendaryColorCard));
            else if (i < 5) this.cards.add(new Nightmare(i, this.cardWidth, this.cardHeight, this.legendaryColorCard));
            else if (i < 8) this.cards.add(new RoboBruin(i, this.cardWidth, this.cardHeight, this.epicColorCard));
            else if (i < 11) this.cards.add(new Roborat(i, this.cardWidth, this.cardHeight, this.epicColorCard));
            else if (i < 14) this.cards.add(new QuantumGrade(i, this.cardWidth, this.cardHeight, this.epicColorCard));
            else if (i < 18) this.cards.add(new Horde(i, this.cardWidth, this.cardHeight, this.stdColorCard));
            else if (i < 24) this.cards.add(new ElRaton(i, this.cardWidth, this.cardHeight, this.stdColorCard));
            else if (i < 32) this.cards.add(new ByteHowler(i, this.cardWidth, this.cardHeight, this.stdColorCard));
            else if (i < 38) this.cards.add(new TecnoPlatypus(i, this.cardWidth, this.cardHeight, this.epicColorCard));
            else this.cards.add(new Spaceman(i, this.cardWidth, this.cardHeight, this.stdColorCard));
        }

        Collections.shuffle(this.cards);

        LinkedList<Card> deckP1 = new LinkedList<>();
        LinkedList<Card> deckP2 = new LinkedList<>();

        for(int i = 0; i < 14; i++) {
            if (i % 2 == 0) deckP1.add(this.cards.removeFirst());
            else deckP2.add(this.cards.removeFirst());
        }

        this.p1.giveCards(deckP1);
        this.p2.giveCards(deckP2);

        printDeck();

        repaint();
    }

    public void printDeck() {
        int i = 0;
        for (Card c : this.cards) {
            c.setBounds((int) ((this.panelCards.getWidth() - this.cardWidth) / 1.75), (this.panelCards.getHeight() - this.cardHeight + 180) / 2 - i, c.getCardWidth(), c.getCardHeight());
            c.setLayout(null);
            c.setVisible(true);
            c.coverCard();
            this.panelCards.add(c);
            i += 6;
        }
    }


    public boolean isPhasePositioning() {
        return this.phasePositioning;
    }


    public void setPhasePositioning(boolean phasePositioning) {
        this.phasePositioning = phasePositioning;
    }


    public int getActualTurn() {
        return this.actualTurn;
    }


    public void setActualTurn(int actualTurn) {
        this.actualTurn = actualTurn;
    }


    public void placeAttack(Card card, Player player) {
        if(this.attOrderCont < 3) {
            int i = 0;
            for (Card c : player.getSelectedCards()) {
                c.setEnabled(false);
                if (c.equals(card)) {
                    c.setEnabled(false);
                    c.changeBkg(this.attOrderCont);
                    this.attackOrder[this.attOrderCont] = i;
                    this.attOrderCont++;
                }
                i++;
            }
            this.actualWaitPlayer.enableField(this.defenseOrder, this.defOrderCont);
        }
    }


    public void placeDefense(Card card, Player player) {
        if(this.defOrderCont < 3) {
            int i = 0;
            for (Card c : player.getSelectedCards()) {
                c.setEnabled(false);
                if (c.equals(card)) {
                    c.changeBkg(this.defOrderCont);
                    this.defenseOrder[this.defOrderCont] = i;
                    this.defOrderCont++;
                }
                i++;
            }
            this.actualTurnPlayer.enableField();
        }
        if (this.defOrderCont == 3) {
            this.actualTurnPlayer.disableField();
            this.attackButton.setEnabled(true);
        }
    }


    private void attack() {
        for (int i = 0; i < this.attackOrder.length; i++) {
            int j = 0;
            Card attacker;
            Card victim;
            int damage = 0;
            for (Card c : this.actualTurnPlayer.getSelectedCards()) {
                if (this.attackOrder[i] == j) {
                    attacker = c;
                    damage = attacker.getAttack();
                    break;
                }
                j++;
            }
            j = 0;
            for (Card c : this.actualWaitPlayer.getSelectedCards()) {
                if (this.defenseOrder[i] == j) {
                    victim = c;
                    victim.setDefense(victim.getDefense() - damage);
                    break;
                }
                j++;
            }
        }
        this.actualWaitPlayer.updateCards(this.actualTurnPlayer);
        this.actualTurnPlayer.updateCards(this.actualWaitPlayer);
        this.actualTurnPlayer.waitTurn();
        this.actualTurnPlayer.updateView();
        this.actualWaitPlayer.updateView();

        this.repaint();

        this.attOrderCont = 0;
        this.defOrderCont = 0;
        for (int i = 0; i < this.attackOrder.length; i++) {
            this.attackOrder[i] = -1;
            this.defenseOrder[i] = -1;
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

                if(changeTurnButton.getText().equals("TERMINA"))
                    System.exit(1);
            }

            if (e.getSource() == positioningButton) {
                if (actualTurnPlayer.checkSelectedCards()) {
                    if (actualTurn > 2) {
                        phasePositioning = !phasePositioning;
                        actualTurnPlayer.disableDeck();
                        actualTurnPlayer.waitTurn();
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
                if (actualTurn == 16) {
                    attack();
                    attackButton.setEnabled(false);
                    changeTurnButton.setText("TERMINA");
                    changeTurnButton.setEnabled(true);
                    if (p1.getPoints() > p2.getPoints())
                        JOptionPane.showMessageDialog(null, "Ha vinto il Player " + p1.getId());
                    else if (p1.getPoints() < p2.getPoints())
                        JOptionPane.showMessageDialog(null, "Ha vinto il Player " + p2.getId());
                    else JOptionPane.showMessageDialog(null, "La partita è finita in pareggio");


                } else {
                    attack();
                    attackButton.setEnabled(false);
                    changeTurnButton.setEnabled(true);
                }
            }
        }
    }
}
