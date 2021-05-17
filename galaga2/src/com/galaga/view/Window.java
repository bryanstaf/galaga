
package com.galaga.view;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;


public class Window {
    public static int WIDTH = 800;
    public static int HEIGHT = 600;
    public Window(int width, int height, String title, Game game) {
        JFrame frame = new JFrame();
        frame.add(game);
        frame.setTitle(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMaximumSize(new Dimension(width, height));
        frame.setPreferredSize(new Dimension(width, height));
        frame.setMinimumSize(new Dimension(width, height));
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
        
    }
    public static void main(String[] args) {
        Game game = new Game();
        
        java.awt.EventQueue.invokeLater(() -> {
            
            Window window = new Window(WIDTH, HEIGHT, "Galaga", game);
            
        });
    }
}