package micus.dailyways.helper;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import micus.dailyways.main.Track;

public class GPXCreator {

	public static void write(Track track) {

	  try {
		  
		Date now = new Date();
		
		SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss");
		SimpleDateFormat sdfTimeFile = new SimpleDateFormat("HH_mm_ss");

		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

		// gpx
		Document doc = docBuilder.newDocument();
		Element gpx = doc.createElement("gpx");
		doc.appendChild(gpx);
		gpx.setAttribute("version", "1.1");
		gpx.setAttribute("creator", Settings.GPX_AUTHOR);
		
		// metadata
		Element meta = doc.createElement("metadata");
		gpx.appendChild(meta);
		meta.setAttribute("time", String.format(sdfDay.format(now)+"T"+sdfDay.format(now)+"Z"));
		
		// track + trackseq
		Element trk = doc.createElement("trk");
		gpx.appendChild(trk);
		Element seq = doc.createElement("trkseg");
		trk.appendChild(seq);
		
		// waypoints
		Iterator<Coordinate> it = track.getIterator();
		while (it.hasNext()) {
			Coordinate coord = it.next();
			Element point = doc.createElement("trkpt");
			point.setAttribute("lat", coord.getLat()+"");
			point.setAttribute("lon", coord.getLon()+"");
			seq.appendChild(point);
			
			
		}

		// write the content into xml file
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		
		File path = new File(System.getProperty("user.dir")+"/data/tracks");
		if (!path.exists()) path.mkdirs();
		
		String filename = sdfDay.format(now)+"_"+sdfTimeFile.format(now)+".gpx";
		
		StreamResult result = new StreamResult(new File(path+"/"+filename));

		// Output to console for testing
		// StreamResult result = new StreamResult(System.out);

		transformer.transform(source, result);

		System.out.println("File saved!\n"+filename);

	  } catch (ParserConfigurationException pce) {
		pce.printStackTrace();
	  } catch (TransformerException tfe) {
		tfe.printStackTrace();
	  }
	}


	public static void main(String[] args) {
		Settings.load();
		Track track = new Track(new Coordinate(1,1));
		track.addWayPoint(new Coordinate(2,2));
		track.addWayPoint(new Coordinate(3,3));
		track.addWayPoint(new Coordinate(4,4));
		track.finish(new Coordinate(5,5));
		write(track);
	}

}
