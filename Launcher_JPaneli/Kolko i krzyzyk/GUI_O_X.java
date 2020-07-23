import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public final class GUI_O_X implements ActionListener{// Komponenty interfejsu gry "Kó³ko i krzy¿yk".
	
	private Component[] KOMPONENTY;
	private Panel_O_X panel;
	protected Button res_but,sta_but;
	private TextField PED_ilosc_pol, PED_warunek_zwyciestwa;
	private Label label1, label2;
	
	GUI_O_X(Panel_O_X panel){
		this.panel = panel;
		
		res_but = new Button("Reset");
		res_but.addActionListener(this);
		res_but.setEnabled(true);
		res_but.setBounds(400 + 5, 15, 90, 20);
		
		sta_but = new Button("Start");
		sta_but.setBounds(400 - 95, 15, 90, 20);
		sta_but.addActionListener(this);
		
		PED_ilosc_pol = new TextField("3");
		PED_ilosc_pol.setBounds(400 - 95 - 25, 15 , 20, 20);
		
		PED_warunek_zwyciestwa = new TextField("3"); 
		PED_warunek_zwyciestwa.setBounds(400 - 95 - 25, 40 , 20, 20);
		
		label1 = new Label("Bok planszy w polach.");
		label1.setBounds(80,15,200, 20);
		label1.setBackground(Color.LIGHT_GRAY);
		
		label2 = new Label("Warunek zwyciêstwa (max = bok)");
		label2.setBounds(80,40,200, 20);
		label2.setBackground(Color.LIGHT_GRAY);
		// Tablica zostanie dodana do widoku w panelu.
		KOMPONENTY = new Component[] {res_but,sta_but, PED_ilosc_pol, PED_warunek_zwyciestwa, label1, label2};	
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {// Start i reset gry.
		Object source = e.getSource();
		
		if (source == res_but) {
			panel.plansza.tura = 0;
			panel.ustaw_Stale();
			panel.rozmiar_planszy = -1;
			panel.plansza.tura = 0;
			sta_but.setEnabled(true);
			panel.repaint(); 		
		}
		if (source == sta_but) {
			try {
				panel.pole = Integer.parseInt(PED_ilosc_pol.getText());
				panel.warunek = Integer.parseInt(PED_warunek_zwyciestwa.getText());
				if(panel.warunek > panel.pole) {
					panel.warunek = panel.pole;
					PED_warunek_zwyciestwa.setText(Integer.toString(panel.warunek));
				}
			}
			catch (Exception ex) {
			}
			if (panel.pole >2 && panel.pole<100) {
				panel.rozmiar_planszy = panel.pole;
				panel.setBackground(Color.BLACK);
				panel.ustaw_Stale();
				panel.plansza.tura = 0;
				sta_but.setEnabled(false);
				res_but.setEnabled(true);
				panel.plansza.zwyciestwo = false;
				panel.repaint();
			}	
		}
	}
	
	public Component[] getElementy() {
		return KOMPONENTY;
	}
}