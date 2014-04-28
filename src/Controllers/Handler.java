package Controllers;


import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.swing.*;

import Main.main;
import UI.Gameboard;
import UI.LoadMenu;
import UI.Menu;
import UI.saveMenu;

	//refer to page 8 in the design document

	public class Handler extends Checkers implements ActionListener{
		private boolean gameOver;
		private int animationCounter;
		private boolean gameSound;
	    private int themeCounter;
		private int setup;
		private int blacks;
		private int reds;
		private String message;
		private Menu menu;
		private Gameboard game;
		private LoadMenu load;
		private saveMenu save;
		private int row;
		private int col;
		private int[][] board;
		private int player;
		private int singlePlayer;
		private int computerPlayer;
		private int difficulty;
		public void actionPerformed(ActionEvent event) {
			menu=main.menu;
			game=main.game;
			load=main.load;
			save=main.save;
			board=game.board();
			player=game.player()%2+1;
			//Due to new requirement stating red must be player 1, and start
			Object Source = event.getSource();

			if(Source==menu.start())
				StartButtonClicked();
			
			else if(Source==game.setupComplete())
				setupCompleteClicked();
					
			else if(Source==menu.Single()){
				SingleCheckBoxClicked();
			}
			
			else if(Source==menu.Double()){
				DoubleCheckBoxClicked();
			}
			
			else if(Source==menu.music()){
				musicButtonClicked();
			}
		
			else if(Source==game.music()){
				gameMusic();
			}
			
			else if(Source==menu.load()){
				LoadButtonClicked();
			}
			
			else if(Source==game.saveGame()){
				save.setVisible(true);
			}
			
			else if(Source==load.Load()){
				LoadGameClicked();
			}
			
			else if(Source==load.toMenu()){
				toMenu();
			}
			
			else if(Source==game.switchPlayer()){
				SwitchPlayerClicked();
			}
			
			else if(Source==game.toMenu()){
				toMenu();
			}

			else if(Source==save.save()) {
				saveClicked();
			} 
			
			else if(Source==save.cancel()){
				save.setVisible(false);
			}
			
			//Handles case where a checkers square was clicked
			for(int i=0;i<game.BTNS().length;i++)
				if(Source==game.BTNS()[i])//loop finds square that was clicked
						CheckersSquareClicked(board, i);
			
			
			
			
			
			update();
			//Scenario x = new Scenario(board, player, 3);
		
			//AI MOVE
			if(!gameOver(board) && menu.Single().isSelected() && Source==game.timer() && game.isVisible() && player==computerPlayer){
				aiMove();
				update();
			}
			
			if(game.timer().isRunning())
				game.timer().restart();
			
			if(Source!=game.timer()){
				game.timer().start();
			}
			//MENU ANIMATION
			if(Source==game.timer()){
				animationCounter++;
				if(animationCounter==14) animationCounter=0;
				if(animationCounter%2==0) menu.red();
				if(animationCounter%2==1) menu.grey();
				if(menu.music().getText().equals("Sound On")) menu.pic();
				}
			
			//CUSTOM SETUP
			if(blacks!=0 || reds!=0) game.timer().stop();
			
			
			
			//MUSIC
			if(Source==game.timer() && menu.isVisible() && menu.music().getText().equals("Sound Off")){
			    theme();
			}
			
			
			
			if(game.music().getText().equals("Sound Off")) gameSound=true;
			else gameSound=false;
		}
		
		private void saveClicked() {
			if(save.getSaveName().equals("")){
				save.setVisible(true);
				save.setNotification("Please enter a valid name");
			} else {
				save.setVisible(false);
			}
			SaveGameClicked();
			save.clearTextField();
		}

		private void gameMusic() {
			if(game.music().getText()=="Sound Off"){
				game.music().setText("Sound On");
				gameSound=false;
			}
			else{
				game.music().setText("Sound Off");
				gameSound=true;
			}
			
		}

		private void musicButtonClicked() {
			if(menu.music().getText()=="Sound Off"){
				menu.music().setText("Sound On");
				menu.clip().stop();
			}
			else{
				menu.music().setText("Sound Off");
				themeCounter=0;
				theme();
			}
		}

		public void update(){
			game.setMessage(message); //displays message
			game.setPlayer(player%2+1); //Due to new requirement stating red must be player 1, and start
			game.setlblPlayer(); //displays the active player
			game.board(board);
			game.update();
		}
		
		private void theme(){
		    
		    if(themeCounter%124==0) {
                menu.clip().play();
                themeCounter=0;
            }
            themeCounter++;
            
		}
//Assignment 2	Start	
		
		private void checkGameOver(){
			//GAME OVER
			if(setup==2 && (!menu.isVisible() && gameOver(board) && reds==0 && blacks==0)){
				if(gameSound){
                if(menu.Single().isSelected() && getNumPieces(board,singlePlayer)==0) game.getClip("lose").play();
                else theme();
				}
                message = "Game Over!";
                if(getNumPieces(board,1)<getNumPieces(board,2)){
                	message="Player 1 wins!";
                }
                else if(getNumPieces(board,1)>getNumPieces(board,2)){
                	message="Player 2 wins!";
                }
                else{
                	message="Game Over! (draw)";
                }
                game.timer().stop();
                
            }
		}
		
		private void moveSelected(int[][] board, int row, int col){	
				
			
			boolean madeJump = false;
			int sRow = getSelected(board)[0];
			int sCol = getSelected(board)[1];
			if(board[row][col]==13 || board[row][col]==14){
				jumpMove(board,row, col);
				if(player==1) message=">:(";
				madeJump = true;
				if(gameSound==true){
					if(player==1 && row!=7) game.getClip("E5").play();
					if(player==2 && row!=0) game.getClip("E6").play();
				}
			}
			else if(board[row][col]==33 || board[row][col]==34){
				jumpMove(board,row, col);
				if(player==1) message=">:(";
				madeJump = true;if(gameSound==true){
					if(player==1 && row!=7) game.getClip("E5").play();
					if(player==2 && row!=0) game.getClip("E6").play();
				}
				
			}
			removeLegalJumpsFor(board,sRow,sCol);
			
	
			if(board[row][col]==11 || board[row][col]==12){
				regularMove(board,row, col);
				if(gameSound==true){
					if(player==1 && row!=7) game.getClip("C6").play();
					if(player==2 && row!=0) game.getClip("C7").play();
				}
			}
			else if(board[row][col]==31 || board[row][col]==32){
				regularMove(board,row, col);
				if(gameSound==true){
					if(player==1 && row!=7) game.getClip("C6").play();
					if(player==2 && row!=0) game.getClip("C7").play();
				}
			}
			removeLegalMovesFor(board,sRow,sCol);
			
			boolean anyJumps = canJump(board,player,row,col);
			
			if(player==1 && row==7){
				board[row][col]=21;
				if(gameSound) game.getClip("Roll").play();
			}
			else if(player==2 && row==0){
				board[row][col]=22;
				if(gameSound) game.getClip("Roll").play();
			}
			if(!anyJumps || !madeJump){
				player = player%2 + 1;
			}
			Deselect(board);
			removeLegalMovesFor(board,sRow,sCol);
			removeLegalJumpsFor(board,sRow,sCol);
			checkGameOver();
			}
		
		private void aiMove(){
			int[][] move = chooseMove(board, player, difficulty);
			int sRow = move[0][0];
			int sCol = move[0][1];
			int fRow = move[1][0];
			int fCol = move[1][1];
			if(sRow != fRow && sCol != fCol){
				setSelected(board,player,sRow,sCol);
				moveSelected(board,fRow,fCol);
				if(sRow+1!=fRow && sRow-1!=fRow) message=">:D";
			}
			else{
				game.timer().stop();
				SwitchPlayerClicked(); //in order to resign
			}
		if(player!=computerPlayer)	
			game.timer().stop();
		AIComment();
		checkGameOver();
		}
		
	
	
			
		private void LoadGameClicked(){
			game.setupComplete().setVisible(false);
			//implement method
			// check if saveData exists
			File f = new File("saveData.txt");
			boolean check = f.exists();
			
			// determine which game to load
			String selectedGame = load.getSelectedGame();
			
			// check if a game is selected
			if(selectedGame == null){
				load.setVisible(true);
				
				load.setMessage("                 Please select a game");
			} else {
				load.setVisible(false);
				load.deselect();
				loadGameData(f,selectedGame);		
				continueGame();
			}		
		}
		
	private void loadGameData(File f, String selectedGame){
		// load game data
		String line;
		List<String> gameName;
		int counter = 1;
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(f));
			while((line = br.readLine()) != null){
				// get data's game name
				gameName = Arrays.asList(line.split(","));
				
				if(gameName.get(0).equals(selectedGame)){
					for(int i = 0; i < 8; i++){
						for(int j = 0; j < 8; j++){
							board[i][j] = Integer.parseInt(gameName.get(counter));
							System.out.print(gameName.get(counter) + " ");
							counter++;
						}
					}
					System.out.println("Loaded game: " + gameName.get(0));
					player=Integer.parseInt(gameName.get(gameName.size()-1));
				}
				
				
			}
			br.close();
			
			game.setVisible(true);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
			
		private void SaveGameClicked(){
			//implement method
			BufferedWriter gameData = null;

			// check if saveData already exists
			File f = new File("saveData.txt");
			boolean check = f.exists();
			
			String gameName = save.getSaveName();
			System.out.println("gameName: " + gameName);
			
			if(!check){
				try {
					// create saveData.txt if it doesn't exist
					PrintWriter writer = new PrintWriter("saveData.txt", "UTF-8");
					
					// write game name
					writer.print(gameName);
					
					// write data
					for(int row=0;row<8;row++){
						for(int col=0;col<8;col++)
							if(row == 7 && col == 7){
								writer.print(board[row][col] + "\r\n");
							} else {
								writer.print(board[row][col]+",");
							}
							
					}
					writer.print(board[row][col]+",");
					writer.println(player);
					
					writer.close();
					
					String currPath = new java.io.File(".").getCanonicalPath();
					System.out.println("File saved to: " + currPath);
					
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					System.out.println("Error: File not found");
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					System.out.println("Error: Unsupported Encoding");
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println("Error: I/O Exception");
					e.printStackTrace();
				}
				
			} else {
				System.out.println("File exists");
				try {
					
					// open file for writing
					FileWriter fstream = new FileWriter("./saveData.txt", true);
					gameData = new BufferedWriter(fstream);
					
					// write game name
					gameData.write(gameName + ",");
					String[] savedGames = load.getSavedGames();
					for(int i = 0; i < 50; i++){
						if(savedGames[i]==(null)){
							savedGames[i] = gameName;
							load.setSavedGames(savedGames);
							break;
						}
					}
					
					// write actual game data
					for(int row=0;row<8;row++){
						for(int col=0;col<8;col++)
							if(row == 7 && col == 7){
								gameData.write(String.valueOf(board[row][col]) + "\r\n");
							} else if (row == 0 && col == 0){
								gameData.write(String.valueOf(board[row][col]) + ",");
							} else {
								gameData.write(String.valueOf(board[row][col])+",");
							}
					}
					
					String currPath = new java.io.File(".").getCanonicalPath();
					gameData.close();
					System.out.println("File saved to: " + currPath + "\\saveData.txt");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		
		
		
		
	//Assignment 2 Ends	
		

		
	//Assignment 1	Starts
		private void setupCompleteClicked(){
			game.switchPlayer().setText("Resign");
			game.switchPlayer().setVisible(true);
			game.saveGame().setVisible(true);
			reds=0;
			blacks=0;
			message="Player 1's turn";
			player=2;
			game.setupComplete().setVisible(false);
			for(int row=0;row<8;row++)
				for(int col=0;col<8;col++)
					if(board[row][col]==100)
						board[row][col]=0;
		}
			
		private void toMenu(){
			if(game.music().getText().equals("Sound Off")) menu.music().setText("Sound Off");
			else if(game.music().getText().equals("Sound On")) menu.music().setText("Sound On");
			game.setVisible(false);
			load.setVisible(false);
			load.deselect();
			menu.setVisible(true);
		}
			
		private void SingleCheckBoxClicked(){
			menu.Single().setSelected(true);
			menu.Double().setSelected(false);
		}
			
		private void DoubleCheckBoxClicked(){
			menu.Double().setSelected(true);
			menu.Single().setSelected(false);
		}
		
		
		private void LoadButtonClicked(){
			gameOver=false;
			loadData();
			menu.setVisible(false);
			load.setVisible(true);
		}
		
		private void loadData() {
			String line;
			List<String> gameName;
			String[] savedGames = new String[50];
			
			File f = new File("saveData.txt");
			try {
				BufferedReader br = new BufferedReader(new FileReader(f));
				while((line = br.readLine()) != null){
					// get data's game name
					gameName = Arrays.asList(line.split(","));
					
					// add saved game to list
					for(int i = 0; i < 50; i++){
						if(savedGames[i]==(null)){
							savedGames[i] = gameName.get(0);
							break;
						}
					}
					
				}
				br.close();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			load.setSavedGames(savedGames);
		}

		private void continueGame(){
			game.music().setText(menu.music().getText());
			menu.clip().stop();
			update();
			game.timer().stop();
			singlePlayerMode();
			
			setup=2;
			
			if(setup==2 && ((menu.Single().isSelected() && singlePlayer!=computerPlayer) || (!menu.Single().isSelected()))){
			
			displaySetupMessage();
			game.switchPlayer().setText("Resign");
			game.setupComplete().setVisible(false);
			game.saveGame().setVisible(true);
			game.setVisible(true);
			menu.setVisible(false);	
			game.timer().start();
			}
		}
		private void newGame(){
			setup=0;
			for(int i=0;i<8;i++)
				for(int j=0;j<8;j++)
					board[i][j]=0;
			game.update();
			game.switchPlayer().setText("Switch Player");
			game.switchPlayer().setVisible(true);
			StartButtonClicked();
		}		
		private void regularSetup(){
			reds=0;
			blacks=0;
			player=2;
			board=game.init();
			game.update();
			game.setupComplete().setVisible(false);
			game.saveGame().setVisible(true);
			displaySetupMessage();
			game.switchPlayer().setVisible(true);
			setup=2; //game in progress
		}
		private void customSetup(){
			player=2;
			game.setupComplete().setVisible(true);
			game.saveGame().setVisible(false);
			board=game.customInit();
			blacks=12;
			reds=12;
			displaySetupMessage();
			setup=2; //game in progress
		}

		private void StartButtonClicked(){	
			gameOver=false;
			game.music().setText(menu.music().getText());
			game.timer().stop();
			int regular = 0;
			int custom = 1;
			int inprogress = 2;
			//Check if a game is in progress
			if(setup==inprogress){		
				String[] options = {"Continue Game","New Game"};		
				setup = JOptionPane.showOptionDialog(menu, "Continue Game or Start a New Game?", "Game Setup", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);	
				//If Continue Game was selected
				if(setup==0) continueGame();
				//If New Game was selected
				else if(setup==1) newGame();
			}
			//If no game is in progress
			else{
				player=1;
				String[] options = {"Regular","Custom"};
				setup = JOptionPane.showOptionDialog(menu, "Regular or Custom Setup?", "Game Setup", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
				
				//If user chose regular setup
				if(setup==regular){		
					regularSetup();
					game.switchPlayer().setText("Resign");
				}	
				//If user chose custom setup
				else if(setup==custom){
					customSetup();
					
				}
				if(menu.Single().isSelected()){
					singlePlayerMode();
				}
				System.out.println((menu.Single().isSelected() && singlePlayer!=computerPlayer));
				System.out.println(difficulty);
				if(setup==2 && ((menu.Single().isSelected() && singlePlayer!=computerPlayer) || (!menu.Single().isSelected()))){
					game.setVisible(true);
					menu.setVisible(false);
					menu.clip().stop();
					themeCounter=0;
				}
				
			}
			game.timer().start();
			update();
		}
		
		private void singlePlayerMode(){
			if(menu.Single().isSelected()){
				String[] options = {"Player 1","Player 2"};
				singlePlayer = JOptionPane.showOptionDialog(menu, "Which player will you be?", "Game Setup", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
				singlePlayer = (singlePlayer + 1)%2 + 1;
				computerPlayer = singlePlayer%2+1;
				
				selectDifficuluty();
				
			}
			
			else{
				singlePlayer=0;
				computerPlayer=0;
			}
		}
		
		
		private void selectDifficuluty(){
			String[] options = new String[4];
			options[0] = "Very Easy";
			options[1] = "Easy";
			options[2] = "Medium";
			options[3] = "Hard";
			int choice = JOptionPane.showOptionDialog(menu, "What difficulty?", "Game Setup", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
			difficulty = choice - 2;
			
		}

		private void SwitchPlayerClicked(){
			if(game.switchPlayer().getText().equals("Switch Player")){
				player = player%2 + 1;
			}
			else{
				gameOver=true;
				message="Player " + player + " wins!";		
			}
		}
		
		private void CheckersSquareClicked(int[][] board, int row, int col){
			int button = row*8+col;
			CheckersSquareClicked(board, button);
		}
		private void CheckersSquareClicked(int[][] board, int i){
			if(!gameOver){
				row=i/8;
				col=i%8;
					
				//if pieces still have to be placed custom setup will handle this click
				if(reds>0||blacks>0) CustomSetup();
				
				//actual game
				else{
					
					int pieceOfPlayer = player;
					int clickedSquare = board[row][col];
				
					
					switch(clickedSquare){
				
					case -1:
						message="You clicked an illegal square";
					break;
					
					case 0:
						if(!isSelected(board)) message="Please select a square first";
						else if(isSelected(board) && anyLegalJumps(board, player)) message="Make the jump!";
					break;
					
					case 1:
						if(player==1){
							setSelected(board,player,row,col);
							if(menu.Double().isSelected())message="";
						}
						else{
							message="Player 2 cannot select Player 1's pieces";
						}
					break;
					
					case 2:
						if(player==2){
							setSelected(board,player,row,col);
							if(menu.Double().isSelected())message="";
						}
						else{
							message="Player 1 cannot select Player 2's pieces";
						}
					break;
					
					case 3:
						if(player==1){
							Deselect(board);
							if(menu.Double().isSelected())message="";
						}
					break;
					
					case 4:
						if(player==2){
							Deselect(board);	
							if(menu.Double().isSelected())message="";
						}
					break;
					
					case 13:
					case 11: // If player one has a selected piece and clicks 11, then they can legally move there selected piece to this square
						if(player==1){
							moveSelected(board,row,col);
							if(menu.Double().isSelected())message="";
						}
					break;
					
					case 14:
					case 12: // If player one has a selected piece and clicks 11, then they can legally move there selected piece to this square
						if(player==2){
							moveSelected(board,row,col);
							if(menu.Double().isSelected())message="";
						}
					break;
					
					case 21: //player 1 has a king on this square
						if(player==1){
							setSelected(board,player,row,col);
						}
					break;
					
					case 22: //player 2 has a king on this square
						if(player==2){
							setSelected(board,player,row,col);
						}
					break;
					
					case 23: //player 1 has a selected king on this square
						if(player==1){
							Deselect(board);
						}
					break;
					
					case 24: //player 2 has a piece on this square
						if(player==2){
							Deselect(board);
						}
					break;
					
					case 33:
					case 31: //player 1 king can legally move here
						if(player==1){
							moveSelected(board,row,col);
						}
					break;
					
					case 34:
					case 32: //player 2 king can legally move here
						if(player==2){
							moveSelected(board,row,col);
						}
					break;
					
					case 100: //custom setup
						//Handled by CustomSetup()
					break;
					}	
					
				}
			}
		}
		
		
		private void displaySetupMessage(){
			if(setup==0){ //message to be displayed if user chooses a regular setup
				message = "Start Game";			
			}
			else if(setup==1){ //message to be displayed if user chooses a custom setup
				message = "Setup 12 black pieces and then 12 red pieces. Double click for King.";
			}
		}
		
		
		private void CustomSetup(){
			game.timer().stop();
		if(board[row][col]!=-1){
			if(player==2) placeBlack();
			else if(player==1) placeRed();
			if(blacks==0&&reds==0) {
				setupCompleteClicked();	
				game.timer().start();
			}
		}	
		else message="Illegal Placement. Place on brown squares only.";
		
		}
		
		private void placeBlack(){
			if(board[row][col]==1) board[row][col]=21;
			if(blacks>0)
				if(board[row][col]==100){
					blacks=blacks-1;
					if(blacks==0){
						player=2;
						message="Pieces remaining for Player 2: " + reds;
					}
					else message="Pieces remaining for Player 1: " + blacks;
					board[row][col]=1;
				}
			if(blacks==0) player=player%2+1;
		}	
		private void placeRed(){
			if(board[row][col]==2) board[row][col]=22;
			if(reds>0)
				if(board[row][col]==100){
				reds=reds-1;
				if(reds==0){
					player=1;
					message="Start Game";
				}
				else message="Pieces remaining for Player 2: " + reds;
				board[row][col]=2;		
			}	
		}
		
		
		private void pause(int t){
			try {
				Thread.sleep(t);
			} catch (InterruptedException e) {
				
			}
		}

		//Assignment 1 Ends	
		
		private void AIComment(){
			int n = getNumPieces(board,singlePlayer);
			int x = getNumPieces(board,computerPlayer);
			Random random = new Random();
			int r = random.nextInt(30);
			switch(r){
			case 0:
				break;
				
			case 1:
				if(n+3<x) message="Are you sure you know what you're doing?";
				else if(n-3>x) message="I can still turn this thing around.";
				else if(n+1==x) message="I like this game.";
				else if(n-1==x) message="I'm not sure I like this game.";
				else if(n==x) message="Your move!";
				break;
				
			case 2:
				if(n+3<x) message="I've got you now!";
				else if(n-3>x) message="I think I made a mistake :(";
				else if(n+1==x) message="Take that!";
				else if(n-1==x) message="Well well well";
				else if(n==x) message="So far so good.";
				break;
				
			case 3:
				if(n+3<x) message="I've got a good feeling about this one!";
				else if(n-3>x) message="I've got a bad feeling about this one.";
				else if(n==x) message="Hit me with your best shot. Fire away.";
				break;
				
			case 4:
				if(n+3<x) message="You do know that you're losing, right?";
				else if(n-3>x) message="I think I should take up a different hobby.";
				else if(n+1==x) message="I like this game.";
				else if(n-1==x) message="You're only winning by one, don't get too excited.";
				else if(n==x) message="Go go go! You're too slow!";
				else if(x==1 && n>1) message="I think I'm screwed.";
				break;
				
			case 5:
				if(n+3<x) message="Are you sure you know what you're doing?";
				else if(n-3>x) message="I can still turn this thing around.";
				else if(n+1==x) message="Technically I'm winning.";
				else if(n-1==x) message="You're only technically winning.";
				else if(n==x) message="Your move!";
				else if(x==1 && n>1) message="I hate this game.";
				break;
				
			case 6:
				if(n+3<x) message="Maybe after the game I should show you how to play properly.";
				else if(n-3>x) message="You need to spend more time outside.";
				else if(n+1==x) message="Muahaha!";
				else if(n-1==x) message="It's still pretty close.";
				else if(n==x) message="I wonder why they call it Checkers.";
				break;
				
			case 7:
				if(n+3<x) message="Maybe you should stick to an easy game.";
				else if(n-3>x) message="Maybe I should stick to Tic-Tac-Toe.";
				else if(n+1==x) message="You sort of walked into this one.";
				else if(n-1==x) message="It's still anyone's game (probably mine).";
				else if(n==x) message="ur move playa";
				break;
				
			case 8:
				if(n+3<x) message="Maybe you should stick to Tic-Tac-Toe.";
				else if(n-3>x) message="I thought we were friends.";
				else if(n+1==x) message="Which of your " + n + " pieces will you throw away next?";
				else if(n-1==x) message="Hmm, gotta think this through more carefully.";
				else if(n==x) message="Hmm";
				break;
				
			case 9:
				if(n+3<x) message="I'm starting to think you aren't even trying.";
				else if(n-3>x) message="Have pity on me, I don't even have arms!";
				else if(n+1==x) message="Did you know that tomoto paste isn't a glue subsitute?";
				else if(n-1==x) message="Before I got this job I was a used car salesmen in Nebraska.";
				else if(n==x) message="Your turn Bucko";
				break;
				
			case 10:
				if(n+3<x) message="I rock at this game.";
				else if(n-3>x) message="If only you were this good at playing real life.";
				else if(n==x) message="Did you know the British call this Draughts? I thought that was wild.";
				else if(n+1==x) message="You're move, make it count!";
				else if(n-1==x) message="You're doing quite well for someone so ugly.";
				else if(x==1 && n>1) message="I swear you're cheating.";
				break;
				
			case 11:
				if(n+3<x) message="I think I've got this one in the bag.";
				else if(n-3>x) message="Have your friends ever told you that you're not fun?";
				else if(n+1==x) message="Checkers is fun, isn't it?";
				else if(n-1==x) message="You're doing alright I guess.";
				else if(n==x) message="You have " + n + " pieces. I have " + x + " pieces. We have so much in common.";
				break;
				
			case 12:
				if(n+3<x) message="I think you should defrag me after this.";
				else if(n-3>x) message="If you gave me better hardward this wouldn't be happening.";
				else if(n+1==x) message="Muahahaha";
				else if(n-1==x) message="I really need to even things up.";
				else if(n==x) message="I flew a kite last weekend.";
				break;
				
			case 13:
				if(n+3<x) message="Don't feel bad, I'm basically a natural at stuff like this.";
				else if(n-3>x) message="It's not over till your mother sings.";
				else if(n+1==x) message="If I was a fruit I think I'd be a tomato.";
				else if(n-1==x) message="You're move, I suppose.";
				else if(n==x) message="Let's see...";
				else if(x==1 && n>1) message="I want a rematch.";
				break;
				
			case 14:
				if(n+3<x) message="You sort of set yourself up for this.";
				else if(n-3>x) message="I'm sad about this game.";
				else if(n+1==x) message="Don't feel bad, I'm only beating you by a little.";
				else if(n-1==x) message="You've got one more piece than me, big deal!";
				else if(n==x) message="What will you make of that?";
				else if(x==1 && n>1) message="Well, I think it's fair to say I'm toast.";
				break;
				
			case 15:
				if(n+3<x) message="Some people just aren't good at checkers, it's no big deal.";
				else if(n-3>x) message="You're definitely cheating!";
				else if(n+1==x) message="(='.'=) <-- this is what you look like right now.";
				else if(n-1==x) message="You're actually not too bad at this.";
				else if(n==x) message="So far you've proven a worthy opponent. Namaste!";
				else if(x==1 && n>1) message="I'm not sure where I went wrong...";
				break;
				
			case 16:
				if(n+3<x) message="I bet you wish you were me right now.";
				else if(n-3>x) message="Can we switch pieces?";
				else if(n+1==x) message="I think you'd make a good owl.";
				else if(n-1==x) message="Let's get this party started.";
				else if(n==x) message="64 squares but only half are used. What a waste.";
				else if(x==1 && n>1) message="Oh no.";
				break;
				
			case 17:
				if(n+3<x) message="You should upgrade my RAM after I beat you.";
				else if(n-3>x) message="You should upgrade your CPU, then this would be more fair!";
				else if(n+1==x) message="I wonder how old this game is.";
				else if(n-1==x) message="What now Bucko?";
				else if(n==x) message="Can I call you Bucko?";
				else if(x==1 && n>1) message="This sucks.";
				break;
				
			case 18:
				if(n+3<x) message="I'm sure we can find a game that's more your speed.";
				else if(n-3>x) message="Quick, let's switch pieces! Darn, I thought that would work.";
				else if(n+1==x) message="Can you believe that tomatoes aren't vegetables?";
				else if(n-1==x) message="I'm not doing as well as usual.";
				else if(n==x) message="I'm not sure where this is going.";
				else if(x==1 && n>1) message="Quick! Close the game! No? Oh well, it was worth a shot.";
				break;
				
			case 19:
				if(n+3<x) message="I love winning.";
				else if(n-3>x) message="Things aren't looking so great for me.";
				else if(n+1==x){
					if(gameSound)
						message="Beep! I love that sound.";
					else
						message="Why'd you turn off the sound :(";
				}
				else if(n-1==x) message="You're move!";
				else if(n==x) message="Ain't nobody got time for this!";
				else if(x==1 && n>1) message="I think I have a headache.";
				break;
				
			case 20:
				if(n+3<x) message="Today is just not your day.";
				else if(n-3>x) message="Today is just not my day.";
				else if(n+1==x) message="One small step for the checkers piece.";
				else if(n-1==x) message="You look like a horse when you play checkers.";
				else if(n==x) message="Pretty chill game if you ask me.";
				else if(x==1 && n>1) message="I definitely screwed up somewhere.";
				break;
				
			case 22:
				if(n+3<x) message="I'm unstoppable!";
				else if(n-3>x) message="I have a feeling I'm doomed.";
				else if(n+1==x) message="I wonder what it would be like to be a checkers piece.";
				else if(n-1==x) message="What if we used mouse droppings instead of checkers?";
				else if(n==x) message="Do you ever wonder about what your doing with your life?";
				else if(x==1 && n>1) message="I think I counted something wrong.";
				break;
				
			case 23:
				if(n+3<x) message="If you resign now, I won't be mad.";
				else if(n-3>x) message="I definitely need to rethink my strategy...";
				else if(n+1==x) message="Checkers is as checkers does.";
				else if(n-1==x) message="Don't get too excited, I'm only one piece down from you.";
				else if(n==x) message="I like it when we play Checkers like this.";
				else if(x==1 && n>1) message="I hate you.";
				break;
				
			case 24:
				if(n+3<x) message="If you feel like you're going to cry just resign.";
				else if(n-3>x) message="Sometimes you're not very nice.";
				else if(n+1==x) message="So many choices, so little you.";
				else if(n-1==x) message="One move at a time.";
				else if(n==x) message="Sometimes I wish I was a train.";
				else if(x==1 && n>1) message="Quick, press resign! Well it was worth a shot.";
				break;
				
			case 25:
				if(n+3<x) message="You're like the little engine that couldn't.";
				else if(n-3>x) message="I'm like the little engine that can't.";
				else if(n+1==x) message="I think I made a fine move just now.";
				else if(n-1==x) message="I'm coming around the mountain.";
				else if(n==x) message="Checkers all day e'ryday.";
				else if(x==1 && n>1) message="You are only going to win because I'm getting over a virus.";
				break;
				
			case 26:
				if(n+3<x) message="I'm a boss.";
				else if(n-3>x) message="Who taught you how to checkers?";
				else if(n+1==x) message="Advantage me. Think carefully Bucko.";
				else if(n-1==x) message="Your advantage. I need to play smarter.";
				else if(n==x) message="Your move.";
				else if(x==1 && n>1) message="You've made me sad this day.";
				break;
				
			case 27:
				if(n+3<x) message="I feel like you're trying to lose.";
				else if(n-3>x) message="I don't want to lose!";
				else if(n+1==x) message="Move that funky checkers piece Bucko Boy.";
				else if(n-1==x) message="Put your checkers where your mouth is!";
				else if(n==x) message="A dollar a day keeps the checkers away.";
				else if(x==1 && n>1) message="If you take my last piece I die. Alright I was bluffing!";
				break;
				
			case 28:
				if(n+3<x) message="Some were born to sing the blues (you).";
				else if(n-3>x) message="Some were born to sing the blues (me)";
				else if(n+1==x) message="Checkers is a gentlemen's sport. Why are you playing?";
				else if(n-1==x) message="I don't like where this is headed.";
				else if(n==x) message="Close but no checkers.";
				else if(x==1 && n>1) message="I'm caught between a rock and a hard checkers.";
				break;
				
			case 29:
				if(n+3<x) message="I'm sure you're good at other things.";
				else if(n-3>x) message="Quit beating around the checkers.";
				else if(n+1==x) message="Checkers is fun.";
				else if(n-1==x) message="I can't believe I'm losing to you.";
				else if(n==x) message="Never look before you leap.";
				else if(x==1 && n>1) message="Can't we just call it a draw?";
				break;
				
			case 30:
				if(n+3<x) message="Let's cut to the checkers, I'm going to win.";
				else if(n-3>x) message="You play this game too much.";
				else if(n+1==x) message="Your fingers look like sausages to me.";
				else if(n-1==x) message="You're only ahead by one piece. I can do this.";
				else if(n==x) message="I'd yawn if I had a mouth.";
				else if(x==1 && n>1) message="Wait, stop the game, I think I'm having a heartattack!";
				break;
				
			default:
				break;
			}
			
			
			
		}
	}