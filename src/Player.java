import java.awt.*;
import java.util.LinkedList;
import javax.swing.*;

public class Player extends JPanel {
    private int points;
    private JPanel deckPanel;
    private JPanel field;
    private JPanel fieldName;
    private JLabel pName;
    private JPanel fieldScore;
    private JLabel pScore;
    private LinkedList<Card> deck;
    private LinkedList<Card> selectedCards;
    private int deckWidth;
    private int id;
    private int fieldWidth;
    private int fieldHeight;
    private int cardWidth;
    private int cardHeight;
    private boolean turn;
    private Board board;
    private Font font = new Font("Helvetica", Font.BOLD, 14);


    public Player(int panelWidth, int panelHeight, int id, Color color, boolean turn, Board board) {
        this.points = 0;
        this.deckWidth = panelWidth - 20;
        this.turn = turn;
        this.board = board;
        this.id = id;
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

        deckPanel = new JPanel();
        deckPanel.setBounds(10, 10 + fieldHeight * (id - 1), deckWidth, fieldHeight);
        deckPanel.setBorder(BorderFactory.createLoweredBevelBorder());
        deckPanel.setBackground(color);
        deckPanel.setLayout(null);
        deckPanel.setVisible(true);
        this.add(deckPanel);

        field = new JPanel();
        field.setBounds((panelWidth - fieldWidth) / 2, (10 * 2) + (fieldHeight) - (fieldHeight + 20) * (id - 1),
                fieldWidth, fieldHeight);
        field.setBorder(BorderFactory.createLoweredBevelBorder());
        field.setBackground(color);
        field.setLayout(null);
        field.setVisible(true);
        this.add(field);

        fieldName = new JPanel();
        if(id == 2) {
            fieldName.setBounds((panelWidth - fieldWidth) / 2 - fieldWidth / 5, (int) field.getY() + fieldHeight - (fieldHeight / 6) * 2,
                    fieldWidth / 5, fieldHeight / 6);
        } else {
            fieldName.setBounds((panelWidth - fieldWidth) / 2 - fieldWidth / 5, (int) ((10 * 2) + (fieldHeight) - (fieldHeight + 20) * (id - 1)),
                    fieldWidth / 5, fieldHeight / 6);
        }

        fieldName.setBackground(new Color(170, 70, 1));
        fieldName.setBorder(BorderFactory.createLoweredBevelBorder());
        fieldName.setLayout(null);
        fieldName.setVisible(true);
        this.add(fieldName);

        pName = new JLabel("PLAYER " + id);
        pName.setFont(font);
        pName.setForeground(new Color(235, 212, 203));
        pName.setBounds(10, (fieldName.getHeight() - 40) / 2, 150, 40);
        pName.setLayout(null);
        pName.setVisible(true);
        fieldName.add(pName);

        fieldScore = new JPanel();
        fieldScore.setBounds(fieldName.getX(), fieldName.getY() + fieldName.getHeight(),
                fieldName.getWidth(), fieldName.getHeight());
        fieldScore.setBackground(new Color(170, 70, 1));
        fieldScore.setBorder(BorderFactory.createLoweredBevelBorder());
        fieldScore.setLayout(null);
        fieldScore.setVisible(true);
        this.add(fieldScore);

        pScore = new JLabel("" + points);
        pScore.setFont(font);
        pScore.setForeground(new Color(235, 212, 203));
        pScore.setBounds(10, (fieldScore.getHeight() - 40) / 2, 150, 40);
        pScore.setLayout(null);
        pScore.setVisible(true);
        fieldScore.add(pScore);

        cardHeight = fieldHeight - 20;
        cardWidth = (int) (deckWidth - 80) / 7;

        validate();
        repaint();
        this.setVisible(true);
    }


    public void updateScore() {
        pScore.setText("" + points);
    }


