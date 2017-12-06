package micus.dailyways.main;

import java.awt.Point;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;

import micus.dailyways.helper.GPXCreator;
import micus.dailyways.helper.Settings;
import micus.dailyways.io.ControlView;
import micus.dailyways.io.MainFrame;

public class Model {
	
	private MainFrame frame;
	private ControlView controlView;
	//private JMapViewer map;
	//private MapOverlay overlay;
	private Track track;
	
	public Model(MainFrame frame) {
		this.frame = frame;
		controlView = new ControlView(this);
		//this.map = map;
		//overlay = new MapOverlay();
		//map.setOverlay(overlay);
		
		
		frame.map().setDisplayPosition(new Coordinate(Settings.START_COORD_LAT, Settings.START_COORD_LON), Settings.START_ZOOM);
		
		//frame.paintOverlay();
		
	}
	
	/*public void addVehicle(Coordinate coord) {
		if (track==null) {
			track = new Track(coord);
			frame.getOverlay().setTrack(track);
			//map.add
		}
		else track.addWayPoint(coord);
		//map.repaint();
		paint();
	}*/
	
	public void updateVehicle(Coordinate coord) {
		if (track==null) {
			track = new Track(coord);
			frame.getOverlay().setTrack(track);
		}
		else {
			track.addWayPoint(coord);
			paint();
		}
	}
	
	public void endVehicle(Coordinate coord) {
		track.finish(coord);
		GPXCreator.write(track);
	}
	
	/*public JMapViewer getMap() {
		return map;
	}*/
	
	public void showFiducial(int id, Point p) {
		frame.getOverlay().setFiducial(id, p);
	}
	
	public void paint() {
		//map.repaint();
		frame.getOverlay().repaint();
		//if (track!=null) track.paint(g, map);
	}
	
	public MainFrame getFrame() {return frame;}
	public JMapViewer getMap() {return frame.map();}


}
