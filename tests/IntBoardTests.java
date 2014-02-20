package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Set;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import board.IntBoard;

public class IntBoardTests {
	
	private IntBoard board;
	
	@Before 
	public void setUpBoard() {
		board = new IntBoard();
	}
	
	@Test
	public void testCalcIndex() {
		Assert.assertEquals(15, board.calcIndex(3, 3));
		Assert.assertEquals(4, board.calcIndex(1, 0));
		Assert.assertEquals(2, board.calcIndex(0, 2));
		Assert.assertEquals(6, board.calcIndex(1, 2));
	}
	
	@Test
	public void testGetAdjList() {
		ArrayList<Integer> testList = board.getAdjList(0);
		Assert.assertTrue(testList.contains(4));
		Assert.assertTrue(testList.contains(1));
		Assert.assertEquals(2, testList.size());
	}
	
	/*
	@Test
	public void testTargets0_3_2() {
		board.startTargets(0, 3, 2);
		Set targets= board.getTargets();
		Assert.assertEquals(3, targets.size());
		Assert.assertTrue(targets.contains(11));
		Assert.assertTrue(targets.contains(6));
		Assert.assertTrue(targets.contains(2));
	}
	
	@Test
	public void testTargets1_0_1() {
		board.startTargets(1, 0, 1);
		Set targets= board.getTargets();
		Assert.assertEquals(3, targets.size());
		Assert.assertTrue(targets.contains(0));
		Assert.assertTrue(targets.contains(5));
		Assert.assertTrue(targets.contains(8));
	}
	
	@Test
	public void testTargets3_1_2() {
		board.startTargets(3, 1, 2);
		Set targets= board.getTargets();
		Assert.assertEquals(4, targets.size());
		Assert.assertTrue(targets.contains(8));
		Assert.assertTrue(targets.contains(5));
		Assert.assertTrue(targets.contains(10));
		Assert.assertTrue(targets.contains(15));
	}
	
	@Test
	public void testTargets1_2_4() {
		board.startTargets(1, 2, 4);
		Set targets= board.getTargets();
		Assert.assertEquals(7, targets.size());
		Assert.assertTrue(targets.contains(11));
		Assert.assertTrue(targets.contains(4));
		Assert.assertTrue(targets.contains(9));
		Assert.assertTrue(targets.contains(3));
		Assert.assertTrue(targets.contains(12));
		Assert.assertTrue(targets.contains(14));
		Assert.assertTrue(targets.contains(1));
	}
	
	@Test
	public void testTargets0_1_2() {
		board.startTargets(0, 1, 2);
		Set targets= board.getTargets();
		Assert.assertEquals(4, targets.size());
		Assert.assertTrue(targets.contains(3));
		Assert.assertTrue(targets.contains(6));
		Assert.assertTrue(targets.contains(9));
		Assert.assertTrue(targets.contains(4));
	}
	
	@Test
	public void testTargets2_2_3() {
		board.startTargets(2, 2, 3);
		Set targets= board.getTargets();
		Assert.assertEquals(8, targets.size());
		Assert.assertTrue(targets.contains(1));
		Assert.assertTrue(targets.contains(3));
		Assert.assertTrue(targets.contains(4));
		Assert.assertTrue(targets.contains(6));
		Assert.assertTrue(targets.contains(9));
		Assert.assertTrue(targets.contains(11));
		Assert.assertTrue(targets.contains(12));
		Assert.assertTrue(targets.contains(14));
	}
	*/

}
