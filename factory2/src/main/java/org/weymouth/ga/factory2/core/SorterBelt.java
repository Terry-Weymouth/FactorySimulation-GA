package org.weymouth.ga.factory2.core;

import java.util.ArrayList;
import java.util.List;

public class SorterBelt extends StreightBelt {

	List<Thing> sortedOut = new ArrayList<Thing>();
	List<Thing> runoff = new ArrayList<Thing>();
	Belt drain = null;

	public SorterBelt(Location startLocation, double l, Belt.Orientation o) {
		super(startLocation, l, o);
	}

	@Override
	public List<Thing> update() {
		for (Thing t : objects) {
			if (isSortedOut(t)) {
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
		return c.g < 128;
	}

	private void drainSortedOut() {
		if (drain != null) {
			for (Thing t: sortedOut) {
				double x = 50.0 - (t.y - drain.y());
				double y = 0.0;
				x += drain.x();
				y += drain.y();
				t.x = x;
				t.y = y;
				drain.handoff(t);
			}
		}
		sortedOut = new ArrayList<Thing>();
	}

	public void linkUpDrain(StreightBelt theDrain) {
		drain = theDrain;
	}
}
