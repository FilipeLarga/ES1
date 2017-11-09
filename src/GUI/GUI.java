package GUI;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GUI {

	private JFrame frame;
	String rules;
	String ham;
	String spam;
	private JPanel panel;

	public GUI() {
		promptPath();
		// frame = new JFrame("ISCTE Searcher");
		//
		// // para que o botao de fechar a janela termine a aplicacao
		// frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		//
		// // conteudo em sequencia da esquerda para a direita
		// frame.setLayout(new BorderLayout());
		//
		// addFrameContent();
		//
		// // para que a janela se redimensione de forma a ter todo o seu
		// conteudo
		// // visivel
		// frame.pack();

	}

	private void addFrameContent() {

		// Window path

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

		// JOptionPane jop = new JOptionPane(null, panel)
		JOptionPane.showMessageDialog(null, panel, "Insira o path para os seguintes ficheiros", 3);
		// f1.setPreferredSize(new Dimension(200, 20));
		// f1.setPreferredSize(new Dimension(200, 20));
		// f1.setPreferredSize(new Dimension(200, 20));

		rules = f1.getText();
		ham = f2.getText();
		spam = f3.getText();

	}
}
