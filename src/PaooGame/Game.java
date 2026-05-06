package PaooGame;

import PaooGame.State.*;
import PaooGame.GameWindow.GameWindow;
import PaooGame.Graphics.Assets;
import PaooGame.KeyImput.InputTaste;
import PaooGame.KeyImput.MouseManager;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class Game implements Runnable {
    private GameWindow wnd;
    private boolean runState = false;
    private Thread gameThread;
    private BufferStrategy bs;
    private Graphics g;

    // Input
    private InputTaste keyInput = new InputTaste();
    private MouseManager mouseManager = new MouseManager();

    // Stari
    private State introState;
    private State menuState;
    private State cutsceneState;
    private State playState;

    public Game(String title, int width, int height) {
        wnd = new GameWindow(title, width, height);
    }

    private void InitGame() {
        wnd.BuildGameWindow();
        wnd.GetCanvas().addKeyListener(keyInput);
        wnd.GetCanvas().addMouseListener(mouseManager);
        wnd.GetCanvas().addMouseMotionListener(mouseManager);

        Assets.Init();

        // 1. Initializam PlayState (destinatia finala)
        playState = new PlayState(keyInput, wnd.GetWndWidth(), wnd.GetWndHeight());

        // 2. Initializam CutsceneState (care stie sa treaca la playState)
        cutsceneState = new CutsceneState(keyInput, (PlayState)playState);

        // 3. Initializam MenuState (care stie sa treaca la cutsceneState)
        menuState = new MenuState(keyInput, mouseManager, cutsceneState);

        // 4. Initializam IntroState (care stie sa treaca la menuState)
        introState = new IntroState(keyInput, wnd.GetWndWidth(), wnd.GetWndHeight(), menuState);

        // Start joc!
        State.SetState(introState);
    }

    public void run() {
        InitGame();
        long oldTime = System.nanoTime();
        final int fps = 60;
        final double timeFrame = 1000000000 / fps;

        while (runState) {
            long curentTime = System.nanoTime();
            if ((curentTime - oldTime) > timeFrame) {
                Update();
                Draw();
                oldTime = curentTime;
            }
        }
    }

    public synchronized void StartGame() {
        if (!runState) {
            runState = true;
            gameThread = new Thread(this);
            gameThread.start();
        }
    }

    public synchronized void StopGame() {
        if (runState) {
            runState = false;
            try { gameThread.join(); } catch (InterruptedException e) { e.printStackTrace(); }
        }
    }

    private void Update() {
        if (State.GetState() != null) State.GetState().Update();
    }

    private void Draw() {
        bs = wnd.GetCanvas().getBufferStrategy();
        if (bs == null) {
            wnd.GetCanvas().createBufferStrategy(3);
            return;
        }
        g = bs.getDrawGraphics();
        g.clearRect(0, 0, wnd.GetWndWidth(), wnd.GetWndHeight());

        if (State.GetState() != null) State.GetState().Draw(g);

        bs.show();
        g.dispose();
    }
}