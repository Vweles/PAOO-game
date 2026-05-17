package PaooGame.State;

import PaooGame.Graphics.GameCamera;
import PaooGame.Map.Map;
import PaooGame.Characters.MC;
import PaooGame.KeyImput.InputTaste;
import PaooGame.Physics.CollisionChecker;
import PaooGame.Graphics.HUD;
import java.awt.Graphics;

public class PlayState extends State {
    private Map currentMap;
    private MC player;
    private GameCamera camera;
    private CollisionChecker checker;
    private HUD hud;

    // 1. ADĂUGĂ aceastâ variabilâ membrâ pentru a păstra referința
    private InputTaste keyInput;

    public PlayState(InputTaste keyInput, int width, int height) {
        // 2. SALVEAZĂ referința primită în variabila clasei
        this.keyInput = keyInput;

        camera = new GameCamera(width, height);

        currentMap = new Map(
                "/LVL1/layout1final.txt",
                "/LVL1/laye2final.txt"
        );

        checker = new CollisionChecker(currentMap);

        // Folosești parametrul sau variabila salvată, ambele funcționează aici
        player = new MC("Erou", 200, 200, keyInput);
        hud = new HUD(player);
    }

    @Override
    public void Update() {
        player.update(checker);
        camera.centerOnEntity(player);

        // 3. ACUM poți folosi keyInput aici pentru a schimba starea
        if (player.hp <= 0) {
            State.SetState(new GameOverState(keyInput));
        }
    }

    @Override
    public void Draw(Graphics g) {
        currentMap.Draw(g, camera);
        player.Draw(g, (int)camera.getxOffset(), (int)camera.getyOffset());
        hud.draw(g);
    }
}