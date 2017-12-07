package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

/**
 * Graphics Unit Interface. The user can see the rules and select the
 * correspondent weights, as well as see the result of the configuration
 * 
 */

public class GUI {

	private JFrame frame;
	String rules;
	String ham;
	String spam;
	private JPanel panel;

	/**
	 * Creates the gui
	 */

	public GUI() {
		promptPath();
		frame = new JFrame("Anti-Spam Filter");
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		addFrameContent();
		frame.setSize(700, 522);
		frame.setResizable(false);
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation((int) dimension.getWidth() / 2 - (frame.getWidth() / 2),
				(int) dimension.getHeight() / 2 - (frame.getHeight() / 2));
		frame.setVisible(true);

	}

	/**
	 * Adds the components to the frame
	 */

	private void addFrameContent() {
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

		Object[][] data = { { "Regra 1", 0 }, { "Regra 2", 0 }, { "Regra 2", 0 }, { "Regra 2", 0 }, { "Regra 2", 0 },
				{ "Regra 2", 0 }, { "Regra 2", 0 }, { "Regra 2", 0 }, { "Regra 2", 0 }, { "Regra 2", 0 },
				{ "Regra 2", 0 }, { "Regra 2", 0 }, { "Regra 2", 0 }, { "Regra 2", 0 }, { "Regra 2", 0 },
				{ "Regra 2", 0 }, { "Regra 2", 0 }, { "Regra 2", 0 }, { "Regra 2", 0 }, { "Regra 2", 0 },
				{ "Regra 2", 0 }, { "Regra 2", 0 }, { "Regra 2", 0 }, { "Regra 2", 0 }, { "Regra 2", 0 },
				{ "Regra 2", 0 }, { "Regra 2", 0 }, { "Regra 2", 0 }, { "Regra 2", 0 }, { "Regra 2", 0 },
				{ "Regra 2", 0 }, { "Regra 2", 0 }, { "Regra 2", 0 }, { "Regra 2", 0 }, { "Regra 2", 0 },
				{ "Regra 2", 0 }, { "Regra 2", 0 }, { "Regra 2", 0 }, { "Regra 2", 0 }, { "Regra 2", 0 },
				{ "Regra 2", 0 }, { "Regra 2", 0 }, { "Regra 2", 0 }, { "Regra 2", 0 }, { "Regra 2", 0 },
				{ "Regra 2", 0 }, { "Regra 2", 0 }, { "Regra 2", 0 }, { "Regra 2", 0 }, { "Regra 2", 0 },
				{ "Regra 2", 0 }, { "Regra 2", 0 }, { "Regra 2", 0 }, { "Regra 2", 0 }, { "Regra 2", 0 },
				{ "Regra 2", 0 }, { "Regra 2", 0 }, { "Regra 2", 0 }, { "Regra 2", 0 }, { "Regra 2", 0 },
				{ "Regra 2", 0 }, { "Regra 2", 0 }, { "Regra 2", 0 }, { "Regra 2", 0 }, { "Regra 2", 0 },
				{ "Regra 2", 0 }, { "Regra 2", 0 }, { "Regra 2", 0 }, { "Regra 2", 0 }, { "Regra 2", 0 },
				{ "Regra 2", 0 }, { "Regra 2", 0 }, { "Regra 2", 0 }, { "Regra 2", 0 }, { "Regra 2", 0 } };

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

		frame.add(left_panel, BorderLayout.WEST);

		// right panel components

		right_panel.setLayout(new GridLayout(15, 1));

		JLabel result = new JLabel("                      Resultado");
		JLabel fp = new JLabel("Falsos Positivos: ");
		JLabel fn = new JLabel("Falsos Negativos: ");

		right_panel.add(result);
		right_panel.add(fp);
		right_panel.add(fn);

		frame.add(right_panel, BorderLayout.CENTER);
	}

	/**
	 * Prompts the user for the path to the files
	 */

	public void promptPath() {
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

		JOptionPane.showMessageDialog(null, panel, "Insira o path para os seguintes ficheiros", 3);

		rules = f1.getText();
		ham = f2.getText();
		spam = f3.getText();

	}
}
