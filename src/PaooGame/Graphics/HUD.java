package PaooGame.Graphics;

import PaooGame.Characters.MC;
import java.awt.*;

public class HUD {
    private MC player;


    private final int BAR_WIDTH = 150;
    private final int BAR_HEIGHT = 15;

    public HUD(MC player) {
        this.player = player;
    }

    public void draw(Graphics g) {

        g.setColor(new Color(20, 20, 20, 180));
        g.fillRect(10, 10, 280, 80);


        g.setColor(Color.WHITE);
        g.drawRect(10, 10, 280, 80);


        g.setFont(new Font("Monospaced", Font.BOLD, 14));


        g.setColor(Color.WHITE);

        g.drawString("HP: " + player.getHp() + "/" + player.getMaxHp(), 20, 35);


        int hpWidth = (int) (((double) player.getHp() / player.getMaxHp()) * BAR_WIDTH);
        hpWidth = Math.max(0, Math.min(hpWidth, BAR_WIDTH));

        g.setColor(Color.RED);
        g.fillRect(120, 22, hpWidth, BAR_HEIGHT);
        g.setColor(Color.WHITE);
        g.drawRect(120, 22, BAR_WIDTH, BAR_HEIGHT);


        g.setColor(Color.WHITE);

        g.drawString("MP: " + player.getMana() + "/" + player.getMaxMana(), 20, 65);


        int manaWidth = (int) (((double) player.getMana() / player.getMaxMana()) * BAR_WIDTH);
        manaWidth = Math.max(0, Math.min(manaWidth, BAR_WIDTH));

        g.setColor(new Color(0, 100, 255));
        g.fillRect(120, 52, manaWidth, BAR_HEIGHT);
        g.setColor(Color.WHITE);
        g.drawRect(120, 52, BAR_WIDTH, BAR_HEIGHT);
    }
}