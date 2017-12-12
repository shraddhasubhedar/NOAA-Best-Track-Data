package reader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;

import java.util.Scanner;

public class LatLong {
	public static final String inputFile = "hurdat2.txt";
	public static final double minLat = 24.396308;
	public static final double maxLat = 31.000968;
	public static final double maxLong = -79.974306;
	public static final double minLong = -87.634896;

	public void parseFile() throws IOException, ParseException{
		Scanner file= new Scanner(new File(inputFile));

		while(file.hasNext()) {
			String line = file.nextLine();

			if(line.startsWith("AL171995")) {
				int counter = 0;
				String header[] = line.split(",");
				int numReadings = Integer.parseInt(header[header.length-1].trim());
				while(counter < numReadings){
					String dataLine = file.nextLine();
					String dataLineSplit[] = dataLine.split(",");

					double latitude = 0;
					if(dataLineSplit[4].trim().charAt(dataLineSplit[4].trim().length() - 1) == 'N') {
						latitude = Double.parseDouble(dataLineSplit[4].trim().substring(0, dataLineSplit[4].trim().length()-1));
					}
					else {
						latitude = Double.parseDouble("-" + dataLineSplit[4].trim().substring(0, dataLineSplit[4].trim().length()-1));
					}
					double longitude = 0;
					if(dataLineSplit[5].trim().charAt(dataLineSplit[5].trim().length() - 1) == 'E') {
						longitude = Double.parseDouble(dataLineSplit[5].trim().substring(0, dataLineSplit[5].trim().length()-1));
					}
					else {
						longitude = Double.parseDouble("-" + dataLineSplit[5].trim().substring(0, dataLineSplit[5].trim().length()-1));
					}
					System.out.println(latitude+","+longitude);
					counter++;
				}
			}
		}	
		file.close();
	}
	public static void main(String args[]) throws FileNotFoundException{
		LatLong parser = new LatLong();
		try{
			parser.parseFile();
		}
		catch(Exception e){
			System.out.println("ERROR");
			e.printStackTrace();
		}
	}
}
