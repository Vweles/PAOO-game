package PaooGame.State;

import PaooGame.KeyImput.InputTaste;
import PaooGame.KeyImput.MouseManager;
import PaooGame.KeyImput.UIImageButton;
import PaooGame.Graphics.ImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class MenuState extends State {
    private InputTaste keyInput;
    private MouseManager mouseManager;
    private PlayState playState;

    private BufferedImage background;
    private BufferedImage uiSheet;

    private ArrayList<UIImageButton> buttons;

    public MenuState(InputTaste keyInput, MouseManager mouseManager, PlayState playState) {
        this.keyInput = keyInput;
        this.mouseManager = mouseManager;
        this.playState = playState;

        buttons = new ArrayList<>();

        background = ImageLoader.LoadImage("/textures/MeniulPrincipal.png");
        uiSheet = ImageLoader.LoadImage("/textures/Butoanele.png");

        if (uiSheet != null) {

            int btnWidth = uiSheet.getWidth() / 2 - 40;
            int btnHeight = uiSheet.getHeight() / 3 - 30;

            // --- START GAME (Randul 1) ---
            BufferedImage[] startImages = new BufferedImage[2];
            startImages[0] = uiSheet.getSubimage(20, 15, btnWidth, btnHeight); // Coloana stanga: Normal
            startImages[1] = uiSheet.getSubimage(uiSheet.getWidth() / 2 + 10, 15, btnWidth, btnHeight); // Coloana dreapta: Hover

            // --- LOAD GAME (Randul 2) ---
            BufferedImage[] loadImages = new BufferedImage[2];
            loadImages[0] = uiSheet.getSubimage(20, 15 + btnHeight + 30, btnWidth, btnHeight);
            loadImages[1] = uiSheet.getSubimage(uiSheet.getWidth() / 2 + 10, 15 + btnHeight + 30, btnWidth, btnHeight);

            // --- EXIT (Randul 3) ---
            BufferedImage[] exitImages = new BufferedImage[2];
            exitImages[0] = uiSheet.getSubimage(20, 15 + (btnHeight + 30) * 2, btnWidth, btnHeight);
            exitImages[1] = uiSheet.getSubimage(uiSheet.getWidth() / 2 + 10, 15 + (btnHeight + 30) * 2, btnWidth, btnHeight);

            // 1. Buton START
            buttons.add(new UIImageButton(250, 200, 300, 80, startImages, () -> {
                State.SetState(playState);
                mouseManager.leftPressed = false; // Resetam starea mouse-ului
            }));

            // 2. Buton LOAD
            buttons.add(new UIImageButton(250, 300, 300, 80, loadImages, () -> {
                System.out.println("INFO: Nu exista nicio amintire salvata in acest Pit...");
                mouseManager.leftPressed = false;
            }));

            // 3. Buton EXIT
            buttons.add(new UIImageButton(250, 400, 300, 80, exitImages, () -> {
                System.exit(0);
            }));
        }
    }

    @Override
    public void Update() {

        for (UIImageButton btn : buttons) {
            btn.Update(mouseManager.mouseX, mouseManager.mouseY, mouseManager.leftPressed);
        }

        if (keyInput.enter) {
            State.SetState(playState);
        }
    }

    @Override
    public void Draw(Graphics g) {
        if (background != null) {
            g.drawImage(background, 0, 0, 800, 600, null);
        }

        if (uiSheet != null) {
            for (UIImageButton btn : buttons) {
                btn.Draw(g);
            }
        } else {
            g.setColor(Color.RED);
            g.drawString("EROARE: Verifica resursele", 200, 300);
        }
    }
}