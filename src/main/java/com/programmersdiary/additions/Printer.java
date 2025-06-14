package com.programmersdiary.additions;

import com.programmersdiary.utils.InputError;
import org.jfree.chart.JFreeChart;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

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
	
	public Printer(String path, String[] data) {
		InputError.clear();
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
		InputError.clear();
		File file = createFile(path);
		if(file != null) saveChart(file, width, height, chart);
	}
	
	private void saveChart(File file, int width, int height, JFreeChart chart) {
		try {
			BufferedImage image = chart.createBufferedImage(width, height);
			ImageIO.write(image, "png", file);
		} catch (IOException e) {
			e.printStackTrace();
			InputError.addError("er_file_save");
			new InputError();
		}
	}
	
	private File createFile(String path) {
		File file = new File(path);
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
			InputError.addError("er_file_create");
			new InputError();
			return null;
		}
		return file;
	}
	
	private void write(String path, String[] data) {
		PrintWriter writer;
		try {
			writer = new PrintWriter(path, StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
			InputError.addError("er_file_write");
			new InputError();
			return;
		}
        for (String datum : data) writer.write(datum + "\n");
		writer.close();
	}

}
