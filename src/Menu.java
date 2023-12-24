import javax.swing.*;
import javax.swing.event.MenuListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Menu extends JFrame {
    private JButton play;
    private JButton rules;

    private Board board;


    public Menu() {
        this.setTitle("Menu CyberAttack");

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();

        int height = 200;
        int width = 400;

        this.setBounds((screenWidth - width) / 2, (screenHeight - height) / 2, width, height);
        this.setVisible(true);
        this.setLayout(null);
        this.setResizable(false);
        //this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        int buttonHeight = 70;
        int buttonWidth = 380;

        play = new JButton("Play");
        play.setBounds(10, 10, buttonWidth, buttonHeight);
        play.setVisible(true);
        play.setFocusPainted(false);
        play.addMouseListener(new MenuListener());
        add(play);

        rules = new JButton("Rules");
        rules.setBounds(10, 20 + buttonHeight, buttonWidth, buttonHeight);
        rules.setVisible(true);
        rules.setFocusPainted(false);
        add(rules);
    }

    private class MenuListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent mouseEvent) {
            if(mouseEvent.getSource() == play) {
                board = new Board();
                dispose();
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

