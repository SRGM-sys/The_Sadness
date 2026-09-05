
package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import main.GamePanel;
import main.UtilityTool;

public class TileManager {
    
    GamePanel gp;
    public Tile[] tile;
    public int mapTileNum [] [] []; //Usaremos los números del archivo de texto para dibujarlo
    boolean drawPath = false;
    
    ArrayList<String> fileNames = new ArrayList<>();
    ArrayList<String> collisionStatus = new ArrayList<>();
    
    public TileManager(GamePanel gp) {
        this.gp = gp;
        
        // READ TILE DATA FILE
        InputStream is = getClass().getResourceAsStream("/maps/tiledata.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        
        // GETTING TILE STATES AND COLLISION INFO FROM THE FILE
        String line;
        
        try{
            while((line = br.readLine()) != null){
                fileNames.add(line);
                collisionStatus.add(br.readLine());
            }
            
            br.close();
        } catch(IOException e){
            e.printStackTrace();
        }
        
        // Inicializamos la base
        tile = new Tile[fileNames.size()]; // Cantidad de diferentes bloques
        getTileImage();
        
        
        // Obtenemos el maxWorldCol & maxWorldRow
        is = getClass().getResourceAsStream("/maps/worldmap.txt");
        br = new BufferedReader(new InputStreamReader(is));
        
        try{
            String line2 = br.readLine();
            String maxFile[] = line2.split(" ");
            
            gp.maxWorldCol = maxFile.length;
            gp.maxWorldRow = maxFile.length;
            mapTileNum = new int [gp.maxMap] [gp.maxWorldCol] [gp.maxWorldRow];  
            
            br.close();
            
        } catch(IOException e){
            e.printStackTrace();
        }
        
        loadMap("/maps/worldmap.txt", 0);
       
    }
    
    public void getTileImage(){
        
        for(int i = 0; i < fileNames.size(); i++){
            
            String fileName;
            boolean collision;
            
            // Get fileName
            fileName = fileNames.get(i);
            
            // Get a collision status
            if(collisionStatus.get(i).equals("true")){
                collision = true;
            } else{
                collision = false;
            }
            
            setup(i, fileName, collision);
            
        }
    }
    
    // Esta función va a optimizar la creación de los mosaicos y su escala
    public void setup(int index, String image, boolean collision){
        UtilityTool uTool = new UtilityTool();
        
        try{
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/"+image));
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
