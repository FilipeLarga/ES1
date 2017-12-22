package antiSpamFilter;

import java.io.FileNotFoundException;

import FileManager.FileManager;
import GUI.GUI;

public class Main {

	public static void main(String[] args) {
		GUI gui = new GUI();

		String[] paths = gui.promptPath();

		System.out.println(paths[0] + "\n" + paths[1] + "\n" + paths[2]);

		FileManager.getInstance().addPaths(paths);

		while (!FileManager.getInstance().validate()) {
			if (gui.displayError("Erro a validar os paths, tente de novo", 2) == 0) {
				paths = gui.promptPath();
				FileManager.getInstance().addPaths(paths);
			} else
				System.exit(1);
		}

		try {
			Object[][] data = FileManager.getInstance().readRules();
			gui.addFrameContent(data);
		} catch (FileNotFoundException e) {
			gui.displayError("Erro a ler as regras");
			System.exit(1);
		}

	}

}
