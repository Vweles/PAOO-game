package PaooGame.Characters.LVL3;

import PaooGame.Characters.Entity;
import PaooGame.Characters.Gru;
import PaooGame.Graphics.Assets;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.BufferedImage;

public class Bossfinal extends Gru {
    private int baniiLuiTaticu = 200;
    private boolean esteCamuflat = false;
    private int myMaxHp = 7000;

    public Bossfinal(int x, int y) {
        super("Campionul lui Emilian", 90, 70, 40, 120, 2, 2, 30, x, y, 2, false, true, false, "Impostor God", false, 120, 0);
        this.hp = 7000;
    }

    public void furaMeritele(Entity target) {
        this.rage += 15;
        this.att_dmg_hit += 5;
        int damage = this.att_dmg_hit + (this.inte * 2);
        target.hp -= damage;
    }

    public void binecuvantareaLuiEmilian() {
        this.esteCamuflat = true;
        this.speed += 2;
    }

    @Override
    public void specialAttack(Entity target) {
        int damage = (int)(this.att_dmg_hit * 3.0 + (this.rage * 2.0));
        target.hp -= damage;
    }

    public void platestePentruVictorie() {
        if (this.baniiLuiTaticu >= 50) {
            this.hp += 250;
            if (this.hp > myMaxHp) {
                this.hp = myMaxHp;
            }
            this.baniiLuiTaticu -= 50;
            this.enrage();
        }
    }

    @Override
    public void draw(Graphics g, int xOffset, int yOffset) {
        if (esteCamuflat) {
            g.setColor(new Color(0, 255, 150, 80));
            g.fillOval(x - xOffset - 10, y - yOffset - 10, 84, 84);
        }
        BufferedImage img = (Assets.lvl3bosss != null) ? Assets.lvl3bosss : Assets.boss;
        g.drawImage(img, x - xOffset, y - yOffset, 64, 64, null);
    }
}