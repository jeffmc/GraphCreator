package net.mcmillan.traffic.gfx;

import java.awt.Canvas;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferStrategy;

import net.mcmillan.traffic.event.Event;
import net.mcmillan.traffic.event.EventQueue;
import net.mcmillan.traffic.math.IVec2;

// A class meant to hold a JWT canvas instance and manage all event passthrough to the app-specific event queue.
public class RenderableCanvas {

	// Components
	private Canvas canvas;
	public Canvas getAWTCanvas() { return canvas; }
	private BufferStrategy bufferStrategy = null;
	
	// Viewport size
	private IVec2 size = IVec2.make();
	public int getWidth() { return size.x(); }
	public int getHeight() { return size.y(); }

	// Listeners
	private KeyListener keyListener;
	private MouseListener mouseListener;
	private MouseMotionListener mouseMotionListener;
	private MouseWheelListener mouseWheelListener;
	private EventQueue eventq;
	
	public RenderableCanvas() {
		canvas = new Canvas();
		eventq = new EventQueue();
		
		setupCanvas(new Dimension(1280, 720));
		updateSizeVec();
		
		canvas.addComponentListener(new ComponentAdapter() {
			@Override public void componentResized(ComponentEvent e) {
				updateSizeVec(); 
//				eventq.push(new Event(CANVAS_RESIZED)) // TODO: Add listener model
			}
		});
		setupListeners();
	}

	// Canvas buffers
	public void makeBuffers() {
		canvas.createBufferStrategy(2);
		bufferStrategy = canvas.getBufferStrategy();
	}
	public Graphics getGraphics() {
		Graphics gg = bufferStrategy.getDrawGraphics();
		return gg.create();
	}
	
	public void showBuffer() {
		bufferStrategy.show();
	}
	
	// Size
	private void setupCanvas(Dimension size) {
		canvas.setMinimumSize(size);
		canvas.setPreferredSize(size);
		canvas.setMaximumSize(size);
	}
	private void updateSizeVec() {
		size.x(canvas.getWidth());
		size.y(canvas.getHeight());
	}
	
	// Custom cursor
	public void setCursor(Cursor c) {
		canvas.setCursor(c);
	}
	
	// Events
	private void setupListeners() {
		keyListener = new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) { 
				eventq.push(new Event(RenderableCanvas.this, 0x00, new Object[] {e.getKeyChar(), e.getKeyCode(), e.getModifiersEx() }));
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				eventq.push(new Event(RenderableCanvas.this, 0x01, new Object[] {e.getKeyChar(), e.getKeyCode(), e.getModifiersEx() }));
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				eventq.push(new Event(RenderableCanvas.this, 0x02, new Object[] {e.getKeyChar(), e.getKeyCode(), e.getModifiersEx() }));
			}
		};
		mouseListener = new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
				eventq.push(new Event(RenderableCanvas.this, 0x10, new Object[] {e.getX(), e.getY(), e.getButton(), e.getModifiersEx(), e.getClickCount()} ));
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				eventq.push(new Event(RenderableCanvas.this, 0x11, new Object[] {e.getX(), e.getY(), e.getButton(), e.getModifiersEx(), e.getClickCount()} ));
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				eventq.push(new Event(RenderableCanvas.this, 0x12, new Object[] {e.getX(), e.getY(), e.getButton(), e.getModifiersEx(), e.getClickCount()} ));
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				eventq.push(new Event(RenderableCanvas.this, 0x13, new Object[] {e.getX(), e.getY(), e.getButton(), e.getModifiersEx(), e.getClickCount()} ));
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				eventq.push(new Event(RenderableCanvas.this, 0x14, new Object[] {e.getX(), e.getY(), e.getButton(), e.getModifiersEx(), e.getClickCount()} ));
			}
		};
		mouseMotionListener = new MouseMotionListener() {
			@Override
			public void mouseMoved(MouseEvent e) {
				eventq.push(new Event(RenderableCanvas.this, 0x15, new Object[] {e.getX(), e.getY(), e.getButton(), e.getModifiersEx()} ));
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				eventq.push(new Event(RenderableCanvas.this, 0x16, new Object[] {e.getX(), e.getY(), e.getButton(), e.getModifiersEx()} ));
			}
		};
		mouseWheelListener = new MouseWheelListener() {
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				eventq.push(new Event(RenderableCanvas.this, 0x17, new Object[] {e.getX(), e.getY(), e.getWheelRotation(), e.getModifiersEx() } ));
			}
		};
	}
	public void setEventQueue(EventQueue eq) {
		eventq = eq;
		if (eventq != null) {
			canvas.addKeyListener(keyListener);
			canvas.addMouseListener(mouseListener);
			canvas.addMouseMotionListener(mouseMotionListener);
			canvas.addMouseWheelListener(mouseWheelListener);
			System.out.println("Listeners enabled!");
		} else {
			canvas.removeKeyListener(keyListener);
			canvas.removeMouseListener(mouseListener);
			canvas.removeMouseMotionListener(mouseMotionListener);
			canvas.removeMouseWheelListener(mouseWheelListener);
			System.out.println("Listeners removed!");
		}
	}
	public void removeEventQueue() {
		setEventQueue(null);
	}
}
