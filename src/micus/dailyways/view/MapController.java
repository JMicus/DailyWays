package micus.dailyways.view;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapController;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapMarkerCircle;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.MapPolygonImpl;
import org.openstreetmap.gui.jmapviewer.interfaces.ICoordinate;
import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;

public class MapController extends JMapController implements MouseListener, MouseMotionListener, ActionListener {

	private JMapViewer map;
	
	//private boolean makeTrack = false;
	private Coordinate lastTrackPoint = null;
	
	public MapController(JMapViewer map) {
		super(map);
		this.map = map;
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (lastTrackPoint!=null) {
			Coordinate coord = map.getPosition(e.getX(), e.getY());
			//map.addMapMarker(new MapMarkerDot(coord.getLat(), coord.getLon()));
			MapPolygonImpl polygon = new MapPolygonImpl(coord, coord, lastTrackPoint);
			map.addMapPolygon(polygon);
			lastTrackPoint = coord;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getButton()==2) {
			if (lastTrackPoint==null) {
				lastTrackPoint = map.getPosition(e.getX(), e.getY());
				MapMarker marker = new MapMarkerDot(lastTrackPoint);
				map.addMapMarker(marker);
			}
			else {
				lastTrackPoint = map.getPosition(e.getX(), e.getY());
				MapMarker marker = new MapMarkerDot(lastTrackPoint);
				map.addMapMarker(marker);
				lastTrackPoint = null;
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("test")) {
			Point p = new Point(map.getMapPosition(51.316342, 9.492947));
			//map.setZoom(p);
			System.out.println("(MapController.actionPerformed) "+map.getZoom());
		}
	}

	

}
