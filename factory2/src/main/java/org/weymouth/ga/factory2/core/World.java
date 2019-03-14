package org.weymouth.ga.factory2.core;

import java.util.ArrayList;
import java.util.List;

public class World {

	public static final int HEIGHT = 800;
	public static final int WIDTH = 800;

	List<Belt> normalBelts = new ArrayList<Belt>();
	List<Belt> allBelts = new ArrayList<Belt>();
	SorterBelt sbelt;
	Belt sortRunoffBelt;

	public World() {
		double x = 150.0;
		double y = 150.0;
		double length = 200.0;
		Belt belt = new Belt(new Location(x, y), length, Belt.Orientation.HORIZONTAL);
		normalBelts.add(belt);
		allBelts.add(belt);

		x += length;
		double runoffX = x + 50.0;
		y = 150.0;
		length = 100.0;
		sbelt = new SorterBelt(new Location(x, y), length, Belt.Orientation.HORIZONTAL);
		normalBelts.add(sbelt);
		allBelts.add(sbelt);

		x += length;
		y = 150.0;
		length = 200.0;
		belt = new Belt(new Location(x, y), length, Belt.Orientation.HORIZONTAL);
		normalBelts.add(belt);
		allBelts.add(belt);

		x += length + 50.0;
		y += 50.0;
		length = 500.0;
		Belt beltTwo = new Belt(new Location(x, y), length, Belt.Orientation.VETRICAL);
		normalBelts.add(beltTwo);
		allBelts.add(beltTwo);

		length = 500.0;
		sortRunoffBelt = new Belt(new Location(runoffX, y), length, Belt.Orientation.VETRICAL);
		allBelts.add(sortRunoffBelt);
	}

	public void add(Thing t) {
		normalBelts.get(0).add(t);
	}

	public List<Thing> update() {
		List<Thing> runOff = normalBelts.get(0).update();
		for (int i = 1; i < normalBelts.size(); i++) {
			for (Thing t : runOff) {
				normalBelts.get(i).add(t);
			}
			runOff = normalBelts.get(i).update();
		}
		for (Thing t : sbelt.dumpSortedOut()) {
			sortRunoffBelt.add(t);
		}
		List<Thing> moreRunoff = sortRunoffBelt.update();
		runOff.addAll(moreRunoff);
		return runOff;
	}

	public boolean isLive() {
		for (Belt b : normalBelts) {
			if (b.hasThings())
				return true;
		}
		return false;
	}

	public List<Belt> getBelts() {
		return allBelts;
	}

}
