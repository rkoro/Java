import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Random;

public final class Gra_Weze {// Obs³uga gry Wê¿e i timera.
	
	protected final Gracz_Weze[] Gracze = new Gracz_Weze[6];
	private BufferedImage obraz, obraz_tla;
	private Random generator;
	private boolean kolizja = false, _wygrany = false, zmiana_wyniku = false;
	private short ilosc_graczy = 6, ilosc_przegranych, ziarno_koloru;	
	private double[] punkt = new double[2];
	Object x;
	protected Gra_Weze() {// Konstruktor, nadanie kolorów graczom.
		generator = new Random();
		for (short i = 0; i < 6; i++) {
			Gracze[i] = new Gracz_Weze(new Color(40 * ((i+5) % 6) ,45 * ((i+2) %6), 50 * ((i*i+2) % 6)));
		}
	} 
	
	protected BufferedImage nowaGRA(int ilosc_graczy, int predkosc, int grubosc) {// Nowa partia.
		this.ilosc_graczy =(short) ilosc_graczy;
				
		for (int i = 0; i < 6; i++) {
			Gracze[i].setIlosc_ptk((short)0);
			Gracze[i].setGrubosc(grubosc);
			Gracze[i].setPredkosc(predkosc/10);	
		}
		nowaRunda();
		return obraz;
	}
	
	protected void nowaRunda() {//Nowa runda
		ilosc_przegranych = 0;
		nowePolozenie();
		nowaPlansza();
	}
	
	protected void nowePolozenie () {//Nowe po³o¿enia startowe graczy oraz Gracze[i].setCzy_zyje(true);.
		byte j;
		short odleglosc;
		for(byte i = 0; i < ilosc_graczy; i++) {
				j = i;
				Gracze[i].setPoz_x_pop(generator.nextInt(400) + 200);
				Gracze[i].setPoz_y_pop(generator.nextInt(400) + 200);
				while(j > 0) { // petla aby nowe weze nie byly za blisko
					odleglosc = (short) Math.sqrt(Math.pow(Gracze[i].getPoz_x_pop() - Gracze[i - j].getPoz_x_pop(), 2)+ Math.pow(Gracze[i].getPoz_y_pop() - Gracze[i - j].getPoz_y_pop(), 2));
					if (odleglosc  < 150) {
						Gracze[i].setPoz_x_pop(generator.nextInt(400) + 200);
						Gracze[i].setPoz_y_pop(generator.nextInt(400) + 200);
						j = i;
					}
					else j--;
				}
			Gracze[i].setCzy_zyje(true);
			Gracze[i].setKat(generator.nextFloat() * 360); 
		}
	}
	
	private void nowaPlansza() {// Nowe t³o pola gry i obraz do wyswietlania. Obraz t³a jest porównywany z wyœwietlanym w celu wtkrycia kolizji.
		ziarno_koloru = (short)( generator.nextInt(2000) + 5);
		ziarno_koloru *= ziarno_koloru;
		obraz = new BufferedImage(800, 800, BufferedImage.TYPE_INT_RGB);
		obraz_tla = new BufferedImage(800, 800, BufferedImage.TYPE_INT_RGB);
		for(short i = 0; i<800; i++) {
			for(short j = 0; j < 800; j++) {	
				obraz_tla.setRGB(i, j, i*j*ziarno_koloru);
				obraz.setRGB(i, j, MikserKolorow.miks(Color.BLACK, new Color(obraz_tla.getRGB(i, j)), 0.45f).getRGB());
				obraz_tla.setRGB(i, j, obraz.getRGB(i, j));
			}
		} 
	}
	 
	private void punkt(float x,float y, float promien, float kat) {// Obliczenie pkt. okrêgu o œrodku [x, y] i r = promien, a = kat.
		punkt[0] =  (x + promien*Math.sin(kat*Math.PI/180));
		punkt[1] =  (y + promien*Math.cos(kat*Math.PI/180));
	}
	
	private boolean czy_Banda() {// Sprawdzenie czy punkt jest wewn¹trz pola gry.
		if(punkt[0] > 799 || punkt[1] > 799 || punkt[0] < 0 || punkt[1] < 0 )  return true;
		else return false;
	}
	
	private void aktualizacja_Wyniku() {// Obs³uga efektów kolizji, zatrzymanie przegranych, dodanie punktów.
		for (byte i = 0; i < ilosc_graczy; i++) {	
			if(Gracze[i].getKolizja()) {
			Gracze[i].setCzy_zyje(false);
	    	ilosc_przegranych++;
	    	Gracze[i].setKolizja(false);
			}
		}
		for (byte i = 0; i < ilosc_graczy; i++) {
			if(Gracze[i].czyZyje()) {
				Gracze[i].setIlosc_ptk((short)(Gracze[i].getIlosc_ptk()+1));
			}
		}
		if(ilosc_graczy - ilosc_przegranych <= 1) _wygrany = true;// Jeœli zosta³ jeden gracz to jest wygranym.
	}
	
	protected BufferedImage graStart() { // Metoda wykonuj¹ca zmiany na bitmapie i wykrywanie kolizji, zwraca obraz do rysowania.
		for (byte i = 0; i < ilosc_graczy; i++) {
			Gracze[i].nowaPozycja();
		}
		// Tu aby ruch byl wykonywany jednoczesnie dla kazdego z graczy
		for (byte i = 0; i < ilosc_graczy; i++) { 
			if(Gracze[i].czyZyje()) { // Wykonanie tylko dla aktywnych graczy.
				// Sprawdzenie kolizji po obrysie g³owy wê¿a (jeœli piksel jest ró¿ny od obrazu to kolizja), jednoczesnie dla wszystkich graczy.
				for(short kat = (short)(Gracze[i].getKat() - 45); kat < Gracze[i].getKat() + 45; kat += 1) {
					punkt(Gracze[i].getPozycjax(), Gracze[i].getPozycjay(), Gracze[i].getGrubosc(), kat);
					if(czy_Banda() || obraz.getRGB((short)punkt[0],(short) punkt[1]) != obraz_tla.getRGB((short)punkt[0],(short) punkt[1]) ) {
						Gracze[i].setKolizja(true);
						kolizja = true;// kolizja w grze, jednoczesnosc efektów.
						break;
					}
				}	
				
				  // Rysowanie weza w aktualnej pozycji, tj. g³owy wê¿a o kolorze gracza.
				for(short kat = -180; kat<180; kat += 2) {
					for(short promien = 0; promien<Gracze[i].getGrubosc(); promien++) {	
						punkt(Gracze[i].getPozycjax(), Gracze[i].getPozycjay(), promien, kat);
						if(!czy_Banda()) {
							obraz.setRGB((short)punkt[0], (short)punkt[1],  Gracze[i].getKolor().getRGB());
						}
					}
				}
				   // Przypisanie aktualnych punktów jako poprzednich.
				Gracze[i].setPoz_x_pop(Gracze[i].getPozycjax());
				Gracze[i].setPoz_y_pop(Gracze[i].getPozycjay());
			}
		}
		
		if(kolizja == true) {// Jeœli wyst¹pi³a kolizja ustaw flage zmiany wyniku i aktualizuj wynik.
			kolizja = false;
			zmiana_wyniku = true;
			aktualizacja_Wyniku();
		}
	
		return obraz;
	}
	
	protected boolean is_wygrany() {
		return _wygrany;
	}
	
	protected void set_wygrany(boolean b) {
		this._wygrany = b;
	}

	protected boolean isZmiana_wyniku() {
		return zmiana_wyniku;
	}

	protected void setZmiana_wyniku(boolean b) {
		this.zmiana_wyniku = b;
	}
}

