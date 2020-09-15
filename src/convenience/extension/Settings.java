package convenience.extension;

import processing.core.PApplet;

import g4p_controls.*;

public class Settings extends PApplet {

	private static ConvenienceExtension parent;

	GLabel label;
	GCheckbox closeBrackets;

	public static void run(ConvenienceExtension _parent) {
		parent = _parent;

		Settings settings = new Settings();
		PApplet.runSketch(new String[] { "Settings" }, settings);
	}

	public void settings() {
		size(400, 400);
	}

	public void setup() {
		label = new GLabel(this, 10, 15, 100, 15, "Settings");
		closeBrackets = new GCheckbox(this, 20, 30, 200, 60, "Close Brackets?");

		parent.setSettings(false);
	}

	public void draw() {
	}
}
