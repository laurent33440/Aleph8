/******************************************************************************
 * By SILIBRE - www.silibre.com - 2009
 * contact@silibre.com
 ******************************************************************************
 * StatusLine.java
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
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

/**
* Class StatusLine :  UI component to show state and line number of code
*/
public class StatusLine extends JPanel implements IEditorConsts{
        IEditorServices services;
        private JLabel lineText;
        private JLabel statusText;
        private final String TEXTLINE = "Numéro de ligne : ";
        private final String TEXTSTATUS = "Document : ";
        private int lineNumber;

        StatusLine(IEditorServices services){
            this.services = services;
            lineNumber = 1;
            double height = services.getFrameDim().getHeight();
            double width = services.getFrameDim().getWidth();
            //System.out.println("dim frame : ");
            //System.out.println(height);
            //System.out.println(width);
            height *= 0.07; // 5% of total height
            width *= 0.95; // 95% of total width
            lineText = new JLabel(TEXTLINE);
            statusText = new JLabel(TEXTSTATUS);
            setPreferredSize(new Dimension((int)width,(int)height));
            setLayout(new BorderLayout());
            add(BorderLayout.WEST, lineText);
            add(BorderLayout.EAST, statusText);
            TitledBorder titled;
            titled = BorderFactory.createTitledBorder("Informations editeur");
            setBorder(titled);
        }
        public void updateStatus(){
            String mess;
            switch(services.getUIState()){
                    case NEW :
                            mess = new String("Nouveau fichier");
                            buildStatus(mess);
                            buildLineNum();
                    break;
                    case UNSAFE:
                            mess = new String("fichier non sauvegard�");
                            buildStatus(mess);
                            buildLineNum();
                    break;
                    case SAFE:
                            mess = new String("fichier sauvegard�");
                            buildStatus(mess);
                            buildLineNum();
                    break;
                    case ASSEMBLED:
                            mess = new String("fichier assembl�");
                            buildStatus(mess);
                            buildLineNum();
                    break;
                    case SIMULED:
                            mess = new String("Simulation en cours");
                            buildStatus(mess);
                            buildLineNum();
                    break;
                    case ERR:
                            mess = new String("Erreur d'assemblage");
                            buildStatus(mess);
                            buildLineNum();
                    break;
            }
        }
        private void buildStatus(String message){
                statusText.setText(TEXTSTATUS+message);
        }
        private void buildLineNum(){
                Integer num2 = new Integer(0);
                num2 = num2.valueOf(lineNumber);
                lineText.setText(TEXTLINE+num2.toString());
        }
        protected void setLineNum(int lineNum){
                lineNumber = lineNum;
        }
}

