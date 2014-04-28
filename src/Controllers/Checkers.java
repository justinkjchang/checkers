package Controllers;
import java.util.ArrayList;
import java.util.Random;


//Refer to pages 9-10 in the design document

public abstract class Checkers {
	
	public boolean anyLegalJumps(int[][] board, int player) { //Jack
		boolean anyJump = false;		
		int sRow = getSelected(board)[0];
		int sCol = getSelected(board)[1];
		Deselect(board);	
		for(int row = 0; row < 8; row++)
			for(int col = 0; col < 8; col++)
				if(board[row][col] == player || board[row][col] == (player + 20))		
					if(canJump(board,player,row,col)) anyJump = true;
		
		if(sRow < 8){
			board[sRow][sCol]+=2;
			if(!anyJump) setLegalMovesFor(board,player,sRow,sCol);
			setLegalJumpsFor(board,player,sRow,sCol);
		}
		return anyJump;
	}
	public boolean canJump(int[][] board, int player, int row, int col){
		Deselect(board);
		boolean canJump = false;
		board[row][col] = board[row][col] + 2;
		setLegalJumpsFor(board,player,row,col);
		for(int r = 0; r < 8; r++)
		for(int c = 0; c < 8; c++)
			if(board[r][c]==board[row][col]+10 || board[r][c]==board[row][col]+30)
				canJump=true;
		
		removeLegalJumpsFor(board,row,col);
		board[row][col]=board[row][col]-2;
		return canJump;
	}

	public void jumpMove(int[][] board, int row, int col) { //John	
		int sRow = getSelected(board)[0];
		int sCol = getSelected(board)[1];	
		int jRow = row;
		int jCol = col;		
		board[jRow][jCol]=board[sRow][sCol];	
		int ovRow = (sRow+jRow)/2;
		int ovCol = (sCol+jCol)/2;	
		board[sRow][sCol]=0;
		board[ovRow][ovCol]=0;
	}

	public void regularMove(int[][] board, int row, int col) { //Vincent

		int sRow = getSelected(board)[0];
		int sCol = getSelected(board)[1];
		
		int mRow = row;
		int mCol = col;
		
		board[mRow][mCol]=board[sRow][sCol];
		board[sRow][sCol]=0;

	}

	public void setLegalJumpsFor(int[][] board, int player, int row, int col){
       int playerPiece = player + 2; int playerKing = playerPiece + 20;
       int enemyPiece = player%2 + 1; int enemyKing = enemyPiece + 20;  
       if(player==1 || (player==2 && board[row][col]==playerKing))          
	       if((0<=row)&&(row<=5)) setDownJumps(board,player,row,col,enemyPiece,enemyKing); 
	                  
       if(player==2 || (player==1 && board[row][col]==playerKing))          
    	   if((2<=row)&&(row<=7)) setUpJumps(board,player,row,col,enemyPiece,enemyKing);           
       }
	private void setDownJumps(int[][]board, int player, int row, int col, int enemyPiece, int enemyKing){
		   //down right jump
	       if(!((col == 6)||(col == 7)))
	       if((board[row+1][col+1] == enemyPiece)||(board[row+1][col+1] == enemyKing))
		   if (board[row+2][col+2] == 0){
		   removeLegalMovesFor(board,row,col);
		   board[row+2][col+2] = board[row][col]+10;
		   }
	                       
	       //down left jump
	       if (!((col == 0)||(col == 1)))
	       if((board[row+1][col-1] == enemyPiece)||(board[row+1][col-1] == enemyKing))
		   if (board[row+2][col-2] == 0){
		   removeLegalMovesFor(board,row,col);
		   board[row+2][col-2] = board[row][col]+10;
		   }   
}
	private void setUpJumps(int[][]board, int player, int row, int col, int enemyPiece, int enemyKing){
		   //up right jump
	       if(!((col == 6)||(col == 7)))	   
	       if((board[row-1][col+1] == enemyPiece)||(board[row-1][col+1] == enemyKing))
	       if (board[row-2][col+2] == 0){
	       removeLegalMovesFor(board,row,col);
	       board[row-2][col+2] = board[row][col]+10;
	       }                             
	                       
	       //up left jump
	       if (!((col == 0)||(col == 1)))
	       if((board[row-1][col-1] == enemyPiece)||(board[row-1][col-1] == enemyKing))
	       if (board[row-2][col-2] == 0){
	       removeLegalMovesFor(board,row,col);
	       board[row-2][col-2] = board[row][col]+10;
	       }                
}
	
