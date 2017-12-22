package tests;

import java.io.IOException;

import org.junit.Test;

import antiSpamFilter.AntiSpamFilterAutomaticConfiguration;
import antiSpamFilter.AntiSpamFilterProblem;
import fileManager.FileManager;

public class AutomaticTests {

	String[] paths = new String[] { "jUnitTests\\rules.cf", "jUnitTests\\ham.log", "jUnitTests\\spam.log" };

	@Test
	public void test() throws IOException {

		FileManager.getInstance().addPaths(paths);
		FileManager.getInstance().readRules();
		new AntiSpamFilterAutomaticConfiguration().start();
	}

}
