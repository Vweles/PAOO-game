package PaooGame.Characters.LVL2;

import PaooGame.Characters.Entity;
import PaooGame.Characters.Gru;
import PaooGame.Graphics.Assets;
import java.awt.Graphics;
import java.awt.Color;            // IMPORT ADAUGAT
import java.awt.image.BufferedImage; // IMPORT ADAUGAT

public class Bossss extends Gru {
    private int soulEnergy = 100;
    private boolean isAuraActive = false;
    private int myMaxHp = 5000;

    public Bossss(int x, int y) {
        super("Morbryx, Flacara Infernului", 80, 50, 30, 100, 2, 2, 20, x, y, 2, false, true, false, "Champion of Hell", false, 100, 0);
        this.hp = 5000;
    }

    public void infernoBlast(Entity target) {
        if (this.rage >= 20) {
            target.hp -= (this.str + this.rage) * 3;
            this.rage -= 10;
        } else {
            this.enrage();
        }
    }

    public void activateMagmaAura() {
        this.isAuraActive = true;
        this.speed += 1;
    }

    @Override
    public void specialAttack(Entity target) {
        target.hp -= (int)(this.att_dmg_hit * 2.5 + (this.rage * 1.5));
    }

    public void soulSacrifice() {
        if (soulEnergy > 0) {
            this.hp += 150;
            this.soulEnergy -= 20;
            this.rage += 15;
        }
    }

    @Override
    public void draw(Graphics g, int xOffset, int yOffset) {
        if (isAuraActive) {
            g.setColor(new Color(255, 69, 0, 100));
            g.fillOval(x - xOffset - 20, y - yOffset - 20, 100, 100);
        }
        BufferedImage img = (Assets.boss_lvl2 != null) ? Assets.boss_lvl2 : Assets.boss;
        g.drawImage(img, x - xOffset, y - yOffset, 64, 64, null);
    }
}