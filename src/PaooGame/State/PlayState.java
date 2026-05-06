package PaooGame.State;

import PaooGame.Graphics.GameCamera;
import PaooGame.Map.Map;
import PaooGame.Characters.MC;
import PaooGame.KeyImput.InputTaste;
import PaooGame.Physics.CollisionChecker;
import java.awt.Graphics;

public class PlayState extends State {
    private Map currentMap;
    private MC player;
    private GameCamera camera;
    private CollisionChecker checker;

    public PlayState(InputTaste keyInput, int width, int height) {
        camera = new GameCamera(width, height);

        currentMap = new Map(
                "/LVL1/layout1final.txt",
                "/LVL1/laye2final.txt"
        );

        checker = new CollisionChecker(currentMap);

        player = new MC("Erou", 200, 200, keyInput);
    }

    @Override
    public void Update() {
        // REPARAT: Am schimbat din Update(...) in update(...) cu 'u' mic
        player.update(checker);
        camera.centerOnEntity(player);
    }

    @Override
    public void Draw(Graphics g) {
        currentMap.Draw(g, camera);
        // Aici ramane player.Draw (cu D mare) pentru ca asa am definit-o in MC
        player.Draw(g, (int)camera.getxOffset(), (int)camera.getyOffset());
    }
}