/******************************************************************************
 * By SILIBRE - www.silibre.com - 2009
 * contact@silibre.com
 ******************************************************************************
 * SourceParser.java
 *
 * This build a vector of token
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
 *  0.1         prog         31 déc. 2011                 Creation
 */

package pascal.parser;

import java.util.TreeMap;
import java.util.Vector;
import pascal.PascalException;
import pascal.tokenfactory.ITokenDef.TokenValue;
import pascal.tokenfactory.Token;

/**
 * Parse over all lines of source
 */
public final class SourceParser implements ISourceParser {

    private Vector<Token> allToken;
    private Token currToken;
    private Integer tokenIndex;
    private static  Integer currentLineNumber;
    private String currentLineSrc;

    /**
     * Construct source parser and parse all source code
     * @param source : string représentaion of Pascal source
     * @throws PascalException
     */
    public SourceParser(String source) throws PascalException{
        PascalSource ps = new PascalSource(source);
        if(!ps.isSourceEmpty()){
            this.allToken = new Vector<Token>();
            TreeMap<Integer, Token> currLineToken = new TreeMap<Integer, Token>();
            this.tokenIndex=0; //initialise iterator
            LineParser lParser = new LineParser(currLineToken);
            while (!ps.isEndSource()){
                this.currentLineNumber =  ps.getCurrentLineNumber();
                this.currentLineSrc=ps.getLine();
                if(!lParser.parseLine(this.currentLineSrc, this.currentLineNumber))
                    throw new PascalException("Erreur de syntaxe ",
                                        " Aucun programme Pascal trouvé dans le texte : "+this.currentLineSrc + " à la ligne : ",
                                        this.currentLineNumber);
                this.allToken.addAll(currLineToken.values());
                currLineToken.clear();
            }
            this.currToken = this.allToken.elementAt(tokenIndex);
        }else
            throw new PascalException("Erreur !! "," Aucun programme Pascal à compiler  ",1);
    }

    /**
     * Iterate on tokens. Give next token of source.
     * @return the next token or null token at end
     */
    public Token nextToken(){
        if(this.tokenIndex<this.allToken.size()-1){
            return this.currToken = this.allToken.elementAt(++this.tokenIndex);
        }
        else{
            return this.currToken = new Token(Token.TokenValue.NOTOKEN);
        }
    }

    /**
     * Get current token symbol
     * @return TokenValue enumeration
     */
    public TokenValue getTokenSymbol(){
        return this.currToken.getTokenValue();
    }

    /**
     * Get representation of current meaning token
     * @return String
     */
    public String getTokenMeaning(){
        return this.currToken.getMeaning();
    }

/**
     * Get current line number in source code
     * @return line number
     */
    public  static Integer getStaticCurrentLineNumber() {
        return currentLineNumber;
    }
    
    /**
     * Get current line number in source code
     * @return line number
     */
    public  Integer getCurrentLineNumber() {
        return currentLineNumber;
    }

    /**
     * Get current line source
     * @return String
     */
    public String getCurrentLineSrc() {
        return currentLineSrc;
    }

    /**
     * Get current token
     * @return Token
     */
    public Token getCurrToken() {
        return currToken;
    }
}