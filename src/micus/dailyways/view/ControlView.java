package micus.dailyways.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import javax.swing.text.Element;

import micus.dailyways.helper.Settings;
import micus.dailyways.main.Model;

public class ControlView extends JFrame {
	
	private Listener listener = new Listener();
	
	private JTextField tfPosX, tfPosY;
	
	private Model model;
	
	public ControlView(Model model) {
		super("Daily Ways - Control Panel");
		this.model = model;
		
		
		Container all = this.getContentPane();
		all.setLayout(new GridLayout(1,1));
		
		
		// POSITION /////////////////////////////////////////////////
		Box position = Box.createVerticalBox();
		
		position.add(new JLabel("Window position"));
		
		position.add(createValueLine("x", tfPosX = new JTextField(""+Settings.POS_X)));
		position.add(createValueLine("y", tfPosY = new JTextField(""+Settings.POS_Y)));
		
		
		
		
		
		
		all.add(position);
		
		
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	private Component createValueLine(String label, JTextField tf) {
		Box bx = Box.createHorizontalBox();
		bx.add(new JLabel(label+": "));
		bx.add(tf);
		tf.getDocument().addDocumentListener(listener);
		return bx;
	}

	private class Listener implements DocumentListener {

		@Override
		public void changedUpdate(DocumentEvent arg0) {
			update(arg0);
		}

		@Override
		public void insertUpdate(DocumentEvent arg0) {
			update(arg0);
		}

		@Override
		public void removeUpdate(DocumentEvent arg0) {
			update(arg0);
		}

		private void update(DocumentEvent de) {
			System.out.println("(ControlView.Listener.update) "+de.getDocument());
			if (de.getDocument().equals(tfPosX.getDocument()) || de.getDocument().equals(tfPosY.getDocument())) {
				try {
					int x = Integer.parseInt(tfPosX.getText());
					int y = Integer.parseInt(tfPosY.getText());
					model.getFrame().setLocation(x, y);
					Settings.savePosition(x,y);
				} catch (NumberFormatException e) {
					System.err.println("ERROR: Wrong input format!");
				}
			}
		}

		
		
	}
}
