package main.additions;

import java.awt.Choice;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import main.Screen;
import main.utils.Conditions;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import main.utils.Error;
import main.utils.Misc;

/**
 * Used for showing the situation if the user decides to delay the credit.
 * @author Evaldas
 *
 */

public class Holiday extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	private final int width = 500;
	private final int height = 300;
	private JTextField textField;
	
	public Holiday(double[] monthlyLoan, double[] monthlyInterest, double[] monthlyTotal, double[] leftToPay, int months) {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(width, height);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("Nuo");
		label.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		label.setBounds(99, 36, 107, 14);
		contentPane.add(label);
		
		JLabel label1 = new JLabel("Iki");
		label1.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		label1.setBounds(311, 36, 145, 14);
		contentPane.add(label1);
		
		textField = new JTextField();
		textField.setBounds(311, 78, 56, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		Choice choice = new Choice();
		choice.setBounds(99, 78, 152, 20);
		contentPane.add(choice);
		
		JButton button = new JButton("Patvirtinti");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(Conditions.isEmpty(textField.getText())) {
					Error.addError(Error.e11);
					new Error();
				}
				else if(!Conditions.isInt(textField.getText())) {
					Error.addError(Error.e12);
					new Error();
				}
				else if((choice.getSelectedIndex() + 1) >= Integer.parseInt(textField.getText())) {
					Error.addError(Error.e12);
					new Error();
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
			}
		});
		button.setBounds(221, 156, 89, 23);
		contentPane.add(button);
		
		JLabel label2 = new JLabel("mėnesio");
		label2.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		label2.setBounds(398, 81, 58, 14);
		contentPane.add(label2);
		for(int i = 0; i < months; i++) choice.add((i+1) + " mėnesio");
		setLocationRelativeTo(null);
		setVisible(true);
	}
}
