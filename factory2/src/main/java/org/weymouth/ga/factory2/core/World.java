package org.weymouth.ga.factory2.core;

import java.util.ArrayList;
import java.util.List;

import org.weymouth.ga.factory2.state.MarkerState;
import org.weymouth.ga.factory2.state.SensorState;
import org.weymouth.ga.factory2.state.State;

public class World {

	public static final int HEIGHT = 800;
	public static final int WIDTH = 800;

	private List<Belt> allBelts = new ArrayList<Belt>();
	
	private final MarkerBelt marker;
	
	public World() {
		double x = 50.0;
		double y = 150.0;
		double length = 200.0;
		double speed = 5.0;
		StreightBelt segA = new StreightBelt(new Location(x, y), length, Belt.Orientation.EAST);
		segA.setSpeed(speed);
		segA.setName("Segment A");
		allBelts.add(segA);
		
		x += length;
		y = 150.0;
		length = 20.0;
		SensorBelt s1 = new SensorBelt(new Location(x,y), length, Belt.Orientation.EAST);
		s1.setSpeed(speed);
		s1.setName("Sensor 1");
		allBelts.add(s1);
		
		x += length;
		y = 150.0;
		length = 20.0;
		SensorBelt s2 = new SensorBelt(new Location(x,y), length, Belt.Orientation.EAST);
		s2.setSpeed(speed);
		s2.setName("Sensor 2");
		allBelts.add(s2);

		x += length;
		y = 150.0;
		length = 20.0;
		SensorBelt s3 = new SensorBelt(new Location(x,y), length, Belt.Orientation.EAST);
		s3.setSpeed(speed);
		s3.setName("Sensor 3");
		allBelts.add(s3);

		x += length;
		y = 150.0;
		length = 20.0;
		marker = new MarkerBelt(new Location(x,y), length, Belt.Orientation.EAST);
		marker.setSpeed(speed);
		marker.setName("Marker");
		allBelts.add(marker);

		x += length;
		y = 150.0;
		length = 100.0;
		SorterBelt sorter = new SorterBelt(new Location(x, y), length, Belt.Orientation.EAST);
		sorter.setSpeed(speed);
		sorter.setName("Sorter");
		allBelts.add(sorter);
		
		double dx = x + 50.0;
		
		x += length;
		y = 150.0;
		length = 200.0;
		StreightBelt segB = new StreightBelt(new Location(x, y), length, Belt.Orientation.EAST);
		segB.setSpeed(speed);
		segB.setName("Segment B");
		allBelts.add(segB);

		double cx = x + length;
		
		x += length + 50.0;
		y += 50.0;
		length = 500.0;
		StreightBelt drain2 = new StreightBelt(new Location(x, y), length, Belt.Orientation.SOUTH);
		drain2.setSpeed(speed);
		drain2.setName("Drain 2");
		allBelts.add(drain2);
		
		x = cx;
		y = 150.0;
		length = 100.0;
		CornerBelt corner = new CornerBelt(new Location(x, y), length, Belt.Orientation.EAST);
		corner.setSpeed(speed);
		corner.setName("Corner");
		allBelts.add(corner);

		x = dx;
		y = 150.0 + 50.0;
		length = 500.0;
		StreightBelt drain1 = new StreightBelt(new Location(x, y), length, Belt.Orientation.SOUTH);
		drain1.setSpeed(speed);
		drain1.setName("Drain 1");
		allBelts.add(drain1);
		
		for (Belt b: allBelts) {
			b.setSpeed(speed);
		}

		segA.linkUp(s1);
		s1.linkUp(s2);
		s2.linkUp(s3);
		s3.linkUp(marker);
		marker.linkUp(sorter);
		sorter.linkUp(segB);
		sorter.linkUpDrain(drain1);
		segB.linkUp(corner);
		corner.linkUp(drain2);
	}

	public void add(Thing t) {
		allBelts.get(0).add(t);
	}
	
	public void setSorterThreshold(Color c) {
		marker.threshold = c;
	}
	
	public Color getSorterThreshold() {
		return marker.threshold;
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

	public List<State> allStates() {
		List<State> ret = new ArrayList<State>();
		for (Belt b: allBelts) {
			State s = stateOf(b);
			if (s != null) {
				ret.add(s);
			}
		}
		return ret;
	}

	private State stateOf(Belt b) {
		if (b instanceof SensorBelt) {
			return stateOf((SensorBelt)b);
		}
		if (b instanceof MarkerBelt) {
			return stateOf((MarkerBelt)b);
		}
		return null;
	}

	private State stateOf(SensorBelt b) {
		String name = b.getName();
		Thing t = b.getLastSensed();
		return new SensorState(name, t);
	}

	private State stateOf(MarkerBelt b) {
		String name = b.getName();
		Thing t = b.getLastMarked();
		return new MarkerState(name, t);
	}

}
