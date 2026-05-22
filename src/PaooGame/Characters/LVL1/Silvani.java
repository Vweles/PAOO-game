package PaooGame.Characters.LVL1;

import PaooGame.Characters.Entity;
import PaooGame.Characters.MC;
import PaooGame.Characters.MinionBANANA;
import java.util.Random;

public class Silvani extends MinionBANANA {

    private int naturePower;
    private int rootChance;
    private Random rand;

    public Silvani(int x, int y) {
        super(
                "Silvani", 8, 14, 10, 12, 4, 2, 1, x, y, 2,
                false, false, true, "Forest Shaman", true, 8, 15
        );

        this.naturePower = 20;
        this.rootChance = 35;
        this.rand = new Random();
        this.setHp(120);
        this.setMana(80); // Corectat
        this.setSpellDmg(18); // Corectat
    }

    public void thornBurst(Entity target) {
        if (this.getMana() < 10) {
            System.out.println(this.getNume() + " nu are suficienta mana!");
            return;
        }
        // Folosim getSpellDmg() si getMagicPower()
        int damage = this.getSpellDmg() + this.getMagicPower() + rand.nextInt(10);
        target.setHp(target.getHp() - damage);
        this.setMana(this.getMana() - 10);
        System.out.println(this.getNume() + " lanseaza Thorn Burst si provoaca " + damage + " damage!");
    }

    public void rootSnare(Entity target) {
        if (this.getMana() < 20) {
            System.out.println(this.getNume() + " nu are suficienta mana pentru Root Snare!");
            return;
        }
        this.setMana(this.getMana() - 20);
        int chance = rand.nextInt(100);
        if (chance < rootChance) {
            int damage = 10 + naturePower;
            target.setHp(target.getHp() - damage);
            System.out.println(this.getNume() + " invoca radacini din pamant!");
            System.out.println(target.getNume() + " este imobilizat si primeste " + damage + " damage!");
        } else {
            System.out.println(this.getNume() + " incearca sa prinda tinta in radacini, dar rateaza.");
        }
    }

    public void forestBlessing() {
        if (this.getMana() < 15) {
            System.out.println(this.getNume() + " nu are suficienta mana!");
            return;
        }
        int heal = 25 + naturePower / 2;
        this.setHp(this.getHp() + heal);
        this.setMana(this.getMana() - 15);
        System.out.println(this.getNume() + " absoarbe energia naturii si se vindeca cu " + heal + " HP!");
    }

    public void wrathOfTheForest(Entity target) {
        if (this.getMana() < 40) {
            System.out.println(this.getNume() + " nu are suficienta mana pentru Wrath Of The Forest!");
            return;
        }
        int damage = (this.getSpellDmg() + this.getMagicPower() + naturePower) * 2;
        target.setHp(target.getHp() - damage);
        this.setMana(this.getMana() - 40);
        System.out.println("Pamantul se cutremura...");
        System.out.println(this.getNume() + " dezlantuie Wrath Of The Forest!");
        System.out.println(target.getNume() + " primeste " + damage + " DAMAGE!");
    }
    @Override
    public void performTurn(MC player) {

        int r = new Random().nextInt(4);


        if (this.getHp() < 50 && this.getMana() >= 15) {
            this.forestBlessing();
        }

        else if (r == 0 && this.getMana() >= 40) {
            this.wrathOfTheForest(player);
        }
        else if (r == 1 && this.getMana() >= 20) {
            this.rootSnare(player);
        }
        else {

            this.thornBurst(player);
        }
    }
}