	public void setSelected(int[][] board, int player, int row, int col){
		Deselect(board);
		board[row][col]=board[row][col]+2;
		setLegalMovesFor(board,player,row,col);
		setLegalJumpsFor(board,player,row, col);
		if(anyLegalJumps(board, player)){
			removeLegalMovesFor(board,row,col);
		}
	}
	
	
	public void Deselect(int[][] board){
		if(isSelected(board)){
		int row = getSelected(board)[0];
		int col = getSelected(board)[1];
		board[row][col]=board[row][col]-2;
		removeLegalMovesFor(board,row,col);
		removeLegalJumpsFor(board,row,col);
		}
	}
	
	
	public void setLegalMovesFor(int[][] board, int player, int row, int col){
		boolean isKing=false;
		//Determines whether or not the given piece is a king
		if(board[row][col]==23||board[row][col]==24){
			isKing=true;
		}
		
		//down right
		if(col!=7 && row!=7 && board[row+1][col+1]==0 && (player==1 || isKing)){
			board[row+1][col+1]=board(board,getSelected(board))+8;
		}
		
		//down left
		if(col!=0 && row!=7 && board[row+1][col-1]==0 && (player==1 || isKing)){
			board[row+1][col-1]=board(board,getSelected(board))+8;
		}
			
		//up right
		if(col!=7 && row!=0 && board[row-1][col+1]==0 && (player==2 || isKing)){
			board[row-1][col+1]=board(board,getSelected(board))+8;
		}
		
		//up left
		if(col!=0 && row!=0 && board[row-1][col-1]==0 && (player==2 || isKing)){
			board[row-1][col-1]=board(board,getSelected(board))+8;
		}
	}
	
	
	public void removeLegalMovesFor(int[][] board, int row, int col){
		
		//down right
        if(((col!=7) && (row!=7)) && !(board[row+1][col+1]<=4 || (board[row+1][col+1]>=20 && board[row+1][col+1]<=24))){
            board[row+1][col+1]=0;
        }
        
        //down left
        if(((col!=0) && (row !=7)) && !(board[row+1][col-1]<=4 || (board[row+1][col-1]>=20 && board[row+1][col-1]<=24))){
            board[row+1][col-1]=0;
        }
        
        //up right 
        if(((col!=7) && (row!=0)) && !(board[row-1][col+1]<=4 || (board[row-1][col+1]>=20 && board[row-1][col+1]<=24))){
            board[row-1][col+1]=0;
        }
        
        //up left 
        if(((col!=0) && (row!=0)) && !(board[row-1][col-1]<=4 || (board[row-1][col-1]>=20 && board[row-1][col-1]<=24))){
            board[row-1][col-1]=0;
        }
	}
	
	
	public void removeLegalJumpsFor(int[][] board, int row, int col){
		//legalJumps
		
		//down right x 2
        if(((col!=6 && col!=7) && (row!=6 && row!=7)) && !(board[row+2][col+2]<=4 || (board[row+2][col+2]>=20 && board[row+2][col+2]<=24))){
            board[row+2][col+2]=0;
        }
        
        //down left x 2
        if(((col!=0 && col!=1) && (row!=6 && row !=7)) && !(board[row+2][col-2]<=4 || (board[row+2][col-2]>=20 && board[row+2][col-2]<=24))){
            board[row+2][col-2]=0;
        }
        
        //up right x 2
        if(((col!=6 && col!=7) && (row!=0 && row!=1)) && !(board[row-2][col+2]<=4 || (board[row-2][col+2]>=20 && board[row-2][col+2]<=24))){
            board[row-2][col+2]=0;
        }
        
        //up left x 2
        if(((col!=0 && col!=1) && (row!=0 && row!=1)) && !(board[row-2][col-2]<=4 || (board[row-2][col-2]>=20 && board[row-2][col-2]<=24))){
            board[row-2][col-2]=0;
        }
	}
	
