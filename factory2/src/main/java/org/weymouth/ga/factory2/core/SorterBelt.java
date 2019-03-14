package org.weymouth.ga.factory2.core;

import java.util.ArrayList;
import java.util.List;

public class SorterBelt extends StreightBelt {

	List<Thing> sortedOut = new ArrayList<Thing>();
	List<Thing> runoff = new ArrayList<Thing>();

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
		return super.update();
	}

	private boolean isSortedOut(Thing t) {
		Color c = t.color;
		return c.g < 128;
	}

	public List<Thing> dumpSortedOut() {
		List<Thing> dump = sortedOut;
		sortedOut = new ArrayList<Thing>();
		return dump;
	}
}
