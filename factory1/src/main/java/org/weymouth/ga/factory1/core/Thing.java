package org.weymouth.ga.factory1.core;

public class Thing {

	private static int count = 0;

	public final Color color;
	public final double radius = 10.0;
	public int id = count++;

	public Thing(Color c) {
		color = c;
	}

	public String toString() {
		return String.format("%d: %03d,%03d,%03d", id, color.r, color.g, color.b);
	}

}
