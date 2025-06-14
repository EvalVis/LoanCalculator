package com.programmersdiary.additions;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RefineryUtilities;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.io.Serial;
import com.programmersdiary.Loan;
import com.programmersdiary.additions.Printer;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Uses JFreeChart library to draw XY graph.
 * @author Evaldas
 *
 */
public class Graph extends JFrame {

	@Serial
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
    private JTextField textField;
	private JFreeChart chart;
	private XYLineAndShapeRenderer renderer;
	private JRadioButton ltRadio;
	private JRadioButton enRadio;
	private Button button;
	private JCheckBox box, box1, box2, box3;
	
	/**
	 * 
	 * @param month1 - start of the x.
	 * @param month2 - end of the x.
	 */
	
public Graph(double[] monthlyLoan, double[] monthlyInterest, double[] monthlyTotal, double[] leftToPay, int month1, int month2) {
	createContentPane();
	setFrame();
	createChart(monthlyLoan, monthlyInterest, monthlyTotal, leftToPay, month1, month2);
   }

   private void createContentPane() {
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
   }

   private void setFrame() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    setVisible(true);
	    RefineryUtilities.centerFrameOnScreen(this);
       int height = 530;
       int width = 576;
       setSize(width, height);
		textField = new JTextField();
		textField.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		textField.setBounds(53, 454, 300, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
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
		
		button = new Button(Loan.messages.getString("save"));
		button.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		button.addActionListener(e -> new Printer(textField.getText(), 1000, 1000, chart));
		button.setBounds(379, 452, 164, 22);
		contentPane.add(button);
		
		JPanel panel = new JPanel();
		panel.setBounds(53, 408, 490, 36);
		contentPane.add(panel);
		
		box = new JCheckBox(Loan.messages.getString("principal"));
		box.addActionListener(e -> changeVisibility(0));
		box.setSelected(true);
		box.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		panel.add(box);
		
		box1 = new JCheckBox(Loan.messages.getString("interest"));
		box1.addActionListener(e -> changeVisibility(1));
		box1.setSelected(true);
		box1.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		panel.add(box1);
		
		box2 = new JCheckBox(Loan.messages.getString("total"));
		box2.addActionListener(e -> changeVisibility(2));
		box2.setSelected(true);
		box2.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		panel.add(box2);
		
		box3 = new JCheckBox(Loan.messages.getString("left"));
		box3.addActionListener(e -> changeVisibility(3));
		box3.setSelected(true);
		box3.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		panel.add(box3);
	    setLocationRelativeTo(null);
   }

   private void createChart(double[] monthlyLoan, double[] monthlyInterest, double[] monthlyTotal, double[] leftToPay, int month1, int month2) {
	    chart = ChartFactory.createXYLineChart(Loan.messages.getString("loan_calculator"), Loan.messages.getString("month"), Loan.messages.getString("total"), 
	    		createDataset(monthlyLoan, monthlyInterest, monthlyTotal, leftToPay, month1, month2), 
	    		PlotOrientation.VERTICAL, true, true, false);     
	    ChartPanel panel = new ChartPanel(chart);
	    panel.setSize(560, 367);
	    XYPlot plot = chart.getXYPlot();
	    renderer = new XYLineAndShapeRenderer();
	    renderer.setSeriesPaint(0, Color.RED);
	    renderer.setSeriesPaint(1, Color.GREEN);
	    renderer.setSeriesPaint(2, Color.BLUE);
	    renderer.setSeriesPaint(3, Color.ORANGE);
	    renderer.setSeriesStroke(0, new BasicStroke(4.0f));
	    renderer.setSeriesStroke(1, new BasicStroke(3.0f));
	    renderer.setSeriesStroke(2, new BasicStroke(2.0f));
	    renderer.setSeriesStroke(3, new BasicStroke(1.0f));
	    plot.setRenderer(renderer);
	    renderer.setSeriesVisible(0, true);
	    renderer.setSeriesVisible(1, true);
	    renderer.setSeriesVisible(2, true);
	    renderer.setSeriesVisible(3, true);
	    contentPane.add(panel, BorderLayout.WEST);
   }
   
   private XYDataset createDataset(double[] monthlyLoan, double[] monthlyInterest, double[] monthlyTotal, double[] leftToPay, int month1, int month2) {
      XYSeries loan = new XYSeries(Loan.messages.getString("principal"));
      XYSeries interest = new XYSeries(Loan.messages.getString("interest"));
      XYSeries total = new XYSeries(Loan.messages.getString("total"));
      XYSeries left = new XYSeries(Loan.messages.getString("left"));
      for(int i = month1; i < month2 + 1; i++) {
    	  loan.add(i+1, monthlyLoan[i]);
    	  interest.add(i+1, monthlyInterest[i]);
    	  total.add(i + 1, monthlyTotal[i]);
    	  left.add(i + 1, leftToPay[i]);
      }
       XYSeriesCollection dataset = new XYSeriesCollection();
      dataset.addSeries(loan);          
      dataset.addSeries(interest);
      dataset.addSeries(total);
      dataset.addSeries(left);
      return dataset;
   }
   
   /**
    * turn off or on a specific sequence.
    */
   
   private void changeVisibility(int number) {
	   renderer.setSeriesVisible(number, !renderer.getSeriesVisible(number));
   }

   private void updateTexts() {
	   button.setLabel(Loan.messages.getString("save"));
	   box.setText(Loan.messages.getString("principal"));
	   box1.setText(Loan.messages.getString("interest"));
	   box2.setText(Loan.messages.getString("total"));
	   box3.setText(Loan.messages.getString("left"));
	   
	   // Update chart title and axis labels
	   chart.setTitle(Loan.messages.getString("loan_calculator"));
	   chart.getXYPlot().getDomainAxis().setLabel(Loan.messages.getString("month"));
	   chart.getXYPlot().getRangeAxis().setLabel(Loan.messages.getString("total"));
   }
}