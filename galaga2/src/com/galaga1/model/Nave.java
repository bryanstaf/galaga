
package com.galaga1.model;

import com.galaga.model.proxy.ProxyImage;
import com.galaga.view.Window;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.List;

public class Nave extends GameObject {
private int base = Window.HEIGHT -400;
    private List<Bullet> bullet;
    
    private List<Enemy> enemys;
    private int speed = -5;
    boolean create=false;
    
    private ProxyImage proxyImage;
    private Enemy[] enemy;
    public Nave(int x, int y){
        super(x, y);
        if(proxyImage == null) {
            proxyImage = new ProxyImage("/assets/nave.png");
        }
        this.image = proxyImage.loadImage().getImage();
        this.width = image.getWidth(null);
        this.height = image.getHeight(null);
        this.x -= width;
        this.y -= height;
        enemy = new Enemy[1];
        enemy[0] = new Enemy(900, Window.HEIGHT - 60);
        this.dy = 4;
        
        
        bullet = new ArrayList<>();
    }
    
    
    private void initEnemys() {

        int last = base;
        int randWay = 10;

        
       for (int i = 0; i < 1; i++) {
            Bullet tempBullet = new Bullet(x, y);   
            tempBullet.setDy(speed);
            last = tempBullet.getY() - tempBullet.getHeight();
            if (i < randWay || i > randWay + 4) {
                bullet.add(tempBullet);
            }

        }

    }
    
    
    
    @Override
    public void tick() {
        if(dy < 5) {
            dy += 2;
        }
        this.y += dy;
        
        enemy[0].tick();
        checkWindowBorder();
        
        
        //bala muere
        for (int i = 0; i < bullet.size(); i++) {
            bullet.get(i).tick();//aqui
            if (bullet.get(i).getY() < 0) {
                bullet.remove(bullet.get(i));
            }
        }
        
        //crea bala
        if (create==true) {
            initEnemys();
            create=false;
        }
        
        
    }
    public void jump() {
        if(dy > 0) {
            dy = 0;
        }
        dy -= 15;
    }
    
    public void right() {
        x += 15;
    }
    
    public void left() {
        x -= 15;
    }
    
    public void atack(){
        create=true;
    }
    
    
    private void checkWindowBorder() {
        if(this.x > Window.WIDTH-50) {
            this.x = Window.WIDTH-50;
        }
        if(this.x < 10) {
            this.x = 10;
        }
        if(this.y > Window.HEIGHT - 50) {
            this.y = Window.HEIGHT - 50;
        }
        if(this.y < 0) {
            this.y = 0;
        }
    }

    @Override
    public void render(Graphics2D g, ImageObserver obs) {
        g.drawImage(image, x, y, obs);
        enemy[0].render(g, obs);
        
        for (int i = 0; i < bullet.size(); i++) {
            
            bullet.get(i).render(g, obs);
        }
        
    }
    
    
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}
