package object;

import entity.Entity;
import main.GamePanel;

public class Obj_Key extends Entity{
    
    public Obj_Key(GamePanel gp){
        super(gp);
        name = "key";
        down1 = setup("objects","key", super.px, super.px);
        description = "["+name+"]" + "\nPuede servir para algo";
    }
}
