package icfp2015;

public class Board {
	
	//Board Dimensional Constraints
	int width; //number of columns
	int hieght; //number of rows
	
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
			B[row[i]][col[i]].full = true;
		}
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
			if((B[this.hieght - 1][i].full) == false){
				return false;
			}
		}
		return true;
	}
	
	public boolean genNewUnit(){
		//Get Coordinates
		//Check if unit can fit on the board
		//Attempt to Center the Unit in the avail area
		return true;
	}
	
}
