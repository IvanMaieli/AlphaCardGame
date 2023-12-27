import java.awt.*;
import java.util.LinkedList;
import javax.swing.*;

public class Player extends JPanel{

    private JPanel deckPanel;
    private JPanel field;
    private LinkedList<Card> deck;
    private LinkedList<Card> selectedCards;
    private int panelWidth;
    private int panelHeight;
    private  int deckWidth;
    private int fieldWidth;
    private int fieldHeight;
    private int cardWidth;
    private int cardHeight;
    private boolean turn;
    private Board board;
    
    public Player(int panelWidth, int panelHeight, int p, Color color, boolean turn, Board board){
        this.panelWidth = panelWidth;
        this.panelHeight = panelHeight;
        this.deckWidth = panelWidth - 20;
        this.turn = turn;
        this.board = board;
        this.setSize(panelWidth, panelHeight);
        this.setLayout(null);

        selectedCards = new LinkedList<>();
        selectedCards.add(null);
        selectedCards.add(null);
        selectedCards.add(null);
        deck = new LinkedList<>();

        fieldWidth = panelWidth / 7 * 3;
        fieldHeight = (panelHeight - 20) / 2; 

        deckPanel = new JPanel();
        deckPanel.setBounds(10, 10 + fieldHeight * (p - 1), deckWidth, fieldHeight);
        deckPanel.setBorder(BorderFactory.createLineBorder(color, 3));
        deckPanel.setLayout(null);
        deckPanel.setVisible(true);
        this.add(deckPanel);

        field = new JPanel();
        field.setBounds((panelWidth - fieldWidth) / 2, (10 * 2) + (fieldHeight) - (fieldHeight + 20) * (p - 1), fieldWidth, fieldHeight);
        field.setBorder(BorderFactory.createLineBorder(color, 3));
        field.setLayout(null);
        field.setVisible(true);
        this.add(field);

        cardHeight = fieldHeight - 20;
        cardWidth = (int) (deckWidth - 80) / 7;


        validate();
        repaint();
        this.setVisible(true);
    }

    public void giveCards(LinkedList<Card> deckP) {
        for(Card c : deckP) {
            this.deck.addFirst(c);
            c.setPlayer(this);
            c.setEnabled(turn);
        }
        System.out.println(deck.toString());
        System.out.println(selectedCards.toString());
        updateDeckView();
    }

    private void updateDeckView() {
        int i = 0;
        for(Card c : deck) {
            if(c != null) {
                c.setBounds((10 * (i + 1)) + (cardWidth * i), 10, cardWidth, cardHeight);
                c.setLayout(null);
                c.setVisible(true);
                if (!turn) c.coverCard();
                deckPanel.add(c);

            }
            i++;
        }
        deckPanel.repaint();
    }

    private void updateFieldView() {
        int i = 0;
        for(Card c : selectedCards) {
            if(c != null) {
                c.setBounds((10 * (i + 1)) + (cardWidth * i), 10, cardWidth, cardHeight);
                c.setLayout(null);
                c.setVisible(true);
                if (!turn) c.coverCard();
                field.add(c);

            }
            i++;
        }
        field.repaint();
    }

    public void waitTurn() {
        for(Card c : deck) {
            if(c != null) c.setEnabled(false);
        }
        for(Card c : selectedCards) {
            if(c != null) c.setEnabled(false);
        }
    }

    public void chooseCards(LinkedList<Card> cards) {
        LinkedList<Card> tempCards = new LinkedList<>();
        int i = 0;
        int j = 0;
        for(Card c : selectedCards) {
            if(!(c == null)) tempCards.add(selectedCards.remove(i));
            i++;
        }

        i = 0;
        for(Card c : deck) {
            if(c == null) {
                if(i < tempCards.size()){
                    deck.add(j, tempCards.removeFirst());
                    i++;
                }
                else {
                    deck.add(j, cards.removeFirst());
                    deck.get(j).setPlayer(this);
                }
            }
            j++;
        }
        for(Card c : deck) {
            c.setEnabled(true);
            c.showCard();
        }
    }

    public void cardClicked(Card card) {
        if(this.board.isPhasePositioning()) {
            if(deck.contains(card)) {
                if(numberOfElements(selectedCards) == 3) {
                    JOptionPane.showMessageDialog(null, "Non puoi selezionare altre carte!");
                } else {
                    int i = 0;
                    for(Card c : deck) {
                        if(c != null) {
                            if(c.getId() == card.getId()) {
                                int j = 0;
                                for(Card z : selectedCards) {
                                    if(z == null){
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
                for(Card c : selectedCards) {
                    boolean exit = false;
                    if(c != null) {
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
                                break;
                            }
                        }
                    }
                    i++;
                }
                field.remove((Component) card);
                selectedCards.set(i, null);
            }
        }
        System.out.println(deck.toString());
        System.out.println(selectedCards.toString());
        updateDeckView();
        updateFieldView();
    }

    private int numberOfElements(LinkedList<Card> cards) {
        int i = 0;
        for(Card c : cards)
            if(c != null)
                i++;
        return i;
    }
}
