import java.awt.*;
import java.util.LinkedList;
import javax.swing.*;

public class Player extends JPanel {

    //punti del giocatore
    private int points;

    //pannello del mazzo delle carte non schierate
    private JPanel deckPanel;

    //pannello delle carte schierate
    private JPanel field;

    //pannello del nome del giocatore
    private JPanel fieldName;

    //label con il nome del giocatore
    private JLabel pName;

    //pannello del punteggio del giocatore
    private JPanel fieldScore;

    //label con il punteggio
    private JLabel pScore;

    //mazzo delle carte non schierate
    private LinkedList<Card> deck;

    //mazzo delle carte schierate
    private LinkedList<Card> selectedCards;

    //larghezza dell pannello del mazzo delle carte non schierate
    private int deckWidth;

    //identificativo del giocatore
    private int id;

    //larghezza del pannello delle carte schierate
    private int fieldWidth;

    //altezza del pannello delle carte schierate
    private int fieldHeight;

    //larghezza di una carta
    private int cardWidth;

    //altezza di una carta
    private int cardHeight;

    //booleana che viene messa a true se è il turno del giocatore e false se non lo è
    private boolean turn;

    //il frame di gioco in modo da potersi interfacciare anche con la schermata principale
    private Board board;

    //font della classe
    private Font font = new Font("Helvetica", Font.BOLD, 14);


    public Player(int panelWidth, int panelHeight, int id, Color color, boolean turn, Board board) {
        //proprietà del giocatore
        this.points = 0;
        this.deckWidth = panelWidth - 20;
        this.turn = turn;
        this.board = board;
        this.id = id;
        this.setSize(panelWidth, panelHeight);
        this.setBackground(new Color(42, 45, 52));
        this.setLayout(null);

        //metto a null tutte le carte schierate
        this.selectedCards = new LinkedList<>();
        this.selectedCards.add(null);
        this.selectedCards.add(null);
        this.selectedCards.add(null);

        //inizializzo il mazzo del giocatore (con il metodo giveCards passerò dopo le carte dalla board)
        this.deck = new LinkedList<>();

        this.fieldWidth = panelWidth / 7 * 3;
        this.fieldHeight = (panelHeight - 20) / 2;

        //proprietà del pannello delle carte non schierate
        this.deckPanel = new JPanel();
        this.deckPanel.setBounds(10, 10 + this.fieldHeight * (id - 1), this.deckWidth, this.fieldHeight);
        this.deckPanel.setBorder(BorderFactory.createLoweredBevelBorder());
        this.deckPanel.setBackground(color);
        this.deckPanel.setLayout(null);
        this.deckPanel.setVisible(true);
        this.add(this.deckPanel);

        //proprietà del mazzo delle carte schierate
        this.field = new JPanel();
        this.field.setBounds((panelWidth - this.fieldWidth) / 2,
                (10 * 2) + (this.fieldHeight) - (this.fieldHeight + 20) * (id - 1),
                this.fieldWidth, this.fieldHeight);
        this.field.setBorder(BorderFactory.createLoweredBevelBorder());
        this.field.setBackground(color);
        this.field.setLayout(null);
        this.field.setVisible(true);
        this.add(this.field);

        //proprietà del pannello del nome del giocatore
        this.fieldName = new JPanel();
        if(id == 2) {
            this.fieldName.setBounds((panelWidth - this.fieldWidth) / 2 - this.fieldWidth / 5,
                    this.field.getY() + this.fieldHeight - (this.fieldHeight / 6) * 2,
                    this.fieldWidth / 5, this.fieldHeight / 6);
        } else {
            fieldName.setBounds((panelWidth - this.fieldWidth) / 2 - this.fieldWidth / 5,
                    ((10 * 2) + (this.fieldHeight) - (this.fieldHeight + 20) * (id - 1)),
                    this.fieldWidth / 5, this.fieldHeight / 6);
        }
        this.fieldName.setBackground(new Color(170, 70, 1));
        this.fieldName.setBorder(BorderFactory.createLoweredBevelBorder());
        this.fieldName.setLayout(null);
        this.fieldName.setVisible(true);
        this.add(this.fieldName);

        //proprietà label con il nome del giocatore e del suo id
        this.pName = new JLabel("PLAYER " + id);
        this.pName.setFont(this.font);
        this.pName.setForeground(new Color(235, 212, 203));
        this.pName.setBounds(10, (this.fieldName.getHeight() - 40) / 2, 150, 40);
        this.pName.setLayout(null);
        this.pName.setVisible(true);
        this.fieldName.add(this.pName);

        //proprietà pannello del punteggio
        this.fieldScore = new JPanel();
        this.fieldScore.setBounds(this.fieldName.getX(), this.fieldName.getY() + this.fieldName.getHeight(),
                this.fieldName.getWidth(), this.fieldName.getHeight());
        this.fieldScore.setBackground(new Color(170, 70, 1));
        this.fieldScore.setBorder(BorderFactory.createLoweredBevelBorder());
        this.fieldScore.setLayout(null);
        this.fieldScore.setVisible(true);
        this.add(this.fieldScore);

        //proprietà label del punteggio
        this.pScore = new JLabel("" + this.points);
        this.pScore.setFont(this.font);
        this.pScore.setForeground(new Color(235, 212, 203));
        this.pScore.setBounds(10, (this.fieldScore.getHeight() - 40) / 2, 150, 40);
        this.pScore.setLayout(null);
        this.pScore.setVisible(true);
        this.fieldScore.add(this.pScore);

        this.cardHeight = this.fieldHeight - 20;
        this.cardWidth = (int) (this.deckWidth - 80) / 7;

        this.validate();
        this.repaint();
        this.setVisible(true);
    }


