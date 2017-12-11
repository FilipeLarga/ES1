package FileManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileManager {

	private static FileManager instance = null;

	private File rules;
	private File ham;
	private File spam;

	public static FileManager getInstance() {
		if (instance == null) {
			instance = new FileManager();
		}
		return instance;
	}

	/**
	 * Reads the rules from a file, given the path of said file
	 * 
	 * @param path
	 *            File's path
	 * @return Object[][] A matrix of rules (String) and the correspondent weight
	 *         (Integer).
	 * @throws FileNotFoundException
	 *             If the given path doesn't correspond to a file
	 */

	public Object[][] readRules() throws FileNotFoundException {
		Scanner scanner = new Scanner(rules);
		ArrayList<String> rules = new ArrayList<>();
		while (scanner.hasNextLine()) {
			String aux = scanner.nextLine();
			String[] split = aux.split(" ");
			rules.add(split[0]);
		}
		Object[][] matrix = new Object[rules.size()][2];
		for (int i = 0; i < rules.size(); i++) {
			matrix[i][0] = rules.get(i);
			matrix[i][1] = 0;
		}
		scanner.close();
		return matrix;
	}

	/**
	 * Saves the current configuration of weights in the file specified by the path
	 * 
	 * @param Object[][]
	 *            A matrix of rules and the correspondent weight
	 * @param String
	 *            The desired path of the file
	 * @throws UnsupportedEncodingException
	 *             If the named charset is not supported
	 * @throws FileNotFoundException
	 *             If the given string does not denote an existing, writable regular
	 *             file and a new regular file of that name cannot be created, or if
	 *             some other error occurs while opening or creating the file
	 */

	public int[] evaluate(Object[][] data) throws FileNotFoundException {

		int fp = 0;
		int fn = 0;

		Scanner scannerHam = new Scanner(ham);
		Scanner scannerSpam = new Scanner(spam);

		while (scannerHam.hasNextLine()) {
			int resultado = 0;
			String line = scannerHam.nextLine();
			String[] split = line.split("	");
			for (int i = 1; i < split.length; i++) {
				String rule = split[i];
				for (int x = 0; x < data.length; x++) {
					if (data[x][0].equals(rule)) {
						try {
							String aux = (String) data[x][1];
							resultado += Integer.parseInt(aux);
						} catch (ClassCastException e) {
							resultado += (int) data[x][1];
						}
					}
				}
			}
			if (resultado < 0) {
				fp++;
			}
		}
		scannerHam.close();

		while (scannerSpam.hasNextLine()) {
			int resultado = 0;
			String line = scannerHam.nextLine();
			String[] split = line.split("	");
			for (int i = 1; i < split.length; i++) {
				String rule = split[i];
				for (int x = 0; x < data.length; x++) {
					if (data[x][0].equals(rule)) {
						try {
							String aux = (String) data[x][1];
							resultado += Integer.parseInt(aux);
						} catch (ClassCastException e) {
							resultado += (int) data[x][1];
						}
					}
				}
				if (resultado > 0) {
					fn++;
				}
			}

			scannerSpam.close();
		}
		return new int[] { fp, fn };
	}

	public void saveConfig(Object[][] data, String path) throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter pw = new PrintWriter(path, "UTF-8");
		for (int i = 0; i < data.length; i++) {
			pw.write(data[i][0] + " " + data[i][1] + "\n");
		}
		pw.close();
	}

	/**
	 * Checks if the given paths correspond to files
	 * 
	 * @param paths
	 *            A vector of strings with the paths of the files
	 * 
	 * @return true if all paths correspond to files; false otherwise
	 */

	public boolean validate(String[] paths) {
		rules = new File(paths[0]);
		ham = new File(paths[1]);
		spam = new File(paths[2]);

		return rules.isFile() && ham.isFile() && spam.isFile();
	}
}