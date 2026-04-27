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
                // o sa fie un submeniu cu casete in fiecare cateraw e cate o de aia un ability si o scurta descrierede gen
                // Fireball
                //7+(int/3) DMG
                // o sa fie cv de genu
                break;
            case 1:
                System.out.println("Potiune folosita!");
                break;
            case 2:
                System.out.println("Ai fugit!");
                // aici nush ce sa zic cum sa facem ca mie sa nu fie de ala dai run te intorci la state de explorare si dupa intri iar in state de battle
                // ca esti prea aproape
                // idk aici tre sa punem capu la gandire putin cu cum facem
                break;
        }
    }

    @Override
    public void Draw(Graphics g) {
        // aici la fundal am  facut asa la misto unu random  da am niste fundaluri de pe itch cred ca pun azi tot pe resurse si vedem care se potriveste
        g.setColor(new Color(20, 20, 20));
        g.fillRect(0, 0, 800, 600);
        // ba deci hai sa facem pact de fiecare data cand hardcodam cv il lasam asa basic si punem de asta comentariu si la final cand facem patching
        // facem niste variabile ca astea  o sa fie din meniu gen iti alegi rezolutia

        drawBattleUI(g);
    }

    private void drawBattleUI(Graphics g) {
       // asta e asa la misto ca am gasit niste asseturi misto pe itch da nush cum sa le fac sudura aici in cod
        g.setColor(Color.WHITE);
        g.drawRect(30, 400, 740, 160);
// ba deci am gasit un font asa de misto e asa gen horror si e gen vechi cu litere asa ghosty etc ba deci e misto rau da tre sa caut cum le pun
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

        // am pus asa un cv la misto sa vad daca merge
        g.setColor(Color.GRAY);
        g.fillRect(300, 100, 200, 250);
        g.setColor(Color.WHITE);
        g.drawRect(300, 100, 200, 250);
        g.drawString("ENEMY PNG", 330, 230);
    }
}