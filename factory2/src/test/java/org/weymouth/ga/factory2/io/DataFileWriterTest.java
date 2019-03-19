package org.weymouth.ga.factory2.io;

import java.io.File;
import java.io.IOException;

import org.junit.Test;
import org.weymouth.ga.factory2.core.FactoryState;
import org.weymouth.ga.factory2.core.World;

public class DataFileWriterTest {

	@Test
	public void oneShortTest() throws IOException {

		File temp = File.createTempFile("TestDataFileWriter", ".txt");

		System.out.println("Temp file : " + temp.getAbsolutePath());

		String absolutePath = temp.getAbsolutePath();
		
		World theWorld = new World();

		FactoryState testState = new FactoryState(theWorld);
		
		DataFileWriter writer = new DataFileWriter(absolutePath);
		writer.writeFactoryState(testState);
		writer.close();
		
		writer = new DataFileWriter(absolutePath);
		writer.writeFactoryState(testState);
		writer.close();
	}
}
