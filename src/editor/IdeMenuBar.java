/******************************************************************************
 * By SILIBRE - www.silibre.com - 2009
 * contact@silibre.com
 ******************************************************************************
 * IdeMenuBar.java
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

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

/**
 *
 Class IdeMenuBar : Create the menu bar of UI
 */
public class IdeMenuBar implements IEditorConsts{
    IEditorServices services;
    JMenuBar menuBar;
    JMenu menu, editMenu, toolsMenu;
    JMenuItem menuItem;
    JMenuItem cutItem, copyItem, pasteItem, slctItem, assembleItem, simulItem;
    //events
    private EventsCalls eventsCalls;
    private MouseEvents mouseEvents;

    public IdeMenuBar(IEditorServices services) {
        this.services = services;
    }

    //Create the menu bar.
    JMenuBar CreateMenuBar(){
            menuBar = new JMenuBar();
            eventsCalls = new EventsCalls(services);
            mouseEvents = new MouseEvents(services);
            //menu files
            menu = new JMenu("Fichier");
            menu.setMnemonic(KeyEvent.VK_F);
            //menu.getAccessibleContext().setAccessibleDescription(
            //"The only menu in this program that has menu items");
            menuBar.add(menu);
            //a group of JMenuItems
            ImageIcon icon = services.createImageIcon("images/new_i.png");
            menuItem = new JMenuItem("Nouveau",icon );
            menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.ALT_MASK));
            //menuItem.getAccessibleContext().setAccessibleDescription("This doesn't really do anything");
            menuItem.addActionListener(eventsCalls);
            menu.add(menuItem);
            icon = services.createImageIcon("images/open_i.png");
            menuItem = new JMenuItem("Ouvrir",icon );
            menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.ALT_MASK));
            //menuItem.getAccessibleContext().setAccessibleDescription("This doesn't really do anything");
            menuItem.addActionListener(eventsCalls);
            menu.add(menuItem);
            icon = services.createImageIcon("images/save_i.png");
            menuItem = new JMenuItem("Enregistrer", icon);
            menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.ALT_MASK));
            menuItem.addActionListener(eventsCalls);
            menu.add(menuItem);
            icon = services.createImageIcon("images/saveas_i.png");
            menuItem = new JMenuItem("Enregistrer sous...", icon);
            menuItem.addActionListener(eventsCalls);
            menu.add(menuItem);
            menu.addSeparator();
            icon = services.createImageIcon("images/exit_i.png");
            menuItem = new JMenuItem("Quitter",icon);
            menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.ALT_MASK));
            menuItem.addActionListener(eventsCalls);
            menu.add(menuItem);
            //menu edition
            editMenu = new JMenu("Edition");
            editMenu.setMnemonic(KeyEvent.VK_E);
            //editMenu.getAccessibleContext().setAccessibleDescription("This menu does nothing");
            editMenu.addMouseListener(mouseEvents);
            menuBar.add(editMenu);
            icon = services.createImageIcon("images/scissors_i.png");
            cutItem = new JMenuItem("Couper",icon);
            cutItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
            cutItem.addActionListener(eventsCalls);
            editMenu.add(cutItem);
            icon = services.createImageIcon("images/copy_i.png");
            copyItem = new JMenuItem("Copier",icon);
            copyItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
            copyItem.addActionListener(eventsCalls);
            editMenu.add(copyItem);
            icon = services.createImageIcon("images/paste_i.png");
            pasteItem = new JMenuItem("Coller",icon);
            pasteItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
            pasteItem.addActionListener(eventsCalls);
            editMenu.add(pasteItem);
            icon = services.createImageIcon("images/slctall_i.png");
            slctItem = new JMenuItem("S�lectionner tout",icon);
            slctItem.addActionListener(eventsCalls);
            slctItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
            editMenu.add(slctItem);
            //menu outils
            toolsMenu = new JMenu("Outils");
            toolsMenu.setMnemonic(KeyEvent.VK_O);
            //toolsMenu.getAccessibleContext().setAccessibleDescription("This menu does nothing");
            toolsMenu.addMouseListener(mouseEvents);
            menuBar.add(toolsMenu);
            icon = services.createImageIcon("images/assemble_i.png");
            assembleItem = new JMenuItem("Assembler",icon);
            assembleItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.ALT_MASK));
            assembleItem.addActionListener(eventsCalls);
            toolsMenu.add(assembleItem);
            toolsMenu.addSeparator();
            icon = services.createImageIcon("images/simu_i.png");
            simulItem = new JMenuItem("Simuler",icon);
            simulItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.ALT_MASK));
            simulItem.addActionListener(eventsCalls);
            toolsMenu.add(simulItem);
            //menu Aide
            menu = new JMenu("Aide");
            menu.setMnemonic(KeyEvent.VK_I);
            menu.getAccessibleContext().setAccessibleDescription(
            "This menu does nothing");
            menuBar.add(menu);
            icon = services.createImageIcon("images/ligth_i.png");
            menuItem = new JMenuItem("Manuel",icon);
            menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, ActionEvent.ALT_MASK));
            menuItem.addActionListener(eventsCalls);
            menu.add(menuItem);
            menu.addSeparator();
            icon = services.createImageIcon("images/about_i.png");
            menuItem = new JMenuItem("A propos",icon);
            menuItem.addActionListener(eventsCalls);
            menu.add(menuItem);
            return menuBar;
        }

        public void updateMenuBar() {
            switch(services.getUIState()){
                    case NEW :
                            assembleItem.setEnabled(false);
                            simulItem.setEnabled(false);
                    break;
                    case UNSAFE:
                            assembleItem.setEnabled(true);
                    break;
                    case SAFE:
                            assembleItem.setEnabled(true);
                    break;
                    case ASSEMBLED:
                            if (!services.getActions().isSimuAlive()) //avoid multiple simu frame
                                    simulItem.setEnabled(true);
                            else
                                    simulItem.setEnabled(false);
                    break;
                    case SIMULED:
                    case ERR:
                            assembleItem.setEnabled(true);
                            simulItem.setEnabled(false);
                    break;
            }
            if (services.editViewIsSelectedText()) {
                    cutItem.setEnabled(true);
                    copyItem.setEnabled(true);
                    pasteItem.setEnabled(true);
                    slctItem.setEnabled(true);
            }
            else {
                    cutItem.setEnabled(false);
                    copyItem.setEnabled(false);
                    pasteItem.setEnabled(false);
                    slctItem.setEnabled(false);
            }
        }

}