	public boolean isSelected(int[][] board){
		boolean found=false;
		for(int i=0;i<8;i++)
			for(int j=0;j<8;j++)
				if((board[i][j]==3||board[i][j]==4)||(board[i][j]%20==3||board[i][j]%20==4)){
					found=true;
				}
		return found;
	}
	
	
	public int[] getSelected(int[][] board){
		int[] c = {9,9};
		for(int i=0;i<8;i++)
			for(int j=0;j<8;j++)
				if((board[i][j]==3||board[i][j]==4)||(board[i][j]==23||board[i][j]==24)){
					c[0]=i;
					c[1]=j;
				}
		return c;					
	}
	
	public int board(int[][] board, int[] coords){
		return board[coords[0]][coords[1]];
	}

	public boolean gameOver(int[][] board){
		int sRow = getSelected(board)[0];
		int sCol = getSelected(board)[1];
		int player=0;
		if(sRow < 8){
		if(board[sRow][sCol]%2==0) player=2;
		else player=1;
		}
		Deselect(board);
		
		
		boolean gameOver = true;
		boolean p1hasPiece = false;
		boolean p2hasPiece = false;
		for(int row=0; row<8; row++)
			for(int col=0; col<8; col++){
				if(board[row][col] == 1 || board[row][col]==21) p1hasPiece=true;
				if(board[row][col] == 2 || board[row][col]==22) p2hasPiece=true;
			}
		gameOver=!(p1hasPiece && p2hasPiece);
		
		if(sRow < 8){
			setSelected(board, player, sRow, sCol);
		}		
		
		return gameOver;
	}
	
	public ArrayList<int[][]> getMoves(int[][] board, int player){
		int[][] move = new int[2][2];
		ArrayList<int[][]> moves = new ArrayList<int[][]>();
		for(int row=0; row<8; row++)
			for(int col=0; col<8; col++){
				if(isPiece(board,player,row,col)){
					setSelected(board,player,row,col);
					if(hasLegals(board,row,col)){
						move[0][0]=row;
						move[0][1]=col;
						if(row>0 && col>0 && isLegal(board[row-1][col-1])){
							move[1][0] = row-1;
							move[1][1] = col-1;
							moves.add(copy(move));
						}
						if(row>0 && col<7 && isLegal(board[row-1][col+1])){
							move[1][0] = row-1;
							move[1][1] = col+1;
							moves.add(copy(move));
						}
						if(row<7 && col>0 && isLegal(board[row+1][col-1])){
							move[1][0] = row+1;
							move[1][1] = col-1;
							moves.add(copy(move));
						}
						if(row<7 && col<7 && isLegal(board[row+1][col+1])){
							move[1][0] = row+1;
							move[1][1] = col+1;
							moves.add(copy(move));
						}
						if(row>1 && col>1 && isLegal(board[row-2][col-2])){
							move[1][0] = row-2;
							move[1][1] = col-2;
							moves.add(copy(move));
						}
						if(row>1 && col<6 && isLegal(board[row-2][col+2])){
							move[1][0] = row-2;
							move[1][1] = col+2;
							moves.add(copy(move));
						}
						if(row<6 && col>1 && isLegal(board[row+2][col-2])){
							move[1][0] = row+2;
							move[1][1] = col-2;
							moves.add(copy(move));
						}
						if(row<6 && col<6 && isLegal(board[row+2][col+2])){
							move[1][0] = row+2;
							move[1][1] = col+2;
							moves.add(copy(move));
						}
					}
					Deselect(board);
				}
			}
		
		//for(int[][] x : moves){
		//	System.out.printf("%d,%d -> %d,%d\n", x[0][0],x[0][1],x[1][0],x[1][1]);
		//}
		return moves;
	}
	
