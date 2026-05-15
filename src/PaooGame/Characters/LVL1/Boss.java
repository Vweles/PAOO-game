package PaooGame.Characters.LVL1;

import PaooGame.Characters.Entity;
import PaooGame.Characters.Gru;

import java.util.Random;

public class Boss extends Gru {

    private int corruptionPower;
    private int poisonDamage;
    private boolean phaseTwo;
    private Random rand;

    public Boss(int x, int y) {

        super(
                "Morbryx, Herald of Rot",
                20,
                16,
                10,
                25,
                6,
                4,
                5,
                x,
                y,
                1,
                false,
                false,
                true,
                "Champion of the Rotten God",
                true,
                20,
                15
        );

        this.hp = 500;
        this.mana = 150;

        this.att_dmg_hit = 35;
        this.spell_dmg = 30;

        this.corruptionPower = 25;
        this.poisonDamage = 12;

        this.phaseTwo = false;

        this.rand = new Random();
    }


    public void rottenClaws(Entity target) {

        int damage = att_dmg_hit + corruptionPower;

        target.hp -= damage;

        System.out.println(this.nume +
                " sfasaie tinta cu gheare infectate!");
        System.out.println(target.nume +
                " primeste " + damage + " damage de putreziciune!");
    }


    public void toxicRain(Entity target) {

        if (this.mana < 25) {
            System.out.println(this.nume +
                    " incearca sa invoce ploaia toxica, dar nu are mana.");
            return;
        }

        this.mana -= 25;

        int damage = spell_dmg + corruptionPower + rand.nextInt(20);

        target.hp -= damage;

        System.out.println("Cerul devine negru...");
        System.out.println(this.nume +
                " invoca TOXIC RAIN!");
        System.out.println(target.nume +
                " este ars de acid pentru "
                + damage + " damage!");
    }


    public void spreadDecay(Entity target) {

        target.hp -= poisonDamage;

        System.out.println(this.nume +
                " raspandeste spori ai mortii!");
        System.out.println(target.nume +
                " pierde " + poisonDamage +
                " HP din cauza infectiei.");
    }


    public void checkPhase() {

        if (this.hp <= 250 && !phaseTwo) {

            phaseTwo = true;

            this.rage += 25;
            this.att_dmg_hit += 20;
            this.spell_dmg += 15;
            this.corruptionPower += 20;

            System.out.println("Pamantul incepe sa pulseze...");
            System.out.println(this.nume +
                    " este consumat complet de zeul putregaiului!");
            System.out.println("PHASE 2 ACTIVATED!");
        }
    }


    public void apocalypseOfRot(Entity target) {

        if (this.mana < 60) {

            System.out.println(this.nume +
                    " nu are suficienta mana pentru APOCALYPSE OF ROT!");
            return;
        }

        this.mana -= 60;

        int damage =
                (att_dmg_hit +
                        spell_dmg +
                        corruptionPower +
                        rage) * 2;

        target.hp -= damage;

        System.out.println("Aerul devine toxic...");
        System.out.println("Copacii se topesc...");
        System.out.println(this.nume +
                " dezlantuie APOCALYPSE OF ROT!");
        System.out.println(target.nume +
                " sufera " + damage +
                " DAMAGE FATAL!");
    }


    public void bossTurn(Entity target) {

        checkPhase();

        int choice = rand.nextInt(5);

        switch (choice) {

            case 0:
                rottenClaws(target);
                break;

            case 1:
                toxicRain(target);
                break;

            case 2:
                spreadDecay(target);
                break;

            case 3:
                specialAttack(target);
                break;

            case 4:
                if (phaseTwo) {
                    apocalypseOfRot(target);
                }
                else {
                    enrage();
                }
                break;
        }
    }
}