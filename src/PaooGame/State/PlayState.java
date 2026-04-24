package PaooGame.State;

import PaooGame.Graphics.GameCamera;
import PaooGame.Characters.MC;
import PaooGame.KeyImput.InputTaste;
import PaooGame.Physics.CollisionChecker;
import PaooGame.Tiles.Tile;
import java.awt.*;

public class PlayState extends State {
    private MC player;
    private GameCamera gameCamera;
    private CollisionChecker checker;
    private InputTaste keyInput;

    public PlayState(InputTaste keyInput, int wndWidth, int wndHeight) {
        this.keyInput = keyInput;
        // Spawn pe iarbă (48, 48)
        player = new MC("Erou", 48, 48, keyInput);
        gameCamera = new GameCamera(wndWidth, wndHeight);
        checker = new CollisionChecker();
    }

    @Override
    public void Update() {
        if (player != null) {
            player.update(checker);
            gameCamera.centerOnEntity(player);
            gameCamera.checkBlankSpace(800, 800);
        }
    }

    @Override
    public void Draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        java.awt.geom.AffineTransform oldTransform = g2d.getTransform();

        g2d.translate(-gameCamera.getxOffset(), -gameCamera.getyOffset());
        g2d.scale(gameCamera.getZoom(), gameCamera.getZoom());

        // Desenăm harta
        for (int rand = 0; rand < checker.testMap.length; rand++) {
            for (int col = 0; col < checker.testMap[0].length; col++) {
                int tileId = checker.testMap[rand][col];
                Tile.tiles[tileId].Draw(g2d, col * Tile.TILE_WIDTH, rand * Tile.TILE_HEIGHT);
            }
        }

        player.draw(g2d);
        g2d.setTransform(oldTransform);
    }
}