package antiSpamFilter;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.uma.jmetal.problem.impl.AbstractDoubleProblem;
import org.uma.jmetal.solution.DoubleSolution;

import FileManager.FileManager;

public class AntiSpamFilterProblem extends AbstractDoubleProblem {

	private Object[] rules;
	private Object[][] matrix;

	public AntiSpamFilterProblem() {
		this(10);
	}

	private void addRules() {
		for (int i = 0; i < rules.length; i++)
			matrix[i][0] = rules[i];
	}

	public AntiSpamFilterProblem(Integer numberOfVariables) {
		rules = FileManager.getInstance().getRulesArray();
		matrix = new Object[rules.length][2];
		addRules();

		setNumberOfVariables(numberOfVariables);
		setNumberOfObjectives(2);
		setName("AntiSpamFilterProblem");

		List<Double> lowerLimit = new ArrayList<>(getNumberOfVariables());
		List<Double> upperLimit = new ArrayList<>(getNumberOfVariables());

		for (int i = 0; i < getNumberOfVariables(); i++) {
			lowerLimit.add(-5.0);
			upperLimit.add(5.0);
		}

		setLowerLimit(lowerLimit);
		setUpperLimit(upperLimit);
	}

	public void evaluate(DoubleSolution solution) {

		double[] x = new double[getNumberOfVariables()];
		for (int i = 0; i < solution.getNumberOfVariables(); i++) {
			x[i] = solution.getVariableValue(i);
		}

		for (int i = 0; i < rules.length; i++) {
			matrix[i][1] = x[i];
		}

		try {
			int results[] = FileManager.getInstance().evaluate(matrix);

//			System.out.println(results[0] + " " + results[1]);

			solution.setObjective(0, results[0]);
			solution.setObjective(1, results[1]);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
