package com.www.learning;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


public class CaculatorTest {
	
	@Rule
    public ExpectedException exp = ExpectedException.none();

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testAdd() {
		Caculator cac = new Caculator();
		String exp = "1+2";
		int actual = cac.caculate(exp);
		assertEquals(3, actual);
	}
	
	
	@Test
	public void testMultiple() {
		Caculator cac = new Caculator();
		String exp = "4*2";
		int actual = cac.caculate(exp);
		assertEquals(8, actual);
	}
	
	@Test
	public void testDivide() {
		Caculator cac = new Caculator();
		String exp = "10/4";
		int actual = cac.caculate(exp);
		assertEquals(2, actual);
	}
	
	@Test
	public void testSub() {
		Caculator cac = new Caculator();
		String exp = "10-4";
		int actual = cac.caculate(exp);
		assertEquals(6, actual);
	}
	
	
	@Test
	public void testMix() {
		Caculator cac = new Caculator();
		String exp = "1+2*3+6/2";
		int actual = cac.caculate(exp);
		assertEquals(10, actual);
	}
	
	@Test
	public void testMixBrackets() {
		Caculator cac = new Caculator();
		String exp = "1+4*(3+6)/2";
		int actual = cac.caculate(exp);
		assertEquals(19, actual);
	}
	
	
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidCharExp() {
		Caculator cac = new Caculator();
		String exp = "1+4*(3+6)/2s";
		int actual = cac.caculate(exp);
		assertEquals(19, actual);
	}
	
	
	@Test
	public void testNullExp() {
		exp.expect(IllegalArgumentException.class);
		Caculator cac = new Caculator();
		String exp = "";
		int actual = cac.caculate(exp);
		assertEquals(19, actual);
	}
	
	@Test
	public void testInvalidOperatorExp() {
		exp.expect(IllegalArgumentException.class);
		Caculator cac = new Caculator();
		String exp = "1+4*(3+6*)/2";
		int actual = cac.caculate(exp);
		assertEquals(19, actual);
	}
	
	
	@Test
	public void testUnClosedBrackets() {
		exp.expect(IllegalArgumentException.class);
		Caculator cac = new Caculator();
		String exp = "1+4*(3+6(*/2";
		int actual = cac.caculate(exp);
		assertEquals(19, actual);
	}
	
	
	
}
