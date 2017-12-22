package tests;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

import org.junit.Test;

import fileManager.FileManager;

public class FileManagerTests {

	// Insert correct paths here: rules, ham, spam.

	String[] paths = new String[] { "jUnitTests\\rules.cf", "jUnitTests\\ham.log", "jUnitTests\\spam.log" };

	@Test
	public void testReadRules() throws FileNotFoundException {
		Object[] testRule = { "BAYES_00", 0.0 };
		Object[][] matrix = FileManager.getInstance().readRules();
		assertEquals(matrix[0][0], testRule[0]);
		assertEquals(matrix[0][1], testRule[1]);
	}

	@Test
	public void testEvaluate() throws FileNotFoundException {

		FileManager.getInstance().addPaths(
				new String[] { "jUnitTests\\rules.cf", "jUnitTests\\hamtest.txt", "jUnitTests\\spamtest.txt" });

		Object[] rules = FileManager.getInstance().getRulesArray();
		Object[][] data = new Object[rules.length][2];

		for (int i = 0; i < rules.length; i++) {
			data[i][0] = rules[i];
			data[i][1] = 0.0;
		}

		data[0][1] = 1.0;
		data[4][1] = -2.0;

		int[] results = FileManager.getInstance().evaluate(data);

		assertEquals(results[0], 1);
		assertEquals(results[1], 1);

		FileManager.getInstance().addPaths(paths);
	}

	@Test
	public void testSaveConfig() throws FileNotFoundException, UnsupportedEncodingException {
		Object[][] testMatrix = new Object[1][2];
		testMatrix[0][0] = "BAYES_00";
		testMatrix[0][1] = 2.0;
		FileManager.getInstance().saveConfig(testMatrix, "jUnitTests/saveConfigTest.txt");

		Scanner scanner = new Scanner(new File("jUnitTests/saveConfigTest.txt"));

		assertEquals("BAYES_00	2.0", scanner.nextLine());

		scanner.close();
	}

	@Test
	public void testAddPaths() {
		FileManager.getInstance().addPaths(paths);

		assertEquals(paths[0], FileManager.getInstance().getRulesPath());
		assertEquals(paths[1], FileManager.getInstance().getHamPath());
		assertEquals(paths[2], FileManager.getInstance().getSpamPath());

		assertNotEquals(paths[2], FileManager.getInstance().getRulesPath());
		assertNotEquals(paths[0], FileManager.getInstance().getHamPath());
		assertNotEquals(paths[1], FileManager.getInstance().getSpamPath());
	}

	@Test
	public void testValidate() {
		FileManager.getInstance().addPaths(new String[] { "", "", "" });
		assertFalse(FileManager.getInstance().validate());

		FileManager.getInstance().addPaths(new String[] { paths[0], paths[1], paths[2] });
		assertTrue(FileManager.getInstance().validate());

		FileManager.getInstance().addPaths(new String[] { paths[0], "", "" });
		assertFalse(FileManager.getInstance().validate());

		FileManager.getInstance().addPaths(new String[] { paths[0], paths[1], "" });
		assertFalse(FileManager.getInstance().validate());
	}

	@Test
	public void testReadResults() throws FileNotFoundException {

		double[] results = FileManager.getInstance().readResults("jUnitTests/resultsTest");
		assertEquals(4.0, results[0]);
		assertEquals(0.0, results[1]);

	}

	@Test
	public void testReadWeights() throws FileNotFoundException {
		double[] weights = FileManager.getInstance().readWeights("jUnitTests/weightsTest");
		assertEquals(-4.5, weights[0]);
		assertEquals(-1.4, weights[1]);
	}

}
