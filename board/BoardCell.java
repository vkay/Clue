package board;

public abstract class BoardCell {

	private int row, column;
	
	public boolean isWalkway(int location) {
		return false;
	}
	
	public boolean isRoom(int location) {
		return false;
	}
	
	public boolean isDoorway(int location) {
		return false;
	}
	
	abstract public void draw();
	
}
