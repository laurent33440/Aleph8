/******************************************************************************
 * By SILIBRE - www.silibre.com - 2009
 * contact@silibre.com
 ******************************************************************************
 * IAssDef.java
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

package assembler;

/**
 *
 */
public interface IAssDef {
    short BEGVAR = 0, DCB = 1, BEGIN = 2, END = 3;
    short NOR = 4, ADD = 5, STA = 6, JCC = 7;
    short NUM = 8, ID = 9;
    // keywords use same cardinal as constants above
    String [] KEYWORDS = {"var", "<=", "begin", "end", "nor", "add", "sta", "jcc"};
    int MAXIDLENGTH = 8;
    int MAXHEXALENGTH = 3; //prefix (h or H) + 2 digits
    int MAXBINLENGTH = 9; //prefix (b or B) + 8 bits
    int MAXSYMBOLS = 64;
    int UNKNOWN=1, VAR = 2, JUMP=3;	//class type for symbols

}
