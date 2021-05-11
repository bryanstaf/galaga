package Juego;

import javax.swing.JOptionPane;

public class Bullet extends Thread{
	private int range = 1;
	private int posx;
	private int posy;
	private int bombTimer = 2000; //en miliseg
	private Stage escenario;
	private player jugador;
	
	public void run() {
		try {
			sleep(bombTimer);
			explosion();
			render();
			sleep(1000);
			clearExplosion();
			render();
		} catch (Exception e) {}
	}
	
        
        
	public Bullet() {}
	public Bullet(int posx, int posy, Stage escenario, player jugador) {
		this.posx = posx;
		this.posy = posy;
		this.escenario = escenario;
		this.jugador = jugador;
	}
	
	public int getExplosionRange() {
		return this.range;
	}
	
	public void explosion() {
		int explosionRange = getExplosionRange();
		char icon; 
		int jugadorX = this.jugador.getx();
		int jugadorY = this.jugador.gety();
		
		for (int i = -explosionRange; i <= explosionRange; i++) {
			if (getX() + i>= 0 && getY()>=0) {
				icon = escenario.getCaracterEnCoordenada(getX() + i, getY());
				if ( icon == '#') continue; 
				if ( jugadorX == getX() + i && jugadorY == getY() && !this.jugador.getIsDead()) {
                                    this.jugador.setIsDead();
                                    JOptionPane.showMessageDialog(null, "Has muerto 1");
                                    escenario.setUserAlive(false);
				}
				if ( icon == 'o' && !this.jugador.getIsDead() ) {
                                    this.jugador.setIsDead();
                                    JOptionPane.showMessageDialog(null, "Has muerto 2");
                                    escenario.setUserAlive(false);
				}
				escenario.setCaracterEnCoordenada('*', getX() + i, getY());					
			}
		}
		for (int i = -explosionRange; i <= explosionRange; i++) {
			if (getX()>= 0 && getY() + i>=0) {
				icon = escenario.getCaracterEnCoordenada(getX(), getY() + i);
				if ( icon == '#') continue;
				if ( jugadorX == getX() && jugadorY == getY() + i && !this.jugador.getIsDead()) {
					this.jugador.setIsDead();
                                        JOptionPane.showMessageDialog(null, "Has muerto 3");
					escenario.setUserAlive(false);
				}
				if ( icon == 'o' && !this.jugador.getIsDead()) {
                                        this.jugador.setIsDead();
					JOptionPane.showMessageDialog(null, "Has muerto 4");
					escenario.setUserAlive(false);
				}
				escenario.setCaracterEnCoordenada('*', getX(), getY() + i);
			}
		}
	}
	
	public void clearExplosion() {
		int explosionRange = getExplosionRange();
		int x = getX();
		int y = getY();
		for (int i = -explosionRange; i <= explosionRange; i++) {
			if (x + i>= 0 && y>=0 && escenario.getCaracterEnCoordenada(getX() + i, getY()) != '#')
				escenario.setCaracterEnCoordenada(' ', x + i, y);
		}
		for (int i = -explosionRange; i <= explosionRange; i++) {
			if (x>= 0 && y + i>=0 && escenario.getCaracterEnCoordenada(getX(), getY() + i) != '#')
				escenario.setCaracterEnCoordenada(' ', x, y + i);
		}
	}
        
        
	
	public void render() {
		escenario.mostrarEscenario();
    }
	
	public int getX() {
		return this.posx;
	}
	
	public int getY() {
		return this.posy;
	}
}
