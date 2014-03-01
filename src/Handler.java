import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.border.Border;

	public class Handler extends JFrame implements ActionListener{
		private int setup;
		private int blacks;
		private int reds;
		private String message;
		Menu menu;
		Gameboard game;
		private int row;
		private int col;
		public void actionPerformed(ActionEvent event) {
			menu=main.menu;
			game=main.game;

			//Handles case where Start Button was clicked
			if(event.getSource()==menu.start())
				StartButtonClicked();
			
			if(event.getSource()==game.setupComplete())
				setupCompleteClicked();
					
			//Handles Checkboxs on main Menu
			if(event.getSource()==menu.Single()){
				SingleCheckBoxClicked();
			}
			if(event.getSource()==menu.Double()){
				DoubleCheckBoxClicked();
			}
			
			//Handles case where switchPlayer is clicked
			if(event.getSource()==game.switchPlayer())
				SwitchPlayerClicked();
			
			//Handles case where Menu button is clicked
			if(event.getSource()==game.toMenu())
				toMenu();
			
			//Handles case where a checkers square was clicked
			for(int i=0;i<game.BTNS().length;i++)
				if(event.getSource()==game.BTNS()[i])//loop finds square that was clicked
						CheckersSquareClicked(i);
	
			
			game.setMessage(message); //displays message
			game.setlblPlayer(); //displays the active player
		}
		
		
		
		
		
		
		
		public void setupCompleteClicked(){
			reds=0;
			blacks=0;
			message="Player 1's turn";
			game.setupComplete().setVisible(false);
			for(int row=0;row<8;row++)
				for(int col=0;col<8;col++)
					if(game.board(row,col)==100)
						game.board(row,col,0);
			game.update();
		}
		
		public void toMenu(){
			game.setVisible(false);
			menu.setVisible(true);
		}
		
		
		public void SingleCheckBoxClicked(){
			menu.Single().setSelected(true);
			menu.Double().setSelected(false);
		}
		
		
		
		
		
		public void DoubleCheckBoxClicked(){
			menu.Double().setSelected(true);
			menu.Single().setSelected(false);
		}
		
		

		
		public void StartButtonClicked(){	
			//Check if a game is in progress
			if(setup==2){
				
				String[] options = {"Continue Game","New Game"};
				
				setup = JOptionPane.showOptionDialog(null, "Continue Game or Start a New Game?", "Game Setup", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
				
				//If Continue Game was selected
				if(setup==0){
					setup=2;
					displaySetupMessage();
					game.setupComplete().setVisible(false);
					game.setVisible(true);
					menu.setVisible(false);	
				}
				
				//If New Game was selected
				else if(setup==1){
					for(int i=0;i<8;i++)
						for(int j=0;j<8;j++)
							game.board(i,j,0);
					game.update();
					StartButtonClicked();
				}	
			}
			
			//If no game is in progress
			else{
				String[] options = {"Regular","Custom"};
				setup = JOptionPane.showOptionDialog(null, "Regular or Custom Setup?", "Game Setup", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
				
				//If user chose regular setup
				if(setup==0){
					reds=0;
					blacks=0;
					game.board(game.init());
					game.update();
					game.setupComplete().setVisible(false);
				}
				
				//If user chose custom setup
				else if(setup==1){
					game.setupComplete().setVisible(true);
					game.board(game.customInit());
				}
				displaySetupMessage();
				game.setVisible(true);
				menu.setVisible(false);	
			}
			game.update();
		}
		
		public void SwitchPlayerClicked(){
			if(game.player()==1)
				game.setPlayer(2);
			else if(game.player()==2)
				game.setPlayer(1);
			game.update();
		}
		
		
		
		public void CheckersSquareClicked(int i){
					row=i/8;
					col=i%8;
					message = String.format("You pressed %s%s",row,col);
					
				//if pieces still have to be placed custom setup will handle this click
				if(reds>0||blacks>0) CustomSetup();
				
				//actual game
				else{
					
					int piece = game.player();
					int clickedSquare = game.board(row, col);
					
					//select
					if(game.board(row,col)==piece && game.isPieceSelected()==false){
						setSelected(row,col);
					}
					
					//deselect
					else if(clickedSquare==piece+2){
						Deselect();					
					}
					
					//switch select
					else if(game.board(row,col)==piece){			
						Deselect();
						setSelected(row,col);
					}		
					game.update();
				}
		}
		
		
		
		
		public void displaySetupMessage(){
			if(setup==0){ //message to be displayed if user chooses a regular setup
				message = "Start Game";
				setup=2;
			}
			else if(setup==1){ //message to be displayed if user chooses a custom setup
				message = "Setup 12 black pieces and then 12 red pieces";
				setup=2;
				blacks=12;
				reds=12;
			}
		}
		
		
		
		
		
		public void CustomSetup(){
		if(game.getColour(row, col)!=game.white()){
			int piece = game.player();
			
			if(blacks>0 && game.board(row,col)==100 && piece==1){
				blacks=blacks-1;
				if(blacks==0){
					game.setPlayer(2);
					message="Pieces remaining for Player 2: " + reds;
				}
				else message="Pieces remaining for Player 1: " + blacks;
				game.placePiece("black", row, col);
			}
			else if(reds>0 && game.board(row,col)==100 && piece==2){
				reds=reds-1;
				if(reds==0){
					game.setPlayer(1);
					message="Start Game";
				}
				else message="Pieces remaining for Player 2: " + reds;
				game.placePiece("red", row, col);			
			}	
			if(blacks==0&&reds==0) {
				setupCompleteClicked();
			}
		}
		
		else{
			message="Illegal Placement. Place on brown squares only.";
		}
		}
		
		public void setSelected(int row, int col){
			game.board(row,col,game.board(row, col)+2);
			setLegalsFor(row,col);
		}
		
		public void Deselect(){
			int row = game.getSelected()[0];
			int col = game.getSelected()[1];
			game.board(row,col,game.board(row, col)-2);
			removeLegalsFor(row,col);
		}
		
		public void setLegalsFor(int row, int col){
			boolean isKing=false;
			//Determines whether or not the given piece is a king
			if(game.board(row,col)==21||game.board(row,col)==22){
				isKing=true;
			}
			
			//down right
			if(col!=7 && row!=7 && game.board(row+1, col+1)==0 && (game.player()==1 || isKing)){
				game.board(row+1,col+1,game.board(game.getSelected())+8);
			}
			
			//down left
			if(col!=0 && row!=7 && game.board(row+1, col-1)==0 && (game.player()==1 || isKing)){
				game.board(row+1, col-1,game.board(game.getSelected())+8);
			}
				
			//up right
			if(col!=7 && row!=0 && game.board(row-1, col+1)==0 && (game.player()==2 || isKing)){
				game.board(row-1, col+1,game.board(game.getSelected())+8);
			}
			
			//up left
			if(col!=0 && row!=0 && game.board(row-1, col-1)==0 && (game.player()==2 || isKing)){
				game.board(row-1, col-1,game.board(game.getSelected())+8);
			}
		}
		
		public void removeLegalsFor(int row, int col){
			//down right
			if(col!=7 && row!=7 && game.board(row+1, col+1)>10 && (game.board(row+1, col+1)%10==1 || game.board(row+1, col+1)%10==2)){
				game.board(row+1,col+1,0);
			}
			
			//down left
			if(col!=0 && row!=7 && game.board(row+1, col-1)>10 && (game.board(row+1, col-1)%10==1 || game.board(row+1, col-1)%10==2)){
				game.board(row+1, col-1,0);
			}
				
			//up right
			if(col!=7 && row!=0 && game.board(row-1, col+1)>10 && (game.board(row-1, col+1)%10==1 || game.board(row-1, col+1)%10==2)){
				game.board(row-1, col+1,0);
			}
			
			//up left
			if(col!=0 && row!=0 && game.board(row-1, col-1)>10 && (game.board(row-1, col-1)%10==1 || game.board(row-1, col-1)%10==2)){
				game.board(row-1, col-1,0);
			}
		}
		
		
	}