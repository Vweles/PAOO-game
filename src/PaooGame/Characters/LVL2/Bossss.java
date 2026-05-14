package PaooGame.Characters.LVL2;

import PaooGame.Characters.Entity;
import PaooGame.Characters.Gru;
import PaooGame.Graphics.Assets;
import java.awt.Graphics;

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
            int fireDamage = (this.str + this.rage) * 3; // str cu minuscule
            target.hp -= fireDamage;
            this.rage -= 10;
            System.out.println("INFERNO BLAST!");
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
        int damage = (int)(this.att_dmg_hit * 2.5 + (this.rage * 1.5));
        target.hp -= damage;
    }

    public void soulSacrifice() {
        if (soulEnergy > 0) {
            this.hp += 150;
            this.soulEnergy -= 20;
            this.rage += 15;
        }
    }

    public void bossLogic(Entity player) {
        if (this.hp < this.myMaxHp * 0.3) {
            soulSacrifice();
        }

        if (this.rage > 40) {
            infernoBlast(player);
        } else {
            specialAttack(player);
        }

        if (this.hp < this.myMaxHp / 2 && !isAuraActive) {
            activateMagmaAura();
        }
    }

    @Override
    public void draw(Graphics g, int xOffset, int yOffset) {
        if (isAuraActive) {
            g.setColor(new java.awt.Color(255, 69, 0, 100));
            g.fillOval(x - xOffset - 20, y - yOffset - 20, 80, 80);
        }

        if (Assets.boss != null) {
            g.drawImage(Assets.boss, x - xOffset, y - yOffset, 64, 64, null);
        }
    }
}