import java.awt.*;
import java.io.IOException;

public class Spaceman extends CharacterCard {
    //classe che estende quella dei personaggi

    public Spaceman(int id, int cardWidth, int cardHeight, Color color)  {
        //inizializzo i parametri poiché saranno carte concrete
        super(id, "SPACEMAN", 1, 1, "raw_images/cards/spaceman.jpeg", color, cardWidth, cardHeight);
    }
}
