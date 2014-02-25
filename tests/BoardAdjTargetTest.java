package tests;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Set;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import board.BadConfigFormatException;
import board.Board;
import board.BoardCell;

public class BoardAdjTargetTest {
	private static Board board;
	@BeforeClass
	public static void setUp() throws FileNotFoundException, BadConfigFormatException {
		board = new Board();
		board.loadConfigFiles();
		board.calcAdjacencies();

	}

	// Ensure that player does not move around within room
	// These cells are ORANGE on the planning spreadsheet
	@Test
	public void testAdjacenciesInsideRooms()
	{
		// Test a corner
		ArrayList<Integer> testList = board.getAdjList(board.calcIndex(0, 0));
		Assert.assertEquals(0, testList.size());
		// Test one that has walkway underneath
		testList = board.getAdjList(board.calcIndex(4, 4));
		Assert.assertEquals(0, testList.size());
		// Test one that has walkway to the left
		testList = board.getAdjList(board.calcIndex(19, 7));
		Assert.assertEquals(0, testList.size());
		// Test one that is in middle of room
		testList = board.getAdjList(board.calcIndex(10, 2));
		Assert.assertEquals(0, testList.size());
		// Test one beside a door also with a walkway above
		testList = board.getAdjList(board.calcIndex(17, 4));
		Assert.assertEquals(0, testList.size());
		// Test one in a corner of room
		testList = board.getAdjList(board.calcIndex(3, 16));
		Assert.assertEquals(0, testList.size());
	}

	// Ensure that the adjacency list from a doorway is only the
	// walkway. NOTE: This test could be merged with door 
	// direction test. 
	// These tests are PURPLE on the planning spreadsheet
	@Test
	public void testAdjacencyRoomExit()
	{
		// TEST DOORWAY RIGHT 
		ArrayList<Integer> testList = board.getAdjList(board.calcIndex(2, 5));
		Assert.assertEquals(1, testList.size());
		Assert.assertTrue(testList.contains(board.calcIndex(2, 6)));
		// TEST DOORWAY LEFT 
		testList = board.getAdjList(board.calcIndex(15, 10));
		Assert.assertEquals(1, testList.size());
		Assert.assertTrue(testList.contains(board.calcIndex(15, 9)));
		//TEST DOORWAY DOWN
		testList = board.getAdjList(board.calcIndex(6, 16));
		Assert.assertEquals(1, testList.size());
		Assert.assertTrue(testList.contains(board.calcIndex(7, 16)));
		//TEST DOORWAY UP
		testList = board.getAdjList(board.calcIndex(14, 21));
		Assert.assertEquals(1, testList.size());
		Assert.assertTrue(testList.contains(board.calcIndex(13, 21)));
		
	}
	
	// Test adjacency at entrance to rooms
	// These tests are GREEN in planning spreadsheet
	@Test
	public void testAdjacencyDoorways()
	{
		// Test beside a door direction RIGHT
		ArrayList<Integer> testList = board.getAdjList(board.calcIndex(17, 6));
		Assert.assertTrue(testList.contains(board.calcIndex(18, 6)));
		Assert.assertTrue(testList.contains(board.calcIndex(17, 5)));
		Assert.assertTrue(testList.contains(board.calcIndex(16, 6)));
		Assert.assertEquals(3, testList.size());
		// Test beside a door direction DOWN
		testList = board.getAdjList(board.calcIndex(5, 14));
		Assert.assertTrue(testList.contains(board.calcIndex(5, 13)));
		Assert.assertTrue(testList.contains(board.calcIndex(5, 15)));
		Assert.assertTrue(testList.contains(board.calcIndex(4, 14)));
		Assert.assertEquals(3, testList.size());
		// Test beside a door direction LEFT
		testList = board.getAdjList(board.calcIndex(15, 9));
		Assert.assertTrue(testList.contains(board.calcIndex(15, 10)));
		Assert.assertTrue(testList.contains(board.calcIndex(15, 8)));
		Assert.assertTrue(testList.contains(board.calcIndex(14, 9)));
		Assert.assertEquals(3, testList.size());
		// Test beside a door direction UP
		testList = board.getAdjList(board.calcIndex(13, 21));
		Assert.assertTrue(testList.contains(board.calcIndex(12, 21)));
		Assert.assertTrue(testList.contains(board.calcIndex(13, 20)));
		Assert.assertTrue(testList.contains(board.calcIndex(13, 22)));
		Assert.assertTrue(testList.contains(board.calcIndex(14, 21)));
		Assert.assertEquals(4, testList.size());
		// Test beside a door that's not the up direction
		testList = board.getAdjList(board.calcIndex(14, 20));
		Assert.assertTrue(testList.contains(board.calcIndex(14, 19)));
		Assert.assertTrue(testList.contains(board.calcIndex(13, 20)));
		// This ensures we haven't included cell (4, 3) which is a doorway
		Assert.assertEquals(2, testList.size());		
	}
	
	
	// Test a variety of walkway scenarios
	// These tests are LIGHT PURPLE on the planning spreadsheet
	@Test
	public void testAdjacencyWalkways()
	{
		// Test on top edge of board, just one walkway piece
		ArrayList<Integer> testList = board.getAdjList(board.calcIndex(0, 12));
		Assert.assertTrue(testList.contains(board.calcIndex(1,12)));
		Assert.assertEquals(1, testList.size());
		
		// Test on left edge of board, two walkway pieces
		testList = board.getAdjList(board.calcIndex(5, 0));
		Assert.assertTrue(testList.contains(board.calcIndex(6, 0)));
		Assert.assertTrue(testList.contains(board.calcIndex(5, 1)));
		Assert.assertEquals(2, testList.size());

		// Test between two rooms, walkways up and down
		testList = board.getAdjList(board.calcIndex(17, 15));
		Assert.assertTrue(testList.contains(board.calcIndex(16, 15)));
		Assert.assertTrue(testList.contains(board.calcIndex(18, 15)));
		Assert.assertEquals(2, testList.size());

		// Test surrounded by 4 walkways
		testList = board.getAdjList(board.calcIndex(15,6));
		Assert.assertTrue(testList.contains(board.calcIndex(15, 7)));
		Assert.assertTrue(testList.contains(board.calcIndex(15, 5)));
		Assert.assertTrue(testList.contains(board.calcIndex(14, 6)));
		Assert.assertTrue(testList.contains(board.calcIndex(16, 6)));
		Assert.assertEquals(4, testList.size());
		
		// Test on bottom edge of board, next to 2 room piece
		testList = board.getAdjList(board.calcIndex(21, 5));
		Assert.assertTrue(testList.contains(board.calcIndex(21, 6)));
		Assert.assertEquals(1, testList.size());
		
		// Test on right edge of board, next to 1 room piece
		testList = board.getAdjList(board.calcIndex(13, 22));
		Assert.assertTrue(testList.contains(board.calcIndex(13, 21)));
		Assert.assertTrue(testList.contains(board.calcIndex(12, 22)));
		Assert.assertEquals(2, testList.size());

		// Test on walkway next to  door that is not in the needed
		// direction to enter
		testList = board.getAdjList(board.calcIndex(14, 10));
		Assert.assertTrue(testList.contains(board.calcIndex(14, 11)));
		Assert.assertTrue(testList.contains(board.calcIndex(14, 9)));
		Assert.assertTrue(testList.contains(board.calcIndex(13, 10)));
		Assert.assertEquals(3, testList.size());
	}
	
