package PaooGame.Physics;

import PaooGame.Characters.Entity;
import PaooGame.Tiles.Tile;

public class CollisionChecker {

    public int[][] testMap = {
            {0, 0, 1, 1, 1, 1},
            {0, 0, 0, 0, 4, 1},
            {1, 0, 3, 0, 2, 1},
            {1, 0, 0, 0, 0, 1},
            {1, 1, 1, 1, 1, 1}
    };

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
                break;
            case "down":
                entityBottomRow = (entityBottomWorldY + entity.speed) / Tile.TILE_HEIGHT;
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX - entity.speed) / Tile.TILE_WIDTH;
                break;
            case "right":
                entityRightCol = (entityRightWorldX + entity.speed) / Tile.TILE_WIDTH;
                break;
        }

        if (entityLeftCol < 0 || entityRightCol >= testMap[0].length ||
                entityTopRow < 0 || entityBottomRow >= testMap.length) {
            entity.collisionOn = true;
            return;
        }

        // Verificam in matrice daca e solid
        switch (entity.directie) {
            case "up":
                tileNum1 = testMap[entityTopRow][entityLeftCol];
                tileNum2 = testMap[entityTopRow][entityRightCol];

                if (Tile.tiles[tileNum1].IsSolid() || Tile.tiles[tileNum2].IsSolid()) entity.collisionOn = true;
                break;
            case "down":
                tileNum1 = testMap[entityBottomRow][entityLeftCol];
                tileNum2 = testMap[entityBottomRow][entityRightCol];
                if (Tile.tiles[tileNum1].IsSolid() || Tile.tiles[tileNum2].IsSolid()) entity.collisionOn = true;
                break;
            case "left":
                tileNum1 = testMap[entityTopRow][entityLeftCol];
                tileNum2 = testMap[entityBottomRow][entityLeftCol];
                if (Tile.tiles[tileNum1].IsSolid() || Tile.tiles[tileNum2].IsSolid()) entity.collisionOn = true;
                break;
            case "right":
                tileNum1 = testMap[entityTopRow][entityRightCol];
                tileNum2 = testMap[entityBottomRow][entityRightCol];
                if (Tile.tiles[tileNum1].IsSolid() || Tile.tiles[tileNum2].IsSolid()) entity.collisionOn = true;
                break;
        }
    }
}