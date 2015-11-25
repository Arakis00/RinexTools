package rbrown.rinex;

import java.io.File;

public class Rinex {
	File rinFile;
	
	/**
	 * Constructor
	 * @param file The rinex file.
	 */
	public Rinex(File file){
		rinFile = file;
	}
	
	/**
	 * Creates a statistical report for a Rinex file.
	 * @param path The location to save the created report.
	 */
	public void createStatistics(String path) {
		// TODO create statistics file
	}

}
