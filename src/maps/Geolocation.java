package maps;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

public class Geolocation {
	/**
	 * This function returns true if latitude and longitude are geolocated in Florida; false otherwise.
	 * @param latitude string representation of latitude
	 * @param longitude string representation of longitude
	 * @return returns true if latitude and longitude are geolocated in Florida; false otherwise.
	 */
	public static boolean isFlorida(String latitude, String longitude){
		String state = geoLocate(latitude, longitude);  // call geolocate function
		if(!state.isEmpty() && "FL".equals(state))
			return true;
		else if(!state.isEmpty() && "ZERO_RESULTS".equals(state))
			return false;
		return false;
	}

	/**
	 * This function returns the state for the given coordinates
	 * @param latitude string representation of latitude
	 * @param longitude string representation of longitude
	 * @return the state in which the coordinates belong
	 */
	public static String geoLocate(String latitude, String longitude){
		// create URL string
		String urlStr = "https://maps.googleapis.com/maps/api/geocode/json?key=AIzaSyA6siXOoj7wDMVndw1mY9SlrWFlBBItruw&latlng=" + latitude + "," + longitude +"&sensor=true";
		// making URL request
		try {
			URL url = new URL(urlStr);
			// making connection
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}
			// Reading data's from URL
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			String output;
			String out="";
			while ((output = br.readLine()) != null) {
				out+=output;
			}
			// Converting JSON formatted string into JSON object
			if(out.contains("ZERO_RESULTS"))
				return "ZERO_RESULTS";
			JSONObject json = (JSONObject) JSONSerializer.toJSON(out);  //convert to JSON object
			JSONArray results=json.getJSONArray("results");  //get the JSON value
			JSONObject results_value = results.getJSONObject(0);  // get the JSON object associated with the index 0
			String formatted_address = results_value.getString("formatted_address");  //convert the key to String format
			if("United States".equals(formatted_address))
				return "ZERO_RESULTS";
			String address[] = formatted_address.split(",");  //parse address
			String state = address[address.length-2].trim().split(" ")[0].trim();  // get the state part
			conn.disconnect();
			return state;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * This function finds if the coordinate is on land or water
	 * @param latitude string representation of latitude
	 * @param longitude string representation of longitude
	 * @return land or water according to the elevation value
	 */
	public static String elevation(String latitude, String longitude){
		//generate URL
		String urlStr = "https://maps.googleapis.com/maps/api/elevation/json?locations="+latitude+","+longitude+"&key=AIzaSyCwhVUzmJZAmYwMTDfkCZ4fxvqT7kN6Bmk";
		// making URL request
		try {
			URL url = new URL(urlStr);
			// making connection
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "+ conn.getResponseCode());
			}
			// Reading data's from URL
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			String output;
			String out="";
			while ((output = br.readLine()) != null) {
				out+=output;
			}
			// Converting JSON formatted string into JSON object
			JSONObject json = (JSONObject) JSONSerializer.toJSON(out);  //convert to JSON object
			JSONArray results=json.getJSONArray("results"); //get the JSON value
			JSONObject results_value = results.getJSONObject(0); // get the JSON object associated with the index 0
			String elevation=results_value.getString("elevation"); //get string value of elevation
			// check for elevation value
			if(Double.parseDouble(elevation)<0) {
				return "water";
			}
			else {
				return "land";
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
}