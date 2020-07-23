import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

public final class Panel_O_X extends Panel {
	private static final long serialVersionUID = 3718180213956352039L;
	
	private final int pocz_rys_x = 150;
	private final int pocz_rys_y = 150;
	
	private GUI_O_X gui;
	protected Wygrana_O_X wygr;
	protected Plansza_O_X plansza;
	private Dimension rozdzielczosc;
	protected int warunek, bok, odstep, pole, rozmiar_planszy = -1;  
	
	
	protected Panel_O_X() {// Panel zawieraj¹cy grê "Kó³ko i Krzy¿yk".
		setBackground(Color.GRAY);
		ustaw_Stale();
	    addMouseListener(new Mysz_O_X(this));// Dodanie wykrywania kilkniêæ Mysz¹ nad polami gry.
	    
		wygr = new Wygrana_O_X();
		gui = new GUI_O_X(this);
		for(short i = 0 ; i < gui.getElementy().length; i++) add(gui.getElementy()[i]);
		
		rozdzielczosc = new Dimension(800, 800);
		setPreferredSize(rozdzielczosc);	
		setLayout(null);
	}
	
	protected void ustaw_Stale() {// Parametry rozmiarów.
		plansza = new Plansza_O_X(rozmiar_planszy, warunek);	
		odstep = (int) 50/(rozmiar_planszy);
		bok = (int) 625/rozmiar_planszy - odstep;
	}
	
	protected void rysuj_Pole_Gry(Graphics g) {// Rysowanie pola gry.
		if(rozmiar_planszy > 2) {
			g.setColor(Color.WHITE);
			for (int x = 0; x < rozmiar_planszy; x++) {
				for (int y = 0; y < rozmiar_planszy; y++) { 
					plansza.pola[x][y].poz_x = (int) pocz_rys_x + 20 + x*(bok+odstep);
					plansza.pola[x][y].poz_y = (int) pocz_rys_y + 20 + y*(bok+odstep);
					g.fillRect(plansza.pola[x][y].poz_x, plansza.pola[x][y].poz_y, bok, bok);
					
				}
			}
		}
	}
	
	protected void rys_X_O(Graphics g) {// Rysowanie kó³ i krzy¿y.
		if(rozmiar_planszy > 2) {
			g.setColor(Color.BLACK); 
			for (int x = 0; x < rozmiar_planszy; x++) {
				for (int y = 0; y < rozmiar_planszy; y++) {
					if (plansza.pola[x][y].stan == 'x') {// krzy¿e
						g.drawLine(plansza.pola[x][y].poz_x, plansza.pola[x][y].poz_y, plansza.pola[x][y].poz_x + bok, plansza.pola[x][y].poz_y + bok);
						g.drawLine(plansza.pola[x][y].poz_x, plansza.pola[x][y].poz_y + bok, plansza.pola[x][y].poz_x + bok, plansza.pola[x][y].poz_y);
					}
					if (plansza.pola[x][y].stan == 'o') {// ko³a
						g.drawOval(plansza.pola[x][y].poz_x, plansza.pola[x][y].poz_y, bok, bok);
					}
				}
			}
		}
		if(plansza.zwyciestwo == true) {// Reakcja na zwyciêstwo
			g.setColor(Color.RED);
	        g.setFont(MAIN.F_ARIAL_20);
	        g.drawString(("ZWYCIESTWO GRACZA " + ((plansza.tura - 1) % 2 + 1)), 10, 100);
	        gui.sta_but.setEnabled(true);
    		gui.res_but.setEnabled(false);
		}	
	}
	
    protected void paintComponent(Graphics g) {
    	g.clearRect(0, 0, rozdzielczosc.width, rozdzielczosc.height);
    	g.setColor(Color.LIGHT_GRAY);
    	g.fillRect(0, 0, rozdzielczosc.width, rozdzielczosc.height);  

    	rysuj_Pole_Gry(g);
    	rys_X_O(g);
  }
}

