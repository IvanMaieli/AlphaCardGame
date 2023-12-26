import javax.swing.*;
import java.awt.*;

public abstract class CharacterCard extends Card {
    private String name;
    private int attack;
    private int defense;
    private Image frontImg;
    private Image backImg;
    private JLabel img;
    private Color color;
    private JPanel panelSpecs;
    private JPanel panelName;
    private JLabel attackTextLabel;
    private JLabel attackLabel;
    private JLabel defTextLabel;
    private JLabel defLabel;
    private JLabel nameLabel;
    private int cardWidth;
    private int cardHeight;


    public CharacterCard(String name, int attack, int defense, String imgPath, Color color, int cardWidth, int cardHeight) {
        this.name = name;
        this.attack = attack;
        this.defense = defense;
        this.frontImg = new ImageIcon(imgPath).getImage().getScaledInstance(cardWidth - 30, (int)(cardHeight - (cardHeight * 0.2)), Image.SCALE_SMOOTH);
        this.backImg = new ImageIcon("raw_images/cards/back_card.jpg").getImage().getScaledInstance(cardWidth, cardHeight, Image.SCALE_SMOOTH);
        this.color = color;
        this.cardWidth = cardWidth;
        this.cardHeight = cardHeight;

        Font f = new Font("Helvetica", Font.BOLD, 16);

        this.setSize(cardWidth, cardHeight);
        this.setLayout(null);
        this.setVisible(true);
        this.setBackground(color);

        panelSpecs = new JPanel();
        panelSpecs.setBounds(0,0,cardWidth, (int)(cardHeight * 0.2));
        panelSpecs.setBackground(Color.GRAY);
        panelSpecs.setLayout(null);
        panelSpecs.setVisible(true);
        this.add(panelSpecs);

        panelName = new JPanel();
        panelName.setBounds(0, (int)(cardHeight - 1.35 * (cardHeight * 0.15)), cardWidth, (int)(cardHeight * 0.15) + 1);
        panelName.setBackground(Color.GRAY);
        panelName.setFont(f);
        panelName.setLayout(null);
        panelName.setVisible(true);
        this.add(panelName);

        img = new JLabel();
        img.setIcon(new ImageIcon(frontImg));
        img.setBounds((cardWidth - img.getIcon().getIconWidth()) / 2 - 2,(int) (cardHeight * 0.2), cardWidth,(int) (cardHeight - (cardHeight * 0.20)));
        img.setVisible(true);
        this.add(img);

        int labelWidth = 50;
        int labelHeight = 20;

        attackTextLabel = new JLabel("ATT:");
        attackTextLabel.setForeground(Color.WHITE);
        attackTextLabel.setFont(f);
        attackTextLabel.setBounds(15, (panelSpecs.getHeight() - labelHeight) / 2, labelWidth, labelHeight);
        attackTextLabel.setVisible(true);
        panelSpecs.add(attackTextLabel);

        attackLabel = new JLabel("" + attack);
        attackLabel.setForeground(Color.RED);
        attackLabel.setFont(f);
        attackLabel.setBounds(labelWidth + 10, (panelSpecs.getHeight() - labelHeight) / 2, labelWidth, labelHeight);
        attackLabel.setVisible(true);
        panelSpecs.add(attackLabel);

        defTextLabel = new JLabel("DEF:");
        defTextLabel.setForeground(Color.WHITE);
        defTextLabel.setFont(f);
        defTextLabel.setBounds(((panelSpecs.getWidth() - labelWidth) / 2) + 10, (panelSpecs.getHeight() - labelHeight) / 2, labelWidth, labelHeight);
        defTextLabel.setVisible(true);
        panelSpecs.add(defTextLabel);

        defLabel = new JLabel("" + defense);
        defLabel.setForeground(Color.GREEN);
        defLabel.setFont(f);
        defLabel.setBounds(((panelSpecs.getWidth() - labelWidth) / 2) + labelWidth + 10, (panelSpecs.getHeight() - labelHeight) / 2, labelWidth, labelHeight);
        defLabel.setVisible(true);
        panelSpecs.add(defLabel);

        int nameLabelWidth = 100;
        int nameLabelHeight = labelHeight;
        nameLabel = new JLabel(name);
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setFont(f);
        nameLabel.setHorizontalAlignment(SwingConstants.HORIZONTAL);
        nameLabel.setBounds((panelName.getWidth() - nameLabelWidth) / 2, (panelName.getHeight() - nameLabelHeight) / 2, nameLabelWidth, nameLabelHeight);
        nameLabel.setVisible(true);
        panelName.add(nameLabel);
        System.out.println(panelName.getWidth());
        System.out.println(panelName.getHeight());

    }

    @Override
    public void coverCard() {
        panelSpecs.setVisible(false);
        panelName.setVisible(false);
        img.setIcon(new ImageIcon(backImg));
        img.setBounds(0,0, cardWidth, cardHeight);
        img.setVisible(true);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public int getDamage() {
        return attack;
    }

    public void setDamage(int damage) {
        this.attack = damage;
    }

    public int getLife() {
        return defense;
    }

    public void setLife(int defense) {
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

    @Override
    public String toString() {
        return this.name;
    }
}
