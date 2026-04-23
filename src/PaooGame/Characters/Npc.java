package PaooGame.Characters;

public class Npc extends Entity {
    public boolean Friend;
    public boolean Fallow;
    public boolean Roam;
    public String Job;
    public boolean interact;

    public Npc(String NUME, int STR, int INTE, int AGI, int THO,
               int LIMBS_n, int LIMBS_at, int LVL,int x, int y, int speed,
               boolean friend, boolean fallow, boolean roam,
               String job, boolean interact) {

        super(NUME, STR, INTE, AGI, THO, LIMBS_n, LIMBS_at, LVL, x, y, speed);

        this.Friend = friend;
        this.Fallow = fallow;
        this.Roam = roam;
        this.Job = job;
        this.interact = interact;
    }
}
