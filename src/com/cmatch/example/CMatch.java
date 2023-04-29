package com.cmatch.example;

import java.util.Vector;

public class CMatch {
	public int cofoundersNum;													// The Number of the Co-founders
	public int traitsNum;														// The Number of the Traits									
	public Vector<Integer> cofounders;											// Co-founders
	public int selectedCofounders; 												// Selected Co-founders
	
	// Constructor
	public CMatch(int nCofoundersNum, int nTraitsNum) {
		cofoundersNum = nCofoundersNum;
		traitsNum = nTraitsNum;
		cofounders = new Vector<Integer>();
		selectedCofounders = 0;
	}
	
	// Copy Constructor
	public CMatch(Vector<Integer> vCofounders, int nTraitsNum) {
		cofounders = new Vector<Integer>();
		cofoundersNum = vCofounders.size();
		traitsNum = nTraitsNum;
		
		for(int i = 0; i < cofoundersNum; i ++) {
			cofounders.add(decToBin(vCofounders.elementAt(i)));
			System.out.println(i + 1 + " Cofounder:" + vCofounders.elementAt(i)); 
		}
	}
	
	// Generate Random Co-founders
	public void generateCofounders() {
		for (int i = 0; i < cofoundersNum; i ++) {
			System.out.print(i + 1 + " Cofounder:");
			cofounders.add(generateSingleCofounder());
		}
	}
	
	// Generate Random Single Co-founder
	public int generateSingleCofounder() {
		int cofounder = 0;
		int tmp = (int)(Math.random() * Math.pow(10, traitsNum));
		
		System.out.println(tmp);		
		cofounder = decToBin(tmp);
		
		return cofounder;
	}

	// Get the Number of the Covered Traits of Selected Co-founders
	public int getCoveredTraitsNum(int cofoundersIndice) {
		int totalTraitsNum = 0;
		int tmp = 0;
		
		for(int i = 0 ; i < cofoundersNum; i ++) {
			if(((cofoundersIndice & (1 << i)) >> i) == 1) {
				tmp |= cofounders.elementAt(i);
			}
		}
		
		for(int i = 0 ; i < traitsNum; i ++) {
			totalTraitsNum += (tmp & (1 << i)) >> i;
		}
		
		return totalTraitsNum;
	}
	
	// Select Co-founders according to the Conditions of the problem
	public void selectCofounders() {
		int iterators = 0;
		int selectedCofounderIndex = -1;
		int currentCoveredTraitsNum = 0;
		
		while(true) {
			if(iterators == traitsNum) break;
			
			currentCoveredTraitsNum = getCoveredTraitsNum(selectedCofounders);
			if(currentCoveredTraitsNum >= traitsNum) break;
			
			selectedCofounderIndex = selectNextCofounder();
			selectedCofounders |= 1 << selectedCofounderIndex;
			
			iterators ++;
		}
	}
	
	// Select the Next Co-founder
	public int selectNextCofounder() {
		int selectedCofounderIndex = 0;
		int tmpCoveredTraitsNum = 0;
		int tmpSelectedCofounders = 0;
		int currentCoveredTraitsNum = getCoveredTraitsNum(tmpSelectedCofounders);
		
		for(int i = 0; i < cofoundersNum; i ++) {
			tmpSelectedCofounders = selectedCofounders | (1 << i);
			tmpCoveredTraitsNum = getCoveredTraitsNum(tmpSelectedCofounders);
			
			if(tmpCoveredTraitsNum > currentCoveredTraitsNum) {
				currentCoveredTraitsNum = tmpCoveredTraitsNum;
				selectedCofounderIndex = i;
			}
		}
		
		return selectedCofounderIndex;
	}
	
	// Convert to Binary
	public int decToBin(int decimal) {
		int digit = 0;
		int binary = 0;
		
		for(int j = 0 ; j < traitsNum; j ++) {
			digit = decimal % 10;
			decimal /= 10;
			if( digit >= 1 && digit <= traitsNum) {
				binary |= (1 << (digit - 1));
			}
		}
		
		return binary;
	}
	
	// Output the Selected Co-founders
	public void printOutput() {
		System.out.print("Output:");
		for(int i = 0; i < cofoundersNum; i ++) {
			System.out.print((selectedCofounders & (1 << i)) >> i); 
		}
	}
	
	
}
