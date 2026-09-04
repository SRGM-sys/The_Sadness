package object;

import entity.Projectile;
import main.GamePanel;

public class Obj_Rock extends Projectile{
    
    GamePanel gp;
    
    public Obj_Rock(GamePanel gp) {
        super(gp);
        this.gp = gp;
        
        name = "Rock";
        speed = 8;
        maxLife = 80;
        life = maxLife;
        attack = 2;
        useCost = 1;
        alive = false; // Esto de aquí es para no spamear proyectiles
        getImage();
    }
    
    public void getImage(){
        up1 = setup("projectile", "rock_down_1", super.px, super.px);
        up2 = setup("projectile", "rock_down_1", super.px, super.px);
        down1 = setup("projectile", "rock_down_1", super.px, super.px);
        down2 = setup("projectile", "rock_down_1", super.px, super.px);
        left1 = setup("projectile", "rock_down_1", super.px, super.px);
        left2 = setup("projectile", "rock_down_1", super.px, super.px);
        right1 = setup("projectile", "rock_down_1", super.px, super.px);
        right2 = setup("projectile", "rock_down_1", super.px, super.px);
    }
    
    
}
