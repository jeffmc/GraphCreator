package net.mcmillan.traffic.simulation.tools;

import java.awt.Color;

import net.mcmillan.traffic.event.Event;
import net.mcmillan.traffic.gfx.Camera;
import net.mcmillan.traffic.gfx.CameraGraphics;
import net.mcmillan.traffic.math.ITransform2D;
import net.mcmillan.traffic.math.IVec2;
import net.mcmillan.traffic.simulation.AppLogic;

public class SelectTool implements Tool {

	public static final String NAME = "Select";
	@Override
	public String getName() { return NAME; }
	private boolean active = false;
	private IVec2 mstart = IVec2.make(), mnow = IVec2.make(), msize = IVec2.make(), morigin = IVec2.make();
	private ITransform2D selection = new ITransform2D(morigin, msize);
	
	@Override
	public boolean mousePressed(Event e, AppLogic sim) { 
		switch (e.button()) {
		case Event.BUTTON1:
			active = true;
			setMouseNowRelativeToCam(e, sim.getCamera());
			mstart.set(mnow);
			return true;
		}
		return false;
	}
	@Override
	public boolean mouseReleased(Event e, AppLogic sim) {
		switch (e.button()) {
		case Event.BUTTON1:
			active = false;
			selectMouseArea();
			return true;
		}
		return false;
	}
	@Override
	public boolean mouseDragged(Event e, AppLogic sim) {
		if (active) {
			setMouseNowRelativeToCam(e, sim.getCamera());
			msize.set(mstart).sub(mnow).abs();
			morigin.set(mstart).min(mnow);
			return true;
		}
		return false;
	}

	private void setMouseNowRelativeToCam(Event e, Camera c) { // Converts from screen -> world coords
		mnow.set(e.x()+c.x, e.y()+c.y);
	}
	
	private void selectMouseArea() {
		ITransform2D t = getSelectionTransform();
		System.out.println("[AppLogic] Select mouse area: [" + String.join(",", 
				Integer.toString(t.x()), Integer.toString(t.y()), 
				Integer.toString(t.w()), Integer.toString(t.h())) + "]");
	}
	public ITransform2D getSelectionTransform() {
		return selection;
	}
	
	@Override
	public boolean mouseClicked(Event e, AppLogic sim) { return false; }
	@Override
	public void draw(CameraGraphics cg, AppLogic sim) {
		if (msize.x() > 0 || msize.y() > 0) {
			cg.setColor(Color.white);
			cg.drawRect(selection);
		}
	}


}
