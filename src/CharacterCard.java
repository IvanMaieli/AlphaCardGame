import javax.swing.*;
import java.awt.*;

public abstract class CharacterCard extends Card {
    private String name;
    private int damage;
    private int life;
    private Image frontImg;
    private Image backImg = new ImageIcon("../raw_images/cards/back_card.jpg").getImage().getScaledInstance(116,150,Image.SCALE_SMOOTH);
    private JLabel img;
    private Color color;
    private JPanel panelSpecs;
    private JPanel panelName;

    public CharacterCard(String name, int attack, int life, String imgPath, Color color) {
        this.name = name;
        this.damage = attack;
        this.life = life;
        this.frontImg = new ImageIcon(imgPath).getImage().getScaledInstance(116,150,Image.SCALE_SMOOTH);
        this.color = color;

        this.setSize(185, 210);
        this.setLayout(null);
        this.setVisible(true);
        this.setBackground(color);

        panelSpecs = new JPanel();
        panelSpecs.setBounds(0,0,185,40);
        panelSpecs.setBackground(Color.lightGray);
        panelSpecs.setVisible(true);
        this.add(panelSpecs);

        img = new JLabel();
        img.setIcon(new ImageIcon(frontImg));
        img.setBounds(34,40,116,150);
        img.setVisible(true);
        this.add(img);

        panelName = new JPanel();
        panelName.setBounds(0,190,185,20);
        panelName.setBackground(Color.lightGray);
        panelName.setVisible(true);
        this.add(panelName);




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
}
