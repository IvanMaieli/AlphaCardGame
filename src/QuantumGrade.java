import java.awt.*;

public class QuantumGrade extends CharacterCard {
    //classe che estende quella dei personaggi

    public QuantumGrade(int id, int cardWidth, int cardHeight, Color color) {
        //inizializzo i parametri poiché saranno carte concrete
        super(id, "QUANTUMGRADE", 4, 7, "raw_images/cards/quantumgrade.jpeg", color, cardWidth, cardHeight);
    }
}
