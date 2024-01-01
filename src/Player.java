import java.awt.*;
import java.util.LinkedList;
import javax.swing.*;
import javax.swing.plaf.basic.BasicLabelUI;

public class Player extends JPanel {

    private JPanel deckPanel;
    private JPanel field;
    private JPanel fieldName;
    public LinkedList<Card> deck;
    private LinkedList<Card> selectedCards;
    private int deckWidth;
    private int fieldWidth;
    private int fieldHeight;
    private int cardWidth;
    private int cardHeight;
    private boolean turn;
    private Board board;
    private Color colorAttack1 = Color.BLUE;
    private Color colorAttack2 = Color.GREEN;
    private Color getColorAttack3 = Color.PINK;

    public Player(int panelWidth, int panelHeight, int p, Color color, boolean turn, Board board) {
        this.deckWidth = panelWidth - 20;
        this.turn = turn;
        this.board = board;
        this.setSize(panelWidth, panelHeight);
        this.setBackground(new Color(42, 45, 52));
        this.setLayout(null);

        selectedCards = new LinkedList<>();
        selectedCards.add(null);
        selectedCards.add(null);
        selectedCards.add(null);
        deck = new LinkedList<>();

        fieldWidth = panelWidth / 7 * 3;
        fieldHeight = (panelHeight - 20) / 2;

        Font font = new Font("Monospaced", Font.BOLD, 16);

        deckPanel = new JPanel();
        deckPanel.setBounds(10, 10 + fieldHeight * (p - 1), deckWidth, fieldHeight);
        deckPanel.setBorder(BorderFactory.createLoweredBevelBorder());
        deckPanel.setBackground(color);
        deckPanel.setLayout(null);
        deckPanel.setVisible(true);
        this.add(deckPanel);

        field = new JPanel();
        field.setBounds((panelWidth - fieldWidth) / 2, (10 * 2) + (fieldHeight) - (fieldHeight + 20) * (p - 1),
                fieldWidth, fieldHeight);
        field.setBorder(BorderFactory.createLoweredBevelBorder());
        field.setBackground(color);
        field.setLayout(null);
        field.setVisible(true);
        this.add(field);

        fieldName = new JPanel();
        fieldName.setBounds((panelWidth - fieldWidth) / 2 - fieldWidth / 6, (int) ((10 * 2) + (fieldHeight) - (fieldHeight + 20) * (p - 1)),
                fieldWidth / 6, fieldHeight / 6);
        fieldName.setBackground(new Color(170, 70, 1));
        fieldName.setBorder(BorderFactory.createLoweredBevelBorder());
        fieldName.setLayout(null);
        fieldName.setVisible(true);
        this.add(fieldName);

        JLabel pName = new JLabel("PLAYER " + p);
        pName.setFont(font);
        pName.setForeground(new Color(235, 212, 203));
        pName.setBounds(10, (fieldName.getHeight() - 40) / 2, 150, 40);
        pName.setLayout(null);
        pName.setVisible(true);
        fieldName.add(pName);

        cardHeight = fieldHeight - 20;
        cardWidth = (int) (deckWidth - 80) / 7;

        validate();
        repaint();
        this.setVisible(true);
    }

    public void giveCards(LinkedList<Card> deckP) {
        for (Card c : deckP) {
            this.deck.addFirst(c);
            c.setPlayer(this);
        }
        updateView();
    }

    private void updateView() {
        int i = 0;
        for (Card c : deck) {
            if (c != null) {
                c.setBounds((10 * (i + 1)) + (cardWidth * i), 10, cardWidth, cardHeight);
                c.setLayout(null);
                c.setVisible(true);
                deckPanel.add(c);

            }
            i++;
        }
        deckPanel.repaint();
        i = 0;
        for (Card c : selectedCards) {
            if (c != null) {
                c.setBounds((10 * (i + 1)) + (cardWidth * i), 10, cardWidth, cardHeight);
                c.setLayout(null);
                c.setVisible(true);
                field.add(c);
            }
            i++;
        }
        field.repaint();
    }

    public void chooseCards(LinkedList<Card> cards) {
        int i = 0;
        int j = 0;
        for (Card c : selectedCards) {
            if (c != null) {
                j = 0;
                for (Card c2 : deck) {
                    if (c2 == null) {
                        deck.set(j, c);
                        field.remove((Component) selectedCards.get(i));
                        selectedCards.set(i, null);
                        break;
                    }
                    j++;
                }
            }
            i++;
        }

        i = 0;
        for (Card c : deck) {
            if (c == null) {
                deck.set(i, cards.removeFirst());
                deck.get(i).setPlayer(this);
            }
            i++;
        }
        for (Card c : deck) {
            c.showCard();
        }
        updateView();
    }

    public void cardClicked(Card card) {
        // se è il proprio turno
        if (turn) {
            // se la partita è in fase di posizionamento
            if (this.board.isPhasePositioning()) {
                // se il mazzo contiene la carta
                if (deck.contains(card)) {
                    // se non abbiamo selezionato già 3 carte
                    if (numberOfElements(selectedCards) == 3) {
                        JOptionPane.showMessageDialog(null, "Non puoi selezionare altre carte!");
                    } else {
                        int i = 0;
                        for (Card c : deck) {
                            if (c != null) {
                                if (c.getId() == card.getId()) {
                                    int j = 0;
                                    for (Card z : selectedCards) {
                                        if (z == null) {
                                            selectedCards.set(j, deck.get(i));
                                            break;
                                        }
                                        j++;
                                    }
                                    break;
                                }
                            }
                            i++;
                        }
                        deckPanel.remove((Component) card);
                        deck.set(i, null);
                    }
                } else {
                    int i = 0;
                    for (Card c : selectedCards) {
                        boolean exit = false;
                        if (c != null) {
                            if (c.getId() == card.getId()) {
                                int j = 0;
                                for (Card s : deck) {
                                    if (s == null) {
                                        exit = true;
                                        break;
                                    }
                                    j++;
                                }
                                if (exit) {
                                    deck.set(j, c);
                                    field.remove((Component) card);
                                    selectedCards.set(i, null);
                                    break;
                                }
                            }
                        }
                        i++;
                    }
                }
            } else {

            }
            updateView();

        }
    }

    public void changeTurn() {
        this.turn = !turn;
    }

    private int numberOfElements(LinkedList<Card> cards) {
        int i = 0;
        for (Card c : cards)
            if (c != null)
                i++;
        return i;
    }

    public void waitTurn() {
        for (Card c : deck)
            if (c != null)
                c.coverCard();
        updateView();
    }

    public boolean isTurn() {
        return turn;
    }

    public boolean checkSelectedCards() {
        if (numberOfElements(selectedCards) < 3) {
            JOptionPane.showMessageDialog(null, "Devi selezionare 3 carte!");
            return false;
        }
        return true;
    }

}
