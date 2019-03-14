package org.weymouth.ga.factory1.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Belt {

	public enum Orientation {
		HORIZONTAL, VETRICAL
	};

	private final Random random = new Random();
	private final Location start, stop;
	private final double length;
	private final double dx, dy;
	private final float graphicsX, graphicsY, graphicsWidth, graphicsHeight;
	private final Orientation orientation;
	private double speed = 1.0;
	private double width = 100.0;
	private List<ThingOnBelt> objects = new ArrayList<ThingOnBelt>();

	public Belt(Location startLocation, double l, Orientation o) {
		orientation = o;
		start = startLocation;
		length = l;
		if (orientation.equals(Orientation.HORIZONTAL)) {
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
		if (orientation.equals(Orientation.HORIZONTAL)) {
			y += jiggle;
		} else {
			x += jiggle;
		}
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
		if (orientation.equals(Orientation.HORIZONTAL) && (holder.x > stop.x)) {
			return true;
		}
		if (orientation.equals(Orientation.VETRICAL) && (holder.y > stop.y)) {
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
