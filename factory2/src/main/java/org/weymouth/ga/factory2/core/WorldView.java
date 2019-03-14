package org.weymouth.ga.factory2.core;

import java.util.List;

import processing.core.PApplet;

public class WorldView extends PApplet {

	World theWorld = null;

	public void settings() {
		size(World.WIDTH, World.HEIGHT);
		WorldViewController.getController().register(this);
	}

	public void setTheWorld(World w) {
		theWorld = w;
	}

	public void close() {
		this.exit();
	}

	public void draw() {
		background(100);
		if (theWorld != null)
			drawTheWorld();
	}

	private void drawTheWorld() {
		fill(150);
		stroke(255);
		List<Belt> belts = theWorld.getBelts();
		for (Belt b : belts) {
			rect(b.x(), b.y(), b.width(), b.height());
		}
		stroke(0);
		for (Belt b : belts) {
			List<ThingOnBelt> holders = b.getThingHoldersCopy();
			for (ThingOnBelt t : holders) {
				if (t != null) {
					Color c = t.thing.color;
					float r = (float) t.thing.radius;
					float x = (float) t.x;
					float y = (float) t.y;
					fill(c.r, c.g, c.b);
					ellipse(x, y, r, r);
				}
			}
		}
	}

}
