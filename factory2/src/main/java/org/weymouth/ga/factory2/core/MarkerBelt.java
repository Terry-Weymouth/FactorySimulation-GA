package org.weymouth.ga.factory2.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MarkerBelt implements Belt {

	private final Random random = new Random();
	private final Location start, stop;
	private final double length;
	private final double dx, dy;
	private final float graphicsX, graphicsY, graphicsWidth, graphicsHeight;
	private final Orientation orientation;
	private double speed = 1.0;
	private double width = 100.0;
	protected List<Thing> objects = new ArrayList<Thing>();
	protected Belt next = null;
	Color threshold = null;

	public MarkerBelt(Location startLocation, double l, Belt.Orientation o) {
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
	
	public void linkUp(Belt nextBelt) {
		next = nextBelt;
	}
	
	public void setSpeed(double s) {
		speed = s;
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
		t.x = x;
		t.y = y;
		objects.add(t);
	}

	public void handoff(Thing t) {
		testSelected(t);
		objects.add(t);
	}
	
	private void testSelected(Thing t) {
		t.matched = (t.color.r <= threshold.r && t.color.g <= threshold.g && t.color.b <= threshold.b);
	}
	
	public List<Thing> update() {
		List<Thing> goneThings = new ArrayList<Thing>();
		for (Thing t : objects) {
			t.x += dx * speed;
			t.y += dy * speed;
			if (isOffBelt(t)) {
				goneThings.add(t);
			}
		}
		objects.removeAll(goneThings);
		if (next != null) {
			for (Thing t: goneThings) {
				next.handoff(t);
			}
			goneThings = new ArrayList<Thing>();
		}
		return goneThings;
	}

	private boolean isOffBelt(Thing t) {
		if (orientation.equals(Belt.Orientation.EAST) && (t.x > stop.x)) {
			return true;
		}
		if (orientation.equals(Belt.Orientation.SOUTH) && (t.y > stop.y)) {
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

	public List<Thing> getThingsCopy() {
		return new ArrayList<Thing>(objects);
	}
}