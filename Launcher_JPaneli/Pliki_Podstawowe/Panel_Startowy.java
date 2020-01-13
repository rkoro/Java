import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;

public final class Panel_Startowy extends Panel {
	private static final long serialVersionUID = -5332575308092550047L;
	private short ilosc_buttonow = 0;
	protected Dimension rozdzielczosc = new Dimension(500, 300); 
	
	Panel_Startowy() {//Panel uruchamiany jako powitalny, ekran wyboru gier.
		setPreferredSize(rozdzielczosc);
		setLayout(null);
		
		add(new But_Launcher(Panel_Startowy.class, "Launcher"));
		add(new But_Launcher(Panel_O_X.class, "Kó³ko i Krzy¿yk"));
		add(new But_Launcher(Panel_Saper.class, "Saper"));
		add(new But_Launcher(Panel_Weze.class, "Wê¿e"));	
	}
	private class But_Launcher extends Button implements ActionListener {//Buttony uruchamiaj¹ce poszczególne gry/panele.
		private static final long serialVersionUID = -3271723786741007452L;	
		
		private Class<?> klasa;
		But_Launcher(Class<?> klasa, String text) {
			super(text);
			this.klasa = klasa;
			ilosc_buttonow++;
			addActionListener(this);
			this.setBounds(205, 50 + 40 * ilosc_buttonow, 90, 30);
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			new Okno((Panel)StworzObiektZeStringa.New(klasa));// Tworzenie nowego obiektu po nazwie klasy.
		} 
	}
	
	@Override // Z klasy Panel.
	protected int getClose() {return JFrame.EXIT_ON_CLOSE;}// Zamyka ca³y wszystkie okna.
	@Override
	protected void paintComponent(Graphics g) {
		g.clearRect(0, 0, rozdzielczosc.width, rozdzielczosc.height);
		g.setColor(Color.BLUE.darker());
		g.fillRect(0, 0, rozdzielczosc.width, rozdzielczosc.height);
		g.setColor(Color.GREEN);
		g.setFont(MAIN.F_MONOSPACED_15);
		g.drawString("Launcher R K", 10, 20);
		g.drawString("Wszystkie okna mo¿na uruchamiaæ wielokrotnie.", 10, 60);
		g.drawString("Zamkniêcie okna 'Launcher' zamyka wszystkie okna.", 10, 80);
	}
	@Override
	protected void zamknijOkno() {
		// TODO Auto-generated method stub
		
	}
}