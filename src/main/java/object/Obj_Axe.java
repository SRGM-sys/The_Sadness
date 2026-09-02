package object;

import entity.Entity;
import main.GamePanel;

public class Obj_Axe extends Entity{
    
    public Obj_Axe(GamePanel gp) {
        super(gp);
        type = type_axe;
        name = "Hacha";
        down1 = setup("objects","axe", super.px, super.px);
        attackValue = 2;
        attackArea.width = 30;
        attackArea.height = 30;
        description = "["+name+"]" + "\nPuedes talar árboles";
    }
    
}
