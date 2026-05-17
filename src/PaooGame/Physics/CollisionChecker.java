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

        int entityLeftX = (int) (entity.x + entity.solidArea.x);
        int entityRightX = (int) (entity.x + entity.solidArea.x + entity.solidArea.width);
        int entityTopY = (int) (entity.y + entity.solidArea.y);
        int entityBottomY = (int) (entity.y + entity.solidArea.y + entity.solidArea.height);

        int col1, col2, row1, row2;

        switch (entity.directie) {
            case "up":
                row1 = (entityTopY - entity.speed) / Tile.TILE_HEIGHT;
                col1 = entityLeftX / Tile.TILE_WIDTH;
                col2 = entityRightX / Tile.TILE_WIDTH;
                if (checkSolid(col1, row1) || checkSolid(col2, row1)) {
                    entity.collisionOn = true;
                }
                break;
            case "down":
                row1 = (entityBottomY + entity.speed) / Tile.TILE_HEIGHT;
                col1 = entityLeftX / Tile.TILE_WIDTH;
                col2 = entityRightX / Tile.TILE_WIDTH;
                if (checkSolid(col1, row1) || checkSolid(col2, row1)) {
                    entity.collisionOn = true;
                }
                break;
            case "left":
                col1 = (entityLeftX - entity.speed) / Tile.TILE_WIDTH;
                row1 = entityTopY / Tile.TILE_HEIGHT;
                row2 = entityBottomY / Tile.TILE_HEIGHT;
                if (checkSolid(col1, row1) || checkSolid(col1, row2)) {
                    entity.collisionOn = true;
                }
                break;
            case "right":
                col1 = (entityRightX + entity.speed) / Tile.TILE_WIDTH;
                row1 = entityTopY / Tile.TILE_HEIGHT;
                row2 = entityBottomY / Tile.TILE_HEIGHT;
                if (checkSolid(col1, row1) || checkSolid(col1, row2)) {
                    entity.collisionOn = true;
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

        return (id == 1 || id == 6 || (id >= 144 && id <= 197 || id ==13 || id ==257));
    }

    // În PaooGame.Physics.CollisionChecker.java

    public boolean isOnHazard(Entity entity) {
        // Calculăm centrul entității pentru a vedea exact pe ce tile stă
        int centerX = (int) (entity.x + entity.solidArea.x + entity.solidArea.width / 2);
        int centerY = (int) (entity.y + entity.solidArea.y + entity.solidArea.height / 2);

        int col = centerX / Tile.TILE_WIDTH;
        int row = centerY / Tile.TILE_HEIGHT;

        // Verificăm ID-ul pe ambele straturi (ID 5 = WaterTile)
        int tileIdL0 = map.getTileID(col, row, 0);
        int tileIdL1 = map.getTileID(col, row, 1);

        return (tileIdL0 == 5 || tileIdL1 == 5);
    }
}