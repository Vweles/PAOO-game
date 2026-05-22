package PaooGame.State;

import PaooGame.KeyImput.MouseManager;
import PaooGame.KeyImput.UIImageButton;
import java.awt.*;
import java.util.ArrayList;

public class OptionsState extends State {
    private MouseManager mouseManager;
    private PlayState playState;
    private State backState;
    private ArrayList<UIImageButton> buttons;

    public OptionsState(MouseManager mouseManager, PlayState playState, State backState) {
        this.mouseManager = mouseManager;
        this.playState = playState;
        this.backState = backState;
        buttons = new ArrayList<>();


        buttons.add(new UIImageButton(250, 150, 300, 60, null, () -> {
            playState.loadGameData(
                    playState.getPlayer().getMaxHp(),
                    playState.getPlayer().getMaxMana(),
                    100, 100, 1
            );
        }));

        buttons.add(new UIImageButton(250, 250, 300, 60, null, () -> {
            playState.goToLevel2();
        }));

        buttons.add(new UIImageButton(250, 350, 300, 60, null, () -> {
            playState.goToLevel3();
        }));

        buttons.add(new UIImageButton(250, 480, 300, 60, null, () -> {
            State.SetState(backState);

            this.mouseManager.setLeftPressed(false);
        }));
    }

    @Override
    public void Update() {

        for (UIImageButton btn : buttons) {
            btn.Update(mouseManager.getMouseX(), mouseManager.getMouseY(), mouseManager.isLeftPressed());
        }
    }

    @Override
    public void Draw(Graphics g) {
        g.setColor(new Color(20, 20, 30));
        g.fillRect(0, 0, 800, 600);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString("SELECTEAZA NIVELUL", 180, 80);

        String[] labels = {"NIVELUL 1 (Inceput)", "NIVELUL 2", "NIVELUL 3 (Final)", "INAPOI"};
        g.setFont(new Font("Arial", Font.BOLD, 20));

        for (int i = 0; i < buttons.size(); i++) {
            UIImageButton btn = buttons.get(i);
            g.setColor(btn.isHovering() ? Color.YELLOW : Color.DARK_GRAY);


            int yPos = 150 + (i * (i == 3 ? 130 : 100));
            g.fillRect(250, yPos, 300, 60);

            g.setColor(Color.BLACK);
            g.drawString(labels[i], 300, yPos + 35);
        }
    }
}