    public void updateCards(Player player) {
        for(Card c : selectedCards) {
            if(c != null)
                c.updateLabels();
        }

        int i = 0;
        for(Card c : getSelectedCards()) {
            if (c != null)
                if (c.getDefense() <= 0) {
                    this.field.remove((Component) c);
                    player.setPoints(player.getPoints() + 1);
                    getSelectedCards().set(i, null);
                }
            i++;
        }
    }


    public void updateView() {
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
        updateScore();

        field.repaint();
    }


    public void giveCards(LinkedList<Card> deckP) {
        for (Card c : deckP) {
            this.deck.addFirst(c);
            c.setPlayer(this);
        }
        updateView();
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
        if (turn) {
            if (this.board.isPhasePositioning()) {
                if (deck.contains(card)) {
                    if (numberOfElements(selectedCards) == 3) {
                        disableDeck();
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
                    enableDeck();
                }
            } else {
                this.board.placeAttack(card, this);
            }
            updateView();
        } else {
            this.board.placeDefense(card, this);
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


    public boolean checkSelectedCards() {
        if (numberOfElements(selectedCards) < 3) {
            JOptionPane.showMessageDialog(null, "Devi selezionare 3 carte!");
            return false;
        }
        return true;
    }


    public void enableDeck() {
        for (Card c : deck)
            if(c != null)
                c.setEnabled(true);
    }


    public void enableField(int[] vet, int v) {
        enableField();
        for (int i = 0; i < v; i++) {
            int j = 0;
            for (Card c : selectedCards) {
                if (vet[i] == j)
                    c.setEnabled(false);
                j++;
            }
        }
    }


    public void disableDeck() {
        for (Card c : deck)
            if(c != null)
                c.setEnabled(false);
    }


    public void enableField() {
        for (Card c : selectedCards)
            if(c != null)
                c.setEnabled(true);
    }


    public void disableField() {
        for (Card c : selectedCards)
            if(c != null)
                c.setEnabled(false);
    }


    public JPanel getDeckPanel() {
        return deckPanel;
    }


    public void setDeckPanel(JPanel deckPanel) {
        this.deckPanel = deckPanel;
    }


    public JPanel getField() {
        return field;
    }


    public void setField(JPanel field) {
        this.field = field;
    }


    public JPanel getFieldName() {
        return fieldName;
    }


    public void setFieldName(JPanel fieldName) {
        this.fieldName = fieldName;
    }


    public LinkedList<Card> getDeck() {
        return deck;
    }


    public void setDeck(LinkedList<Card> deck) {
        this.deck = deck;
    }


    public LinkedList<Card> getSelectedCards() {
        return selectedCards;
    }


    public void setSelectedCards(LinkedList<Card> selectedCards) {
        this.selectedCards = selectedCards;
    }


    public int getDeckWidth() {
        return deckWidth;
    }


    public void setDeckWidth(int deckWidth) {
        this.deckWidth = deckWidth;
    }


    public int getFieldWidth() {
        return fieldWidth;
    }


    public void setFieldWidth(int fieldWidth) {
        this.fieldWidth = fieldWidth;
    }


    public int getFieldHeight() {
        return fieldHeight;
    }


    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public void setFieldHeight(int fieldHeight) {
        this.fieldHeight = fieldHeight;
    }


    public int getCardWidth() {
        return cardWidth;
    }


    public void setCardWidth(int cardWidth) {
        this.cardWidth = cardWidth;
    }


    public int getCardHeight() {
        return cardHeight;
    }


    public void setCardHeight(int cardHeight) {
        this.cardHeight = cardHeight;
    }


    public boolean isTurn() {
        return turn;
    }


    public void setTurn(boolean turn) {
        this.turn = turn;
    }


    public Board getBoard() {
        return board;
    }


    public void setBoard(Board board) {
        this.board = board;
    }


    public int getPoints() {
        return points;
    }


    public void setPoints(int points) {
        this.points = points;
    }

}
