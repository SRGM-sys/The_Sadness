package object;

import entity.Entity;
import main.GamePanel;

public class Obj_ManaCrystal extends Entity{
    
    GamePanel gp;
    
    public Obj_ManaCrystal(GamePanel gp) {
        super(gp);
        this.gp = gp;
        name = "Cristal de Mana";
        image = setup("objects", "manacrystal_full", super.px, super.px);
        image2 = setup("objects", "manacrystal_blank", super.px, super.px);

    }
    
}
