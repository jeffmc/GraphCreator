package net.mcmillan.traffic.math;

public class DTransform2D {

	public DVec2 pos, size;
	
	public DTransform2D(DVec2 p, DVec2 s) {
		pos = p;
		size = s;
	}
	
	public DTransform2D() {
		pos = DVec2.make();
		size = DVec2.make(1,1);
	}

	public DTransform2D pos(double x, double y) {
		pos.set(x,y);
		return this;
	}
	
	public double x() { return pos.x(); }
	public DTransform2D x(double v) {
		pos.x(v);
		return this;
	}
	public double y() { return pos.y(); }
	public DTransform2D y(double v) {
		pos.y(v);
		return this;
	}
	
	public DTransform2D move(DVec2 v) {
		pos.add(v);
		return this;
	}
	public DTransform2D move(double x, double y) {
		pos.add(x, y);
		return this;
	}
	
	public double w() { return size.x(); }
	public double h() { return size.y(); }
	public double width() { return size.x(); }
	public double height() { return size.y(); }
	
	public DTransform2D copy() { return new DTransform2D(pos.copy(), size.copy()); }
	
	public double cx() { return this.x() + this.width()/2; }
	public double cy() { return this.y() + this.height()/2; }
	
	public DTransform2D offset(int o) {
		pos.add(-o, -o);
		int doubleo = 2*o;
		size.add(doubleo, doubleo);
		return this;
	}
	
	public static boolean intersects(DTransform2D a, DTransform2D b) {
		return (a.x() < b.x() + b.w() &&
	    a.x() + a.w() > b.x() &&
	    a.y() < b.y() + b.h() &&
	    a.y() + a.h() > b.y());		
	}
	public static boolean intersects(DTransform2D a, ITransform2D b) {
		return (a.x() < b.x() + b.w() &&
	    a.x() + a.w() > b.x() &&
	    a.y() < b.y() + b.h() &&
	    a.y() + a.h() > b.y());		
	}
	public boolean intersects(DTransform2D o) { return DTransform2D.intersects(this, o); }
	public boolean intersects(ITransform2D o) { return DTransform2D.intersects(this, o); }
}
