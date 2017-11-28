package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class GUI {

	private JFrame frame;
	String rules;
	String ham;
	String spam;
	private JPanel panel;

	public GUI() {
		promptPath();
		frame = new JFrame("Anti-Spam Filter");
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		addFrameContent();
		frame.setSize(800, 600);
		frame.setVisible(true);

	}

	private void addFrameContent() {
		JPanel bottom_panel = new JPanel();
		JPanel left_panel = new JPanel();

		// bottom panel components

		JButton random = new JButton("Gerar Aleatóriamente");
		JButton auto = new JButton("Gerar Automáticamente");
		JButton avaliate = new JButton("Avaliar Configurações");
		JButton save = new JButton("Gravar Configurações");

		bottom_panel.add(random);
		bottom_panel.add(auto);
		bottom_panel.add(avaliate);
		bottom_panel.add(save);

		frame.add(bottom_panel, BorderLayout.SOUTH);

		// left panel components

		left_panel.setLayout(new GridLayout(2, 1));

		JPanel label_panel = new JPanel();

		label_panel.add(new JLabel("Regras"));
		label_panel.add(new JLabel("Pesos"));

		JPanel list_panel = new JPanel();

		DefaultListModel<String> rulesModel = new DefaultListModel<>();
		JList<String> rulesList = new JList<>(rulesModel);

		rulesModel.addElement("rule1");
		rulesModel.addElement("rule2");

		DefaultListModel<JTextField> textModel = new DefaultListModel<>();
		JList<JTextField> textList = new JList<>(textModel);

		textModel.addElement(new JTextField("123"));

		list_panel.add(rulesList);
		list_panel.add(textList);

		left_panel.add(label_panel);
		left_panel.add(list_panel);

		frame.add(left_panel, BorderLayout.WEST);
	}

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
