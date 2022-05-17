package net.mcmillan.traffic.math;

public class DVec2 { // TODO: Add immutable vectors for sending through events
	public static final int VECSIZE = 2;
	
	private double[] n;
	public double x() { return n[0]; }
	public double y() { return n[1]; }
	public void x(double nx) { n[0] = nx; }
	public void y(double ny) { n[1] = ny; }
	
	private DVec2(double[] t) { n = t; }
	private DVec2(double x, double y) { n = new double[] {x, y}; }
	
	public static DVec2 make() { return new DVec2(0,0); }
	public static DVec2 make(double x, double y) { return new DVec2(x,y); }
	public static DVec2 copy(DVec2 src) { return new DVec2(src.n.clone()); }
	public static DVec2 ref(double[] raw) { return new DVec2(raw); }
	
	public double[] raw() { return n; }
	public DVec2 copy() {
		return DVec2.copy(this);
	}
	
	// Assignment
	public DVec2 set(double x, double y) {
		n[0] = x;
		n[1] = y;
		return this;
	}
	public DVec2 set(DVec2 a) {
		for (int i=0;i<VECSIZE;i++)
			n[i] = a.n[i];
		return this;
	}
	
	// Addition
	public DVec2 add(DVec2 a) {
		for (int i=0;i<VECSIZE;i++)
			n[i] += a.n[i];
		return this;
	}
	public DVec2 add(double x, double y) {
		n[0] += x;
		n[1] += y;
		return this;
	}
	public static DVec2 add(DVec2 a, DVec2 b) { return a.copy().add(b); }
	
	// Subtraction
	public DVec2 sub(DVec2 a) {
		for (int i=0;i<VECSIZE;i++)
			n[i] -= a.n[i];
		return this;
	}
	public DVec2 sub(double x, double y) {
		n[0] -= x;
		n[1] -= y;
		return this;
	}
	public static DVec2 sub(DVec2 a, DVec2 b) { return a.copy().sub(b); }
	
	// Divison
	public DVec2 div(double d) {
		for (int i=0;i<VECSIZE;i++)
			n[i] /= d;
		return this;
	}
	
	// Absolute value
	public DVec2 abs() {
		for (int i=0;i<VECSIZE;i++)
			n[i] = Math.abs(n[i]);
		return this;
	}
	
	// Minimum/maximum
	public DVec2 min(DVec2 o) {
		for (int i=0;i<VECSIZE;i++)
			n[i] = Math.min(n[i], o.n[i]);
		return this;
	}
	public static DVec2 min(DVec2 a, DVec2 b) {
		DVec2 x = DVec2.make();
		for (int i=0;i<VECSIZE;i++)
			x.n[i] = Math.min(a.n[i], b.n[i]);
		return x;
	}
	public DVec2 max(DVec2 o) {
		for (int i=0;i<VECSIZE;i++)
			n[i] = Math.max(n[i], o.n[i]);
		return this;
	}
	public static DVec2 max(DVec2 a, DVec2 b) {
		DVec2 x = DVec2.make();
		for (int i=0;i<VECSIZE;i++)
			x.n[i] = Math.max(a.n[i], b.n[i]);
		return x;
	}
	
	public static boolean rectIntersect(DVec2 ap,DVec2 as,DVec2 bp,DVec2 bs) {
		return (ap.x() < bp.x() + bs.x() &&
	    ap.x() + as.x() > bp.x() &&
	    ap.y() < bp.y() + bs.y() &&
	    ap.y() + as.y() > bp.y());		
	}
	
}
