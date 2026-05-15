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
            State.SetState(backState); // Revenire în joc
        }
        // Aici vei adăuga logica pentru butoanele de Save/Load cu mouseManager
    }

    @Override
    public void Draw(Graphics g) {
        backState.Draw(g); // Desenăm jocul în fundal (înghețat)

        // Overlay negru semi-transparent
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, 0, 800, 600);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 50));
        g.drawString("PAUZA", 320, 200);

        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.drawString("1. Apasa ESC pentru Resume", 280, 300);
        g.drawString("2. Apasa S pentru SAVE", 280, 350);
        g.drawString("3. Apasa L pentru LOAD", 280, 400);
    }
}