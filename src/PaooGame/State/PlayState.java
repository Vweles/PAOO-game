package PaooGame.State;

import PaooGame.Graphics.GameCamera;
import PaooGame.Characters.MC;
import PaooGame.KeyImput.InputTaste;
import PaooGame.Map.Map;
import PaooGame.Physics.CollisionChecker;
import PaooGame.Tiles.Tile;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class PlayState extends State {
    private MC player;
    private GameCamera gameCamera;
    private CollisionChecker checker;
    private InputTaste keyInput;
    private Map curentMap;

    public PlayState(InputTaste keyInput, int wndWidth, int wndHeight) {
        this.keyInput = keyInput;
        this.gameCamera = new GameCamera(wndWidth, wndHeight);
        // Setăm un zoom (de exemplu 1.5 pentru a vedea harta mai aproape)
        this.gameCamera.setZoom(1.0f);

        this.curentMap = new Map("/LVL1/layout1final.txt");
        this.player = new MC("Erou", 100, 100, keyInput); // Spawn la coordonate reale, nu indici
        this.checker = new CollisionChecker(curentMap);
    }

    @Override
    public void Update() {
        if (player != null) {
            player.update(checker);
            gameCamera.centerOnEntity(player);

            int worldWidth = curentMap.getWidth() * Tile.TILE_WIDTH;
            int worldHeight = curentMap.getHeight() * Tile.TILE_HEIGHT;

            gameCamera.checkBlankSpace(worldWidth, worldHeight);
        }
    }

    @Override
    public void Draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform oldTransform = g2d.getTransform();

        // ORDINEA CORECTA PENTRU CAMERA:
        // 1. Aplicăm zoom-ul
        g2d.scale(gameCamera.getZoom(), gameCamera.getZoom());
        // 2. Translatăm harta în sens invers camerei
        g2d.translate(-gameCamera.getxOffset(), -gameCamera.getyOffset());

        // Desenăm harta (trimitem camera pentru culling)
        if (curentMap != null) {
            curentMap.Draw(g2d, gameCamera);
        }

        // Desenăm jucătorul
        if (player != null) {
            player.draw(g2d);
        }

        // Resetăm transformarea ca să nu afectăm alte elemente (ex: mouse, UI)
        g2d.setTransform(oldTransform);
    }
}