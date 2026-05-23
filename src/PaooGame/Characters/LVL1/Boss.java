package PaooGame.Characters.LVL1;

import PaooGame.Characters.Entity;
import PaooGame.Characters.Gru;
import PaooGame.Characters.MC;

import java.util.Random;

public class Boss extends Gru {

    private int corruptionPower;
    private int poisonDamage;
    private boolean phaseTwo;
    private Random rand;

    public Boss(int x, int y) {
        super(
                "Morbryx, Herald of Rot",
                20, 16, 10, 25, 6, 4, 5, x, y, 1,
                false, false, true, "Champion of the Rotten God", true, 1, 15
        );

        this.setHp(500);
        this.setMana(150);
        this.setAttDmgHit(35);
        this.setSpellDmg(30);
        this.corruptionPower = 25;
        this.poisonDamage = 12;
        this.phaseTwo = false;
        this.rand = new Random();
    }

    public void rottenClaws(Entity target) {
        int damage = this.getAttDmgHit() + corruptionPower;
        target.setHp(target.getHp() - damage);

        System.out.println(this.getNume() + " sfasaie tinta cu gheare infectate!");
        System.out.println(target.getNume() + " primeste " + damage + " damage de putreziciune!");
    }

    public void toxicRain(Entity target) {
        if (this.getMana() < 25) {
            System.out.println(this.getNume() + " incearca sa invoce ploaia toxica, dar nu are mana.");
            return;
        }

        this.setMana(this.getMana() - 25);
        int damage = this.getSpellDmg() + corruptionPower + rand.nextInt(20);
        target.setHp(target.getHp() - damage);

        System.out.println("Cerul devine negru...");
        System.out.println(this.getNume() + " invoca TOXIC RAIN!");
        System.out.println(target.getNume() + " este ars de acid pentru " + damage + " damage!");
    }

    public void spreadDecay(Entity target) {
        target.setHp(target.getHp() - poisonDamage);
        System.out.println(this.getNume() + " raspandeste spori ai mortii!");
        System.out.println(target.getNume() + " pierde " + poisonDamage + " HP din cauza infectiei.");
    }

    public void checkPhase() {
        if (this.getHp() <= 250 && !phaseTwo) {
            phaseTwo = true;
            this.setRage(this.getRage() + 25);
            this.setAttDmgHit(this.getAttDmgHit() + 20);
            this.setSpellDmg(this.getSpellDmg() + 15);
            this.corruptionPower += 20;
            System.out.println("Pamantul incepe sa pulseze...");
            System.out.println(this.getNume() + " este consumat complet de zeul putregaiului!");
            System.out.println("PHASE 2 ACTIVATED!");
        }
    }

    public void apocalypseOfRot(Entity target) {
        if (this.getMana() < 60) {
            System.out.println(this.getNume() + " nu are suficienta mana pentru APOCALYPSE OF ROT!");
            return;
        }
        this.setMana(this.getMana() - 60);
        int damage = (this.getAttDmgHit() + this.getSpellDmg() + corruptionPower + this.getRage()) * 2;
        target.setHp(target.getHp() - damage);
        System.out.println("Aerul devine toxic...");
        System.out.println("Copacii se topesc...");
        System.out.println(this.getNume() + " dezlantuie APOCALYPSE OF ROT!");
        System.out.println(target.getNume() + " sufera " + damage + " DAMAGE FATAL!");
    }

    public void bossTurn(Entity target) {
        checkPhase();
        int choice = rand.nextInt(5);
        switch (choice) {
            case 0: rottenClaws(target); break;
            case 1: toxicRain(target); break;
            case 2: spreadDecay(target); break;
            case 3: specialAttack(target); break;
            case 4: if (phaseTwo) apocalypseOfRot(target); else enrage(); break;
        }
    }
    @Override
    public void performTurn(MC player) {

        this.bossTurn(player);
    }
}