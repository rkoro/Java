import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public final class Mysz_O_X implements MouseListener, MouseMotionListener {
	
	private Panel_O_X panel;

	public Mysz_O_X(Panel_O_X panel) {
		this.panel = panel;	
		this.panel.addMouseListener(this);
		this.panel.addMouseMotionListener(this);
	}

	@Override
	public void mouseDragged(MouseEvent e) {

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {// Wywyo³anie wykrywania zwyciêstwa  i ustawienie pola. 
		int x = e.getX();
		int y = e.getY();
		for (int i = 0; i < panel.rozmiar_planszy; i++) {
			for (int j = 0; j < panel.rozmiar_planszy; j++) {
				if( panel.plansza.pola[i][j].poz_x < x &&
					panel.plansza.pola[i][j].poz_x+panel.bok > x && 
					e.getButton() == MouseEvent.BUTTON1 && 
					panel.plansza.zwyciestwo == false)
				{
					if( panel.plansza.pola[i][j].poz_y < y && 
						panel.plansza.pola[i][j].poz_y+panel.bok > y ) {
			
						if(panel.plansza.tura % 2  == 0 && panel.plansza.pola[i][j].stan == ' ') {
							panel.plansza.pola[i][j].stan = 'o';
							panel.wygr.czy_Wygrana(panel.plansza);
							panel.plansza.tura++;
							panel.repaint();
						}
						else if(panel.plansza.tura % 2 == 1 && panel.plansza.pola[i][j].stan == ' ') {
							panel.plansza.pola[i][j].stan = 'x';
							panel.wygr.czy_Wygrana(panel.plansza);
							panel.plansza.tura++;
							panel.repaint();			
						}
					}
				}
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
