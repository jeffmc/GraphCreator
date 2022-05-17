package net.mcmillan.traffic.debug;

import java.util.ArrayList;
import java.util.HashMap;

public class DebugOptions {

	public static final String 
			DRAW_GRIDLINES = "Draw Gridlines";
	
	public static final String[] OPTIONS = new String[] {
			DRAW_GRIDLINES,
		};
	
	private HashMap<String, Boolean> options = new HashMap<>();
	
	public DebugOptions() {
		// Defaults
		options.put(DRAW_GRIDLINES, false);
	}
	
	public void set(String key, boolean v) {
		options.put(key, Boolean.valueOf(v));
	}
	
	public boolean get(String key) {
		Boolean b = options.get(key);
		if (b == null) throw new IllegalArgumentException("Invalid debug option: '" + key + "'");
		return b;
	}

	private HashMap<String, ArrayList<OptionListener>> listeners = new HashMap<>();
	public interface OptionListener { public void stateChanged(boolean state); }
	public void addListener(String opt, OptionListener l) {
		ArrayList<OptionListener> list = listeners.get(opt);
		if (list == null) {
			list = new ArrayList<>();
			listeners.put(opt, list);
		}
		list.add(l);
	}
	public boolean removeListener(String opt, OptionListener l) {
		ArrayList<OptionListener> list = listeners.get(opt);
		if (list == null) return false;
		return list.remove(l);
	}
}
