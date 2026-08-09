package main;

import entity.Entity;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
//----Estas 3 me sirven para manejar el texto en pantalla----
import java.awt.Shape;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
//-----------------------------------------------------------
import java.io.IOException;
import java.io.InputStream;
import object.Obj_Heart;

/*
* Esta clase manejará todo lo relacionado a la interfaz de usuario
* Mensajes de texto
* Iconos de elementos en pantalla
*/

public class UI {
    
    GamePanel gp;
    Graphics2D g2;
    Font fixedsys;
    
    public boolean messageOn = false;
    public String message;
    int messageCounter = 0;
    
    public boolean gameFinished = false;
    public String currentDialogue;
    public int commandNum = 0;
    
    BufferedImage heart_full, heart_half, heart_blank;

    public UI(GamePanel gp){
        this.gp = gp;
   
        try {
            InputStream is = getClass().getResourceAsStream("/font/DePixelHalbfett.ttf");
            fixedsys = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        // CREATE HUD OBJECT
        Entity heart = new Obj_Heart(gp);
        heart_full = heart.image;
        heart_half = heart.image2;
        heart_blank = heart.image3;
    }
    
    public void showMessage(String text){
        message = text;
        messageOn = true;
    }

    /*
    * Esta función va a dibujar el texto de ayuda de cuantas llaves
    * tenemos en nuestro inventario actualmente
    */
    public void draw(Graphics2D g2){
        this.g2 = g2;
        
        g2.setFont(fixedsys);
        g2.setColor(Color.white);
        
        // TTLE STATE
        if(gp.gameState == gp.titleState){
            drawTitleScreen();
        }
        
        // GAME STATE
        if(gp.gameState == gp.playState){
            drawPlayerLife();
        }
        
        // PAUSE STATE
        if(gp.gameState == gp.pauseState){
            drawPauseScreen();
            drawPlayerLife();
        }
        
        // DIALOGUE STATE
        if(gp.gameState == gp.dialogueState){
            drawDialogueScreen();
        }
        
        // CHARACTER STATE
        if(gp.gameState == gp.characterState){
            drawCharacterScreen();
        }
    }
    
    public void drawTitleScreen(){
        
        // BACKGROUND COLOR
        g2.setColor(new Color(95, 103, 120));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        
        // TITLE NAME
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 60F));
        String text = "The Sadness";
        int x = getXforCenteredText(text);
        int y = gp.tileSize * 2 + 12;
    
        g2.setColor(Color.black);       // Shadow color text
        g2.drawString(text, x+5, y+5);
        g2.setColor(Color.white);       // Main color text
        g2.drawString(text, x, y);
        
        // MAIN CHARACTER IMAGE
        x = gp.screenWidth/2 - (gp.tileSize);
        y += gp.tileSize*2;
        g2.drawImage(gp.player.down1, x, y, gp.tileSize*2, gp.tileSize*2, null);
        
