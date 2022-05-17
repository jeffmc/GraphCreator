package net.mcmillan.traffic.math;

public class ITransform2D {

	public IVec2 pos, size;
	
	public ITransform2D(IVec2 p, IVec2 s) {
		pos = p;
		size = s;
	}
	
	public ITransform2D() {
		pos = IVec2.make();
		size = IVec2.make(1,1);
	}
	
	public int x() { return pos.x(); }
	public int y() { return pos.y(); }
	public int w() { return size.x(); }
	public int h() { return size.y(); }
	public int width() { return size.x(); }
	public int height() { return size.y(); }
	
	public ITransform2D copy() { return new ITransform2D(pos.copy(), size.copy()); }
	
	public int cx() { return this.x() + this.width()/2; }
	public int cy() { return this.y() + this.height()/2; }
	
	public ITransform2D offset(int o) {
		pos.add(-o, -o);
		int doubleo = 2*o;
		size.add(doubleo, doubleo);
		return this;
	}
	
	public static boolean intersects(ITransform2D a, ITransform2D b) {
		return (a.x() < b.x() + b.w() &&
	    a.x() + a.w() > b.x() &&
	    a.y() < b.y() + b.h() &&
	    a.y() + a.h() > b.y());		
	}
	public boolean intersects(ITransform2D o) { return ITransform2D.intersects(this, o); }
}
