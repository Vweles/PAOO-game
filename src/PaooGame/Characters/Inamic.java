package PaooGame.Characters;

public class Inamic extends Npc {

    public int agresivitate;

    public Inamic(String NUME, int STR, int INTE, int AGI, int THO,
                 int LIMBS_n, int LIMBS_at, int LVL,int x ,int y ,int speed,
                 boolean friend, boolean fallow, boolean roam,
                 String job, boolean interact,
                 int agresivitate) {

        super(NUME, STR, INTE, AGI, THO, LIMBS_n, LIMBS_at, LVL,x,y,speed,
                friend, fallow, roam, job, interact);

        this.agresivitate = agresivitate;
    }

//aici tre sa schimbam ca inamicu ataca player sau npc nu entity nu uita ca crapa naibii codu


    public void attack(Entity target) {
        int damage = this.att_dmg_hit;
        target.hp -= damage;
        System.out.println(this.nume + " a palit " + target.nume + " cu " + damage + " damage!");
    }


    public boolean isAggressive() {
        return this.agresivitate > 5;
    }
    //public boolean urmareste(bazata pe npc cu alea roam fallow etc)
    // nush cum facem asta


    public void takeDamage(int dmg) {
        this.hp -= dmg;
        System.out.println(this.nume + " a inghitit " + dmg + " damage!");
    }

    public void attack(Entity target, int targetedLimb) {
        if (this.hp <= 0) return; // asta cred ca o sa dispara ca o sa reglez cand fac ala fight stateul

        int damage = this.att_dmg_hit;


        double chance = Math.random();
        if (chance > 0.8) { // 20% sansa  sa te taie la piciro sau mana idk poate pun mai micut ca e cam mult 20
            target.loseLimb(targetedLimb);
            System.out.println(this.nume + " a reusit sa reteze un membru lui " + target.nume + "!");
        }

        target.hp -= damage;
        System.out.println(this.nume + " ataca " + target.nume + " si da " + damage + " DMG.");
    }
}