/**
 * A bunch of Tools to ease the editing in the Processing IDE
 *
 * ##copyright##
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General
 * Public License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA  02111-1307  USA
 *
 * @author   Okaghana
 * @modified 15.09.2020
 * @version  0.1
 */

package convenience.extension;

import processing.app.Base;
import processing.app.tools.Tool;
import processing.app.ui.Editor;

public class ConvenienceExtension implements Tool {
	private final String NAME = "Convenience Extension";
	private final String VERSION = "0.1";
	private final String AUTHOR = "Okaghana";
	private final String URL = "https://github.com/Okaghana/ConvenienceExtension";

	Base base;
	Editor editor;
	BracketCloser bracketCloser;
	Settings settings;

	boolean isRunning = false;
	boolean closeBrackets = true;
	boolean askForDocumentation = true;

	public void init(Base base) {
		this.base = base;
	}

	public void run() {
		editor = base.getActiveEditor();

		if (!isRunning) {
			bracketCloser = new BracketCloser(editor);

			isRunning = true;
			System.out.println("" + NAME + ". Version " + VERSION + ". Author " + AUTHOR + "  " + URL);
		} else {
			Settings.run(this);
			System.out.println("" + NAME + " is already running. Running Settings instead");
		}
	}

	public String getMenuTitle() {
		return NAME;
	}

	public void setSettings(boolean doCloseBrackets) {

	}
}
