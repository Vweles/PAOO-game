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

    // Input-uri
    private InputTaste keyInput = new InputTaste();
    private MouseManager mouseManager = new MouseManager();

    // Obiectele pentru Stări
    private State introState;
    private State menuState;
    private State cutsceneState;
    private State playState;

    public Game(String title, int width, int height) {
        wnd = new GameWindow(title, width, height);
    }

    private void InitGame() {
        // 1. Construim fereastra
        wnd.BuildGameWindow();

        // 2. Setăm Input-urile pe Canvas și pe Frame
        wnd.GetCanvas().addKeyListener(keyInput);
        wnd.GetCanvas().addMouseListener(mouseManager);
        wnd.GetCanvas().addMouseMotionListener(mouseManager);
        wnd.GetFrame().addMouseListener(mouseManager);

        // 3. Încărcăm resursele (Aici se încarcă și PNG-urile cu inamici)
        Assets.Init();

        // --- LANȚUL DE STĂRI (Configurare de la coadă la cap) ---

        // 4. Inițializăm PlayState (Destinația finală)
        playState = new PlayState(keyInput, mouseManager, wnd.GetWndWidth(), wnd.GetWndHeight());

        // 5. Inițializăm CutsceneState (Primește playState pentru a ști unde să treacă după dialog)
        // Verifică dacă constructorul tău din CutsceneState acceptă aceste argumente
        cutsceneState = new CutsceneState(keyInput, (PlayState)playState);

        // 6. Inițializăm MenuState (Primește mouseManager pentru butoane și cutsceneState ca următoarea destinație)
        menuState = new MenuState(keyInput, mouseManager, cutsceneState);

        // 7. Inițializăm IntroState (Prima stare care apare)
        // Îi dăm menuState pentru ca după Intro (sau apăsare Enter) să treacă la Meniu
        introState = new IntroState(keyInput, wnd.GetWndWidth(), wnd.GetWndHeight(), menuState);

        // 8. Pornim jocul cu INTRO
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
        // Actualizează starea curentă (oricare ar fi ea: Intro, Meniu, etc.)
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

        // Desenează starea curentă
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