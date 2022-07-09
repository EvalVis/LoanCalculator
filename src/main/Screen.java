package main;

import java.awt.Choice;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import main.additions.Graph;
import main.additions.Holiday;
import main.additions.Printer;
import main.utils.Misc;
import javax.swing.JLabel;

/**
 * 
 * @author Evaldas
 *
 */

public class Screen extends JFrame {

	private static final long serialVersionUID = 1L;
	private final int width = 700;
	private final int height = width * 9 / 16;
	
	private double[] monthlyLoan;
	private double[] monthlyInterest;
	private double[] monthlyTotal;
	private double[] leftToPay;
	private String[] data;
	private String total = "";
	private int months;
	private JTextField textField;
	
	/**
	 * 
	 * @param monthlyLoan - array given from calculator.
	 * @param monthlyInterest - array given from calculator.
	 * @param monthlyTotal - array given from calculator.
	 * @param leftToPay - array given from calculator.
	 * @param months - total months of maturity.
	 */
	
	public Screen(double[] monthlyLoan, double[] monthlyInterest, double[] monthlyTotal, double[] leftToPay, int months) {
		this.monthlyLoan = monthlyLoan;
		this.monthlyInterest = monthlyInterest;
		this.monthlyTotal = monthlyTotal;
		this.leftToPay = leftToPay;
		this.months = months;
		collectData();
		setFrame();
	}
	
	/**
	 * prepares the data to be written on the list.
	 */
	
	private void collectData() {
		data = new String[months];
		for(int i = 0; i < months; i++) {
			String l = Double.toString(monthlyLoan[i]);
			String in = Double.toString(monthlyInterest[i]);
			String t = Double.toString(monthlyTotal[i]);
			String ltp = Double.toString(leftToPay[i]);
			data[i] = (i + 1)  + " mėnuo: paskolos suma: " + l + ", palūkanos: " + in + ", viso: " + t + ", liko: " + ltp;
		}
	}
	
	/**
	 * creates the frame.
	 */
	
	private void setFrame() {
		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		panel.setLayout(null);
		JList<String> list = new JList<String>();
		list.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		list.setListData(data);
		list.setBounds(20, 20, width - 20, height - 60);
		JScrollPane scroll = new JScrollPane();
		scroll.setBounds(20, 20, width / 2 + 100, height - 80);
		scroll.setViewportView(list);
		list.setLayoutOrientation(JList.VERTICAL);
		panel.add(scroll);
		getContentPane().add(panel);
		
		Choice choice = new Choice();
		for(int i = 0; i < months; i++) choice.add((i + 1) + " mėnuo");
		choice.setBounds(486, 20, 90, 20);
		panel.add(choice);
		
		Choice choice1 = new Choice();
		for(int i = 0; i < months; i++) choice1.add((i + 1) + " mėnuo");
		choice1.setBounds(592, 20, 90, 20);
		panel.add(choice1);
		
		textField = new JTextField();
		textField.setBounds(486, 273, 185, 20);
		panel.add(textField);
		textField.setColumns(10);
		choice1.select(months - 1);
		
		JButton button = new JButton("Įrašyti");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Printer(textField.getText(), getSelectedData(choice, choice1));
			}
		});
		button.setBounds(533, 310, 107, 23);
		panel.add(button);
		
		JButton button1 = new JButton("Grafikas");
		button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				double[][] data = getSelectedRange(choice, choice1);
				int[] mOrder = Misc.getMonthOrder(choice, choice1);
				new Graph(data[0], data[1], data[2], data[3], mOrder[0], mOrder[1]);
			}
		});
		button1.setBounds(539, 170, 89, 23);
		panel.add(button1);
		
		JButton button2 = new JButton("Mokėjimo atostogos");
		button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Holiday(monthlyLoan, monthlyInterest, monthlyTotal, leftToPay, months);
			}
		});
		button2.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		button2.setBounds(498, 124, 184, 23);
		panel.add(button2);
		
		JLabel label = new JLabel("");
		label.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		label.setBounds(491, 55, 180, 58);
		panel.add(label);
		setTotal(choice, choice1, label);
		
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(width, height);
		setLocationRelativeTo(null);
		
		choice.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				choiceSelected(choice, choice1, list, label);
			}
		});
		
		choice1.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				choiceSelected(choice, choice1, list, label);
			}
		});
	}
	
	/**
	 * used to draw a graph.
	 */
	
	private double[][] getSelectedRange(Choice choice, Choice choice1) {
		int[] months = Misc.getMonthOrder(choice, choice1);
		int firstMonth = months[0];
		// Gali pasikeisti order tarp?
		int secondMonth = months[1];
		double[] loan = new double[secondMonth - firstMonth + 1];
		double[] interest = new double[secondMonth - firstMonth + 1];
		double[] total = new double[secondMonth - firstMonth + 1];
		double[] left = new double[secondMonth - firstMonth + 1];
		int r = 0;
		for(int i = firstMonth; i < secondMonth + 1; i++) {
			loan[r] = monthlyLoan[i];
			interest[r] = monthlyInterest[i];
			total[r] = monthlyTotal[i];
			left[r] = leftToPay[i];
			r++;
		}
		double[][] selected = {loan, interest, total, left};
		return selected;
	}
	
	/**
	 * used to prepare for the update of the list.
	 */
	
	private String[] getSelectedData(Choice choice, Choice choice1) {
		int[] months = Misc.getMonthOrder(choice, choice1);
		int firstMonth = months[0];
		int secondMonth = months[1];
		String[] data = new String[secondMonth - firstMonth + 1];
		int r = 0;
		for(int i = firstMonth; i < secondMonth + 1; i++) {
			data[r] = this.data[i];
			r++;
		}
		return data;
	}
	/**
	 * when choice action triggers, this comes after.
	 */
	
	private void choiceSelected(Choice choice, Choice choice1, JList<String> list, JLabel label) {
		setTotal(choice, choice1, label);
		list.setListData(getSelectedData(choice, choice1));
	}
	
	/**
	 * set data, which JLabel will use to announce total cost.
	 */
	
	private void setTotal(Choice choice, Choice choice1, JLabel label) {
		double data[][] = getSelectedRange(choice, choice1);
		double dataSum[] = Calculator.sumData(data[0], data[1]);
		total = "Turėsite grąžinti: " + dataSum[0] + " + \n + palūkanos: " + dataSum[1] + " = \n = " + dataSum[2] + ".";
		total = Misc.setLines(total);
		label.setText(total);
	}
}