	/*
	// Tests of just walkways, 1 step, includes on edge of board
	// and beside room
	// Have already tested adjacency lists on all four edges, will
	// only test two edges here
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsOneStep() {
		board.calcTargets(21, 15, 1);
		Set<BoardCell> targets= board.getTargets();
		Assert.assertEquals(2, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(20, 15))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(21, 16))));	
		
		board.calcTargets(15, 0, 1);
		targets= board.getTargets();
		Assert.assertEquals(2, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(15, 1))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(16, 0))));			
	}
	// Tests of just walkways, 2 steps
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsTwoSteps() {
		board.calcTargets(4, 20, 2);
		Set<BoardCell> targets= board.getTargets();
		Assert.assertEquals(2, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(4, 22))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(4, 18))));
		
		board.calcTargets(13, 20, 2);
		targets= board.getTargets();
		Assert.assertEquals(4, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(12, 21))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(14, 21))));	
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(14, 19))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(13, 18))));
	}
	// Tests of just walkways, 4 steps
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsFourSteps() {
		board.calcTargets(0, 7, 4);
		Set<BoardCell> targets= board.getTargets();
		Assert.assertEquals(3, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(4, 7))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(3, 6))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(2, 5))));
		
		// Includes a path that doesn't have enough length
		board.calcTargets(6, 0, 4);
		targets= board.getTargets();
		Assert.assertEquals(3, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(6, 4))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(5, 3))));	
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(6, 2))));	
	}	
	// Tests of just walkways plus one door, 6 steps
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsSixSteps() {
		board.calcTargets(12, 22, 6);
		Set<BoardCell> targets= board.getTargets();
		Assert.assertEquals(5, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(14, 21))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(13, 17))));	
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(14, 18))));	
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(14, 20))));	
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(13, 19))));	
	}	
	
	// Test getting into a room
	// These are LIGHT BLUE on the planning spreadsheet

	@Test 
	public void testTargetsIntoRoom()
	{
		// One room is exactly 2 away
		board.calcTargets(15, 14, 2);
		Set<BoardCell> targets= board.getTargets();
		Assert.assertEquals(6, targets.size());
		// directly right 
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(15, 16))));
		// directly up (can't go directly down)
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(13, 14))));
		// directly left
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(15, 12))));
		// one up/down, one left/right
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(14, 15))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(14, 13))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(16, 15))));
	}
	
	// Test getting into room, doesn't require all steps
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsIntoRoomShortcut() 
	{
		board.calcTargets(18, 6, 3);
		Set<BoardCell> targets= board.getTargets();
		Assert.assertEquals(5, targets.size());
		// directly up and down
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(21, 6))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(15, 6))));
		// up then left
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(16, 5))));
		// into the rooms
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(18, 5))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(17, 5))));		
	}

	// Test getting out of a room
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testRoomExit()
	{
		// Take one step, essentially just the adj list
		board.calcTargets(15, 4, 1);
		Set<BoardCell> targets= board.getTargets();
		// Ensure doesn't exit through the wall
		Assert.assertEquals(1, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(16, 4))));
		// Take two steps
		board.calcTargets(15, 4, 2);
		targets= board.getTargets();
		Assert.assertEquals(2, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(16, 3))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(16, 5))));
	}
	*/
}

