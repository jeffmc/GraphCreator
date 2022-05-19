package net.mcmillan.traffic.debug;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SpinnerNumberModel;

import net.mcmillan.traffic.simulation.AppLogic;
import net.mcmillan.traffic.simulation.tools.Tool;

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
		updateToolBtns();
		sim.nextNodeLabel = () -> {
			return nodeLabelField.getText();
		};
		sim.nextEdgeLabel = () -> {
			int ret = (int) edgeLabelField.getValue();
			edgeLabelField.setValue(ret+1);
			return Integer.toString(ret);
		};
	}
	
	private JTextField nodeLabelField;
	private JSpinner edgeLabelField;
	
	private JButton[] toolBtns;
	
	private HashMap<String, JToggleButton> debugBtns = new HashMap<>();
	
	public ControlPanel() {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.PAGE_AXIS));
		
		frame.add(makeToolPane());
		frame.add(Box.createVerticalStrut(SECTION_SEPERATION));
		frame.add(makeLabelPane());
		frame.add(Box.createVerticalStrut(SECTION_SEPERATION));
		frame.add(makeDebugOptionsPane());
		
		frame.pack();
		frame.setVisible(true);
	}

	private JPanel makeLabelPane() {
		JPanel pane = new JPanel();
		pane.setLayout(new BoxLayout(pane, BoxLayout.PAGE_AXIS));
		pane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createRaisedBevelBorder(), "Label"));
		
		JLabel nodeL = new JLabel("Node Label:"), edgeL = new JLabel("Edge Label:");
		nodeLabelField = new JTextField("A");
		edgeLabelField = new JSpinner(new SpinnerNumberModel(1,0,Integer.MAX_VALUE,1));
		nodeLabelField.setAlignmentX(Component.CENTER_ALIGNMENT);
		edgeLabelField.setAlignmentX(Component.CENTER_ALIGNMENT);
		nodeL.setAlignmentX(Component.CENTER_ALIGNMENT);
		edgeL.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		pane.add(nodeL);
		pane.add(Box.createVerticalStrut(BUTTON_SEPERATION));
		pane.add(nodeLabelField);
		pane.add(Box.createVerticalStrut(BUTTON_SEPERATION));
		pane.add(edgeL);
		pane.add(Box.createVerticalStrut(BUTTON_SEPERATION));
		pane.add(edgeLabelField);
		return pane;
	}
	
	private JPanel makeToolPane() {
		JPanel pane = new JPanel();
		pane.setLayout(new BoxLayout(pane, BoxLayout.LINE_AXIS));
		pane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createRaisedBevelBorder(), "Tools"));
		
		toolBtns = new JButton[AppLogic.TOOLS.length];
		for (int i=0;i<AppLogic.TOOLS.length;i++) {
			Tool t = AppLogic.TOOLS[i];
			JButton btn = new JButton(t.getName());
			btn.setAlignmentY(Component.CENTER_ALIGNMENT);
			btn.addActionListener((e) -> {
				sim.setTool(t);
				updateToolBtns();
			});
			pane.add(btn);
			toolBtns[i] = btn;
			if (i<AppLogic.TOOLS.length-1) pane.add(Box.createHorizontalStrut(BUTTON_SEPERATION));
		}
		return pane;
	}

	private void updateToolBtns() {
		final Color enabled = Color.GREEN, disabled = Color.WHITE;
		Tool t = sim.getTool();
		for (int i=0;i<AppLogic.TOOLS.length;i++) 
			toolBtns[i].setBackground(AppLogic.TOOLS[i] == t ? enabled : disabled);
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

	public interface LabelFactory {
		public String get();
	}
	
}
