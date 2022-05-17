package net.mcmillan.traffic.event;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;

public class EventQueue {

	private LinkedList<Event> queue;
	private LinkedList<Event> unloaded;
	private AtomicBoolean unloading = new AtomicBoolean(false); // attempt at making this thread-safe, not entirely sure
	
	private long stalledForUnloading = 0;
	public long getStalls() { return stalledForUnloading; }
	
	public EventQueue() {
		queue = new LinkedList<>();
		unloaded = new LinkedList<>();
	}
	
	public boolean isEmpty() {
		return queue.isEmpty();
	}
	
	// Adds event to the end of queue
	public void push(Event e) {
		if (unloading.get()) {
			stalledForUnloading++;
			while (unloading.get()) { } // This is atrocious code
		}
		queue.add(e);
//		printList();
	}
	
	// Removes the first event of the unloaded
	public Event pop() {
		Event popped = unloaded.pop();
		return popped;
	}
	
	public void unload() {
		unloading.set(true);
		unloaded.addAll(queue);
		queue.clear();
		unloading.set(false);
	}
	
	public boolean unloadedEmpty() {
		return unloaded.isEmpty();
	}
	
//	public LinkedList<Event> list() { return queue; }
	
	public void printList() {
		for (Event e : queue)
			System.out.println(e.getLabel());
	}
}
