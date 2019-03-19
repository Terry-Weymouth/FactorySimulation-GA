package org.weymouth.ga.factory2.io;

import java.io.File;
import java.io.IOException;

import org.junit.Test;
import org.weymouth.ga.factory2.core.FactoryState;

public class DataFileWriterTest {

	@Test
	public void oneShortTest() throws IOException {

		File temp = File.createTempFile("TestDataFileWriter", ".txt");

		System.out.println("Temp file : " + temp.getAbsolutePath());

		String absolutePath = temp.getAbsolutePath();

		FactoryState testState = new FactoryState();
		
		DataFileWriter writer = new DataFileWriter(absolutePath);
		writer.writeFactoryState(testState);
		
		writer.writeFactoryState(testState);
		writer.close();
	}
}
