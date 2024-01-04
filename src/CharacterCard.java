import javax.swing.*;
import java.awt.*;


public abstract class CharacterCard extends Card {

    public CharacterCard(int id, String name, int attack, int defense,
                         String imgPath, Color color, int cardWidth, int cardHeight) {
        super(id, name, attack, defense, imgPath, color, cardWidth, cardHeight);
    }
    
}
