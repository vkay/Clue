package board;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class IntBoard {
	
	private Map<Integer, ArrayList<Integer>> adjMtx;
	
	public IntBoard() {
		adjMtx = new HashMap<Integer, ArrayList<Integer>>();
		for (int i = 0; i < 16; i++) {
			adjMtx.put(i, null);
		}
	}
	
	public void calcAdjacencies() {
		Set<Integer> keySet = adjMtx.keySet();
		for (Integer key : keySet) {
			if(key >= 4) {
				adjMtx.get(key).add(key - 4);
			} if (key < 12) {
				adjMtx.get(key).add(key + 4);
			} if (key%4 != 0) {
				adjMtx.get(key).add(key - 1);
			} 
		}
	}
	
	public void startTargets(int rows, int columns, int steps) {
		
	}
	
	public Set<Integer> getTargets() {
		Set<Integer> targets = new HashSet<Integer>();
		return targets;
	}
	
	public ArrayList<Integer> getAdjList(int location) {
		ArrayList<Integer> adjList = new ArrayList<Integer>();
		
		return adjList;
	}
	
	public int calcIndex(int row, int column) {
		return row * 4 + column;
	}
	
}
