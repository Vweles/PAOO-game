package PaooGame.Characters;

public class MinionBANANA extends Inamic {

    public int magicPower;
    public MinionBANANA(String NUME, int STR, int INTE, int AGI, int THO, int LIMBS_n, int LIMBS_at, int LVL, int x, int y, int speed, boolean friend, boolean fallow, boolean roam, String job, boolean interact, int agresivitate, int magicPower) {
        super(NUME, STR, INTE, AGI, THO, LIMBS_n, LIMBS_at, LVL, x, y, speed, friend, fallow, roam, job, interact, agresivitate);
        this.magicPower = magicPower;
    }

    public void castSpell(Entity target) {
        int damage = this.spell_dmg + magicPower;
        target.hp -= damage;
        this.mana -= 10;
        System.out.println(this.nume + " a lansat un blestem de " + damage + " DMG!");
    }
}