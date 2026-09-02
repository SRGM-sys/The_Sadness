package object;

import entity.Entity;
import main.GamePanel;

public class Obj_Potion_Red extends Entity{
    
    GamePanel gp;
    int value = 2;

    public Obj_Potion_Red(GamePanel gp) {
        super(gp);
        
        this.gp = gp;
        type = type_consumable;
        name = "Poción Roja";
        down1 = setup("objects", "potion_red", super.px, super.px);
        description = "["+name+"]" + "\nRecuperas un corazon";
    }
    
    @Override
    public void use(Entity entity){
        gp.gameState = gp.dialogueState;
        
        gp.ui.currentDialogue = "Haz bebido la pocion roja";
        entity.life += value;
        if(gp.player.life > gp.player.maxLife){
            gp.player.life = gp.player.maxLife;
        }
        gp.soundEffect(2);
    }
    
    
}
