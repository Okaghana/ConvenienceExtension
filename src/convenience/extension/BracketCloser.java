package convenience.extension;

import processing.app.ui.Editor;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class BracketCloser implements KeyListener {
	
	private Editor editor;
	
	// define which characters should get closed and with what
	// for the sake of understanding all opening or closing chars will be called "brackets"
	private char[] openingChars = { '(', '[', '{', '"', '\'' };
	private char[] closingChars = { ')', ']', '}', '"', '\'' };
	
	// Indentation Depth
	private String tabSpace = "    ";
	private long methonLastCalled = 0L;
	
	public  boolean isRunning = true;
	
	public BracketCloser(Editor _editor) {
		editor = _editor;
		editor.getTextArea().addKeyListener(this);
	}
	
	public void keyPressed(KeyEvent key) {
		if (!isRunning) return;
		
		// Check if pressed key is in openingChars
		for (int i = 0; i < openingChars.length; i++) {
			// Close Brackets
			if (key.getKeyChar() == openingChars[i]) {
				// Place around selection if there is one.
				if (editor.isSelectionActive()) {
					addFullBracket(i);
				} else {
					addClosingBracket(i);
				}
			} else if (key.getKeyCode() ==  KeyEvent.VK_ENTER) {
				if (System.currentTimeMillis() - methonLastCalled >= 50)  // Apply cooldown because methods gets fired multiple times
					repositionBracket();
				methonLastCalled = System.currentTimeMillis();
	
			}
			
		}
	}

	// add closing bracket and set cursor inside the brackets
	private void addClosingBracket(int index) {
		editor.insertText("" + closingChars[index]);

		int cursorPosition = editor.getCaretOffset();
		editor.setSelection(cursorPosition - 1, cursorPosition - 1);  // Start end End of Selection are the same
	}

	private void addFullBracket(int index) {
		int selectionStart = editor.getSelectionStart();
		int selectionEnd = editor.getSelectionStop();
		
		// The closing char will be placed before the IDE can place the opening char
		editor.setSelection(selectionEnd, selectionEnd);
		editor.insertText(Character.toString(closingChars[index]));
		editor.setSelection(selectionStart, selectionStart);	// So we have to place the cursor at the beginning
	}
	
	private void repositionBracket() {
		int cursorPosition = editor.getCaretOffset();
		
		if (cursorPosition == 0 || cursorPosition == editor.getText().length()) return;
		
		char charBefore = editor.getText(cursorPosition - 1, cursorPosition).charAt(0);
		char charAfter =  editor.getText(cursorPosition, cursorPosition + 1).charAt(0);
		
		// Only continue if the chars before and after the cursors are brackets
		for (int i = 0; i < openingChars.length; i++) {
			if(openingChars[i] == charBefore && closingChars[i] == charAfter) {
				editor.insertText("\n" + tabSpace);
			}
		}
	}

	public void keyReleased(KeyEvent key) {
	}

	public void keyTyped(KeyEvent key) {
	}
}