    public void updateScore() {
        //aggiornamento dei valori nella label dei punti del giocatore
        this.pScore.setText("" + this.points);
    }


    public void updateCards(Player player) {
        //questo metodo serve per aggiornare i parametri nelle carte

        //aggiorno la vita e il colore delle carte nel campo di battaglia
        for(Card c : this.selectedCards) {
            if(c != null)
                c.updateLabels();
        }

        int i = 0;

        //per ogni carta in quelle schierate
        for(Card c : this.selectedCards) {
            //se non è nulla
            if (c != null)
                //se ha una vita rimanente minore o uguale a 0, quindi è morta
                if (c.getDefense() <= 0) {
                    //la rimuovo e incremento i punti del nemico
                    this.field.remove((Component) c);
                    player.setPoints(player.getPoints() + 1);
                    getSelectedCards().set(i, null);
                }
            i++;
        }
    }


    public void updateView() {
        //questo metodo serve a riposizionare le carte dopo averle spostate
        int i = 0;

        //per ogni carta nel mazzo delle non schierate
        for (Card c : this.deck) {
            //se non è nulla
            if (c != null) {
                //le posiziono sequenzialmente
                c.setBounds((10 * (i + 1)) + (this.cardWidth * i), 10, this.cardWidth, this.cardHeight);
                c.setLayout(null);
                c.setVisible(true);
                this.deckPanel.add(c);
            }
            i++;
        }
        this.deckPanel.repaint();

        i = 0;

        //per ogni carta in quelle schierate
        for (Card c : this.selectedCards) {
            //se non è nulla
            if (c != null) {
                //le posiziono sequenzialmente
                c.setBounds((10 * (i + 1)) + (this.cardWidth * i), 10, this.cardWidth, this.cardHeight);
                c.setLayout(null);
                c.setVisible(true);
                this.field.add(c);
            }
            i++;
        }
        //aggiorno il punteggio
        this.updateScore();

        this.field.repaint();
    }


    public void giveCards(LinkedList<Card> deckP) {
        //questo metodo serve per dare le carte sorteggiate in board ai giocatori
        for (Card c : deckP) {
            this.deck.addFirst(c);
            c.setPlayer(this);
        }
        this.updateView();
    }


