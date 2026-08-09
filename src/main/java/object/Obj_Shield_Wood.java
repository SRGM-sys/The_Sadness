package object;

import entity.Entity;
import main.GamePanel;

public class Obj_Shield_Wood extends Entity{
    
    public Obj_Shield_Wood(GamePanel gp) {
        super(gp);
        name = "Wood Shield";
        down1 = setup("objects","shield_wood", super.px, super.px);
        defenseValue = 1;
    }
}
