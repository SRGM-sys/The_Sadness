
package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;
import main.GamePanel;
import main.UtilityTool;

public class TileManager {
    
    GamePanel gp;
    public Tile[] tile;
    public int mapTileNum [] [] []; //Usaremos los números del archivo de texto para dibujarlo
    boolean drawPath = false;
    
    public TileManager(GamePanel gp) {
        this.gp = gp;
        tile = new Tile[50]; // Cantidad de diferentes bloques
        mapTileNum = new int [gp.maxMap] [gp.maxWorldCol] [gp.maxWorldRow];  
        getTileImage();
        loadMap("/maps/world01.txt", 0);
        //loadMap("/maps/world02.txt", 1);
    }
    
    public void getTileImage(){
        
        // Index, Image, Collision
        // No usaremos las posiciones del 0 al 9
        setup(0, "grass00", false);
        setup(1, "grass00", false);
        setup(2, "grass00", false);
        setup(3, "grass00", false);
        setup(4, "grass00", false);
        setup(5, "grass00", false);
        setup(6, "grass00", false);
        setup(7, "grass00", false);
        setup(8, "grass00", false);
        setup(9, "grass00", false);
        
        // A partir de aquí usaremos los tiles
        // Esto lo hago para que sea más facil de leer los números en el .txt
        setup(10, "grass00", false);
        setup(11, "grass01", false);
        setup(12, "water00", false);
        setup(13, "water01", true);
        setup(14, "water02", true);
        setup(15, "water03", true);
        setup(16, "water04", true);
        setup(17, "water05", true);
        setup(18, "water06", true);
        setup(19, "water07", true);
        setup(20, "water08", true);
        setup(21, "water09", true);
        setup(22, "water10", true);
        setup(23, "water11", true);
        setup(24, "water12", true);
        setup(25, "water13", true);
        setup(26, "road00", false);
        setup(27, "road01", false);
        setup(28, "road02", false);
        setup(29, "road03", false);
        setup(30, "road04", false);
        setup(31, "road05", false);
        setup(32, "road06", false);
        setup(33, "road07", false);
        setup(34, "road08", false);
        setup(35, "road09", false);
        setup(36, "road10", false);
        setup(37, "road11", false);
        setup(38, "road12", false);
        setup(39, "earth", false);
        setup(40, "wall", true);
        setup(41, "tree", true);
    }
    
    // Esta función va a optimizar la creación de los mosaicos y su escala
    public void setup(int index, String image, boolean collision){
        UtilityTool uTool = new UtilityTool();
        
        try{
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/"+image+".png"));
            tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
            tile[index].collision = collision;
            
        } catch(IOException e){
            e.printStackTrace();
        }
    }
    
    public void loadMap(String filePath, int map){
        try{
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            
            int col = 0;
            int row = 0;
                    
            while(col < gp.maxWorldCol && row < gp.maxWorldRow){
                String  line = br.readLine();
                while(col < gp.maxWorldCol){
                    String numbers[] = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[map][col][row] = num;
                    col++;
                }
                if(col == gp.maxWorldCol){
                    col = 0;
                    row++;
                }
            }

            br.close();
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void draw(Graphics2D g2){
        /* SEPARACIÓN DE IMÁGENES
        Debe ir de 48 en 48, para que esten uno alado del otro*/
      
        int worldCol = 0;
        int worldRow = 0;
     
        
        // Este bucle pintará toda la pantalla visible
        while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow){
            
            // Quiero obtener el número que se encuentra en [col] [row]
            int index = mapTileNum[gp.currentMap][worldCol][worldRow];
            
            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
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
                g2.drawImage(tile[index].image, screenX, screenY, null);
            }
            
            worldCol++;
            
            if(worldCol == gp.maxWorldCol){
                worldCol = 0;
                worldRow++;
                
            }   
        }
        
    }
    
    
    
    
}
