package PaooGame.State;

import PaooGame.KeyImput.InputTaste;
import PaooGame.KeyImput.MouseManager;
import PaooGame.KeyImput.UIImageButton;
import PaooGame.Graphics.ImageLoader;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class MenuState extends State {
    private MouseManager mouseManager;
    private State cutsceneState;
    private BufferedImage background, uiSheet;
    private ArrayList<UIImageButton> buttons;

    public MenuState(InputTaste keyInput, MouseManager mouseManager, State cutsceneState) {
        this.mouseManager = mouseManager;
        this.cutsceneState = cutsceneState;
        buttons = new ArrayList<>();

        background = ImageLoader.LoadImage("/textures/MeniulPrincipal.png");
        uiSheet = ImageLoader.LoadImage("/textures/Butoanele.png");

        if (uiSheet != null) {
            // Calculam dimensiuni butoane (ajusteaza daca subimage nu e corect)
            int btnWidth = uiSheet.getWidth() / 2 - 40;
            int btnHeight = uiSheet.getHeight() / 3 - 30;

            // Buton START
            BufferedImage[] startImgs = {
                    uiSheet.getSubimage(20, 15, btnWidth, btnHeight),
                    uiSheet.getSubimage(uiSheet.getWidth() / 2 + 10, 15, btnWidth, btnHeight)
            };
            buttons.add(new UIImageButton(250, 200, 300, 80, startImgs, () -> {
                State.SetState(cutsceneState);
                this.mouseManager.leftPressed = false;
            }));

            // Buton EXIT
            BufferedImage[] exitImgs = {
                    uiSheet.getSubimage(20, 15 + (btnHeight + 30) * 2, btnWidth, btnHeight),
                    uiSheet.getSubimage(uiSheet.getWidth() / 2 + 10, 15 + (btnHeight + 30) * 2, btnWidth, btnHeight)
            };
            buttons.add(new UIImageButton(250, 400, 300, 80, exitImgs, () -> System.exit(0)));
        }
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
        for (UIImageButton btn : buttons) btn.Draw(g);
    }
}