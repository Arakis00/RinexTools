package rbrown.rinex;

import java.io.File;

public class RinexController {
	Rinex rinex;
	
	/**
	 * Constructor
	 */
	public RinexController(){
		//blank
	}
	
	/**
	 * Calls the getStatistics method of the Rinex class
	 * @param file The file to create a statistic report of.
	 * @param path The location to save the created report.
	 */
	public void getStatistics(File file, String path) {
		rinex = new Rinex(file);
		rinex.createStatistics(path);
	}
	
}
