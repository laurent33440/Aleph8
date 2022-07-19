/******************************************************************************
 * By SILIBRE - www.silibre.com - 2009
 * contact@silibre.com
 ******************************************************************************
 * IdeAleph8.java
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
 *
 *      Ver 	Who 	When		What				   *
 *	0.1	ATL	04122006	Creation
 *	0.2	ATL	14012007	Fix file's path bug under win$ for simulation
 *	0.3	ATL	15022007	Changes in simulator :
 *						-add two views for memory
						-cpu is shown in a graphic way
	0.4	ATL	03062007	Extension missing bug in name's file for assembler
					Bug fixes for ID length
					Change syntax error message for ID in parser
 *      0.5     ATL     28102010        Restructural rewrite and NetBeans migration
 */

package main;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import editor.Editor;


   /**
     * Create the IDE GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
public class IdeAleph8 {
        //ide tools
        private static IdeTools ideTools;
	// ui components
	private static Editor editor;
	private static JFrame frame; // main frame

	/**
	 */
	public IdeAleph8(){
		//Create and set up the window.
        	frame = new JFrame("Environnement de d�veloppement pour le processeur Aleph8");
		frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		frame.setSize(800, 600);
		frame.setResizable(false);
		ImageIcon icon = new ImageIcon("images/icon1_40.png");
		frame.setIconImage(icon.getImage());
                this.ideTools = new IdeTools();
		editor = new Editor(ideTools, frame) ;
	}

	private static void showGUI() {
        	frame.setVisible(true);
    	}

	public static void main(String[] args) {
		new IdeAleph8();
        	//Schedule a job for the event-dispatching thread:
        	//creating and showing this application's GUI.
        	javax.swing.SwingUtilities.invokeLater(new Runnable() {
            	public void run() {
                	showGUI();
            	}
        	});
    	}
}

