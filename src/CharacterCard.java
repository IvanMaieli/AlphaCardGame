import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public abstract class CharacterCard extends Card {

    private String name;
    private int attack;
    private int defense;
    private Image frontImg;
    private Image backImg;
    private JLabel img;
    private final Color stdColorCard = new Color(28, 49, 68);
    private Color color; // (255, 186, 8) = leggendaria, (208, 0, 0) = epica, colore standard = comune
    private JPanel panelSpecs;
    private JPanel panelName;
    private JLabel attackTextLabel;
    private JLabel attackLabel;
    private JLabel defTextLabel;
    private JLabel defLabel;
    private JLabel nameLabel;
    private int cardWidth;
    private int cardHeight;
    private boolean pressed = false;


    public CharacterCard(String name, int attack, int defense, String imgPath, Color color, int cardWidth, int cardHeight) {
        this.name = name;
        this.attack = attack;
        this.defense = defense;
        this.frontImg = new ImageIcon(imgPath).getImage().getScaledInstance(cardWidth - 30, (int)(cardHeight - (cardHeight * 0.2) - (cardHeight * 0.15)), Image.SCALE_SMOOTH);
        this.backImg = new ImageIcon("raw_images/cards/back_card.jpg").getImage().getScaledInstance(cardWidth, cardHeight, Image.SCALE_SMOOTH);
        this.color = color;
        this.cardWidth = cardWidth;
        this.cardHeight = cardHeight;

        Font fontCard = new Font("Monospaced", Font.BOLD, 16);

        this.setSize(cardWidth, cardHeight);
        this.setLayout(null);
        this.setVisible(true);
        this.setBackground(color);
        this.addMouseListener(new CardListener());

        panelSpecs = new JPanel();
        panelSpecs.setBounds(0,0,cardWidth, (int)(cardHeight * 0.2));
        panelSpecs.setBackground(stdColorCard);
        panelSpecs.setLayout(null);
        panelSpecs.setVisible(true);
        this.add(panelSpecs);

        panelName = new JPanel();
        panelName.setBounds(0, (int)(cardHeight - 1.45 * (cardHeight * 0.15)), cardWidth, (int)(cardHeight * 0.15) + 1);
        panelName.setBackground(stdColorCard);
        panelName.setFont(fontCard);
        panelName.setLayout(null);
        panelName.setVisible(true);
        this.add(panelName);

        img = new JLabel();
        img.setIcon(new ImageIcon(frontImg));
        img.setBounds((cardWidth - img.getIcon().getIconWidth()) / 2 - 2, (int) (cardHeight * 0.2), cardWidth,(int) (cardHeight - (cardHeight * 0.20) - (cardHeight* 0.15)));
        img.setVisible(true);
        this.add(img);

        int labelWidth = (int) (panelSpecs.getWidth() * 0.25);
        int labelHeight = 30;

        attackTextLabel = new JLabel("ATT:");
        attackTextLabel.setForeground(new Color(235, 212, 203));
        attackTextLabel.setFont(fontCard);
        attackTextLabel.setBounds((int)(cardWidth * 0.09), (panelSpecs.getHeight() - labelHeight) / 2, labelWidth, labelHeight);
        attackTextLabel.setVisible(true);
        panelSpecs.add(attackTextLabel);

        attackLabel = new JLabel("" + attack);
        attackLabel.setForeground(new Color(235, 212, 203));
        attackLabel.setFont(fontCard);
        attackLabel.setBounds(attackTextLabel.getX() + labelWidth, (panelSpecs.getHeight() - labelHeight) / 2, labelWidth, labelHeight);
        attackLabel.setVisible(true);
        panelSpecs.add(attackLabel);

        defTextLabel = new JLabel("DEF:");
        defTextLabel.setForeground(new Color(235, 212, 203));
        defTextLabel.setFont(fontCard);
        defTextLabel.setBounds(panelSpecs.getWidth() - 2 * labelWidth, (panelSpecs.getHeight() - labelHeight) / 2, labelWidth, labelHeight);
        defTextLabel.setVisible(true);
        panelSpecs.add(defTextLabel);

        defLabel = new JLabel("" + defense);
        defLabel.setForeground(new Color(235, 212, 203));
        defLabel.setFont(fontCard);
        defLabel.setBounds(panelSpecs.getWidth() - labelWidth, (panelSpecs.getHeight() - labelHeight) / 2, labelWidth, labelHeight);
        defLabel.setVisible(true);
        panelSpecs.add(defLabel);

        int nameLabelWidth = 150;
        nameLabel = new JLabel(name);
        nameLabel.setForeground(new Color(235, 212, 203));
        nameLabel.setFont(fontCard);
        nameLabel.setHorizontalAlignment(SwingConstants.HORIZONTAL);
        nameLabel.setBounds((panelName.getWidth() - nameLabelWidth) / 2, (panelName.getHeight() - labelHeight) / 2, nameLabelWidth, labelHeight);
        nameLabel.setVisible(true);
        panelName.add(nameLabel);

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

    private class CardListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent mouseEvent) {}

        @Override
        public void mousePressed(MouseEvent mouseEvent) {}

        @Override
        public void mouseReleased(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseEntered(MouseEvent mouseEvent) {
            Component[] components = getComponents();
            for(Component c : components)
                c.setBackground(new Color(5, 100, 240));
        }

        @Override
        public void mouseExited(MouseEvent mouseEvent) {
            Component[] components = getComponents();
            for(Component c : components)
                c.setBackground(stdColorCard);

        }

    }
}
