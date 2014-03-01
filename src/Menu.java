import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
public class Menu extends JFrame{
	private JCheckBox Single;
	private JCheckBox Double;
	private JButton start;
	
	
	public Menu(Handler handler){
		super("Checkers Menu");
		setLayout(new FlowLayout());
		start = new JButton();
		start.setText("Start Game");
		add(start);

		
		Single = new JCheckBox("Single Player");
		Double = new JCheckBox("Two Player");
		add(Single);
		add(Double);
		Single.addActionListener(handler);
		Double.addActionListener(handler);
		start.addActionListener(handler);
	}
	
	public JButton start(){
		return start;
	}
	public JCheckBox Single(){
		return Single;
	}
	public JCheckBox Double(){
		return Double;
	}
	
}
