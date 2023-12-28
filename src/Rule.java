import javax.swing.*;
import java.awt.*;

public class Rule extends JFrame {
    private JLabel img;
    private ImageIcon icon = new ImageIcon("raw_images/cards/rules.png");

    public Rule() {
        this.setTitle("Rules");
        this.setBounds(0, 0, icon.getIconWidth(), icon.getIconHeight());
        this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        img = new JLabel(icon);
        img.setBounds(0, 0, icon.getIconWidth(), icon.getIconHeight());
        img.setVisible(true);
        this.add(img);

    }
}
