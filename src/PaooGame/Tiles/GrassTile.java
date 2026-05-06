package PaooGame.Tiles;
import PaooGame.Graphics.Assets;

public class GrassTile extends Tile {
    public GrassTile(int id) {
        super(Assets.grass, id, false); // Iarba nu e solida
    }
}