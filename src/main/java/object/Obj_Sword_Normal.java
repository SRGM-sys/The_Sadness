package object;

import entity.Entity;
import main.GamePanel;

public class Obj_Sword_Normal extends Entity{
    
    public Obj_Sword_Normal(GamePanel gp) {
        super(gp);
        type = type_sword;
        name = "Normal Sword";
        down1 = setup("objects","sword_normal", super.px, super.px);
        attackValue = 1;
        attackArea.width = 36;
        attackArea.height = 36;
        description = "["+name+"]" + "\nUna espada vieja";
        
    }
    
    
}
