import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public final class Gracz_Weze implements KeyListener{// Definicja gracza w grze Wê¿e.
	
	private Color kolor;
	private short ilosc_ptk = 0;
	private float predkosc, pozycjax, pozycjay, grubosc, kat,poz_x_pop, poz_y_pop;
	private boolean lewo = false, prawo = false, czy_zyje, kolizja = false;
	protected char lewo_znak , prawo_znak;
	
	protected Gracz_Weze (Color kolor) {
		this.kolor = kolor;
	}
	
	protected void nowaPozycja() { // Obliczenie nowego po³o¿enia œrodka g³owy wê¿a.
		
		//obliczanie nowego k¹ta
		if(this.lewo == true && this.prawo == true)/*pass*/ {
    	} else if (lewo == true) {
    		this.kat += 3;
    	} else if (prawo == true) {
    		this.kat -= 3;
    	}     
		this.kat %=360;
		
		setPozycjax((float) (getPoz_x_pop() + this.predkosc * Math.sin(this.kat * Math.PI/180)));
		setPozycjay((float) (getPoz_y_pop() + this.predkosc * Math.cos(this.kat * Math.PI/180)));		     
	}

	protected void setPredkosc(float predkosc) {
		this.predkosc = predkosc;
	}
		
	protected void setLewo(boolean lewo) {
		this.lewo = lewo; 
	}
	
	protected void setPrawo(boolean prawo) {
		this.prawo = prawo;
	}

	protected void setKat(float kat) {
		this.kat = kat;
	}
	
	protected void setGrubosc(float grubosc) {
		this.grubosc = grubosc;
	}
	
	protected void setIlosc_ptk(short ilosc_ptk) {
		this.ilosc_ptk = ilosc_ptk;
	}
	
	protected boolean czyZyje() {
		return czy_zyje;
	}

	protected void setCzy_zyje(boolean czy_zyje) {
		this.czy_zyje = czy_zyje;
	}

	protected float getKat(){
		return kat;
	}
	
	protected float getGrubosc() {
		return grubosc;
	}
	
	protected short getIlosc_ptk() {
		return ilosc_ptk;
	}

	@Override
	public void keyTyped(KeyEvent es) {
		
	}
	
	@Override
	public void keyPressed(KeyEvent es) {
		if (es.getKeyChar() == this.lewo_znak ) {
			this.setLewo(true);
		}
		if (es.getKeyChar() == this.prawo_znak ) {
			this.setPrawo(true);
		}
	}
	
	@Override
	public void keyReleased(KeyEvent es) {
		if (es.getKeyChar() == this.lewo_znak ) {
			this.setLewo(false);
		}
		if (es.getKeyChar() == prawo_znak ) {
			this.setPrawo(false);
		}
	}

	protected float getPoz_y_pop() {
		return poz_y_pop;
	}

	protected void setPoz_y_pop(float poz_y_pop) {
		this.poz_y_pop = poz_y_pop;
	}

	protected float getPoz_x_pop() {
		return poz_x_pop;
	}

	protected void setPoz_x_pop(float poz_x_pop) {
		this.poz_x_pop = poz_x_pop;
	}

	protected Color getKolor() {
		return kolor;
	}

	protected float getPozycjax() {
		return pozycjax;
	}

	protected void setPozycjax(float pozycjax) {
		this.pozycjax = pozycjax;
	}

	protected float getPozycjay() {
		return pozycjay;
	}

	protected void setPozycjay(float pozycjay) {
		this.pozycjay = pozycjay;
	}

	protected boolean getKolizja() {
		return kolizja ;
	}

	protected void setKolizja(boolean b) {
		this.kolizja = b;
	}

}

