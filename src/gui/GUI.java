package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableCellRenderer;

import antiSpamFilter.AntiSpamFilterAutomaticConfiguration;
import fileManager.FileManager;

/**
 * Graphics Unit Interface. The user can see the rules and select the
 * correspondent weights, as well as see the result of the configuration
 * 
 */

public class GUI {

	private JFrame frame;
	private JPanel panel;

	private JLabel fp;
	private JLabel fn;

	JButton random;
	JButton auto;
	JButton avaliate;
	JButton save;

	private JTable table;
	private Object[][] data;

	public GUI() {
		frame = new JFrame("Anti-Spam Filter");
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.setSize(670, 522);
		frame.setResizable(false);
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation((int) dimension.getWidth() / 2 - (frame.getWidth() / 2),
				(int) dimension.getHeight() / 2 - (frame.getHeight() / 2));
	}

	/**
	 * @param data
	 *            The JTable wich will be displayed
	 * 
	 *            Adds the components to the frame
	 */

	@SuppressWarnings("serial")
	public void addFrameContent(Object[][] data) {
		this.data = data;

		JPanel bottom_panel = new JPanel();
		JPanel left_panel = new JPanel();
		JPanel right_panel = new JPanel();

		// bottom panel components

		random = new JButton("Gerar Aleatoriamente");
		auto = new JButton("Gerar Automaticamente");
		avaliate = new JButton("Avaliar Configurações");
		save = new JButton("Gravar Configurações");

		bottom_panel.add(random);
		bottom_panel.add(auto);
		bottom_panel.add(avaliate);
		bottom_panel.add(save);

		frame.add(bottom_panel, BorderLayout.SOUTH);

		// left panel components

		String[] columnNames = { "Regras", "Pesos" };

		table = new JTable(data, columnNames) {
			@Override
			public boolean isCellEditable(int row, int column) {
				if (column == 0)
					return false;
				return true;
			}
		};

		JScrollPane scrollPane = new JScrollPane(table);

		left_panel.add(scrollPane);

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		table.setDefaultRenderer(Object.class, centerRenderer);

		frame.add(left_panel, BorderLayout.WEST);

		// right panel components

		right_panel.setLayout(new GridLayout(15, 1));

		JLabel result = new JLabel("                      Resultado");
		fp = new JLabel("Falsos Positivos: ");
		fn = new JLabel("Falsos Negativos: ");

		random.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				randomButton();
			}
		});

		save.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					FileManager.getInstance().saveConfig(data, FileManager.getInstance().getRulesPath());
				} catch (FileNotFoundException | UnsupportedEncodingException e) {
				}
			}
		});

		avaliate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					updateResults(FileManager.getInstance().evaluate(data));
				} catch (FileNotFoundException e1) {
				}

			}
		});

		right_panel.add(result);
		right_panel.add(fp);
		right_panel.add(fn);

		auto.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					new AntiSpamFilterAutomaticConfiguration().start();
					double[] results = FileManager.getInstance()
							.readResults("experimentBaseDirectory/referenceFronts/AntiSpamFilterProblem.rf");
					double[] weights = FileManager.getInstance()
							.readWeights("experimentBaseDirectory/referenceFronts/AntiSpamFilterProblem.rs");
					updateWeights(weights);
					updateResults(new int[] { (int) results[0], (int) results[1] });

					// HV.Boxplot compilation

					String[] params = new String[2];

					params[0] = "C:\\Program Files\\R\\R-3.4.3\\bin\\x64\\Rscript.exe";

					params[1] = "C:\\Users\\Filipe\\git\\ES1-2017-IC1-68\\experimentBaseDirectory\\AntiSpamStudy\\R\\HV.Boxplot.R";

					String[] envp = new String[1];

					envp[0] = "Path=C:\\Program Files\\R\\R-3.4.1\\bin\\x64";

					Process p = Runtime.getRuntime().exec(params, envp, new File(
							"C:\\Users\\Filipe\\git\\ES1-2017-IC1-68\\experimentBaseDirectory\\AntiSpamStudy\\R"));

					// AntiSpamStudy.tex compilation

					String[] params2 = new String[2];

					params2[0] = "C:\\Users\\Filipe\\AppData\\Local\\Programs\\MiKTeX 2.9\\miktex\\bin\\x64\\pdflatex.exe";

					params2[1] = "C:\\Users\\Filipe\\git\\ES1-2017-IC1-68\\experimentBaseDirectory\\AntiSpamStudy\\latex\\AntiSpamStudy.tex";

					String[] envp2 = new String[1];

					envp2[0] = "Path=C:\\Users\\Filipe\\AppData\\Local\\Programs\\MiKTeX 2.9\\miktex\\bin\\x64";

					Process p2 = Runtime.getRuntime().exec(params2, envp2, new File(
							"C:\\Users\\Filipe\\git\\ES1-2017-IC1-68\\experimentBaseDirectory\\AntiSpamStudy\\latex"));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		frame.add(right_panel, BorderLayout.CENTER);
		frame.setVisible(true);
	}

	/**
	 * Prompts the user for the path to the files using a JOptionPane
	 * 
	 * @return A vector of Strings with the paths to the files rules, ham and spam,
	 *         respectively
	 */

	public String[] promptPath() {

		panel = new JPanel(new BorderLayout());
		JPanel panel_north = new JPanel();
		JPanel panel_center = new JPanel();
		JPanel panel_south = new JPanel();

		JTextField f1 = new JTextField("", 20);
		JTextField f2 = new JTextField("", 20);
		JTextField f3 = new JTextField("", 20);

		JLabel path1 = new JLabel(" Rules.cf");
		JLabel path2 = new JLabel("  Ham.log");
		JLabel path3 = new JLabel("Spam.log");

		panel_north.add(path1);
		panel_north.add(f1);
		panel_center.add(path2);
		panel_center.add(f2);
		panel_south.add(path3);
		panel_south.add(f3);

		panel.add(panel_north, BorderLayout.NORTH);
		panel.add(panel_center, BorderLayout.CENTER);
		panel.add(panel_south, BorderLayout.SOUTH);

		String[] paths = new String[3];

		JOptionPane.showMessageDialog(null, panel, "Insira o path para os seguintes ficheiros", 3);

		paths[0] = f1.getText();
		paths[1] = f2.getText();
		paths[2] = f3.getText();

		return paths;

	}

	/**
	 * Displays an error message on the screen with the given message
	 * 
	 * @param message
	 *            Message to display on the error dialog
	 * @param option
	 *            Option type
	 * 
	 * @return integer according to the option selected
	 */

	public int displayError(String message, int option) {
		return JOptionPane.showConfirmDialog(null, message, null, option, JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Displays an error message on the screen with the given message
	 * 
	 * @param message
	 *            Message to display on the error dialog
	 * 
	 */

	public void displayError(String message) {
		JOptionPane.showMessageDialog(null, message);
	}

	/**
	 * Updates the results component on the GUI with the given results
	 * 
	 * @param results
	 *            The results of the configuration
	 */

	public void updateResults(int[] results) {
		fp.setText("Falsos Positivos: " + results[0]);
		fn.setText("Falsos Negativos: " + results[1]);
	}

	/**
	 * Displays the given weights into the table
	 * 
	 * @param weights
	 *            The weights to be displayed on the table
	 */

	public void updateWeights(double[] weights) {
		for (int i = 0; i < data.length; i++) {
			data[i][1] = weights[i];
			table.updateUI();
		}
	}

	/**
	 * Fill the weights with random doubles between -5 and 5
	 */
	public void randomButton() {
		for (int i = 0; i < data.length; i++) {
			data[i][1] = Math.round((ThreadLocalRandom.current().nextDouble(-5, 5) * 10)) / 10D;
			table.updateUI();
		}
	}

	/**
	 * Getter for the False Positives JLabel
	 * 
	 * @return the JLabel
	 */

	public JLabel getFp() {
		return fp;
	}

	/**
	 * Getter for the False Negatives JLabel
	 * 
	 * @return the JLabel
	 */

	public JLabel getFn() {
		return fn;
	}
}