	public int[][] chooseMove(int[][] board, int player, int difficulty){
		if(getNumPieces(board,player)==1 || getNumPieces(board,player)==10) return chooseRandom(board,player);
		boolean suicidal = false;
		if(difficulty<-1){
			difficulty=1; //hard mode, but picking the worst moves instead of the best ones
			suicidal = true;
		}
		else if(difficulty==-1){
			return chooseRandom(board,player);
		}
		int intellegence;		
		ArrayList<int[][]> moves = getMoves(board, player);
		int n = moves.size();
		
		if(n>=10) intellegence = 2;
		else if(n>=8) intellegence = 2 + 4*difficulty;
		else if(n>=2) intellegence = 2 + 6*difficulty;
		else intellegence = 2;
		System.out.println(intellegence);
		int[][] chosenMove = new int[2][2];
		ArrayList<Double> moveRatios = new ArrayList<Double>();
		double ratio;
		int p;
		int[][] boardCopy = new int[8][8];
		copy(board,boardCopy);
		for(int[][] move : moves){
			int sRow = move[0][0];
			int sCol = move[0][1];
			int fRow = move[1][0];
			int fCol = move[1][1];
			
			p = move(board, player, sRow, sCol, fRow, fCol);
			ratio = getRatio(board, player, p , intellegence);
			moveRatios.add(ratio);
			copy(boardCopy,board);
		}
		int[][] m;
		for(int i = 0; i<moves.size(); i++){
			m = moves.get(i);
			System.out.printf("Player %d: %d,%d -> %d,%d : %f\n", player, m[0][0], m[0][1], m[1][0], m[1][1], moveRatios.get(i));
		}
		System.out.println();
		if(moveRatios.size()>=1){
			if(!suicidal)chosenMove = moves.get(maxIndex(moveRatios));
			else chosenMove = moves.get(minIndex(moveRatios));
		}
			
		
		return chosenMove;
	}
	
	private int minIndex(ArrayList<Double> list) {
		int minIndex = 0;
		double minValue = 100;
		for(int i=0; i<list.size(); i++){
			if(list.get(i)<minValue){
				minIndex=i;
				minValue = list.get(i);
			}
		}
		return minIndex;
	}
	private int[][] chooseRandom(int[][] board, int player) {
		ArrayList<int[][]> moves = getMoves(board,player);
		int[][] chosenMove = new int[2][2];
		Random random = new Random();
		if(moves!=null && (ratio(board,player)<=1 || getNumPieces(board,player)<=3) && moves.size()>=1){
			int randomMove = random.nextInt(moves.size());
			chosenMove=moves.get(randomMove);	
		}	
		else if(moves!=null && moves.size()>=1) {
			chosenMove=moves.get(0);
		}
		return chosenMove;
	}
	private int maxIndex(ArrayList<Double> list){
		int maxIndex = 0;
		double maxValue = 0;
		for(int i=0; i<list.size(); i++){
			if(list.get(i)>=maxValue){
				maxIndex=i;
				maxValue = list.get(i);
			}
		}
		return maxIndex;
	}
	
	public int move(int[][] board, int player, int startRow, int startCol, int finishRow, int finishCol){
		setSelected(board, player, startRow,startCol);
		return moveSelected(board, player, finishRow, finishCol);
	}
	
	private int moveSelected(int[][] board, int player, int row, int col){	
		boolean madeJump = false;
		int sRow = getSelected(board)[0];
		int sCol = getSelected(board)[1];
		if(board[row][col]==13 || board[row][col]==14){
			jumpMove(board,row, col);
			madeJump = true;
		}
		else if(board[row][col]==33 || board[row][col]==34){
			jumpMove(board,row, col);
			madeJump = true;
		}
		removeLegalJumpsFor(board,sRow,sCol);
		

		if(board[row][col]==11 || board[row][col]==12) regularMove(board,row, col);
		else if(board[row][col]==31 || board[row][col]==32) regularMove(board,row, col);
		removeLegalMovesFor(board,sRow,sCol);
		
		boolean anyJumps = canJump(board,player,row,col);
		
		if(player==1 && row==7){
			board[row][col]=21;
		}
		else if(player==2 && row==0){
			board[row][col]=22;
		}
		
		if(!anyJumps || madeJump == false){
			if(player==1) player=2;
			else player=1;
		}
		Deselect(board);
		removeLegalMovesFor(board,sRow,sCol);
		removeLegalJumpsFor(board,sRow,sCol);
		return player;
		}
	
	public double ratio(int[][] board, int player){
		double ratio = 0;
		int enemyPiece = player%2 + 1;
		double pla = getNumPieces(board,player);
		int enemy = getNumPieces(board,enemyPiece);
		ratio = pla/enemy;
		return ratio;
	}
	
