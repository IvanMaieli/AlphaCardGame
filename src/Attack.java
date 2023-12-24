public class Attack {

    private int attacker;
    private int victim;

    public Attack(int attacker, int victim) {
        this.attacker = attacker;
        this.victim = victim;
    }

    public Attack() {}

    public int getAttacker() {
        return attacker;
    }

    public void setAttacker(int attacker) {
        this.attacker = attacker;
    }

    public int getVictim() {
        return victim;
    }

    public void setVictim(int victim) {
        this.victim = victim;
    }


}
