package PaooGame.Graphics;

import PaooGame.Characters.MC;
import java.awt.*;

public class HUD {
    private MC player;

    // Dimensiunile barelor
    private final int BAR_WIDTH = 150;
    private final int BAR_HEIGHT = 15;

    public HUD(MC player) {
        this.player = player;
    }

    public void draw(Graphics g) {
        // 1. Fundal semi-transparent pentru lizibilitate
        g.setColor(new Color(20, 20, 20, 180));
        g.fillRect(10, 10, 280, 80);

        // Chenar fundal
        g.setColor(Color.WHITE);
        g.drawRect(10, 10, 280, 80);

        // Setare Font
        g.setFont(new Font("Monospaced", Font.BOLD, 14));

        // --- BARA DE HP ---
        g.setColor(Color.WHITE);
        g.drawString("HP: " + player.hp + "/" + player.maxHp, 20, 35);

        // Calcul lățime bară HP în funcție de procentajul curent
        int hpWidth = (int) (((double) player.hp / player.maxHp) * BAR_WIDTH);
        hpWidth = Math.max(0, Math.min(hpWidth, BAR_WIDTH)); // Ne asigurăm că nu depășește marginile

        g.setColor(Color.RED);
        g.fillRect(120, 22, hpWidth, BAR_HEIGHT);
        g.setColor(Color.WHITE);
        g.drawRect(120, 22, BAR_WIDTH, BAR_HEIGHT); // Chenar exterior bară HP

        // --- BARA DE MANA ---
        g.setColor(Color.WHITE);
        g.drawString("MP: " + player.mana + "/" + player.maxMana, 20, 65);

        // Calcul lățime bară Mana
        int manaWidth = (int) (((double) player.mana / player.maxMana) * BAR_WIDTH);
        manaWidth = Math.max(0, Math.min(manaWidth, BAR_WIDTH));

        g.setColor(new Color(0, 100, 255)); // Un albastru mai deschis
        g.fillRect(120, 52, manaWidth, BAR_HEIGHT);
        g.setColor(Color.WHITE);
        g.drawRect(120, 52, BAR_WIDTH, BAR_HEIGHT); // Chenar exterior bară Mana

        // Aici vei putea adăuga pe viitor statusul membrelor, lanterna, etc.
    }
}