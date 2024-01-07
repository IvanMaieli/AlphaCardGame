import java.awt.*;

public class Roborat extends CharacterCard {
    //classe che estende quella dei personaggi

    public Roborat(int id, int cardWidth, int cardHeight, Color color) {
        //inizializzo i parametri poiché saranno carte concrete
        super(id, "ROBORAT", 2, 8, "raw_images/cards/roborat.jpg", color, cardWidth, cardHeight);
    }
}
