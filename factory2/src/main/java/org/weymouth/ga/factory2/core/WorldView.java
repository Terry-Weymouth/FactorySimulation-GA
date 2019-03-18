package org.weymouth.ga.factory2.core;

import java.util.List;

import processing.core.PApplet;
import processing.core.PImage;

public class WorldView extends PApplet {

	World theWorld = null;
	ColorPicker cp;
	Color worldThreshold = null;
	boolean thresholdSet = false;

	public void settings() {
		size(World.WIDTH, World.HEIGHT);
		WorldViewController.getController().register(this);
		cp = new ColorPicker(5, 600, 200, 150, 0xFFFFFF);
	}

	public void setTheWorld(World w) {
		theWorld = w;
	}
	
	public boolean getThresholdSetFlag() {
		return thresholdSet;
	}
	
	public void close() {
		this.exit();
	}

	public void draw() {
		background(100);
		if (thresholdSet && theWorld != null) {
			checkButtons();
			drawTheWorld();			
		} else if (worldThreshold != null && theWorld != null) {
			System.out.println("World Threshold - " + worldThreshold);
			theWorld.setSorterThreshold(worldThreshold);
			thresholdSet = true;
			worldThreshold = null;
		}
		cp.render();
	}

	private void checkButtons() {
		//if(mousePressed){
		//	  if(mouseX>x && mouseX <x+w && mouseY>y && mouseY <y+h){
		//	   println("The mouse is pressed and over the button");
		// TODO Auto-generated method stub
		//
	}

	private void drawTheWorld() {
		fill(150);
		stroke(255);
		List<Belt> belts = theWorld.getBelts();
		for (Belt b : belts) {
			if (b instanceof CornerBelt) {
				arc(b.x(), b.y() + b.height(), 2.0f * b.width(), 2.0f * b.height(), PI + HALF_PI, PI + PI, PIE);
			} else {
				rect(b.x(), b.y(), b.width(), b.height());
				if (b instanceof SorterBelt) {
					stroke(100, 100);
					float x1 = b.x();
					float y1 = b.y() + b.height();
					float x2 = x1 + b.width();
					float y2 = y1 - b.height();
					line(x1, y1, x2, y2);
					stroke(255);
				}
			}
			if (b instanceof SorterBelt) {
				Color c = ((SorterBelt) b).threshold;
				float start = (b.width() - 34.0f) / 2.0f;
				float x = b.x() + start;
				float y = b.y() - 12.0f;
				float w = 10.0f;
				float h = 10.0f;
				fill(c.r, 0, 0);
				ellipse(x + 5.0f, y + 5.0f, w, h);
				x += 12.0f;
				fill(0, c.g, 0);
				ellipse(x + 5.0f, y + 5.0f, w, h);
				x += 12.0f;
				fill(0, 0, c.b);
				ellipse(x + 5.0f, y + 5.0f, w, h);
				fill(150);
			}
			if (b instanceof SensorBelt) {
				Color c = ((SensorBelt) b).getColor();
				float x = b.x() + (b.width() / 2.0f) - 5.0f;
				float y = b.y() - 12.0f;
				float w = 10.0f;
				float h = 10.0f;
				if (c == null) {
					noFill();
					rect(x, y, w, h);
					line(x, y, x + w, y + h);
					y -= 12.0f;
					rect(x, y, w, h);
					line(x, y, x + w, y + h);
					y -= 12.0f;
					rect(x, y, w, h);
					line(x, y, x + w, y + h);
				} else {
					fill(0, 0, c.b);
					ellipse(x + 5.0f, y + 5.0f, w, h);
					y -= 12.0f;
					fill(0, c.g, 0);
					ellipse(x + 5.0f, y + 5.0f, w, h);
					y -= 12.0f;
					fill(c.r, 0, 0);
					ellipse(x + 5.0f, y + 5.0f, w, h);
				}
				fill(150);
			}
		}
		stroke(0);
		for (Belt b : belts) {
			List<Thing> things = b.getThingsCopy();
			for (Thing t : things) {
				if (t != null) {
					stroke(255);
					if (t.matched)
						stroke(0);
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

	// https://forum.processing.org/one/topic/processing-color-picker.html
	public class ColorPicker {
		int x, y, w, h, c, ct;
		PImage cpImage;
		boolean initialized = false;
		int count = 0;

		public ColorPicker(int x, int y, int w, int h, int c) {
			this.x = x;
			this.y = y;
			this.w = w;
			this.h = h;
			this.c = c;
			this.ct = c;

			cpImage = new PImage(w, h);

		}

		private void init() {
			if ( initialized ) return;
			// draw color.
			int cw = w - 60;
			for (int i = 0; i < cw; i++) {
				float nColorPercent = i / (float) cw;
				float rad = (-360 * nColorPercent) * (PI / 180);
				int nR = (int) (cos(rad) * 127 + 128) << 16;
				int nG = (int) (cos(rad + 2 * PI / 3) * 127 + 128) << 8;
				int nB = (int) (Math.cos(rad + 4 * PI / 3) * 127 + 128);
				int nColor = nR | nG | nB;

				setGradient(i, 0, 1, h / 2, 0xFFFFFF, nColor);
				setGradient(i, (h / 2), 1, h / 2, nColor, 0x000000);
			}

			// draw black/white.
			drawRect(cw, 0, 30, h / 2, 0xFFFFFF);
			drawRect(cw, h / 2, 30, h / 2, 0);

			// draw grey scale.
			for (int j = 0; j < h; j++) {
				int g = 255 - (int) (j / (float) (h - 1) * 255);
				drawRect(w - 30, j, 30, 1, color(g, g, g));
			}
			initialized = true;
		}

		private void setGradient(int x, int y, float w, float h, int c1, int c2) {
			float deltaR = red(c2) - red(c1);
			float deltaG = green(c2) - green(c1);
			float deltaB = blue(c2) - blue(c1);

			for (int j = y; j < (y + h); j++) {
				int c = color(red(c1) + (j - y) * (deltaR / h), green(c1) + (j - y) * (deltaG / h),
						blue(c1) + (j - y) * (deltaB / h));
				cpImage.set(x, j, c);
			}
		}

		private void drawRect(int rx, int ry, int rw, int rh, int rc) {
			for (int i = rx; i < rx + rw; i++) {
				for (int j = ry; j < ry + rh; j++) {
					cpImage.set(i, j, rc);
				}
			}
		}

		public void render() {
			init();
			image(cpImage, x, y);
			if ((worldThreshold == null) && mousePressed && mouseX >= x && mouseX < x + w && mouseY >= y && mouseY < y + h) {
				c = get(mouseX, mouseY);
				worldThreshold = new Color((int)red(c), (int)green(c), (int)blue(c));
			}
			fill(c);
			rect(x, y + h + 10, 20, 20);
		}
	}

}
