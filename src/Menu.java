import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Menu extends JFrame {

    //pulsante per iniziare a giocare
    private JButton play;

    //pulsante per scoprire le regole del gioco
    private JButton rules;

    public Menu() {
        this.setTitle("Deep Space launcher");

        //prendo le dimensioni dello schermo per rendere il programma responsive
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();

        int width = screenWidth / 4;
        int height = screenHeight / 5;

        //proprietà del menù
        this.getContentPane().setBackground(new Color(42, 45, 52));
        this.setBounds((screenWidth - width) / 2, (screenHeight - height) / 2, width, height);
        this.setLayout(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);

        int buttonWidth = width / 2;
        int buttonHeight = height / 6;

        Font font = new Font("Helvetica", Font.BOLD, 16);

        //proprieta del pulsante di gioco
        this.play = new JButton("PLAY");
        this.play.setFont(font);
        this.play.setBackground(new Color(85, 87, 93));
        this.play.setForeground(new Color(235, 212, 203));
        this.play.setBounds((width - buttonWidth) / 2 - 5, buttonHeight, buttonWidth, buttonHeight);
        this.play.setLayout(null);
        this.play.setFocusPainted(false);
        this.play.addActionListener(new MenuListener());
        this.play.setVisible(true);
        this.add(this.play);

        //proprieta del pulsante delle regole
        this.rules = new JButton("RULES");
        this.rules.setFont(font);
        this.rules.setBackground(new Color(85, 87, 93));
        this.rules.setForeground(new Color(235, 212, 203));
        this.rules.setBounds((width - buttonWidth) / 2 - 5, 3 * buttonHeight, buttonWidth, buttonHeight);
        this.rules.setLayout(null);
        this.rules.setFocusPainted(false);
        this.rules.addActionListener(new MenuListener());
        this.rules.setVisible(true);
        this.add(this.rules);

    }


    public JButton getPlay() {
        return play;
    }


    public void setPlay(JButton play) {
        this.play = play;
    }


    public JButton getRules() {
        return rules;
    }


    public void setRules(JButton rules) {
        this.rules = rules;
    }


    private class MenuListener implements ActionListener {
        //classe di ascolto che implementa l'interfaccia ActionListener per il click sui pulsanti

        @Override
        public void actionPerformed(ActionEvent e) {
            //se viene premuto il pulsante play
            if (e.getSource() == play) {
                //si apre una finestra di gioco
                new Board();
                //e si chiude questa
                dispose();
            }

            //se viene premuto quello delle regole
            if (e.getSource() == rules) {
                //si apre la finestra delle regole ma non si chiude questa
                new Rule();
            }
        }
    }

}

