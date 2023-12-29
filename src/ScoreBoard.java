import javax.swing.*;
import java.awt.*;

public class ScoreBoard extends JPanel {
    public ScoreBoard() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();

        int height = (int) (screenHeight - (screenHeight * 0.1));
        int width = (int) (screenWidth - (screenWidth * 0.11));

        int scoreBoardWidth = 200;
        int scoreBoardHeight = 100;

        this.setBackground(new Color(42, 45, 52));
        this.setBounds((int) (width * 0.06), (height - scoreBoardHeight) / 2 - 24, scoreBoardWidth, scoreBoardHeight);
        this.setBorder(BorderFactory.createRaisedBevelBorder());
        this.setLayout(null);
        this.setVisible(true);
    }
}
