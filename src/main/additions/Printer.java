package main.additions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import main.utils.Error;

/**
 * 
 * @author Evaldas
 *
 */
public class Printer {
	
	/**
	 * Used to print data written on the list in Screen class JFrame to a file.
	 * @param path - absolute location of the file.
	 * @param data - String[] to write.
	 */
	
	public Printer(String path, String data[]) {
		Error.clear();
		File file = createFile(path);
		if(file != null) write(path, data);
	}
	
	/**
	 * Used to save the chart.
	 * @param path - absolute location of the file.
	 * @param width - width of image.
	 * @param height - height of image.
	 * @param chart - instance of JFreeChart.
	 */
	
	public Printer(String path, int width, int height, JFreeChart chart) {
		Error.clear();
		File file = createFile(path);
		if(file != null) saveChart(file, width, height, chart);
	}
	
	private void saveChart(File file, int width, int height, JFreeChart chart) {
			try {
				ChartUtilities.saveChartAsPNG(file, chart, width, height);
			} catch (IOException e) {
				e.printStackTrace();
				Error.addError(Error.e10);
				new Error();
			}
	}
	
	private File createFile(String path) {
		File file = new File(path);
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
			Error.addError(Error.e8);
			new Error();
			return null;
		}
		return file;
	}
	
	private void write(String path, String[] data) {
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(path, "UTF-8");
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
			Error.addError(Error.e9);
			new Error();
			return;
		}
		for(int i = 0; i < data.length; i++) writer.write(data[i] + "\n");
		writer.close();
	}

}
