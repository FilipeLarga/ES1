package FileManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class contains methods that operate on Files, with read and write
 * operations.
 * 
 * @author Filipe
 * @author Rita G
 * @author Rita A
 * @author Hussein
 *
 */

public class FileManager {

	private static FileManager instance = null;

	private File rules;
	private File ham;
	private File spam;
	private Object[][] matrix;
	private Object[] rulesArray;
	private int line;

	public static FileManager getInstance() {
		if (instance == null) {
			instance = new FileManager();
		}
		return instance;
	}

	/**
	 * Reads the rules from the {@link rules} file
	 * 
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
		matrix = new Object[rules.size()][2];
		for (int i = 0; i < rules.size(); i++) {
			matrix[i][0] = rules.get(i);
			matrix[i][1] = 0.0;
		}

		rulesArray = new Object[rules.size()];
		rules.toArray(rulesArray);
		scanner.close();
		return matrix;
	}

	/**
	 * Evaluates the given configuration to determine the false positives and false
	 * negatives
	 * 
	 * @param data
	 *            The configuration to be evaluated
	 * @return The result of the given configuration; the false positives and false
	 *         negatives
	 * @throws FileNotFoundException
	 *             if source is not found
	 */

	public int[] evaluate(Object[][] data) throws FileNotFoundException {

		int fp = 0;
		int fn = 0;

		Scanner scannerHam = new Scanner(ham);

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
							resultado += Double.parseDouble(aux);
						} catch (ClassCastException e) {
							resultado += (double) data[x][1];
						}
					}
				}
			}
			if (resultado > 0) {
				fp++;
			}
		}
		scannerHam.close();

		Scanner scannerSpam = new Scanner(spam);

		while (scannerSpam.hasNextLine()) {
			int resultado = 0;
			String line = scannerSpam.nextLine();
			String[] split = line.split("	");
			for (int i = 1; i < split.length; i++) {
				String rule = split[i];
				for (int x = 0; x < data.length; x++) {
					if (data[x][0].equals(rule)) {
						try {
							String aux = (String) data[x][1];
							resultado += Double.parseDouble(aux);
						} catch (ClassCastException e) {
							resultado += (double) data[x][1];
						}
					}
				}
			}
			if (resultado < 0) {
				fn++;
			}
		}
		scannerSpam.close();

		return new int[] { fp, fn };

	}

	/**
	 * 
	 * Saves the current configuration of weights in the file specified by the path
	 * 
	 * @param data
	 *            The content to be saved. This includes the rules and the
	 *            correspondent weights
	 * @param path
	 *            The path to the file where the configuration will be saved
	 * 
	 * @throws FileNotFoundException
	 *             If the given string does not denote an existing, writable regular
	 *             file and a new regular file of that name cannot be created, or if
	 *             some other error occurs while opening or creating the file
	 * @throws UnsupportedEncodingException
	 *             If the named charset is not supported
	 */

	public void saveConfig(Object[][] data, String path) throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter pw = new PrintWriter(path, "UTF-8");
		for (int i = 0; i < data.length; i++) {
			pw.write(data[i][0] + " " + data[i][1] + "\n");
		}
		pw.close();
	}

	/**
	 * Uses the paths to create the {@link File} instances of {@link #rules}
	 * {@link #ham} {@link #spam}
	 * 
	 * @param paths
	 *            the paths that will be added to the File Manager
	 */

	public void addPaths(String[] paths) {
		rules = new File(paths[0]);
		ham = new File(paths[1]);
		spam = new File(paths[2]);
	}

	/**
	 * Checks if the given paths correspond to files
	 * 
	 * @return <code> true </code> if the paths correspond to files;
	 *         <code> false </code> otherwise
	 */
	public boolean validate() {
		return rules.isFile() && ham.isFile() && spam.isFile();
	}

	/**
	 * Getter for the path of the rules file
	 * 
	 * @return the path of the rules file
	 */

	public String getRulesPath() {
		return rules.getPath();
	}

	/**
	 * Getter for the path of the ham file
	 * 
	 * @return the path of the ham file
	 */

	public String getHamPath() {
		return ham.getPath();
	}

	/**
	 * Getter for the path of the spam file
	 * 
	 * @return the path of the spam file
	 */

	public String getSpamPath() {
		return spam.getPath();
	}

	/**
	 * Getter for matrix
	 * 
	 * @return the matrix
	 */

	public Object[][] getMatrix() {
		return matrix;
	}

	/**
	 * Getter for rulesArray
	 * 
	 * @return the array with rules
	 */

	public Object[] getRulesArray() {
		return rulesArray;
	}

	/**
	 * Reads the preferred result from the file created by the JMetal algorithm
	 * "AntiSpamFilterProblem.rf". The preferred result is the one with the fewer
	 * false negatives.
	 * 
	 * 
	 * @return The result with the fewer false negatives, created by the JMetal
	 *         algorithm
	 * @throws FileNotFoundException
	 *             if source is not found
	 */

	public double[] readResults() throws FileNotFoundException {

		Scanner scanner = new Scanner(new File("experimentBaseDirectory/referenceFronts/AntiSpamFilterProblem.rf"));

		double[] results = null;

		boolean first = true;

		while (scanner.hasNextLine()) {
			String aux = scanner.nextLine();
			String[] split = aux.split(" ");
			double[] nextResults = new double[] { Double.parseDouble(split[0]), Double.parseDouble(split[1]) };

			if (first || results[1] > nextResults[1]) {
				first = false;
				results = nextResults;
				line++;
			} else if (results[1] == nextResults[1]) {
				if (results[0] > nextResults[0]) {
					results = nextResults;
					line++;
				}
			}
		}
		scanner.close();
		return results;
	}

	/**
	 * Reads the preferred weights from the file created by the JMetal algorithm
	 * "AntiSpamFilterProblem.rs"
	 * 
	 * 
	 * @return The weights that the JMetal algorithm considered optimal
	 * @throws FileNotFoundException
	 *             if source is not found
	 */

	public double[] readWeights() throws FileNotFoundException {

		Scanner scanner = new Scanner(new File("experimentBaseDirectory/referenceFronts/AntiSpamFilterProblem.rs"));
		int counter = 0;

		double[] weights = new double[335];

		while (scanner.hasNextLine() && counter < line) {
			String aux = scanner.nextLine();
			String[] split = aux.split(" ");

			counter++;
			if (line == counter) {
				for (int x = 0; x < split.length; x++) {
					weights[x] = Double.parseDouble(split[x]);
				}
			}
		}
		scanner.close();
		return weights;
	}

}