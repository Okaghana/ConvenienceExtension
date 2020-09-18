package convenience.extension;

import processing.core.PApplet;

import g4p_controls.*;
import java.awt.Font;

public class Settings extends PApplet {

	private static ConvenienceExtension parent;
	
	GCheckbox doCloseBrackets; 
	GCheckbox doAutocomplete; 

	public static void run(ConvenienceExtension _parent) {
		parent = _parent;

		Settings settings = new Settings();
		PApplet.runSketch(new String[] { "Settings" }, settings);
	}

	public void settings() {
		size(400, 400);
	}

	public void setup() {
	  G4P.messagesEnabled(false);
	  G4P.setGlobalColorScheme(GCScheme.BLUE_SCHEME);
	  G4P.setMouseOverEnabled(false);
	  surface.setTitle("Sketch Window");
	  this.noLoop();
	  doCloseBrackets = new GCheckbox(this, 20, 20, 300, 40);
	  doCloseBrackets.setIconAlign(GAlign.LEFT, GAlign.MIDDLE);
	  doCloseBrackets.setText("Close Brackets");
	  doCloseBrackets.setOpaque(false);
	  doAutocomplete = new GCheckbox(this, 20, 60, 300, 40);
	  doAutocomplete.setIconAlign(GAlign.LEFT, GAlign.MIDDLE);
	  doAutocomplete.setText("Autocomplete");
	  doAutocomplete.setOpaque(false);
	  
		parent.setSettings(false);
	}

	public void draw() {
	}
	
	// Intercept Exit-Command or IDE will be closed to
	public void exit() {
		dispose();
	}
}
