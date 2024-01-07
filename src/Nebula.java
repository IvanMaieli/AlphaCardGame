import java.awt.*;

public class Nebula extends CharacterCard {
    //classe che estende quella dei personaggi

    public Nebula(int id, int cardWidth, int cardHeight, Color color) {
        //inizializzo i parametri poiché saranno carte concrete
        super(id, "NEBULA", 7, 11 , "raw_images/cards/nebula.jpg", color, cardWidth, cardHeight);
    }
}
