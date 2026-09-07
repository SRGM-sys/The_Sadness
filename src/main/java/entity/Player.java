      
package entity;

import java.awt.AlphaComposite;
import java. awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import main.GamePanel;
import handler.KeyHandler;
import java.util.ArrayList;
import object.Obj_Fireball;
import object.Obj_Key;
import object.Obj_Potion_Blue;
import object.Obj_Shield_Wood;
import object.Obj_Sword_Normal;

public class Player extends Entity{
    
    KeyHandler keyH;
    
    // Estas variables van a ser la camara del jugador
    public final int screenX;
    public final int screenY;
    int standCounter = 0;
    
    // Inventario del Jugador
    public ArrayList<Entity> inventory = new ArrayList<>();
    public final int maxInventorySize = 20;

    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp);
        this.keyH = keyH;
        
        // Esto es para obtener el punto medio de la pantalla
        screenX = gp.screenWidth/2 - gp.tileSize/2;
        screenY = gp.screenHeight/2 - gp.tileSize/2;
        
        solidArea = new Rectangle(8,16,32,32);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y; 
        
        //attackArea.width = 36;
        //attackArea.height = 36;
        
        setDefaultValues();
        getPlayerImage();
        getPlayerAttackImage();
        setItems();
    }
    
    // Vamos a establecer las configuraciones del Player
    public void setDefaultValues(){
        // POSICIÓN INICIAL
        worldX = gp.tileSize * 36;
        worldY = gp.tileSize * 10;
        speed = 4;
        direction = "down";
        
        // ESTADO DEL JUGADOR
        level = 1;
        maxLife = 6;    // 1 vida es la mitad de un corazón
        life = maxLife;
        maxMana = 4;
        mana = maxMana;
        strength = 1;
        dexterity = 1;
        exp = 0;
        nextLevelExp = 5;
        coin = 0;
        currentWeapon = null;
        currentShield = null;
        projectile = null;
        attack = getAttack();
        defense = getDefense();
    }
    
    public void returnByDeath() {
        // 1. Vacía el inventario
        inventory.clear();

        // 2. Reinicia el nivel y stats (dejando intacto el 'projectile' de magia)
        level = 1;
        exp = 0;
        nextLevelExp = 5;
        maxLife = 6;
        life = maxLife;
        maxMana = 4;
        mana = maxMana;
        strength = 1;
        dexterity = 1;
        coin = 0;

        // 3. Añade un hacha al inventario y la equipa
        currentWeapon = new object.Obj_Axe(gp); // Se usa la ruta directa para evitar importar la clase
        inventory.add(currentWeapon);
        inventory.add(new Obj_Key(gp));
        inventory.add(new Obj_Potion_Blue(gp));
        inventory.add(new Obj_Potion_Blue(gp));
        inventory.add(new Obj_Potion_Blue(gp));
        currentShield = null; // El jugador revive sin escudo

        // Actualiza el daño y carga las imágenes de ataque del hacha
        attack = getAttack();
        defense = getDefense();
        getPlayerAttackImage();
    }

    public void setDefaultValuesPositions(){
        worldX = gp.tileSize * 14;
        worldY = gp.tileSize * 36;
        direction = "down";
    } 
    
    public void setDefaultStatePlayer(){
        level = 1;
        maxLife = 6;    // 1 vida es la mitad de un corazón
        life = maxLife;
        maxMana = 4;
        mana = maxMana;
        strength = 1;
        dexterity = 1;
        exp = 0;
        nextLevelExp = 5;
        coin = 0;
        currentWeapon = new Obj_Sword_Normal(gp);
        currentShield = new Obj_Shield_Wood(gp);
        projectile = new Obj_Fireball(gp);
        attack = getAttack();
        defense = getDefense();
    }
    
    
    public void setItems(){
        inventory.clear();
    }
    
    public int getAttack(){
        if(currentWeapon == null) {
            return 0; // El jugador hace 0 daño sin arma
        }
        attackArea = currentWeapon.attackArea;
        return attack = strength * currentWeapon.attackValue;
    }
    
    public int getDefense(){
        if(currentShield == null) {
            return 0; // O puedes retornar 'dexterity' si quieres una defensa base
        }
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
        
        if(currentWeapon == null) return;
        
        if(currentWeapon.type == type_sword){
            atkUp1 = setup("player", "boy_attack_up_1", super.px, super.px*2);
            atkUp2 = setup("player", "boy_attack_up_2", super.px, super.px*2);
            atkDown1 = setup("player", "boy_attack_down_1", super.px, super.px*2);
            atkDown2 = setup("player", "boy_attack_down_2", super.px, super.px*2);
            atkLeft1 = setup("player", "boy_attack_left_1", super.px*2, super.px);
            atkLeft2 = setup("player", "boy_attack_left_2", super.px*2, super.px);
            atkRight1 = setup("player", "boy_attack_right_1", super.px*2, super.px);
            atkRight2 = setup("player", "boy_attack_right_2", super.px*2, super.px);
        }
        
        if(currentWeapon.type == type_axe){
            atkUp1 = setup("player", "boy_axe_up_1", super.px, super.px*2);
            atkUp2 = setup("player", "boy_axe_up_2", super.px, super.px*2);
            atkDown1 = setup("player", "boy_axe_down_1", super.px, super.px*2);
            atkDown2 = setup("player", "boy_axe_down_2", super.px, super.px*2);
            atkLeft1 = setup("player", "boy_axe_left_1", super.px*2, super.px);
            atkLeft2 = setup("player", "boy_axe_left_2", super.px*2, super.px);
            atkRight1 = setup("player", "boy_axe_right_1", super.px*2, super.px);
            atkRight2 = setup("player", "boy_axe_right_2", super.px*2, super.px);
        }
        
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
            
            // CHECK INTERACTIVE TILE COLLISION
            gp.cChecker.checkEntity(this, gp.iTile);
            
            // CHECK EVENT COLLISION
            gp.eHandler.checkEvent();
            
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
            
            gp.keyH.enterPressed = false;
            
        } else{
            standCounter++;
            if(standCounter==20 ){
                spriteNum=1;
                standCounter=0;
            }
        } 
        
        if(gp.mouseH.rightPressed && shotAvailableCounter == 30) {
            // 1. Si tiene magia y recursos
            if(projectile != null && !projectile.alive && projectile.haveResource(this)){
                projectile.set(worldX, worldY, direction, true, this);
                projectile.subtractResource(this);
                gp.projectileList.add(projectile);
                shotAvailableCounter = 0;
                gp.soundEffect(11);
            } 
            // 2. Si intenta disparar sin magia
            else if (projectile == null) {
                gp.ui.addMessage("¡No sabes ningún hechizo!");
                shotAvailableCounter = 0; // Reseteamos el contador para evitar spam visual
            }
        }
        
        if(invincible){
            invincibleCounter++;
            if(invincibleCounter > 60){
                invincible = false;
                invincibleCounter = 0;
            }
            
        }
        
        if(shotAvailableCounter < 30){
            shotAvailableCounter++;
        }
        
        if(life > maxLife){
            life = maxLife;
        }
        if(mana > maxMana){
            mana = maxMana;
        }
        if(life <= 0){
            gp.gameState = gp.gameOverState;
            gp.stopMusic();
            gp.soundEffect(13);
            
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
            damageMonster(monsterIndex, attack, false);
            
            int iTileIndex = gp.cChecker.checkEntity(this, gp.iTile);
            damageInteractiveTile(iTileIndex);
            
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
            if(currentWeapon != null){
                gp.soundEffect(8);
                attacking = true;
            } 
            else if (shotAvailableCounter == 30) { 
                // Reutilizamos el timer de los proyectiles como enfriamiento para el texto
                gp.ui.addMessage("No tienes ningún arma");
                shotAvailableCounter = 0;
            }
        }
    }
    
    public void interactNPC(int i){
        // Cuando i = 999 es pq tocamos a la entidad
        if(i != 999){
            if(gp.keyH.enterPressed){
                gp.gameState = gp.dialogueState;
                gp.npc[gp.currentMap][i].speak();
            }
        }  
    }
    
    public void damageInteractiveTile(int i){
        
        if(i != 999 && gp.iTile[gp.currentMap][i].destructible && 
                gp.iTile[gp.currentMap][i].isCorrectItem(this) && !gp.iTile[gp.currentMap][i].invincible){
            gp.iTile[gp.currentMap][i].playSE();
            gp.iTile[gp.currentMap][i].life--;
            gp.iTile[gp.currentMap][i].invincible = true;
            if(gp.iTile[gp.currentMap][i].life == 0){
                gp.iTile[gp.currentMap][i] = gp.iTile[gp.currentMap][i].getDestroyedForm();
            }
        }
        
    }
    
    public void pickUpObject(int i){
        // Si i = 999, significa que no hemos tocado el objeto
        if(i != 999){
            // PICKUP ONLY ITEMS
            if(gp.obj[gp.currentMap][i].type == type_pickUpOnly){
                gp.obj[gp.currentMap][i].pickUp(this);
                gp.obj[gp.currentMap][i] = null;
            }
            
            // ESTO ES PARA LAS PUERTAS
            else if(gp.obj[gp.currentMap][i].type == type_door){
                if(gp.keyH.enterPressed) { // Verifica que presionaste la 'X'
                    if(gp.obj[gp.currentMap][i].name.equals("door")) {

                        boolean hasKey = false;
                        // Escaneamos el inventario
                        for(int j = 0; j < inventory.size(); j++) {
                            if(inventory.get(j).name.equals("key")) {
                                gp.soundEffect(3); // Sonido unlock
                                gp.obj[gp.currentMap][i] = null; // Puerta destruida
                                inventory.remove(j); // Gastamos la llave
                                gp.ui.addMessage("Puerta desbloqueada");
                                gp.eHandler.checkDoorsOpened();
                                hasKey = true;
                                break;
                            }
                        }
                        if(!hasKey) {
                            gp.ui.addMessage("Necesitas una llave");
                        }
                    }
                }
            }
            
            // INVENTORY ITEMS
            else{
                String text;
            
                if(inventory.size() != maxInventorySize){
                    inventory.add(gp.obj[gp.currentMap][i]);
                    gp.soundEffect(1);
                    text = "Conseguiste: " + gp.obj[gp.currentMap][i].name;
                }
                else{
                    text = "El inventario se encuentra lleno";
                }

                gp.ui.addMessage(text);
                gp.obj[gp.currentMap][i] = null;
            }
            
        }
    }
    
    public void contactMonster(int i){
        if (i != 999 && !invincible && !gp.mon[gp.currentMap][i].dying){
            gp.soundEffect(7);
            
            int damage = gp.mon[gp.currentMap][i].attack - defense;
            if(damage < 0){
                damage = 0;
            }
            
            life -=damage;
            invincible = true;
        }
    }
    
    public void damageMonster(int i, int attack, boolean isMagic){
        if(i != 999){
            if(!gp.mon[gp.currentMap][i].invincible){
                if (gp.mon[gp.currentMap][i].name.equals("Green Slime") && !isMagic) {
                    gp.soundEffect(15);
                    gp.ui.addMessage("¡Ataque físico ineficaz!");
                    
                    gp.mon[gp.currentMap][i].invincible = true; 
                    gp.mon[gp.currentMap][i].damageReaction();
                    
                    return; // Corta la ejecución: no resta vida ni da experiencia
                }
                
                gp.soundEffect(6);
                
                int damage = attack - gp.mon[gp.currentMap][i].defense;
                if(damage < 0){
                    damage = 0;
                }
                gp.mon[gp.currentMap][i].life -= damage;
                
                gp.mon[gp.currentMap][i].invincible = true;
                gp.mon[gp.currentMap][i].damageReaction();
                
                if(gp.mon[gp.currentMap][i].life <= 0){
                    gp.mon[gp.currentMap][i].dying = true;
                    exp += gp.mon[gp.currentMap][i].exp;
                    gp.ui.addMessage("+ "+ gp.mon[gp.currentMap][i].exp + " xp");
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
            attack = getAttack();
            defense = getDefense();
            
            gp.soundEffect(9);
            gp.gameState = gp.dialogueState;
            gp.ui.currentDialogue = "Subiste al nivel "+level +", sientes como tu \n determinación aumenta";
            
            life = maxLife;
            mana = maxMana;
        }
    }
    
    public void selectItem(){
        int itemIndex = gp.ui.getItemIndexOnSlot();
        
        if(itemIndex < inventory.size()){
            Entity selectedItem = inventory.get(itemIndex);
            
            if(selectedItem.type == type_sword || selectedItem.type == type_axe){
                currentWeapon = selectedItem;
                attack = getAttack();
                getPlayerAttackImage();
            }
            if(selectedItem.type == type_shield){
                currentShield = selectedItem;
                defense = getDefense();
            }
            if(selectedItem.type == type_consumable && selectedItem.use(this)){
                inventory.remove(itemIndex);
            }
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
