package org.weymouth.ga.factory2.core;

import java.util.ArrayList;
import java.util.List;

public class World {

	public static final int HEIGHT = 800;
	public static final int WIDTH = 800;

	List<Belt> allBelts = new ArrayList<Belt>();

	public World() {
		double x = 150.0;
		double y = 150.0;
		double length = 200.0;
		StreightBelt segA = new StreightBelt(new Location(x, y), length, Belt.Orientation.EAST);
		allBelts.add(segA);
		
		x += length;
		y = 150.0;
		length = 20.0;
		SensorBelt s1 = new SensorBelt(new Location(x,y), length, Belt.Orientation.EAST);
		allBelts.add(s1);
		
		x += length;
		y = 150.0;
		length = 100.0;
		SorterBelt sorter = new SorterBelt(new Location(x, y), length, Belt.Orientation.EAST);
		allBelts.add(sorter);
		
		double dx = x + 50.0;
		
		x += length;
		y = 150.0;
		length = 200.0;
		StreightBelt segB = new StreightBelt(new Location(x, y), length, Belt.Orientation.EAST);
		allBelts.add(segB);

		double cx = x + length;
		
		x += length + 50.0;
		y += 50.0;
		length = 500.0;
		StreightBelt drain2 = new StreightBelt(new Location(x, y), length, Belt.Orientation.SOUTH);
		allBelts.add(drain2);
		
		x = cx;
		y = 150.0;
		length = 100.0;
		CornerBelt corner = new CornerBelt(new Location(x, y), length, Belt.Orientation.EAST);
		allBelts.add(corner);

		x = dx;
		y = 150.0 + 50.0;
		length = 500.0;
		StreightBelt drain1 = new StreightBelt(new Location(x, y), length, Belt.Orientation.SOUTH);
		allBelts.add(drain1);
		
		

		segA.linkUp(s1);
		s1.linkUp(sorter);
		sorter.linkUp(segB);
		sorter.linkUpDrain(drain1);
		segB.linkUp(corner);
		corner.linkUp(drain2);
	}

	public void add(Thing t) {
		allBelts.get(0).add(t);
	}

	public List<Thing> update() {
		List<Thing> runOff = new ArrayList<Thing>();
		for (Belt b : allBelts) {
			List<Thing> more = b.update();
			runOff.addAll(more);
		}
		return runOff;
	}

	public boolean isLive() {
		for (Belt b : allBelts) {
			if (b.hasThings())
				return true;
		}
		return false;
	}

	public List<Belt> getBelts() {
		return allBelts;
	}

}
