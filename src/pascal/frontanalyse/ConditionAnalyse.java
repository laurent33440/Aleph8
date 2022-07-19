/******************************************************************************
 * By SILIBRE - www.silibre.com - 2009
 * contact@silibre.com
 ******************************************************************************
 * ConditionAnalyse.java
 *  
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

package pascal.frontanalyse;

import java.util.Vector;
import pascal.PascalException;
import pascal.parser.ISourceParser;
import pascal.tokenfactory.Token;


public class ConditionAnalyse {
    private ISourceParser sourceParser;
    private Vector<Token.TokenValue> RELOP_TOKENS = new Vector<Token.TokenValue>();
    private Vector<Token.TokenValue> EXP_DELIMITERS = new Vector<Token.TokenValue>();
    private ExpressionAnalyse leftExpressionAnalyse;
    private ExpressionAnalyse rightExpressionAnalyse;
    private Token relOpToken;

    public ConditionAnalyse(ISourceParser sourceParser, Vector<Token.TokenValue> RELOP_TOKENS, Vector<Token.TokenValue> EXP_DELIMITERS ) {
        this.sourceParser = sourceParser;
        this.RELOP_TOKENS = RELOP_TOKENS;
        this.EXP_DELIMITERS = EXP_DELIMITERS;
        try{
            condition();
        }catch (PascalException pe){
        }
        
    }
    
    public ExpressionAnalyse getLeftExpressionAnalyse(){
        return this.leftExpressionAnalyse;
    }

    public ExpressionAnalyse getRightExpressionAnalyse() {
        return this.rightExpressionAnalyse;
    }

    public Token getRelOpToken() {
        return relOpToken;
    }

    private void condition() throws PascalException{
        this.leftExpressionAnalyse = new ExpressionAnalyse(this.sourceParser, this.EXP_DELIMITERS);
        if(!this.RELOP_TOKENS.contains(this.sourceParser.getCurrToken().getTokenValue())){
                throw new PascalException("Erreur de syntaxe ",
                                       " opérateur de relation attendu au lieu de : "+this.sourceParser.getCurrToken().getTokenValue().getVal(),
                                       this.sourceParser.getCurrentLineNumber());
        }
        this.relOpToken = this.sourceParser.getCurrToken();
        this.sourceParser.nextToken();//right exp
        this.rightExpressionAnalyse = new ExpressionAnalyse(this.sourceParser, this.EXP_DELIMITERS);
    }
    
}
