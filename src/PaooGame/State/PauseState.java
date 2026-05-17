package PaooGame.State;

import PaooGame.KeyImput.InputTaste;
import PaooGame.KeyImput.MouseManager;
import PaooGame.Database.DatabaseManager;
import java.awt.*;

public class PauseState extends State {
    private PlayState backState;
    private InputTaste keyInput;
    private MouseManager mouseManager;

    public PauseState(PlayState backState, InputTaste keyInput, MouseManager mouseManager) {
        this.backState = backState;
        this.keyInput = keyInput;
        this.mouseManager = mouseManager;
    }

    @Override
    public void Update() {

        if (keyInput.esc) {
            keyInput.esc = false;
            State.SetState(backState);
            return;
        }


        if (keyInput.s_key) {

            int level = backState.getCurrentLevelNo();

            DatabaseManager.save(
                    backState.getPlayer().hp,
                    backState.getPlayer().mana,
                    backState.getPlayer().x,
                    backState.getPlayer().y,
                    level
            );
            keyInput.s_key = false;
        }


        if (keyInput.l_key) {
            DatabaseManager.load(backState);
            keyInput.l_key = false;
            State.SetState(backState); // Revenim în joc după load
        }
    }

    @Override
    public void Draw(Graphics g) {
        backState.Draw(g);

        g.setColor(new Color(0, 0, 0, 180));
        g.fillRect(0, 0, 800, 600);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 50));
        g.drawString("PAUZA", 310, 200);

        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.drawString("ESC - Resume", 330, 300);
        g.drawString("S - Salveaza Progres", 330, 350);
        g.drawString("L - Incarca Salvare", 330, 400);
    }
}