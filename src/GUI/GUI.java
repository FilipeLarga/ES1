package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
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

import FileManager.FileManager;

/**
 * Graphics Unit Interface. The user can see the rules and select the
 * correspondent weights, as well as see the result of the configuration
 * 
 */

public class GUI {

	private JFrame frame;
	private String rulesPath;
	private JPanel panel;

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
	 * Adds the components to the frame
	 */

	public void addFrameContent(Object[][] data) {
		JPanel bottom_panel = new JPanel();
		JPanel left_panel = new JPanel();
		JPanel right_panel = new JPanel();

		// bottom panel components

		JButton random = new JButton("Gerar Aleatoriamente");
		JButton auto = new JButton("Gerar Automaticamente");
		JButton avaliate = new JButton("Avaliar Configurações");
		JButton save = new JButton("Gravar Configurações");

		bottom_panel.add(random);
		bottom_panel.add(auto);
		bottom_panel.add(avaliate);
		bottom_panel.add(save);

		frame.add(bottom_panel, BorderLayout.SOUTH);

		// left panel components

		String[] columnNames = { "Regras", "Pesos" };
		@SuppressWarnings("serial")
		JTable table = new JTable(data, columnNames) {
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
		JLabel fp = new JLabel("Falsos Positivos: ");
		JLabel fn = new JLabel("Falsos Negativos: ");

		random.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				for (int i = 0; i < data.length; i++) {
					data[i][1] = ThreadLocalRandom.current().nextInt(-5, 6);
					table.updateUI();
				}
			}
		});

		save.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					FileManager.getInstance().saveConfig(data, rulesPath);
				} catch (FileNotFoundException | UnsupportedEncodingException e) {
				}
			}
		});

		avaliate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					System.out.println(FileManager.getInstance().evaluate(data));
				} catch (FileNotFoundException e1) {
				}

			}
		});

		right_panel.add(result);
		right_panel.add(fp);
		right_panel.add(fn);

		frame.add(right_panel, BorderLayout.CENTER);
		frame.setVisible(true);
	}

	/**
	 * Prompts the user for the path to the files, using JOptionPane
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

		// paths[0] = f1.getText();
		// paths[1] = f2.getText();
		// paths[2] = f3.getText();

		paths[0] = "C:/Users/Filipe/Desktop/ES/rules.cf";
		paths[1] = "C:/Users/Filipe/Desktop/ES/ham.txt";
		paths[2] = "C:/Users/Filipe/Desktop/ES/spam.log";

		return paths;

	}

	/**
	 * 
	 * @param message
	 *            Message to display on the error dialog
	 * @param option
	 * @return integer according to the option selected
	 */

	public int displayError(String message, int option) {
		return JOptionPane.showConfirmDialog(null, message, null, option, JOptionPane.ERROR_MESSAGE);
	}
}