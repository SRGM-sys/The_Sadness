package object;

import entity.Entity;
import main.GamePanel;

public class Obj_Potion_Red extends Entity{
    
    GamePanel gp;

    public Obj_Potion_Red(GamePanel gp) {
        super(gp);
        
        this.gp = gp;
        type = type_consumable;
        name = "Poción Roja";
        value = 2;
        down1 = setup("objects", "potion_red", super.px, super.px);
        description = "["+name+"]" + "\nRecuperas un corazón";
    }
    
    @Override
    public boolean use(Entity entity){
        gp.gameState = gp.dialogueState;
        
        if(gp.player.life == gp.player.maxLife){
            gp.ui.currentDialogue = "Todos los corazones están \n llenos, no se uso la poción";
            return false;
        } else{
            gp.ui.currentDialogue = "Recuperaste 1 corazón";
            entity.life += value;
        }
        if(gp.player.life > gp.player.maxLife){
            gp.player.life = gp.player.maxLife;
        }
        gp.soundEffect(2);
        return true;
    }
    
    
}
