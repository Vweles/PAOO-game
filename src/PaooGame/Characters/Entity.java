package PaooGame.Characters;

public class Entity {
    public String nume;
    public int str;
    public int inte;
    public int agi;
    public int tho;
    public int hp;
    public int mana;
    public double att_spd;
    public int att_dmg_hit;
    public int spell_dmg;
    public int attacks;
    public int limbs_at;
    public int lvl;
    public int Limbs_normal;
    public int x, y;
    public int speed;
    public static final int R_ARM = 0;
    public static final int L_ARM = 1;
    public static final int R_LEG = 2;
    public static final int L_LEG = 3;
    public static final int HEAD = 4;

    public boolean[] limbsStatus = {true, true, true, true, true};

    public Entity(String NUME, int STR, int INTE, int AGI, int THO, int LIMBS_n, int LIMBS_at, int LVL, int x, int y, int speed) {
        this.nume = NUME;
        this.str = STR;
        this.inte = INTE;
        this.agi = AGI;
        this.tho = THO;
        this.hp = 100 + 10 * this.tho + 5 * this.str;
        this.mana = 150 + 15 * this.inte;
        this.Limbs_normal = LIMBS_n;
        this.lvl = LVL;
        this.limbs_at = LIMBS_at;
        this.attacks = this.limbs_at;
        this.spell_dmg = this.inte * 5;
        this.att_dmg_hit = this.str + this.limbs_at;
        this.att_spd = this.agi * 5 + this.Limbs_normal;
        this.speed = 1 + this.agi;

    }

    public void loseLimb(int limbIndex) {
        if (limbIndex >= 0 && limbIndex < limbsStatus.length) {
            limbsStatus[limbIndex] = false;
            updateStatsAfterInjury();
        }


    }
    //AICI E MAGIAAA
    // uitate la Fear and HUnger si o sa intelgi sistemu asta
    // dupa ce teai uitat si mai ai idei bagale aici e misto rau asta cu membre
    // vezi ca as  vrea sa mai adaug aici mai multe da momentan lasam asa mai basic
    public void updateStatsAfterInjury() {

        if (!limbsStatus[R_LEG] || !limbsStatus[L_LEG]) {
            this.speed = Math.max(1, (1 + agi) / 2);
        }

        if (!limbsStatus[R_LEG] && !limbsStatus[L_LEG]) {
            this.speed = 1;
        }

        if (!limbsStatus[HEAD]) {
            this.hp = 0;
        }


        int activeArms = 0;
        if(limbsStatus[R_ARM]) activeArms++;
        if(limbsStatus[L_ARM]) activeArms++;
        this.att_dmg_hit = this.str + activeArms;
    }
}

