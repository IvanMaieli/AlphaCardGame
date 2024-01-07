import java.awt.*;

public class Horde extends CharacterCard {
    //classe che estende quella dei personaggi

    public Horde(int id, int cardWidth, int cardHeight, Color color) {
        //inizializzo i parametri poiché saranno carte concrete
        super(id, "HORDE", 2, 1, "raw_images/cards/horde.jpeg", color, cardWidth, cardHeight);
    }
}
