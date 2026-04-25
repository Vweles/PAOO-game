package PaooGame.State;

import PaooGame.Graphics.GameCamera;
import PaooGame.Characters.MC;
import PaooGame.KeyImput.InputTaste;
import PaooGame.Map.Map;
import PaooGame.Physics.CollisionChecker;
import PaooGame.Tiles.Tile;
import java.awt.*;

public class PlayState extends State {
    private MC player;
    private GameCamera gameCamera;
    private CollisionChecker checker;
    private InputTaste keyInput;
    private Map curentMap;

    public PlayState(InputTaste keyInput, int wndWidth, int wndHeight) {
        this.keyInput = keyInput;
        this.gameCamera = new GameCamera(wndWidth,wndHeight);
        this.curentMap = new Map("/LVL1/MAP1.txt");
        this.player = new MC("Erou", 50,50,keyInput);
        this.checker = new CollisionChecker(curentMap);

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


        if (curentMap != null) {
            curentMap.Draw(g2d);
        }


        if (player != null) {
            player.draw(g2d);
        }

        g2d.setTransform(oldTransform);
    }
}