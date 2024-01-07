import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public abstract class Card extends JButton {

    //identificatore univoco di una carta
    private int id;

    //il giocatore che possiede la carta
    protected Player player = null;

    //i colori che possono assumere le carte quando vengono selezionati nella fase di attacco
    private Color[] colors;

    //nome della carta
    private String name;

    //danno che infliggono alle altre carte
    private int attack;

    //vita della carta
    private int defense;

    //label che contiene l'immagine
    private JLabel img;

    //immagine della carta
    private Image frontImg;

    //retro della carta
    private Image backImg;

    //colore standard (creato per comodità)
    private final Color stdColorCard = new Color(42, 45, 52);

    //colore della carta
    private Color color;

    //pannello con le statistiche della carta
    private JPanel panelSpecs;

    //label che contiene le statistiche della carta
    private JLabel specsLabel;

    //pannello con il nome della carta
    private JPanel panelName;

    //label che contiene il nome della carta
    private JLabel nameLabel;

    //larghezza della carta
    private int cardWidth;

    //altezza della carta
    private int cardHeight;

    //font della classe
    private Font fontCard = new Font("Helvetica", Font.BOLD, 14);


    public Card(int id, String name, int attack, int defense,
                String imgPath, Color color, int cardWidth, int cardHeight) {
        this.id = id;
        this.name = name;
        this.attack = attack;
        this.defense = defense;
        this.backImg = new ImageIcon("raw_images/cards/back_card.jpg").getImage().getScaledInstance(cardWidth, cardHeight, Image.SCALE_SMOOTH);
        this.color = color;
        this.cardWidth = cardWidth;
        this.cardHeight = cardHeight;

        this.colors = new Color[3];
        this.colors[0] = new Color(111, 29, 27);
        this.colors[1] = new Color(36, 130, 50);
        this.colors[2] = new Color(0, 117, 162);

        //proprietà della classe
        this.setSize(cardWidth, cardHeight);
        this.setBorder(BorderFactory.createRaisedBevelBorder());
        this.setLayout(null);
        this.setBorderPainted(false);
        this.setBackground(color);
        this.addActionListener(new CardActionListener());
        this.setVisible(true);

        //proprietà del pannello del nome della carta
        this.panelName = new JPanel();
        this.panelName.setBounds(0,0, this.cardWidth, this.cardHeight / 6);
        this.panelName.setBackground(this.stdColorCard);
        this.panelName.setBorder(BorderFactory.createRaisedBevelBorder());
        this.panelName.setFont(this.fontCard);
        this.panelName.setLayout(null);
        this.panelName.setVisible(true);
        this.add(this.panelName);

        //proprietà della label che contiene il nome della carta
        this.nameLabel = new JLabel(name);
        this.nameLabel.setForeground(new Color(235, 212, 203));
        this.nameLabel.setFont(this.fontCard);
        this.nameLabel.setHorizontalAlignment(SwingConstants.HORIZONTAL);
        this.nameLabel.setBounds(0, 0, cardWidth, this.panelName.getHeight());
        this.nameLabel.setVisible(true);
        this.panelName.add(this.nameLabel);

        //proprietà del pannello delle statistiche della carta
        this.panelSpecs = new JPanel();
        this.panelSpecs.setBounds(0, this.panelName.getHeight() + 2,
                this.cardWidth, (int) (this.cardHeight / 7.5));
        this.panelSpecs.setBackground(this.stdColorCard);
        this.panelSpecs.setBorder(BorderFactory.createRaisedSoftBevelBorder());
        this.panelSpecs.setLayout(null);
        this.panelSpecs.setVisible(true);
        this.add(panelSpecs);

        //proprietà della label che contiene le statistiche della carta
        this.specsLabel = new JLabel(attack + "/" + defense);
        this.specsLabel.setForeground(new Color(235, 212, 203));
        this.specsLabel.setFont(this.fontCard);
        this.specsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.specsLabel.setBounds(0, 0, this.cardWidth, this.panelSpecs.getHeight());
        this.specsLabel.setVisible(true);
        this.panelSpecs.add(this.specsLabel);

        //proprietà dell'immagine della carta, stretchata per potersi adattare al layout della carta
        this.frontImg = new ImageIcon(imgPath).getImage().getScaledInstance(this.cardWidth,
                this.cardHeight - this.panelName.getHeight() - this.panelSpecs.getHeight() - (cardHeight / 8) - 2,
                Image.SCALE_SMOOTH);

        //label che contiene come icona l'immagine della carta
        this.img = new JLabel();
        this.img.setIcon(new ImageIcon(this.frontImg));
        this.img.setBounds((this.cardWidth - this.img.getIcon().getIconWidth()) / 2,
                this.panelName.getHeight() + this.panelSpecs.getHeight() + 2, this.cardWidth,
                this.cardHeight - this.panelName.getHeight() - this.panelSpecs.getHeight() - (cardHeight / 8));
        this.img.setVisible(true);
        this.add(this.img);

    }


    public void coverCard() {
        //questo metodo oscura tutto e mette l'immagine del retro della carta, comune a tutte
        this.panelSpecs.setVisible(false);
        this.panelName.setVisible(false);
        this.img.setIcon(new ImageIcon(this.backImg));
        this.img.setBounds(0,0, this.cardWidth, this.cardHeight);
        this.img.setVisible(true);
        this.setEnabled(false);
    }


    public void showCard() {
        //questo metodo rende visibile la carta
        this.panelSpecs.setVisible(true);
        this.panelName.setVisible(true);
        this.img.setIcon(new ImageIcon(this.frontImg));
        this.img.setBounds((this.cardWidth - this.img.getIcon().getIconWidth()) / 2,
                this.panelName.getHeight() + this.panelSpecs.getHeight(), this.cardWidth,
                this.cardHeight - this.panelName.getHeight() - this.panelSpecs.getHeight() - (cardHeight / 8));
        this.img.setVisible(true);
        this.setEnabled(true);
    }


    public void updateLabels() {
        //aggiorno la GUI delle carte con la nuova vita rimanente e le rimetto al colore standard
        this.specsLabel.setText(this.attack + "/" + this.defense);
        Component[] components = getComponents();
        for (Component c : components)
            c.setBackground(new Color(42, 45, 52));
        this.repaint();
    }


    public void changeBkg(int c){
        //cambio il colore della carta in base all'ordine di attacco e di difesa
        //(implementato in placeAttack() e placeDefense())
        Component[] components = getComponents();
        for (Component comp : components) {
            comp.setBackground(this.colors[c]);
        }
        this.repaint();
    }


    @Override
    public boolean equals(Object o) {
        //faccio un override del metodo speciale equals per fare il controllo dell'uguaglianza
        //delle carte in base all'id

        //se il confronto viene effettuato tra elementi che sono null o non compatibili
        if (o == null) return false;
        if (!(o instanceof Card)) return false;
        Card temp = (Card) o;
        return this.getId() == temp.getId();
    }

    protected void cardClicked() {
        //metodo che invoca il metodo del click sulla carta del player da poter usare
        //nella classe di ascolto
        this.player.cardClicked(this);
    }

    public int getId() {
        return this.id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public void setPlayer(Player player) {
        this.player = player;
    }


    @Override
    public String getName() {
        return this.name;
    }


    @Override
    public void setName(String name) {
        this.name = name;
    }


    public int getAttack() {
        return this.attack;
    }


    public void setAttack(int attack) {
        this.attack = attack;
    }


    public int getDefense() {
        return this.defense;
    }


    public void setDefense(int defense) {
        this.defense = defense;
    }


    public Color getColor() {
        return this.color;
    }


    public void setColor(Color color) {
        this.color = color;
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


    protected class CardActionListener implements ActionListener {
        //classe di ascolto che implementa l'interfaccia ActionListener per gestire il click
        @Override
        public void actionPerformed(ActionEvent e) {
            cardClicked();
        }
    }

}
