/******************************************************************************
 * By SILIBRE - www.silibre.com - 2009
 * contact@silibre.com
 ******************************************************************************
 * editor.java
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
 *  0.1         prog         26 oct. 2010                 Creation
 */

package editor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import main.IActionsIde;
import utils.Version;

/**
 *
 * Create the Editor. TODO : use class (event-listener) to handle uiState and update UI component.
 */
public class Editor extends JPanel implements IEditorServices, IEditorConsts{
        //ide tools access
        protected IActionsIde actions;
	// ui components
	protected IdeMenuBar ideMenuBar;
	protected EditView editView;
	protected StatusLine statusLine;
	protected JFrame frame; // main frame
	protected MyFile fileEdit;
	//ui dimensions
	protected Dimension frameDim;
	protected short uiState;

	public Editor(IActionsIde actions, JFrame ideFrame){
            this.actions = actions;
            frame = ideFrame;
            frameDim = frame.getSize();
            createEditorGUI();
	}

        public Editor(){} //for sub classes

        private void createEditorGUI() {
            frameDim = frame.getSize();
            //Create and set up the content pane.

            this.ideMenuBar = new IdeMenuBar(this);
            frame.setJMenuBar(ideMenuBar.CreateMenuBar());
            fileEdit = new MyFile(this);
            editView = new EditView(this, "",0,0) ;// we don't show editor until file selection by user
            statusLine = new StatusLine(this) ;
            //setup ui logic
            uiState = NEW;
            ideMenuBar.updateMenuBar();
            statusLine.updateStatus();
        }

        public void updateMenuBar(){
            this.ideMenuBar.updateMenuBar();
        }

        // new file
	public void newFile(){
            if(uiState == UNSAFE){
                    showMess("Attention le fichier actuel n'est pas sauvegarder !!", "Information");
                    saveFileAs();
            }else{
                    String title = new String("Nouveau_fchier");
                    editView.clearAll();
                    editView.setTitle(title);
                    fileEdit.newFile(title);
                    statusLine.updateStatus();
                    showEditor();
                    uiState = NEW;
            }
	}
	// open file
	public void openFile(){
            JFileChooser fileCh = new JFileChooser();
            fileCh.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            int returnVal = fileCh.showOpenDialog(frame);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fileCh.getSelectedFile();
                    fileEdit.readFile(file);
                    String title = file.getName();
                    editView.setTitle(title);
                    uiState = SAFE;
                    statusLine.updateStatus();
                    showEditor();
            }
	}
	// save file
	public void saveFile(){
            if (fileEdit.fileAsName())
            {
                    fileEdit.writeFile(null);
                    uiState = SAFE;
                    statusLine.updateStatus();
            }
            else{
                    showMess("Votre fichier est savegard� sans avoir de nom ! Utilisez la commande enregistrer sous...", "Information");
                    saveFileAs();
            }
	}
	// save file as ...
	public void saveFileAs(){
            JFileChooser fileCh = new JFileChooser();
            fileCh.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            int returnVal = fileCh.showSaveDialog(frame);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fileCh.getSelectedFile();
                    //check asm extension
                    String nf = file.getName();
                    int i = nf.indexOf(".asm",0);
                    if (i>0){
                            fileEdit.writeFile(file);
                            uiState = SAFE;
                            editView.setTitle(fileEdit.getFileName());
                            statusLine.updateStatus();
                    }else{
                            showMess("Votre fichier doit contenir l'extension .asm ; par exemple : test.asm ", "Information");
                            saveFileAs();
                    }
            }
            else {
                    //command cancelled by user
            }
	}

    public File getFile() {
        return this.fileEdit.getFile();
    }

    // about box
    public void about(){
        JOptionPane pane = new JOptionPane();
        String message = new String(" Environnement de d�veloppement pour le processeur Aleph8\n\n "+"Version : "+Version.getIt()+"\n\nAuteur : Laurent Authier Tabanon\n authier.tabanon@free.fr");
        pane.showMessageDialog(frame,message,"A propos de...",JOptionPane.INFORMATION_MESSAGE);
    }

    // Returns an ImageIcon, or null if the path was invalid.
    public ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = IdeMenuBar.class.getResource(path);
        if (imgURL != null) {
                return new ImageIcon(imgURL);
        } else {
                System.err.println("Couldn't find file: " + path);
                return null;
        }
    }

    public void showMess(String message, String title) {
        JOptionPane.showMessageDialog(null, message,title, JOptionPane.INFORMATION_MESSAGE);
    }

    public short getUIState(){
        return this.uiState;
    }

    public void setUIState(short newState) {
        this.uiState = newState;
    }

    public void upDateStatusLine() {
        this.statusLine.updateStatus();
    }

    public void setLineNumStatusLine(int lineNum) {
        this.statusLine.setLineNum(lineNum);
    }

    public Dimension getFrameDim() {
        return this.frameDim;
    }

    public void editViewCopy() {
        this.editView.copy();
    }

    public void editViewCut() {
        this.editView.cut();
    }

    public void editViewPaste() {
        this.editView.paste();
    }

    public void editViewSlctAll() {
        this.editView.slctAll();
    }

    public boolean editViewIsSelectedText() {
        return this.editView.isSelectedText();
    }

    public void editViewClearAll() {
        this.editView.clearAll();
    }

    public void editViewShowText(String txt) {
        this.editView.viewLoadCode(txt);
    }

    public String editViewGetText() {
        return this.editView.getCode();
    }

    public IActionsIde getActions() {
        return this.actions;
    }


    private void showEditor(){
        //Add ui components to the content pane.
        frame.getContentPane().add(editView, BorderLayout.CENTER);
        frame.getContentPane().add(statusLine, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

		
}



	



