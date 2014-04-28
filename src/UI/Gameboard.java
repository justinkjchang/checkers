package UI;


import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.net.URL;

import javax.swing.*;
import javax.swing.border.Border;

import Controllers.Handler;
public class Gameboard extends JFrame{
	
	private int[][] board = new int[8][8];
	private int player;
	
	private URL url = Menu.class.getResource("lose.wav");
	private URL url1 = Menu.class.getResource("C6.wav");
	private URL url2 = Menu.class.getResource("C7.wav");
	private URL url3 = Menu.class.getResource("C5.wav");
	private URL url4 = Menu.class.getResource("E5.wav");
	private URL url5 = Menu.class.getResource("E6.wav");
	private URL url6 = Menu.class.getResource("Roll.wav");
	private AudioClip lose = Applet.newAudioClip(url);
	private AudioClip C6 = Applet.newAudioClip(url1);
	private AudioClip C7 = Applet.newAudioClip(url2);
	private AudioClip C5 = Applet.newAudioClip(url3);
	private AudioClip E5 = Applet.newAudioClip(url4);
	private AudioClip E6 = Applet.newAudioClip(url5);
	private AudioClip Roll = Applet.newAudioClip(url6);
	
    
    private JButton music;
	private Timer timer;
	private JLabel message;
	private JLabel lblplayer;
	private JButton toMenu;
	private JButton setupComplete;
	private JButton saveGame;
	private JPanel panel1;
	private JPanel panel2;
	private JPanel panel3;
	private JPanel panel4;
	private JButton switchPlayer;
	private JButton btn00,btn01,btn02,btn03,btn04,btn05,btn06,btn07;
	private JButton btn10,btn11,btn12,btn13,btn14,btn15,btn16,btn17;
	private JButton btn20,btn21,btn22,btn23,btn24,btn25,btn26,btn27;
	private JButton btn30,btn31,btn32,btn33,btn34,btn35,btn36,btn37;
	private JButton btn40,btn41,btn42,btn43,btn44,btn45,btn46,btn47;
	private JButton btn50,btn51,btn52,btn53,btn54,btn55,btn56,btn57;
	private JButton btn60,btn61,btn62,btn63,btn64,btn65,btn66,btn67;
	private JButton btn70,btn71,btn72,btn73,btn74,btn75,btn76,btn77;
	private JButton[] BTNS = {btn00,btn01,btn02,btn03,btn04,btn05,btn06,btn07,btn10,btn11,btn12,btn13,btn14,btn15,btn16,btn17,
								btn20,btn21,btn22,btn23,btn24,btn25,btn26,btn27,btn30,btn31,btn32,btn33,btn34,btn35,btn36,btn37,
								btn40,btn41,btn42,btn43,btn44,btn45,btn46,btn47,btn50,btn51,btn52,btn53,btn54,btn55,btn56,btn57,
								btn60,btn61,btn62,btn63,btn64,btn65,btn66,btn67,btn70,btn71,btn72,btn73,btn74,btn75,btn76,btn77};
	
	final int[] whitesquares = {0,2,4,6,9,11,13,15};
	final int[] brownsquares = {1,3,5,7,8,10,12,14};
	
	final private Border loweredBorder = BorderFactory.createLoweredBevelBorder();
	final private Icon white =  new ImageIcon(getClass().getResource("white.png"));
	final private Icon blackselected = new ImageIcon(getClass().getResource("selected.png"));
	final private Icon brown = new ImageIcon(getClass().getResource("brown.png"));
	final private Icon black = new ImageIcon(getClass().getResource("black-brown.png"));
	final private Icon red = new ImageIcon(getClass().getResource("red-brown.png"));
	final private Icon redselected = new ImageIcon(getClass().getResource("redselected.png"));
	final private Icon blackking = new ImageIcon(getClass().getResource("black-brown-king.png"));
	final private Icon redking= new ImageIcon(getClass().getResource("red-brown-king.png"));
	final private Icon blackkingselected = new ImageIcon(getClass().getResource("black-brown-king-selected.png"));
	final private Icon redkingselected = new ImageIcon(getClass().getResource("red-brown-king-selected.png"));
	final private Icon[] squarecolour = new Icon[BTNS.length];
	
