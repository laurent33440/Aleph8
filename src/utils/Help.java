/******************************************************************************
 * By SILIBRE - www.silibre.com - 2009
 * contact@silibre.com
 ******************************************************************************
 * Help.java
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

package utils;

import java.awt.Dimension;
import java.io.IOException;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import main.IdeAleph8;

/**
** class Help : Help for editor : show html file
*/
public class Help extends JFrame{

        public Help(){
                super("Aide pour le processeur Aleph8 -- "+Version.getIt());
                JEditorPane editorPane = new JEditorPane();
                editorPane.setEditable(false);
                java.net.URL helpURL = IdeAleph8.class.getResource("/doc/ide_aleph8.html");
                if (helpURL != null) {
                        try {
                                editorPane.setPage(helpURL);
                        } catch (IOException e) {
                                System.err.println("Attempted to read a bad URL: " + helpURL);
                        }
                } else {
                System.err.println("Couldn't find file: aleph8help.html");
                }
                //Put the editor pane in a scroll pane.
                JScrollPane editorScrollPane = new JScrollPane(editorPane);
                editorScrollPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                editorScrollPane.setPreferredSize(new Dimension(800, 600));
                //editorScrollPane.setMinimumSize(new Dimension(10, 10));
                setSize(800, 600);
                add(editorScrollPane);
                setVisible(true);
        }
}

