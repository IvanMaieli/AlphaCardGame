import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.LinkedList;

public class Board extends JFrame {

    //pannello dei pulsanti
    private JPanel buttonField;

    //pannello del mazzo con le carte rimanenti
    private JPanel panelCards;

    //campo dove si vede il turno e il giocatore attivo
    private JPanel turnField;

    //label dove inserisco il turno
    private JLabel turnLabel;

    //pulsante per confermare l'attacco
    private JButton attackButton;

    //pulsante per schierare le carte
    private JButton positioningButton;

    //pulsante per cambiare il turno
    private JButton changeTurnButton;

    //mazzo di carte
    private LinkedList<Card> cards;

    //larghezza delle carte
    private int cardWidth;

    //altezza delle carte
    private int cardHeight;

    //numero di carte selezionate per l'attacco
    private int attOrderCont;

    //numero di carte selezionate per subire l'attacco
    private int defOrderCont;

    //ordine delle carte attaccanti
    private int[] attackOrder;

    //ordine delle carte attaccate
    private int[] defenseOrder;

    //giocatore 1
    private Player p1;

    //giocatore 2
    private Player p2;

    //colore carte comuni
    private final Color stdColorCard = new Color(42, 45, 52);

    //colore carte epiche
    private final Color epicColorCard = new Color(217, 114, 255);

    //colore carte leggendarie
    private final Color legendaryColorCard = new Color(250, 199, 72);

    //se la partita è in fase di schieramento o di attacco
    private boolean phasePositioning = true;

    //turno attuale
    private int actualTurn = 1;

    //giocatore attivo al momento
    private Player actualTurnPlayer;

    //giocatore in attesa al momento
    private Player actualWaitPlayer;

    //font della classe
    private Font buttonFont = new Font("Helvetica", Font.BOLD, 14);


