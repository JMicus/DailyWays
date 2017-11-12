package micus.dailyways.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;

import micus.dailyways.helper.Settings;
import micus.dailyways.main.Model;

public class MapOverlay {
	
	
	private Model model;
	
	private Point zoomFiducial, panFiducial;
	
	//private int zoomLevel = 0;
	
	public MapOverlay(Model model) {
		this.model = model;
	}
	
	
	
	public void paint(Graphics g) {
		paintZoomSlider(g);
		
		paintFiducial(g, zoomFiducial, Color.YELLOW, "zoom slider");
		paintFiducial(g, panFiducial, Color.ORANGE, "pan");
	}
	
	public void paintFiducial(Graphics g, Point p, Color c, String label) {
		if (Settings.SHOW_FIDUCIALS && p!=null) {
			g.setColor(c);
			int rad = 20;
			g.fillOval(p.x-rad, p.y-rad, 2*rad+1, 2*rad+1);
			g.setColor(Color.black);
			g.drawString(label, p.x, p.y);
		}
	}
	
	public void paintZoomSlider(Graphics graphics) {
		int symbolDistance = 20;
		
		int maxThickness = 9; // odd value
		
		Color colorB = Color.GRAY;
		Color colorA = Color.black;
		
		int posX = Settings.ZOOMSLIDER_POSX;
		int width = Settings.ZOOMSLIDER_SIZE;
		int margin = Settings.ZOOMSLIDER_MARGIN;
		if (width % 2 != 0) width += 1; // odd value
		int symbolPadding = (int)(width*0.75f); // < width / 2
		
		
		Graphics2D g = (Graphics2D)graphics;
		g.setStroke(new BasicStroke(width/5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		// + and - symbol
		g.setColor(colorB);
		g.fillOval(posX, margin-width-symbolDistance, width, width);
		g.fillOval(posX, Settings.HEIGHT-margin+symbolDistance, width, width);
		g.setColor(colorA);
		int y = margin-width/2-symbolDistance;
		g.drawLine(posX+symbolPadding, y, posX+width-symbolPadding, y);
		int x = posX+width/2;
		g.drawLine(x, margin-symbolDistance-width+symbolPadding, x, margin-symbolDistance-symbolPadding);
		y = Settings.HEIGHT-margin+width/2+symbolDistance;
		g.drawLine(posX+symbolPadding, y, posX+width-symbolPadding, y);
		
		// slider
		float levelDistance = (Settings.HEIGHT-2*margin) / (float)(Settings.MAX_ZOOM-Settings.MIN_ZOOM);
		g.setStroke(new BasicStroke(Math.min(maxThickness, levelDistance/5), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		g.setColor(colorB);
		x = posX+width/2;
		g.drawLine(x, margin, x, Settings.HEIGHT-margin);
		for (int i=Settings.MIN_ZOOM; i<=Settings.MAX_ZOOM; i++) {
			y = Settings.HEIGHT-margin - (int)((i-Settings.MIN_ZOOM) * levelDistance);
			if (i==model.getMap().getZoom()) g.setColor(colorA);
			else g.setColor(colorB);
			g.drawLine(posX, y, posX+width, y);
		}
	}
	
	public void setFiducial(int id, Point p) {
		if (id==Settings.ZOOMSLIDER_ID) zoomFiducial = p;
		else if (id==Settings.PAN_ID) panFiducial = p;
	}

}
