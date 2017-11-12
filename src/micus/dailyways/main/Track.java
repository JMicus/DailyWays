package micus.dailyways.main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.util.Iterator;
import java.util.LinkedList;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.Layer;
import org.openstreetmap.gui.jmapviewer.MapObjectImpl;
import org.openstreetmap.gui.jmapviewer.Style;
import org.openstreetmap.gui.jmapviewer.interfaces.MapObject;

public class Track {
	
	/*private class Marker {
		Coordinate coord;
		public Marker(Coordinate coord) {
			this.coord = coord;
		}
	}
	
	private class StartMarker extends Marker {
		
	}*/
	
	//private Marker start, end;
	
	private JMapViewer map;
	
	private LinkedList<Coordinate> waypoints;
	
	private boolean finished = false;
	
	public Track(Coordinate start) {
		waypoints = new LinkedList<Coordinate>();
		waypoints.add(start);
	}
	
	public void addWayPoint(Coordinate coord) {
		waypoints.add(coord);
	}

	public void finish(Coordinate coord) {
		addWayPoint(coord);
		finished = true;
	}

	public void paint(Graphics g, JMapViewer map) {
		Graphics2D g2d = (Graphics2D)g;
		g2d.setStroke(new BasicStroke(5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2d.setColor(Color.blue);
		Iterator<Coordinate> it = waypoints.iterator();
		Point lastPos;
		
		// startpoint
		lastPos = map.getMapPosition(it.next());
		paintStartEndPoint(g2d, lastPos, "Start");
		
		// track
		while (it.hasNext()) {
			Point pos = map.getMapPosition(it.next());
			if (pos!=null && lastPos!=null) {
				//g.fillOval(pos.x-5, pos.y-5, 11, 11);
				g2d.drawLine(pos.x, pos.y, lastPos.x, lastPos.y);
			}
			lastPos = pos;
		}
		
		// endpoint
		paintStartEndPoint(g2d, lastPos, "End");
	}
	
	private void paintStartEndPoint(Graphics2D g2d, Point p, String label) {
		if (p!=null) {
			int size = 10;
			g2d.fillOval(p.x-size, p.y-size, 2*size+1, 2*size+1);
			g2d.drawString(label, p.x, p.y);
		}
	}
	
	public Iterator<Coordinate> getIterator() {return waypoints.iterator();}

}
