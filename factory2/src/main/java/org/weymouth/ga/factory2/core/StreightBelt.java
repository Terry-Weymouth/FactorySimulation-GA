package org.weymouth.ga.factory2.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StreightBelt implements Belt {

	private final Random random = new Random();
	private final Location start, stop;
	private final double length;
	private final double dx, dy;
	private final float graphicsX, graphicsY, graphicsWidth, graphicsHeight;
	private final Orientation orientation;
	private double speed = 1.0;
	private double width = 100.0;
	protected List<ThingOnBelt> objects = new ArrayList<ThingOnBelt>();

	public StreightBelt(Location startLocation, double l, Belt.Orientation o) {
		orientation = o;
		start = startLocation;
		length = l;
		if (orientation.equals(Belt.Orientation.EAST)) {
			stop = new Location(start.x + length, start.y);
			dx = 1.0;
			dy = 0.0;
			graphicsX = (float) start.x;
			graphicsY = (float) (start.y - (width / 2.0));
			graphicsHeight = (float) width;
			graphicsWidth = (float) length;
		} else {
			stop = new Location(start.x, start.y + length);
			dx = 0.0;
			dy = 1.0;
			graphicsX = (float) (start.x - (width / 2.0));
			graphicsY = (float) start.y;
			graphicsHeight = (float) length;
			graphicsWidth = (float) width;
		}
	}

	public void add(Thing t) {
		double jiggle = random.nextGaussian();
		while (jiggle > 1.0 || jiggle < -1.0) {
			jiggle = random.nextGaussian();
		}
		jiggle = (width / 2.0 - 10.0) * jiggle;
		double x = start.x;
		double y = start.y;
		if (orientation.equals(Belt.Orientation.EAST)) {
			y += jiggle;
		} else {
			x += jiggle;
		}
		objects.add(new ThingOnBelt(t, x, y));
	}
	
	public void handoff(Thing t) {
		double x = start.x;
		double y = start.y;
		objects.add(new ThingOnBelt(t, x, y));		
	}

	public List<Thing> update() {
		List<Thing> runOff = new ArrayList<Thing>();
		List<ThingOnBelt> goneHolders = new ArrayList<ThingOnBelt>();
		for (ThingOnBelt holder : objects) {
			holder.x += dx * speed;
			holder.y += dy * speed;
			if (isOffBelt(holder)) {
				goneHolders.add(holder);
				runOff.add(holder.thing);
			}
		}
		objects.removeAll(goneHolders);
		return runOff;
	}

	private boolean isOffBelt(ThingOnBelt holder) {
		if (orientation.equals(Belt.Orientation.EAST) && (holder.x > stop.x)) {
			return true;
		}
		if (orientation.equals(Belt.Orientation.SOUTH) && (holder.y > stop.y)) {
			return true;
		}
		return false;
	}

	public boolean hasThings() {
		return !objects.isEmpty();
	}

	public float x() {
		return graphicsX;
	}

	public float y() {
		return graphicsY;
	}

	public float height() {
		return graphicsHeight;
	}

	public float width() {
		return graphicsWidth;
	}

	public List<ThingOnBelt> getThingHoldersCopy() {
		return new ArrayList<ThingOnBelt>(objects);
	}

}
