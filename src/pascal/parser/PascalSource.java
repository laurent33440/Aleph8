/******************************************************************************
 * By SILIBRE - www.silibre.com - 2009
 * contact@silibre.com
 ******************************************************************************
 * PascalSource.java
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
 *  0.1         prog         27 nov. 2011                 Creation
 */

package pascal.parser;

/**
 * Cette classe permet d'obtenir une ligne de texte délimtée par les retours chariot
 * Elle renvoie la chaine vide si elle arrive en fin de texte ou si le texte est vide
 * Elle gère les numéros de lignes
 */
public final class PascalSource {
    private final String source;
    private String[] textLines;
    private Integer currentLineNumber;
    private boolean emptySource;
    private final String nlf = System.getProperty("line.separator");

    public PascalSource(String source) {
        this.source = source;
        this.currentLineNumber = 1;
        if(!source.isEmpty()){
            this.getAllTxtLine();
        }
        else
            this.emptySource = true;
    }

    final public String getLine(){
        String line = this.textLines[this.currentLineNumber-1];
        this.currentLineNumber++;
        return line;
    }

    final public Integer getCurrentLineNumber(){
        return this.currentLineNumber;
    }

    final public boolean isEndSource(){
        return (this.currentLineNumber-1 == this.textLines.length);
    }
    
    final public boolean isSourceEmpty(){
        return this.emptySource;
    }

    private void getAllTxtLine(){
        this.textLines=this.source.split(this.nlf);
    }

}
