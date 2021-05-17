
package com.galaga.controller;

import com.galaga1.model.Nave;
import java.awt.event.KeyEvent;

public interface IStrategy {
    
    public void controller(Nave nave, KeyEvent kevent);
    public void controllerReleased(Nave nave, KeyEvent kevent);
}
