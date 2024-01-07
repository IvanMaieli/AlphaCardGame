import java.awt.*;

public class TecnoPlatypus extends CharacterCard {
    //classe che estende quella dei personaggi

    public TecnoPlatypus(int id, int cardWidth, int cardHeight, Color color) {
        //inizializzo i parametri poiché saranno carte concrete
        super(id, "TECNOPLATYPUS", 2, 2, "raw_images/cards/tecnoplatypus.jpg", color, cardWidth, cardHeight);
    }
}
