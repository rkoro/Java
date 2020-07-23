
import java.awt.Font;

public final class MAIN {
	//Czcionki
	public final static Font F_SERIF_16 = new Font("serif", Font.PLAIN, 16);
	public final static Font F_MONOSPACED_15 = new Font("Monospaced", Font.BOLD, 15);
	public final static Font F_MONOSPACED_12 = new Font("Monospaced", Font.PLAIN, 12);
	public final static Font F_ARIAL_20 = new Font("Arial", Font.BOLD, 20);
	
	public static void main(String[] args) {
				new Okno(new Panel_Startowy());//Wywo³anie okna startowego.
	}
}


