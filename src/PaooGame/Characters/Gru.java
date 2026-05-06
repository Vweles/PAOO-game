package PaooGame.Characters;


public class Gru extends Inamic {

    public int rage;

    public Gru(String NUME, int STR, int INTE, int AGI, int THO, int LIMBS_n, int LIMBS_at, int LVL, int x, int y, int speed, boolean friend, boolean fallow, boolean roam, String job, boolean interact, int agresivitate, int rage) {
        super(NUME, STR, INTE, AGI, THO, LIMBS_n, LIMBS_at, LVL, x, y, speed, friend, fallow, roam, job, interact, agresivitate);
        this.rage = rage;

    }



    public void specialAttack(Entity target) {
        int damage = this.att_dmg_hit * 2 + rage;
        target.hp = target.hp - damage;
        System.out.println("BOSSULICAAAAAAAAAAAA " + this.nume + " a dat SPECIALA" + damage + " damage!");
    }
  

    public void enrage() {
        this.rage = this.rage +10;
        this.att_dmg_hit = this.att_dmg_hit +5;
        System.out.println(this.nume + " a intrat in RAGE MODE!");
    }


    public void regenerate() {
        this.hp = this.hp +20;
        System.out.println(this.nume + " s-a regenerat!");
    }
}