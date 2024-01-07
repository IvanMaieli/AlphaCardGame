import java.awt.*;

public class Gigatron extends CharacterCard {
    //classe che estende quella dei personaggi

    public Gigatron(int id, int cardWidth, int cardHeight, Color color) {
        //inizializzo i parametri poiché saranno carte concrete
        super(id, "GIGATRON", 9, 13, "raw_images/cards/gigatron.jpg", color, cardWidth, cardHeight);
    }
}
