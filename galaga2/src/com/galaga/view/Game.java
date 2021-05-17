
package com.galaga.view;

import com.galaga.controller.Controller;
import com.galaga1.model.Nave;
import com.galaga1.model.Enemy;
import com.galaga1.model.Enemys;
import com.galaga.model.proxy.ProxyImage;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;
import javax.swing.Timer;


public class Game extends JPanel implements ActionListener {

    private boolean isRunning = false;
    private ProxyImage proxyImage;
    private Image background;
    private Nave nave;
    private Enemys enemys;
    private int score;
    private int highScore;

    public Game() {

        proxyImage = new ProxyImage("/assets/background.png");
        background = proxyImage.loadImage().getImage();
        setFocusable(true);
        setDoubleBuffered(false);
        addKeyListener(new GameKeyAdapter());
        Timer timer = new Timer(15, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Toolkit.getDefaultToolkit().sync();
        if (isRunning) {
            ////////////////////////////////
            nave.tick();
            enemys.tick();
            checkColision();
            ///////////////////////////////
        }

        repaint();
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(background, 0, 0, null);
        if (isRunning) {
            ///////////////////////////////
            this.nave.render(g2, this);
            this.enemys.render(g2, this);
            g2.setColor(Color.white);
            g.setFont(new Font("Arial", 1, 20));
            g2.drawString("Your score: " + this.enemys.getPoints(), 10, 20);
            ///////////////////////////////
        } else {
            g2.setColor(Color.white);
             g.setFont(new Font("Arial", 1, 20));
            g2.drawString("Press Enter to Start the Game", Window.WIDTH / 2 - 150, Window.HEIGHT / 2);
            g2.setColor(Color.white);
            g.setFont(new Font("Arial", 1, 15));
        }
        g2.setColor(Color.white);
        g.setFont(new Font("Arial", 1, 20));
        g2.drawString("High Score: " + highScore, Window.WIDTH - 160, 20);

        g.dispose();
    }

    private void restartGame() {
        if (!isRunning) {
            this.isRunning = true;
            this.nave = new Nave(Window.WIDTH / 2, Window.HEIGHT / 2);
            this.enemys = new Enemys();
        }
    }

    private void endGame() {
        this.isRunning = false;
        if (this.enemys.getPoints() > highScore) {
            this.highScore = this.enemys.getPoints();
        }
        this.enemys.setPoints(0);

    }

    private void checkColision() {
        Rectangle rectNave = this.nave.getBounds();
        Rectangle rectEnemy;

        for (int i = 0; i < this.enemys.getEnemys().size(); i++) {
            Enemy tempEnemy = this.enemys.getEnemys().get(i);
            rectEnemy = tempEnemy.getBounds();
            if (rectNave.intersects(rectEnemy)) {
                endGame();
            }
        }
    }

    // Key
    private class GameKeyAdapter extends KeyAdapter {

        private final Controller controller;

        public GameKeyAdapter() {
            controller = new Controller();
        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                restartGame();
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            if (isRunning) {
                controller.controllerReleased(nave, e);
            }
        }
    }
}
