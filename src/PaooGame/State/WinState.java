package PaooGame.State;

import PaooGame.KeyImput.InputTaste;
import PaooGame.KeyImput.MouseManager;
import PaooGame.Characters.MC;
import java.awt.*;

public class WinState extends State {
    private MC player;
    private MouseManager mouseManager;
    private Rectangle exitButton = new Rectangle(300, 420, 200, 40);

    public WinState(PlayState playState, MouseManager mouseManager) {
        this.player = playState.getPlayer();
        this.mouseManager = mouseManager;
    }

    @Override
    public void Update() {
        if (mouseManager.isLeftPressed()) {
            int mx = mouseManager.getMouseX();
            int my = mouseManager.getMouseY();

            if (exitButton.contains(mx, my)) {
                System.exit(0);
            }
            mouseManager.setLeftPressed(false);
        }
    }

    @Override
    public void Draw(Graphics g) {

        g.setColor(new Color(15, 15, 30));
        g.fillRect(0, 0, 800, 600);


        g.setColor(Color.YELLOW);
        g.setFont(new Font("Arial", Font.BOLD, 55));
        g.drawString("VICTORIE!", 260, 160);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 22));
        g.drawString("Felicitari! Ai terminat jocul.", 265, 220);


        g.setFont(new Font("Arial", Font.PLAIN, 16));
        g.setColor(Color.LIGHT_GRAY);
        g.drawString("Statistici Finale ale Eroului:", 300, 280);

        g.drawString("Strength (STR): " + player.getStr(), 320, 310);
        g.drawString("Intelligence (INT): " + player.getInte(), 320, 330);
        g.drawString("Agility (AGI): " + player.getAgi(), 320, 350);
        g.drawString("Toughness (THO): " + player.getTho(), 320, 370);

        g.setColor(Color.DARK_GRAY);
        g.fillRect(exitButton.x, exitButton.y, exitButton.width, exitButton.height);
        g.setColor(Color.YELLOW);
        g.drawRect(exitButton.x, exitButton.y, exitButton.width, exitButton.height);
        g.setColor(Color.WHITE);
        g.drawString("Iesire", exitButton.x + 75, exitButton.y + 25);
    }
}