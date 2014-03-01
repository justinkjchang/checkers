import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.border.Border;
public class Gameboard extends JFrame{
	
	private int[][] board = new int[8][8];
	private int player;
	private boolean isPieceSelected;
	
	private JLabel message;
	private JLabel lblplayer;
	private JButton toMenu;
	private JButton setupComplete;
	private JPanel panel1;
	private JPanel panel2;
	private JPanel panel3;
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
	
	int[] whitesquares = {0,2,4,6,9,11,13,15};
	int[] brownsquares = {1,3,5,7,8,10,12,14};
	
	Border loweredBorder = BorderFactory.createLoweredBevelBorder();
	Icon white =  new ImageIcon(getClass().getResource("white.png"));
	Icon blackselected = new ImageIcon(getClass().getResource("selected.png"));
	Icon brown = new ImageIcon(getClass().getResource("brown.png"));
	Icon black = new ImageIcon(getClass().getResource("black-brown.png"));
	Icon red = new ImageIcon(getClass().getResource("red-brown.png"));
	Icon redselected = new ImageIcon(getClass().getResource("redselected.png"));
	Icon[] squarecolour = new Icon[BTNS.length];
	
	public Gameboard(Handler handler){
		super("Checkers");
		setLayout(new FlowLayout());
		message = new JLabel();
		lblplayer = new JLabel();
		toMenu = new JButton("Menu");
		switchPlayer = new JButton("Switch Player");
		setupComplete = new JButton("Setup Complete");
		player=1;
		panel1 = new JPanel();
		panel2 = new JPanel();
		panel3 = new JPanel();
		panel1.setPreferredSize(new Dimension(400, 20));
		panel2.setPreferredSize(new Dimension(400, 20));
		panel3.setPreferredSize(new Dimension(200, 20));
		panel1.setLayout(new BorderLayout());
		panel2.setLayout(new BorderLayout());
		panel3.setLayout(new BorderLayout());
		panel1.add(message,BorderLayout.WEST);
		panel1.add(toMenu,BorderLayout.EAST);
		panel2.add(setupComplete,BorderLayout.WEST);
		panel3.add(switchPlayer, BorderLayout.EAST);
		panel3.add(lblplayer,BorderLayout.WEST);
		panel2.add(panel3,BorderLayout.EAST);
		toMenu.addActionListener(handler);
		setupComplete.addActionListener(handler);
		switchPlayer.addActionListener(handler);


		
		for(int i=0;i<BTNS.length;i++){
			BTNS[i] = new JButton("", white);			
			for(int j=0;j<8;j++){
				if(i%16==whitesquares[j]) squarecolour[i]=white;
				else if(i%16==brownsquares[j]) squarecolour[i]=brown;
			}
			
			
			BTNS[i].setBorder(loweredBorder);
			add(BTNS[i]);
			BTNS[i].addActionListener(handler);
		}
		update();
		add(panel1);
		add(panel2);
		
	}
	
	public boolean isPieceSelected(){
		return isPieceSelected;
	}
	
	public JButton[] BTNS(){
		return BTNS;
	}
	
	public int board(int row, int col){
		return board[row][col];
	}
	
	public int board(int[] coords){
		return board[coords[0]][coords[1]];
	}
	
	public void board(int board[][]){
		this.board=board;
	}
	
	
	
	public void board(int row, int col, int value){
		board[row][col]=value;
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
	
	public void setlblPlayer(){
		lblplayer.setText(String.format("Player %s's turn",player));
	}
	
	public void placePiece(String colour, int a, int b){
		if(colour=="black"){
			board[a][b]=1;
		}
		else if(colour=="red"){
			board[a][b]=2;
		}
		update();
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
	
	public int[] getSelected(){
		int[] c = {9,9};
		for(int i=0;i<8;i++)
			for(int j=0;j<8;j++)
				if(board[i][j]==3||board[i][j]==4){
					c[0]=i;
					c[1]=j;
				}
		return c;					
	}
	
public void updateSelected(){
	boolean found=false;
	for(int i=0;i<8;i++)
		for(int j=0;j<8;j++)
			if(board[i][j]==3||board[i][j]==4){
				found=true;
			}
	isPieceSelected=found;
}

public Icon getColour(int a, int b){
	return squarecolour[a*8+b];
}

public Icon white(){
	return white;
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
		print(board);
		updateSelected();
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
				
			case 11://P1 can move here with the selected piece
				BTNS[i].setIcon(brown);
				BTNS[i].setRolloverIcon(black);
				break;
				
			case 12://P2 can move here with the selected piece
				BTNS[i].setIcon(brown);	
				BTNS[i].setRolloverIcon(red);
				break;
				
			case 21:
				//P1 King
				break;
				
			case 22:
				//P2 King
				break;
				
			case 23:
				//P1 King selected
				break;
				
			case 24:
				//P2 King selected
				break;
				
			case 31:
				//P1 selected king can move here
				break;
				
			case 32:
				//P2 selected king can move here
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