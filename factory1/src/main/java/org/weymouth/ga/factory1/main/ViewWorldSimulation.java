package org.weymouth.ga.factory1.main;

import java.util.List;

import org.weymouth.ga.factory1.core.Color;
import org.weymouth.ga.factory1.core.Thing;
import org.weymouth.ga.factory1.core.World;
import org.weymouth.ga.factory1.core.WorldView;
import org.weymouth.ga.factory1.core.WorldViewController;

import processing.core.PApplet;

public class ViewWorldSimulation {

	WorldViewController worldController = WorldViewController.getController();
	World theWorld = new World();

	public ViewWorldSimulation() {
		PApplet.main(WorldView.class);
		worldController.initialize(theWorld);
	}

	public void driveExampleSimulation() {
		runInNewThings();
		runAllOut();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException ignore) {
		}
		worldController.close();
	}

	private void runInNewThings() {
		for (int r = 0; r < 256; r += 32) {
			for (int g = 0; g < 256; g += 32) {
				for (int b = 0; b < 256; b += 32) {
					Thing t = new Thing(new Color(r, g, b));
					theWorld.add(t);
					runForAwhile();
				}
			}
		}
	}

	private void runForAwhile() {
		for (int i = 0; i < 20; i++) {
			List<Thing> overflow = update();
			if (!overflow.isEmpty()) {
				for (Thing t : overflow) {
					System.out.println("Done in the world: " + t);
				}
			}
		}
	}

	private void runAllOut() {
		while (theWorld.isLive()) {
			List<Thing> overflow = update();
			if (!overflow.isEmpty()) {
				for (Thing t : overflow) {
					System.out.println("Done in the world: " + t);
				}
			}
		}
	}

	private List<Thing> update() {
		try {
			Thread.sleep(10);
		} catch (InterruptedException ignore) {
		}
		return theWorld.update();
	}

}
