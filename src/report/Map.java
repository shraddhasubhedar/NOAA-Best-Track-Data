package report;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Map {
	BufferedWriter w;
	/**
	 * This constructor creates an output file 
	 * @throws IOException if an I/O exception occurs
	 */
	public Map() throws IOException{
		w = new BufferedWriter(new FileWriter("latlong.txt",true));
	}
	/**
	 * This function writes the appropriate values to the output file
	 * @param name name of hurricane
	 * @param date date of landfall in Florida
	 * @param speed max wind speed
	 * @param latitude latitude coordinate
	 * @param longitude longitude coordinate
	 * @throws IOException if an I/O exception occurs
	 */
	public void addLine(String name, String date, String speed,String latitude, String longitude) throws IOException {
		w.write("['"+name+","+date+","+speed+"',"+latitude+","+longitude+"],"); // write to file
		w.newLine();
	}
	
	/**
	 * This function closes the file
	 * @throws IOException if an I/O exception occurs
	 */
	public void close() throws IOException {
		w.close();
	}
}
