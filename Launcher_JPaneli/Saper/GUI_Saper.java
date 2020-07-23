import java.awt.Button;
import java.awt.Component;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public final class GUI_Saper implements ActionListener{// Elementy interfejsu gry Saper.
	
	private Component[] KOMPONENTY;
	private Panel_Saper panel;
	private TextField pole_szer, pole_wys, pole_miny;
	private Label lab_szer, lab_wys, lab_miny, label, label1;
	protected Button start_button, odkryj_button,  poddaj_button;
	
	GUI_Saper(Panel_Saper panel){ 
		this.panel = panel;
		
	 	pole_szer = new TextField(Integer.toString(panel.szerokosc)); 
		pole_szer.setBounds(10, 40, 30, 20);
		
		pole_wys = new TextField(Integer.toString(panel.wysokosc)); 
		pole_wys.setBounds(10, 70, 30, 20);
		
		pole_miny = new TextField(Integer.toString(panel.miny));
		pole_miny.setBounds(10, 100, 30, 20);
		
		start_button = new Button("Start");
		start_button.setBounds(10, 130, 100, 40);
		start_button.addActionListener(this);
		
		odkryj_button = new Button("Poka¿ pola");
		odkryj_button.setBounds(10, 170, 100, 40);
		odkryj_button.addActionListener(this);
		
		poddaj_button = new Button("Poddaj siê");
		poddaj_button.setBounds(10, 210, 100, 40);
		poddaj_button.addActionListener(this);
		
		lab_szer = new Label("Szerokoœæ");
		lab_szer.setBounds(45, 40, 60, 20);
		
		lab_wys = new Label("Wysokoœæ");
		lab_wys.setBounds(45, 70, 60, 20);
		
		lab_miny = new Label("Liczba min");
		lab_miny.setBounds(45, 100, 60, 20);
		
		label = new Label ("Miny sa losowane po pierwszym wciœniêciu pola gry.  Max wys.  = 48");
		label.setBounds(10, 0, 400, 20);
		
		label1 = new Label("Sterowanie: LPM (odkryj) i PPM (ustaw/zmieñ flagê). Max szer. = 86");
		label1.setBounds(10, 20, 400, 20);
		// Tablica komponentów zostania dodana w panelu.
		KOMPONENTY = new Component[] {pole_miny, pole_szer, pole_wys, start_button, odkryj_button, poddaj_button,
									  lab_szer, lab_wys, lab_miny, label, label1 };	
	}
	
	public void pobierzDane (){// Pobranie Danych i maksymalne wartoœci.
		try {
			panel.szerokosc = Integer.parseInt(pole_szer.getText());
			panel.wysokosc = Integer.parseInt(pole_wys.getText());
			panel.miny = Integer.parseInt(pole_miny.getText());
			
			if(panel.szerokosc > 86) panel.szerokosc = 86;
			else if(panel.szerokosc < 2) panel.szerokosc = 2;
			if(panel.wysokosc > 48) panel.wysokosc = 48;
			else if(panel.wysokosc < 2) panel.wysokosc = 2;
			if(panel.miny > 2000 || panel.miny > 0.5 * panel.wysokosc * panel.szerokosc || panel.miny < 1) 
				panel.miny = (int) (0.5 * panel.wysokosc * panel.szerokosc);
			
		} catch (Exception ex) {
			return;
		}			
		if(panel.szerokosc == 0 || panel.wysokosc == 0 || panel.miny == 0) 	
			return;
	} 
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == start_button) {// Otworzenie okna z zadanymi wartoœciami i zamkniêcie aktualnego okna.
			pobierzDane();
			new Okno(new Panel_Saper(panel.szerokosc, panel.wysokosc, panel.miny));
			panel.zamknijOkno();
		}
		
		if (source == odkryj_button) {// Odkrycie stanu pól.
			panel.setVisible(false); 
			for(int i = 0; i < panel.szerokosc; i++) {
				for(int j = 0; j < panel.wysokosc; j++) {	
					if(panel.pola[i][j].czy_mina) panel.pola[i][j].setText("*");
					else if(panel.pola[i][j].stan == 0) panel.pola[i][j].setText("");
					else panel.pola[i][j].setText(Integer.toString(panel.pola[i][j].stan));
				}
			}
			panel.setVisible(true);
		}	
		
		if (source == poddaj_button) {// Poddanie gry.
			panel.setVisible(false);
			for(int i = 0; i < panel.szerokosc; i++) {
				for(int j = 0; j < panel.wysokosc; j++) {	
					if(panel.pola[i][j].czy_mina) panel.pola[i][j].setText("*");
					else if(panel.pola[i][j].stan == 0) panel.pola[i][j].setText("");
					else panel.pola[i][j].setText(Integer.toString(panel.pola[i][j].stan));
					panel.pola[i][j].setEnabled(false);
				}
			}
			panel.setVisible(true);
			poddaj_button.setEnabled(false);

		}
		
	}
	
	public Component[] getElementy() {
		return KOMPONENTY;
	}
}