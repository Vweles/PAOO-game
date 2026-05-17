package PaooGame.State;

import PaooGame.KeyImput.InputTaste;
import java.awt.*;

public class GameOverState extends State {
    private InputTaste keyInput;
    private String[] options = {"RESTART", "EXIT TO MENU", "QUIT GAME"};
    private int currentOption = 0;
    private int inputDelay = 0;

    public GameOverState(InputTaste keyInput) {
        this.keyInput = keyInput;
    }

    @Override
    public void Update() {
        if (inputDelay > 0) inputDelay--;

        if (inputDelay == 0) {
            if (keyInput.up) {
                currentOption = (currentOption - 1 + options.length) % options.length;
                inputDelay = 15;
            }
            if (keyInput.down) {
                currentOption = (currentOption + 1) % options.length;
                inputDelay = 15;
            }
            if (keyInput.isEnterPressed()) {
                handleSelection();
            }
        }
    }

    private void handleSelection() {
        switch (currentOption) {
            case 0:
                // Restart: Reinițializăm PlayState-ul (va trebui să ai o referință sau să creezi unul nou în Game)
                // Pentru simplitate, aici poți trimite utilizatorul înapoi la începutul jocului
                System.out.println("Restarting game...");
                // State.SetState(new PlayState(...)); // Ai nevoie de acces la parametrii de lățime/înălțime
                break;
            case 1:
                // Întoarcere la meniu (trebuie să te asiguri că MenuState e accesibil)
                System.out.println("Returning to menu...");
                break;
            case 2:
                System.exit(0);
                break;
        }
    }

    @Override
    public void Draw(Graphics g) {
        // Fundal negru cinematic
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 800, 600);

        // Text principal
        g.setColor(new Color(150, 0, 0)); // Roșu închis/sângeriu
        g.setFont(new Font("Times New Roman", Font.BOLD, 85));
        g.drawString("YOU PERISHED", 100, 200);

        // Opțiuni meniu
        g.setFont(new Font("Monospaced", Font.PLAIN, 28));
        for (int i = 0; i < options.length; i++) {
            if (i == currentOption) {
                g.setColor(Color.RED);
                g.drawString("> " + options[i], 300, 350 + (i * 50));
            } else {
                g.setColor(Color.GRAY);
                g.drawString("  " + options[i], 300, 350 + (i * 50));
            }
        }
    }
}