	public Gameboard(Handler handler){
		super("Checkers");
		setLayout(new FlowLayout());
		message = new JLabel();
		lblplayer = new JLabel();
		toMenu = new JButton("Menu");
		switchPlayer = new JButton("Switch Player");
		setupComplete = new JButton("Setup Complete");
		saveGame = new JButton("Save Game");
		music = new JButton();
		music.setPreferredSize(new Dimension(20,20));
		player=1;
		message.setFont(message.getFont().deriveFont(9.0f));
		panel1 = new JPanel();
		panel2 = new JPanel();
		panel3 = new JPanel();
		panel4 = new JPanel();
		panel1.setPreferredSize(new Dimension(400, 20));
		panel2.setPreferredSize(new Dimension(400, 40));
		panel3.setPreferredSize(new Dimension(200, 20));
		panel4.setPreferredSize(new Dimension(190, 30));
		panel1.setLayout(new BorderLayout());
		panel2.setLayout(new BorderLayout());
		panel3.setLayout(new BorderLayout());
		panel1.add(message,BorderLayout.WEST);
		panel1.add(toMenu,BorderLayout.EAST);
		panel4.add(setupComplete,BorderLayout.WEST);
		panel4.add(saveGame, BorderLayout.CENTER);;
		panel2.add(panel4,BorderLayout.CENTER);
		
		panel3.add(switchPlayer, BorderLayout.EAST);
		panel3.add(lblplayer,BorderLayout.WEST);
		panel3.add(music,BorderLayout.SOUTH);
		panel2.add(panel3,BorderLayout.EAST);
		toMenu.addActionListener(handler);
		setupComplete.addActionListener(handler);
		switchPlayer.addActionListener(handler);
		saveGame.addActionListener(handler);
		music.addActionListener(handler);
		
		timer = new Timer(440, handler);

		Listener listener = new Listener();
		
		for(int i=0;i<BTNS.length;i++){
			BTNS[i] = new JButton("", white);			
			for(int j=0;j<8;j++){
				if(i%16==whitesquares[j]) squarecolour[i]=white;
				else if(i%16==brownsquares[j]) squarecolour[i]=brown;
			}
			
			
			BTNS[i].setBorder(loweredBorder);
			add(BTNS[i]);
			BTNS[i].addActionListener(handler);
			BTNS[i].addPropertyChangeListener(listener);
		}
		
		
		add(panel1);
		add(panel2);				
		timer.start();
	}
	
	public class Listener implements PropertyChangeListener{

		public void propertyChange(PropertyChangeEvent event) {
			update();
		}
		
	}
	
	public JButton music(){
		return music;
	}
	
	public Timer timer(){
		return timer;
	}

	
	public JButton[] BTNS(){
		return BTNS;
	}
	
	public int[][] board(){
		return board;
	}
	
	
	
	public void board(int board[][]){
		this.board=board;
	}
	
	public int player(){
		return player;
	}
	
	public void setPlayer(int p){
		player = p;
	}
	
	public void setMessage(String s){
		message.setText(s);
	}
	
	public String getMessage(){
		return message.getText();
	}
	
	public void setlblPlayer(){
		if(!isCustom())
			lblplayer.setText(String.format("Player %s's turn",player));
		else
			lblplayer.setText(String.format("Player %s's turn",player%2+1));
	}
	
	public JButton switchPlayer(){
		return switchPlayer;
	}
	
	public JButton toMenu(){
		return toMenu;
	}
	
	public JButton setupComplete(){
		return setupComplete;
	}
	
