import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Menu extends JFrame {
    private JButton play;
    private JButton rules;
    private Board board;
    private Rule rule;

    public Menu() {
        this.setTitle("Deep Space launcher");

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();

        int width = 400;
        int height = 160;

        this.getContentPane().setBackground(new Color(42, 45, 52));
        this.setBounds((screenWidth - width) / 2, (screenHeight - height) / 2, width, height);
        this.setLayout(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);


        int buttonWidth = 380;
        int buttonHeight = 50;

        Font font = new Font("Monospaced", Font.BOLD, 16);

        play = new JButton("Play");
        play.setBackground(new Color(42, 45, 52));
        play.setForeground(new Color(235, 212, 203));
        play.setFont(font);
        play.setBounds(10, 10, buttonWidth, buttonHeight);
        play.setLayout(null);
        play.setFocusPainted(false);
        play.setRolloverEnabled(false);
        play.addMouseListener(new MenuListener());
        play.setVisible(true);
        add(play);

        rules = new JButton("Rules");
        rules.setBackground(new Color(42, 45, 52));
        rules.setForeground(new Color(235, 212, 203));
        rules.setFont(font);
        rules.setBounds(10, 20 + buttonHeight, buttonWidth, buttonHeight);
        play.setLayout(null);
        rules.setFocusPainted(false);
        rules.setRolloverEnabled(false);
        rules.addMouseListener(new MenuListener());
        rules.setVisible(true);
        add(rules);

    }

    private class MenuListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent mouseEvent) {
            if(mouseEvent.getSource() == play) {
                board = new Board();
                dispose();
            }

            if(mouseEvent.getSource() == rules) {
                rule = new Rule();
            }
        }

        @Override
        public void mousePressed(MouseEvent mouseEvent) {}

        @Override
        public void mouseReleased(MouseEvent mouseEvent) {}

        @Override
        public void mouseEntered(MouseEvent mouseEvent) {}

        @Override
        public void mouseExited(MouseEvent mouseEvent) {}

    }

}

