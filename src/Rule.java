import javax.swing.*;
import java.awt.*;

public class Rule extends JFrame {
    //questa classe contiene solo un'immagine con stampate le regole del gioco

    //l'immagine
    private JLabel img;

    //icona contiene l'immagine
    private ImageIcon icon = new ImageIcon("raw_images/cards/rules.png");


    public Rule() {
        //proprietà del frame
        this.setTitle("Rules");
        this.setBounds(0, 0, icon.getIconWidth(), icon.getIconHeight());
        this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        //proprietà della label con l'immagine
        img = new JLabel(icon);
        img.setBounds(0, 0, icon.getIconWidth(), icon.getIconHeight());
        img.setVisible(true);
        this.add(img);

    }


    public JLabel getImg() {
        return img;
    }


    public void setImg(JLabel img) {
        this.img = img;
    }


    public ImageIcon getIcon() {
        return icon;
    }


    public void setIcon(ImageIcon icon) {
        this.icon = icon;
    }

}
