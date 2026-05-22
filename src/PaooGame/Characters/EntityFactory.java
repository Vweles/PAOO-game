package PaooGame.Characters;

import PaooGame.Characters.LVL1.*;
import PaooGame.Characters.LVL2.*;
import PaooGame.Characters.LVL3.*;

public class EntityFactory {
    public static Entity createEntity(int id, int x, int y) {
        switch (id) {
            case 241: return new Silvani(x, y);
            case 244: return new Boss(x, y);
            case 741: return new mn(x, y);
            case 773: return new Bossss(x, y);
            case 98:  return new Minionlvl3(x, y);
            case 267: return new Bossfinal(x, y);
            default:  return null;
        }
    }
}