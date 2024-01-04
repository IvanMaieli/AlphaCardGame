import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public abstract class Card extends JButton {
    private int id;
    protected Player player = null;
    private Color[] colors;
    private String name;
    private int attack;
    private int defense;
    private Image frontImg;
    private Image backImg;
    private JLabel img;
    private final Color stdColorCard = new Color(42, 45, 52);
    private Color color;
    private JPanel panelSpecs;
    private JPanel panelName;
    private JLabel specsLabel;
    private JLabel nameLabel;
    private int cardWidth;
    private int cardHeight;
    private boolean attackMode = false;
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

        colors = new Color[3];
        colors[0] = new Color(111, 29, 27);
        colors[1] = new Color(36, 130, 50);
        colors[2] = new Color(0, 117, 162);

        this.setSize(cardWidth, cardHeight);
        this.setBorder(BorderFactory.createRaisedBevelBorder());
        this.setLayout(null);
        this.setBorderPainted(false);
        this.setBackground(color);
        this.addActionListener(new CardActionListener());
        this.setVisible(true);

        this.panelName = new JPanel();
        this.panelName.setBounds(0,0, this.cardWidth, this.cardHeight / 6);
        this.panelName.setBackground(this.stdColorCard);
        this.panelName.setBorder(BorderFactory.createRaisedBevelBorder());
        this.panelName.setFont(this.fontCard);
        this.panelName.setLayout(null);
        this.panelName.setVisible(true);
        this.add(this.panelName);

        this.panelSpecs = new JPanel();
        this.panelSpecs.setBounds(0, this.panelName.getHeight() + 2,
                this.cardWidth, this.cardHeight / 6);
        this.panelSpecs.setBackground(this.stdColorCard);
        this.panelSpecs.setBorder(BorderFactory.createRaisedSoftBevelBorder());
        this.panelSpecs.setLayout(null);
        this.panelSpecs.setVisible(true);
        this.add(panelSpecs);


        this.frontImg = new ImageIcon(imgPath).getImage().getScaledInstance(this.cardWidth,
                this.cardHeight - this.panelName.getHeight() - this.panelSpecs.getHeight() - (cardHeight / 8) - 2,
                Image.SCALE_SMOOTH);

        this.img = new JLabel();
        this.img.setIcon(new ImageIcon(this.frontImg));
        this.img.setBounds((this.cardWidth - this.img.getIcon().getIconWidth()) / 2,
                this.panelName.getHeight() + this.panelSpecs.getHeight() + 2, this.cardWidth,
                this.cardHeight - this.panelName.getHeight() - this.panelSpecs.getHeight() - (cardHeight / 8));

        this.img.setVisible(true);
        this.add(this.img);

        int labelWidth = (int) (this.panelSpecs.getWidth() * 0.42);
        int labelHeight = 30;

        this.specsLabel = new JLabel(attack + "/" + defense);
        this.specsLabel.setForeground(new Color(235, 212, 203));
        this.specsLabel.setFont(this.fontCard);
        this.specsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.specsLabel.setBounds((cardWidth - labelWidth) / 2, (this.panelSpecs.getHeight() - labelHeight) / 2, labelWidth, labelHeight);
        this.specsLabel.setVisible(true);
        this.panelSpecs.add(this.specsLabel);

        int nameLabelWidth = 150;
        this.nameLabel = new JLabel(name);
        this.nameLabel.setForeground(new Color(235, 212, 203));
        this.nameLabel.setFont(this.fontCard);
        this.nameLabel.setHorizontalAlignment(SwingConstants.HORIZONTAL);
        this.nameLabel.setBounds((this.panelName.getWidth() - nameLabelWidth) / 2, (this.panelName.getHeight() - labelHeight) / 2, nameLabelWidth, labelHeight);
        this.nameLabel.setVisible(true);
        this.panelName.add(this.nameLabel);
    }


    public void coverCard() {
        this.panelSpecs.setVisible(false);
        this.panelName.setVisible(false);
        this.img.setIcon(new ImageIcon(this.backImg));
        this.img.setBounds(0,0, this.cardWidth, this.cardHeight);
        this.img.setVisible(true);
        this.setEnabled(false);
    }


    public void showCard() {
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
        this.specsLabel.setText(this.attack + "/" + this.defense);
        Component[] components = getComponents();
        for (Component c : components)
            c.setBackground(new Color(42, 45, 52));
        this.repaint();
    }


    public void changeBkg(int c){
        this.attackMode = !this.attackMode;
        Component[] components = getComponents();
        for (Component comp : components) {
            comp.setBackground(this.colors[c]);
        }
        this.repaint();
    }


    @Override
    public boolean equals(Object o) {
        Card temp;
        if (!(o instanceof Card)) return false;
        temp = (Card) o;
        return this.getId() == temp.getId();
    }


    protected class CardActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            cardClicked();
        }
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


    protected void cardClicked() {
        this.player.cardClicked(this);
    }


    public Player getPlayer() {
        return this.player;
    }


    public Color[] getColors() {
        return this.colors;
    }


    public void setColors(Color[] colors) {
        this.colors = colors;
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


    public Image getFrontImg() {
        return this.frontImg;
    }


    public void setFrontImg(Image frontImg) {
        this.frontImg = frontImg;
    }


    public Image getBackImg() {
        return this.backImg;
    }


    public void setBackImg(Image backImg) {
        this.backImg = backImg;
    }


    public JLabel getImg() {
        return this.img;
    }


    public void setImg(JLabel img) {
        this.img = img;
    }


    public Color getStdColorCard() {
        return this.stdColorCard;
    }


    public Color getColor() {
        return this.color;
    }


    public void setColor(Color color) {
        this.color = color;
    }


    public JPanel getPanelSpecs() {
        return this.panelSpecs;
    }


    public void setPanelSpecs(JPanel panelSpecs) {
        this.panelSpecs = panelSpecs;
    }


    public JPanel getPanelName() {
        return this.panelName;
    }


    public void setPanelName(JPanel panelName) {
        this.panelName = panelName;
    }


    public JLabel getSpecsLabel() {
        return this.specsLabel;
    }


    public void setSpecsLabel(JLabel specsLabel) {
        this.specsLabel = specsLabel;
    }


    public JLabel getNameLabel() {
        return this.nameLabel;
    }


    public void setNameLabel(JLabel nameLabel) {
        this.nameLabel = nameLabel;
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


    public Font getFontCard() {
        return this.fontCard;
    }


    public void setFontCard(Font fontCard) {
        this.fontCard = fontCard;
    }


    public boolean isAttackMode() {
        return this.attackMode;
    }


    public void setAttackMode(boolean attackMode) {
        this.attackMode = attackMode;
    }

}