	public AudioClip getClip(String clip){
		switch(clip){
		case "lose": return lose; 
		case "C5": return C5; 
		case "C6": return C6; 
		case "C7": return C7; 
		case "E5": return E5; 
		case "E6": return E6; 
		case "Roll": return Roll; 
		}
		return Roll;
	}

public Icon getColour(int a, int b){
	return squarecolour[a*8+b];
}

public JButton saveGame(){
	return saveGame;
}

static void print(int[][] A){
	System.out.println();
	System.out.println();
	for(int row=0;row<8;row++){
		if(row>0){
			System.out.println();
			System.out.println();
		}
		for(int col=0;col<8;col++)
			System.out.print(A[row][col]+"  ");
	}
	System.out.println();
}

public boolean isCustom(){
	for(int row=0;row<8;row++)
		for(int col=0;col<8;col++)
			if(board[row][col]==100) return true;
	return false;
}

public int[][] init(){
	int[][] A=new int[8][8];
	
	for(int row=0;row<8;row++)
		for(int col=0;col<8;col++){
			if((row==5||row==7) && col%2==0) A[row][col]=2;
			else if(row==6 && col%2==1)A[row][col]=2;
			else if((row==0||row==2) && col%2==1) A[row][col]=1;
			else if(row==1&& col%2==0) A[row][col]=1;
			else A[row][col]=0;
			
			if(getColour(row,col)==white) A[row][col]=-1;
		}
	return A;			
}

public int[][] customInit(){
	int[][] A=new int[8][8];
	for(int row=0;row<8;row++)
		for(int col=0;col<8;col++){
			A[row][col]=100;		
			if(getColour(row,col)==white) A[row][col]=-1;
		}
	return A;			
}

	public void update(){
		//print(board);
		int row, col;
		
		for(int i=0;i<BTNS.length;i++){
			row = i/8;
			col = i%8;
			
			switch(board[row][col]){
			case -1://a white square
				BTNS[i].setIcon(white);
				BTNS[i].setRolloverIcon(white);
				break;
				
			case 0://a brown square
				BTNS[i].setIcon(brown);
				BTNS[i].setRolloverIcon(brown);
				break;
				
			case 1://P1 has a piece on this square
				BTNS[i].setIcon(black);
				BTNS[i].setRolloverIcon(blackselected);
				break;
				
			case 2://P2 has a piece on this square
				BTNS[i].setIcon(red);
				BTNS[i].setRolloverIcon(redselected);
				break;
				
			case 3://P1 has selected the piece on this square
				BTNS[i].setIcon(blackselected);
				BTNS[i].setRolloverIcon(blackselected);
				break;
				
			case 4://P2 has selected the piece on this square
				BTNS[i].setIcon(redselected);
				BTNS[i].setRolloverIcon(redselected);
				break;
			
			case 13:
			case 11://P1 can move here with the selected piece
				BTNS[i].setIcon(brown);
				BTNS[i].setRolloverIcon(black);
				break;
			
			case 14:
			case 12://P2 can move here with the selected piece
				BTNS[i].setIcon(brown);	
				BTNS[i].setRolloverIcon(red);
				break;
				
			case 21:
				BTNS[i].setIcon(blackking);
				BTNS[i].setRolloverIcon(blackkingselected);
				break;
				
			case 22:
				BTNS[i].setIcon(redking);
				BTNS[i].setRolloverIcon(redkingselected);
				break;
				
			case 23:
				BTNS[i].setIcon(blackkingselected);
				BTNS[i].setRolloverIcon(blackkingselected);
				break;
				
			case 24:
				BTNS[i].setIcon(redkingselected);
				BTNS[i].setRolloverIcon(redkingselected);
				break;
			
			case 33:
			case 31:
				BTNS[i].setIcon(brown);
				BTNS[i].setRolloverIcon(blackking);
				break;
			
			case 34:
			case 32:
				BTNS[i].setIcon(brown);
				BTNS[i].setRolloverIcon(redking);
				break;
				
			case 100://custom setup
				BTNS[i].setIcon(brown);
				if(player==1)
					BTNS[i].setRolloverIcon(black);
				else if(player==2)
					BTNS[i].setRolloverIcon(red);
				break;
				
				
			}//switch end		
		}//loop end
	}//method end
	
	
	
	
}