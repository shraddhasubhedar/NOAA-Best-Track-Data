package report;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Report {
	BufferedWriter writer;
	/**
	 * This constructor creates an output file and writes the header to it.
	 * @throws IOException if an I/O exception occurs
	 */
	public Report() throws IOException{
		 writer = new BufferedWriter(new FileWriter("output.txt",true));  // create file
		 writer.write("Name,Date of landfall,Max wind speed");  // write header
		 writer.newLine();
	}
		
	/**
	 * This function writes the appropriate values to the output file
	 * @param name name of hurricane
	 * @param dateOfLandfall date of landfall in Florida
	 * @param maxWindSpeed maximum wind speed
	 * @throws IOException if an I/O exception occurs
	 */
	public void addDataLine(String name, String dateOfLandfall, String maxWindSpeed) throws IOException{
		writer.write(name+","+dateOfLandfall+","+maxWindSpeed); // write line
		writer.newLine();
	}
	
	/**
	 * This function closes the file
	 * @throws IOException if an I/O exception occurs
	 */
	public void close() throws IOException {
		writer.close(); //close file
	}
}