
package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;
import main.UtilityTool;

public class Entity {
    
    // VALORES PRINCIPALES
    public GamePanel gp;
    public int worldX,worldY;
    public String direction = "down";
    public int px;
   
    // COLISIONES
    public Rectangle solidArea = new Rectangle(0,0,48,48); // Cuadro por defecto
    public Rectangle attackArea = new Rectangle(0,0,0,0);
    public int solidAreaDefaultX, solidAreaDefaultY;
    
    
    // DIÁLOGOS
    String dialogues[] = new String[20];
    int dialogueIndex = 0;
    
    // IMÁGENES
    public BufferedImage image, image2, image3;
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public BufferedImage atkUp1, atkUp2, atkDown1, atkDown2, atkLeft1, atkLeft2, atkRight1, atkRight2;
    
    // ESTADO DE LA ENTIDAD
    public int spriteNum = 1;
    public boolean collisionOn = false;
    public boolean invincible = false;
    public boolean attacking = false;
    public boolean alive = true; 
    public boolean dying = false;
    boolean hpBarOn = false;
    
    // ATRIBUTO DE LA ENTIDAD
    public boolean collision = false;
    public String name;
    public int speed;
    public int maxLife;
    public int life;
    public int level;
    public int strength;
    public int dexterity;
    public int attack;
    public int defense;
    public int exp;
    public int nextLevelExp;
    public int coin;
    public Entity currentWeapon;
    public Entity currentShield;
    
    // ATRIBUTO DE LOS ITEMS
    public int attackValue;
    public int defenseValue;
    public String description = "["+name+"]\n";
    
    // COUNTERS
    public int invincibleCounter = 0;
    public int actionLockCounter = 0;
    public int spriteCounter = 0;
    public int dyingCounter = 0;
    public int hpBarCounter = 0;
    
    // TIPO
    public int type;
    public final int type_player = 0;
    public final int type_npc = 1;
    public final int type_monster = 2;
    public final int type_sword = 3;
    public final int type_axe = 4;
    public final int type_shield = 5;
    public final int type_consumable = 6;
    
    public Entity(GamePanel gp){
        this.gp = gp;
        this.px = gp.tileSize;
    }
    
    public void setAction(){}
    public void damageReaction(){}
    public void speak(){
        if(dialogues[dialogueIndex] == null){
            dialogueIndex = 0;
        }
        
        gp.ui.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex++;

        switch(gp.player.direction){
            case "up": direction = "down"; break;
            case "left": direction = "right"; break;
            case "down": direction = "up"; break;
            case "right": direction = "left"; break;
        }
    }
    
    public void use(Entity entity){}
    
    public void update(){
        setAction();
        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false);
        gp.cChecker.checkEntity(this, gp.npc);
        gp.cChecker.checkEntity(this, gp.mon);
        boolean contactPlayer = gp.cChecker.checkerPlayer(this);
        
        // Si el jugador choca con un mounstro entonces
        if(this.type == type_monster && contactPlayer){
            if(!gp.player.invincible){
                gp.soundEffect(7);
                
                int damage = attack - gp.player.defense;
                if(damage < 0){
                    damage = 0;
                }
                gp.player.life -= damage;
                gp.player.invincible = true;
            }
        }
        
        if(!collisionOn){
                
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
        
        if(invincible){
            invincibleCounter++;
            if(invincibleCounter > 40){
                invincible = false;
                invincibleCounter = 0;
            }
        }
    }
    
    // Vamos a crear una función para dibujara los npc
    public void draw(Graphics2D g2){
        BufferedImage image = null;
        
        int screenX = worldX - gp.player.worldX + gp.player.screenX; 
        int screenY = worldY - gp.player.worldY + gp.player.screenY;
            
        int op1 = gp.player.worldX - gp.player.screenX;
        int op2 = gp.player.worldX + gp.player.screenX;
        int op3 = gp.player.worldY - gp.player.screenY;
        int op4 = gp.player.worldY + gp.player.screenY;
            
        /* Este condicional es CLAVE (MEJORA EL RENDIMIENTO)
        No queremos dibujar un bloque que este a 500 metros de nosotros
        Se iran dibujando nuevos bloques pero solo a nuestro alrededor */
        if((worldX + gp.tileSize > op1) && (worldX - gp.tileSize < op2) && 
           (worldY + gp.tileSize > op3) && (worldY - gp.tileSize <= op4)){
            
            switch(direction){
            case "up":     
                if (spriteNum == 1) image = up1;    
                else if(spriteNum == 2) image = up2;
                break;
            case "down":   
                if (spriteNum == 1) image = down1; 
                else if(spriteNum == 2) image = down2;
                break;
            case "left":   
                if (spriteNum == 1) image = left1;  
                else if(spriteNum == 2) image = left2;
                break;
            case "right":  
                if (spriteNum == 1) image = right1; 
                else if(spriteNum == 2) image = right2;
                break;
            }
            
            // MONSTER HP BAR
            if(type == type_monster && hpBarOn){
                
                double oneScale = (double) gp.tileSize / maxLife;
                double hpBarValue = oneScale*life;
                
                g2.setColor(new Color(35,35,35));
                g2.fillRect(screenX-1, screenY-16, gp.tileSize+2, 12);
                g2.setColor(new Color(255,0,30));
                g2.fillRect(screenX, screenY-15, (int)hpBarValue, 10);
            
                hpBarCounter++;
                if(hpBarCounter > 600){ // Desaparece luego de 10 segundos
                    hpBarCounter  = 0;
                    hpBarOn = false;
                }
            }
            
            if(invincible){
                hpBarOn = true;
                hpBarCounter = 0;
                changeAlpha(g2, 0.4F);
            }
            
            if(dying){
                dyingAnimaton(g2);
            }
            
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
            changeAlpha(g2, 1F);
        }
         
    }
    
    public void dyingAnimaton(Graphics2D g2){
        dyingCounter++;
        int i = 5;
        
        if(dyingCounter <= i) changeAlpha(g2, 0);
        if(dyingCounter > i && dyingCounter <= i*2) changeAlpha(g2, 1);
        if(dyingCounter > i*2 && dyingCounter <= i*3) changeAlpha(g2, 0);
        if(dyingCounter > i*3 && dyingCounter <= i*4) changeAlpha(g2, 1);
        if(dyingCounter > i*4 && dyingCounter <= i*5) changeAlpha(g2, 0);
        if(dyingCounter > i*5 && dyingCounter <= i*6) changeAlpha(g2, 1);
        if(dyingCounter > i*6 && dyingCounter <= i*7) changeAlpha(g2, 0);
        if(dyingCounter > i*7 && dyingCounter <= i*8) changeAlpha(g2, 1);
        if(dyingCounter > i*8){
            dying = false;
            alive = false;            
        }
        
    }
    
    public void changeAlpha(Graphics2D g2, float alphaValue){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));            
        
    }
    
    // Vamos a escalar los personajes antes de entrar al bucle
    public BufferedImage setup(String folder, String imageName, int width, int height){
        
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;
        
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/"+folder+"/"+imageName+".png"));
            image = uTool.scaleImage(image, width, height);
            
        }catch(IOException e){
            e.printStackTrace();
        }
        
        return image;
    }
    
}
