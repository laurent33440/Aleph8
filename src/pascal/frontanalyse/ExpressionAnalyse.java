/******************************************************************************
 * By SILIBRE - www.silibre.com - 2009
 * contact@silibre.com
 ******************************************************************************
 * ExpressionAnalyse.java
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
 *  0.1         prog         25 janv. 2012                 Creation
 */

package pascal.frontanalyse;

import java.util.Iterator;
import java.util.Vector;
import pascal.PascalException;
import pascal.frontanalyse.expressionstack.AExpressionSymbol;
import pascal.frontanalyse.expressionstack.ExpressionStack;
import pascal.frontanalyse.expressionstack.AExpressionSymbol.Operator;
import pascal.frontanalyse.expressionstack.ExpressionQueue;
import pascal.frontanalyse.expressionstack.IExpressionList;
import pascal.frontanalyse.expressionstack.NameSymbol;
import pascal.frontanalyse.expressionstack.OperationSymbol;
import pascal.frontanalyse.expressionstack.ValueSymbol;
import pascal.parser.ISourceParser;
import pascal.symboltable.ISymbolTable;
import pascal.symboltable.SymbolTable;
import pascal.tokenfactory.ITokenDef;
import pascal.tokenfactory.ITokenDef.TokenValue;
import static pascal.tokenfactory.ITokenDef.TokenValue.MINUS;
import static pascal.tokenfactory.ITokenDef.TokenValue.NAME;
import static pascal.tokenfactory.ITokenDef.TokenValue.NUMBER;
import static pascal.tokenfactory.ITokenDef.TokenValue.PLUS;
import pascal.tokenfactory.Token;

/**
 * Check syntax expression and build stack of expression symbols
 *
 *      EXPR       ::= PRIMARY { ADDOP PRIMARY } NOTOKEN
 *      ADDOP      ::= + | - 
 *      PRIMARY    ::= Id | Num 
 * NOTE : token "NOTOKEN" ends expression
 */
public class ExpressionAnalyse {
    private ISourceParser sourceParser;
    private Integer srcLineNumber;
    private Vector<Token> expressionTokens;
    private ISymbolTable st ;
    private IExpressionList exprStack;
    private Vector<Token.TokenValue> EXP_DELIMITERS = new Vector<Token.TokenValue>();

    /**
     * Check syntax expression and build stack of expression symbols
     * @param sourceParser
     */
    public ExpressionAnalyse(ISourceParser sourceParser, Vector<Token.TokenValue> EXP_DELIMITERS) {
        this.sourceParser = sourceParser;
        this.st = SymbolTable.getInstance();
        this.exprStack = new ExpressionQueue();
        this.srcLineNumber = this.sourceParser.getCurrentLineNumber();
        this.EXP_DELIMITERS = EXP_DELIMITERS;
        try{
            this.expressionTokens =this.extractExpressionFromSourceParser();
            Iterator<Token> itTokens =  this.expressionTokens.iterator();
            expressionToken(itTokens);
        }catch(PascalException pe){
            //new PascalException("fail","fail",1);
        }
    }

    /**
     * get expression stack
     * @return expression stack
     */
    public IExpressionList getExprStack() {
        return exprStack;
    }
    
    private void expressionToken(Iterator<Token> itTokens ) throws PascalException{
        Token currToken = itTokens.next();
        primaryToken(currToken); //first element is always a primary
        currToken = itTokens.next();
        while(!currToken.getTokenValue().equals(TokenValue.NOTOKEN)){
            switch(currToken.getTokenValue()){
                case PLUS :
                    primaryToken(itTokens.next());
                    this.exprStack.pushSymbol(new OperationSymbol(Operator.PLUS));
                    break;
                case MINUS :
                    primaryToken(itTokens.next());
                    this.exprStack.pushSymbol(new OperationSymbol(Operator.MINUS));
                    break;
                default :
                    throw new PascalException("Erreur de syntaxe"," opérateurs '+' ou '-' attendu !",this.srcLineNumber);
            }
            currToken = itTokens.next();
        }
    }
    
