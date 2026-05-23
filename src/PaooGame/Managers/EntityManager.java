package PaooGame.Managers;

import PaooGame.Characters.Entity;
import PaooGame.Graphics.GameCamera;
import PaooGame.Physics.CollisionChecker;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;

public class EntityManager {
    private ArrayList<Entity> entities;

    public EntityManager() {
        entities = new ArrayList<>();
    }

    public void update(CollisionChecker checker) {
        Iterator<Entity> it = entities.iterator();
        while (it.hasNext()) {
            Entity e = it.next();
            e.update(checker);

            if (e.getHp() <= 0) it.remove();
        }
    }

    public void draw(Graphics g, GameCamera camera) {
        for (Entity e : entities) {
            e.draw(g, (int)camera.getxOffset(), (int)camera.getyOffset());
        }
    }

    public void addEntity(Entity e) { entities.add(e); }
    public ArrayList<Entity> getEntities() { return entities; }
}