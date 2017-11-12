package micus.dailyways.view;

import java.awt.Point;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.interfaces.ICoordinate;

import micus.dailyways.helper.Settings;
import micus.dailyways.main.Model;

public class InputController {
	
	//private MainFrame frame;
	//private JMapViewer map;
	//private MapOverlay overlay;
	private Model model;
	
	private Coordinate panCoord;
	private Point panMapPoint;
	
	public InputController(Model model/*, JMapViewer map, MapOverlay overlay*/) {
		//this.frame = frame;
		//this.map = map;
		//this.overlay = overlay;
		this.model = model;
	}

	
	public void fiducialAdded(int fID, float x, float y) {
		System.out.println("added: "+fID+" at "+x+":"+y);
		
		//Point mapPoint = new Point((int)x,(int)y);
		Point mapPoint = changeInput(x,y);
		
		model.showFiducial(fID, mapPoint);
		
		if (fID == Settings.ZOOMSLIDER_ID) {
			model.getMap().setZoom(zoomLevel(y));
		}
		else if (fID == Settings.PAN_ID) {
			panMapPoint = mapPoint;
			panCoord = model.getMap().getPosition(mapPoint);
			model.getMap().setDisplayPosition(panMapPoint, panCoord, model.getMap().getZoom());
			System.out.println("(InputController.fiducialAdded) pan: "+panMapPoint+" - "+panCoord);
		}
		else if (fID == Settings.CAR_ID) {
			model.addVehicle(model.getMap().getPosition(mapPoint));
		}
	}
	
	public void fiducialUpdated(int fID, float x, float y) {
		System.out.println("updated: "+fID+" at "+x+":"+y);
		
		//Point mapPoint = new Point((int)x,(int)y);
		Point mapPoint = changeInput(x,y);
		
		model.showFiducial(fID, mapPoint);
		
		if (fID == Settings.ZOOMSLIDER_ID) {
			int zoomOld = model.getMap().getZoom();
			int zoomNew = zoomLevel(y);
			model.getMap().setZoom(zoomNew);
			if (panMapPoint!=null) panCoord = model.getMap().getPosition(panMapPoint);
			if (zoomOld==zoomNew) repaint(); // otherwise it is repainted already
		}
		else if (fID == Settings.PAN_ID) {
			panMapPoint = mapPoint;
			model.getMap().setDisplayPosition(panMapPoint, panCoord, model.getMap().getZoom());
		}
		else if (fID == Settings.CAR_ID) {
			model.updateVehicle(model.getMap().getPosition(mapPoint));
		}
	}
	
	public void fiducialEnded(int fID, float x, float y) {
		System.out.println("ended: "+fID+" at "+x+":"+y);
		
		model.showFiducial(fID, null);
		
		Point mapPoint = changeInput(x,y);
		
		if (fID == Settings.PAN_ID) panMapPoint = null;
		else if (fID == Settings.CAR_ID) {
			model.endVehicle(model.getMap().getPosition(mapPoint));
		}
		
		repaint();
	}
	
	
	private int zoomLevel(float y) {
		//System.out.println("(InputController.zoomLevel) y: "+y);
		float level = 0.5f + Settings.MAX_ZOOM - (y-Settings.ZOOMSLIDER_MARGIN) * (Settings.MAX_ZOOM-Settings.MIN_ZOOM) / (Settings.HEIGHT-2*Settings.ZOOMSLIDER_MARGIN);
		//System.out.println("(InputController.zoomLevel) l: "+level);
		level = Math.max(Settings.MIN_ZOOM, level);
		level = Math.min(Settings.MAX_ZOOM, level);
		//System.out.println("(InputController.zoomLevel) l: "+level);
		return (int)level;
	}


	public void repaint() {
		model.getMap().repaint();
	}
	
	
	private Point changeInput(float x, float y) {
		Point p = new Point((int)-y,(int)-x);
		return p;
	}

}
