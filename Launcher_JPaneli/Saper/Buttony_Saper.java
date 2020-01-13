import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public final class Buttony_Saper extends JButton {// Klasa zawieraj�ca dzia�ania pola gry "Saper".
	private static final long serialVersionUID = 7469882051996330098L;
	
	private int flaga;
	protected boolean czy_odkryty = false,  czy_mina,  sprawdzone = false; 
	protected int stan, x, y;
	protected Panel_Saper panel;
	
	protected Buttony_Saper(Panel_Saper panel) {
		this.setFont(MAIN.F_ARIAL_20);
		this.setMargin(new Insets(0,0,0,0));// Usuni�cie marginesu aby zmie�ci� si� ca�y znak.
		this.setText("");	
		flaga = 0;
		stan = 0;
		this.panel = panel; 

		this.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				if (SwingUtilities.isLeftMouseButton(e)) {
					if(panel.czy_pierwszy == true) {// Losowanie min je�li zosta� wykonany pierwszy ruch.
						panel.stworz_Miny(x, y);
						panel.czy_pierwszy = false;
					} 
					if(flaga == 0) { //Sprawdzenie pola je�li nie ma flagi.
						panel.setVisible(false);// Usuni�cie widoczno�ci na czas rekurencyjnej metody dla optymalizacji.
						panel.odkryj(panel.pola[x][y]);// Rekurencyjna metoda odkrywaj�ca.
						panel.setVisible(true);// Przywr�cenie widoczno�ci.
					}	
				}

                if (SwingUtilities.isRightMouseButton(e)) {// Ustawienie flag i sprawdzenie warunk�w zwyci�stwa.
                	if(czy_odkryty == false) {  
                        if (flaga == 0) {
                        	setText("F");
                        	panel.licznik_flag++;
                        	if(czy_mina) {
                        		panel.licznik_zwyciestwa++;
                        		if(panel.licznik_zwyciestwa == panel.miny && panel.licznik_zwyciestwa == panel.licznik_flag) 
                        			JOptionPane.showMessageDialog(null, "wygrana!");
                        	}
                        	flaga = 1;
                        } else if (flaga == 1) {
                        	setText("?");
                        	if(czy_mina) {
                        		panel.licznik_zwyciestwa--;
                        	}
                        	flaga = 2;
                        } else if (flaga == 2) {   	
                        	panel.licznik_flag--;
                        	setText("");
                        	flaga = 0;
                        	if(panel.licznik_zwyciestwa == panel.miny && panel.licznik_zwyciestwa == panel.licznik_flag) 
                        		JOptionPane.showMessageDialog(null, "wygrana!");
                        	
                        }
                	}
                }
			}
		});
	}
}