	private double getRatio(int[][] board, int playerTurn, int player, int counter) {
		if(counter==0) return ratio(board, playerTurn);
		ArrayList<int[][]> moves = getMoves(board, player);
		int n = moves.size();
		if(counter>=4){
			if(n>=7) counter=counter-2;
		}
		
		if(counter>=6){
			if(n>=3) counter=counter-2;
		}
		
		int[][] boardCopy = new int[8][8];
		int p = player;
		copy(board,boardCopy);
		double avgRatio = 0;
		
		for(int[][] move : moves){
		int sRow = move[0][0];
		int sCol = move[0][1];
		int fRow = move[1][0];
		int fCol = move[1][1];
		
		p = move(board, p, sRow, sCol, fRow, fCol);

			avgRatio = avgRatio + getRatio(board, playerTurn, p, counter-1);	
			
		p = player;
		copy(boardCopy,board);
		}
		
		avgRatio = avgRatio/moves.size();
		
			
		
		return avgRatio;
	}
	
	public int getNumPieces(int[][] board, int player){
		int sum = 0;
		for(int row=0; row<8; row++)
		for(int col=0; col<8; col++){
			if(board[row][col]==player || board[row][col]==player+20) sum++;
		}
		return sum;
	}
	
	public int sumAllPieces(int[][] board){
		int sum = 0;
		sum = sum + getNumPieces(board, 1);
		sum = sum + getNumPieces(board, 2);
		return sum;
	}
	
	public int[][] copy(int[][] move){
		int n = move.length;
		int[][] copy = new int[n][n];
		for(int i=0; i<n; i++)
		for(int j=0; j<n; j++)
			copy[i][j]=move[i][j];
		return copy;
	}
	
	//Start AI methods
	
	public boolean hasLegals(int[][] board, int row, int col){
		boolean hasLegals = false;
		if(hasLegalJumps(board,row,col)) hasLegals=true;
		if(hasLegalMoves(board,row,col)) hasLegals=true;
		return hasLegals;
	}
	
	private boolean hasLegalMoves(int[][] board, int row, int col){
		boolean hasLegals = false;
		if(row>0 && col>0 && isLegal(board[row-1][col-1])) hasLegals=true;
		if(row>0 && col<7 && isLegal(board[row-1][col+1])) hasLegals=true;
		if(row<7 && col>0 && isLegal(board[row+1][col-1])) hasLegals=true;
		if(row<7 && col<7 && isLegal(board[row+1][col+1])) hasLegals=true;
		return hasLegals;
	}

	public boolean hasLegalJumps(int[][] board, int row, int col){
		boolean hasLegals = false;
		if(row>1 && col>1 && isLegal(board[row-2][col-2])) hasLegals=true;
		if(row>1 && col<6 && isLegal(board[row-2][col+2])) hasLegals=true;
		if(row<6 && col>1 && isLegal(board[row+2][col-2])) hasLegals=true;
		if(row<6 && col<6 && isLegal(board[row+2][col+2])) hasLegals=true;
		return hasLegals;
	}
	
	protected static boolean isLegal(int i) {
		return (i==11||i==12||i==13||i==14||i==31||i==32||i==33||i==34);	
	}

	public boolean isPiece(int[][] board, int player, int row, int col){
		return (player==1 && (board[row][col]==1 || board[row][col]==21) || player==2 && (board[row][col]==2 || board[row][col]==22));
	}
	
	public boolean isLegal(int[][] board, int row, int col){
		return ((board[row][col]==1 || board[row][col]==21) || (board[row][col]==2 || board[row][col]==22));
	}
	
	public void print(int[][] A){
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
	
	public void copy(int[][] board1, int[][] board2){
		for(int row=0; row<8; row++)
		for(int col=0; col<8; col++)
			board2[row][col]=board1[row][col];
	}
	
	public boolean areEqual(int[][] board1, int[][] board2){
		boolean areEqual = true;
		for(int row=0; row<8; row++)
		for(int col=0; col<8; col++)
			if(board1[row][col]!=board2[row][col]) areEqual = false;
		return areEqual;
	}
	//end AI methods
	
}
