package org.weymouth.ga.factory2.main;

import java.util.List;
import java.util.Random;

import org.weymouth.ga.factory2.core.Color;
import org.weymouth.ga.factory2.core.Thing;
import org.weymouth.ga.factory2.core.World;
import org.weymouth.ga.factory2.core.WorldView;
import org.weymouth.ga.factory2.core.WorldViewController;

import processing.core.PApplet;

public class ViewWorldSimulation {

	WorldViewController worldController = WorldViewController.getController();
	World theWorld = new World();
	private final Random random = new Random();

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
		for (int i = 0; i < 200; i++) {
			int r = random.nextInt(256);
			int g = random.nextInt(256);
			int b = random.nextInt(256);
			Thing t = new Thing(new Color(r, g, b));
			theWorld.add(t);
			runForAwhile();
		}
	}

	private void runForAwhile() {
		for (int i = 0; i < 20; i++) {
			List<Thing> overflow = update();
//			if (!overflow.isEmpty()) {
//				for (Thing t : overflow) {
//					System.out.println("Done in the world: " + t);
//				}
//			}
		}
	}

	private void runAllOut() {
		while (theWorld.isLive()) {
			List<Thing> overflow = update();
//			if (!overflow.isEmpty()) {
//				for (Thing t : overflow) {
//					System.out.println("Done in the world: " + t);
//				}
//			}
		}
	}

	private List<Thing> update() {
		try {
			Thread.sleep(20);
		} catch (InterruptedException ignore) {
		}
		return theWorld.update();
	}

}
