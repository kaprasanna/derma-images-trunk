package com.bh.derma.images.internal.snippets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Try {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		String[] original = {"Element 1", "Element 2", "Element 3", "Element 4", "Element 5", "Element 6", "Element 7", "Element 8", "Element 9", "Element 10", };
		Integer[] originalIntArray = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
		List<Integer[]> brokenArraysList = getArrays(originalIntArray, 2);
		for(Integer[] splitArray : brokenArraysList) {
			System.out.println(Arrays.toString(splitArray));
		}
//		display(original);
//		String[] oneToThree = Arrays.copyOfRange(original, 0, 3);
//		display(oneToThree);
	}
	
	private static List<Integer[]> getArrays(Integer[] originalArray, int splitBy) {
		List<Integer[]> listofArrays = new ArrayList<Integer[]>();
		for(int i = 0; i < originalArray.length; i+=splitBy) {
			Integer[] newArray = new Integer[splitBy];
			for(int j = 0, k = i; j < splitBy; j++, k++) {
				if(k < originalArray.length)
					newArray[j] = originalArray[k];
				else
					newArray[j] = null;
			}
			listofArrays.add(newArray);
		}
		return listofArrays;
	}

	public static void display(Object[] original) {
		for(Object str : original) {
			System.out.println(str);
		}
	}

}
