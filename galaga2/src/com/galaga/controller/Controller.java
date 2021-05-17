
package com.galaga.controller;

import com.galaga1.model.Nave;
import java.awt.event.KeyEvent;


public class Controller implements IStrategy {

    @Override
    public void controller(Nave nave, KeyEvent kevent) {
    }

    @Override
    public void controllerReleased(Nave nave, KeyEvent kevent) {
        if(kevent.getKeyCode() == KeyEvent.VK_SPACE) {
            nave.jump();
        }
        
        if(kevent.getKeyCode() == KeyEvent.VK_RIGHT) {
            nave.right();
        }
        
        if(kevent.getKeyCode() == KeyEvent.VK_LEFT) {
            nave.left();
        }
        
        
        if(kevent.getKeyCode() == KeyEvent.VK_X) {
            nave.atack();
        }
    }
    
}