        // MENU OPTIONS
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 30F));
        
        int yM = 8, yS = 0, s = 10;
        x = drawText("NEW GAME", yM, yS);
        if(commandNum == 0) g2.drawString(">", x-gp.tileSize, (gp.tileSize*yM)+yS);
        
        yM++; yS+=s;
        x = drawText("LOAD GAME", yM, yS);
        if(commandNum == 1) g2.drawString(">", x-gp.tileSize, (gp.tileSize*yM)+yS);
        
        yM++; yS+=s;
        x = drawText("QUIT", yM, yS);
        if(commandNum == 2) g2.drawString(">", x-gp.tileSize, (gp.tileSize*yM)+yS);
        
    }
    
    public void drawPlayerLife(){
        
        int x = (gp.tileSize * gp.maxScreenCol) - (gp.tileSize + gp.tileSize/3);
        int y = gp.tileSize/3;
        int i = 0;
        
        // DRAW BLANK HEART
        while(i < gp.player.maxLife/2){
            g2.drawImage(heart_blank, x, y, null);
            i++;
            x -= gp.tileSize;
        }
        
        // DRAW HALF/COMPLETE HEART'S
        // Básicamente vamos a dibujar encima de los corazones blancos
        x += i * gp.tileSize;
        i = 0;
        while(i < gp.player.life){
            g2.drawImage(heart_half, x, y, null);
            i++;
            if(i < gp.player.life){
                g2.drawImage(heart_full, x, y, null);
            }
            i++;
            x -= gp.tileSize;
        }
        
    }
    
    public void drawPauseScreen(){
        // Asignamos fuente y texto
        g2.setFont(fixedsys.deriveFont(Font.PLAIN, 80f));
        String text = "PAUSED";
        
        // Coordenadas del texto
        int x = getXforCenteredText(text);
        int y = gp.screenHeight/2;
        
        // Convertimos el texto en un objeto de diseño
        TextLayout textL = new TextLayout(text, g2.getFont(), g2.getFontRenderContext());
        
        // Se extrae la silueta geométrica del texto
        Shape shape = textL.getOutline(null); // Se crea en (0,0)
        AffineTransform transform = new AffineTransform();
        transform.translate(x, y); // Asignamos las coordenadas del centro
        Shape textoGeometrico = transform.createTransformedShape(shape);
        
        // Se dibuja un contorno negro
        g2.setColor(Color.black);
        g2.setStroke(new BasicStroke(5));
        g2.draw(textoGeometrico);
        
        // Se dibuja el relleno blanco
        g2.setColor(Color.white);
        g2.drawString(text, x, y); // Combinamos todo
    }
    
    public void drawDialogueScreen(){
        // Se va a mostrar un Dialog Window
        int x = gp.tileSize*2;
        int y = gp.tileSize/2;
        int width = gp.screenWidth - (gp.tileSize*4);
        int height = gp.tileSize*3;
        
        drawSubWindow(x,y,width,height);
        
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 20f));
        x += gp.tileSize/2;
        y += gp.tileSize;
        
        // Hacemos esta wea de aquí para que funcione el salto de línea \n
        for(String line : currentDialogue.split(("\n"))){
            g2.drawString(line, x, y);
            y +=35;
        }
    }
    
    public void drawCharacterScreen(){
        
        // CREATE A FRAME
        final int frameX = gp.tileSize * 2;
        final int frameY = gp.tileSize;
        final int frameWidth = gp.tileSize * 5;
        final int frameHeight = gp.tileSize * 10;
        
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);
        
        // TEXT
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(16F));
        
        int textX = frameX + 20;
        int textY = frameY + gp.tileSize;
        final int lineHeight = 45;

        // 1. NOMBRES (ETIQUETAS)
        // Metemos todos los nombres en un arreglo y los dibujamos con un simple bucle
        String[] labels = {
            "Nivel", "Vida", "Destreza", "Fuerza", 
            "Ataque", "Defensa", "XP", "Oro", "Arma", "Escudo"
        };

        for (String label : labels) {
            g2.drawString(label, textX, textY);
            textY += lineHeight;
        }

        // 2. VALORES NUMÉRICOS
        int tailX = (frameX + frameWidth) - 30;
        textY = frameY + gp.tileSize; // Reseteamos la Y para que empiece desde arriba otra vez

        // Extraemos las estadísticas del jugador en el mismo orden
        String[] values = {
            String.valueOf(gp.player.level),
            gp.player.life + "/" + gp.player.maxLife,
            String.valueOf(gp.player.dexterity),
            String.valueOf(gp.player.strength),
            String.valueOf(gp.player.attack),
            String.valueOf(gp.player.defense),
            String.valueOf(gp.player.exp),
            String.valueOf(gp.player.coin)
        };

        for (String value : values) {
            int valueX = getXforAlingToRight(value, tailX);
            g2.drawString(value, valueX, textY);
            textY += lineHeight;
        }

        // 3. IMÁGENES DE EQUIPAMIENTO
        // textY ya quedó posicionado exactamente después de "Oro" gracias al bucle
        textY -= 35; // Tu ajuste original de píxeles

        g2.drawImage(gp.player.currentWeapon.down1, tailX - gp.tileSize + 10, textY, null);
        textY += gp.tileSize;
        g2.drawImage(gp.player.currentShield.down1, tailX - gp.tileSize + 10, textY, null);

    }
    
    public void drawSubWindow(int x, int y, int width, int height){
        // El cuarto parámetro del color indica transparencia
        Color c1 = new Color(0,0,0, 0.75f); // De esta manera indico el porcentaje %
        Color c2 = new Color(255,255,255);
        
        g2.setColor(c1);
        g2.fillRoundRect(x, y, width, height, 35, 35); // Rectangulo redondo
        g2.setColor(c2);
        g2.setStroke(new BasicStroke(2)); // Grosor del borde
        g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);
        
        
    }
    
    // Esta función esta diseñada únicamente para las opciones del juego
    public int drawText(String text, int yM, int yS){
        int x = getXforCenteredText(text);
        int y = (gp.tileSize*yM) + yS;
        g2.drawString(text, x, y);  
        
        return x;
    }
    
    public int getXforCenteredText(String text){
        // Si solo pongo "int x = gp.screenWidth/2;", no se va a presentar el texto en la mitad
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gp.screenWidth/2 - length/2;
    }
    
    public int getXforAlingToRight(String text, int tailX){
        // Si solo pongo "int x = gp.screenWidth/2;", no se va a presentar el texto en la mitad
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = tailX - length;
        return x;
    }
    
    public void controlCommandNum(){
        if(commandNum > 2) commandNum = 0;
        if(commandNum < 0) commandNum = 2;
    }
    
}
