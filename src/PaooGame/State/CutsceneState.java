package PaooGame.State;

import PaooGame.KeyImput.InputTaste;
import java.awt.*;
import java.io.File;

public class CutsceneState extends State {
    private PlayState playState;
    private InputTaste keyInput;
    private int stage = 0; // 0 = asteapta pornire v1, 1 = v1 pornit, 2 = asteapta v2, 3 = v2 pornit

    public CutsceneState(InputTaste keyInput, PlayState playState) {
        this.keyInput = keyInput;
        this.playState = playState;
    }

    private void openVideo(String filename) {
        try {
            File vFile = new File("res/Cutscene/" + filename);
            if (vFile.exists()) {
                Desktop.getDesktop().open(vFile);
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    @Override
    public void Update() {
        // Folosim metoda noua isEnterPressed() ca sa nu detectam o apasare lunga
        boolean enterHit = keyInput.isEnterPressed();

        switch (stage) {
            case 0: // Pornim primul video
                openVideo("intro0.mp4");
                stage = 1;
                break;

            case 1: // Asteptam ca userul sa vada video 1 si sa apese Enter
                if (enterHit) {
                    openVideo("intro.mp4");
                    stage = 2;
                }
                break;

            case 2: // Asteptam Enter dupa al doilea video pentru a intra in joc
                if (enterHit) {
                    State.SetState(playState);
                }
                break;
        }
    }

    @Override
    public void Draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 800, 600);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 18));

        if (stage == 1) {
            g.drawString("Se vizualizeaza: INTRO 1", 280, 280);
            g.drawString("Dupa final, apasa ENTER pentru INTRO 2", 220, 320);
        } else if (stage == 2) {
            g.drawString("Se vizualizeaza: INTRO 2", 280, 280);
            g.drawString("Dupa final, apasa ENTER pentru START JOC", 210, 320);
        }
    }
}