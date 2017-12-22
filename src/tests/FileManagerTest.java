package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

import FileManager.FileManager;

class FileManagerTest {

	// Insert correct paths here: rules, ham, spam.

	String[] paths = new String[] { "C:\\Users\\Filipe\\Desktop\\ES\\rules.cf",
			"C:\\Users\\Filipe\\Desktop\\ES\\ham.log", "C:\\Users\\Filipe\\Desktop\\ES\\ham.log" };

	@Test
	void testAddPaths() {

		FileManager.getInstance().addPaths(paths);

		assertEquals(paths[0], FileManager.getInstance().getRulesPath());
		assertEquals(paths[1], FileManager.getInstance().getHamPath());
		assertEquals(paths[2], FileManager.getInstance().getSpamPath());

		assertNotEquals(paths[2], FileManager.getInstance().getRulesPath());
		assertNotEquals(paths[0], FileManager.getInstance().getHamPath());
		assertNotEquals(paths[1], FileManager.getInstance().getSpamPath());
	}

	@Test
	void testReadRules() throws FileNotFoundException {
		Object[] testRule = { "BAYES_00", 0.0 };
		Object[][] matrix = FileManager.getInstance().readRules();
		assertEquals(matrix[0][0], testRule[0]);
		assertEquals(matrix[0][1], testRule[1]);
	}

	@Test
	void testEvaluate() {

	}

	@Test
	void testSaveConfig() {

	}

	@Test
	void testValidate() {

		FileManager.getInstance().addPaths(new String[] { "", "", "" });
		assertFalse(FileManager.getInstance().validate());

		FileManager.getInstance().addPaths(new String[] { paths[0], paths[1], paths[2] });
		assertTrue(FileManager.getInstance().validate());

		FileManager.getInstance().addPaths(new String[] { paths[0], "", "" });
		assertFalse(FileManager.getInstance().validate());

		FileManager.getInstance().addPaths(new String[] { paths[0], paths[1], "" });
		assertFalse(FileManager.getInstance().validate());

	}

}
