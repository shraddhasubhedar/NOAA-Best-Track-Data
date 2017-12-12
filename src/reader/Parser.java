package reader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

import maps.Geolocation;
import report.Report;
import report.Map;

public class Parser {
	public static final String inputFile = "hurdat2.txt";
	// bounding box for Florida
	public static final double minLat = 24.396308;
	public static final double maxLat = 31.000968;
	public static final double maxLong = -79.974306;
	public static final double minLong = -87.634896;

	/**
	 * This function reads and parses the file and writes the information for the first land coordinate in Florida to the report file.
	 * @throws IOException if an I/O error occurs
	 * @throws ParseException if a parsing error occurs
	 */
	public void parseFile() throws ParseException, IOException{
		Scanner file= new Scanner(new File(inputFile));
		// create map and report files
		Map map = new Map();
		Report report = new Report();
		System.out.println("Writing to file...");
		// read file line by line
		while(file.hasNext()) {
			String line = file.nextLine();
			// here, we are only considering hurricanes since 1900
			if(line.startsWith("AL") && (line.substring(4).startsWith("19") || line.substring(4).startsWith("20"))) {
				int counter = 0;
				String header[] = line.split(",");
				int numReadings = Integer.parseInt(header[header.length-1].trim());
				// loop for the number of readings for each hurricane
				while(counter < numReadings){
					String dataLine = file.nextLine();
					String dataLineSplit[] = dataLine.split(",");
					// get latitude, longitude and date in correct format
					double latitude = getLatitude(dataLineSplit[4].trim());
					double longitude = getLongitude(dataLineSplit[5].trim());
					String date= getDate(dataLineSplit[0].trim());
					// check if coordinate is within the bounding box of Florida
					if(latitude > minLat && latitude < maxLat && longitude > minLong && longitude < maxLong) {
						if((latitude > 29) || (longitude > -83)){  // Further reduction for Florida's coordinate
							// call reverse geocoding function
							if(Geolocation.isFlorida(Double.toString(latitude), Double.toString(longitude))){
								// check if elevation function returns land coordinate 
								if(Geolocation.elevation(Double.toString(latitude), Double.toString(longitude))=="land") {
									// write to file
									report.addDataLine(header[1].trim(), date, dataLineSplit[6].trim());
									map.addLine(header[1].trim(),date,dataLineSplit[6].trim(),Double.toString(latitude), Double.toString(longitude));
									break;
								}
							}
						}
					}
					counter++;  // increment count
				}
			}
		}	
		// close files 
		report.close();
		map.close();
		file.close();
		System.out.println("Finished writing to file");
	}
	
	/**
	 * This function takes a string and returns the formatted latitude for the same
	 * @param value string that is to be formatted
	 * @return double representation of the latitude
	 */
	public double getLatitude(String value) {
		// if the last char is N then do not append a negative sign, else append it
		if(value.charAt(value.length() - 1) == 'N') {
			return Double.parseDouble(value.substring(0, value.length()-1));
		}
		else {
			return Double.parseDouble("-" + value.substring(0, value.length()-1));
		}
	}
	/**
	 * This function takes a string and returns the formatted longitude for the same
	 * @param value string that is to be formatted
	 * @return double representation of the longitude
	 */
	public double getLongitude(String value) {
		// if the last char is E then do not append a negative sign, else append it
		if(value.charAt(value.length() - 1) == 'E') {
			return Double.parseDouble(value.substring(0, value.length()-1));
		}
		else {
			return Double.parseDouble("-" + value.substring(0, value.length()-1));
		}
	}
	
	/**
	 * This function takes a date in yyyymmdd format and returns it in the mm/dd/yyyy format
	 * @param value string that is to be formatted
	 * @return string value of the date
	 * @throws ParseException if a parsing error occurs
	 */
	public String getDate(String value) throws ParseException  {
		return new SimpleDateFormat("mm/dd/yyyy").format(new SimpleDateFormat("yyyymmdd").parse(value));
	}
	
	public static void main(String args[]) throws FileNotFoundException{
		Parser parser = new Parser();
		try{
			parser.parseFile();
		}
		catch(Exception e){
			System.out.println("ERROR");
			e.printStackTrace();
		}
	}
}