/******************************************************************************
 * By SILIBRE - www.silibre.com - 2009
 * contact@silibre.com
 ******************************************************************************
 * PascalException.java
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
 *  0.1         prog         13 janv. 2012                 Creation
 */

package pascal;

import javax.swing.JOptionPane;

/**
 * FIXME :
 */
public class PascalException extends Exception{
        PascalException(String s){
                super(s);
        }
        public PascalException(String message, String token, Integer line ){
                super(message);
                String s;
                s = message + token + " ligne n° : " + line.toString();
                Log log = Log.getUniqueInstance();
                log.logMessage(s+"\n");
                JOptionPane.showMessageDialog(null, s+"\n (Voir fichier de log)","Erreur de syntaxe", JOptionPane.ERROR_MESSAGE);
        }

}
