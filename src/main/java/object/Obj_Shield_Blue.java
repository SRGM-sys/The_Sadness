package object;

import entity.Entity;
import main.GamePanel;

public class Obj_Shield_Blue extends Entity{
    
    public Obj_Shield_Blue(GamePanel gp){
        super(gp);
        type = type_shield;
        name = "Escudo Azul";
        down1 = setup("objects","shield_blue", super.px, super.px);
        defenseValue = 1;
        description = "["+name+"]" + "\nUn escudo brillante";
        
    }
    
    
}
