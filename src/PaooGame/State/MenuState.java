package PaooGame.State;

import PaooGame.KeyImput.InputTaste;
import PaooGame.KeyImput.MouseManager;
import PaooGame.KeyImput.UIImageButton;
import PaooGame.Graphics.ImageLoader;
import PaooGame.Database.DatabaseManager;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class MenuState extends State {
    private MouseManager mouseManager;
    private State cutsceneState;
    private PlayState playState;
    private State optionsState;
    private BufferedImage background, uiSheet;
    private ArrayList<UIImageButton> buttons;

    public MenuState(InputTaste keyInput, MouseManager mouseManager, State cutsceneState, PlayState playState) {
        this.mouseManager = mouseManager;
        this.cutsceneState = cutsceneState;
        this.playState = playState;
        buttons = new ArrayList<>();

        background = ImageLoader.LoadImage("/textures/MeniulPrincipal.png");
        uiSheet = ImageLoader.LoadImage("/textures/Butoane2.png");

        if (uiSheet != null) {
            int btnWidth = uiSheet.getWidth() / 2 - 40;
            int btnHeight = uiSheet.getHeight() / 3 - 30;

            BufferedImage[] blankTemplate = {
                    uiSheet.getSubimage(20, 15, btnWidth, btnHeight),
                    uiSheet.getSubimage(uiSheet.getWidth() / 2 + 10, 15, btnWidth, btnHeight)
            };

            buttons.add(new UIImageButton(250, 150, 300, 80, blankTemplate, "START GAME", () -> {
                State.SetState(this.cutsceneState);
                this.mouseManager.leftPressed = false;
            }));


            buttons.add(new UIImageButton(250, 250, 300, 80, blankTemplate, "CONTINUE GAME", () -> {
                DatabaseManager.load(1, this.playState);
                State.SetState(this.playState);
                this.mouseManager.leftPressed = false;
            }));

            buttons.add(new UIImageButton(250, 350, 300, 80, blankTemplate, "OPTIONS", () -> {
                if (optionsState != null) State.SetState(optionsState);
                this.mouseManager.leftPressed = false;
            }));

            buttons.add(new UIImageButton(250, 450, 300, 80, blankTemplate, "EXIT", () -> System.exit(0)));
        }
    }

    public void setOptionsState(State optState) {
        this.optionsState = optState;
    }

    @Override
    public void Update() {
        for (UIImageButton btn : buttons) {
            btn.Update(mouseManager.mouseX, mouseManager.mouseY, mouseManager.leftPressed);
        }
    }

    @Override
    public void Draw(Graphics g) {
        if (background != null) g.drawImage(background, 0, 0, 800, 600, null);
        for (UIImageButton btn : buttons) {
            btn.Draw(g);
        }
    }
}