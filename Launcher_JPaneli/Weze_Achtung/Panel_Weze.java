import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;


public final class Panel_Weze extends Panel  {//Panel gry "Wê¿e".
	private static final long serialVersionUID = -1687985848157458244L;
	
	private Dimension rozdzielczosc;
	private BufferedImage obraz;
	private Color kolor_planszy;
	private GUI_Weze elementy;
	private Timer timer;
	private Gra_Weze GRA;
	private boolean  koniec_partii, start, ilosc_graczy_zmiana;
	private byte ilosc_graczy; 
	private short grubosc, predkosc, warunek_zwyciestwa, max_punktow, czas = 0;
	
	public Panel_Weze() {
		rozdzielczosc = new Dimension(1000, 900);
		setPreferredSize(rozdzielczosc);
		setFocusable(true);
		
		kolor_planszy = MikserKolorow.miks(Color.BLUE, Color.DARK_GRAY, 0.4f);
		GRA = new Gra_Weze(); // Stworzenie Gry
		obraz = GRA.nowaGRA(0, 0, 0);// Na³o¿enie obrysu pola gry.
		elementy = new GUI_Weze();//Stworzenie elementów (buttony, texfield...)
		// Dodawanie elementow GUI, to co dodane pierwsze jest na wierzchu.
		for(byte i = 0; i < elementy.getElementy().length; i++) add(elementy.getElementy()[i]);//Dodanie buttonow, listy...
		for(byte i = 0; i < GRA.Gracze.length; i++) { // Dodanie pól wyboru sterowania.
			add(elementy.pole_edycji[i][0]);
			add(elementy.pole_edycji[i][1]);
			addKeyListener(GRA.Gracze[i]);
		}
		for(short i = 0; i < elementy.label.length; i++) add(elementy.label[i]);// Dodawanie napisów.
		
		timer = new Timer();
		timer.schedule(timerGra, 0, 10);  
		setLayout(null);// Umozliwia ustawienie wlasnego rozmieszczenia elementow.
	}
			  
	TimerTask timerGra = new TimerTask() { // W¹tek timera - obs³uga gry.     
		public void run() {			
			if(elementy.sta_but.isEnabled()) { // Jeœli przycisk start jest aktywny to sprawdzaj zadan¹ ilosc_graczy.
				if(ilosc_graczy != (byte)(elementy.lista.getSelectedIndex() + 2)) {
					ilosc_graczy = (byte)(elementy.lista.getSelectedIndex() + 2);
					ilosc_graczy_zmiana = true;
				}
						
				if (start == true) {// tylko raz, przywrocenie focusa.
					umozliwFocus(true);
					start = false;// Flaga trwania rozgrywki.	
				}
				if (ilosc_graczy_zmiana) {
					for(byte i = 0; i < GRA.Gracze.length; i++) {// Widocznoœæ Labeli graczy w zale¿noœci od wybranej liczby graczy.
						if (i < ilosc_graczy) {
							elementy.pole_edycji[i][0].setVisible(true);	
							elementy.pole_edycji[i][1].setVisible(true);			
							elementy.label[i].setVisible(true);			
						} else {
							elementy.pole_edycji[i][0].setVisible(false);		
							elementy.pole_edycji[i][1].setVisible(false);
							elementy.label[i].setVisible(false);
						}
						ilosc_graczy_zmiana = false;
					}
				}
			}			
			
			if(elementy.res_but.isEnabled() && start == false) {// Je¿eli przycisk reset jest aktywny i gra nie jest w toku(tylko raz)
				pobranieDanych();								// Inicjacja nowej gry, wczytanie danych z pól.	
				umozliwFocus(false);// Odebranie focusa komponentom.
				obraz = GRA.nowaGRA(ilosc_graczy,predkosc,grubosc);// Zerowanie gry.
				wyswietlWynik();// 0 0 0 0 0 0 
				repaint();
				requestFocus();// Nadanie focusa grafice.
				elementy.label[7].setText("");			
				koniec_partii = false;
				max_punktow = 0;
				czas = -3000;//DELAY w ms miêdzy partiami.
				start = true;// Rozpoczêcie toku rozgrywki.
			}
			
			if(start == true) {// Jeœli rozgrywka jest w toku.
				if (czas >= 0) {
					obraz = GRA.graStart();// Kolejny obraz do wyœwietlenia, wywo³anie ruchu.
					repaint();
					sprawdzWynik();// Aktualizacja wyników i sprawdzenie warunku zwyciêstwa.
					
				} else if (czas < 0) {//Odliczanie do zera i przesuniecie weza o kolejn¹ pozycjê co 1 sekunde.
					elementy.label[6].setText(Integer.toString((-czas/1000)));
					if (czas % 1000 == 0) {//
						obraz = GRA.graStart();
						repaint();
					}
				}
				czas += 10;	
			} 
			
		}
	};
		