    private void primaryToken(Token token) throws PascalException{
        switch(token.getTokenValue()){
            case NUMBER :
                String numRep = token.getMeaning();
                Integer valNum = new Integer(numRep);
                this.exprStack.pushSymbol(new ValueSymbol(valNum));
                break;
            case NAME :
                String name = token.getMeaning();
                if(this.st.isSymbolExist(name)){//check NAME in symbol table
                    this.exprStack.pushSymbol( new NameSymbol(name));
                    break;
                }else
                    throw new PascalException("Erreur de Syntaxe"," indentifiant : "+name+", non trouvé !",this.srcLineNumber);
            default :
                throw new PascalException("Erreur de Syntaxe"," Valeur numérique ou indentifiant attendu!",this.srcLineNumber);
        }
    }
    
    //extract expression tokens from source parser. Ends tokens list with "NOTOKEN"  
    private Vector<Token> extractExpressionFromSourceParser() throws PascalException{
        Vector<Token> expressionToken = new Vector<Token>();
        while(!this.EXP_DELIMITERS.contains(this.sourceParser.getTokenSymbol())&&!this.sourceParser.getTokenSymbol().equals(TokenValue.NOTOKEN)){
            expressionToken.add(this.sourceParser.getCurrToken());
            this.sourceParser.nextToken();
        }
        if(this.sourceParser.getTokenSymbol().equals(TokenValue.NOTOKEN))
            throw new PascalException("Erreur de syntaxe."," Expression non terminée! Fin du programme atteinte! ",this.srcLineNumber);
        expressionToken.add(new Token(ITokenDef.TokenValue.NOTOKEN));// ends expression
        return expressionToken;
    }
    
    //==================================== obsolette
    
     /**
     * Check syntax expression and build stack of expression symbols
     * @param Tokens list of expression
     */
//    public ExpressionAnalyse(Vector<Token> expressionTokens, Integer srcLineNumber) {
//        this.expressionTokens = expressionTokens;
//        this.srcLineNumber = srcLineNumber;
//        this.st = SymbolTable.getInstance();
//        this.exprStack = new ExpressionStack();
//        Iterator<Token> itTokens =  this.expressionTokens.iterator();
//        try{
//            expressionToken(itTokens);
//        }catch(PascalException pe){
//            //new PascalException("fail","fail",1);
//        }
//    }
    
//    private void expression() throws PascalException{
//        boolean first=true;
//        while(!this.sourceParser.getTokenSymbol().equals(TokenValue.SEMICOLON)){
//            switch(this.sourceParser.getTokenSymbol()){
//                case PLUS :
//                    primary(true);
//                    this.exprStack.pushSymbol(new OperationSymbol(Operator.PLUS));
//                    break;
//                case MINUS :
//                    primary(true);
//                    this.exprStack.pushSymbol(new OperationSymbol(Operator.MINUS));
//                    break;
//                default :
//                    if(first){
//                        primary(false);
//                        first = !first;
//                    }else{
//                        throw new PascalException("Erreur de syntaxe"," opérateurs '+' ou '-' attendu !",this.sourceParser.getCurrentLineNumber());
//                    }
//            }
//            this.sourceParser.nextToken();
//        }
//    }
//
//    private void primary(boolean get) throws PascalException{
//        if(get){
//            this.sourceParser.nextToken();
//        }
//        switch(this.sourceParser.getTokenSymbol()){
//            case NUMBER :
//                String numRep = this.sourceParser.getTokenMeaning();
//                Integer valNum = new Integer(numRep);
//                this.exprStack.pushSymbol(new ValueSymbol(valNum));
//                break;
//            case NAME :
//                String name = this.sourceParser.getTokenMeaning();
//                if(this.st.isSymbolExist(name)){//check NAME in symbol table
//                    this.exprStack.pushSymbol( new NameSymbol(name));
//                    break;
//                }else
//                    throw new PascalException("Syntax Error"," Symbol name : "+name+", not found !",this.sourceParser.getCurrentLineNumber());
//            default :
//                throw new PascalException("Syntax Error"," Primary expected",this.sourceParser.getCurrentLineNumber());
//        }
//    }

