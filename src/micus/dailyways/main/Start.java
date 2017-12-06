package micus.dailyways.main;

import micus.dailyways.fiducialSimulator.FiducialSimulator;
import micus.dailyways.helper.Settings;
import micus.dailyways.io.InputController;
import micus.dailyways.io.MainFrame;

public class Start {
	private static final long serialVersionUID = 1L;
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Settings.load();
		
		//System.out.println("(Start.startUp) "+System.getProperty("user.dir"));
		
		MainFrame frame = new MainFrame();
		frame.setVisible(true);
		
		//System.out.println("(Start.startUp) "+frame.map());
		Model model = new Model(frame);
		
		InputController controller = new InputController(model);
		
		FiducialSimulator simulator = new FiducialSimulator(controller);
		frame.map().addMouseListener(simulator);
		frame.map().addMouseMotionListener(simulator);
		frame.addKeyListener(simulator);
		
		frame.getOverlay().setSimulator(simulator);
		
		
		
		//this.addScene(new FiducialScene(this, "Fiducial Scene", controller));
		
		//this.frame.setVisible(false);
		
	}


}
