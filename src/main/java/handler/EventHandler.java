package handler;

import main.EventRect;
import main.GamePanel;


// Esta clase manejara diferentes eventos del jugador en puntos específicos del mapa

public class EventHandler {
    
    GamePanel gp;
    EventRect eventRect[][];
    
    /* Estas 2 variables de aqui nos sirven para
    * Que un evento vuelva a ocurrir luego de que el jugador se aleje cierta distancia
    */
    int previusEventX, previusEventY;
    boolean canTouchEvent = false;
    
    public EventHandler(GamePanel gp) {
        this.gp = gp;
        
        eventRect = new EventRect[gp.maxWorldCol][gp.maxWorldRow];
        
        int col = 0;
        int row = 0;
        while(col < gp.maxWorldCol && row < gp.maxWorldRow){
            eventRect[col][row] = new EventRect();
            eventRect[col][row].x = 23;
            eventRect[col][row].y = 23;
            eventRect[col][row].width = 2;
            eventRect[col][row].height = 2;
            eventRect[col][row].eventRectDefaultX = eventRect[col][row].x;
            eventRect[col][row].eventRectDefaultY = eventRect[col][row].y;
            
            col++;
            if(col == gp.maxWorldCol){
                col = 0;
                row++;
            }
        }
        
        
    }
    
    public void checkEvent(){
        
        // Comprobamos que el usuario esté a 1 cuadro de distancia luego de activar un evento
        // Para que vuelva a funcionar nuevamente 
        int xDistance = Math.abs(gp.player.worldX - previusEventX);
        int yDistance = Math.abs(gp.player.worldY - previusEventY);
        int distance = Math.max(xDistance, yDistance);
        
        if(distance > gp.tileSize) canTouchEvent = true;
        
        if(canTouchEvent){
            // Estas van a ser las coordenadas del evento y la dirección del jugador
            if(hit(27,16, "right")) damagePit(27, 16, gp.dialogueState);
            if(hit(23,12, "up")) healingPool(23, 12, gp.dialogueState);
        }   
    }
    
    // Va a ver si el usuario se acerca al rectangulo evento
    public boolean hit(int col, int row, String reqDirection){
        
        boolean hit = false;
        gp.player.solidArea.x += gp.player.worldX;
        gp.player.solidArea.y += gp.player.worldY;
        eventRect[col][row].x += col*gp.tileSize;
        eventRect[col][row].y += row*gp.tileSize;
        
        if(gp.player.solidArea.intersects(eventRect[col][row]) && !eventRect[col][row].eventDone){
            // QUE XD: Bueno básicamente si ambos colisionan es true
            if(gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")){
                hit = true;
                // Luego de que el jugador active el evento, tomamos sus coordenadas
                previusEventX = gp.player.worldX;
                previusEventY = gp.player.worldY;
                
            }
        }
        
        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;
        eventRect[col][row].x = eventRect[col][row].eventRectDefaultX;
        eventRect[col][row].y = eventRect[col][row].eventRectDefaultY;
        
        return hit;
        
    }
    
    public void damagePit(int col, int row, int gameState){
        
        gp.gameState = gameState;
        gp.ui.currentDialogue = "Pisaste una trampa";
        gp.player.life--;
        canTouchEvent = false;
        
    }
    
    public void healingPool(int col, int row, int gameState){
        if(gp.keyH.enterPressed){
            gp.gameState = gameState;
            gp.ui.currentDialogue = "Observar el lago te llena de \ndeterminación";
            gp.player.life = gp.player.maxLife;
            gp.player.mana = gp.player.maxMana;
        }
        
        gp.keyH.enterPressed = false;
    }
    
    
    
}
