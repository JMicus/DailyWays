package micus.dailyways.view;

import java.awt.Point;
import java.util.Date;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.interfaces.ICoordinate;

import TUIO.TuioBlob;
import TUIO.TuioClient;
import TUIO.TuioCursor;
import TUIO.TuioListener;
import TUIO.TuioObject;
import TUIO.TuioTime;
import micus.dailyways.helper.Settings;
import micus.dailyways.main.Model;

public class InputController implements TuioListener {
	
	//private MainFrame frame;
	//private JMapViewer map;
	//private MapOverlay overlay;
	private Model model;
	
	// pan
	private Coordinate panCoord;
	private Point panMapPoint;
	private long lastPanUpdate;
	
	public InputController(Model model/*, JMapViewer map, MapOverlay overlay*/) {
		//this.frame = frame;
		//this.map = map;
		//this.overlay = overlay;
		this.model = model;
		
		TuioClient client = new TuioClient(Settings.PORT);
		client.addTuioListener(this);
		client.connect();
	}

	
	@Override
	public void addTuioObject(TuioObject to) {
		int fID = to.getSymbolID();
		float x = to.getX();
		float y = to.getY();
		System.out.println(new Date().getTime()+" added: "+fID+" at "+x+":"+y);
		
		//Point mapPoint = new Point((int)x,(int)y);
		/*Point mapPoint = changeInput(x,y);
		
		//if (mapPoint!=null) updateTuioObject(to);
		
		model.showFiducial(fID, mapPoint);
		
		if (fID == Settings.ZOOMSLIDER_ID) {
			model.getMap().setZoom(zoomLevel(mapPoint.y));
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
		
		repaint();*/
	}
	
	@Override
	public void updateTuioObject(TuioObject to) {
		int fID = to.getSymbolID();
		float x = to.getX();
		float y = to.getY();
		System.out.println(new Date().getTime()+" updated: "+fID+" at "+x+":"+y);
		
		//Point mapPoint = new Point((int)x,(int)y);
		Point mapPoint = changeInput(x,y);
		
		model.showFiducial(fID, mapPoint);
		
		if (fID == Settings.ZOOMSLIDER_ID) {
			int zoomOld = model.getMap().getZoom();
			int zoomNew = zoomLevel(mapPoint.y);
			model.getMap().setZoom(zoomNew);
			if (panMapPoint!=null) panCoord = model.getMap().getPosition(panMapPoint);
			if (zoomOld==zoomNew) repaint(); // otherwise it is repainted already
		}
		else if (fID == Settings.PAN_ID) {
			panMapPoint = mapPoint;
			long now = new Date().getTime();
			double distance = Double.MAX_VALUE;
			if (panCoord!=null) distance = mapPoint.distance(model.getMap().getMapPosition(panCoord));
			//System.out.println("(InputController.updateTuioObject) pan last update: "+(now-lastPanUpdate)+", distance: "+distance);
			if (now-lastPanUpdate>4000 || distance>200) {
				panCoord = model.getMap().getPosition(mapPoint);
				lastPanUpdate = now;
			}
			else model.getMap().setDisplayPosition(panMapPoint, panCoord, model.getMap().getZoom());
		}
		else if (fID == Settings.CAR_ID) {
			model.updateVehicle(model.getMap().getPosition(mapPoint));
		}
		
		repaint();
	}
	
	@Override
	public void removeTuioObject(TuioObject to) {
		int fID = to.getSymbolID();
		float x = to.getX();
		float y = to.getY();
		System.out.println(new Date().getTime()+" removed: "+fID+" at "+x+":"+y);
		
		model.showFiducial(fID, null);
		
		Point mapPoint = changeInput(x,y);
		
		if (fID == Settings.PAN_ID) {
			//panMapPoint = null;
			//panCoord = null;
		}
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
		model.paint();
	}
	
	
	private Point changeInput(float x, float y) {
		if (Settings.PORTRAIT) {
			float xTemp = x;
			x = (1-y)*Settings.WIDTH;
			y = xTemp*Settings.HEIGHT;
		}
		else {
			x *= Settings.WIDTH;
			y *= Settings.HEIGHT;
		}
		Point p = new Point((int)x,(int)y);
		return p;
	}


	@Override
	public void addTuioBlob(TuioBlob arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void addTuioCursor(TuioCursor arg0) {
		// TODO Auto-generated method stub
		
	}




	@Override
	public void refresh(TuioTime arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void removeTuioBlob(TuioBlob arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void removeTuioCursor(TuioCursor arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void updateTuioBlob(TuioBlob arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void updateTuioCursor(TuioCursor arg0) {
		// TODO Auto-generated method stub
		
	}



}
