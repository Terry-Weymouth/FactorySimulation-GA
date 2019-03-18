package org.weymouth.ga.factory2.core;

public class Color {

	public final int r;
	public final int g;
	public final int b;

	public Color(int r, int g, int b) {
		this.r = r;
		this.g = g;
		this.b = b;
	}
	
	public String toString() {
		return String.format("C: %3d, %3d, %3d", r, g, b);
	}

}
