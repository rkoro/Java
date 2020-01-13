import java.awt.Button;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Component;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public final class GUI_Weze  implements ActionListener{// Komponenty do gry Wê¿e.
	
	private Component[] KOMPONENTY;
	protected Choice lista;
	protected Button res_but, sta_but;
	protected TextField pole_edycji[][], pole_edycji_max_wyn, pole_edycji_grubosc ,pole_edycji_predkosc;
	protected Label label[];
	
	protected GUI_Weze() {// Elementy graficzne.
		//Lista wyboru liczby graczy.
		lista = new Choice();
		lista.setBounds(5, 100, 50, 50);
		lista.setVisible(true); 
		for(byte i = 0; i < 5; i++) {
			lista.add(String.valueOf(i+2));//wybor graczy 2 - 6 
		}
		lista.setFocusable(false);
		lista.select(2);
		
		// Przyciski Start i Reset
		res_but = new Button("Reset");
		res_but.setBounds(5,335, 190, 25);
		res_but.addActionListener(this);
		res_but.setEnabled(false);
		res_but.setVisible(true);
		res_but.setFocusable(false);

		sta_but = new Button("Start");
		sta_but.setBounds(5, 305, 190, 25);
		sta_but.addActionListener(this);
		sta_but.setVisible(true);
		sta_but.setFocusable(false);
		
		// Pola edycji musza byc focusable(domyslnie true)
		pole_edycji_max_wyn = new TextField("15");
		pole_edycji_max_wyn.setVisible(true);
		pole_edycji_max_wyn.setBounds(5, 75, 40, 20); 
		
		pole_edycji_grubosc = new TextField("10");
		pole_edycji_grubosc.setVisible(true);
		pole_edycji_grubosc.setBounds(5, 50, 40, 20); 
		
		pole_edycji_predkosc = new TextField("20");
		pole_edycji_predkosc.setVisible(true);
		pole_edycji_predkosc.setBounds(5, 25, 40, 20); 
		
		// Tu wpisaæ do tablicy komponentów unikatowe komponenty, zostan¹ dodane w Panel_Achtung.
		KOMPONENTY = new Component[] {res_but, sta_but, lista, pole_edycji_max_wyn, pole_edycji_grubosc, pole_edycji_predkosc};
		
		pole_edycji = new TextField[6][2];
		
		pole_edycji[0][0] = new TextField("q");
		pole_edycji[0][1] = new TextField("e");
		
		pole_edycji[1][0] = new TextField("z");
		pole_edycji[1][1] = new TextField("c");
		
		pole_edycji[2][0] = new TextField("b");
		pole_edycji[2][1] = new TextField("m");
		
		pole_edycji[3][0] = new TextField("y");
		pole_edycji[3][1] = new TextField("i");
		
		pole_edycji[4][0] = new TextField("[");
		pole_edycji[4][1] = new TextField("]");
		
		pole_edycji[5][0] = new TextField("7");
		pole_edycji[5][1] = new TextField("9");
		
		
		for(byte i = 0; i < 6; i++) {
			pole_edycji[i][0].setBounds(120, 153+25*i, 30, 19);
			pole_edycji[i][1].setBounds(160, 153+25*i, 30, 19); 
			pole_edycji[i][0].setVisible(true);
			pole_edycji[i][1].setVisible(true);
		}
		//Tu zwiêkszyæ rozmiar tablicy w razie nowego Labela, zostanie dodany w Panel_Achtung.
		label = new Label[13];
		for(byte i = 0; i < 6; i++) {
			label[i] = new Label("Gracz " + (i + 1));
			label[i].setBounds(0, 150+25*i, 200, 25);
			label[i].setForeground(Color.BLACK);
			label[i].setFont(MAIN.F_SERIF_16);
			label[i].setBackground(new Color(50 * ((i+5) % 6) ,45 * ((i+2) %6), 50 * ((i*i+2) % 6)));
			label[i].setVisible(true); 
		}
		
		label[6] = new Label();
		label[6].setBounds(575, 75, 65, 25);
		label[6].setBackground(Color.LIGHT_GRAY.darker());
		label[6].setFont(MAIN.F_SERIF_16);
		
		
		label[7] = new Label();
		label[7].setBounds(0, 0, 200, 25);
		label[7].setBackground(Color.LIGHT_GRAY.darker());
		
		label[8] = new Label("                             Lewo Prawo");
		label[8].setFont(MAIN.F_SERIF_16);
		label[8].setBounds(0, 125, 200, 25);
		label[8].setBackground(Color.LIGHT_GRAY.darker());
		
		label[9] = new Label("                 Liczba graczy.");
		label[9].setFont(MAIN.F_SERIF_16);
		label[9].setBounds(0, 100, 200, 25);
		label[9].setBackground(Color.LIGHT_GRAY.darker());
		
		label[10] = new Label("                Pkt. do zwyciêstwa.");
		label[10].setFont(MAIN.F_SERIF_16);
		label[10].setBounds(0, 75, 200, 25);
		label[10].setBackground(Color.LIGHT_GRAY.darker());
		
		label[11] = new Label("                Gruboœæ  (3 - 20).");
		label[11].setFont(MAIN.F_SERIF_16);
		label[11].setBounds(0, 50, 200, 25);
		label[11].setBackground(Color.LIGHT_GRAY.darker());
		
		label[12] = new Label("                Prêdkoœæ (3 - 40).");
		label[12].setFont(MAIN.F_SERIF_16);
		label[12].setBounds(0, 25, 200, 25);
		label[12].setBackground(Color.LIGHT_GRAY.darker());
	
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {//Start/Stop
		Object source1 = e.getSource();
		if (source1 == res_but) {
			sta_but.setEnabled(true);
			res_but.setEnabled(false);	
		}
		if (source1 == sta_but) {
			sta_but.setEnabled(false);
			res_but.setEnabled(true);
		}
	}
	
	public 	Component[] getElementy( ) {
		return KOMPONENTY;
	}	
}	
