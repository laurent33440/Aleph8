/******************************************************************************
 * By SILIBRE - www.silibre.com - 2009
 * contact@silibre.com
 ******************************************************************************
 * MyMenuBar.java
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

package simulator.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;
import utils.Help;
import utils.Version;

/**
 **Class MyMenuBar : Create and treat the menu bar of UI
 */
 class MyMenuBar implements ActionListener, ISimuUIConsts{
        ISimuUIServices services;
        String newline = "\n";
        JMenuBar menuBar;
        JMenu menu, submenu;
        JMenuItem menuItem;
        JRadioButtonMenuItem rbMenuItem;
        JCheckBoxMenuItem cbMenuItem;
        JFileChooser fileCh;

        //Create the menu bar.
        JMenuBar CreateMenuBar(ISimuUIServices services) {
                this.services = services;
                menuBar = new JMenuBar();

                //Build the first menu.
                menu = new JMenu("Fichier");
                menu.setMnemonic(KeyEvent.VK_F);
                menu.getAccessibleContext().setAccessibleDescription(
                "The only menu in this program that has menu items");
                menuBar.add(menu);

                //a group of JMenuItems
                ImageIcon icon = createImageIcon("images/open_i.png");
                menuItem = new JMenuItem("Charger",icon );
                menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.ALT_MASK));
                menuItem.getAccessibleContext().setAccessibleDescription("This doesn't really do anything");
                menuItem.addActionListener(this);
                menu.add(menuItem);
                menu.addSeparator();
                icon = createImageIcon("images/exit_i.png");
                menuItem = new JMenuItem("Quitter",icon);
                menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.ALT_MASK));
                menuItem.addActionListener(this);
                menu.add(menuItem);
                //Build second menu in the menu bar.
                menu = new JMenu("Aide");
                menu.setMnemonic(KeyEvent.VK_I);
                menu.getAccessibleContext().setAccessibleDescription("This menu does nothing");
                menuBar.add(menu);
                icon = createImageIcon("images/ligth_i.png");
                menuItem = new JMenuItem("Manuel",icon);
                menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, ActionEvent.ALT_MASK));
                menuItem.addActionListener(this);
                menu.add(menuItem);
                menu.addSeparator();
                icon = createImageIcon("images/about_i.png");
                menuItem = new JMenuItem("A propos",icon);
                menuItem.addActionListener(this);
                menu.add(menuItem);
                return menuBar;
        }

        public void actionPerformed(ActionEvent e) {
                JMenuItem source = (JMenuItem)(e.getSource());
                String sourceTxt = source.getText();
                //String s = "Action event detected."+ newline+ "    Event source: " + source.getText()+ " (an instance of " + getClassName(source) + ")";
                //System.out.println(s + newline);
                if (sourceTxt == "Charger") {
                        JFileChooser fileCh = new JFileChooser();
                        fileCh.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                        int returnVal = fileCh.showOpenDialog(services.getMainFrame());
                        if (returnVal == JFileChooser.APPROVE_OPTION) {
                                File file = fileCh.getSelectedFile();
                                services.getHexFile().readFile(file);
                                services.resetUI(file.getName());
                        }
                        else {
                                //command cancelled by user
                        }

                }
                else
                        if (sourceTxt == "A propos"){
                                JOptionPane pane = new JOptionPane();
                                String message = new String(" Simulateur de processeur Aleph8\n\n Version : "+Version.getIt()+"\n\nAuteur : Laurent Authier Tabanon\n authier.tabanon@free.fr");
                                pane.showMessageDialog(services.getMainFrame(),message,"A propos de ... ",JOptionPane.INFORMATION_MESSAGE);
                        }
                        else{
                                if (sourceTxt == "Manuel"){
                                        Help myhelp = new Help();
                                }
                                else
                                        if (sourceTxt == "Quitter"){
                                                int sel = JOptionPane.showConfirmDialog(null,"Voulez vous quittez le programme ?","Choisissez une option",
                                                JOptionPane.YES_NO_OPTION,
                                                JOptionPane.QUESTION_MESSAGE);
                                                if(sel == JOptionPane.YES_OPTION)
                                                        System.exit(0);
                                        }
                        }
        }

        // Returns just the class name -- no package info.
        protected String getClassName(Object o) {
                String classString = o.getClass().getName();
                int dotIndex = classString.lastIndexOf(".");
                return classString.substring(dotIndex+1);
        }

        /** Returns an ImageIcon, or null if the path was invalid. */
	private ImageIcon createImageIcon(String path) {
        	java.net.URL imgURL = MyMenuBar.class.getResource(path);
        	if (imgURL != null) {
            		return new ImageIcon(imgURL);
        	} else {
            		System.err.println("Couldn't find file: " + path);
            		return null;
        	}
    	}
}

