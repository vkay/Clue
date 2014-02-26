package board;

public class RoomCell extends BoardCell {

	public enum DoorDirection {UP, DOWN, LEFT, RIGHT, NONE};
	private DoorDirection doorDirection = DoorDirection.NONE;
	private char roomInitial;
	
	public RoomCell(String room) {
		roomInitial = room.charAt(0);
		if(room.length() == 2){
			if (room.charAt(1) == 'R') {
				doorDirection = DoorDirection.RIGHT;
			} else if (room.charAt(1) == 'U') {
				doorDirection = DoorDirection.UP;
			} else if (room.charAt(1) == 'D') {
				doorDirection = DoorDirection.DOWN;
			} else if (room.charAt(1) == 'L') {
				doorDirection = DoorDirection.LEFT;
			} else {
				doorDirection = DoorDirection.NONE;
			}
		}
	}
	
	@Override
	public boolean isRoom() {
		return true;
	}
	
	@Override
	public boolean isDoorway() {
		if(doorDirection != DoorDirection.NONE){
			return true;
		}
		return false;
	}
	
	@Override
	public void draw() {
		
	}

	public DoorDirection getDoorDirection() {
		return doorDirection;
	}

	public char getInitial() {
		return roomInitial;
	}
	
}
