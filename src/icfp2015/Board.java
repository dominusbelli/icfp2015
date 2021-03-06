package icfp2015;

public class Board {
	
	//Board Dimensional Constraints
	int width; //number of columns
	int hieght; //number of rows
	int middle; //middle column of the board
	
	public Hexagon[][] B;
	
	/**
	 * Board Constructor
	 * @param width 
	 * 		Number of Columns in the Board
	 * @param height 
	 * 		Number of Rows in the Board
	 * @param col
	 * 		array containing all the col components of the filled hexagons
	 * @param row
	 * 		array containing all the row components of the filled hexagons
	 */
	public Board(int width , int height, int index, int[] col , int[] row){
		this.B = new Hexagon[height][width];
		for(int i = 0 ; i < height; i++){
			for(int j = 0; j < width ; j++){
				this.B[i][j] = new Hexagon(i , j , false, false);
			}
		}
		middle = ((width / 2));
		
		if(false == generate(col, row)){
			System.out.println("Game failed to start.");
		}
	}
	
	
	/**
	 * This functions will attempt to populate the game board 
	 * with the initial filled hexagons
	 * @param col
	 * 		array containing all the col components of the filled hexagons
	 * @param row
	 * 		array containing all the row components of the filled hexagons
	 * @return
	 * 		TRUE -- if the board was able to generate
	 * 		FALSE -- if the board failed to generate
	 */
	public boolean generate(int[] col , int[] row){
		if(col.length != row.length){
			System.out.println("Mismatch: Input arrays are of different lengths");
			return false;
		}
		for(int i = 0; i < col.length; i++){
			B[row[i]][col[i]].isFull = true;
		}
		System.out.println("Initial Board: Generate Successful");
		return true;
	}
	
	/**
	 * Will Check if the bottom row is filled 
	 * @return
	 * 		TRUE -- If bottom row is filled
	 * 		FALSE -- IF bottom row is not filled
	 */	
	public boolean isBottomRowFull(){
		for(int i = 0; i < this.width; i++){
			if((B[this.hieght - 1][i].isFull) == false){
				return false;
			}
		}
		return true;
	}
	
	public Unit centerUnit(Unit piece){
		Hexagon[] thex = piece.u;
		int pivotindex = -1;
		for(int i = 0; i < thex.length ; i++){
			if(thex[i].isPivot == true){
				pivotindex = i;
				break;
			}
		}
		System.out.println("The pivot index is : " + pivotindex);
		System.out.println("The pivot is at : " + thex[pivotindex].x);
		System.out.println("The middle of the board is:  " +  middle);
		
		boolean badmotion = false;
		if(thex[pivotindex].x < middle){
			System.out.println("The pivot is less then the middle");
			int iter = 0;
			while((thex[pivotindex].x < middle)){
				for(int i = 0; i < thex.length ; i++){
					thex[i].x++;
				}
				for(int i = 0; i < B.length ; i++){
					for(int j = 0; j < B[i].length; j++){
						for(int k = 0 ; k < thex.length ; k++){
							if(B[i][j].isFull == true && B[i][j].x == thex[k].x && B[i][j].y == thex[k].y){
								badmotion = true;
								break;
							}
						}
						if(badmotion == true){
							break;
						}
					}
					if(badmotion == true){
						break;
					}
				}
				
				if(badmotion == true){
					for(int i = 0; i < thex.length ; i++){
						thex[i].x--;
					}
				}
				
				/*for(int i = 0; i < thex.length ; i++ ){
					
					System.out.println( "id = " + i + " : " + "Row , Column : " + thex[i].x  + " , " + thex[i].y);
				}*/
				
				if(badmotion == true){
					break;
				}
				
				iter++;
			}
		}
		
		
		
		return new Unit(thex);
	}
	
	/**
	 * Will insert new unit into the board from the source
	 * @param piece
	 * @return
	 * 		Centered piece if possible
	 */
	public Unit genNewUnit(Unit piece){
		//retrieve hexagons
		Hexagon[] t_hex = piece.u;
		//Find upper hexagons
		int sum = 0;
		int count = 0;
		for(int i = 0 ; i < t_hex.length ; i++){
			if(t_hex[i].y == 0){
				sum = sum + t_hex[i].x;
				count++;
			}
		}
		int avg = (sum / count);
		char mov = ' ';
		if(avg == middle){
			return piece;
		}
		if(avg < middle){
			mov = 'r';
		}else if(avg > middle){
			mov = 'l';
		}
		//Find deepest index
		int deep = 0;
		for(int i = 0; i < t_hex.length; i++){
			if(t_hex[i].y > deep){
				deep = t_hex[i].y;
			}
		}
		//Find key index
		int pivot_index = -1;
		for(int i = 0; i < t_hex.length; i++){
			if(t_hex[i].isPivot == true){
				pivot_index = i;
				break;
			}
		}
			if(avg != middle){
				if(mov == 'r'){
					boolean failed = false;
					int moves = 0;
					while(t_hex[pivot_index].x <= middle){
						for(int i = 0; i <= deep ; i++){
							if(t_hex[i].x < (width - 1)){
								t_hex[i].x++;
							}
						}
						moves++;
						for(int f = 0 ; f < t_hex.length ; f++){
							if(t_hex[f].y >= width){
								failed = true;
							}
						}
						if(failed == true){
							for(int i = 0; i <= deep ; i++){
								if(t_hex[i].x < (width - 1)){
									t_hex[i].x--;
								}
							}
						}
					}

				}
				if(mov == 'l'){
					boolean failed = false;
					int moves = 0;
					while(t_hex[pivot_index].x >= middle){
						for(int i = 0; i <= deep ; i++){
							if( t_hex[i].x > (0)){
								t_hex[i].x--;
							}
						}
						moves++;
						for(int f = 0 ; f < t_hex.length ; f++){
							if(t_hex[f].y < 0){
								failed = true;
							}
						}
						if(failed == true){
							for(int i = 0; i <= deep ; i++){
								if(t_hex[i].x < (0)){
									t_hex[i].x++;
								}
							}
						}
					}
				}
			}
			Unit finalLoc = new Unit(t_hex);
			return finalLoc;
	}
	/**
	 * Will check if game is over by determining if any more 
	 * units are left to place or if unit cannot be centered on the board
	 * @param piece
	 * 		unit attempting to be placed
	 * @return
	 * 		TRUE - if game is over
	 * 		FALSE - if game is not over
	 */
	//TODO: Check queue for any more pieces
	public boolean isGameOver(Unit piece){
		//check if any new units left
		
		//check if new unit can be placed
		piece = genNewUnit(piece);
		boolean flag = false;
		for(int i = 0; i < piece.u.length ; i++){
			if(B[piece.u[i].y][piece.u[i].x].isFull == true){
				flag = false;
				continue;
			}
			flag = true;
		}
		//Lock in the piece and set hexagons to full
		return flag;
	}
	
	
		
}
