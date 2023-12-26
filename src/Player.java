import java.awt.Color;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class Player extends JPanel{

    private JPanel deckPanel;
    private JPanel field;
    private LinkedList<Card> deck;
    private JPanel[] cardPanels;
    
    private int panelWidth;
    private int panelHeight;
    private int fieldWidth;
    private int fieldHeight;
    private int cardWidth;
    private int cardHeight;
    
    public Player(int panelWidth, int panelHeight){
        this.panelWidth = panelWidth;
        this.panelHeight = panelHeight;
        deck = new LinkedList<>();

        fieldWidth = panelWidth / 7 * 3;
        fieldHeight = (panelHeight - 20) / 2; 

        deckPanel = new JPanel();
        deckPanel.setBounds(10, 10, panelWidth, fieldHeight);
        deckPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        deckPanel.setLayout(null);
        deckPanel.setVisible(true);
        add(deckPanel);

        field = new JPanel();
        field.setBounds((panelWidth - fieldWidth) / 2, (10 * 2) + (panelHeight), fieldWidth, fieldHeight);
        field.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        field.setLayout(null);
        field.setVisible(true);
        add(field);

        cardHeight = panelHeight - 20;
        cardWidth = (int) (panelWidth - 80) / 7;

        
        


    }

    public void giveCards(LinkedList<Card> deckP1) {
        for (Card c: deckP1) {
            this.deck.addFirst(c);
        }
        
        cardPanels = new JPanel[7];
        for(int i = 0; i < 7; i++) {
            cardPanels[i] = new JPanel();
            cardPanels[i].setBounds((10 * (i + 1)) + (cardWidth * i), 10, cardWidth, cardHeight);
            cardPanels[i].setLayout(null);
            cardPanels[i].setVisible(true);
            deckPanel.add(cardPanels[i]);
        }
    }
}
