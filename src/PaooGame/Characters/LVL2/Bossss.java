package PaooGame.Characters.LVL2;

import PaooGame.Characters.Entity;
import PaooGame.Characters.Gru;
import PaooGame.Characters.MC;
import PaooGame.Graphics.Assets;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Bossss extends Gru {
    private int soulEnergy = 100;
    private boolean isAuraActive = false;
    private int myMaxHp = 5000;

    public Bossss(int x, int y) {
        super("Morbryx, Flacara Infernului", 80, 50, 30, 100, 2, 2, 20, x, y, 2, false, true, false, "Champion of Hell", false, 100, 0);
        this.setHp(5000);
    }

    public void infernoBlast(Entity target) {
        // Accesăm rage prin getRage() din Gru și str prin getStr() din Entity
        if (this.getRage() >= 20) {
            int damage = (this.getStr() + this.getRage()) * 3;
            target.setHp(target.getHp() - damage);
            this.setRage(this.getRage() - 10);
        } else {
            this.enrage();
        }
    }

    public void activateMagmaAura() {
        this.isAuraActive = true;
        this.setSpeed(this.getSpeed() + 1);
    }

    @Override
    public void specialAttack(Entity target) {
        // Folosim getAttDmgHit() din Entity și getRage() din Gru
        int damage = (int)(this.getAttDmgHit() * 2.5 + (this.getRage() * 1.5));
        target.setHp(target.getHp() - damage);
    }

    public void soulSacrifice() {
        if (soulEnergy > 0) {
            this.setHp(this.getHp() + 150);
            this.soulEnergy -= 20;
            this.setRage(this.getRage() + 15);
        }
    }

    @Override
    public void draw(Graphics g, int xOffset, int yOffset) {
        if (isAuraActive) {
            g.setColor(new Color(255, 69, 0, 100));
            g.fillOval(this.getX() - xOffset - 20, this.getY() - yOffset - 20, 100, 100);
        }
        BufferedImage img = (Assets.boss_lvl2 != null) ? Assets.boss_lvl2 : Assets.boss;
        g.drawImage(img, this.getX() - xOffset, this.getY() - yOffset, 64, 64, null);
    }
    @Override
    public void performTurn(MC player) {
        int r = new Random().nextInt(4);
        if (r == 0) { this.infernoBlast(player); }
        else if (r == 1) { this.activateMagmaAura(); }
        else if (r == 2 && this.getHp() < 2000) { this.soulSacrifice(); }
        else { this.specialAttack(player); }
    }
}