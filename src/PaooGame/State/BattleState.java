package PaooGame.State;

import PaooGame.Characters.MC;
import PaooGame.KeyImput.InputTaste;
import java.awt.*;

public class BattleState extends State {
    private MC player;
    private InputTaste keyInput;

    private String[] mainCommands = {"ATTACK", "ITEMS", "FLEE"};
    private int currentCommand = 0;


    private int inputDelay = 0;

    public BattleState(InputTaste keyInput, MC player) {
        this.keyInput = keyInput;
        this.player = player;
    }

    @Override
    public void Update() {

        if(inputDelay > 0) inputDelay--;


        if (inputDelay == 0) {
            if (keyInput.up) {
                currentCommand = (currentCommand - 1 + mainCommands.length) % mainCommands.length;
                inputDelay = 15;
            }
            if (keyInput.down) {
                currentCommand = (currentCommand + 1) % mainCommands.length;
                inputDelay = 15;
            }
            if (keyInput.enter) {
                handleSelection();
                inputDelay = 20;
            }
        }
    }

    private void handleSelection() {
        switch (currentCommand) {
            case 0:
                System.out.println("Deschidem submeniul de atacuri......");

                break;
            case 1:
                System.out.println("Potiune folosita!");
                break;
            case 2:
                System.out.println("Ai fugit!");

                break;
        }
    }

    @Override
    public void Draw(Graphics g) {

        g.setColor(new Color(20, 20, 20));
        g.fillRect(0, 0, 800, 600);


        drawBattleUI(g);
    }

    private void drawBattleUI(Graphics g) {

        g.setColor(Color.WHITE);
        g.drawRect(30, 400, 740, 160);

        g.setFont(new Font("Monospaced", Font.BOLD, 28));

        for (int i = 0; i < mainCommands.length; i++) {
            if (i == currentCommand) {
                g.setColor(Color.RED);
                g.drawString("> " + mainCommands[i], 60, 450 + (i * 40));
            } else {
                g.setColor(Color.WHITE);
                g.drawString("  " + mainCommands[i], 60, 450 + (i * 40));
            }
        }


        g.setColor(Color.GRAY);
        g.fillRect(300, 100, 200, 250);
        g.setColor(Color.WHITE);
        g.drawRect(300, 100, 200, 250);
        g.drawString("ENEMY PNG", 330, 230);
    }
}