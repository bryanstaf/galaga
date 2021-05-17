
package com.galaga1.model;

import com.galaga.view.Window;
import java.awt.Graphics2D;

import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Enemys {

    private int base = Window.HEIGHT -400;

    private List<Enemy> enemys;
    private Random random;
    private int points = 0;
    private int speed = 5;
    private int changeSpeed = speed;

    public Enemys() {
        enemys = new ArrayList<>();
        random = new Random();
        initEnemys();
    }

    private void initEnemys() {

        int last = base;
        int randWay = 10;
        int ran=60;
        
       for (int i = 0; i < 2; i++) {

            Enemy tempEnemy = new Enemy(ran, last);
            ran=ran+50;
            tempEnemy.setDy(speed);
            last = tempEnemy.getY() - tempEnemy.getHeight();
            if (i < randWay || i > randWay + 4) {
                enemys.add(tempEnemy);
            }

        }

    }

    public void tick() {
        
        for (int i = 0; i < enemys.size(); i++) {
            enemys.get(i).tick();
            if (enemys.get(i).getX() < 0) {
                enemys.remove(enemys.get(i));
            }
        }
        if (enemys.isEmpty()) {
            
            initEnemys();
            
        }

    }

    public void render(Graphics2D g, ImageObserver obs) {
        for (int i = 0; i < enemys.size(); i++) {
            enemys.get(i).render(g, obs);
        }
        
    }

    public List<Enemy> getEnemys() {
        return enemys;
    }

    public void setEnemys(List<Enemy> enemys) {
        this.enemys = enemys;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

}
