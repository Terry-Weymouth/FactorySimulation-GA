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
			if (b instanceof CornerBelt) {
				arc(b.x(), b.y() + b.height(), 2.0f * b.width(), 2.0f * b.height(), PI+HALF_PI, PI + PI, PIE);
			} else {
				rect(b.x(), b.y(), b.width(), b.height());
				if (b instanceof SorterBelt) {
					stroke(100,100);
					float x1 = b.x();
					float y1 = b.y() + b.height();
					float x2 = x1 + b.width();
					float y2 = y1 - b.height();
					line(x1,y1,x2,y2);
					stroke(255);
				}
			}
			if (b instanceof SensorBelt) {
				Color c = ((SensorBelt)b).getColor();
				float x = b.x() + 5.0f;
				float y = b.y() - 10.0f;
				float w = b.width() - 10.0f;
				float h = 5.0f;
				if (c == null) {
					noFill();
					rect(x,y,w,h);
					line(x,y,x+w,y+h);
				} else {
					fill(c.r, c.g, c.b);
					rect(x,y,w,h);
				}
				fill(150);
			}
		}
		stroke(0);
		for (Belt b : belts) {
			List<Thing> things = b.getThingsCopy();
			for (Thing t : things) {
				if (t != null) {
					Color c = t.color;
					float r = (float) t.radius;
					float x = (float) t.x;
					float y = (float) t.y;
					fill(c.r, c.g, c.b);
					ellipse(x, y, r, r);
				}
			}
		}
	}

}
