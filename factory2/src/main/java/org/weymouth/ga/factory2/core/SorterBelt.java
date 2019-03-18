package org.weymouth.ga.factory2.core;

import java.util.ArrayList;
import java.util.List;

public class SorterBelt extends StreightBelt {

	List<Thing> sortedOut = new ArrayList<Thing>();
	List<Thing> runoff = new ArrayList<Thing>();
	Belt drain = null;
	Color threshold = new Color(255, 127, 255);

	public SorterBelt(Location startLocation, double l, Belt.Orientation o) {
		super(startLocation, l, o);
	}

	@Override
	public List<Thing> update() {
		for (Thing t : objects) {
			float dx = (float)t.x - x();
			float dy = height() - ((float)t.y - y());
			if ((dx >= dy) && isSortedOut(t)) {
				sortedOut.add(t);
				runoff.add(t);
			}
		}
		objects.removeAll(runoff);
		drainSortedOut();
		return super.update();
	}

	private boolean isSortedOut(Thing t) {
		Color c = t.color;
		return c.g <= threshold.g && c.r <= threshold.r && c.b <= threshold.b;
	}

	private void drainSortedOut() {
		if (drain != null) {
			for (Thing t: sortedOut) {
				drain.handoff(t);
			}
		}
		sortedOut = new ArrayList<Thing>();
	}

	public void linkUpDrain(StreightBelt theDrain) {
		drain = theDrain;
	}
}
