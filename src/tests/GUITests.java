package tests;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.junit.Test;

import fileManager.FileManager;
import gui.GUI;

public class GUITests {

	GUI g = new GUI();
	String[] paths = new String[] { "jUnitTests\\rules.cf", "jUnitTests\\ham.log", "jUnitTests\\spam.log" };

	public void start() throws FileNotFoundException {
		g.promptPath();
		FileManager.getInstance().addPaths(paths);
		Object[][] data = FileManager.getInstance().readRules();
		g.addFrameContent(data);
	}

	@Test
	public void testUpdateResults() throws FileNotFoundException {
		start();
		g.updateResults(new int[] { 1, 2 });
		assertEquals("Falsos Positivos: " + 1, g.getFp().getText());
		assertEquals("Falsos Negativos: " + 2, g.getFn().getText());
	}
}
