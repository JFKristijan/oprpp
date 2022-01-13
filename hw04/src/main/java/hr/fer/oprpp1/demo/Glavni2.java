package hr.fer.oprpp1.demo;

import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilderProvider;
import hr.fer.zemris.lsystems.gui.LSystemViewer;
import hr.fer.zemris.lsystems.impl.LSystemBuilderImpl;

public class Glavni2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LSystemViewer.showLSystem(createKochCurve2(LSystemBuilderImpl::new));
	}
	private static LSystem createKochCurve2(LSystemBuilderProvider provider) {
		String[] data = new String[] {
		"origin 0.05 0.4",
		"",
		"",
		"axiom F",
		"angle 0",
		"unitLength 0.9",
		"unitLengthDegreeScaler 1.0 / 3.0",
		"",
		"production F F+F--F+F",
		"command F draw 1",
		"command + rotate 60",
		"command - rotate -60"
		};
		return provider.createLSystemBuilder().configureFromText(data).build();
		}
}
