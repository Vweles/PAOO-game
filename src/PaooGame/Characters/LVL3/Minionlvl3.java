package PaooGame.Characters.LVL3;

import PaooGame.Characters.Entity;
import PaooGame.Characters.MinionBANANA;
import PaooGame.Graphics.Assets;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.BufferedImage;

public class Minionlvl3 extends MinionBANANA {

    private boolean iluzieActiva = false;
    private int myMaxHp = 450;

    public Minionlvl3(int x, int y) {
        super("Sclavuletul lui Emilian", 25, 45, 30, 35, 2, 1, 15, x, y, 3, false, true, true, "Liar Mimic", false, 90, 25);
        this.hp = 450;
    }

    public void distragereMincinoasa(Entity target) {
        if (!iluzieActiva) {
            this.magicPower += 5;
            this.iluzieActiva = true;
        }
        int damage = this.spell_dmg + (this.inte / 2);
        target.hp -= damage;
    }

    @Override
    public void castSpell(Entity target) {
        int totalDamage = this.spell_dmg + this.magicPower;
        target.hp -= totalDamage;
        this.mana -= 10;
    }

    public void lingusireZeiasca() {
        if (this.hp < 100) {
            this.iluzieActiva = false;
            this.speed += 1;
            this.mana += 20;
        }
    }

    @Override
    public void update(PaooGame.Physics.CollisionChecker checker) {
        super.update(checker);
    }

    @Override
    public void draw(Graphics g, int xOffset, int yOffset) {
        if (iluzieActiva) {
            g.setColor(new Color(138, 43, 226, 120));
            g.drawOval(x - xOffset - 5, y - yOffset - 5, 42, 42);
        }

        BufferedImage img = (Assets.lvl3minion != null) ? Assets.lvl3minion : Assets.minion_full;
        if (img != null) {
            g.drawImage(img, x - xOffset, y - yOffset, 32, 32, null);
        }
    }
}