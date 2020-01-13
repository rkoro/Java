import java.awt.Dimension;
import java.awt.Window;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public abstract class Panel extends JPanel {//Klasa abstrakcyjna uogólniaj¹ca panele gry (Wykorzystane w Okno.class).
	private static final long serialVersionUID = 813471686919109241L;
	
	protected int getClose() {return JFrame.DISPOSE_ON_CLOSE;} 
	protected void zamknijOkno() {
		Window win = SwingUtilities.getWindowAncestor((JComponent) this);
		win.dispose();
		
		
	}
	public Dimension getPrefferedSize() {
		// TODO Auto-generated method stub
		return null;
	}
}
	
	
	

