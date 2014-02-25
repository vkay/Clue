package board;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Board {
	
	private ArrayList<BoardCell> cells;
	private Map<Character, String> rooms;
	private int numRows;
	private int numColumns;
	private String legend="legend.txt"; //= "ClueLegend.txt";
	private String board="clueLayout1.csv"; //= "ClueLayoutBadColumns.csv";//";
	
	public Board() {
		rooms = new HashMap<Character, String>();
		cells = new ArrayList<BoardCell>();
	}
	
	public Board(String board, String legend) {
		this.legend = legend;
		this.board = board;
		rooms = new HashMap<Character, String>();
		cells = new ArrayList<BoardCell>();
	}
	
	public void loadConfigFiles() throws FileNotFoundException, BadConfigFormatException {
		loadRoomConfig();
		loadBoardConfig();
	}
	
	public int calcIndex(int row, int column) {
		return row*numColumns + column;
	}

	public ArrayList<BoardCell> getCells() {
		return cells;
	}

	public Map<Character, String> getRooms() {
		return rooms;
	}

	public int getNumRows() {
		return numRows;
	}

	public int getNumColumns() {
		return numColumns;
	}

	public RoomCell getRoomCellAt(int i, int j) {
		return (RoomCell) cells.get(calcIndex(i, j));
	}

	public BoardCell getCellAt(int calcIndex) {
		return cells.get(calcIndex);
	}

	//Load room legend file
	public void loadRoomConfig() throws FileNotFoundException, BadConfigFormatException {
		FileReader reader = new FileReader(legend);
		Scanner in = new Scanner(reader);
		while (in.hasNextLine()) {
			//try {
				String wholeLine = in.nextLine();
				String[] separate = wholeLine.split(", ");
				if (separate.length != 2) {
					throw new BadConfigFormatException("Inconsistent legend format.");
				}
				rooms.put(separate[0].charAt(0), separate[1]);
			//} catch (BadConfigFormatException e) {
			//	System.out.println(e.getMessage());
			//}
		}
		//Set<Character> keySet = rooms.keySet();
		//for (Character key : keySet)
			//System.out.println(key + ", " + rooms.get(key));
	}

	//Load room board file
	public void loadBoardConfig() throws FileNotFoundException, BadConfigFormatException {
		FileReader reader = new FileReader(board);
		Scanner in = new Scanner(reader);
		Set<Character> keySet = rooms.keySet();
		int countRow = 0;
		while (in.hasNextLine()) {
			String wholeLine = in.nextLine();
			String[] separate = wholeLine.split(",");
			//try{
				if (countRow == 0){
					 numColumns =separate.length;
				}
				if (numColumns != separate.length){
					throw new BadConfigFormatException("Inconsistent number of columns per row.");
				}
				for (String s : separate) {
					//try {
						boolean inKey = false;
						for (Character key : keySet){
							if( s.charAt(0) == key){
								inKey = true;
							}
						}
						if (inKey == false){
							throw new BadConfigFormatException("Incorrect room character.");
						}
						if (s.equals("W")) {
							cells.add(new WalkwayCell());
						//} else if (s.length() != 2) {
							//cells.add(new RoomCell('n'));
						} else {
							cells.add(new RoomCell(s));
						}	
					//} 
					
					/*catch (BadConfigFormatException e) {
						System.out.println(e.getMessage());
					}*/
				}
			//}catch(BadConfigFormatException e){
				//System.out.println(e.getMessage());
			//}
			countRow++;
		}
		numRows = countRow;
		
		//for (BoardCell cell : cells) {
			//System.out.println(cell + " " + count);
			//count++;
		//}
		//System.out.println(numRows+ " " + numColumns);
	}
	
	public static void main(String[] args) throws FileNotFoundException, BadConfigFormatException {
		/*Board board = new Board();
		board.loadRoomConfig();
		board.loadBoardConfig();*/
	}

	public void calcAdjacencies() {
		
	}

	public ArrayList<Integer> getAdjList(int calcIndex) {
		return new ArrayList<Integer>();
	}

	public void calcTargets(int i, int j, int k) {
		
	}

	public Set<BoardCell> getTargets() {
		return new HashSet<BoardCell>();
	}
	
}
