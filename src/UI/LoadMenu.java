package UI;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.border.Border;

import Controllers.Handler;

	//Refer to page 12 in design document:
	//1.1/1.2: Player can choose to start a new game using the Start Button, 
	//or they can load a previously stored game using the Load Game button, 
	//which uses the saveData text file that stores saved games.

public class LoadMenu extends JFrame {
	private JScrollPane scrollPane;
	private JButton Load, toMenu;
	private JPanel panelTop, panelBottom, panelWhole;
	private JLabel instr;
	private String[] savedGames;
	private JList<String> list;
	
	public LoadMenu(Handler handler){
		super("Load Menu");
		setLayout(new FlowLayout());
		scrollPane = new JScrollPane();
		savedGames = new String[50]; // max 50 games can be saved
		list = new JList<String>(savedGames);
		Load = new JButton();	
		instr = new JLabel("                Select a game and click load");
		toMenu = new JButton("Menu");
		panelWhole = new JPanel();
		panelTop = new JPanel();
		panelBottom = new JPanel();
		
		
		panelWhole.setLayout(new BorderLayout());
		panelBottom.setLayout(new BorderLayout());
		panelTop.setLayout(new BorderLayout());
		
		
		panelTop.setPreferredSize(new Dimension(400, 425));
		panelBottom.setPreferredSize(new Dimension(400, 40));
		
		panelTop.add(scrollPane);
		panelBottom.add(Load, BorderLayout.WEST);
		panelBottom.add(toMenu, BorderLayout.EAST);
		panelBottom.add(instr, BorderLayout.CENTER);
		
		panelWhole.add(panelBottom, BorderLayout.PAGE_END);
		panelWhole.add(panelTop, BorderLayout.NORTH);
		
		
		loadList();	
		Load.setText("Load");
		
		
		Load.addActionListener(handler);
		toMenu.addActionListener(handler);
		add(panelWhole);
	}
	
	public JButton Load(){
		return Load;
	}
	
	public void loadList(){
		JList<String> list = new JList<String>(this.savedGames);
		scrollPane.setViewportView(list);
	}
	
	public String[] getSavedGames(){
		return savedGames;
	}
	
	public void setSavedGames(String[] savedGames){
		this.savedGames = savedGames;
		loadList();
	}
	
	public JButton toMenu(){
		return toMenu;
	}
	
	public String getSelectedGame(){
		JViewport viewport = scrollPane.getViewport();
		JList gamesList = (JList)viewport.getView();
		return (String)gamesList.getSelectedValue();
	}
	
	public void deselect(){
		JList<String> list = null;
		list = new JList<String>(this.savedGames);
		scrollPane.setViewportView(list);
	}
	
	public void setMessage(String message){
		instr.setText(message);
	}
}
