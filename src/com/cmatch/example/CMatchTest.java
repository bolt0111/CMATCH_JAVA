package com.cmatch.example;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Vector;

public class CMatchTest {

	public static void main(String[] args) throws IOException {
		
		System.out.println("Please input co-founders...");
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String argu = reader.readLine();
		String[] argus = argu.split("\\s+");
		
		Vector<Integer> cofounders = new Vector<Integer>();
		
		for(int i = 0; i < argus.length; i ++) {
			cofounders.add(Integer.parseInt(argus[i]));
			System.out.println(cofounders.elementAt(i));
		}
		
		CMatch cmatch = new CMatch(cofounders, 5);
		cmatch.selectCofounders();
		cmatch.printOutput();
	}
}
