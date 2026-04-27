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
        // Luam marginile solidArea (hitbox)
        int entityLeftWorldX = (int) (entity.x + entity.solidArea.x);
        int entityRightWorldX = (int) (entity.x + entity.solidArea.x + entity.solidArea.width);
        int entityTopWorldY = (int) (entity.y + entity.solidArea.y);
        int entityBottomWorldY = (int) (entity.y + entity.solidArea.y + entity.solidArea.height);

        int col1, col2, row1, row2;

        switch (entity.directie) {
            case "up":
                row1 = (entityTopWorldY - entity.speed) / Tile.TILE_WIDTH;
                col1 = entityLeftWorldX / Tile.TILE_WIDTH;
                col2 = entityRightWorldX / Tile.TILE_WIDTH;
                if (isSolid(map.getTileID(col1, row1)) || isSolid(map.getTileID(col2, row1))) {
                    entity.collisionOn = true;
                }
                break;
            case "down":
                row1 = (entityBottomWorldY + entity.speed) / Tile.TILE_WIDTH;
                col1 = entityLeftWorldX / Tile.TILE_WIDTH;
                col2 = entityRightWorldX / Tile.TILE_WIDTH;
                if (isSolid(map.getTileID(col1, row1)) || isSolid(map.getTileID(col2, row1))) {
                    entity.collisionOn = true;
                }
                break;
            case "left":
                col1 = (entityLeftWorldX - entity.speed) / Tile.TILE_WIDTH;
                row1 = entityTopWorldY / Tile.TILE_WIDTH;
                row2 = entityBottomWorldY / Tile.TILE_WIDTH;
                if (isSolid(map.getTileID(col1, row1)) || isSolid(map.getTileID(col1, row2))) {
                    entity.collisionOn = true;
                }
                break;
            case "right":
                col1 = (entityRightWorldX + entity.speed) / Tile.TILE_WIDTH;
                row1 = entityTopWorldY / Tile.TILE_WIDTH;
                row2 = entityBottomWorldY / Tile.TILE_WIDTH;
                if (isSolid(map.getTileID(col1, row1)) || isSolid(map.getTileID(col1, row2))) {
                    entity.collisionOn = true;
                }
                break;
        }
    }

    // Verificam direct ID-urile de wall (6) si abis (1)
    private boolean isSolid(int id) {
        return (id == 6 || id == 1);
    }
}