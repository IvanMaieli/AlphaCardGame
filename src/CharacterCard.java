import javax.swing.*;
import java.awt.*;

public abstract class CharacterCard extends Card {
    private String name;
    private int damage;
    private int life;
    private Image frontImg;
    private Image backImg;
    private JLabel img;
    private Color color;
    private JPanel panelSpecs;
    private JPanel panelName;
    private int cardWidth;
    private int cardHeight;

    public CharacterCard(String name, int attack, int life, String imgPath, Color color, int cardWidth, int cardHeight) {
        this.name = name;
        this.damage = attack;
        this.life = life;
        this.frontImg = new ImageIcon(imgPath).getImage().getScaledInstance(cardWidth, (int)(cardHeight - (cardHeight * 0.15) - (cardHeight * 0.20)), Image.SCALE_SMOOTH);
        this.backImg = new ImageIcon("raw_images/cards/back_card.jpg").getImage().getScaledInstance(cardWidth, cardHeight, Image.SCALE_SMOOTH);
        this.color = color;
        this.cardWidth = cardWidth;
        this.cardHeight = cardHeight;

        this.setSize(cardWidth, cardHeight);
        this.setLayout(null);
        this.setVisible(true);
        this.setBackground(color);

        panelSpecs = new JPanel();
        panelSpecs.setBounds(0,0,cardWidth,(int)(cardHeight * 0.2));
        panelSpecs.setBackground(Color.lightGray);
        panelSpecs.setLayout(null);
        panelSpecs.setVisible(true);
        this.add(panelSpecs);

        img = new JLabel();
        img.setIcon(new ImageIcon(frontImg));
        img.setBounds(0,(int) (cardHeight * 0.2),cardWidth,(int) (cardHeight - (cardHeight * 0.15) - (cardHeight * 0.20)));
        img.setVisible(true);
        this.add(img);

        panelName = new JPanel();
        panelName.setBounds(0, (int)(cardHeight - (cardHeight * 0.15)),cardWidth, (int)(cardHeight * 0.15) + 1);
        panelName.setBackground(Color.lightGray);
        panelName.setLayout(null);
        panelName.setVisible(true);
        this.add(panelName);

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
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
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
