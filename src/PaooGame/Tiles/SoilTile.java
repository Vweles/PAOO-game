
package PaooGame.Tiles;
import PaooGame.Graphics.Assets;

public class SoilTile extends Tile {
    public SoilTile(int id) {
        super(Assets.soil, id, false); // Solul nu e solid
    }
}