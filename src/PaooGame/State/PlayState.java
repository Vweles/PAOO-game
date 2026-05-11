package PaooGame.State;

import PaooGame.Characters.Entity;
import PaooGame.Characters.MC;
import PaooGame.KeyImput.InputTaste;
import PaooGame.KeyImput.MouseManager; // ################# SEMN: IMPORT NOU
import PaooGame.Physics.CollisionChecker;
import PaooGame.Graphics.GameCamera;
import PaooGame.Map.Map;
import java.awt.Graphics;
import java.awt.Rectangle;

public class PlayState extends State {
    private Map currentMap;
    private MC player;
    private GameCamera camera;
    private CollisionChecker checker;
    private InputTaste keyInput;
    private MouseManager mouseManager; // ################# SEMN: VARIABILA NOUA

    public PlayState(InputTaste keyInput, MouseManager mouseManager, int width, int height) {
        this.keyInput = keyInput;
        this.mouseManager = mouseManager; // Salvezi referinta
        camera = new GameCamera(width, height);

        currentMap = new Map(
                "/LVL1/layout1final.txt",
                "/LVL1/laye2final.txt",
                "/LVL1/layer3Lvl1.txt"
        );

        checker = new CollisionChecker(currentMap);
        player = new MC("Erou", 200, 200, keyInput);
    }

    @Override
    public void Update() {
        player.update(checker);
        camera.centerOnEntity(player);

        // ##########################################
        // SEMN: LOGICA DE DETECTIE LUPTA SI CURATARE INAMICI
        for (int i = 0; i < currentMap.getEnemies().size(); i++) {
            Entity e = currentMap.getEnemies().get(i);

            if (e.hp <= 0) {
                currentMap.getEnemies().remove(i);
                i--;
                continue;
            }

            e.update(checker);

            // Verificam coliziunea (Hitbox vs Hitbox)
            Rectangle pRect = new Rectangle((int)player.x + player.solidArea.x, (int)player.y + player.solidArea.y, player.solidArea.width, player.solidArea.height);
            Rectangle eRect = new Rectangle((int)e.x + e.solidArea.x, (int)e.y + e.solidArea.y, e.solidArea.width, e.solidArea.height);

            if (pRect.intersects(eRect)) {
                // IMPORTANT: Trimitem mouseManager catre BattleState
                State.SetState(new BattleState(keyInput, mouseManager, player, e));
            }
        }
    }

    @Override
    public void Draw(Graphics g) {
        currentMap.Draw(g, camera);
        player.draw(g, (int)camera.getxOffset(), (int)camera.getyOffset());
        for (Entity e : currentMap.getEnemies()) {
            e.draw(g, (int)camera.getxOffset(), (int)camera.getyOffset());
        }
    }
}