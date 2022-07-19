/******************************************************************************
 * By SILIBRE - www.silibre.com - 2009
 * contact@silibre.com
 ******************************************************************************
 * EventsCalls.java
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

import utils.Help;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 *
 * events
 */
public class EventsCalls  implements ActionListener, IEditorConsts {
    IEditorServices services;

    public EventsCalls(IEditorServices services) {
        this.services = services;
    }

    public void actionPerformed(ActionEvent e){
        JMenuItem source = (JMenuItem)(e.getSource());
        String sourceTxt = source.getText();
        String s = "Action event detected."+NEWLINE+"    Event source: " + source.getText()
        + " (an instance of " + getClassName(source) + ")";
        //System.out.println(s + newline);
        //file menu
        if (sourceTxt.equals("Nouveau")) {
                services.newFile();
        }
        if (sourceTxt.equals( "Ouvrir")) {
                services.openFile();
        }
        if (sourceTxt.equals( "Enregistrer")) {
                services.saveFile();
        }
        if (sourceTxt.equals("Enregistrer sous...")) {
                services.saveFileAs();
        }
        if (sourceTxt.equals("Quitter")){
                if(services.getUIState() == UNSAFE)
                        services.showMess("Attention le fichier actuel n'est pas sauvegarder !!\nSauvegardez avant de quitter!", "Information");
                else{
                        int sel = JOptionPane.showConfirmDialog(null,
                                "Voulez vous quittez le programme ?",
                                "Choisissez une option",
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE);
                        if(sel == JOptionPane.YES_OPTION)
                                System.exit(0);
                }
        }
        //edit menu
        if (sourceTxt.equals("Couper")) {
                services.editViewCut();
        }
        if (sourceTxt.equals("Copier")) {
                services.editViewCopy();
        }
        if (sourceTxt.equals("Coller")) {
                services.editViewPaste();
        }
        if (sourceTxt.equals("Sélectionner tout")) {
                services.editViewSlctAll();
        }
        //tools menu
        if (sourceTxt.equals("Assembler")) {
                services.saveFile();
                if (services.getActions().doAssemble(services.getFile())) {
                        services.setUIState(ASSEMBLED);
                        if(services.getActions().isSimuAlive()){
                                File file = services.getFile();
                                String path = new String("");
                                path = file.getAbsolutePath();
                                int idx = path.indexOf(".");
                                path = path.substring(0,idx)+".o8";
                                File fTosim = new File(path);
                                services.getActions().updateSim(fTosim);
                        }
                        services.updateMenuBar();
                }
                else{
                        services.setUIState(ERR);
                        services.updateMenuBar();
                }
        }
        if (sourceTxt.equals("Simuler")) {
                File file = services.getFile();
                String path = new String("");
                path = file.getAbsolutePath();
                int idx = path.indexOf(".");
                path = path.substring(0,idx)+".o8";
                File fTosim = new File(path);
                services.getActions().doSimule(fTosim);
        }
        //help menu
        if (sourceTxt.equals("Manuel")) {
                Help myhelp = new Help();
        }
        if (sourceTxt.equals("A propos")) {
                services.about();
        }
    }
    // Returns just the class name" -- no package info.
    protected String getClassName(Object o) {
            String classString = o.getClass().getName();
            int dotIndex = classString.lastIndexOf(".");
            return classString.substring(dotIndex+1);
    }
}

