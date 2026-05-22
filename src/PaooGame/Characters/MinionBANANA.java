package PaooGame.Characters;

public class MinionBANANA extends Inamic {
    private int magicPower;

    public MinionBANANA(String nume, int str, int inte, int agi, int tho, int limbs_n, int limbs_at, int lvl, int x, int y, int speed,
                        boolean friend, boolean fallow, boolean roam, String job, boolean interact, int agresivitate, int magicPower) {
        super(nume, str, inte, agi, tho, limbs_n, limbs_at, lvl, x, y, speed, friend, fallow, roam, job, interact, agresivitate);
        this.magicPower = magicPower;
    }

    public void castSpell(Entity target) {
        int damage = this.getAttDmgHit() + magicPower;
        target.setHp(target.getHp() - damage);
    }
    public int getMagicPower() {
        return magicPower;
    }

    public void setMagicPower(int magicPower) {
        this.magicPower = magicPower;
    }
}