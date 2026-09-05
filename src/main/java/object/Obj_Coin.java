package object;

import entity.Entity;
import main.GamePanel;

public class Obj_Coin extends Entity{
    
    GamePanel gp;
    
    public Obj_Coin(GamePanel gp) {
        super(gp);
        
        this.gp = gp;
        
        name = "Oro";
        type = type_pickUpOnly;
        value = 1;
        down1 = setup("objects", "coin", super.px, super.px);
    }

    @Override
    public void pickUp(Entity user){
        gp.soundEffect(1);
        gp.ui.addMessage("Moneda +" + value);
        gp.player.coin += value;
    }
    
}
