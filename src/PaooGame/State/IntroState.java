package PaooGame.State;

import PaooGame.KeyImput.InputTaste;
import java.awt.*;

public class IntroState extends State {
    private float alpha = 0;
    private int timer = 0;
    private boolean fadingIn = true;
    private InputTaste keyInput;
    private int wndWidth, wndHeight;
    private State nextState; // Va fi MenuState

    public IntroState(InputTaste keyInput, int wndWidth, int wndHeight, State nextState) {
        this.keyInput = keyInput;
        this.wndWidth = wndWidth;
        this.wndHeight = wndHeight;
        this.nextState = nextState;
    }

    @Override
    public void Update() {
        timer++;
        if (fadingIn) {
            alpha += 0.01f;
            if (alpha >= 1.0f) { alpha = 1.0f; if (timer > 120) fadingIn = false; }
        } else {
            alpha -= 0.02f;
            if (alpha <= 0) { alpha = 0; State.SetState(nextState); }
        }

        if(keyInput.up || keyInput.down || keyInput.enter) {
            State.SetState(nextState);
        }
    }

    @Override
    public void Draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, wndWidth, wndHeight);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Times New Roman", Font.BOLD, 80));
        g2d.drawString("=T-A-P=", wndWidth/2 - 150, wndHeight/2);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
    }
}