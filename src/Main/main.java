package Main;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.swing.JFrame;

import Controllers.Handler;
import UI.Gameboard;
import UI.LoadMenu;
import UI.Menu;
import UI.saveMenu;
public class main{
	public static Menu menu;
	public static Gameboard game;
	public static LoadMenu load;
	public static Handler handler;
	public static saveMenu save;
	public static void main(String args[]){
		
		
		handler = new Handler();
		
		menu = new Menu(handler);
		menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		menu.setSize(400, 355);
		menu.setResizable(false);
		menu.setVisible(true);
		
			
		game = new Gameboard(handler);
		game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.setSize(415, 500);
		game.setResizable(false);
		game.setVisible(false);
	
		load = new LoadMenu(handler);
		load.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		load.setSize(415,500);
		load.setResizable(false);
		load.setVisible(false);
		
		save = new saveMenu(handler);
		save.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		save.setSize(300,120);
		save.setResizable(false);
		save.setVisible(false);
		
		
		// create saveData.txt when game is opened
		
		File f = new File("saveData.txt");
		boolean check = f.exists();
		if(!check){
			try {
				PrintWriter writer = new PrintWriter("saveData.txt", "UTF-8");
				writer.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			System.out.println("SaveData exists");
		}
		
		
	}
	
	
}
