package main;

import handler.KeyHandler;
import handler.EventHandler;
import entity.Entity;
import entity.Player;
import entity.Projectile;
import handler.MouseHandler;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JPanel;
import tile.TileManager;
import tile_interactive.InteractiveTile;

public class GamePanel extends JPanel implements Runnable{
    
    // SCREEN SETTINGS
    final int OriginalTileSize = 16; // 16px * 16px
    // Escalamos los 32 pixeles, para que no se vea minusculo 
    final int scale = 3;
    public final int tileSize = OriginalTileSize * scale;
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol;  // 768 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 576 pixels
    
    // WORLD SETTINGS
    public int maxWorldCol;
    public int maxWorldRow;
    public final int maxMap = 10;
    public int currentMap = 0;
    
    // FPS
    int FPS = 60;
    
    // SYSTEM
    public TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);
    public Sound music = new Sound(); // Main music
    public Sound sound = new Sound(); // Sound Effects
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    public EventHandler eHandler = new EventHandler(this);
    public MouseHandler mouseH = new MouseHandler();
    Thread gameThread;
  
    
    // ENTITY & OBJECT
    public Player player = new Player(this, keyH);
    public Entity obj[][] = new Entity[maxMap][20]; // Mostrar 10 objetos a la vez
    public Entity npc[][] = new Entity[maxMap][10];
    public Entity mon[][] = new Entity[maxMap][20];
    public InteractiveTile iTile[][] = new InteractiveTile[maxMap][50];
    public ArrayList<Entity> projectileList = new ArrayList<>();
    ArrayList<Entity> entityList = new ArrayList<>();
    
    // GAME STATE (Manejar cada estadod el juego)
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int characterState = 4;
    public final int gameOverState = 5;
    
    
    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true); //Mayor renderizado
        this.addKeyListener(keyH);
        this.addMouseListener(mouseH);
        this.setFocusable(true);
    }
    
    public void setupGame(){
        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setMonster();
        aSetter.setInteractiveTile();
        playMusic(5);
        gameState = titleState;
    }
    
    public void retry(){
        player.setDefaultValuesPositions();
        player.setDefaultStatePlayer();

        player.setItems();
        projectileList.clear();
        
        // LIMPIEZA TOTAL BIDIMENSIONAL
        for(int map = 0; map < maxMap; map++) {
            for (int i = 0; i < obj[map].length; i++) {
                obj[map][i] = null;
            }
            for (int i = 0; i < npc[map].length; i++) {
                npc[map][i] = null;
            }
            for (int i = 0; i < mon[map].length; i++) {
                mon[map][i] = null;
            }
            for (int i = 0; i < iTile[map].length; i++) {
                iTile[map][i] = null;
            }
        }
        
        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setMonster();
        aSetter.setInteractiveTile();
    }
    
    /*
    public void restart(){
        player.setDefaultValuesPositions();
        player.setDefaultStatePlayer();
    }
    */
        
    public void startGameThread(){
        gameThread = new Thread(this); // Le pasamos esta clase (Implementa Run)
        gameThread.start();
    }

    // Haré que esta clase se comporte como un Hilo para simular movimiento (FPS)
    @Override
    public void run() {
        /* Tendrá 2 funcionalidades
        1. UPDATE: update information such as character position
        2. DRAW: draw the screen with the update information*/
        
        // Necesito controlar el tiempo para controlar la movilidad
        double drawInterval = 1000000000 / FPS; // 0.01666 seconds
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;
        
        
        while(gameThread != null){
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;
            if(delta >= 1){
                update();
                repaint();
                delta--;
                drawCount++;
            }
            if(timer >= 1000000000){
                drawCount = 0;
                timer = 0;
            }            
        }
    }
    
    public void update(){
        
        if(gameState == playState){
            // El jugador y los NPC se pueden mover
            player.update(); 
            
            for(int i = 0; i < npc[1].length; i++){
                if(npc[currentMap][i] != null){
                    npc[currentMap][i].update();
                }
            }
            
            for (int i = 0; i < mon[1].length; i++) {
                if (mon[currentMap][i] != null) {
                    if (mon[currentMap][i].alive && !mon[currentMap][i].dying) {
                        mon[currentMap][i].update();
                    }

                    if (!mon[currentMap][i].alive) {
                        mon[currentMap][i].checkDrop();
                        mon[currentMap][i] = null; 
                    }
                }
            }
            
            for (int i = 0; i < projectileList.size(); i++) {
                if (projectileList.get(i) != null) {
                    if (projectileList.get(i).alive){
                        projectileList.get(i).update();
                    }

                    if (!projectileList.get(i).alive) {
                        projectileList.remove(i);
                    }
                }
            }
            
            for(int i = 0; i < iTile[1].length; i++){
                if(iTile[currentMap][i] != null){
                    iTile[currentMap][i].update();
                }
            }
            
        }
            
        if(gameState == pauseState){
            // Por ahora nada
        }
        
        
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g; //Casting: Graphics2D extends Graphics
        
        // DEBUG TIME START
        long drawStart = 0;
        if(keyH.checkDrawTime){
            drawStart = System.nanoTime();
        }
        
        // TITLE SCREEN
        if(gameState == titleState){
            ui.draw(g2);
            
        } 
        
        // GAME 
        else{ 
            // TILE
            tileM.draw(g2); // Es importante dibujar antes del jugador para no taparlo
            
            for(int i = 0; i < iTile[1].length; i++){
                if(iTile[currentMap][i] != null){
                    iTile[currentMap][i].draw(g2);
                }
            }
            
            entityList.add(player);
            
            for(int i = 0; i < npc[1].length; i++){
                if(npc[currentMap][i] != null){
                    entityList.add(npc[currentMap][i]);
                }
            }
            
            for(int i = 0; i < obj[1].length; i++){
                if(obj[currentMap][i] != null){
                    entityList.add(obj[currentMap][i]);
                }
            }
            
            for(int i = 0; i < mon[1].length; i++){
                if(mon[currentMap][i] != null){
                    entityList.add(mon[currentMap][i]);
                }
            }

            
            for(int i=0 ; i < projectileList.size(); i++){
                if (projectileList.get(i) != null) {
                    entityList.add(projectileList.get(i));
                }
            }
            
            // Ordenamos con un sort personalizado (Orden de dibujo)
            // Expresión Lambda
            Collections.sort(entityList, (Entity e1, Entity e2) -> {
                int result = Integer.compare(e1.worldY, e2.worldY);
                return result;
            });
            
            // DRAW ENTITYS
            for(int i=0; i < entityList.size(); i++){
                entityList.get(i).draw(g2);
            }
            // EMPTY ENTITY LIST
            entityList.clear();
            
            // UI
            ui.draw(g2);
        }
        
        
        
        // DEBUG TIME END
        if(keyH.checkDrawTime){
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;
            // Vamos a presentar el tiempo de Debug en pantalla
            g2.setColor(Color.white);
            g2.drawString("Draw Time: "+passed, 400, 550); //Coodenadas de pantalla
        }
        
        g2.dispose(); // Buena practica para ahorrar memoria
    }
    
    public void playMusic(int i){
        music.setFile(i);
        music.play();
        music.loop();
    }
    
    public void stopMusic(){
        music.stop();
    }
    
    public void soundEffect(int i){
        sound.setFile(i);
        sound.play();
    }
            
    
}
