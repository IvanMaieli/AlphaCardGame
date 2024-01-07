import java.awt.*;

public class Nightmare extends CharacterCard {
    //classe che estende quella dei personaggi

    public Nightmare(int id, int cardWidth, int cardHeight, Color color) {
        //inizializzo i parametri poiché saranno carte concrete
        super(id, "NIGHTMARE", 8, 11, "raw_images/cards/nightmare.jpeg", color, cardWidth, cardHeight);
    }
}
