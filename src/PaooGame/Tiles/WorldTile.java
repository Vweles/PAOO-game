

package PaooGame.Tiles;
import java.awt.image.BufferedImage;

public class WorldTile extends Tile {
    private boolean isSolid;
    public WorldTile(BufferedImage img, int id, boolean isSolid) {
        super(img, id);
        this.isSolid = isSolid;
    }
    @Override
    public boolean IsSolid() { return isSolid; }
}
