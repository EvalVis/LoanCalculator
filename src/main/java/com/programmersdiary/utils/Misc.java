package com.programmersdiary.utils;

import javax.swing.JComboBox;

public class Misc {
	
	/**
	 * Used to allow Lithuanian standards.
	 * @param text - search for dots and replaces them with commas.
	 * @return - text without dots and possibly with commas.
	 */
	
	public static String changeSeparator(String text) {
		for(int i = 0; i < text.length(); i++) {
			if(text.charAt(i) == ',') {
				if(i + 1 == text.length()) text = text.substring(0, i) + '.';
				else text = text.substring(0, i) + '.' + text.substring(i+1);
			}
		}
		return text;
	}
	
	/**
	 * used to create a new line on JLabel.
	 */
	
	public static String setLines(String text)
	{
	    return "<html>" + text.replaceAll("\n", "<br>");
	}
	
	/**
	 * used with JComboBox. Sets the months in correct order.
	 */
	
	public static int[] getMonthOrder(JComboBox<String> choice, JComboBox<String> choice1) {
		int firstMonth;
		int secondMonth;
		if(choice.getSelectedIndex() > choice1.getSelectedIndex()) {
			firstMonth = choice1.getSelectedIndex();
			secondMonth = choice.getSelectedIndex();
		}
		else {
			firstMonth = choice.getSelectedIndex();
			secondMonth = choice1.getSelectedIndex();
		}
        return new int[]{firstMonth, secondMonth};
	}
	
	/**
	 * used with Holiday class.
	 */
	
	public static double[] changeWithDuplicates(double[] data, double duplicate, int firstPosition, int lastPosition, int newLenght) {
		double[] result = new double[newLenght];
        if (firstPosition >= 0) System.arraycopy(data, 0, result, 0, firstPosition);
		for(int i = firstPosition; i < lastPosition; i++) result[i] = duplicate;
		int r = 0;
		for(int i = lastPosition; i < newLenght; i++) {
			result[i] = data[firstPosition + r];
			r++;
		}
		return result;
	}

}
