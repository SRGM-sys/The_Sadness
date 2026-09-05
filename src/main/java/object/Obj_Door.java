package object;

import entity.Entity;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;

public class Obj_Door extends Entity{
    
    public Obj_Door(GamePanel gp){
        super(gp);
        name = "door";
        down1 = setup("objects","door", super.px, super.px);
        collision = true;
        type = type_door;
        
        solidArea.x = 0;
        solidArea.y = 16;  
        solidArea.width = 48;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        
    }
}
