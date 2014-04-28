package UI;


import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import Controllers.Handler;

	//refer to page 12 in design document
	//1.4: The Save button can be used to save games, 
	//which uses the saveMenu class and saveData text file.

public class saveMenu extends JFrame {
	
	private JTextField gameName;
	private JButton ok;
	private JButton cancel;
	private JTextArea notification;
	
	
	public saveMenu(Handler handler){
		super("Save as");
		setLayout(new FlowLayout());
		gameName = new JTextField(25);
		
		ok = new JButton("OK");
		cancel = new JButton("Cancel");
		notification = new JTextArea("Enter the game name");
		notification.setOpaque(false);
		notification.setEditable(false);
		
		this.add(notification);
		this.add(gameName);
		this.add(ok);
		this.add(cancel);
		
		gameName.addActionListener(handler);
		ok.addActionListener(handler);
		cancel.addActionListener(handler);
	}
	
	public JButton save(){
		return ok;
	}
	
	public String getSaveName(){
		return gameName.getText();
	}
	
	public void setNotification(String not){
		notification.setText(not);
	}
	
	public void clearTextField(){
		gameName.setText("");
	}
	
	public JButton cancel(){
		return cancel;
	}
}
