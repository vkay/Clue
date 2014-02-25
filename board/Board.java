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
import java.util.TreeSet;

import board.RoomCell.DoorDirection;

public class Board {
	
	private ArrayList<BoardCell> cells;
	private Map<Character, String> rooms;
	private int numRows;
	private int numColumns;
	private String legend="legend.txt"; //= "ClueLegend.txt";
	private String board="clueLayout1.csv"; //= "ClueLayoutBadColumns.csv";//";
	
	private Map<Integer, ArrayList<Integer>> adjMtx;
	private boolean[] visited;
	private Set<BoardCell> targets = new TreeSet<BoardCell>();
	
	
	
	public Board() throws FileNotFoundException, BadConfigFormatException {
		rooms = new HashMap<Character, String>();
		cells = new ArrayList<BoardCell>();
		adjMtx = new HashMap<Integer, ArrayList<Integer>>();
		loadConfigFiles();
		visited = new boolean[numRows * numColumns];
		for (int i = 0; i < visited.length; i++) {
			adjMtx.put(i, new ArrayList<Integer>());
			visited[i] = false;
		}
	}
	
	public Board(String board, String legend) throws FileNotFoundException, BadConfigFormatException {
		this.legend = legend;
		this.board = board;
		rooms = new HashMap<Character, String>();
		cells = new ArrayList<BoardCell>();
		loadConfigFiles();
		visited = new boolean[numRows * numColumns];
		for (int i = 0; i < visited.length; i++) {
			adjMtx.put(i, new ArrayList<Integer>());
			visited[i] = false;
		}
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
		Board board = new Board();
		/*board.loadRoomConfig();
		board.loadBoardConfig();*/
	}

	/*public IntBoard() {
		adjMtx = new HashMap<Integer, ArrayList<Integer>>();
		visited = new boolean[16];
		for (int i = 0; i < 16; i++) {
			adjMtx.put(i, new ArrayList<Integer>());
			visited[i] = false;
		}
	}*/
	
	public void calcAdjacencies() {
		Set<Integer> keySet = adjMtx.keySet();
		for (Integer key : keySet) {
			if (cells.get(key).isRoom() && !(cells.get(key).isDoorway())) {
				
			} else if (cells.get(key).isRoom() && (cells.get(key).isDoorway())) {
				if(((RoomCell) cells.get(key)).getDoorDirection() == DoorDirection.UP) {
					adjMtx.get(key).add(key - numColumns);
				} else if(((RoomCell) cells.get(key)).getDoorDirection() == DoorDirection.DOWN) {
					adjMtx.get(key).add(key + numColumns);
				} else if(((RoomCell) cells.get(key)).getDoorDirection() == DoorDirection.LEFT) {
					adjMtx.get(key).add(key - 1);
				} else if(((RoomCell) cells.get(key)).getDoorDirection() == DoorDirection.RIGHT) {
					adjMtx.get(key).add(key + 1);
				} 
			} else { 
				if(key >= numColumns) {
					if(cells.get(key - numColumns).isRoom() && (((RoomCell) cells.get(key - numColumns)).getDoorDirection() == DoorDirection.DOWN)) {
						adjMtx.get(key).add(key - numColumns);
					} else if (cells.get(key - numColumns).isWalkway()) {
						adjMtx.get(key).add(key - numColumns);
					} 
				} 
				if (key < (keySet.size() - numColumns)) {
					if(cells.get(key + numColumns).isRoom() && (((RoomCell) cells.get(key + numColumns)).getDoorDirection() == DoorDirection.UP)) {
						adjMtx.get(key).add(key + numColumns);
					} else if (cells.get(key + numColumns).isWalkway()) {
						adjMtx.get(key).add(key + numColumns);
					}
				} 
				if (key%numColumns != 0) {
					if(cells.get(key - 1).isRoom() && (((RoomCell) cells.get(key - 1)).getDoorDirection() == DoorDirection.RIGHT)) {
						adjMtx.get(key).add(key - 1);
					} else if (cells.get(key - 1).isWalkway()) {
						adjMtx.get(key).add(key - 1);
					} 
				} 
				if((key+1) %numColumns != 0 ){
					if(cells.get(key + 1).isRoom() && (((RoomCell) cells.get(key + 1)).getDoorDirection() == DoorDirection.LEFT)) {
						adjMtx.get(key).add(key + 1);
					} else if (cells.get(key + 1).isWalkway()) {
						adjMtx.get(key).add(key + 1);
					} 
				}
			}
		}
	}
	
	public void startTargets(int location, int steps) {
		ArrayList<Integer> adjList = new ArrayList<Integer>();
		visited[location] = true;
		for (Integer i: adjMtx.get(location)) {
			if (!visited[i]) {
				adjList.add(i);
			}
		} 
		for (Integer i: adjList) {
			visited[i] = true;
			if (steps == 1) {
				//targets.add(i);
			} else {
				startTargets(i, steps - 1);
			}
			visited[i] = false;
		}
		visited[location] = false;
	}
	
	/*public Set<Integer> getTargets() {
		
		return targets;
	}*/
	
	public ArrayList<Integer> getAdjList(int location) {
		return adjMtx.get(location);
	}
	
	/*public int calcIndex(int row, int column) {
		return row * 4 + column;
	}*/

	/*public ArrayList<Integer> getAdjList(int calcIndex) {
		return new ArrayList<Integer>();
	}*/

	public void calcTargets(int i, int j, int k) {
		
	}

	public Set<BoardCell> getTargets() {
		return new HashSet<BoardCell>();
		//return targets;
	}
	
}
