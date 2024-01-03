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
        this.frontImg = new ImageIcon(imgPath).getImage().getScaledInstance(cardWidth - 30, (int)(cardHeight - (cardHeight * 0.2) - (cardHeight * 0.15)), Image.SCALE_SMOOTH);
        this.backImg = new ImageIcon("raw_images/cards/back_card.jpg").getImage().getScaledInstance(cardWidth, cardHeight, Image.SCALE_SMOOTH);
        this.color = color;
        this.cardWidth = cardWidth;
        this.cardHeight = cardHeight;

        colors = new Color[3];
        colors[0] = Color.BLUE;
        colors[1] = Color.GREEN;
        colors[2] = Color.PINK;

        this.setSize(cardWidth, cardHeight);
        this.setBorder(BorderFactory.createRaisedBevelBorder());
        this.setLayout(null);
        this.setBorderPainted(false);
        this.setBackground(color);
        this.addActionListener(new CardActionListener());
        this.setVisible(true);

        panelSpecs = new JPanel();
        panelSpecs.setBounds(0,0,cardWidth, (int)(cardHeight * 0.2));
        panelSpecs.setBackground(stdColorCard);
        panelSpecs.setBorder(BorderFactory.createRaisedBevelBorder());
        panelSpecs.setLayout(null);
        panelSpecs.setVisible(true);
        this.add(panelSpecs);

        panelName = new JPanel();
        panelName.setBounds(0, (int)(cardHeight - 1.35 * (cardHeight * 0.15)), cardWidth, (int)(cardHeight * 0.15) + 1);
        panelName.setBackground(stdColorCard);
        panelName.setBorder(BorderFactory.createRaisedBevelBorder());
        panelName.setFont(fontCard);
        panelName.setLayout(null);
        panelName.setVisible(true);
        this.add(panelName);

        img = new JLabel();
        img.setIcon(new ImageIcon(frontImg));
        img.setBounds((cardWidth - img.getIcon().getIconWidth()) / 2 - 2, (int) (cardHeight * 0.20), cardWidth,(int) (cardHeight - (cardHeight * 0.20) - (cardHeight* 0.15)));
        img.setVisible(true);
        this.add(img);

        int labelWidth = (int) (panelSpecs.getWidth() * 0.42);
        int labelHeight = 30;

        specsLabel = new JLabel(attack + "/" + defense);
        specsLabel.setForeground(new Color(235, 212, 203));
        specsLabel.setFont(fontCard);
        specsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        specsLabel.setBounds((cardWidth - labelWidth) / 2, (panelSpecs.getHeight() - labelHeight) / 2, labelWidth, labelHeight);
        specsLabel.setVisible(true);
        panelSpecs.add(specsLabel);

        int nameLabelWidth = 150;
        nameLabel = new JLabel(name);
        nameLabel.setForeground(new Color(235, 212, 203));
        nameLabel.setFont(fontCard);
        nameLabel.setHorizontalAlignment(SwingConstants.HORIZONTAL);
        nameLabel.setBounds((panelName.getWidth() - nameLabelWidth) / 2, (panelName.getHeight() - labelHeight) / 2, nameLabelWidth, labelHeight);
        nameLabel.setVisible(true);
        panelName.add(nameLabel);
    }


    public void coverCard() {
        panelSpecs.setVisible(false);
        panelName.setVisible(false);
        img.setIcon(new ImageIcon(backImg));
        img.setBounds(0,0, cardWidth, cardHeight);
        img.setVisible(true);
        this.setEnabled(false);
    }


    public void showCard() {
        panelSpecs.setVisible(true);
        panelName.setVisible(true);
        img.setIcon(new ImageIcon(frontImg));
        img.setBounds((cardWidth - img.getIcon().getIconWidth()) / 2 - 2, (int) (cardHeight * 0.2), cardWidth, (int) (cardHeight - (cardHeight * 0.20) - (cardHeight* 0.15)));
        img.setVisible(true);
        this.setEnabled(true);
    }


    public void updateLabels() {
        this.specsLabel.setText(attack + "/" + defense);
        Component[] components = getComponents();
        for (Component c : components)
            c.setBackground(new Color(42, 45, 52));
        this.repaint();
    }


    public void changeBkg(int c){
        attackMode = !attackMode;
        Component[] components = getComponents();
        for (Component comp : components) {
            comp.setBackground(colors[c]);
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
        return id;
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
        return player;
    }


    public Color[] getColors() {
        return colors;
    }


    public void setColors(Color[] colors) {
        this.colors = colors;
    }


    @Override
    public String getName() {
        return name;
    }


    @Override
    public void setName(String name) {
        this.name = name;
    }


    public int getAttack() {
        return attack;
    }


    public void setAttack(int attack) {
        this.attack = attack;
    }


    public int getDefense() {
        return defense;
    }


    public void setDefense(int defense) {
        this.defense = defense;
    }


    public Image getFrontImg() {
        return frontImg;
    }


    public void setFrontImg(Image frontImg) {
        this.frontImg = frontImg;
    }


    public Image getBackImg() {
        return backImg;
    }


    public void setBackImg(Image backImg) {
        this.backImg = backImg;
    }


    public JLabel getImg() {
        return img;
    }


    public void setImg(JLabel img) {
        this.img = img;
    }


    public Color getStdColorCard() {
        return stdColorCard;
    }


    public Color getColor() {
        return color;
    }


    public void setColor(Color color) {
        this.color = color;
    }


    public JPanel getPanelSpecs() {
        return panelSpecs;
    }


    public void setPanelSpecs(JPanel panelSpecs) {
        this.panelSpecs = panelSpecs;
    }


    public JPanel getPanelName() {
        return panelName;
    }


    public void setPanelName(JPanel panelName) {
        this.panelName = panelName;
    }


    public JLabel getSpecsLabel() {
        return specsLabel;
    }


    public void setSpecsLabel(JLabel specsLabel) {
        this.specsLabel = specsLabel;
    }


    public JLabel getNameLabel() {
        return nameLabel;
    }


    public void setNameLabel(JLabel nameLabel) {
        this.nameLabel = nameLabel;
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


    public Font getFontCard() {
        return fontCard;
    }


    public void setFontCard(Font fontCard) {
        this.fontCard = fontCard;
    }


    public boolean isAttackMode() {
        return attackMode;
    }


    public void setAttackMode(boolean attackMode) {
        this.attackMode = attackMode;
    }

}
