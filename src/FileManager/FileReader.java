package FileManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileReader {
	/**
	 * * Reads the rules from a file, given the path of said file * * @param path *
	 * File's path * @return Object[][] A matrix of rules (String) and the
	 * correspondent weight * (Integer). * @throws FileNotFoundException * If the
	 * given path doesn't correspond to a file
	 */
	public Object[][] readRules(String path) throws FileNotFoundException {
		File f = new File(path);
		Scanner scanner = new Scanner(f);
		ArrayList<String> rules = new ArrayList<>();
		// ArrayList<Integer> weights = new ArrayList<>();
		while (scanner.hasNextLine()) {
			rules.add(scanner.nextLine());
		}

		Object[][] matrix = new Object[rules.size()][2];
		for (int i = 0; i < rules.size(); i++) {
			matrix[i][0] = rules.get(i);
			matrix[i][1] = 0;
		}
		scanner.close();
		return matrix;
	}
}
