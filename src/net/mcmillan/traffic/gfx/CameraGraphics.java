package net.mcmillan.traffic.gfx;

import java.awt.Color;
import java.awt.Graphics;

import net.mcmillan.traffic.math.DTransform2D;
import net.mcmillan.traffic.math.ITransform2D;

public class CameraGraphics {

	private Camera cam = new Camera();
	public int camX() { return cam.x; }
	public int camY() { return cam.y; }
	private Graphics g;
	
	// Basic
	public CameraGraphics() {
		this(null);
	}
	public CameraGraphics(Graphics g) {
		this.g = g;
	}
	public void setCamera(Camera c) {
		this.cam = c;
	}
	public void setGraphics(Graphics g) {
		this.g = g;
	}
	
	// Color
	public void setColor(Color c) {
		g.setColor(c);
	}
	
	// Oval
	public void drawOval(ITransform2D t) {
		g.drawOval((int)t.x()-cam.x,(int)t.y()-cam.y,(int)t.w(),(int)t.h());
	}
	
	// Rect
	public void drawRect(ITransform2D t) {
		this.drawRect(t.x(),t.y(),t.w(),t.h());
	}
	public void drawRect(DTransform2D t) {
		this.drawRect((int)t.x(),(int)t.y(),(int)t.w(),(int)t.h());
	}
	public void drawRect(int x, int y, int w, int h) {
		g.drawRect(x-cam.x,y-cam.y,w,h);
	}
	public void drawOverlayRect(ITransform2D t) {
		this.drawOverlayRect(t.x(),t.y(),t.w(),t.h());
	}
	public void drawOverlayRect(DTransform2D t) {
		this.drawOverlayRect((int)t.x(),(int)t.y(),(int)t.w(),(int)t.h());
	}
	public void drawOverlayRect(int x, int y, int w, int h) {
		g.drawRect(x,y,w,h);
	}
	public void fillRect(ITransform2D t) {
		this.fillRect(t.x(),t.y(),t.w(),t.h());
	}
	public void fillRect(DTransform2D t) {
		this.fillRect((int)t.x(),(int)t.y(),(int)t.w(),(int)t.h());
	}
	public void fillRect(int x, int y, int w, int h) {
		g.fillRect(x-cam.x,y-cam.y,w,h);
	}
	public void fillOverlayRect(ITransform2D t) {
		this.fillOverlayRect(t.x(),t.y(),t.w(),t.h());
	}
	public void fillOverlayRect(int x, int y, int w, int h) {
		g.fillRect(x,y,w,h);
	}

	// Text
	public void drawString(String str, int x, int y) { // Not zoomed
		g.drawString(str, x-cam.x,y-cam.y);
	}
	public void drawOverlayString(String str, int x, int y) { // Overlays are non-transformed
		g.drawString(str, x,y);
	}
	
	// Lines
	public void drawLine(int x1, int y1, int x2, int y2) { // Draw line offset
		g.drawLine(x1-cam.x, y1-cam.y, x2-cam.x, y2-cam.y);
	}
	
}
