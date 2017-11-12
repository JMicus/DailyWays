package micus.dailyways.main;

import java.awt.Graphics;
import java.awt.Point;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;

import micus.dailyways.fiducialSimulator.FiducialSimulator;
import micus.dailyways.helper.GPXCreator;
import micus.dailyways.helper.Settings;
import micus.dailyways.view.MainFrame;
import micus.dailyways.view.MapOverlay;

public class Model {
	
	//private MainFrame frame;
	private JMapViewer map;
	private MapOverlay overlay;
	private Track track;
	
	private FiducialSimulator simulator;
	
	public Model(JMapViewer map) {
		//this.frame = frame;
		this.map = map;
		map.setModel(this);
		overlay = new MapOverlay(this);
		//map.setOverlay(overlay);
		
		
		this.map.setDisplayPosition(new Coordinate(Settings.START_COORD_LAT, Settings.START_COORD_LON), Settings.START_ZOOM);
	}
	
	public void addVehicle(Coordinate coord) {
		if (track==null) {
			track = new Track(coord);
			//map.add
		}
		else track.addWayPoint(coord);
		map.repaint();
	}
	
	public void updateVehicle(Coordinate coord) {
		if (track!=null) {
			track.addWayPoint(coord);
			map.repaint();
		}
		else System.out.println("(Model.updadeVehicle) ERROR: There is no track!");
	}
	
	public void endVehicle(Coordinate coord) {
		track.finish(coord);
		GPXCreator.write(track);
	}
	
	public JMapViewer getMap() {
		return map;
	}
	
	public void showFiducial(int id, Point p) {
		overlay.setFiducial(id, p);
	}
	
	public void paint(Graphics g) {
		//map.repaint();
		overlay.paint(g);
		if (simulator != null) simulator.paint(g);
		if (track!=null) track.paint(g, map);
	}
	
	public void setSimulator(FiducialSimulator simulator) {
		this.simulator = simulator;
	}

}
