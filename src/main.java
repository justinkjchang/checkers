import javax.swing.JFrame;
public class main{
	static Menu menu;
	static Gameboard game;
	static Handler handler;
	public static void main(String args[]){
		
		handler = new Handler();
		menu = new Menu(handler);
		menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		menu.setSize(420, 420);
		menu.setResizable(false);
		menu.setVisible(true);
			
		game = new Gameboard(handler);
		game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.setSize(415, 500);
		game.setResizable(false);
		game.setVisible(false);
	
	}
}
