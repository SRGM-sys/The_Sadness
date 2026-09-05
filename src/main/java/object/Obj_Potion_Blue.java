package object;

import entity.Entity;
import main.GamePanel;

public class Obj_Potion_Blue extends Entity{
    
    GamePanel gp;
    
    public Obj_Potion_Blue(GamePanel gp) {
        super(gp);
        this.gp = gp;
        type = type_consumable;
        name = "Poción Azul";
        value = 2;
        down1 = setup("objects", "potion_blue", super.px, super.px);
        description = "["+name+"]" + "\nRecuperas un cristal";
    }
    
    @Override
    public boolean use(Entity entity){
        gp.gameState = gp.dialogueState;
        
        if(gp.player.mana == gp.player.maxMana){
            gp.ui.currentDialogue = "Todos los cristales de maná están \nllenos, no se uso la poción";
            return false;
        } else{
            gp.ui.currentDialogue = "Recuperaste 1 cristal de maná";
            entity.mana += value;
        }
        if(gp.player.mana > gp.player.maxMana){
            gp.player.mana = gp.player.maxMana;
        }
        gp.soundEffect(2);
        return true;
    }
}
    