    public void chooseCards(LinkedList<Card> cards) {
        //questo metodo viene invocato alla fine di ogni turno e riposiziona le carte rimaste in vita
        //nel mazzo delle non schierate e pesca dal mazzo delle rimanenti ogni volta che ne muore una
        int i = 0;
        int j = 0;

        //per ogni carta nel mazzo delle schierate
        for (Card c : this.selectedCards) {
            //se non è nulla
            if (c != null) {
                j = 0;
                //scorro il mazzo di quelle non schierate
                for (Card c2 : this.deck) {
                    //alla prima nulla che trovo
                    if (c2 == null) {
                        //posiziono la carta nello spazio vuoto
                        this.deck.set(j, c);
                        this.field.remove((Component) this.selectedCards.get(i));
                        this.selectedCards.set(i, null);
                        break;
                    }
                    j++;
                }
            }
            i++;
        }

        i = 0;
        //per ogni carta nel mazzo delle non schierate
        for (Card c : this.deck) {
            //se è nulla e quindi una carta è stata eliminata
            if (c == null) {
                //allora la pesco da cards che ho passato (che sarà il mazzo rimanente in board)
                this.deck.set(i, cards.removeFirst());
                //e la assegno a questo giocatore
                this.deck.get(i).setPlayer(this);
            }
            i++;
        }

        //mostro le carte
        for (Card c : this.deck) {
            c.showCard();
        }

        //aggiorno i posizionamenti
        this.updateView();
    }


    public void cardClicked(Card card) {
        //se è il proprio turno
        if (this.turn) {
            //se la partita è in fase di schieramento
            if (this.board.isPhasePositioning()) {
                //e se il mazzo delle carte non schierate contiene la carta cliccata
                if (this.deck.contains(card)) {
                    //se il numero di elementi schierati è già 3
                    if (numberOfElements(this.selectedCards) == 3) {
                        //disattivo il mazzo delle non schierate
                        disableDeck();
                    } else { // altrimenti se si possono ancora cliccare carte
                        int i = 0;

                        //per ogni carta nel mazzo di quelle non schierate
                        for (Card c : this.deck) {
                            //se la carta non è nulla
                            if (c != null) {
                                //e se gli id corrispondono
                                if (c.equals(card)) {
                                    int j = 0;
                                    //scorro le carte già schierate
                                    for (Card z : this.selectedCards) {
                                        //e controllo la prima posizione nulla
                                        if (z == null) {
                                            //se esiste, la sostituisco a quella nulla
                                            this.selectedCards.set(j, this.deck.get(i));
                                            break;
                                        }
                                        j++;
                                    }
                                    break;
                                }
                            }
                            i++;
                        }
                        //rimuovo la carta dal pannello delle carte non schierate
                        this.deckPanel.remove((Component) card);
                        //metto lo spazio a null
                        this.deck.set(i, null);
                    }
                } else { //altrimenti se la carta non si trova nel mazzo delle carte non schierate
                    int i = 0;
                    //scorro le carte schierate
                    for (Card c : this.selectedCards) {
                        boolean exit = false;
                        //se la carta non è null
                        if (c != null) {
                            //e se gli id sono uguali
                            if (c.equals(card)) {
                                int j = 0;
                                //scorro le carte del mazzo di quelle non schierate
                                for (Card s : this.deck) {
                                    //se trovo uno spazio vuoto
                                    if (s == null) {
                                        //interrompo il ciclo e salvo la posizione null
                                        exit = true;
                                        break;
                                    }
                                    j++;
                                }
                                //se c'è una posizione nulla
                                if (exit) {
                                    //allora metto la carta cliccata in quella posizione
                                    this.deck.set(j, c);
                                    //e la rimuovo dal campo di battaglia
                                    this.field.remove((Component) card);
                                    //e metto la posizione nel campo da battaglia a null
                                    this.selectedCards.set(i, null);
                                    break;
                                }
                            }
                        }
                        i++;
                    }
                    //attivo il mazzo delle carte non schierate
                    this.enableDeck();
                }
            } else { //altrimenti se non è in fase di posizionamento
                //agisco sulla carta con la meccanica di attacco
                this.board.placeAttack(card, this);
            }
            //aggiorno la GUI con le nuove posizioni delle carte
            updateView();
        } else {
            //agisco sulle carte con le meccaniche di difesa
            this.board.placeDefense(card, this);
        }
    }


    public void changeTurn() {
        //questo metodo cambia il turno
        this.turn = !this.turn;
    }


