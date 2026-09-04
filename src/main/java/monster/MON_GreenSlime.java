package monster;

import entity.Entity;
import java.util.Random;
import main.GamePanel;
import object.Obj_Rock;

public class MON_GreenSlime extends Entity{
    
    GamePanel gp;
    
    public MON_GreenSlime(GamePanel gp) {
        super(gp);
        this.gp = gp;
        type = type_monster;
        name = "Green Slime";
        speed = 1;
        maxLife = 4;
        life = maxLife;
        attack = 2;
        defense = 0;
        exp = 3;
        projectile = new Obj_Rock(gp);
        
        solidArea.x = 3;
        solidArea.y = 10;
        solidArea.width = 42;
        solidArea.height = 36;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();
    }
    
    public void getImage (){
        up1 = setup("monster","greenslime_down_1", super.px, super.px);
        up2 = setup("monster","greenslime_down_2", super.px, super.px);
        down1 = setup("monster","greenslime_down_1", super.px, super.px);
        down2 = setup("monster","greenslime_down_2", super.px, super.px);
        left1 = setup("monster","greenslime_down_1", super.px, super.px);
        left2 = setup("monster","greenslime_down_2", super.px, super.px);
        right1 = setup("monster","greenslime_down_1", super.px, super.px);
        right2 = setup("monster","greenslime_down_2", super.px, super.px);
    }
    
    // Vamos a hacer una IA sencilla (Copiamos la del NPC)
    @Override
    public void setAction(){
        actionLockCounter++;
        
        if(actionLockCounter == 120){
            Random rd = new Random();
            int i = rd.nextInt(100)+1; 

            if(i<= 25){
                direction = "up";
            }
            if(i>25 && i <= 50){
                direction = "down";
            }
            if(i > 50 && i <= 75){
                direction = "left";
            }
            if(i > 75){
                direction = "right";
            }
            
            actionLockCounter = 0;
            
        }
        
        int i = new Random().nextInt(100)+1;
        if(i > 99 && !projectile.alive && shotAvailableCounter == 30){
            projectile.set(worldX, worldY, direction, true, this);
            gp.projectileList.add(projectile);
            shotAvailableCounter = 0;
        }
        
    }
    
    @Override
    public void damageReaction(){
        actionLockCounter = 0;
        // Esta reacción de aquí el slime mira a la dirección del jugador
        // Es decir cuando es golpeado se va a alejar
        direction = gp.player.direction;
    }
}
