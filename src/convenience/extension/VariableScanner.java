package convenience.extension;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import processing.app.syntax.JEditTextArea;
import processing.app.ui.Editor;
import processing.core.PApplet;

public class VariableScanner implements KeyListener {
	
	private Editor editor;
	private JEditTextArea textArea;
	
	private List<Snippet> variables;
	private List<String> types;  // Stores Strings of all primitive types.
	private List<String> classes;  // Stores names of the classes created.
	private String wordSepperators = "[ ,\\.\\{\\}\\[\\]\\(\\)\"'\\+\\*;]+";  // Splits at the following characters: ,.(){}[]"'+*; and the space
	
	public VariableScanner(Editor _editor) {
		editor = _editor;
		textArea = editor.getTextArea();
		editor.getTextArea().addKeyListener(this);
		
		variables = new ArrayList<Snippet>();
		classes = new ArrayList<String>();
		types = new ArrayList<String>();
		
		// Add primitive types
		types.add("int");
		types.add("long");
		types.add("short");
		types.add("byte");
		types.add("char");
		types.add("float");
		types.add("double");
		types.add("boolean");
		types.add("String");
		
		// Load Snippets from file
		File path = new File(ConvenienceExtension.class.getProtectionDomain().getCodeSource().getLocation().getPath().replace("tool/ConvenienceExtension.jar", "data/AutocompleteSnippets"));
		String[] lines = PApplet.loadStrings(path);
		for (String line : lines) {
			// Split line after the comma and create a Pattern from it
			String[] parts = line.split(", ");
			Snippet var = new Snippet(parts[1], parts[0], "");
			variables.add(var);
		}
	}
	
	public List<Snippet> getVariables(){
		return variables;
	}
	
	// TODO: Add additions like ignoring <> or []
	// TODO: Remove Variable if its deleted (constantly checking for it or so)
	public void keyPressed(KeyEvent key) {
		String[] lines = textArea.getText().split("\n");
		for (int i = 0; i < lines.length; i++) {
			checkLine(lines[i]);
		}
	}
	
	public void keyTyped(KeyEvent key) {}
	
	public void keyReleased(KeyEvent key) {}
	
	
	private void checkLine(String text) {
		List<String> keywords = new ArrayList<String>(types);
		keywords.addAll(classes);
		
		for (String keyword : keywords) {
			if (text.contains(keyword)) {
				findVariable(text, keyword);
			}
		}
	}
	
	private void findVariable(String text, String keyword) {
		String[] words = text.split(wordSepperators);
		for (int i = 0; i < words.length - 2; i++) {	// We add the -2 because we only want to check for words that are finished being typed. otherwise we add partially typed words
			if (keyword.equals(words[i])) {
				
				// Make sure its not already in there
				boolean alreadyAdded = false;
				for (Snippet var : variables) {
					if (var.variableName.equals(words[i+1])) alreadyAdded = true;
				}
				
				if (!alreadyAdded) {
					variables.add(new Snippet(words[i+1], words[i+1], keyword));
				}
			}
		}
	}
	
	@SuppressWarnings("unused")
	private void print() {
		for (Snippet var : variables) {
			System.out.println("Start: " + var.pattern + "\t\t\tFull: " + var.variableName + "\t\t\tType: " + var.type);
		}
	}
}