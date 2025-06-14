package com.programmersdiary;

import com.programmersdiary.utils.Conditions;
import com.programmersdiary.utils.InputError;
import com.programmersdiary.utils.Misc;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.ButtonGroup;
import javax.swing.border.EmptyBorder;
import java.awt.EventQueue;
import java.awt.Font;
import java.io.Serial;
import java.util.Locale;
import java.util.ResourceBundle;
import java.awt.event.ActionListener;

/**
 * 
 * @author Evaldas
 *
 */

public class Loan extends JFrame {

	@Serial
	private static final long serialVersionUID = 1L;

	public static Locale currentLocale = new Locale("lt");
    public static ResourceBundle messages = ResourceBundle.getBundle("messages", currentLocale);

    private final JTextField textField;
	private final JTextField textField2;
	private final JTextField textField1;

    private final JLabel label1;
    private final JLabel label2;
    private final JLabel label3;
    private final JLabel label4;
    private final JRadioButton radio1;
    private final JRadioButton radio2;
    private final JButton button;

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
            try {
                Loan frame = new Loan();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
	}

	public Loan() {
		super();
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int width = 510;
        int height = width  * 2 / 3 + 30;
        setSize(width, height);
        JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// Language selection radio buttons
		JRadioButton ltRadio = new JRadioButton("Lietuvių");
        JRadioButton enRadio = new JRadioButton("English");
		ltRadio.setBounds(10, 10, 80, 20);
		enRadio.setBounds(100, 10, 80, 20);
		ButtonGroup langGroup = new ButtonGroup();
		langGroup.add(ltRadio);
		langGroup.add(enRadio);
		contentPane.add(ltRadio);
		contentPane.add(enRadio);
		ltRadio.setSelected(true);
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

		int l1w = 150;
		int l1h = 20;
		label1 = new JLabel();
		label1.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		label1.setBounds(width / 2 - l1w / 2, 41, l1w, l1h);
		contentPane.add(label1);
		
		textField = new JTextField();
		textField.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		textField.setBounds(width / 2 - l1w / 2, 72, l1w, l1h);
		contentPane.add(textField);
		textField.setColumns(10);
		
		label2 = new JLabel();
		label2.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		label2.setBounds(207, 102, 143, 20);
		contentPane.add(label2);
		
		textField1 = new JTextField();
		textField1.setBounds(180, 133, 150, 20);
		contentPane.add(textField1);
		textField1.setColumns(10);
		
		label3 = new JLabel();
		label3.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		label3.setBounds(180, 163, l1w, l1h);
		contentPane.add(label3);
		
		radio1 = new JRadioButton();
		radio1.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		radio1.setBounds(279, 190, 87, 23);
		contentPane.add(radio1);
		
		radio2 = new JRadioButton();
		radio2.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		radio2.setBounds(185, 190, 87, 23);
		contentPane.add(radio2);
		
		label4 = new JLabel();
		label4.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		label4.setBounds(200, 220, l1w, l1h);
		contentPane.add(label4);
		
		textField2 = new JTextField();
		textField2.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		textField2.setBounds(width / 2 - l1w / 2, 250, l1w, l1h);
		contentPane.add(textField2);
		textField2.setColumns(10);

		button = calculateLoanButton(radio1, radio2);
		button.setBounds(width / 2 - l1w / 2, 290, l1w, l1h);
		contentPane.add(button);
		setLocationRelativeTo(null);
		
		ButtonGroup radio = new ButtonGroup();
		radio.add(radio1);
		radio.add(radio2);

		updateTexts();
	}

	private void updateTexts() {
		setTitle(messages.getString("loan_calculator"));
		label1.setText(messages.getString("enter_loan_amount"));
		label2.setText(messages.getString("loan_term"));
		label3.setText(messages.getString("repayment_schedule_graph"));
		radio1.setText(messages.getString("linear"));
		radio2.setText(messages.getString("annuity"));
		label4.setText(messages.getString("annual_rate"));
		button.setText(messages.getString("calculate"));
	}

	private JButton calculateLoanButton(JRadioButton radio1, JRadioButton radio2) {
		JButton button = new JButton(messages.getString("calculate"));
		button.addActionListener(e -> {
            InputError.clear();
            String loan = textField.getText();
            String maturity = textField1.getText();
            String percent = textField2.getText(); // annual
            if(Conditions.isEmpty(loan)) InputError.addError("er_missing_amount");
            else if(Conditions.isCorrectNumber(loan)) InputError.addError("er_invalid_amount");
            if(Conditions.isEmpty(percent)) InputError.addError("er_missing_rate");
            else if(Conditions.isCorrectNumber(percent)) InputError.addError("er_invalid_rate");
            if(Conditions.isEmpty(maturity)) InputError.addError("er_missing_term");
            else if(!Conditions.isCorrectMaturity(maturity)) InputError.addError("er_invalid_term");
            if(!radio1.isSelected() && !radio2.isSelected()) InputError.addError("er_missing_schedule_graph");
            loan = Misc.changeSeparator(loan);
            percent = Misc.changeSeparator(percent);
            if(!InputError.isEmpty()) {
                new InputError();
            }
            else {
                double loanValue = Double.parseDouble(loan);
                int months = Calculator.sumMonths(maturity);
                double interestValue = Double.parseDouble(percent);
                double[][] data;
                if(radio1.isSelected()) data = Calculator.calculateLinear(loanValue, months, interestValue);
                else data = Calculator.calculateAnuity(loanValue, months, interestValue);
                new Screen(data[0], data[1], data[2], data[3], months);
            }
        });
		button.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		return button;
	}

}
