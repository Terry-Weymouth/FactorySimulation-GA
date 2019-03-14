package org.weymouth.ga.factory1.core;

import java.util.ArrayList;
import java.util.List;

public class World {

	public static final int HEIGHT = 800;
	public static final int WIDTH = 800;

	List<Belt> belts = new ArrayList<Belt>();

	public World() {
		double x = 150.0;
		double y = 150.0;
		double length = 500.0;
		Belt beltOne = new Belt(new Location(x, y), length, Belt.Orientation.HORIZONTAL);
		x += length + 50.0;
		y += 50.0;
		Belt beltTwo = new Belt(new Location(x, y), length, Belt.Orientation.VETRICAL);
		belts.add(beltOne);
		belts.add(beltTwo);
	}

	public void add(Thing t) {
		belts.get(0).add(t);
	}

	public List<Thing> update() {
		List<Thing> runOff = belts.get(0).update();
		for (Thing t : runOff) {
			belts.get(1).add(t);
		}
		runOff = belts.get(1).update();
		return runOff;
	}

	public boolean isLive() {
		for (Belt b : belts) {
			if (b.hasThings())
				return true;
		}
		return false;
	}

	public List<Belt> getBelts() {
		return belts;
	}

}
