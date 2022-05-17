package net.mcmillan.traffic.math;

public class Mat3x2 {

	// Maths:
	// x y 1
	// A B C x'
	// D E F y'
	
	public static final int WIDTH = 3, HEIGHT = 2;
	public static final double[][] IDENTITY = new double[][] { { 1, 0 }, { 0, 1 }, { 0, 0 }, }; // Identity matrix
	public double[][] n = IDENTITY.clone(); // Identity matrix
	
	public double[] multiply(int x, int y) {
		return new double[] {
			x * n[0][0] + y * n[1][0] + n[2][0],
			x * n[0][1] + y * n[1][1] + n[2][1],	
		};
	}
//	public IVec2 copyAndMultiply(IVec2 vec) {
//		return IVec2.make(
//			vec.x() * n[0][0] + vec.y() * n[1][0] + n[2][0],
//			vec.x() * n[0][1] + vec.y() * n[1][1] + n[2][1]
//		);
//	}
	public double[] scalar() { // TODO: Not sure if this is correct term for multiplied (1,1)
		return new double[] {
			n[0][0] + n[1][0],
			n[0][1] + n[1][1],	
		};
	}
	public double[] scaled(double x, double y) { // TODO: Not sure if this is correct term for multiplied (1,1)
		return new double[] {
			x * n[0][0] + y * n[1][0],
			x * n[0][1] + y * n[1][1],	
		};
	}
	
	public Mat3x2 translate(double x, double y) {
		n[2][0] += x;
		n[2][1] += y;
		return this; // Chaining
	}
	
	public Mat3x2 scale(double v) {
		for (int x=0;x<WIDTH;x++) {
			for (int y=0;y<HEIGHT;y++) {
				n[x][y] *= v;
			}
		}
		return this; // Chaining
	}
	
	public Mat3x2 identity() {
		n = IDENTITY.clone();
		return this;
	}
}
