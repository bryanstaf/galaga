
package com.galaga1.model;

import com.galaga.model.proxy.ProxyImage;
import com.galaga.view.Window;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.List;

public class Enemy extends GameObject {

    private ProxyImage proxyImage;
    private int base = Window.HEIGHT -400;
    private int speed = 5;
    
    private List<Bullet> bullet;
    
    private List<Enemy> enemys;
    
    public Enemy(int x, int y) {
        super(x, y);
        if (proxyImage == null) {
            proxyImage = new ProxyImage("/assets/enemy.png");

        }
        this.image = proxyImage.loadImage().getImage();
        this.width = image.getWidth(null);
        this.height = image.getHeight(null);
        bullet = new ArrayList<>();//aqui
    }

    private void initEnemys() {

        int last = base;
        int randWay = 10;//random.nextInt(10);

        
       for (int i = 0; i < 1; i++) {
            Bullet tempBullet = new Bullet(x, y);//aqui     
            tempBullet.setDy(speed);//aqui
            last = tempBullet.getY() - tempBullet.getHeight();//aqui
            if (i < randWay || i > randWay + 4) {
                bullet.add(tempBullet);//aqui
            }

        }

    }
    
    
    
    @Override
    public void tick() {
        //bala muere
        for (int i = 0; i < bullet.size(); i++) {
            bullet.get(i).tick();
            if (bullet.get(i).getY() > 800) {
                bullet.remove(bullet.get(i));
            }
        }
        
        //crea bala
        if (bullet.isEmpty()) {
            initEnemys();
        }
        
        
    }

    
    
    
    
    @Override
    public void render(Graphics2D g, ImageObserver obs) {
        g.drawImage(image, x, y, obs);
        
                for (int i = 0; i < bullet.size(); i++) {
            
            bullet.get(i).render(g, obs);//aqui
        }
        

    }

    
   public List<Bullet> getBullets() {
        return bullet;
    } 
    
    
    
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
    
    
    
    
    
}
