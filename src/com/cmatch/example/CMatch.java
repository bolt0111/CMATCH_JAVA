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
	
	// Generate Random Co-founders
	public void generateCofounders() {
		for (int i = 0; i < cofoundersNum; i ++) {
			cofounders.add(generateSingleCofounder());
		}
	}
	
	// Generate Single Co-founder
	public int generateSingleCofounder() {
		int cofounder = 0;
		int tmp = (int)(Math.random() * Math.pow(10, traitsNum));
		int digit = 0;
		
		for(int j = 0 ; j < traitsNum; j ++) {
			digit = tmp % 10;
			tmp /= 10;
			if( digit >= 1 && digit <= traitsNum) {
				cofounder |= (1 << (digit - 1));
			}
		}
		
		return cofounder;
	}

	// Get the Number of the Traits of Selected Co-founders
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
	
	// Select Co-founders according to the Conditions 
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
	
	// Output the Selected Co-founders
	public void printOutput() {
		for(int i = 0; i < cofoundersNum; i ++) {
			System.out.print((selectedCofounders & (1 << i)) >> i); 
		}
	}
}
