package main.additions;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

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

/**
 * Uses JFreeChart library to draw XY graph.
 * @author Evaldas
 *
 */
public class Graph extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private final int width = 576;
	private final int height = 500;
	private JTextField textField;
	private JFreeChart chart;
	private XYLineAndShapeRenderer renderer;
	
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
	    setSize(width, height);
		textField = new JTextField();
		textField.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		textField.setBounds(53, 424, 300, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		Button button = new Button("Išsaugoti");
		button.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Printer(textField.getText(), 1000, 1000, chart);
			}
		});
		button.setBounds(379, 422, 164, 22);
		contentPane.add(button);
		
		JPanel panel = new JPanel();
		panel.setBounds(53, 378, 490, 36);
		contentPane.add(panel);
		
		JCheckBox box = new JCheckBox("Skola");
		box.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changeVisibility(0);
			}
		});
		box.setSelected(true);
		box.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		panel.add(box);
		
		JCheckBox box1 = new JCheckBox("Palūkanos");
		box1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changeVisibility(1);
			}
		});
		box1.setSelected(true);
		box1.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		panel.add(box1);
		
		JCheckBox box2 = new JCheckBox("Viso");
		box2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changeVisibility(2);
			}
		});
		box2.setSelected(true);
		box2.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		panel.add(box2);
		
		JCheckBox box3 = new JCheckBox("Liko");
		box3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changeVisibility(3);
			}
		});
		box3.setSelected(true);
		box3.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		panel.add(box3);
	    setLocationRelativeTo(null);
   }

   private void createChart(double[] monthlyLoan, double[] monthlyInterest, double[] monthlyTotal, double[] leftToPay, int month1, int month2) {
	    chart = ChartFactory.createXYLineChart("Paskola", "Mėnuo", "Vertė", 
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
	   // setContentPane(panel);
	    contentPane.add(panel, BorderLayout.WEST);
   }
   
   private XYDataset createDataset(double[] monthlyLoan, double[] monthlyInterest, double[] monthlyTotal, double[] leftToPay, int month1, int month2) {
	  int r = 0;
      XYSeries loan = new XYSeries("Skola");
      XYSeries interest = new XYSeries("Palūkanos");
      XYSeries total = new XYSeries("Viso");
      XYSeries left = new XYSeries("Liko");
      for(int i = month1; i < month2 + 1; i++) {
    	  loan.add(i+1, monthlyLoan[r]);
    	  interest.add(i+1, monthlyInterest[r]);
    	  total.add(i + 1, monthlyTotal[r]);
    	  left.add(i + 1, leftToPay[r]);
    	  r++;
      }
      r = 0;
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
}