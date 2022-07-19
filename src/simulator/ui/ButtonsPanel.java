/******************************************************************************
 * By SILIBRE - www.silibre.com - 2009
 * contact@silibre.com
 ******************************************************************************
 * ButtonPanel.java
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

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

/**
* Class ButtonsPanel : build and treat buttons of UI
*
*/
public class ButtonsPanel extends JPanel implements ActionListener, ISimuUIConsts {
        ISimuUIServices services;
        protected JButton stepButton, runButton, stopButton, resetButton;
        final short stepNum = 1;
        final short runNum = 2;
        final short stopNum = 4;
        final short resetNum = 8;

        public  ButtonsPanel(ISimuUIServices services) {
            this.services = services;
            ImageIcon stepButtonIcon = createImageIcon("images/step_b.png");
            ImageIcon runButtonIcon = createImageIcon("images/run_b.png");
            ImageIcon stopButtonIcon = createImageIcon("images/stop_b.png");
            ImageIcon resetButtonIcon = createImageIcon("images/reset_b.png");
            //Use the default text position of CENTER, TRAILING (RIGHT).
            stepButton = new JButton("Pas � Pas", stepButtonIcon);
            stepButton.setMnemonic(KeyEvent.VK_D);
            //stepButton.setActionCommand("disable");

            runButton = new JButton("Execute", runButtonIcon);
            runButton.setMnemonic(KeyEvent.VK_M);

            stopButton = new JButton("Stop", stopButtonIcon);
            stopButton.setMnemonic(KeyEvent.VK_E);
            //stopButton.setActionCommand("enable");
            stopButton.setEnabled(false);

            resetButton = new JButton("Remise � z�ro", resetButtonIcon);
            resetButton.setMnemonic(KeyEvent.VK_D);
            //resetButton.setActionCommand("enable");
            //Listen for actions on buttons
            stepButton.addActionListener(this);
            runButton.addActionListener(this);
            stopButton.addActionListener(this);
            resetButton.addActionListener(this);
            // tool tips
            stepButton.setToolTipText("Appuyez sur ce bouton pour ex�cuter pas � pas le programme");
            runButton.setToolTipText("Appuyez sur ce bouton pour ex�cuter le programme");
            stopButton.setToolTipText("Appuyez sur ce bouton pour arr�ter l'ex�cution du programme");
            resetButton.setToolTipText("Appuyez sur ce bouton pour remettre � z�ro le processeur");
            //set size
            double height = services.getFrameDim().getHeight();
            double width = services.getFrameDim().getWidth();
            height *= 0.15; // 15% of total height
            width *= 0.85; // 85% of total width
            setPreferredSize(new Dimension((int)width,(int)height));
            //setSize(new Dimension((int)width,(int)height));
            //Add Components to this container, using the default FlowLayout.
            add(stepButton);
            add(runButton);
            add(stopButton);
            add(resetButton);
            TitledBorder titled;
            titled = BorderFactory.createTitledBorder("Commandes du simulateur");
            setBorder(titled);
    }
        public void actionPerformed(ActionEvent e) {
                if(e.getActionCommand().equals("Pas � Pas")) {
                        services.cmdeSimu().stepProg();
                }
                if(e.getActionCommand().equals("Execute")) {
                        services.cmdeSimu().runProg();
                }
                if(e.getActionCommand().equals("Stop")) {
                        services.cmdeSimu().stopProg();
                }
                if(e.getActionCommand().equals("Remise � z�ro")) {
                        services.cmdeSimu().reset();
                }
        }

        /** Returns an ImageIcon, or null if the path was invalid. */
        protected ImageIcon createImageIcon(String path) {
                java.net.URL imgURL = Aleph8Simu.class.getResource(path);
                if (imgURL != null) {
                        return new ImageIcon(imgURL);
                } else {
                        System.err.println("Couldn't find file: " + path);
                        return null;
                }
        }

        public void enableButtons(int which){
                disableButtons(stepNum+runNum+stopNum+resetNum);
                if ((which & 1) == 1)
                        stepButton.setEnabled( true );
                if ((which & 2) == 2)
                        runButton.setEnabled( true );
                if ((which & 4) == 4)
                        stopButton.setEnabled( true );
                if ((which & 8) == 8)
                        resetButton.setEnabled( true );
        }

        private void disableButtons(int which){
                if ((which & 1) == 1)
                        stepButton.setEnabled( false );
                if ((which & 2) == 2)
                        runButton.setEnabled( false );
                if ((which & 4) == 4)
                        stopButton.setEnabled( false );
                if ((which & 8) == 8)
                        resetButton.setEnabled( false );
        }
}
