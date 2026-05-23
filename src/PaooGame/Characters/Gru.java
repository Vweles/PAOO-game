package PaooGame.Characters;

public class Gru extends Inamic {

    private int rage;

    public Gru(String NUME, int STR, int INTE, int AGI, int THO, int LIMBS_n, int LIMBS_at, int LVL, int x, int y, int speed, boolean friend, boolean fallow, boolean roam, String job, boolean interact, int agresivitate, int rage) {
        super(NUME, STR, INTE, AGI, THO, LIMBS_n, LIMBS_at, LVL, x, y, speed, friend, fallow, roam, job, interact, agresivitate);
        this.rage = rage;
    }

    public void specialAttack(Entity target) {

        int damage = this.getAttDmgHit() * 2 + this.rage;
        target.setHp(target.getHp() - damage);

        System.out.println("BOSSULICAAAAAAAAAAAA " + this.getNume() + " a dat SPECIALA " + damage + " damage!");
    }

    public void enrage() {
        this.rage = this.rage + 10;

        this.setAttDmgHit(this.getAttDmgHit() + 5);

        System.out.println(this.getNume() + " a intrat in RAGE MODE!");
    }

    public void regenerate() {

        this.setHp(this.getHp() + 20);
        System.out.println(this.getNume() + " s-a regenerat!");
    }


    public int getRage() {
        return rage;
    }

    public void setRage(int rage) {
        this.rage = rage;
    }
}