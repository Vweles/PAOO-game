package PaooGame.Characters.LVL2;

import PaooGame.Characters.Entity;
import PaooGame.Characters.MC;
import PaooGame.Characters.MinionBANANA;
import PaooGame.Graphics.Assets;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Random;

public class mn extends MinionBANANA {

    private boolean hasStolenGlory = false;
    private int temporarySteal = 0;
    private int myMaxHp = 350;

    public mn(int x, int y) {
        super("Vicar of Borrowed Glory", 20, 40, 25, 30, 2, 1, 10, x, y, 3, false, true, true, "Soul Mimic", false, 80, 15);
        this.setHp(350);
    }

    public void stealGlory(Entity target) {
        if (!hasStolenGlory) {
            this.temporarySteal = target.getStr() / 4;

            this.setSpellDmg(this.getSpellDmg() + temporarySteal);
            this.hasStolenGlory = true;
            System.out.println(this.getNume() + " a furat gloria! Spell DMG crescut.");
        }
    }

    @Override
    public void castSpell(Entity target) {
        int targetMaxHp = 100 + 10 * target.getTho() + 5 * target.getStr();
        int bonusDamage = (target.getHp() > targetMaxHp / 2) ? 20 : 0;


        int totalDamage = this.getSpellDmg() + bonusDamage;

        target.setHp(target.getHp() - totalDamage);
    }

    public void humbleReset() {
        if (this.getHp() < 50) {
            this.hasStolenGlory = false;
            this.setSpeed(this.getSpeed() + 2);
        }
    }

    @Override
    public void update(PaooGame.Physics.CollisionChecker checker) {
        super.update(checker);
    }

    @Override
    public void draw(Graphics g, int xOffset, int yOffset) {
        if (hasStolenGlory) {
            g.setColor(new Color(255, 215, 0, 150));
            g.drawArc(this.getX() - xOffset, this.getY() - yOffset - 10, 30, 10, 0, 180);
        }

        BufferedImage img = (Assets.minion_lvl2 != null) ? Assets.minion_lvl2 : Assets.minion_full;
        if (img != null) {
            g.drawImage(img, this.getX() - xOffset, this.getY() - yOffset, 32, 32, null);
        }
    }

    @Override
    public void performTurn(MC player) {
        if (new Random().nextBoolean()) {
            this.stealGlory(player);
        } else {
            this.castSpell(player);
        }
        this.humbleReset();
    }
}