package com.programmersdiary;

import com.programmersdiary.additions.Graph;
import com.programmersdiary.additions.Holiday;
import com.programmersdiary.additions.Printer;
import com.programmersdiary.utils.Misc;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.border.EmptyBorder;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.Serial;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * 
 * @author Evaldas
 *
 */

public class Screen extends JFrame {

	@Serial
	private static final long serialVersionUID = 1L;

    private final double[] monthlyLoan;
	private final double[] monthlyInterest;
	private final double[] monthlyTotal;
	private final double[] leftToPay;
	private String[] data;
    private final int months;
	private JTextField textField;
    private JButton button, button1, button2;
	private JComboBox<String> choice, choice1;
	private JLabel label;
	private JList<String> list;
	
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
			data[i] = java.text.MessageFormat.format(Loan.messages.getString("data_row"), (i + 1), l, in, t, ltp);
		}
	}
	
	/**
	 * creates the frame.
	 */
	
	private void setFrame() {
		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		panel.setLayout(null);
		
		// Language selection radio buttons
		JRadioButton ltRadio = new JRadioButton("Lietuvių");
        JRadioButton enRadio = new JRadioButton("English");
		ltRadio.setBounds(10, 10, 80, 20);
		enRadio.setBounds(100, 10, 80, 20);
		ButtonGroup langGroup = new ButtonGroup();
		langGroup.add(ltRadio);
		langGroup.add(enRadio);
		panel.add(ltRadio);
		panel.add(enRadio);
		if (Loan.currentLocale.getLanguage().equals("lt")) {
			ltRadio.setSelected(true);
		} else {
			enRadio.setSelected(true);
		}
		ActionListener langListener = e -> {
			if (ltRadio.isSelected()) {
				Loan.currentLocale = new Locale("lt");
			} else {
				Loan.currentLocale = new Locale("en");
			}
			Loan.messages = ResourceBundle.getBundle("messages", Loan.currentLocale);
			updateTexts();
		};
		ltRadio.addActionListener(langListener);
		enRadio.addActionListener(langListener);
		
		list = new JList<String>();
		list.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		list.setListData(data);
		int width = 700;
		int height = width * 9 / 16;
		list.setBounds(20, 40, width - 20, height - 80);
		JScrollPane scroll = new JScrollPane();
		scroll.setBounds(20, 40, width / 2 + 100, height - 100);
		scroll.setViewportView(list);
		list.setLayoutOrientation(JList.VERTICAL);
		panel.add(scroll);
		getContentPane().add(panel);
		
		choice = new JComboBox<>();
		for(int i = 0; i < months; i++) choice.addItem((i + 1) + " " + Loan.messages.getString("month"));
		choice.setBounds(486, 40, 90, 20);
		choice.setFont(new Font("Arial", Font.PLAIN, 12));
		panel.add(choice);
		
		choice1 = new JComboBox<>();
		for(int i = 0; i < months; i++) choice1.addItem((i + 1) + " " + Loan.messages.getString("month"));
		choice1.setBounds(592, 40, 90, 20);
		choice1.setFont(new Font("Arial", Font.PLAIN, 12));
		panel.add(choice1);
		
		textField = new JTextField();
		textField.setBounds(486, 293, 185, 20);
		panel.add(textField);
		textField.setColumns(10);
		choice1.setSelectedIndex(months - 1);
		
		button = new JButton(Loan.messages.getString("save"));
		button.addActionListener(e -> new Printer(textField.getText(), getSelectedData(choice, choice1)));
		button.setBounds(533, 330, 107, 23);
		panel.add(button);
		
		button1 = new JButton(Loan.messages.getString("graph"));
		button1.addActionListener(e -> {
			double[][] data = getSelectedRange(choice, choice1);
			int[] mOrder = Misc.getMonthOrder(choice, choice1);
			new Graph(data[0], data[1], data[2], data[3], mOrder[0], mOrder[1]);
		});
		button1.setBounds(539, 190, 89, 23);
		panel.add(button1);
		
		button2 = new JButton(Loan.messages.getString("payment_holiday"));
		button2.addActionListener(e -> new Holiday(monthlyLoan, monthlyInterest, monthlyTotal, leftToPay, months));
		button2.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		button2.setBounds(498, 144, 184, 23);
		panel.add(button2);
		
		label = new JLabel("");
		label.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		label.setBounds(491, 75, 180, 58);
		panel.add(label);
		setTotal(choice, choice1, label);
		
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(width, height);
		setLocationRelativeTo(null);
		
		choice.addItemListener(e -> choiceSelected(choice, choice1, list, label));
		choice1.addItemListener(e -> choiceSelected(choice, choice1, list, label));
	}
	
	/**
	 * used to draw a graph.
	 */
	
	private double[][] getSelectedRange(JComboBox<String> choice, JComboBox<String> choice1) {
		int[] months = Misc.getMonthOrder(choice, choice1);
		int firstMonth = months[0];
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
        return new double[][]{loan, interest, total, left};
	}
	
	/**
	 * used to prepare for the update of the list.
	 */
	
	private String[] getSelectedData(JComboBox<String> choice, JComboBox<String> choice1) {
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
	
	private void choiceSelected(JComboBox<String> choice, JComboBox<String> choice1, JList<String> list, JLabel label) {
		setTotal(choice, choice1, label);
		list.setListData(getSelectedData(choice, choice1));
	}
	
	/**
	 * set data, which JLabel will use to announce total cost.
	 */
	
	private void setTotal(JComboBox<String> choice, JComboBox<String> choice1, JLabel label) {
		double[][] data = getSelectedRange(choice, choice1);
		double[] dataSum = Calculator.sumData(data[0], data[1]);
        String total = java.text.MessageFormat.format(Loan.messages.getString("will_repay"), dataSum[0], dataSum[1], dataSum[2]);
		total = Misc.setLines(total);
		label.setText(total);
	}
	
	private void updateTexts() {
		// Temporarily remove item listeners to prevent exceptions during update
		ItemListener[] choice1Listeners = choice.getItemListeners();
		ItemListener[] choice2Listeners = choice1.getItemListeners();
		
		for (ItemListener listener : choice1Listeners) {
			choice.removeItemListener(listener);
		}
		for (ItemListener listener : choice2Listeners) {
			choice1.removeItemListener(listener);
		}
		
		// Store selected indices before clearing
		int selectedIndex1 = choice.getSelectedIndex();
		int selectedIndex2 = choice1.getSelectedIndex();
		
		// Update combo box items
		choice.removeAllItems();
		for(int i = 0; i < months; i++) choice.addItem((i + 1) + " " + Loan.messages.getString("month"));
		choice1.removeAllItems();
		for(int i = 0; i < months; i++) choice1.addItem((i + 1) + " " + Loan.messages.getString("month"));
		
		// Restore selected indices
		if (selectedIndex1 >= 0 && selectedIndex1 < months) {
			choice.setSelectedIndex(selectedIndex1);
		}
		if (selectedIndex2 >= 0 && selectedIndex2 < months) {
			choice1.setSelectedIndex(selectedIndex2);
		} else {
			choice1.setSelectedIndex(months - 1);
		}
		
		// Re-add item listeners
		for (ItemListener listener : choice1Listeners) {
			choice.addItemListener(listener);
		}
		for (ItemListener listener : choice2Listeners) {
			choice1.addItemListener(listener);
		}
		
		// Update button texts
		button.setText(Loan.messages.getString("save"));
		button1.setText(Loan.messages.getString("graph"));
		button2.setText(Loan.messages.getString("payment_holiday"));
		
		// Update data and total
		collectData();
		list.setListData(data);
		setTotal(choice, choice1, label);
	}
}
