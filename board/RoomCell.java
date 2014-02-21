package board;

public class RoomCell extends BoardCell {

	public enum DoorDirection {UP, DOWN, LEFT, RIGHT, NONE};
	private DoorDirection doorDirection;
	private char roomInitial;
	
	@Override
	public boolean isRoom(int location) {
		return true;
	}
	
	@Override 
	public void draw() {
		
	}
	
}
