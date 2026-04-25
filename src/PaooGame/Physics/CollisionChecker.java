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

        int entityLeftWorldX = entity.x + entity.solidArea.x;
        int entityRightWorldX = entity.x + entity.solidArea.x + entity.solidArea.width - 1;
        int entityTopWorldY = entity.y + entity.solidArea.y;
        int entityBottomWorldY = entity.y + entity.solidArea.y + entity.solidArea.height - 1;


        int entityLeftCol = entityLeftWorldX / Tile.TILE_WIDTH;
        int entityRightCol = entityRightWorldX / Tile.TILE_WIDTH;
        int entityTopRow = entityTopWorldY / Tile.TILE_HEIGHT;
        int entityBottomRow = entityBottomWorldY / Tile.TILE_HEIGHT;

        int tileNum1, tileNum2;


        switch (entity.directie) {
            case "up":
                entityTopRow = (entityTopWorldY - entity.speed) / Tile.TILE_HEIGHT;

                tileNum1 = map.getTileID(entityLeftCol, entityTopRow);
                tileNum2 = map.getTileID(entityRightCol, entityTopRow);
                if (Tile.tiles[tileNum1].IsSolid() || Tile.tiles[tileNum2].IsSolid()) {
                    entity.collisionOn = true;
                }
                break;
            case "down":
                entityBottomRow = (entityBottomWorldY + entity.speed) / Tile.TILE_HEIGHT;

                tileNum1 = map.getTileID(entityLeftCol, entityBottomRow);
                tileNum2 = map.getTileID(entityRightCol, entityBottomRow);
                if (Tile.tiles[tileNum1].IsSolid() || Tile.tiles[tileNum2].IsSolid()) {
                    entity.collisionOn = true;
                }
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX - entity.speed) / Tile.TILE_WIDTH;

                tileNum1 = map.getTileID(entityLeftCol, entityTopRow);
                tileNum2 = map.getTileID(entityLeftCol, entityBottomRow);
                if (Tile.tiles[tileNum1].IsSolid() || Tile.tiles[tileNum2].IsSolid()) {
                    entity.collisionOn = true;
                }
                break;
            case "right":
                entityRightCol = (entityRightWorldX + entity.speed) / Tile.TILE_WIDTH;

                tileNum1 = map.getTileID(entityRightCol, entityTopRow);
                tileNum2 = map.getTileID(entityRightCol, entityBottomRow);
                if (Tile.tiles[tileNum1].IsSolid() || Tile.tiles[tileNum2].IsSolid()) {
                    entity.collisionOn = true;
                }
                break;
        }
    }
}