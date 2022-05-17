package net.mcmillan.traffic.gfx;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import net.mcmillan.traffic.AppManager;

public class AppWindow {

	private JFrame frame;
	public JFrame getFrame() { return frame; }
	
	private RenderableCanvas canvas;
	public RenderableCanvas getCanvas() { return canvas; }
	
	public AppWindow() {
		frame = new JFrame("Traffic Simulation");
		canvas = new RenderableCanvas();
		
		frame.setResizable(true);
		frame.setIgnoreRepaint(true);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			@Override public void windowClosing(WindowEvent e) {
				AppManager.getSingleton().stop();
			}
		});
		
		frame.add(canvas.getAWTCanvas(), BorderLayout.CENTER);
		
 		frame.pack(); // necessary to validate components for buffer functions below
		
		// Set visible in center of screen
		frame.setLocationRelativeTo(null);
		frame.repaint();
		frame.setVisible(true);
		
		canvas.makeBuffers();
	}
}
