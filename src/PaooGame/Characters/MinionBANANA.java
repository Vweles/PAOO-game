package PaooGame.Characters;

public class MinionBANANA extends Inamic {

    public int magicPower;

    public MinionBANANA(String NUME, int STR, int INTE, int AGI, int THO,
                        int LIMBS_n, int LIMBS_at, int LVL,int x, int y, int speed,
                        boolean friend, boolean fallow, boolean roam,
                        String job, boolean interact,
                        int agresivitate, int magicPower) {

        super(NUME, STR, INTE, AGI, THO, LIMBS_n, LIMBS_at, LVL,x,y,speed,
                friend, fallow, roam, job, interact, agresivitate);

        this.magicPower = magicPower;
    }

    // SCHIMBA NU E ENTIRTY ATACA PLAYER /NPC
    public void castSpell(Entity target) {
        int damage = this.spell_dmg + magicPower;
        target.hp = target.hp -damage;
        this.mana = this.mana - 10;
        System.out.println(this.nume + " a dat spell de " + damage + " damage!");
    }


    public void buff() {
        this.att_dmg_hit =  this.att_dmg_hit +2;
        System.out.println(this.nume + " si-a crescut damage-ul baban!");
    }


    public void heal() {
        this.hp += 10;
        System.out.println(this.nume + " s-a vindecat adevarat!");
    }
    // daca mai vrei sa mai faca cv asta e sablonu de la minion  astea is alea basic si dupa pt fiecare
    // inamic o sa mai bagam gen cv special un atac acolo de poate sal faca doar inamicau ala
}