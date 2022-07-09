package main.utils;

/**
 * 
 * @author Evaldas
 *
 */

public class Conditions {
	
	public static boolean isEmpty(String text) {
		return text.length() == 0;
	}
	
	/**
	 * Checks if a given maturity is valid. <p> For example: it cannot be negative.</p> 
	 * @return - true if maturity is valid, false otherwise.
	 */
	public static boolean isCorrectMaturity(String text) {
		boolean hasSpace = false;
		for(int i = 0; i < text.length(); i++) if(text.charAt(i) == ' ') {
			if(!hasSpace) hasSpace = true;
			else return false;
		}
		if(hasSpace) {
			String t[] = text.split(" ");
			if(isInt(t[0]) && isInt(t[1])) {
				if(Integer.parseInt(t[0]) == 0 && Integer.parseInt(t[1]) == 0) return false;
				if(Integer.parseInt(t[0]) < 0 || Integer.parseInt(t[1]) < 0) return false;
				if(Integer.parseInt(t[1]) > 12) return false;
				return true;
			}
			return false;
		}
		return false;
	}
	
	public static boolean isInt(String text) {
		for(int i = 0; i < text.length(); i++) {
			Character c = text.charAt(i);
			if(!Character.isDigit(c)) return false;
		}
		return true;
	}
	
	/**
	 * checks if the number is more than 0 and has no more than two digits after the separator.
	 */
	
	public static boolean isCorrectNumber(String text) {
		boolean separator = false;
		int separatorPosition = 0;
		for(int i = 0; i < text.length(); i++) {
			Character c = text.charAt(i);
			if(!Character.isDigit(c) && !c.equals(',') && !c.equals('.')) return false;
			if(c.equals(',') || c.equals('.')) {
				if(i == 0) return false;
				if(separator) return false;
				separator = true;
				separatorPosition = i;
			}
			if(c.equals(',')) {
				if(text.length() == i+1) return false;
				text = text.substring(0, i) + "." + text.substring(i+1);
			}
		}
		if(separator) {
		if((text.length() - 1) - separatorPosition > 2) return false;
		}
		if(Double.parseDouble(text) == 0) return false;
		return true;
	}

}
