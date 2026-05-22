package PaooGame.Characters.LVL3;

import PaooGame.Characters.Entity;
import PaooGame.Characters.Gru;
import PaooGame.Characters.MC;
import PaooGame.Graphics.Assets;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Bossfinal extends Gru {
    private int baniiLuiTaticu = 200;
    private boolean esteCamuflat = false;
    private int myMaxHp = 7000;

    public Bossfinal(int x, int y) {
        super("Campionul lui Emilian", 90, 70, 40, 120, 2, 2, 30, x, y, 2, false, true, false, "Impostor God", false, 120, 0);
        this.setHp(7000);
    }

    public void furaMeritele(Entity target) {

        this.setRage(this.getRage() + 15);
        this.setAttDmgHit(this.getAttDmgHit() + 5);

        int damage = this.getAttDmgHit() + (this.getInte() * 2);
        target.setHp(target.getHp() - damage);
    }

    public void binecuvantareaLuiEmilian() {
        this.esteCamuflat = true;
        this.setSpeed(this.getSpeed() + 2);
    }

    @Override
    public void specialAttack(Entity target) {

        int damage = (int)(this.getAttDmgHit() * 3.0 + (this.getRage() * 2.0));
        target.setHp(target.getHp() - damage);
    }

    public void platestePentruVictorie() {
        if (this.baniiLuiTaticu >= 50) {
            this.setHp(this.getHp() + 250);
            if (this.getHp() > myMaxHp) {
                this.setHp(myMaxHp);
            }
            this.baniiLuiTaticu -= 50;
            this.enrage();
        }
    }

    @Override
    public void draw(Graphics g, int xOffset, int yOffset) {
        if (esteCamuflat) {
            g.setColor(new Color(0, 255, 150, 80));

            g.fillOval(this.getX() - xOffset - 10, this.getY() - yOffset - 10, 84, 84);
        }
        BufferedImage img = (Assets.lvl3bosss != null) ? Assets.lvl3bosss : Assets.boss;
        g.drawImage(img, this.getX() - xOffset, this.getY() - yOffset, 64, 64, null);
    }

    @Override
    public void performTurn(MC player) {
        int r = new Random().nextInt(4);
        if (r == 0) { this.furaMeritele(player); }
        else if (r == 1) { this.binecuvantareaLuiEmilian(); }
        else if (r == 2) { this.specialAttack(player); }
        else { this.platestePentruVictorie(); }
    }
}