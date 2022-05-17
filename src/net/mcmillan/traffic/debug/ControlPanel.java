package net.mcmillan.traffic.debug;

import java.awt.Component;
import java.awt.GridLayout;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import net.mcmillan.traffic.simulation.AppLogic;

// Meant for the user's modification of variables in the simulation and management of the sim's state.
public class ControlPanel {

	private static final int SECTION_SEPERATION = 10; // Between panes
	private static final int BUTTON_SEPERATION = 3; // Between UI elements within single panes
	
	private JFrame frame = new JFrame("Control Panel");
	public JFrame getFrame() { return frame; }
	
	private AppLogic sim;
	public void setSimulation(AppLogic s) { 
		sim = s;
		updateDebugBtns();
		updateStateBtns();
	}
	
	private JButton playPauseBtn, stepBtn;
	private static final String PLAY_STR = "Play", PLAY_TOOLTIP = "Continously run the simulation",
			PAUSE_STR = "Pause", PAUSE_TOOLTIP = "Stop the simulation",
			STEP_STR = "Step", STEP_TOOLTIP = "Tick the simulation once";
	
	private HashMap<String, JToggleButton> debugBtns = new HashMap<>();
	
	public ControlPanel() {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.PAGE_AXIS));
		
		frame.add(makeStatePane());
		frame.add(Box.createVerticalStrut(SECTION_SEPERATION));
		frame.add(makeDebugOptionsPane());
		
		frame.pack();
		frame.setVisible(true);
	}
	
	private JPanel makeStatePane() {
		JPanel pane = new JPanel();
		pane.setLayout(new BoxLayout(pane, BoxLayout.LINE_AXIS));
		pane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createRaisedBevelBorder(), "State"));
		
		playPauseBtn = new JButton(PLAY_STR);
		playPauseBtn.setAlignmentY(Component.CENTER_ALIGNMENT);
		playPauseBtn.addActionListener((e) -> {
			sim.togglePlayPause();
			updateStateBtns();
		});
		stepBtn = new JButton(STEP_STR);
		stepBtn.setAlignmentY(Component.CENTER_ALIGNMENT);
		stepBtn.addActionListener((e) -> {
			sim.step();
			updateStateBtns();
		});
		stepBtn.setToolTipText(STEP_TOOLTIP);
		
		pane.add(playPauseBtn);
		pane.add(Box.createHorizontalStrut(BUTTON_SEPERATION));
		pane.add(stepBtn);
		
		return pane;
	}

	private void updateStateBtns() {
		boolean p = sim.isPaused();
		stepBtn.setEnabled(p);
		playPauseBtn.setText(p?PLAY_STR:PAUSE_STR);
		playPauseBtn.setToolTipText(p?PLAY_TOOLTIP:PAUSE_TOOLTIP);
	}
	
	private JPanel makeDebugOptionsPane() {
		JPanel pane = new JPanel();
		pane.setLayout(new GridLayout(0, 2, BUTTON_SEPERATION, BUTTON_SEPERATION));
		pane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createRaisedBevelBorder(), "Debug Options"));
		for (int i=0;i<DebugOptions.OPTIONS.length;i++) {
			String opt = DebugOptions.OPTIONS[i];
			JToggleButton btn = new JToggleButton(opt);
			btn.addActionListener((e) -> sim.debugOptions.set(opt, btn.isSelected()));
			debugBtns.put(opt, btn);
			pane.add(btn);
		}
		return pane;
	}
	
	private void updateDebugBtns() {
		for (String opt : DebugOptions.OPTIONS) {
			debugBtns.get(opt).setSelected(sim.debugOptions.get(opt));
		}
	}
	
}
