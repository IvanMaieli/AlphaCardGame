import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public abstract class Card extends JButton {
    private int id;
    protected Player player = null;

    public Card(int id) {
        this.id = id;
    }

    public void coverCard() {}

    public void showCard() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    protected void cardClicked() {
        this.player.cardClicked(this);
    }

    @Override
    public boolean equals(Object o) {
        Card temp;
        if(!(o instanceof Card)) return false;
        temp = (Card) o;
        return this.getId() == temp.getId();
    }


    protected class CardListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent mouseEvent) {
            cardClicked();
        }

        @Override
        public void mousePressed(MouseEvent mouseEvent) {}

        @Override
        public void mouseReleased(MouseEvent mouseEvent) {}

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
                c.setBackground(new Color(28, 49, 68));
        }

    }
}
