package convenience.extension;

import processing.app.syntax.JEditTextArea;
import processing.app.ui.Editor;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPopupMenu;
import javax.swing.Action;
import javax.swing.JMenuItem;

public class Autocomplete implements KeyListener {

	private Editor editor;
	private JEditTextArea textArea;
	private VariableScanner scanner;
	private Entry popup;
	
	private String defaultTriggerString = ".abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ ";  // On which keys the Autocomplete Triggers
	private String wordSepperators = "[ ,\\.\\{\\}\\[\\]\\(\\)\"'\\+\\*;]+";  // Splits at the following characters: ,.(){}[]"'+*; and the space
	
	public List<Character> triggers;
	public int trigger_size = 3;
	public boolean isRunning = true;

	public Autocomplete(Editor _editor) {
		editor = _editor;
		textArea = editor.getTextArea();
		textArea.addKeyListener(this);
		
		scanner = new VariableScanner(editor);
		
		triggers = new ArrayList<>();
		for (char ch : defaultTriggerString.toCharArray()) {
			triggers.add(ch);
		}
		
	}

	public void keyPressed(KeyEvent key) {
		if (!isRunning) return;
		
		// Run if one of the Triggering keys is Pressed
		if (triggers.contains(key.getKeyChar())) {
			// Check if length of word >= trigger_size
			String[] words = textArea.getLineText(textArea.getCaretLine()).split(wordSepperators);
			if (words[words.length - 1].length() >= 3) {
				int cursor = textArea.getCaretPosition();
				int line = textArea.getCaretLine();
				int xPosition = textArea.offsetToX(line, cursor);
				int yPosition = textArea.lineToY(line);
				
				// TODO: Compensate for font size and zoom level when adding offset
				
				popup = new Entry(words[words.length - 1], textArea, scanner);
				popup.show(xPosition + 5, yPosition + 22);
			}
		}
	}

	public void keyReleased(KeyEvent key) {}

	public void keyTyped(KeyEvent key) {}
}

// TODO: Add arrow button control in the menu
@SuppressWarnings("serial")
class Entry extends JPopupMenu{

	private JEditTextArea parent;
	private VariableScanner scanner;
	
	private String stub;
	
	public Entry(String _stub, JEditTextArea _parent, VariableScanner _scanner) {
		super();
		parent = _parent;
		scanner = _scanner;
		
		stub = _stub;
		
		parent.add(this);
		setFocusable(false);
		
		lookForVariable(stub, parent);
	}
	
	public void show(int x, int y) {
		super.show(parent, x, y);
	}
	
	private void lookForVariable(String word, JEditTextArea editor) {
		List<Snippet> variables = scanner.getVariables();
		for (Snippet var : variables) {
			if (var.pattern.startsWith(word)) {
				JMenuItem item = new JMenuItem(new MenuClicked(var, this));
				item.setText(var.variableName + "            " + var.type);
				add(item);
			}
		}
	}
	
	public void menuCallback(Snippet snippet){
		int line = parent.getLineOfOffset(parent.getCaretPosition());
		String lineText = parent.getLineText(line);
		
		int startsAt = parent.getLineStartOffset(line) + lineText.indexOf(stub);
		int endsAt = startsAt + stub.length() + 1;
		
		parent.setSelectionStart(startsAt);
		parent.setSelectionEnd(endsAt);
		parent.setSelectedText(snippet.variableName);
	}
}

class MenuClicked implements Action{
	
	Entry parent;
	Snippet snippet;
	
	public MenuClicked(Snippet _snippet, Entry _parent) {
		snippet = _snippet;
		parent = _parent;
	}
	
	public void actionPerformed(ActionEvent e) {
		parent.menuCallback(snippet);
	}

	public Object getValue(String key) {
		return null;
	}

	public void putValue(String key, Object value) {}

	public void setEnabled(boolean b) {}

	public boolean isEnabled() {
		return true;
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {}

	public void removePropertyChangeListener(PropertyChangeListener listener) {}
	
}