package PaooGame.Physics;

import PaooGame.Characters.Entity;
import PaooGame.Map.Map;
import PaooGame.Tiles.Tile;

public class CollisionChecker {
    private Map map;

    public CollisionChecker(Map map) {
        this.map = map;
    }

    public void checkTile(Entity entity) {

        int entityLeftX = (int) (entity.getX() + entity.getSolidArea().x);
        int entityRightX = (int) (entity.getX() + entity.getSolidArea().x + entity.getSolidArea().width);
        int entityTopY = (int) (entity.getY() + entity.getSolidArea().y);
        int entityBottomY = (int) (entity.getY() + entity.getSolidArea().y + entity.getSolidArea().height);

        int col1, col2, row1, row2;

        switch (entity.getDirectie()) {
            case "up":
                row1 = (entityTopY - entity.getSpeed()) / Tile.TILE_HEIGHT;
                col1 = entityLeftX / Tile.TILE_WIDTH;
                col2 = entityRightX / Tile.TILE_WIDTH;
                if (checkSolid(col1, row1) || checkSolid(col2, row1)) {
                    entity.setCollisionOn(true);
                }
                break;
            case "down":
                row1 = (entityBottomY + entity.getSpeed()) / Tile.TILE_HEIGHT;
                col1 = entityLeftX / Tile.TILE_WIDTH;
                col2 = entityRightX / Tile.TILE_WIDTH;
                if (checkSolid(col1, row1) || checkSolid(col2, row1)) {
                    entity.setCollisionOn(true);
                }
                break;
            case "left":
                col1 = (entityLeftX - entity.getSpeed()) / Tile.TILE_WIDTH;
                row1 = entityTopY / Tile.TILE_HEIGHT;
                row2 = entityBottomY / Tile.TILE_HEIGHT;
                if (checkSolid(col1, row1) || checkSolid(col1, row2)) {
                    entity.setCollisionOn(true);
                }
                break;
            case "right":
                col1 = (entityRightX + entity.getSpeed()) / Tile.TILE_WIDTH;
                row1 = entityTopY / Tile.TILE_HEIGHT;
                row2 = entityBottomY / Tile.TILE_HEIGHT;
                if (checkSolid(col1, row1) || checkSolid(col1, row2)) {
                    entity.setCollisionOn(true);
                }
                break;
        }
    }

    private boolean checkSolid(int col, int row) {
        int tileIdL0 = map.getTileID(col, row, 0);
        int tileIdL1 = map.getTileID(col, row, 1);
        return isTileSolid(tileIdL0) || isTileSolid(tileIdL1);
    }

    private boolean isTileSolid(int id) {
        return (id == 1 || id == 6 || (id >= 144 && id <= 197 || id == 13 || id == 257));
    }

    public boolean isOnHazard(Entity entity) {

        int centerX = (int) (entity.getX() + entity.getSolidArea().x + entity.getSolidArea().width / 2);
        int centerY = (int) (entity.getY() + entity.getSolidArea().y + entity.getSolidArea().height / 2);

        int col = centerX / Tile.TILE_WIDTH;
        int row = centerY / Tile.TILE_HEIGHT;

        int tileIdL0 = map.getTileID(col, row, 0);
        int tileIdL1 = map.getTileID(col, row, 1);

        return (tileIdL0 == 5 || tileIdL1 == 5);
    }
}