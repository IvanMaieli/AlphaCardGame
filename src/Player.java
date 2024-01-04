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

        this.selectedCards = new LinkedList<>();
        this.selectedCards.add(null);
        this.selectedCards.add(null);
        this.selectedCards.add(null);

        this.deck = new LinkedList<>();

        this.fieldWidth = panelWidth / 7 * 3;
        this.fieldHeight = (panelHeight - 20) / 2;

        this.deckPanel = new JPanel();
        this.deckPanel.setBounds(10, 10 + this.fieldHeight * (id - 1), this.deckWidth, this.fieldHeight);
        this.deckPanel.setBorder(BorderFactory.createLoweredBevelBorder());
        this.deckPanel.setBackground(color);
        this.deckPanel.setLayout(null);
        this.deckPanel.setVisible(true);
        this.add(this.deckPanel);

        this.field = new JPanel();
        this.field.setBounds((panelWidth - this.fieldWidth) / 2,
                (10 * 2) + (this.fieldHeight) - (this.fieldHeight + 20) * (id - 1),
                this.fieldWidth, this.fieldHeight);
        this.field.setBorder(BorderFactory.createLoweredBevelBorder());
        this.field.setBackground(color);
        this.field.setLayout(null);
        this.field.setVisible(true);
        this.add(this.field);

        this.fieldName = new JPanel();
        if(id == 2) {
            this.fieldName.setBounds((panelWidth - this.fieldWidth) / 2 - this.fieldWidth / 5,
                    this.field.getY() + this.fieldHeight - (this.fieldHeight / 6) * 2,
                    this.fieldWidth / 5, this.fieldHeight / 6);
        } else {
            fieldName.setBounds((panelWidth - this.fieldWidth) / 2 - this.fieldWidth / 5,
                    ((10 * 2) + (this.fieldHeight) - (this.fieldHeight + 20) * (id - 1)),
                    this.fieldWidth / 5, this.fieldHeight / 6);
        }

        this.fieldName.setBackground(new Color(170, 70, 1));
        this.fieldName.setBorder(BorderFactory.createLoweredBevelBorder());
        this.fieldName.setLayout(null);
        this.fieldName.setVisible(true);
        this.add(this.fieldName);

        this.pName = new JLabel("PLAYER " + id);
        this.pName.setFont(this.font);
        this.pName.setForeground(new Color(235, 212, 203));
        this.pName.setBounds(10, (this.fieldName.getHeight() - 40) / 2, 150, 40);
        this.pName.setLayout(null);
        this.pName.setVisible(true);
        this.fieldName.add(this.pName);

        this.fieldScore = new JPanel();
        this.fieldScore.setBounds(this.fieldName.getX(), this.fieldName.getY() + this.fieldName.getHeight(),
                this.fieldName.getWidth(), this.fieldName.getHeight());
        this.fieldScore.setBackground(new Color(170, 70, 1));
        this.fieldScore.setBorder(BorderFactory.createLoweredBevelBorder());
        this.fieldScore.setLayout(null);
        this.fieldScore.setVisible(true);
        this.add(this.fieldScore);

        this.pScore = new JLabel("" + this.points);
        this.pScore.setFont(this.font);
        this.pScore.setForeground(new Color(235, 212, 203));
        this.pScore.setBounds(10, (this.fieldScore.getHeight() - 40) / 2, 150, 40);
        this.pScore.setLayout(null);
        this.pScore.setVisible(true);
        this.fieldScore.add(this.pScore);

        this.cardHeight = this.fieldHeight - 20;
        this.cardWidth = (int) (this.deckWidth - 80) / 7;

        this.validate();
        this.repaint();
        this.setVisible(true);
    }


    public void updateScore() {
        this.pScore.setText("" + this.points);
    }


    public void updateCards(Player player) {
        for(Card c : this.selectedCards) {
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
        for (Card c : this.deck) {
            if (c != null) {
                c.setBounds((10 * (i + 1)) + (this.cardWidth * i), 10, this.cardWidth, this.cardHeight);
                c.setLayout(null);
                c.setVisible(true);
                this.deckPanel.add(c);
            }
            i++;
        }
        this.deckPanel.repaint();
        i = 0;
        for (Card c : this.selectedCards) {
            if (c != null) {
                c.setBounds((10 * (i + 1)) + (this.cardWidth * i), 10, this.cardWidth, this.cardHeight);
                c.setLayout(null);
                c.setVisible(true);
                this.field.add(c);
            }
            i++;
        }
        this.updateScore();

        this.field.repaint();
    }


    public void giveCards(LinkedList<Card> deckP) {
        for (Card c : deckP) {
            this.deck.addFirst(c);
            c.setPlayer(this);
        }
        this.updateView();
    }


    public void chooseCards(LinkedList<Card> cards) {
        int i = 0;
        int j = 0;
        for (Card c : this.selectedCards) {
            if (c != null) {
                j = 0;
                for (Card c2 : this.deck) {
                    if (c2 == null) {
                        this.deck.set(j, c);
                        this.field.remove((Component) this.selectedCards.get(i));
                        this.selectedCards.set(i, null);
                        break;
                    }
                    j++;
                }
            }
            i++;
        }

        i = 0;
        for (Card c : this.deck) {
            if (c == null) {
                this.deck.set(i, cards.removeFirst());
                this.deck.get(i).setPlayer(this);
            }
            i++;
        }
        for (Card c : this.deck) {
            c.showCard();
        }
        this.updateView();
    }


    public void cardClicked(Card card) {
        if (this.turn) {
            if (this.board.isPhasePositioning()) {
                if (this.deck.contains(card)) {
                    if (numberOfElements(this.selectedCards) == 3) {
                        disableDeck();
                    } else {
                        int i = 0;
                        for (Card c : this.deck) {
                            if (c != null) {
                                if (c.getId() == card.getId()) {
                                    int j = 0;
                                    for (Card z : this.selectedCards) {
                                        if (z == null) {
                                            this.selectedCards.set(j, this.deck.get(i));
                                            break;
                                        }
                                        j++;
                                    }
                                    break;
                                }
                            }
                            i++;
                        }
                        this.deckPanel.remove((Component) card);
                        this.deck.set(i, null);
                    }
                } else {
                    int i = 0;
                    for (Card c : this.selectedCards) {
                        boolean exit = false;
                        if (c != null) {
                            if (c.getId() == card.getId()) {
                                int j = 0;
                                for (Card s : this.deck) {
                                    if (s == null) {
                                        exit = true;
                                        break;
                                    }
                                    j++;
                                }
                                if (exit) {
                                    this.deck.set(j, c);
                                    this.field.remove((Component) card);
                                    this.selectedCards.set(i, null);
                                    break;
                                }
                            }
                        }
                        i++;
                    }
                    this.enableDeck();
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
        this.turn = !this.turn;
    }


    private int numberOfElements(LinkedList<Card> cards) {
        int i = 0;
        for (Card c : cards)
            if (c != null)
                i++;
        return i;
    }


    public void waitTurn() {
        for (Card c : this.deck)
            if (c != null)
                c.coverCard();
        updateView();
    }


    public boolean checkSelectedCards() {
        if (numberOfElements(this.selectedCards) < 3) {
            JOptionPane.showMessageDialog(null, "Devi selezionare 3 carte!");
            return false;
        }
        return true;
    }


    public void enableDeck() {
        for (Card c : this.deck)
            if(c != null)
                c.setEnabled(true);
    }


    public void enableField(int[] vet, int v) {
        enableField();
        for (int i = 0; i < v; i++) {
            int j = 0;
            for (Card c : this.selectedCards) {
                if (vet[i] == j)
                    c.setEnabled(false);
                j++;
            }
        }
    }


    public void disableDeck() {
        for (Card c : this.deck)
            if(c != null)
                c.setEnabled(false);
    }


    public void enableField() {
        for (Card c : this.selectedCards)
            if(c != null)
                c.setEnabled(true);
    }


    public void disableField() {
        for (Card c : this.selectedCards)
            if(c != null)
                c.setEnabled(false);
    }


    public JPanel getDeckPanel() {
        return this.deckPanel;
    }


    public void setDeckPanel(JPanel deckPanel) {
        this.deckPanel = deckPanel;
    }


    public JPanel getField() {
        return this.field;
    }


    public void setField(JPanel field) {
        this.field = field;
    }


    public JPanel getFieldName() {
        return this.fieldName;
    }


    public void setFieldName(JPanel fieldName) {
        this.fieldName = fieldName;
    }


    public LinkedList<Card> getDeck() {
        return this.deck;
    }


    public void setDeck(LinkedList<Card> deck) {
        this.deck = deck;
    }


    public LinkedList<Card> getSelectedCards() {
        return this.selectedCards;
    }


    public void setSelectedCards(LinkedList<Card> selectedCards) {
        this.selectedCards = selectedCards;
    }


    public int getDeckWidth() {
        return this.deckWidth;
    }


    public void setDeckWidth(int deckWidth) {
        this.deckWidth = deckWidth;
    }


    public int getFieldWidth() {
        return this.fieldWidth;
    }


    public void setFieldWidth(int fieldWidth) {
        this.fieldWidth = fieldWidth;
    }


    public int getFieldHeight() {
        return this.fieldHeight;
    }


    public int getId() {
        return this.id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public void setFieldHeight(int fieldHeight) {
        this.fieldHeight = fieldHeight;
    }


    public int getCardWidth() {
        return this.cardWidth;
    }


    public void setCardWidth(int cardWidth) {
        this.cardWidth = cardWidth;
    }


    public int getCardHeight() {
        return this.cardHeight;
    }


    public void setCardHeight(int cardHeight) {
        this.cardHeight = cardHeight;
    }


    public boolean isTurn() {
        return this.turn;
    }


    public void setTurn(boolean turn) {
        this.turn = turn;
    }


    public Board getBoard() {
        return this.board;
    }


    public void setBoard(Board board) {
        this.board = board;
    }


    public int getPoints() {
        return this.points;
    }


    public void setPoints(int points) {
        this.points = points;
    }

}
