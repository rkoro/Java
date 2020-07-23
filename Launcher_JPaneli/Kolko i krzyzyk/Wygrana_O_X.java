public final class Wygrana_O_X {// Klasa przeszukuj¹ca pola w poszukiwaniu zwyciêzcy.
	
	private Plansza_O_X plansza;
	private int przylegle, rozmiar, warunek;
	private char znak;

	public Wygrana_O_X() {
	}
	
	private void wygrana_Poziom() {
		for(int y = 0; y < rozmiar; y++) {
			przylegle = 0;
			for(int x = 0; x < rozmiar; x++) {
				if (plansza.pola[x][y].stan == znak) {
					przylegle = przylegle + 1;
				}
				else {
					przylegle = 0; 
				}
				if (przylegle >= warunek) {
					plansza.zwyciestwo = true; 
				}	
			}
		}
	}
	
	private void wygrana_Pion() {
		for(int x = 0; x < plansza.rozmiar_planszy; x++) {
			przylegle = 0;
			for(int y = 0; y <plansza.rozmiar_planszy; y++) {
				if (plansza.pola[x][y].stan == znak) {
					przylegle++;
				} else {
					przylegle = 0; 
				}		
				if (przylegle >= warunek) 
					plansza.zwyciestwo = true;			
			}	
		}	
	}
	
	private void wygrana_Skos1() {
		int startx = 0;
		for(int starty = 2;starty < plansza.rozmiar_planszy ;starty++) {
			int maxprz = 0;
			przylegle = 0;
			int x = startx;
			int y = starty;
			while (x < plansza.rozmiar_planszy && y >= 0) {
				if (plansza.pola[x][y].stan == znak) {
					przylegle++;
					if (przylegle > maxprz)
						maxprz = przylegle;  
				} else {
					przylegle = 0;
				}
				y--;
				x++;
			}
			przylegle = 0;
			y =plansza.rozmiar_planszy - starty - 1;
			x =plansza.rozmiar_planszy - 1;

			while(y < plansza.rozmiar_planszy && x >= 0) {
				if (plansza.pola[x][y].stan == znak) {
					przylegle++;
					if (przylegle > maxprz)
						maxprz = przylegle;
				}
				else {
					przylegle = 0;
				}
				y++;
				x--;
			}
            if (maxprz >= warunek)
            	plansza.zwyciestwo = true;	
		}
	}
		
		private void wygrana_Skos2() {
			int starty = 0;
			for(int startx = 0; startx < plansza.rozmiar_planszy - 2; startx++) {
				int maxprz = 0;
				przylegle = 0;
				int x = startx;
				int y = starty;
				
				while (x < plansza.rozmiar_planszy && y <plansza.rozmiar_planszy) {
					if (plansza.pola[x][y].stan == znak) {
						przylegle++;
						if (przylegle > maxprz)
							maxprz = przylegle;
					} else {
						przylegle = 0;
					}		
					y++;
					x++;
				}	
				przylegle = 0;
				y = plansza.rozmiar_planszy - 1;
				x = plansza.rozmiar_planszy - 1 - startx;
			
				while(y < plansza.rozmiar_planszy && x >= 0) {
					if (plansza.pola[x][y].stan == znak) {
						przylegle++;
						if (przylegle > maxprz)
							maxprz = przylegle;
					} else {
						przylegle = 0;
					}
					y--;
					x--;		
				}
	            if (maxprz >= warunek)
	            	plansza.zwyciestwo = true;
			}
		}
		
	public void czy_Wygrana(Plansza_O_X plansza) {
		this.plansza = plansza;
		warunek = plansza.warunek;
		rozmiar = plansza.rozmiar_planszy;
		if(plansza.tura % 2 == 0) 
			znak = 'o';
		else 
			znak = 'x';
		wygrana_Poziom();
		wygrana_Pion();
		wygrana_Skos1();
		wygrana_Skos2();
	}
}
