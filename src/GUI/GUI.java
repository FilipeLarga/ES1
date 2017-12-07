package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
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

import FileManager.FileReader;

/**
 * Graphics Unit Interface. The user can see the rules and select the
 * correspondent weights, as well as see the result of the configuration
 * 
 */

public class GUI {

private JFrame frame;
private String rulesPath;
private String hamPath;
private String spamPath;
private JPanel panel;

private Object[][] data;

/**
* Creates the gui
*/

public GUI() {
try {
promptPath();
} catch (FileNotFoundException e) {
System.err.println("Erro no path do rules.cf");
System.exit(1);
}
frame = new JFrame("Anti-Spam Filter");
frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
frame.setLayout(new BorderLayout());
addFrameContent();
frame.setSize(670, 522);
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
System.out.println(data[0][0] + "" + data[0][1]);

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

right_panel.add(result);
right_panel.add(fp);
right_panel.add(fn);

frame.add(right_panel, BorderLayout.CENTER);
}

/**
* Prompts the user for the path to the files
* 
* @throws FileNotFoundException
*             If the path to rules.cf is incorrect
*/

public void promptPath() throws FileNotFoundException {

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

// rulesPath = f1.getText();
rulesPath = "C:/Users/Filipe/Desktop/ES/rules.cf";
hamPath = f2.getText();
spamPath = f3.getText();
data = new FileReader().readRules(rulesPath);

}
}