import java.awt.*;


public abstract class CharacterCard extends Card {
    //classe che estende la classe Card.
    //Può non avere utilità in questo caso, ma era stata creata per contenere diversi tipi di carte.
    //Avrei voluto estendere Card con altri tipi di carte oltre a quelle personaggio, ossia
    //le CharacterCard, e credo di volerle aggiungere successivamente.

    public CharacterCard(int id, String name, int attack, int defense,
                         String imgPath, Color color, int cardWidth, int cardHeight) {
        super(id, name, attack, defense, imgPath, color, cardWidth, cardHeight);
    }
    
}
