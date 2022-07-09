package main;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import main.utils.Conditions;
import main.utils.Error;
import main.utils.Misc;

/**
 * 
 * @author Evaldas
 *
 */

public class Loan extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	private final int width = 510;
	private final int height = width * 2 / 3;
	private JTextField textField;
	private JTextField textField2;
	private JTextField textField1;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Loan frame = new Loan();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Loan() {
		super("Paskolos skaičiuoklė");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(width, height);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label1 = new JLabel("\u012Eveskite norim\u0105 paskolos sum\u0105");
		label1.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		int l1w = 150;
		int l1h = 20;
		label1.setBounds(width / 2 - l1w / 2, 11, l1w, l1h);
		contentPane.add(label1);
		
		textField = new JTextField();
		textField.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		textField.setBounds(width / 2 - l1w / 2, 42, l1w, l1h);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel label2 = new JLabel("Paskolos terminas");
		label2.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		label2.setBounds(207, 72, 143, 20);
		contentPane.add(label2);
		
		textField1 = new JTextField();
		textField1.setBounds(180, 103, 150, 20);
		contentPane.add(textField1);
		textField1.setColumns(10);
		
		JLabel label3 = new JLabel("Paskolos gr\u0105\u017Einimo grafikas");
		label3.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		label3.setBounds(180, 133, l1w, l1h);
		contentPane.add(label3);
		
		JRadioButton radio1 = new JRadioButton("Linijinis");
		radio1.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		radio1.setBounds(279, 160, 87, 23);
		contentPane.add(radio1);
		
		JRadioButton radio2 = new JRadioButton("Anuitetas");
		radio2.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		radio2.setBounds(139, 160, 87, 23);
		contentPane.add(radio2);
		
		JLabel label4 = new JLabel("Metinis procentas");
		label4.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		label4.setBounds(200, 190, l1w, l1h);
		contentPane.add(label4);
		
		textField2 = new JTextField();
		textField2.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		textField2.setBounds(width / 2 - l1w / 2, 220, l1w, l1h);
		contentPane.add(textField2);
		textField2.setColumns(10);
		
		JButton button = new JButton("Skai\u010Diuoti");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Error.clear();
				String loan = textField.getText();
				String maturity = textField1.getText();
				String percent = textField2.getText(); // annual
				if(Conditions.isEmpty(loan)) Error.addError(Error.e4);
				else if(!Conditions.isCorrectNumber(loan)) Error.addError(Error.e1);
				if(Conditions.isEmpty(percent)) Error.addError(Error.e5);
				else if(!Conditions.isCorrectNumber(percent)) Error.addError(Error.e2);
				if(Conditions.isEmpty(maturity)) Error.addError(Error.e6);
				else if(!Conditions.isCorrectMaturity(maturity)) Error.addError(Error.e3);
				if(!radio1.isSelected() && !radio2.isSelected()) Error.addError(Error.e7);
				loan = Misc.changeSeparator(loan);
				percent = Misc.changeSeparator(percent);
				if(!Error.isEmpty()) {
					new Error();
				}
				else {
					double loanValue = Double.parseDouble(loan);
					int months = Calculator.sumMonths(maturity);
					double interestValue = Double.parseDouble(percent);
					double[][] data;
					if(radio1.isSelected()) data = Calculator.calculateLinear(loanValue, months, interestValue);
					else data = Calculator.calculateAnuity(loanValue, months, interestValue);
					new Screen(data[0], data[1], data[2], data[3], months);
					//new Calculator(Double.parseDouble(loan), sumMonths(maturity), linear, Double.parseDouble(percent));
					//new Screen(Double.parseDouble(loan), sumMonths(maturity), linear, Double.parseDouble(percent));
				}
			}
		});
		button.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		button.setBounds(width / 2 - l1w / 2, 260, l1w, l1h);
		contentPane.add(button);
		setLocationRelativeTo(null);
		
		ButtonGroup radio = new ButtonGroup();
		radio.add(radio1);
		radio.add(radio2);
	}
	
}
