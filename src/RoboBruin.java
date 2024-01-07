import java.awt.*;

public class RoboBruin extends CharacterCard {
    //classe che estende quella dei personaggi

    public RoboBruin(int id, int cardWidth, int cardHeight, Color color) {
        //inizializzo i parametri poiché saranno carte concrete
        super(id, "ROBOBRUIN", 6, 4, "raw_images/cards/gigaorso.jpeg", color, cardWidth, cardHeight);
    }
}
