package net.mcmillan.traffic.event;

import java.awt.event.MouseEvent;

import net.mcmillan.traffic.gfx.RenderableCanvas;

public class Event {
	
	public static final int NOBUTTON = MouseEvent.NOBUTTON, 
			BUTTON1 = MouseEvent.BUTTON1, 
			BUTTON2 = MouseEvent.BUTTON2, 
			BUTTON3 = MouseEvent.BUTTON3;
	
	public static final int KEY_TYPED = 0x00, KEY_RELEASED = 0x01, KEY_PRESSED = 0x02,
			MOUSE_RELEASED = 0x10, MOUSE_PRESSED = 0x11, MOUSE_EXITED = 0x12, MOUSE_ENTERED = 0x13,
			MOUSE_CLICKED = 0x14, MOUSE_MOVED = 0x15, MOUSE_DRAGGED = 0x16, MOUSE_WHEEL_MOVED = 0x17;
	
	public int code; // XXXXOOOO, X's are device type, O's are event type
	private String lbl;
	public String getLabel() { return lbl; }
	private Object[] data;
	
	private RenderableCanvas origin;
	public RenderableCanvas getOrigin() { return origin; }
	
	public Event(RenderableCanvas origin, int eventcode) {
		this(origin, eventcode, null);
	}
	
	public Event(RenderableCanvas origin, int eventcode, Object[] data) {
		this.origin = origin;
		code = eventcode;
		lbl = "Event";
		this.data = data;
	}
	
	// Key
	public char keyChar() {
		switch (code) {
		case KEY_TYPED: case KEY_PRESSED: case KEY_RELEASED:
			return (char) data[0];
		default:
			throw new IllegalArgumentException(eventTypeFromCode(code) + " doesn't have keyChar component!");
		}
	}
	public int keyCode() {
		switch (code) {
		case KEY_TYPED: case KEY_PRESSED: case KEY_RELEASED:
			return (int) data[1];
		default:
			throw new IllegalArgumentException(eventTypeFromCode(code) + " doesn't have keyCode component!");
		}
	}
	
	// Mouse
	public int x() {
		switch (code) {
		case MOUSE_RELEASED: case MOUSE_PRESSED: case MOUSE_EXITED: case MOUSE_ENTERED: case MOUSE_CLICKED: case MOUSE_MOVED: case MOUSE_DRAGGED: case MOUSE_WHEEL_MOVED:
			return (int) data[0];
		default:
			throw new IllegalArgumentException(eventTypeFromCode(code) + " doesn't have x component!");
		}
	}
	public int y() {
		switch (code) {
		case MOUSE_RELEASED: case MOUSE_PRESSED: case MOUSE_EXITED: case MOUSE_ENTERED: case MOUSE_CLICKED: case MOUSE_MOVED: case MOUSE_DRAGGED: case MOUSE_WHEEL_MOVED:
			return (int) data[1];
		default:
			throw new IllegalArgumentException(eventTypeFromCode(code) + " doesn't have y component!");
		}
	}
	public int button() {
		switch (code) {
		case MOUSE_RELEASED: case MOUSE_PRESSED: case MOUSE_EXITED: case MOUSE_ENTERED: case MOUSE_CLICKED: case MOUSE_MOVED: case MOUSE_DRAGGED:
			return (int) data[2];
		default:
			throw new IllegalArgumentException(eventTypeFromCode(code) + " doesn't have button component!");
		}
	}
	public int rotation() {
		switch (code) {
		case MOUSE_WHEEL_MOVED:
			return (int) data[2];
		default:
			throw new IllegalArgumentException(eventTypeFromCode(code) + " doesn't have rotation component!");
		}
	}
	public static String eventTypeFromCode(int eventcode) {
		switch (eventcode) {
		case KEY_TYPED:
			return "KeyTyped";
		case KEY_RELEASED:
			return "KeyReleased";
		case KEY_PRESSED:
			return "KeyPressed";
		case MOUSE_RELEASED:
			return "MouseReleased";
		case MOUSE_PRESSED:
			return "MousePressed";
		case MOUSE_EXITED:
			return "MouseExited";
		case MOUSE_ENTERED:
			return "MouseEntered";
		case MOUSE_CLICKED:
			return "MouseClicked";
		case MOUSE_MOVED:
			return "MouseMoved";
		case MOUSE_DRAGGED:
			return "MouseDragged";
		case MOUSE_WHEEL_MOVED:
			return "MouseWheelMoved";
		default:
			return "InvalidEventCode(" + eventcode + ")";
		}
	}
	
	@Override
	public String toString() {
		return lbl;
	}
	
}
