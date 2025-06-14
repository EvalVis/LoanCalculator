package com.programmersdiary;

import java.util.Arrays;

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
			leftToPay[i] = loan - monthlyLoan * (i+1);
		}
		double[] mLoan = new double[months];
        Arrays.fill(mLoan, monthlyLoan);
        return round(mLoan, monthlyInterest, monthlyTotal, leftToPay, months);
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
		}
		double[] mTotal = new double[months];
        Arrays.fill(mTotal, monthlyTotal);
        return round(monthlyLoan, monthlyInterest, mTotal, leftToPay, months);
	}
	
	/**
	 * Round with 2 positions after separator precision.
	 */
	
	private static double[][] round(
			double[] monthlyLoan, double[] monthlyInterest, double[] monthlyTotal, double[] leftToPay, int months
	) {
		for(int i = 0; i < months; i++) {
			monthlyLoan[i] = Math.round(monthlyLoan[i] * 100) / 100.0;
			monthlyInterest[i] = Math.round(monthlyInterest[i] * 100) / 100.0;
			monthlyTotal[i] = Math.round((monthlyLoan[i] + monthlyInterest[i]) * 100) / 100.0;
			leftToPay[i] = Math.round(leftToPay[i] * 100) / 100.0;
		}
        return new double[][]{monthlyLoan, monthlyInterest, monthlyTotal, leftToPay};
	}
	
	/**
	 * @param text - String containing value of years and months.
	 * @return - total months.
	 */
	
	public static int sumMonths(String text) {
		String[] t = text.split(" ");
		return 12 * Integer.parseInt(t[0]) + Integer.parseInt(t[1]);
	}
	
	public static double[] sumData(double[] monthlyLoan, double[] monthlyInterest) {
		double totalLoan = 0;
		double totalInterest = 0;
		double totalTotal = 0;
		
		for(int i = 0; i < monthlyLoan.length; i++) {
			totalLoan += monthlyLoan[i];
			totalInterest += monthlyInterest[i];
			totalTotal += (monthlyLoan[i] + monthlyInterest[i]);
		}
		totalLoan = Math.round(totalLoan * 100) / 100.0;
		totalInterest = Math.round(totalInterest * 100) / 100.0;
		totalTotal = Math.round(totalTotal * 100) / 100.0;
        return new double[]{totalLoan, totalInterest, totalTotal};
		
	}

}