    public Board() {
        //prendo le dimensioni dello schermo per rendere la GUI il più responsive possibile
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();

        int height = (int) (screenHeight - (screenHeight * 0.1));
        int width = (int) (screenWidth - (screenWidth * 0.11));

        int panelWidth = (int)(width - 20 - screenWidth / 100 * 0.8);
        int panelHeight = (height - 85) / 2;

        this.cardHeight = (panelHeight / 2) - 20;
        this.cardWidth = (int) (panelWidth - 80) / 7;


        //proprietà del JFrame
        this.setTitle("DeepSpace by Ivan Maieli");
        this.getContentPane().setBackground(this.stdColorCard);
        this.setBounds((screenWidth - width) / 2, (screenHeight - height) / 2, width, height - 25);
        this.setLayout(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        int buttonFieldHeight = 200;
        int buttonFieldWidth = 200;

        //proprietà del pannello dei pulsanti
        this.buttonField = new JPanel();
        this.buttonField.setBackground(new Color(170, 70, 1));
        this.buttonField.setBounds((int)((width - screenWidth / 100 * 0.8) / 7 * 5.5), (height - buttonFieldHeight - 45) / 2, buttonFieldWidth, buttonFieldHeight);
        this.buttonField.setBorder(BorderFactory.createLoweredBevelBorder());
        this.buttonField.setLayout(null);
        this.buttonField.setVisible(true);
        this.add(this.buttonField);


        //proprietà pulsante d'attacco
        this.attackButton = new JButton("ATTACCA");
        this.attackButton.setBackground(new Color(42, 45, 52));
        this.attackButton.setForeground(new Color(235, 212, 203));
        this.attackButton.setFont(buttonFont);
        this.attackButton.setBounds(10, 10, buttonFieldWidth - 20, (buttonFieldHeight - 40) / 3);
        this.attackButton.addActionListener(new BoardListener());
        this.attackButton.setFocusPainted(false);
        this.attackButton.setEnabled(false);
        this.buttonField.add(this.attackButton);
        this.attackButton.setVisible(true);

        //proprietà pulsante di chieramento
        this.positioningButton = new JButton("SCHIERA");
        this.positioningButton.setBackground(new Color(42, 45, 52));
        this.positioningButton.setForeground(new Color(235, 212, 203));
        this.positioningButton.setFont(this.buttonFont);
        this.positioningButton.setBounds(10, 20 + (buttonFieldHeight - 40) / 3, 180, (buttonFieldHeight - 40) / 3);
        this.positioningButton.addActionListener(new BoardListener());
        this.positioningButton.setFocusPainted(false);
        this.buttonField.add(this.positioningButton);
        this.positioningButton.setVisible(true);

        //proprietà pulsante di cambio turno
        this.changeTurnButton = new JButton("CAMBIA TURNO");
        this.changeTurnButton.setBackground(new Color(42, 45, 52));
        this.changeTurnButton.setForeground(new Color(235, 212, 203));
        this.changeTurnButton.setFont(this.buttonFont);
        this.changeTurnButton.setBounds(10, 30 + ((buttonFieldHeight - 40) / 3) * 2, buttonFieldWidth - 20, (buttonFieldHeight - 40) / 3);
        this.changeTurnButton.addActionListener(new BoardListener());
        this.changeTurnButton.setFocusPainted(false);
        this.changeTurnButton.setEnabled(false);
        this.buttonField.add(this.changeTurnButton);
        this.changeTurnButton.setVisible(true);

        //proprietà pannello delle statistiche
        this.turnField = new JPanel();
        this.turnField.setBackground(new Color(170, 70, 1));
        this.turnField.setBounds(this.buttonField.getX(),this.buttonField.getY() - (height / 16) - 10, this.buttonField.getWidth(), height / 16);
        this.turnField.setBorder(BorderFactory.createLoweredBevelBorder());
        this.turnField.setLayout(null);
        this.turnField.setVisible(true);
        this.add(this.turnField);

        //proprietà pannello del mazzo da cui pescare le carte
        this.panelCards = new JPanel();
        this.panelCards.setBorder(BorderFactory.createLoweredBevelBorder());
        this.panelCards.setBackground(new Color(85, 87, 93));
        this.panelCards.setBounds(20, this.getHeight() / 4 + 6 , cardWidth + 15, (this.getHeight() / 4) * 2 - 40);
        this.panelCards.setVisible(true);
        this.panelCards.setLayout(null);
        this.add(this.panelCards);

        //inizializzo i vettori dell'ordine di attacco e di difesa e azzero il numero di selezionati
        this.attackOrder = new int[3];
        this.attOrderCont = 0;
        this.defenseOrder = new int[3];
        this.defOrderCont = 0;

        //inizializzo a -1 l'ordine delle carte selezionate
        for (int i = 0; i < 3; i++) {
            attackOrder[i] = -1;
            defenseOrder[i] = -1;
        }

        //proprietà giocatore 1
        this.p1 = new Player(panelWidth, panelHeight, 1, new Color(85, 87, 93), false, this);
        this.p1.setBounds(10, 10, panelWidth, panelHeight);
        this.p1.setLayout(null);
        this.p1.setVisible(true);
        this.add(this.p1);

        //proprietà giocatore 2
        this.p2 = new Player(panelWidth, panelHeight, 2, new Color(85, 87, 93), true, this);
        this.p2.setBounds(10, panelHeight + 20, panelWidth, panelHeight);
        this.p2.setLayout(null);
        this.p2.setVisible(true);
        this.add(this.p2);

        //inizia il giocatore 1
        this.actualTurnPlayer = p1;
        this.actualWaitPlayer = p2;

        //modifico le proprietà del turno ora che ho il giocatore che inizia
        this.turnLabel = new JLabel("Turno: " + this.actualTurn + " / 16 - "
                + "Player " + this.actualTurnPlayer.getId());
        this.turnLabel.setFont(this.buttonFont);
        this.turnLabel.setForeground(new Color(235, 212, 203));
        this.turnLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.turnLabel.setBounds(0, 0,
                this.turnField.getWidth(), this.turnField.getHeight());
        this.turnLabel.setLayout(null);
        this.turnLabel.setVisible(true);
        this.turnField.add(this.turnLabel);

        //mescolo le carte e le assegno ai giocatori
        this.mix();

        //visualizzo il mazzo con le carte mancanti nello schermo
        printDeck();

        //parte la prima scelta delle carte
        chooseCards(p1, p2);

        this.repaint();
        this.setVisible(true);
    }


    public void updateTurnField() {
        //richiamo questo metodo quando devo aggiornare il turno graficamente
        this.turnLabel.setText("Turno: " + this.actualTurn + " / 16 - "
        + "Player " + this.actualTurnPlayer.getId());
    }


    private void chooseCards(Player p1, Player p2) {
        //2 giocatori invertono i turni
        p1.changeTurn();
        p2.changeTurn();
        //uno dei due diventa l'attuale e l'altro quello in attesa
        this.actualTurnPlayer = p1;
        this.actualWaitPlayer = p2;
        //aggiorno il turno
        this.updateTurnField();
        //metto quello in attesa in attesa
        p2.waitTurn();
        //e faccio scegliere le carte a quello attivo
        p1.chooseCards(this.cards);
    }


    private void mix() {
        this.cards = new LinkedList<>();

        //il mazzo è formato da 45 carte e lo riempio con questo schema
        for (int i = 0; i < 45; i++) {
            if (i < 1) this.cards.add(new Gigatron(i, this.cardWidth, this.cardHeight, this.legendaryColorCard));
            else if(i < 3) this.cards.add(new Nebula(i, this.cardWidth, this.cardHeight, this.legendaryColorCard));
            else if (i < 5) this.cards.add(new Nightmare(i, this.cardWidth, this.cardHeight, this.legendaryColorCard));
            else if (i < 9) this.cards.add(new RoboBruin(i, this.cardWidth, this.cardHeight, this.epicColorCard));
            else if (i < 12) this.cards.add(new Roborat(i, this.cardWidth, this.cardHeight, this.epicColorCard));
            else if (i < 14) this.cards.add(new QuantumGrade(i, this.cardWidth, this.cardHeight, this.epicColorCard));
            else if (i < 20) this.cards.add(new Horde(i, this.cardWidth, this.cardHeight, this.stdColorCard));
            else if (i < 30) this.cards.add(new ByteHowler(i, this.cardWidth, this.cardHeight, this.stdColorCard));
            else if (i < 38) this.cards.add(new TecnoPlatypus(i, this.cardWidth, this.cardHeight, this.stdColorCard));
            else this.cards.add(new Spaceman(i, this.cardWidth, this.cardHeight, this.stdColorCard));
        }

        //mescolo le  carte
        Collections.shuffle(this.cards);

        //creo un deck per ogni giocatore da riempire con le carte sorteggiate
        LinkedList<Card> deckP1 = new LinkedList<>();
        LinkedList<Card> deckP2 = new LinkedList<>();

        //sorteggio 14 carte e assegno quelle in posizioni pari al mazzo del giocatore 1
        //e le altre al giocatore 2
        for(int i = 0; i < 14; i++) {
            if (i % 2 == 0) deckP1.add(this.cards.removeFirst());
            else deckP2.add(this.cards.removeFirst());
        }

        //passo il mazzo sorteggiato a ciascuno dei due giocatori
        this.p1.giveCards(deckP1);
        this.p2.giveCards(deckP2);

        //ora che 14 carte sono state pescate dal mazzo, richiamo il metodo di visualizzazione
        //del mazzo rimanente, che avrà 14 carte in meno
        this.printDeck();

        repaint();
    }

    public void printDeck() {
        int i = 0;

        //per ogni carta nel mazzo
        for (Card c : this.cards) {
            //visualizzala nel JFrame con una y che cresce ad ogni iterazione (i)
            c.setBounds((int) ((this.panelCards.getWidth() - this.cardWidth) / 1.75), (this.panelCards.getHeight() - this.cardHeight + 180) / 2 - i, c.getCardWidth(), c.getCardHeight());
            c.setLayout(null);
            c.setVisible(true);
            c.coverCard();
            this.panelCards.add(c);
            i += 6;
        }
    }




    public void placeAttack(Card card, Player player) {
        //questo metodo si occupa di gestire la meccanica di attacco

        boolean ok = true;

        //se sono state selezionate meno di 3 carte d'attacco (e quindi è possibile selezionarne altre)
        if(this.attOrderCont < 3) {
            int i = 0;
            //l'algoritmo procede bene
            ok = true;
            //per ogni carta in quelle schierate dall'attaccante
            for (Card c : player.getSelectedCards()) {
                //disattivo la possibilità di cliccarle
                c.setEnabled(false);
                //se l'id delle carte corrispondono
                if (c.equals(card)) {
                    //per tutte le carte selezionate per l'attacco
                    for (int j = 0; j < this.attOrderCont; j++) {
                        //controllo se la carta è stata già selezionata per l'attacco
                        if (this.attackOrder[j] == i)
                            //se è cosi metto ok a false
                            ok = false;
                    }

                    //se la carta non è già stata selezionata
                    if (ok) {
                        //cambio il colore della carta per combinarle più semplicemente
                        c.changeBkg(this.attOrderCont);
                        //aggiorno i parametri di attacco
                        this.attackOrder[this.attOrderCont] = i;
                        this.attOrderCont++;
                    }
                }
                i++;
            }

            //se la carta è stata selezionata correttamente
            if (ok)
                //rendo cliccabili le carte del nemico per poterle attaccare
                this.actualWaitPlayer.enableField(this.defenseOrder, this.defOrderCont);
        }
    }


    public void placeDefense(Card card, Player player) {
        //se sono state selezionate meno di 3 carte da attaccare (e quindi è possibile selezionarne altre)
        if(this.defOrderCont < 3) {
            int i = 0;
            //scorro il mazzo delle carte schierate
            for (Card c : player.getSelectedCards()) {
                //le rendo non cliccabili
                c.setEnabled(false);
                //se gli id corrispondono
                if (c.equals(card)) {
                    //cambio il colore dello sfondo con lo stesso colore della carta che la attaccherà
                    c.changeBkg(this.defOrderCont);
                    //aggiorno i parametri delle carte da attaccare
                    this.defenseOrder[this.defOrderCont] = i;
                    this.defOrderCont++;
                }
                i++;
            }
            //toccherà di nuovo scegliere la carta attaccante e il tutto si ripete fino
            //al prossimo if
            this.actualTurnPlayer.enableField(this.attackOrder, this.attOrderCont);
        }
        //se sono state scelte 3 carte da attaccare, e quindi 3 attaccanti
        if (this.defOrderCont == 3) {
            //disabilito il pannello delle carte schierate dell'attaccante
            this.actualTurnPlayer.disableField();
            //attivo il tasto di attacco
            this.attackButton.setEnabled(true);
        }
    }


    private void attack() {
        //per il numero di carte scelte per attaccare (3)
        for (int i = 0; i < this.attackOrder.length; i++) {
            int j = 0;
            //creo l'attaccante, la vittima e il danno inflitto
            Card attacker;
            Card victim;
            int damage = 0;
            //per ogni carta presente in quelle schierate dal giocatore attivo attualmente
            for (Card c : this.actualTurnPlayer.getSelectedCards()) {
                //quando trovo l'iesimo attaccante
                if (this.attackOrder[i] == j) {
                    //assegno l'attaccante e prendo l'attributo dell'attacco e lo assegno al danno
                    attacker = c;
                    damage = attacker.getAttack();
                    //interrompo il ciclo avendo trovato la jesima corrispondenza
                    break;
                }
                j++;
            }
            j = 0;
            //svolgo lo stesso algoritmo per trovare l'iesimo attaccato
            for (Card c : this.actualWaitPlayer.getSelectedCards()) {
                if (this.defenseOrder[i] == j) {
                    //assegno la vittima
                    victim = c;
                    //sottraggo dalla vita della vittima l'attacco della carta attaccante
                    victim.setDefense(victim.getDefense() - damage);
                    break;
                }
                j++;
            }
        }
        //aggiorno la GUI delle carte poiché le vite sono cambiate
        this.actualWaitPlayer.updateCards(this.actualTurnPlayer);
        this.actualTurnPlayer.updateCards(this.actualWaitPlayer);

        //copro le carte e metto in attesa il giocatore che ha fatto l'attacco
        this.actualTurnPlayer.waitTurn();

        //aggiorno il posizionamento delle carte
        this.actualTurnPlayer.updateView();
        this.actualWaitPlayer.updateView();

        this.repaint();

        //ripristino i dati degli attacchi
        this.attOrderCont = 0;
        this.defOrderCont = 0;
        for (int i = 0; i < this.attackOrder.length; i++) {
            this.attackOrder[i] = -1;
            this.defenseOrder[i] = -1;
        }
    }

    public JPanel getButtonField() {
        return buttonField;
    }


    public void setButtonField(JPanel buttonField) {
        this.buttonField = buttonField;
    }


    public JPanel getPanelCards() {
        return panelCards;
    }


    public void setPanelCards(JPanel panelCards) {
        this.panelCards = panelCards;
    }


    public JPanel getTurnField() {
        return turnField;
    }


    public void setTurnField(JPanel turnField) {
        this.turnField = turnField;
    }


    public JLabel getTurnLabel() {
        return turnLabel;
    }


    public void setTurnLabel(JLabel turnLabel) {
        this.turnLabel = turnLabel;
    }


    public JButton getAttackButton() {
        return attackButton;
    }


    public void setAttackButton(JButton attackButton) {
        this.attackButton = attackButton;
    }


    public JButton getPositioningButton() {
        return positioningButton;
    }


    public void setPositioningButton(JButton positioningButton) {
        this.positioningButton = positioningButton;
    }


    public JButton getChangeTurnButton() {
        return changeTurnButton;
    }


    public void setChangeTurnButton(JButton changeTurnButton) {
        this.changeTurnButton = changeTurnButton;
    }


    public LinkedList<Card> getCards() {
        return cards;
    }


    public void setCards(LinkedList<Card> cards) {
        this.cards = cards;
    }


    public int getCardWidth() {
        return cardWidth;
    }


    public void setCardWidth(int cardWidth) {
        this.cardWidth = cardWidth;
    }


    public int getCardHeight() {
        return cardHeight;
    }


    public void setCardHeight(int cardHeight) {
        this.cardHeight = cardHeight;
    }


    public int getAttOrderCont() {
        return attOrderCont;
    }


    public void setAttOrderCont(int attOrderCont) {
        this.attOrderCont = attOrderCont;
    }


    public int getDefOrderCont() {
        return defOrderCont;
    }


    public void setDefOrderCont(int defOrderCont) {
        this.defOrderCont = defOrderCont;
    }


    public int[] getAttackOrder() {
        return attackOrder;
    }


    public void setAttackOrder(int[] attackOrder) {
        this.attackOrder = attackOrder;
    }


    public int[] getDefenseOrder() {
        return defenseOrder;
    }


    public void setDefenseOrder(int[] defenseOrder) {
        this.defenseOrder = defenseOrder;
    }


    public Player getP1() {
        return p1;
    }


    public void setP1(Player p1) {
        this.p1 = p1;
    }


    public Player getP2() {
        return p2;
    }


    public void setP2(Player p2) {
        this.p2 = p2;
    }


    public Color getStdColorCard() {
        return stdColorCard;
    }


    public Color getEpicColorCard() {
        return epicColorCard;
    }


    public Color getLegendaryColorCard() {
        return legendaryColorCard;
    }


    public boolean isPhasePositioning() {
        return phasePositioning;
    }


    public void setPhasePositioning(boolean phasePositioning) {
        this.phasePositioning = phasePositioning;
    }


    public int getActualTurn() {
        return actualTurn;
    }


    public void setActualTurn(int actualTurn) {
        this.actualTurn = actualTurn;
    }


    public Player getActualTurnPlayer() {
        return actualTurnPlayer;
    }


    public void setActualTurnPlayer(Player actualTurnPlayer) {
        this.actualTurnPlayer = actualTurnPlayer;
    }


    public Player getActualWaitPlayer() {
        return actualWaitPlayer;
    }


    public void setActualWaitPlayer(Player actualWaitPlayer) {
        this.actualWaitPlayer = actualWaitPlayer;
    }


    public class BoardListener implements ActionListener {
        //classe d'ascolto che implementa l'interfaccia ActionListener per il click di un pulsante
        @Override
        public void actionPerformed(ActionEvent e) {
            //se clicco il pulsante di cambio turno
            if (e.getSource() == changeTurnButton) {
                //e sono nei primi due turni (dove entrambi posizionano le carte)
                if (actualTurn < 3) {
                    //incremento il turno
                    actualTurn++;
                    //faccio scegliere le carte al giocatore che prima era in attesa
                    //e lo stato dei player viene invertito
                    chooseCards(actualWaitPlayer, actualTurnPlayer);
                    //attivo i pulsanti di schieramento e disattivo quello di cambio turno
                    positioningButton.setEnabled(true);
                    changeTurnButton.setEnabled(false);
                } else { //altrimenti se siamo in piena partita, quindi con l'alternarsi di attacco
                         //e schieramento
                    //se il giocatore ha schierato 3 carte
                    if (actualTurnPlayer.checkSelectedCards()) {
                        //incremento sempre il turno
                        actualTurn++;
                        //disabilito il pulsante del cambio turno
                        changeTurnButton.setEnabled(false);
                        //abilito il pulsante dello schieramento
                        positioningButton.setEnabled(true);
                        //disabilito il mazzo delle non schierate del giocatore che ha schierato
                        //le carte
                        actualTurnPlayer.disableDeck();
                        //non siamo più in fase di posizionamento ma di attacco
                        phasePositioning = !phasePositioning;
                        //tocca all'avversario scegliere le carte con cui attaccare
                        chooseCards(actualWaitPlayer, actualTurnPlayer);
                    }
                }

                //appena terminata la partita il "cambia turno" diventa termina
                if(changeTurnButton.getText().equals("TERMINA"))
                    System.exit(1);
            }

            //se viene premuto il pulsante di schieramento
            if (e.getSource() == positioningButton) {
                //se sono state selezionate 3 carte
                if (actualTurnPlayer.checkSelectedCards()) {
                    //se siamo fuori dai primi 2 turni
                    if (actualTurn > 2) {
                        //la fase di posizionamento diventa di attacco
                        phasePositioning = !phasePositioning;
                        //il mazzo delle non schierate dell'attaccante viene disabilitato
                        actualTurnPlayer.disableDeck();
                        //le non schierate vengono coperte
                        actualTurnPlayer.waitTurn();
                        //viene disabilitato il pulsante di schieramento
                        positioningButton.setEnabled(false);
                    } else { //altrimenti se siamo dentro i primi 2 turni
                        //la carte non schierate del giocatore attivo vengono coperte
                        actualTurnPlayer.waitTurn();
                        //quelle schierate non possono essere cliccate
                        actualTurnPlayer.disableField();
                        //quelle non schierate non possono essere cliccate
                        actualTurnPlayer.disableDeck();
                        //il cambio turno si attiva
                        changeTurnButton.setEnabled(true);
                        //lo schieramento si disattiva
                        positioningButton.setEnabled(false);
                    }
                }
            }

            //se viene premuto il pulsante di attacco
            if (e.getSource() == attackButton) {
                //se il turno è l'ultimo
                if (actualTurn == 16) {
                    //avvia la procedura di chiusura
                    attack();
                    attackButton.setEnabled(false);
                    //il tasto cambia turno diventa termina
                    changeTurnButton.setText("TERMINA");
                    changeTurnButton.setEnabled(true);
                    //se vince il giocatore 1
                    if (p1.getPoints() > p2.getPoints())
                        JOptionPane.showMessageDialog(null, "Ha vinto il Player " + p1.getId());
                    //altrimenti se vince il giocatore 2
                    else if (p1.getPoints() < p2.getPoints())
                        JOptionPane.showMessageDialog(null, "Ha vinto il Player " + p2.getId());
                    //se finisce in pareggio
                    else JOptionPane.showMessageDialog(null, "La partita è finita in pareggio");

                } else { //altrimenti se ci troviamo in un turno qualsiasi
                    //infliggo l'attacco
                    attack();
                    //disattivo il pulsante d'attacco
                    attackButton.setEnabled(false);
                    //il cambio turno si attiva
                    changeTurnButton.setEnabled(true);
                }
            }
        }
    }
}
