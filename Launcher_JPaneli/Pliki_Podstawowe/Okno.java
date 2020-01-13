import java.awt.Container;
import javax.swing.JFrame;

public final class Okno extends JFrame{//Klasa tworzy nowe okno z przekazanym panelem gry.
	private static final long serialVersionUID = 1242363695078327910L;
	
	protected Okno(Panel panel) {
		super(panel.getClass().getName());// Nazywa okno tak samo jak nazwa klasy wyœwietlanego panelu.
		setResizable(true);
		getContentPane();
		Container cp = getContentPane();
		cp.add(panel); 
		pack();
		setDefaultCloseOperation(panel.getClose());//2 - JFrame.DISPOSE_ON_CLOSE, 3 - JFrame.EXIT_ON_CLOSE
		setVisible(true);
	}
}
 