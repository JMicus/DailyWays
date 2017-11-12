package micus.dailyways.helper;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Settings {
	
	public static int WIDTH, HEIGHT,
					MAX_ZOOM, MIN_ZOOM, ZOOMSLIDER_MARGIN, ZOOMSLIDER_POSX, ZOOMSLIDER_SIZE,
					ZOOMSLIDER_ID, PAN_ID, CAR_ID,
					START_ZOOM,
					PORT;
	
	public static double START_COORD_LAT, START_COORD_LON;
	
	public static boolean SHOW_FIDUCIALS;
	
	public static String GPX_AUTHOR;

	public static void main(String[] args) {
		load();

	}
	
	public static void load() {
		Properties prop = new Properties();
    	InputStream input = null;

    	try {

    		String filename = System.getProperty("user.dir")+"/dailyWaysSettings.properties";
    		//input = Settings.class.getClassLoader().getResourceAsStream(filename);
    		input = new FileInputStream(filename);
    		
    		if(input==null){
    	            System.out.println("Sorry, unable to find " + filename);
    		    return;
    		}

    		//load a properties file from class path, inside static method
    		prop.load(input);

                //get the property value and print it out
                
                WIDTH = Integer.parseInt(prop.get("width").toString());
                HEIGHT = Integer.parseInt(prop.get("height").toString());
                MAX_ZOOM = Integer.parseInt(prop.get("max_zoom").toString());
                MIN_ZOOM = Integer.parseInt(prop.get("min_zoom").toString());
                ZOOMSLIDER_MARGIN = Integer.parseInt(prop.get("zoomslider_margin").toString());
                ZOOMSLIDER_POSX = Integer.parseInt(prop.get("zoomslider_posx").toString());
                ZOOMSLIDER_SIZE = Integer.parseInt(prop.get("zoomslider_size").toString());
                
                START_ZOOM = Integer.parseInt(prop.get("start_zoom").toString());
                START_COORD_LAT = Double.parseDouble(prop.get("start_coordinates_lat").toString());
                START_COORD_LON = Double.parseDouble(prop.get("start_coordinates_lon").toString());
                
                PORT = Integer.parseInt(prop.get("port").toString());
                ZOOMSLIDER_ID = Integer.parseInt(prop.get("zoomslider_id").toString());
                PAN_ID = Integer.parseInt(prop.get("pan_id").toString());
                CAR_ID = Integer.parseInt(prop.get("car_id").toString());
                
                SHOW_FIDUCIALS = Boolean.parseBoolean(prop.get("show_fiducials").toString());
                
                GPX_AUTHOR = prop.get("gpx_author").toString();

    	} catch (IOException ex) {
    		ex.printStackTrace();
        } finally{
        	if(input!=null){
        		try {
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        	}
        }
	}

}
