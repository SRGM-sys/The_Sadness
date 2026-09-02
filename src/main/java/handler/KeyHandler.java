package handler;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import main.GamePanel;

// Con esto manejaremos el juego mediantes las teclas del teclado

public class KeyHandler implements KeyListener{
    // KeyListener: This listener interface for reciving keyboard events 
    
    GamePanel gp;
    
    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed;
    public boolean checkDrawTime = false;
    
    public KeyHandler(GamePanel gp){
        this.gp = gp;
    }
    
    @Override // Generalmente este no se usa
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
        int code = e.getKeyCode(); // Nos devuelve el código de la tecla presionada
        /*
        titleState = 0;
        playState = 1;
        pauseState = 2;
        dialogueState = 3;
        characterState = 4;
        */
       switch(gp.gameState){
           case 0: titleState(code); break;
           case 1: playState(code); break;
           case 2: pauseState(code); break;
           case 3: dialogueState(code); break;
           case 4: characterState(code); break;
           
       }
            
    }
    
    public void titleState(int code){
        if(code == KeyEvent.VK_W) gp.ui.commandNum--;
        if(code == KeyEvent.VK_S) gp.ui.commandNum++;
        gp.ui.controlCommandNum();

        if(code == KeyEvent.VK_ENTER){
            switch(gp.ui.commandNum){
                case 0: 
                    gp.gameState = gp.playState; 
                    gp.stopMusic();
                    gp.playMusic(0);
                    break;
                case 1: break; // Función aún no disponible
                case 2: System.exit(0); break;
            }
        }
    }
    
    public void playState(int code){
        // El usuario se movera con: WSAD
        if(code == KeyEvent.VK_W) upPressed = true;
        if(code == KeyEvent.VK_S) downPressed = true;
        if(code == KeyEvent.VK_A) leftPressed = true;
        if(code == KeyEvent.VK_D) rightPressed = true;

        if(code == KeyEvent.VK_P) gp.gameState = gp.pauseState;
        if(code == KeyEvent.VK_X) enterPressed = true;

        if(code == KeyEvent.VK_C) gp.gameState = gp.characterState;

        // Show Draw Time: Cada vez que presione T, cambia de estado
        if(code == KeyEvent.VK_T){
            if(checkDrawTime){
                checkDrawTime = false;
            } else{
                checkDrawTime = true;
            }
        }
    }
    
    public void pauseState(int code){
        // Vamos a alternar entre Pausa y Play
        if(code == KeyEvent.VK_P) gp.gameState = gp.playState;
    }
    
    public void dialogueState(int code){
        if(code == KeyEvent.VK_X) gp.gameState = gp.playState;
    }
    
    public void characterState(int code){
        if(code == KeyEvent.VK_C) gp.gameState = gp.playState;
        
        if(code == KeyEvent.VK_W){
            if(gp.ui.slotRow != 0){
                gp.ui.slotRow--;
            }
            gp.soundEffect(10);
            
        }
        if(code == KeyEvent.VK_S){
            if(gp.ui.slotRow < 3){
               gp.ui.slotRow++;
            }
            gp.soundEffect(10); 
        }
        if(code == KeyEvent.VK_A){
            if(gp.ui.slotCol != 0){
                gp.ui.slotCol--;
            }
            gp.soundEffect(10);
        }
        if(code == KeyEvent.VK_D){
            if(gp.ui.slotCol < 4){
               gp.ui.slotCol++;
            }
            gp.soundEffect(10);
        }
        if(code == KeyEvent.VK_X){
            gp.player.selectItem();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode(); 
       
        if(code == KeyEvent.VK_W){
            upPressed = false;
        }
        if(code == KeyEvent.VK_S){
            downPressed = false;
        }
        if(code == KeyEvent.VK_A){
            leftPressed = false;
        }
        if(code == KeyEvent.VK_D){
            rightPressed = false;
        }
    }
}
