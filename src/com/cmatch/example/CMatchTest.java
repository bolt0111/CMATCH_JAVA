package com.cmatch.example;

public class CMatchTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CMatch cmatch = new CMatch(5, 5);
		cmatch.generateCofounders();
		cmatch.selectCofounders();
		cmatch.printOutput();
	}
}