  //=======================================================================================================  
    /* Futur grammar :
 
    
    EXPR    ::=     TERM { ADDOP TERM } ;
    ADDOP   ::=     + | -
    TERM    ::=     FACT { MULOP FACT }
    MULOP   ::=     * | /
    FACT    ::=     Id | Num | ( EXPR )
     */
    
    //////// ======== mul / div analyse to FIX

//    private void expression(boolean get) throws PascalException{
//        boolean first=true;
//        Object obj  = term(get);//left
//        while(!this.sourceParser.getTokenSymbol().equals(TokenValue.NOTOKEN)){
//            switch(this.sourceParser.getTokenSymbol()){
//                case PLUS :
//                    if(first){
//                        this.exprStack.pushSymbol((AExpressionSymbol)obj);//left
//                        first=false;
//                    }
//                    this.exprStack.pushSymbol((AExpressionSymbol)term(true)); //right
//                    this.exprStack.pushSymbol(new OperationSymbol(Operator.PLUS));
//                    break;
//                case MINUS :
//                    if(first){
//                        this.exprStack.pushSymbol((AExpressionSymbol)obj);//left
//                       first=false;
//                    }
//                    this.exprStack.pushSymbol((AExpressionSymbol)term(true)); //right
//                    //term(true);
//                    this.exprStack.pushSymbol(new OperationSymbol(Operator.MINUS));
//                    break;
//                default :
//                    obj = term(true);
//            }
//        }
//        this.exprStack.pushSymbol((AExpressionSymbol)obj);//left
//    }
//
//    private Object term(boolean get) throws PascalException{
//        boolean first=true;
//        boolean stop=false;
//        Object obj = primary(get);
//        while(stop != true){
//            switch(this.sourceParser.getTokenSymbol()){
//                case MUL :
//                    //primary(true);
//                    if(first){
//                        this.exprStack.pushSymbol((AExpressionSymbol)obj);//left
//                        first=false;
//                    }
//                    this.exprStack.pushSymbol((AExpressionSymbol)primary(true)); //right
//                    this.exprStack.pushSymbol(new OperationSymbol(Operator.MULT));
//                    break;
//                case DIV :
//                    //primary(true); //denominator
//                    if(first){
//                        this.exprStack.pushSymbol((AExpressionSymbol)obj);//left
//                        first=false;
//                    }
//                    this.exprStack.pushSymbol((AExpressionSymbol)primary(true)); //right
//                    VisitValueSymbol v = new VisitValueSymbol();
//                    this.exprStack.accept(v);
//                    if(v.isNul()){
//                        throw new PascalException("Math Error","Divide by 0",this.sourceParser.getCurrentLineNumber());
//                    }
//                    else{
//                        this.exprStack.pushSymbol(new OperationSymbol(Operator.DIV));
//                    }
//                    break;
//                default :
//                    stop=true;
//            }
//        }
//        return obj;
//    }
//
//    private  Object primary(boolean get) throws PascalException{
//        if(get){
//            this.sourceParser.nextToken();
//        }
//        switch(this.sourceParser.getTokenSymbol()){
//            case NUMBER :
//                String numRep = this.sourceParser.getTokenMeaning();
//                Integer valNum = new Integer(numRep);
//                Object obj = new ValueSymbol(valNum);
//                //this.exprStack.pushSymbol(new ValueSymbol(valNum));
//                this.sourceParser.nextToken();
//                return obj;
//                //break;
//            case NAME :
//                //check NAME in symbol table
//                //Integer evalName = computeCell(this.sourceParser.getTokenMeaning());
//                this.sourceParser.nextToken();
//                //return evalName;
//                //return 0;
//            case MINUS:
//                primary(true);
//            case LP:
//                //val = expression(true);
//                //check RP
//                if(this.sourceParser.getTokenSymbol() != Token.TokenValue.RP){
//                    throw new PascalException("Syntax Error","Right parentesis expected",0);
//                }
//                this.sourceParser.nextToken();//consume RP
//                //return val;
//            default :
//                throw new PascalException("Syntax Error","Primary expected",0);
//        }
//    }


}
