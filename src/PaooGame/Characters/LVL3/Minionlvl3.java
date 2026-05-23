package PaooGame.Characters.LVL3;

import PaooGame.Characters.Entity;
import PaooGame.Characters.MC;
import PaooGame.Characters.MinionBANANA;
import PaooGame.Graphics.Assets;
import PaooGame.Physics.CollisionChecker;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Minionlvl3 extends MinionBANANA {

    private boolean iluzieActiva = false;
    private int myMaxHp = 450;

    public Minionlvl3(int x, int y) {

        super("Sclavuletul lui Emilian", 25, 45, 30, 35, 2, 1, 15, x, y, 3, false, true, true, "Liar Mimic", false, 7, 25);
        this.setHp(450);
    }

    public void distragereMincinoasa(Entity target) {
        if (!iluzieActiva) {
            this.setMagicPower(this.getMagicPower() + 5);
            this.iluzieActiva = true;
        }
        int damage = this.getSpellDmg() + (this.getInte() / 2);
        target.setHp(target.getHp() - damage);
    }

    @Override
    public void castSpell(Entity target) {
        int totalDamage = this.getSpellDmg() + this.getMagicPower();
        target.setHp(target.getHp() - totalDamage);
        this.setMana(this.getMana() - 10);
    }

    public void lingusireZeiasca() {
        if (this.getHp() < 100) {
            this.iluzieActiva = false;
            this.setSpeed(this.getSpeed() + 1);
            this.setMana(this.getMana() + 20);
        }
    }

    @Override
    public void update(CollisionChecker checker) {

        super.update(checker);
    }

    @Override
    public void draw(Graphics g, int xOffset, int yOffset) {
        if (iluzieActiva) {
            g.setColor(new Color(138, 43, 226, 120));
            g.drawOval(this.getX() - xOffset - 5, this.getY() - yOffset - 5, 42, 42);
        }

        BufferedImage img = (Assets.lvl3minion != null) ? Assets.lvl3minion : Assets.minion_full;
        if (img != null) {
            g.drawImage(img, this.getX() - xOffset, this.getY() - yOffset, 32, 32, null);
        }
    }

    @Override
    public void performTurn(MC player) {
        if (new Random().nextBoolean()) {
            this.distragereMincinoasa(player);
        } else {
            this.castSpell(player);
        }
        this.lingusireZeiasca();
    }
}