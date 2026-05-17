package PaooGame.Tiles;
import PaooGame.Graphics.Assets;

public class WaterTile extends Tile {
    public WaterTile(int id) {
        super(Assets.water, id, false); // SCHIMBAT: Apa nu mai este solidă pentru a putea păși în ea
    }
}