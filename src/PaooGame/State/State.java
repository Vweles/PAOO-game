package PaooGame.State;

import java.awt.Graphics;

public abstract class State {
    private static State currentState = null;

    public static void SetState(State state) {
        currentState = state;
    }

    public static State GetState() {
        return currentState;
    }

    public abstract void Update();
    public abstract void Draw(Graphics g);
}
