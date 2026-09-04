
package handler;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseHandler implements MouseListener{
    
    public boolean leftPressed = false;
    public boolean rightPressed = false;

    @Override
    public void mousePressed(MouseEvent e) {
        // BUTTON1 representa el clic izquierdo del mouse
        if(e.getButton() == MouseEvent.BUTTON1){
            leftPressed = true;
        }
        if(e.getButton() == MouseEvent.BUTTON3){
            rightPressed = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1){
            leftPressed = false;
        }
        if(e.getButton() == MouseEvent.BUTTON3){
            rightPressed = false;
        }
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
    
    
    
}
