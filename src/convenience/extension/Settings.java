package convenience.extension;

import processing.core.PApplet;

import g4p_controls.*;

public class Settings extends PApplet {

	private static ConvenienceExtension parent;
	
	GCheckbox doCloseBrackets; 
	GCheckbox doAutocomplete;
	// GCheckbox doAskForDocumentation;
	GButton closeApplication;
	
	
	// TODO: Settings windows doesn't work when opened the second time;
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
	  surface.setTitle("Settings");
	  
	  doCloseBrackets = new GCheckbox(this, 20, 20, 300, 40);
	  doCloseBrackets.setIconAlign(GAlign.LEFT, GAlign.MIDDLE);
	  doCloseBrackets.setText("Close Brackets");
	  doCloseBrackets.setOpaque(false);
	  doCloseBrackets.setSelected(true);
	  doCloseBrackets.addEventHandler(this, "closeButtonCallback");
	  
	  doAutocomplete = new GCheckbox(this, 20, 60, 300, 40);
	  doAutocomplete.setIconAlign(GAlign.LEFT, GAlign.MIDDLE);
	  doAutocomplete.setText("Autocomplete");
	  doAutocomplete.setOpaque(false);
	  doAutocomplete.setSelected(true);
	  doAutocomplete.addEventHandler(this, "autocompleteCallback");
	  
	  /* doAskForDocumentation = new GCheckbox(this, 20, 100, 300, 40);
	  doAskForDocumentation.setIconAlign(GAlign.LEFT, GAlign.MIDDLE);
	  doAskForDocumentation.setText("Ask to fill in Documentation");
	  doAskForDocumentation.setOpaque(false);
	  doAskForDocumentation.setSelected(true);
	  doAskForDocumentation.addEventHandler(this, "askForDocumentationCallback"); */
	  
	  closeApplication = new GButton(this, 20, 340, 360, 40);
	  closeApplication.setText("Close Application");
	  closeApplication.setOpaque(true);
	  closeApplication.addEventHandler(this, "closeApplicationCallback");
	}
	
	// Intercept Exit-Command or IDE will be closed too
	public void exit() {
		dispose();
	}
	
	public void draw() { }
	
	public void closeButtonCallback(GCheckbox source, GEvent event) {
		parent.bracketCloser.isRunning = doCloseBrackets.isSelected();
	}
	
	public void autocompleteCallback(GCheckbox source, GEvent event) {
		parent.autocomplete.isRunning = doAutocomplete.isSelected();
	}
	
	/* public void askForDocumentationCallback(GCheckbox source, GEvent event) {
		parent.doAskForDocumentation = doAskForDocumentation.isSelected();
	} */
	
	public void closeApplicationCallback(GButton source, GEvent event) {
		System.out.println("Goodbye!");
		parent.isRunning = false;
	}
}
