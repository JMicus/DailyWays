package micus.dailyways.fiducialSimulator;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import micus.dailyways.helper.Settings;
import micus.dailyways.view.InputController;

public class FiducialSimulator implements MouseListener, MouseMotionListener, KeyListener {
	
	private static final int fiducialSize = 25;
	
	private InputController controller;
	
	private class Fiducial {
		
		private String name;
		private Point pos;
		boolean onTable;
		
		private Fiducial(String name, Point pos, boolean onTable) {
			this.name = name;
			this.pos = pos;
			this.onTable = onTable;
		}
		
		private boolean isCloseTo(Point p) {
			//System.out.println("(FiducialSimulator.Fiducial.isCloseTo) "+(Math.pow(pos.x-p.x, 2) + Math.pow(pos.y-p.y, 2)));
			if (Math.pow(pos.x-p.x, 2) + Math.pow(pos.y-p.y, 2) < Math.pow(fiducialSize, 2)) {
				return true;
			} else return false;
		}
		
		private int getId() {
			if (name.equals("zoom")) return Settings.ZOOMSLIDER_ID;
			else if (name.equals("pan")) return Settings.PAN_ID;
			else if (name.equals("car")) return Settings.CAR_ID;
			else return -1;
		}
		
		public void paint(Graphics g) {
			g.setColor(Color.black);
			int size = fiducialSize * 2 +1;
			if (onTable) {
				g.fillOval(pos.x-fiducialSize, pos.y-fiducialSize, size, size);
				g.setColor(Color.white);
			}
			else g.drawOval(pos.x-fiducialSize, pos.y-fiducialSize, size, size);
			g.drawString(name, pos.x-10, pos.y+5);
		}
		
	}
	
	private Fiducial[] fiducials;
	private Fiducial fid = null;
	
	public FiducialSimulator(InputController controller) {
		this.controller = controller;
		fiducials = new Fiducial[3];
		fiducials[0] = new Fiducial("zoom", new Point(30, 200), false);
		fiducials[1] = new Fiducial("pan", new Point(300, 100), false);
		fiducials[2] = new Fiducial("car", new Point(400, 100), false);
	}

	@Override
	public void keyPressed(KeyEvent ke) {
		/**/
	}

	@Override
	public void keyReleased(KeyEvent ke) {
		/*if (ke.getKeyCode()==32) {
			space = false;
			if (fid!=null) fid.onTable = space;
		}*/
	}

	@Override
	public void keyTyped(KeyEvent ke) {
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent me) {
		//System.out.println("(FiducialSimulator.mouseMoved) "+fid);
		if (fid!=null) {
			fid.pos = me.getPoint();
			System.out.println("(FiducialSimulator.mouseMoved) "+fid.getId()+fid.pos.x+fid.pos.y);
			if (fid.onTable) controller.fiducialUpdated(fid.getId(), fid.pos.x, fid.pos.y);
			else controller.repaint(); // repaint
		}
	}

	@Override
	public void mouseClicked(MouseEvent me) {
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent me) {
		if (me.getButton()==1) {
			if (fid!=null) {
				//controller.fiducialEnded(fid.getId(), fid.pos.x, fid.pos.y);
				fid = null;
			}
			else {
				Point p = me.getPoint();
				for (int i=0; i<fiducials.length; i++) {
					if (fiducials[i].isCloseTo(p)) {
						fid = fiducials[i];
						//controller.fiducialAdded(fid.getId(), fid.pos.x, fid.pos.y);
						i = 100; // end loop
					}
				}
			}
		}
		if (me.getButton()==3) {
			if (fid!=null) {
				fid.onTable = !fid.onTable;
				if (fid.onTable) controller.fiducialAdded(fid.getId(), fid.pos.x, fid.pos.y);
				else controller.fiducialEnded(fid.getId(), fid.pos.x, fid.pos.y);
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		
	}

	public void paint(Graphics g) {
		for (int i=0; i<fiducials.length; i++) {
			fiducials[i].paint(g);
		}
	}
	
	
}
