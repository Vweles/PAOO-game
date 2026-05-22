package PaooGame.Managers;

import PaooGame.Map.Map;
import PaooGame.State.PlayState;
import PaooGame.Graphics.Assets;
import PaooGame.Tiles.Tile;
import PaooGame.Characters.LVL2.Comp;

public class LevelManager {
    private PlayState playState;

    public LevelManager(PlayState playState) {
        this.playState = playState;
    }

    public void loadLevel(int levelNo) {
        switch (levelNo) {
            case 1:
                Assets.Init();
                Tile.InitCustomTiles();
                playState.setCurrentMap(new Map("/LVL1/layout1final.txt", "/LVL1/laye2final.txt", "/LVL1/layer3Lvl1.txt"));
                break;
            case 2:
                Assets.InitLevel2();
                Tile.InitLevel2Tiles();
                playState.setCurrentMap(new Map("/LVL2/layer1_formatat.txt", "/LVL2/layer2_formatat.txt", "/LVL2/layer3_formatat.txt"));
                playState.setCompanion(new Comp("Guardian", 250, 250, playState.getPlayer()));
                break;
            case 3:
                Assets.InitLevel3();
                Tile.InitLevel3Tiles();
                playState.setCurrentMap(new Map("/LVL3/lvl3final_layer1.txt", "/LVL3/lvl3final_layer2.txt", "/LVL3/lvl3final_layer3.txt"));
                playState.setCompanion(null);
                break;
        }
    }
}