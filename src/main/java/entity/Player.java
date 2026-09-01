
package entity;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import main.GamePanel;
import handler.KeyHandler;
import object.Obj_Shield_Wood;
import object.Obj_Sword_Normal;

public class Player extends Entity{
    
    KeyHandler keyH;
    
    // Estas variables van a ser la camara del jugador
    public final int screenX;
    public final int screenY;
    int standCounter = 0;
    

    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp);
        this.keyH = keyH;
        
        // Esto es para obtener el punto medio de la pantalla
        screenX = gp.screenWidth/2 - gp.tileSize/2;
        screenY = gp.screenHeight/2 - gp.tileSize/2;
        
        solidArea = new Rectangle(8,16,32,32);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y; 
        
        attackArea.width = 36;
        attackArea.height = 36;
        
        setDefaultValues();
        getPlayerImage();
        getPlayerAttackImage();
    }
    
    // Vamos a establecer las configuraciones del Player
    public void setDefaultValues(){
        // POSICIÓN INICIAL
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = 4;
        direction = "down";
        
        // ESTADO DEL JUGADOR
        level = 1;
        maxLife = 6;    // 1 vida es la mitad de un corazón
        life = maxLife;
        strength = 1;
        dexterity = 1;
        exp = 0;
        nextLevelExp = 5;
        coin = 0;
        currentWeapon = new Obj_Sword_Normal(gp);
        currentShield = new Obj_Shield_Wood(gp);
        attack = getAttack();
        defense = getDefense();
    }
    
    public int getAttack(){
        return attack = strength * currentWeapon.attackValue;
    }
    
    public int getDefense(){
        return defense = dexterity * currentShield.defenseValue;
    }
    
    public void getPlayerImage(){
        up1 = setup("player", "boy_up_1", super.px, super.px);
        up2 = setup("player", "boy_up_2", super.px, super.px);
        down1 = setup("player", "boy_down_1", super.px, super.px);
        down2 = setup("player", "boy_down_2", super.px, super.px);
        left1 = setup("player", "boy_left_1", super.px, super.px);
        left2 = setup("player", "boy_left_2", super.px, super.px);
        right1 = setup("player", "boy_right_1", super.px, super.px);
        right2 = setup("player", "boy_right_2", super.px, super.px);
    }
    
    public void getPlayerAttackImage(){
        atkUp1 = setup("player", "boy_attack_up_1", super.px, super.px*2);
        atkUp2 = setup("player", "boy_attack_up_2", super.px, super.px*2);
        atkDown1 = setup("player", "boy_attack_down_1", super.px, super.px*2);
        atkDown2 = setup("player", "boy_attack_down_2", super.px, super.px*2);
        atkLeft1 = setup("player", "boy_attack_left_1", super.px*2, super.px);
        atkLeft2 = setup("player", "boy_attack_left_2", super.px*2, super.px);
        atkRight1 = setup("player", "boy_attack_right_1", super.px*2, super.px);
        atkRight2 = setup("player", "boy_attack_right_2", super.px*2, super.px);
    }
    
    
    /* VAMOS A PONER EL UPDATE Y DRAW PARA CADA ENTIDAD
    Esto lo haremos para evitar un código gigantesco en GamePanel*/
    @Override
    public void update(){
        
        verifyAttack();
        
        if(attacking){
            attacking();
        }
        
        // Este gran if me va a permtir que el personaje no se mueva si el 
        // usuario no presiona ninguna tecla
        else if(keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed
            || keyH.enterPressed){
            if(keyH.upPressed){
                direction = "up";
            }
            else if(keyH.downPressed){
                direction = "down";
            }
            else if(keyH.leftPressed){
                direction = "left";
            }
            else if(keyH.rightPressed){
                direction = "right";
            }
            
            // CHECK TILE COLLISION
            collisionOn = false;
            gp.cChecker.checkTile(this); // Polimorfismo, Player es una Entity
            
            // CHECK OBJECT COLLISION
            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);
            
            // CHECK NPC COLLISION
            int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
            interactNPC(npcIndex);
            
            //  CHECK MONSTER COLLISION
            int monIndex = gp.cChecker.checkEntity(this, gp.mon);
            contactMonster(monIndex);
            
            // CHECK EVENT COLLISION
            gp.eHandler.checkEvent();
            
            // Si presiono Enter se resetea inmediatamente y no se queda "guardado" como true.
            gp.keyH.enterPressed = false;
            
            // IF COLLISION IS FALSE, PLAYER CAN MOVE
            if(!collisionOn && !keyH.enterPressed){
                
                switch(direction){
                    case "up": worldY -= speed; break;
                    case "down": worldY += speed; break;
                    case "left":worldX -= speed; break;
                    case "right": worldX += speed; break;
                }
                
                
            }

            // Aquí el personaje cambiará de imagen en cada frame para simular movimiento
            spriteCounter++;
            if(spriteCounter > 12){ //Velocidad de cambio
                if(spriteNum == 1) spriteNum = 2;
                else if(spriteNum == 2) spriteNum = 1;
                spriteCounter  = 0;
            }
        } else{
            standCounter++;
            if(standCounter==20 ){
                spriteNum=1;
                standCounter=0;
            }
        } 
        
        if(invincible){
            invincibleCounter++;
            if(invincibleCounter > 60){
                invincible = false;
                invincibleCounter = 0;
            }
            
        }
    }
    
    public void attacking(){
        spriteCounter++;
        
        if(spriteCounter <= 5){
            spriteNum = 1;
        }
        if(spriteCounter > 5 && spriteCounter < 25){
            spriteNum = 2;
            
            // Objeto las coordenadas actuales
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;
            
            // Ajustar las coordenadas X y Y globales
            switch(direction){
                case "up": worldY -= attackArea.height; break;
                case "down": worldY += attackArea.height; break;
                case "left": worldX -= attackArea.width; break;
                case "right": worldX += attackArea.width; break;
            }
            
            // Convertir el area de ataque en un solidArea
            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;
            
            // Comprobar la colision del mounstro con el X/Y global y solidArea
            int monsterIndex = gp.cChecker.checkEntity(this, gp.mon);
            damageMonster(monsterIndex);
            
            
            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;
        }
        if(spriteCounter > 25){
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
        }
    }
    
    public void verifyAttack(){
        if(gp.mouseH.leftPressed && !attacking){
            gp.soundEffect(8);
            attacking = true;
        }
    }
    
    public void interactNPC(int i){
        // Cuando i = 999 es pq tocamos a la entidad
        if(i != 999){
            if(gp.keyH.enterPressed){
                gp.gameState = gp.dialogueState;
                gp.npc[i].speak();
            }
        }  
    }
    
    public void pickUpObject(int i){
        // Si i = 999, significa que no hemos tocado el objeto
        if(i != 999){
            
        }
    }
    
    public void contactMonster(int i){
        if (i != 999 && !invincible){
            gp.soundEffect(7);
            
            int damage = gp.mon[i].attack - defense;
            if(damage < 0){
                damage = 0;
            }
            
            life -=damage;
            invincible = true;
        }
    }
    
    public void damageMonster(int i){
        if(i != 999){
            if(!gp.mon[i].invincible){
                gp.soundEffect(6);
                
                int damage = attack - gp.mon[i].defense;
                if(damage < 0){
                    damage = 0;
                }
                gp.mon[i].life -= damage;
                
                gp.mon[i].invincible = true;
                gp.mon[i].damageReaction();
                
                if(gp.mon[i].life <= 0){
                    gp.mon[i].dying = true;
                    exp += gp.mon[i].exp;
                    gp.ui.addMessage("+ "+ gp.mon[i].exp + " xp");
                    checkLevelUp();
                }
            }
        }
    }
    
    public void checkLevelUp(){
        if(exp >= nextLevelExp){
            level ++;
            nextLevelExp = nextLevelExp + 20;
            maxLife += 2;
            strength++;
            dexterity++;
            attack = getAttack();
            defense = getDefense();
            
            gp.soundEffect(9);
            gp.gameState = gp.dialogueState;
            gp.ui.currentDialogue = "Subiste al nivel "+level +", sientes como tu \n determinación aumenta"; 
        }
    }
    
    // Organizamos las imágenes en cada caso de movimiento del personaje
    @Override
    public void draw(Graphics2D g2){
        int tempScreenX = screenX;
        int tempScreenY = screenY;
        
        BufferedImage image = null;
        
        switch(direction){
            case "up" -> {
                if(!attacking){
                    if (spriteNum == 1) image = up1;    
                    if (spriteNum == 2) image = up2;
                } else{
                    tempScreenY  = screenY - gp.tileSize;
                    if (spriteNum == 1) image = atkUp1;    
                    if (spriteNum == 2) image = atkUp2;
                }
            }
            case "down" -> {
                if(!attacking){
                    if (spriteNum == 1) image = down1; 
                    if (spriteNum == 2) image = down2;
                }else{
                    if (spriteNum == 1) image = atkDown1; 
                    if (spriteNum == 2) image = atkDown2;
                }
            }
            case "left" -> {
                if(!attacking){
                    if (spriteNum == 1) image = left1;  
                    if (spriteNum == 2) image = left2;
                } else{
                    tempScreenX  = screenX - gp.tileSize;
                    if (spriteNum == 1) image = atkLeft1;  
                    if (spriteNum == 2) image = atkLeft2;
                }
            }
            case "right" -> {
                if(!attacking){
                    if (spriteNum == 1) image = right1; 
                    if (spriteNum == 2) image = right2;
                } else{
                    if (spriteNum == 1) image = atkRight1; 
                    if (spriteNum == 2) image = atkRight2;
                }
            }
        }
                
        // VISUAL EFFECT DAMAGE
        if(invincible){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
        }
        // Se dibujará al personaje un poco transparente
        g2.drawImage(image, tempScreenX, tempScreenY, null);
        
        // Luego reiniciamos el Alpha
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

     
    }
    
    
}
