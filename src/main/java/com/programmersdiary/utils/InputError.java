package com.programmersdiary.utils;

import com.programmersdiary.Loan;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Font;
import java.io.Serial;
import java.util.ArrayList;
import java.util.List;


/**
 * 
 * @author Evaldas
 *
 */
public class InputError extends JFrame {

	@Serial
	private static final long serialVersionUID = 1L;

    private static final List<String> errorKeys = new ArrayList<>();
	
	/**
	 * If an illegal action happened in the program, the instance of Error is created containing error description.
	 */

	public InputError() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        int width = 600;
        int height = width / 3;
        setSize(width, height);
        JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		StringBuilder sb = new StringBuilder();
		for (String key : errorKeys) {
			sb
			.append(Loan.messages.getString(key))
			.append(System.lineSeparator());
		}
		JLabel label = new JLabel(Misc.setLines(sb.toString()));
		label.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setVerticalAlignment(JLabel.CENTER);
		label.setForeground(Color.RED);
		label.setBounds(10, 11, 563, 149);
		contentPane.add(label);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public static void addError(String errorKey) {
		errorKeys.add(errorKey);
	}
	
	public static boolean isEmpty() {
		return errorKeys.isEmpty();
	}
	
	public static void clear() {
		errorKeys.clear();
	}
}
