package PaooGame.Characters.LVL1;

import PaooGame.Characters.Entity;
import PaooGame.Characters.MinionBANANA;

import java.util.Random;

public class Silvani extends MinionBANANA {

    private int naturePower;
    private int rootChance;
    private Random rand;

    public Silvani(int x, int y) {

        super(
                "Silvani",
                8,
                14,
                10,
                12,
                4,
                2,
                1,
                x,
                y,
                2,
                false,
                false,
                true,
                "Forest Shaman",
                true,
                8,
                15
        );

        this.naturePower = 20;
        this.rootChance = 35;
        this.rand = new Random();

        this.hp = 120;
        this.mana = 80;
        this.spell_dmg = 18;
    }


    public void thornBurst(Entity target) {

        if (this.mana < 10) {
            System.out.println(this.nume + " nu are suficienta mana!");
            return;
        }

        int damage = spell_dmg + magicPower + rand.nextInt(10);

        target.hp -= damage;
        this.mana -= 10;

        System.out.println(this.nume +
                " lanseaza Thorn Burst si provoaca "
                + damage + " damage!");
    }


    public void rootSnare(Entity target) {

        if (this.mana < 20) {
            System.out.println(this.nume + " nu are suficienta mana pentru Root Snare!");
            return;
        }

        this.mana -= 20;

        int chance = rand.nextInt(100);

        if (chance < rootChance) {

            int damage = 10 + naturePower;

            target.hp -= damage;

            System.out.println(this.nume +
                    " invoca radacini din pamant!");
            System.out.println(target.nume +
                    " este imobilizat si primeste "
                    + damage + " damage!");
        }
        else {
            System.out.println(this.nume +
                    " incearca sa prinda tinta in radacini, dar rateaza.");
        }
    }


    public void forestBlessing() {

        if (this.mana < 15) {
            System.out.println(this.nume + " nu are suficienta mana!");
            return;
        }

        int heal = 25 + naturePower / 2;

        this.hp += heal;
        this.mana -= 15;

        System.out.println(this.nume +
                " absoarbe energia naturii si se vindeca cu "
                + heal + " HP!");
    }


    public void wrathOfTheForest(Entity target) {

        if (this.mana < 40) {
            System.out.println(this.nume +
                    " nu are suficienta mana pentru Wrath Of The Forest!");
            return;
        }

        int damage = (spell_dmg + magicPower + naturePower) * 2;

        target.hp -= damage;
        this.mana -= 40;

        System.out.println("Pamantul se cutremura...");
        System.out.println(this.nume +
                " dezlantuie Wrath Of The Forest!");
        System.out.println(target.nume +
                " primeste " + damage + " DAMAGE!");
    }
}