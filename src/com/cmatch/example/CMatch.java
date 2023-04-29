package com.cmatch.example;

import java.util.Vector;

public class CMatch {
	private int cofoundersNum;				// The Number of the Co-founders
	private int traitsNum;					// The Number of the Traits									
	private Vector<Integer> cofounders;		// Co-founders
	private int selectedCofounders; 		// Selected Co-founders
	private Vector<Boolean> isStart;        // If is first co-founder selected
	
	// Constructor
	public CMatch(int nCofoundersNum, int nTraitsNum) {
		cofoundersNum = nCofoundersNum;
		traitsNum = nTraitsNum;
		cofounders = new Vector<Integer>();
		selectedCofounders = 0;
		isStart = new Vector<Boolean>();
		
		for(int i = 0; i < cofoundersNum; i ++) {
			isStart.add(false);
		}
	}
	
	// Copy Constructor
	public CMatch(Vector<Integer> vCofounders, int nTraitsNum) {
		cofoundersNum = vCofounders.size();
		traitsNum = nTraitsNum;
		cofounders = new Vector<Integer>();
		selectedCofounders = 0;
		isStart = new Vector<Boolean>();
		
		for(int i = 0; i < cofoundersNum; i ++) {
			cofounders.add(decToBin(vCofounders.elementAt(i)));
			System.out.println(i + 1 + " Cofounder:" + vCofounders.elementAt(i)); 
		}
		
		for(int i = 0; i < cofoundersNum; i ++) {
			isStart.add(false);
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
		int firstCofounderIndexWithMaxTraits = selectNextCofounder(0);
		int maxTraitsNum = 0;
		int selectedCofoundersNum = cofoundersNum;
		Vector<Integer> cofoundersIndexWithMaxTraits = new Vector<Integer>();
		
		for(int i = 0 ; i < traitsNum; i ++) {
			maxTraitsNum += (cofounders.elementAt(firstCofounderIndexWithMaxTraits) & (1 << i)) >> i;
		}
		
		for(int i = 0; i < cofoundersNum; i ++) {
			int iTraitsNum = 0;
			
			for(int j = 0 ; j < traitsNum; j ++) {
				iTraitsNum += (cofounders.elementAt(i) & (1 << j)) >> j;
			}
			
			if(iTraitsNum == maxTraitsNum) {
				cofoundersIndexWithMaxTraits.add(i);
			}
		}
		
		for(int i = 0; i < cofoundersIndexWithMaxTraits.size(); i ++) {
			int tmpSelectedCofoundersNum = 1;
			int tmpSelectedCofounders = 1 << cofoundersIndexWithMaxTraits.elementAt(i);
			
			while(true) {
				if(iterators == traitsNum) break;
				
				currentCoveredTraitsNum = getCoveredTraitsNum(tmpSelectedCofounders);
				if(currentCoveredTraitsNum >= traitsNum) break;
				
				if(selectedCofounderIndex != selectNextCofounder(tmpSelectedCofounders)) {
					tmpSelectedCofounders |= 1 << selectNextCofounder(tmpSelectedCofounders);
					tmpSelectedCofoundersNum ++;
				}
				
				iterators ++;
			}
			
			if(selectedCofoundersNum >= tmpSelectedCofoundersNum) {
				selectedCofoundersNum = tmpSelectedCofoundersNum;
				selectedCofounders = tmpSelectedCofounders;
			}
		}	
	}
	
	// Select the Next Co-founder
	private int selectNextCofounder(int nSelectedCofounders) {
		int selectedCofounderIndex = 0;
		int tmpCoveredTraitsNum = 0;
		int tmpSelectedCofounders = 0;
		int currentCoveredTraitsNum = 0;
		
		for(int i = 0; i < cofoundersNum; i ++) {
			tmpSelectedCofounders = nSelectedCofounders | (1 << i);
			tmpCoveredTraitsNum = getCoveredTraitsNum(tmpSelectedCofounders);
			
			if(tmpCoveredTraitsNum > currentCoveredTraitsNum) {
				currentCoveredTraitsNum = tmpCoveredTraitsNum;
				selectedCofounderIndex = i;
			}
		}
		
		return selectedCofounderIndex;
	}
	
	// Convert to Binary
	private int decToBin(int decimal) {
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
