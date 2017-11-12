package micus.dailyways.main;

import org.mt4j.MTApplication;

import micus.dailyways.fiducialSimulator.FiducialSimulator;
import micus.dailyways.helper.Settings;
import micus.dailyways.view.FiducialScene;
import micus.dailyways.view.InputController;
import micus.dailyways.view.MainFrame;

public class Start extends MTApplication {
	private static final long serialVersionUID = 1L;
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Settings.load();
		
		initialize();
	}
	
	@Override
	public void startUp() {
		
		System.out.println("(Start.startUp) "+System.getProperty("user.dir"));
		
		MainFrame frame = new MainFrame();
		frame.setVisible(true);
		
		//System.out.println("(Start.startUp) "+frame.map());
		Model model = new Model(frame.map());
		
		InputController controller = new InputController(model);
		
		FiducialSimulator simulator = new FiducialSimulator(controller);
		frame.map().addMouseListener(simulator);
		frame.map().addMouseMotionListener(simulator);
		frame.addKeyListener(simulator);
		model.setSimulator(simulator);
		
		this.addScene(new FiducialScene(this, "Fiducial Scene", controller));
		
		this.frame.setVisible(false);
	}

}
