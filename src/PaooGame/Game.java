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


    private InputTaste keyInput = new InputTaste();
    private MouseManager mouseManager = new MouseManager();


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
        wnd.GetFrame().addMouseListener(mouseManager);

        Assets.Init();


        playState = new PlayState(keyInput, mouseManager, wnd.GetWndWidth(), wnd.GetWndHeight());
        cutsceneState = new CutsceneState(keyInput, (PlayState)playState);

        // Cream meniul principal FARA sa ii dam OptionsState inca (pentru a evita o bucla)
        menuState = new MenuState(keyInput, mouseManager, cutsceneState, (PlayState)playState);

        // Cream OptionsState si i-l transmitem Meniului Principal
        State optionsState = new OptionsState(mouseManager, (PlayState)playState, menuState);
        ((MenuState)menuState).setOptionsState(optionsState);

        introState = new IntroState(keyInput, wnd.GetWndWidth(), wnd.GetWndHeight(), menuState);
        State.SetState(introState);
    }

    @Override
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

    private void Update() {

        if (State.GetState() != null) {
            State.GetState().Update();
        }
    }

    private void Draw() {
        bs = wnd.GetCanvas().getBufferStrategy();
        if (bs == null) {
            wnd.GetCanvas().createBufferStrategy(3);
            return;
        }
        g = bs.getDrawGraphics();
        g.clearRect(0, 0, wnd.GetWndWidth(), wnd.GetWndHeight());


        if (State.GetState() != null) {
            State.GetState().Draw(g);
        }

        bs.show();
        g.dispose();
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
            try {
                gameThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}