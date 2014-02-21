package board;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;


public class IntBoard {
	
	private Map<Integer, ArrayList<Integer>> adjMtx;
	private boolean[] visited;
	private Set<Integer> targets = new TreeSet<Integer>();
	
	public IntBoard() {
		adjMtx = new HashMap<Integer, ArrayList<Integer>>();
		visited = new boolean[16];
		for (int i = 0; i < 16; i++) {
			adjMtx.put(i, new ArrayList<Integer>());
			visited[i] = false;
		}
	}
	
	public void calcAdjacencies() {
		Set<Integer> keySet = adjMtx.keySet();
		for (Integer key : keySet) {
			if(key >= 4) {
				adjMtx.get(key).add(key - 4);
			} 
			if (key < 12) {
				adjMtx.get(key).add(key + 4);
			} 
			if (key%4 != 0) {
				adjMtx.get(key).add(key - 1);
			} 
			if((key+1) %4 != 0 ){
				adjMtx.get(key).add(key+1);
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
				targets.add(i);
			} else {
				startTargets(i, steps - 1);
			}
			visited[i] = false;
		}
		visited[location] = false;
	}
	
	public Set<Integer> getTargets() {
		
		return targets;
	}
	
	public ArrayList<Integer> getAdjList(int location) {
		return adjMtx.get(location);
	}
	
	public int calcIndex(int row, int column) {
		return row * 4 + column;
	}
	
}
