/******************************************************************************
 * By SILIBRE - www.silibre.com - 2009
 * contact@silibre.com
 ******************************************************************************
 * IEditorServices.java
 *
 *
 ******************************************************************************
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 ******************************************************************************
 *  Ver         Who             When                    What
 *  0.1         prog         28 oct. 2010                 Creation
 */

package editor;

import java.awt.Dimension;
import java.io.File;
import javax.swing.ImageIcon;
import main.IActionsIde;

/**
 *
 */
public interface IEditorServices {
    void updateMenuBar();
    void newFile();
    void openFile();
    void saveFile();
    void saveFileAs();
    File getFile();
    void about();
    short getUIState();
    void setUIState(short newState);
    void upDateStatusLine();
    void setLineNumStatusLine(int lineNum);
    ImageIcon createImageIcon(String path);
    void showMess(String message, String title);
    Dimension getFrameDim();
    void editViewCut();
    void editViewPaste();
    void editViewSlctAll();
    void editViewCopy();
    boolean editViewIsSelectedText();
    void editViewClearAll();
    void editViewShowText(String txt);
    String editViewGetText();
    IActionsIde getActions();

}
