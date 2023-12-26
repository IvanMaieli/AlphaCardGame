import java.awt.*;
import java.util.LinkedList;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

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
    
    public Player(int panelWidth, int panelHeight, int p, Color color, boolean turn){
        this.panelWidth = panelWidth;
        this.panelHeight = panelHeight;
        this.deckWidth = panelWidth - 20;
        this.turn = turn;
        this.setSize(panelWidth, panelHeight);
        this.setLayout(null);


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

    public void giveCards(LinkedList<Card> deckP1) {
        for(Card c : deckP1) {
            this.deck.addFirst(c);
        }
        int i = 0;
        for(Card c : deck) {
            c.setBounds((10 * (i + 1)) + (cardWidth * i), 10, cardWidth, cardHeight);
            c.setLayout(null);
            c.setVisible(true);
            //if(!turn) c.coverCard();
            deckPanel.add(c);
            deckPanel.repaint();
            i++;
        }
        validate();
        repaint();
    }
}