	private void umozliwFocus(boolean b) {
			for(byte i = 0; i < GRA.Gracze.length; i++) {
				elementy.pole_edycji[i][0].setFocusable(b);	
				elementy.pole_edycji[i][1].setFocusable(b);
			}
			for(byte i = 0; i < elementy.getElementy().length; i++) {
				elementy.getElementy()[i].setFocusable(b);
			}
		}
	
	private void sprawdzWynik() {
		if (GRA.isZmiana_wyniku() == true) {// Sprawdzenie czy zmieni³ siê wynik graczy.
			wyswietlWynik();
			GRA.setZmiana_wyniku(false);
		}
		if (czas % 100 == 0) // Licznik w górnej czêœci ekranu.
			elementy.label[6].setText(Integer.toString((czas/1000)) + " START");
		
		if (GRA.is_wygrany()) {// Sprawdzenie czy runda sie skonczy³a.
			czas = - 4000;		// DELAY w ms miêdzy rundami i sekunda starego obrazu.
			GRA.nowaRunda();	// Kolejne po³o¿enie graczy i kolejny obraz.
			GRA.set_wygrany(false);     	
			for (int i = 0; i < ilosc_graczy; i++) {// Zapisanie najwy¿szego wyniku wœród graczy i sprawdzenie warunku zwyciêstwa.
				if(GRA.Gracze[i].getIlosc_ptk() > max_punktow) max_punktow = GRA.Gracze[i].getIlosc_ptk();
				if(warunek_zwyciestwa <= GRA.Gracze[i].getIlosc_ptk()) koniec_partii = true; 
			}        	
			if(koniec_partii) {// Jeœli jest zwyciêzca gry.
				// TODO: ZAPISZ WYNIK DO BAZY
				// zatrzymanie rozgrywki
				elementy.res_but.setEnabled(false);
				elementy.sta_but.setEnabled(true);
				for (int i = 0; i < ilosc_graczy; i++) {
					if(GRA.Gracze[i].getIlosc_ptk() == max_punktow)
						elementy.label[7].setText("\n" + elementy.label[7].getText() + "\nGracz " + (i + 1) +" ZWYCIEZA - " + GRA.Gracze[i].getIlosc_ptk() + " pkt.\n");
				}
			}		
		}       		
	}
	
	private void wyswietlWynik() { // Wyœwietla liczbe ptk na ekranie.
		for(byte i = 0; i < ilosc_graczy; i++) {
			elementy.label[i].setText("Gracz " + (i + 1) + " ma " + GRA.Gracze[i].getIlosc_ptk() + " pkt.");
		}			
	}
	
	private void pobranieDanych() {
		ilosc_graczy = (byte)(elementy.lista.getSelectedIndex() + 2);
			try {// Pobranie gruboœci.							
				grubosc = Short.parseShort(elementy.pole_edycji_grubosc.getText());
				if(grubosc < 3 || grubosc > 20) {
					grubosc = 10;// Wartoœæ domyœlna.
					elementy.pole_edycji_grubosc.setText("10");
				}
			} catch (NumberFormatException e){
				grubosc = 10;// Wartoœæ domyœlna.
				elementy.pole_edycji_grubosc.setText("10");
			}
			try {// Pobranie prêdkoœci.
				predkosc = Short.parseShort(elementy.pole_edycji_predkosc.getText());
				if(predkosc < 3 || predkosc > 40) {
					predkosc = 20;// Wartoœæ domyœlna.
					elementy.pole_edycji_predkosc.setText("10");
				}
			} catch (NumberFormatException e){
				predkosc = 20;// Wartoœæ domyœlna.
				elementy.pole_edycji_predkosc.setText("10");
			}
			try {// Pobranie liczby punktów potrzebnych do zwyciêstwa.
				warunek_zwyciestwa = Short.parseShort(elementy.pole_edycji_max_wyn.getText());
			}
			catch (NumberFormatException e){
				warunek_zwyciestwa = (short) (ilosc_graczy * 5);// Wartoœæ domyœlna.
				elementy.pole_edycji_max_wyn.setText("10");
			}			
			
			for( byte i = 0; i < ilosc_graczy; i++) {
				if(elementy.pole_edycji[i][0].getText() != null)
					GRA.Gracze[i].lewo_znak = elementy.pole_edycji[i][0].getText().toCharArray()[0];
				if(elementy.pole_edycji[i][1].getText() != null)
					GRA.Gracze[i].prawo_znak = elementy.pole_edycji[i][1].getText().toCharArray()[0];
			}		
		}	
		
	protected void paintComponent(Graphics g) {//Rysowanie elementów grafiki i bitmapy.
		 g.setColor(kolor_planszy);
		 g.fillRect(0, 0, this.getWidth(),  this.getHeight());
		 g.setColor(Color.BLACK);
		 g.drawImage(obraz, 200, 100, this);
		 g.dispose();		 
	 }
}
	 
	 
	 
	 
	 


