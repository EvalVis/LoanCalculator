package main;

/**
 * Used to form a loan statistics, from priciple (principle is loan in this program),
 * maturity (months) and interest and calculate other more difficult mathematical operations.
 * @author Evaldas
 *
 */

public class Calculator {
	
	/**
	 * Calculates loan statistics when monthly loan remains the same, while monthly interest and total cost decreases each month.
	 * @param loan - principle.
	 * @param months - maturity.
	 * @param interest - percentage of interest.
	 * @return - array of double arrays for statistics.
	 */
	
	public static double[][] calculateLinear(double loan, int months, double interest) {
		double monthlyLoan = loan / months;
		double[] monthlyInterest = new double[months];
		double[] monthlyTotal = new double[months];
		double[] leftToPay = new double[months];
		for(int i = 0; i < months; i++) {
			monthlyInterest[i] = (loan - monthlyLoan * i) * interest * 0.01 / months;
			//monthlyTotal[i] = monthlyLoan + monthlyInterest[i];
			//if(loan - monthlyLoan * (i+1) <= 0) leftToPay[i] = 0;
			leftToPay[i] = loan - monthlyLoan * (i+1);
		}
		double[] mLoan = new double[months];
		for(int i = 0; i < months; i++) mLoan[i] = monthlyLoan;
		double[][] result = round(mLoan, monthlyInterest, monthlyTotal, leftToPay, months);
		return result;
		//new Screen(monthlyLoan, monthlyInterest, monthlyTotal, leftToPay, months);
	}
	
	/**
	 * Calculates loan statistics when total cost remains the same, monthly loan rises and monthly interest decreases each month.
	 * @param loan - principle.
	 * @param months - maturity.
	 * @param interest - percentage of interest.
	 * @return - array of double arrays for anuity statistics.
	 */
	
	public static double[][] calculateAnuity(double loan, int months, double interest) {
		double loanLeft = loan;
		double monthlyTotal;
		if(interest == 0) monthlyTotal = loan / months;
		else {
		double in = interest * 0.01 / months;
		double pow = Math.pow(1 + in, months);
		double K = (in * pow) / (pow - 1);
		monthlyTotal = K * loan;
		}
		double[] monthlyInterest = new double[months];
		double[] monthlyLoan = new double[months];
		double[] leftToPay = new double[months];
		for(int i = 0; i < months; i++) {
			monthlyInterest[i] = loanLeft * interest * 0.01 / months;
			monthlyLoan[i] = monthlyTotal - monthlyInterest[i];
			loanLeft -= monthlyLoan[i];
			leftToPay[i] = loanLeft;
			//loanLeft = Math.round(loanLeft * 100) / 100.0;
		}
		double[] mTotal = new double[months];
		for(int i = 0; i < months; i++) mTotal[i] = monthlyTotal;
		double[][] result = round(monthlyLoan, monthlyInterest, mTotal, leftToPay, months);
		return result;
		//new Screen(monthlyLoan, monthlyInterest, monthlyTotal, leftToPay, months);
	}
	
	/**
	 * Round with 2 positions after separator precision.
	 */
	
	private static double[][] round(double monthlyLoan[], double[] monthlyInterest, double[] monthlyTotal, double[] leftToPay, int months) {
		for(int i = 0; i < months; i++) {
			monthlyLoan[i] = Math.round(monthlyLoan[i] * 100) / 100.0;
			monthlyInterest[i] = Math.round(monthlyInterest[i] * 100) / 100.0;
			monthlyTotal[i] = Math.round((monthlyLoan[i] + monthlyInterest[i]) * 100) / 100.0;
			leftToPay[i] = Math.round(leftToPay[i] * 100) / 100.0;
		}
		double[][] result = {monthlyLoan, monthlyInterest, monthlyTotal, leftToPay};
		return result;
	}
	
	/**
	 * @param text - String containing value of years and months.
	 * @return - total months.
	 */
	
	public static int sumMonths(String text) {
		// first check the maturity.
		String t[] = text.split(" ");
		return 12 * Integer.parseInt(t[0]) + Integer.parseInt(t[1]);
	}
	
	public static double[] sumData(double[] monthlyLoan, double[] monthlyInterest) {
		double totalLoan = 0;
		double totalInterest = 0;
		double totalTotal = 0;
		
		for(int i = 0; i < monthlyLoan.length; i++) {
			//System.out.println(totalLoan + " " + Math.round(monthlyLoan[i] * 100) / 100.0);
			totalLoan += monthlyLoan[i];
			totalInterest += monthlyInterest[i];
			totalTotal += (monthlyLoan[i] + monthlyInterest[i]);
		}
		totalLoan = Math.round(totalLoan * 100) / 100.0;
		totalInterest = Math.round(totalInterest * 100) / 100.0;
		totalTotal = Math.round(totalTotal * 100) / 100.0;
		double[] result = {totalLoan, totalInterest, totalTotal};
		return result;
		
	}

}
