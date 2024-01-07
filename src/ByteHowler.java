import java.awt.*;

public class ByteHowler extends CharacterCard {
    //classe che estende quella dei personaggi

    public ByteHowler(int id, int cardWidth, int cardHeight, Color color) {
        //inizializzo i parametri poiché saranno carte concrete
        super(id, "BYTEHOWLER", 2, 3, "raw_images/cards/bytehowler.jpeg", color, cardWidth, cardHeight);
    }
}
