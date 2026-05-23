package PaooGame.State;

import PaooGame.KeyImput.InputTaste;
import PaooGame.KeyImput.MouseManager;
import PaooGame.Database.DatabaseManager;
import java.awt.*;

public class LoseState extends State {
    private PlayState playState;
    private InputTaste keyInput;
    private MouseManager mouseManager;


    private Rectangle retryButton = new Rectangle(300, 320, 200, 40);
    private Rectangle menuButton = new Rectangle(300, 380, 200, 40);

    public LoseState(PlayState playState, InputTaste keyInput, MouseManager mouseManager) {
        this.playState = playState;
        this.keyInput = keyInput;
        this.mouseManager = mouseManager;


        keyInput.up = false; keyInput.down = false; keyInput.left = false; keyInput.right = false;
    }

    @Override
    public void Update() {
        if (mouseManager.isLeftPressed()) {
            int mx = mouseManager.getMouseX();
            int my = mouseManager.getMouseY();


            if (retryButton.contains(mx, my)) {
                DatabaseManager.load(1,playState);
                State.SetState(playState);
                mouseManager.setLeftPressed(false);
                return;
            }


            if (menuButton.contains(mx, my)) {

                System.exit(0);
                mouseManager.setLeftPressed(false);
                return;
            }

            mouseManager.setLeftPressed(false);
        }
    }

    @Override
    public void Draw(Graphics g) {

        g.setColor(new Color(20, 0, 0, 255));
        g.fillRect(0, 0, 800, 600);


        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 60));
        g.drawString("AI MURIT", 265, 200);


        g.setColor(Color.DARK_GRAY);
        g.fillRect(retryButton.x, retryButton.y, retryButton.width, retryButton.height);
        g.setColor(Color.RED);
        g.drawRect(retryButton.x, retryButton.y, retryButton.width, retryButton.height);
        g.setFont(new Font("Arial", Font.PLAIN, 18));
        g.drawString("Incarca Salvarea", retryButton.x + 30, retryButton.y + 26);


        g.setColor(Color.DARK_GRAY);
        g.fillRect(menuButton.x, menuButton.y, menuButton.width, menuButton.height);
        g.setColor(Color.WHITE);
        g.drawRect(menuButton.x, menuButton.y, menuButton.width, menuButton.height);
        g.drawString("Iesire Joc", menuButton.x + 60, menuButton.y + 26);
    }
}