package com.programmersdiary.additions;

import com.programmersdiary.Loan;
import com.programmersdiary.Screen;
import com.programmersdiary.utils.Conditions;
import com.programmersdiary.utils.InputError;
import com.programmersdiary.utils.Misc;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import java.awt.Font;
import java.io.Serial;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Used for showing the situation if the user decides to delay the credit.
 * @author Evaldas
 *
 */

public class Holiday extends JFrame {

	@Serial
	private static final long serialVersionUID = 1L;

    private final JTextField textField;
	private JRadioButton ltRadio;
	private JRadioButton enRadio;
	private JLabel label, label1, label2;
	private JButton button;
	private JComboBox<String> choice;
	private final int totalMonths;
	
	public Holiday(double[] monthlyLoan, double[] monthlyInterest, double[] monthlyTotal, double[] leftToPay, int months) {
		this.totalMonths = months;
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        int width = 500;
        int height = 330;
        setSize(width, height);
        JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// Language selection radio buttons
		ltRadio = new JRadioButton("Lietuvių");
		enRadio = new JRadioButton("English");
		ltRadio.setBounds(10, 10, 80, 20);
		enRadio.setBounds(100, 10, 80, 20);
		ButtonGroup langGroup = new ButtonGroup();
		langGroup.add(ltRadio);
		langGroup.add(enRadio);
		contentPane.add(ltRadio);
		contentPane.add(enRadio);
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
		
		label = new JLabel(Loan.messages.getString("from"));
		label.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		label.setBounds(99, 56, 107, 14);
		contentPane.add(label);
		
		label1 = new JLabel(Loan.messages.getString("to"));
		label1.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		label1.setBounds(311, 56, 145, 14);
		contentPane.add(label1);
		
		textField = new JTextField();
		textField.setBounds(311, 98, 56, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		choice = new JComboBox<>();
		for(int i = 0; i < months; i++) choice.addItem((i+1) + " " + Loan.messages.getString("months_genitive"));
		choice.setBounds(99, 98, 152, 20);
		choice.setFont(new Font("Arial", Font.PLAIN, 12));
		contentPane.add(choice);
		
		button = new JButton(Loan.messages.getString("confirm"));
		button.addActionListener(e -> {
            if(Conditions.isEmpty(textField.getText())) {
                InputError.addError("er_missing_holiday_end");
                new InputError();
            }
            else if(!Conditions.isInt(textField.getText())) {
                InputError.addError("er_invalid_holiday_end");
                new InputError();
            }
            else if((choice.getSelectedIndex() + 1) >= Integer.parseInt(textField.getText())) {
                InputError.addError("er_invalid_holiday_end");
                new InputError();
            }
            else {
                dispose();
                int index = choice.getSelectedIndex();
                int lastPosition = Integer.parseInt(textField.getText()) - 1;
                int difference = lastPosition - index;
                int newLenght = months + difference;
                double[] newLoan = Misc.changeWithDuplicates(monthlyLoan, 0, index, lastPosition, newLenght);
                double[] newInterest = Misc.changeWithDuplicates(monthlyInterest, monthlyInterest[index], index, lastPosition, newLenght);
                double[] newTotal = Misc.changeWithDuplicates(monthlyTotal, monthlyInterest[index], index, lastPosition, newLenght);
                double[] newPay;
                if(index > 0) newPay = Misc.changeWithDuplicates(leftToPay, leftToPay[index-1], index, lastPosition, newLenght);
                else newPay = Misc.changeWithDuplicates(leftToPay, leftToPay[0] + monthlyLoan[0], index, lastPosition, newLenght);
                new Screen(newLoan, newInterest, newTotal, newPay, months + difference);
            }
        });
		button.setBounds(221, 176, 89, 23);
		contentPane.add(button);
		
		label2 = new JLabel(Loan.messages.getString("months_genitive"));
		label2.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		label2.setBounds(398, 101, 58, 14);
		contentPane.add(label2);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void updateTexts() {
		label.setText(Loan.messages.getString("from"));
		label1.setText(Loan.messages.getString("to"));
		label2.setText(Loan.messages.getString("months_genitive"));
		button.setText(Loan.messages.getString("confirm"));
		
		// Update combo box items
		int selectedIndex = choice.getSelectedIndex();
		choice.removeAllItems();
		for(int i = 0; i < totalMonths; i++) choice.addItem((i+1) + " " + Loan.messages.getString("months_genitive"));
		if (selectedIndex >= 0 && selectedIndex < totalMonths) {
			choice.setSelectedIndex(selectedIndex);
		}
	}
}
