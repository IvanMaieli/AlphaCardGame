import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

        int width = screenWidth / 4;
        int height = screenHeight / 5;

        this.getContentPane().setBackground(new Color(42, 45, 52));
        this.setBounds((screenWidth - width) / 2, (screenHeight - height) / 2, width, height);
        this.setLayout(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);

        int buttonWidth = width / 2;
        int buttonHeight = height / 6;

        Font font = new Font("Helvetica", Font.BOLD, 16);
        this.play = new JButton("PLAY");
        this.play.setFont(font);
        this.play.setBackground(new Color(85, 87, 93));
        this.play.setForeground(new Color(235, 212, 203));
        this.play.setBounds((width - buttonWidth) / 2 - 5, buttonHeight, buttonWidth, buttonHeight);
        this.setLayout(null);
        this.play.setFocusPainted(false);
        this.play.addActionListener(new MenuListener());
        this.add(this.play);

        this.rules = new JButton("RULES");
        this.rules.setFont(font);
        this.rules.setBackground(new Color(85, 87, 93));
        this.rules.setForeground(new Color(235, 212, 203));
        this.rules.setBounds((width - buttonWidth) / 2 - 5, 3 * buttonHeight, buttonWidth, buttonHeight);
        this.setLayout(null);
        this.rules.setFocusPainted(false);
        this.rules.addActionListener(new MenuListener());
        this.add(this.rules);


    }

    private class MenuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == play) {
                board = new Board();
                dispose();
            }

            if (e.getSource() == rules) {
                rule = new Rule();
            }
        }
    }

}

