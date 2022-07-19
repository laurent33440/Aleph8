/******************************************************************************
 * By SILIBRE - www.silibre.com - 2009
 * contact@silibre.com
 ******************************************************************************
 * Token.java
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

package pascal.tokenfactory;

/**
 *  Token class
 */
public class Token implements ITokenDef{

    private TokenValue token;
    private String meaning;
    private Integer LineNumber; //line number in src

    public Token() {
    }

    public Token(TokenValue token) {
        this.token = token;
    }

    public Token(TokenValue token, Integer srcLine) {
        this.token = token;
        this.LineNumber = srcLine;
    }

    public Token(TokenValue token, String meaning, Integer srcLine) {
        this.meaning = meaning;
        this.token = token;
        this.LineNumber = srcLine;
    }

    public String getMeaning() {
        return meaning;
    }

    public TokenValue getTokenValue() {
        return token;
    }

    public Integer getLineNumber() {
        return LineNumber;
    }


}