    private int numberOfElements(LinkedList<Card> cards) {
        //questo metodo conta il numero di elementi non nulli presenti una lista
        int i = 0;
        for (Card c : cards)
            if (c != null)
                i++;
        return i;
    }


    public void waitTurn() {
        //in questo metodo copro tutte le carte del mazzo di un giocatore
        for (Card c : this.deck)
            if (c != null)
                c.coverCard();
        updateView();
    }


    public boolean checkSelectedCards() {
        //in questo metodo controllo se il numero di carte schierate è minore di 3
        if (numberOfElements(this.selectedCards) < 3) {
            //se è vero allora sorge un messaggio di avviso
            JOptionPane.showMessageDialog(null, "Devi selezionare 3 carte!");
            return false;
        }
        return true;
    }


    public void enableDeck() {
        //questo metodo rende cliccabili tutte le carte del mazzo delle non schierate
        for (Card c : this.deck)
            if(c != null)
                c.setEnabled(true);
    }


    public void enableField() {
        //questo metodo si occupa di rendere cliccabili tutte le carte in campo schierate
        for (Card c : this.selectedCards)
            if(c != null)
                c.setEnabled(true);
    }


    public void enableField(int[] vet, int v) {
        //questo metodo attiva tutte le carte schierate in campo tranne quelle già selezionate negli attacchi
        //e nelle difese
        enableField();

        //per ogni carta presente nel vettore passato (carte attaccanti o carte attaccate)
        for (int i = 0; i < v; i++) {
            int j = 0;
            //scorro le carte presenti
            for (Card c : this.selectedCards) {
                //controllo quali sono state selezionate per le meccaniche
                if (vet[i] == j)
                    //e le disattivo
                    c.setEnabled(false);
                j++;
            }
        }
    }


    public void disableDeck() {
        //questo metodo rende non cliccabili tutte le carte non schierate
        for (Card c : this.deck)
            if(c != null)
                c.setEnabled(false);
    }


    public void disableField() {
        //questo metodo rende non cliccabili tutte le carte presenti nel campo schierate
        for (Card c : this.selectedCards)
            if(c != null)
                c.setEnabled(false);
    }


    public JPanel getDeckPanel() {
        return this.deckPanel;
    }


    public void setDeckPanel(JPanel deckPanel) {
        this.deckPanel = deckPanel;
    }


    public JPanel getField() {
        return this.field;
    }


    public void setField(JPanel field) {
        this.field = field;
    }


    public JPanel getFieldName() {
        return this.fieldName;
    }


    public void setFieldName(JPanel fieldName) {
        this.fieldName = fieldName;
    }


    public LinkedList<Card> getDeck() {
        return this.deck;
    }


    public void setDeck(LinkedList<Card> deck) {
        this.deck = deck;
    }


    public LinkedList<Card> getSelectedCards() {
        return this.selectedCards;
    }


    public void setSelectedCards(LinkedList<Card> selectedCards) {
        this.selectedCards = selectedCards;
    }


    public int getDeckWidth() {
        return this.deckWidth;
    }


    public void setDeckWidth(int deckWidth) {
        this.deckWidth = deckWidth;
    }


    public int getFieldWidth() {
        return this.fieldWidth;
    }


    public void setFieldWidth(int fieldWidth) {
        this.fieldWidth = fieldWidth;
    }


    public int getFieldHeight() {
        return this.fieldHeight;
    }


    public int getId() {
        return this.id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public void setFieldHeight(int fieldHeight) {
        this.fieldHeight = fieldHeight;
    }


    public int getCardWidth() {
        return this.cardWidth;
    }


    public void setCardWidth(int cardWidth) {
        this.cardWidth = cardWidth;
    }


    public int getCardHeight() {
        return this.cardHeight;
    }


    public void setCardHeight(int cardHeight) {
        this.cardHeight = cardHeight;
    }


    public boolean isTurn() {
        return this.turn;
    }


    public void setTurn(boolean turn) {
        this.turn = turn;
    }


    public Board getBoard() {
        return this.board;
    }


    public void setBoard(Board board) {
        this.board = board;
    }


    public int getPoints() {
        return this.points;
    }


    public void setPoints(int points) {
        this.points = points;
    }

}
