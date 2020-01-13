import java.awt.Dimension;
import java.util.Random;

public final class Panel_Saper extends Panel {// Panel gry Saper. 
	private static final long serialVersionUID = 1386942754361888367L;
	
	private GUI_Saper gui;
	private Random generator;
	protected Dimension rozdzielczosc;
	protected Buttony_Saper[][] pola;
	private int odstep = 20, bok = 20;
	protected int szerokosc, wysokosc, miny, licznik_zwyciestwa = 0, licznik_flag = 0;
	protected boolean czy_pierwszy;

	Panel_Saper() { //Konstruktor uruchamiany tylko przy uruchomieniu z panelu g³ownego.
		this.szerokosc = 86;
		this.wysokosc = 48;
		this.miny = 200;
		generator = new Random();
		gui = new GUI_Saper(this);
		for(short i = 0; i < gui.getElementy().length; i++) add(gui.getElementy()[i]);
		
		gui.poddaj_button.setEnabled(false);
		gui.odkryj_button.setEnabled(false);
		
		setLayout(null);
		setPreferredSize(new Dimension (400,300));
	}
	
	Panel_Saper(int szerokosc, int wysokosc, int miny) {// Konstrukor uruchamiany z wewn¹trz gry, nowa gra to restart okna.
		this.szerokosc = szerokosc;
		this.wysokosc = wysokosc;
		this.miny = miny;

		generator = new Random();
		gui = new GUI_Saper(this);// Dodanie elementów interfejsu.
		for(short i = 0; i < gui.getElementy().length; i++) add(gui.getElementy()[i]);
		setLayout(null);

		czy_pierwszy = true;
		pola = new Buttony_Saper[szerokosc][wysokosc]; // Tworzenie tablicy pól gry o zadanych wymiarach.
		for(int i = 0; i < szerokosc; i++) {
			for(int j = 0; j < wysokosc; j++) {
				pola[i][j] = new Buttony_Saper(this);
				pola[i][j].setBounds(100 + odstep + i * bok, odstep + j * bok + bok, bok, bok);// Ustawienie pozycji pól gry.
				pola[i][j].x = i;
				pola[i][j].y = j;
				add(pola[i][j]);				
			}
		}
		rozdzielczosc = new Dimension((2 * odstep) + 5 + (bok * szerokosc) + 100, (4 * odstep) + (bok * wysokosc) + 15);		
		if(rozdzielczosc.height < 300) rozdzielczosc.height = 300;
		if(rozdzielczosc.width < 400) rozdzielczosc.width = 400;
		setPreferredSize(rozdzielczosc);	
	}
	
	protected void stworz_Miny(int x, int y) {// Metoda tworz¹ca miny + liczby przyleg³ych min, 
		int minax, minay;//  wywo³ana dopiero przy pierwszym odkryciu (naciœniêciu) pola.
	
		for(int i = 0 ; i < miny ; i++) {
			pola[x][y].czy_mina = true;// Tymczasowe ustawienie miny, aby mina nie wylosowa³a siê w miejscu pierwszego odkrycia.
			while(pola[x][y].czy_mina) {// Pêtla losuj¹ca po³o¿enie miny, a¿ natrafi na pust¹ pozycjê oraz dodaj¹ca liczbe przyleg³ych min.
				minax = generator.nextInt(szerokosc);
				minay = generator.nextInt(wysokosc);
				if(pola[minax][minay].czy_mina == false) {
					pola[minax][minay].czy_mina = true;
					if(minax + 1 < szerokosc && minay + 1 < wysokosc)	
						pola[minax + 1][minay + 1].stan++;
					if(minax + 1 < szerokosc && minay - 1 >= 0		)	
						pola[minax + 1][minay - 1].stan++;
					if(minax - 1 >= 0		 && minay + 1 < wysokosc)	
						pola[minax - 1][minay + 1].stan++;
					if(minax - 1 >= 0		 && minay - 1 >= 0		)	
						pola[minax - 1][minay - 1].stan++;
					
					if(minay + 1 < wysokosc )
						pola[minax][minay + 1].stan++; 
					if(minay - 1 >= 0	    )
						pola[minax][minay - 1].stan++; 
					if(minax + 1 < szerokosc) 
						pola[minax + 1][minay].stan++; 
					if(minax - 1 >= 0		) 
						pola[minax - 1][minay].stan++;
					pola[x][y].czy_mina = false;// Usuniêcie miny, aby miny nie by³o w miejscu pierwszego odkrycia.
				} 
			}		
		}
	}

	protected void odkryj(Buttony_Saper pole) {// Metoda rekurencyjnie odkrywaj¹ca naciœniête pole i przyleg³e pola, które nie s¹siaduj¹ z min¹.
		pole.sprawdzone = true;
		
		if(pole.czy_odkryty == false) {
			if(pole.czy_mina) {// Jeœli gracz odrkyje mine, przegrana.
				 for(int i = 0; i < szerokosc; i++) {
					 for(int j = 0; j < wysokosc; j++) {
						 pola[i][j].setEnabled(false);
					 }
				 }
				setVisible(true);
				pole.setText("!!!");
			} else {
				if(pole.stan == 0) {// Rekurencyjne odkrycie pól.
					pole.setText("");
					pole.czy_odkryty = true;
					pole.setEnabled(false);
					if(pole.x + 1 < szerokosc && pole.y + 1 < wysokosc && !pola[pole.x + 1][pole.y + 1].sprawdzone)	
						odkryj(pola[pole.x + 1][pole.y + 1]);
					if(pole.x + 1 < szerokosc && pole.y - 1 >= 0       && !pola[pole.x + 1][pole.y - 1].sprawdzone)	
						odkryj(pola[pole.x + 1][pole.y - 1]);
					if(pole.x -1 >= 0	      && pole.y + 1 < wysokosc && !pola[pole.x - 1][pole.y + 1].sprawdzone)	
						odkryj(pola[pole.x - 1][pole.y + 1]);
					if(pole.x -1 >= 0		  && pole.y - 1 >= 0       && !pola[pole.x - 1][pole.y - 1].sprawdzone)	
						odkryj(pola[pole.x - 1][pole.y - 1]);
					
					if(pole.y + 1 < wysokosc  && !pola[pole.x][pole.y + 1].sprawdzone)
						odkryj(pola[pole.x][pole.y + 1]);
					if(pole.y - 1 >= 0	      && !pola[pole.x][pole.y - 1].sprawdzone)
						odkryj(pola[pole.x][pole.y - 1]);
					if(pole.x + 1 < szerokosc && !pola[pole.x + 1][pole.y].sprawdzone)
						odkryj(pola[pole.x + 1][pole.y]);
					if(pole.x - 1 >= 0		  && !pola[pole.x - 1][pole.y].sprawdzone)
						odkryj(pola[pole.x - 1][pole.y]);
				} else {
					pole.setEnabled(false);
					pole.setText(Integer.toString(pole.stan));
					pole.czy_odkryty = true;
				} 
			}	
		}
	}	
}