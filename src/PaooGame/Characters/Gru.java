package PaooGame.Characters;
//asta e boss Sablon ca gen minion Gru Despicable me
//Teai prins

public class Gru extends Inamic {

    public int rage;

    public Gru(String NUME, int STR, int INTE, int AGI, int THO, int LIMBS_n, int LIMBS_at, int LVL, int x, int y, int speed, boolean friend, boolean fallow, boolean roam, String job, boolean interact, int agresivitate, int rage) {
        super(NUME, STR, INTE, AGI, THO, LIMBS_n, LIMBS_at, LVL, x, y, speed, friend, fallow, roam, job, interact, agresivitate);
        this.rage = rage;

    }

    //la atac nai nevoie de animatie e png si cu cv efecte naiba vedem csf
    // adauga 3 bosi mostenitori pt lvl1 lvl2 lvl3 sa nu uiti
    // gen vezi ca acolo
    // NVM
    // auzi eu am gandir toti inamicii cu aia de au maini picioare etc si daca le tai nu  mai pot sa faca nush ce
    // lasama pe mn sa fac astea de inamici ca am deja ideile in cap tre doar sa le codez

    public void specialAttack(Entity target) {
        int damage = this.att_dmg_hit * 2 + rage;
        target.hp = target.hp - damage;
        System.out.println("BOSSULICAAAAAAAAAAAA " + this.nume + " a dat SPECIALA" + damage + " damage!");
    }
  
    //Schimba aici in cv normal

    // cauta cum sa aiba 2 faza 1 faza full  hp 2 faza juma hp
    public void enrage() {
        this.rage = this.rage +10;
        this.att_dmg_hit = this.att_dmg_hit +5;
        System.out.println(this.nume + " a intrat in RAGE MODE!");
    }

    //???? idk vedem daca da heal
    public void regenerate() {
        this.hp = this.hp +20;
        System.out.println(this.nume + " s-a regenerat!");
    }
}