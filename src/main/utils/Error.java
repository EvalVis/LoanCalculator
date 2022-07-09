package main.utils;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


/**
 * 
 * @author Evaldas
 *
 */
public class Error extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	private final int width = 600;
	private final int height = width / 3;
	
	private static String errorM = "";
	
	
	//error codes.
	
	public static final String e1 = "Netinkamai įvesta paskolos suma. Prašome įvesti teigiamą skaičių iki dviejų skaitmenų po kablelio.\n ";
	public static final String e2 = "Įvestas metinis procentas netinkamas. Prašome įvesti teigiamą skaičių iki dviejų skaitmenų po kablelio.\n ";
	public static final String e3 = "Įvestas paskolos terminas netinkamas. Teisinga įvestis: metai [tarpas] mėnesiai.\n ";
	public static final String e4 = "Neįvesta paskolos suma.\n ";
	public static final String e5 = "Neįvestas metinis procentas.\n ";
	public static final String e6 = "Neįvestas paskolos terminas.\n ";
	public static final String e7 = "Nepasirinktas skolos grąžinimo grafikas.\n";
	public static final String e8 = "Klaida bandant sukurti failą.\n";
	public static final String e9 = "Klaida bandant rašyti į failą.\n";
	public static final String e10 = "Klaida bandant išsaugoti į failą.\n";
	public static final String e11 = "Prašome mokėjimo atostogų pabaigos mėnesį.\n";
	public static final String e12 = "Mokėjimo atostogų pabaigos mėnuo turi būti skaičius, nemažesnis už pradžios.\n";
	
	
	/**
	 * If an illegal action happened in the program, the instance of Error is created containing error description.
	 */

	public Error() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(width, height);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel(Misc.setLines(errorM));
		label.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setVerticalAlignment(JLabel.CENTER);
		label.setForeground(Color.RED);
		label.setBounds(10, 11, 563, 149);
		contentPane.add(label);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public static void addError(String error) {
		errorM += error;
	}
	
	public static boolean isEmpty() {
		return errorM.length() == 0;
	}
	
	public static void clear() {
		errorM = "";
	}
}
