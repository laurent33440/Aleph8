/******************************************************************************
 * By SILIBRE - www.silibre.com - 2009
 * contact@silibre.com
 ******************************************************************************
 * FrontAnalyse.java
 * Grammar : 
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
import pascal.srcInstructions.HltInstruction;
import pascal.srcInstructions.ISrcInstruction;
import pascal.srcInstructions.IfInstruction;
import pascal.srcInstructions.LetInstruction;
import pascal.srcInstructions.SrcInstructionsList;
import pascal.srcInstructions.WhileInstruction;
import pascal.symboltable.ConstanteSymbol;
import pascal.symboltable.ASymbol;
import pascal.symboltable.ISymbolTable;
import pascal.symboltable.ProgramSymbol;
import pascal.symboltable.SymbolTable;
import pascal.symboltable.VariableSymbol;
import pascal.tokenfactory.Token;

/**
 * Syntax & semantic analyse of source code
 */
public class FrontAnalyse {
    private final ISourceParser sourceParser;
    private ISymbolTable st ;
    private SrcInstructionsList srcInstructionsList;
    private final Vector<Token.TokenValue> RELOP_TOKENS = new Vector<Token.TokenValue>();
    private final Vector<Token.TokenValue> EXP_DELIMITERS = new Vector<Token.TokenValue>();

    public FrontAnalyse(ISourceParser sourceParser) {
        this.sourceParser = sourceParser;
        this.RELOP_TOKENS.add(Token.TokenValue.EQUAL);
        this.RELOP_TOKENS.add(Token.TokenValue.DIFF);
        this.RELOP_TOKENS.add(Token.TokenValue.GEQ);
        this.RELOP_TOKENS.add(Token.TokenValue.GREAT);
        this.RELOP_TOKENS.add(Token.TokenValue.LEQ);
        this.RELOP_TOKENS.add(Token.TokenValue.LESS);
        //see grammar
        this.EXP_DELIMITERS.addAll(this.RELOP_TOKENS);
        this.EXP_DELIMITERS.add(Token.TokenValue.THEN);
        this.EXP_DELIMITERS.add(Token.TokenValue.DO);
        this.EXP_DELIMITERS.add(Token.TokenValue.SEMICOLON);
        frontAnalyse();
    }

    /**
     * Analyse source code. Create symbols & fire Pascal instructions creation.
     */
    public final void frontAnalyse(){
        try{
            this.st = SymbolTable.getInstance();
            this.srcInstructionsList = new SrcInstructionsList();
            program();
        }catch(PascalException pe){}

    }
    
    /**
     * Get list of all instructions 
     * @return instructions list
     */
    public SrcInstructionsList getSourceInstructionsList(){
        return this.srcInstructionsList;
    }

    private void program() throws PascalException{
        if(!this.sourceParser.getCurrToken().getTokenValue().equals(Token.TokenValue.PROGRAM))
            throw new PascalException("Erreur de syntaxe ",
                                       "Mot clé 'program' attendu au lieu de : "+this.sourceParser.getCurrToken().getTokenValue().getVal(),
                                       this.sourceParser.getCurrentLineNumber());
        if(!this.sourceParser.nextToken().getTokenValue().equals(Token.TokenValue.NAME))
            throw new PascalException("Erreur de syntaxe ",
                                        "Nom de 'programme' attendu mais non trouvé ",
                                        this.sourceParser.getCurrentLineNumber());
        st.addPascalSymbol(new ProgramSymbol(this.sourceParser.getTokenMeaning()));
        if(!this.sourceParser.nextToken().getTokenValue().equals(Token.TokenValue.SEMICOLON))
            throw new PascalException("Erreur de syntaxe ",
                                        " ';' attendu mais non trouvé ",
                                        this.sourceParser.getCurrentLineNumber());
        block();
        if(!this.sourceParser.nextToken().getTokenValue().equals(Token.TokenValue.DOT))
            throw new PascalException("Erreur de syntaxe ",
                                       "Point '.'attendu au lieu de : "+this.sourceParser.getCurrToken().getTokenValue().getVal(),
                                       this.sourceParser.getCurrentLineNumber());

    }

    private void block() throws PascalException{
        if(this.sourceParser.nextToken().getTokenValue().equals(Token.TokenValue.CONST)){
            consts();
        }
        if(this.sourceParser.getCurrToken().getTokenValue().equals(Token.TokenValue.VAR)){
            vars();
        }
        insts();
    }

    private void consts() throws PascalException{
        String name;
        if(!this.sourceParser.nextToken().getTokenValue().equals(Token.TokenValue.NAME))
            throw new PascalException("Erreur de syntaxe ",
                                   "Nom de constante attendu au lieu de : "+this.sourceParser.getCurrentLineSrc(),
                                   this.sourceParser.getCurrentLineNumber());
        do{
            name = this.sourceParser.getTokenMeaning();
            if(!this.sourceParser.nextToken().getTokenValue().equals(Token.TokenValue.EQUAL))
                throw new PascalException("Erreur de syntaxe ",
                                       "Mot clé '=' attendu au lieu de : "+this.sourceParser.getCurrToken().getTokenValue().getVal(),
                                       this.sourceParser.getCurrentLineNumber());
            if(!this.sourceParser.nextToken().getTokenValue().equals(Token.TokenValue.NUMBER))
                throw new PascalException("Erreur de syntaxe ",
                                       "Nombre attendu au lieu de : "+this.sourceParser.getCurrToken().getTokenValue().getVal(),
                                       this.sourceParser.getCurrentLineNumber());
            st.addPascalSymbol(new ConstanteSymbol(name,this.sourceParser.getTokenMeaning()));
            if(!this.sourceParser.nextToken().getTokenValue().equals(Token.TokenValue.SEMICOLON))
                throw new PascalException("Erreur de syntaxe ",
                                       "Mot clé ';' attendu au lieu de : "+this.sourceParser.getCurrToken().getTokenValue().getVal(),
                                       this.sourceParser.getCurrentLineNumber());
        }while(this.sourceParser.nextToken().getTokenValue().equals(Token.TokenValue.NAME));

    }

    private void vars() throws PascalException{
        String name;
        if(!this.sourceParser.nextToken().getTokenValue().equals(Token.TokenValue.NAME))
            throw new PascalException("Erreur de syntaxe ",
                                   "Nom de constante attendu au lieu de : "+this.sourceParser.getCurrentLineSrc(),
                                   this.sourceParser.getCurrentLineNumber());
        do{
            name = this.sourceParser.getTokenMeaning();
            if(!this.sourceParser.nextToken().getTokenValue().equals(Token.TokenValue.COLON))
                throw new PascalException("Erreur de syntaxe ",
                                       "Mot clé ':' attendu au lieu de : "+this.sourceParser.getCurrToken().getTokenValue().getVal(),
                                       this.sourceParser.getCurrentLineNumber());
            if(!this.sourceParser.nextToken().getTokenValue().equals(Token.TokenValue.TYPE))
                throw new PascalException("Erreur de syntaxe ",
                                       "Mot clé 'byte' attendu au lieu de : "+this.sourceParser.getCurrToken().getTokenValue().getVal(),
                                       this.sourceParser.getCurrentLineNumber());
            st.addPascalSymbol(new VariableSymbol(name,this.sourceParser.getTokenMeaning()));
            if(!this.sourceParser.nextToken().getTokenValue().equals(Token.TokenValue.SEMICOLON))
                throw new PascalException("Erreur de syntaxe ",
                                       "Mot clé ';' attendu au lieu de : "+this.sourceParser.getCurrToken().getTokenValue().getVal(),
                                       this.sourceParser.getCurrentLineNumber());
        }while(this.sourceParser.nextToken().getTokenValue().equals(Token.TokenValue.NAME));

    }

    private void insts() throws PascalException{
        if(!this.sourceParser.getCurrToken().getTokenValue().equals(Token.TokenValue.BEGIN))
            throw new PascalException("Erreur de syntaxe ",
                                       "Mot clé 'begin' attendu au lieu de : "+this.sourceParser.getCurrToken().getTokenValue().getVal(),
                                       this.sourceParser.getCurrentLineNumber());
        do{
            this.sourceParser.nextToken();
            inst(null);
        }while(this.sourceParser.getCurrToken().getTokenValue().equals(Token.TokenValue.SEMICOLON));
        if(!this.sourceParser.getCurrToken().getTokenValue().equals(Token.TokenValue.END))
            throw new PascalException("Erreur de syntaxe : ",
                                       "Mot clé 'end' attendu au lieu de :  "+this.sourceParser.getCurrToken().getMeaning(),
                                       this.sourceParser.getCurrentLineNumber());
        this.srcInstructionsList.addInstruction(new HltInstruction());
    }

    private void inst(ISrcInstruction instructionOwner) throws PascalException{
        switch(this.sourceParser.getCurrToken().getTokenValue()){
            case NAME : //LET
                instLet(instructionOwner);
                break;
            case IF :
                instIf(instructionOwner);
                break;
            case WHILE :
                instWhile(instructionOwner);
                break;
            case WRITE :
                instWrite(instructionOwner);
                break;
            case READ :
                instRead(instructionOwner);
                break;
            default :
                break;
        }
    }

    // check & construct let instruction
    private void instLet(ISrcInstruction instructionOwner) throws PascalException{
        String identifier = this.sourceParser.getTokenMeaning();
        if(!this.st.isSymbolExist(identifier)){
                throw new PascalException("Erreur de syntaxe",
                                        " Nom de symbol : "+identifier+", "+ "non trouvé !",
                                        this.sourceParser.getCurrentLineNumber());
        }
        ASymbol identifierSymb = this.st.getSymbol(identifier);
        if(identifierSymb.getSymbolClass() == ASymbol.SymbolClass.CONSTANTE){
                throw new PascalException("Erreur de syntaxe",
                                        " L'identifiant : "+identifier+", "+ "est une constante déjà définie !",
                                        this.sourceParser.getCurrentLineNumber());
        }
        if(!this.sourceParser.nextToken().getTokenValue().equals(Token.TokenValue.LET))
                throw new PascalException("Erreur de syntaxe ",
                                       "Mot clé ':=' attendu au lieu de : "+this.sourceParser.getCurrToken().getTokenValue().getVal(),
                                       this.sourceParser.getCurrentLineNumber());
        this.sourceParser.nextToken();//expression 
        ExpressionAnalyse ea = new ExpressionAnalyse(this.sourceParser, this.EXP_DELIMITERS);
        //AExpressionSymbol es = ea.getExprStack();
        //es.print();
        ISrcInstruction letSrcInstruction = new LetInstruction(identifier, ea);
        this.registerInstruction(letSrcInstruction, instructionOwner);    
    }
    
    //if
    private void instIf(ISrcInstruction instructionOwner) throws PascalException{
        this.sourceParser.nextToken();//condition
        ConditionAnalyse condAnalyse = new ConditionAnalyse(this.sourceParser,this.RELOP_TOKENS, this.EXP_DELIMITERS);
        if(!this.sourceParser.getCurrToken().getTokenValue().equals(Token.TokenValue.THEN)){
                throw new PascalException("Erreur de syntaxe ",
                                       " mot clé 'then' attendu au lieu de : "+this.sourceParser.getCurrToken().getTokenValue().getVal(),
                                       this.sourceParser.getCurrentLineNumber());
        }
        ISrcInstruction ifSrcInstruction = new IfInstruction(condAnalyse);
        this.registerInstruction(ifSrcInstruction, instructionOwner);
        this.sourceParser.nextToken();//instruction
        inst(ifSrcInstruction);
    }
    
    //while
    private void instWhile(ISrcInstruction instructionOwner) throws PascalException{
        this.sourceParser.nextToken();//condition
        ConditionAnalyse condAnalyse = new ConditionAnalyse(this.sourceParser,this.RELOP_TOKENS, this.EXP_DELIMITERS);
        if(!this.sourceParser.getCurrToken().getTokenValue().equals(Token.TokenValue.DO)){
                throw new PascalException("Erreur de syntaxe ",
                                       " mot clé 'do' attendu au lieu de : "+this.sourceParser.getCurrToken().getTokenValue().getVal(),
                                       this.sourceParser.getCurrentLineNumber());
        }
        ISrcInstruction whileSrcInstruction = new WhileInstruction(condAnalyse);
        this.registerInstruction(whileSrcInstruction, instructionOwner);
        this.sourceParser.nextToken();//instruction
        inst(whileSrcInstruction);
    }
    
    //write
    private void instWrite(ISrcInstruction instructionOwner) throws PascalException{
        throw new PascalException("Non supportée !",
                                   " instruction 'write' non supportée dans cette version du compilateur : ",
                                   this.sourceParser.getCurrentLineNumber());
    }
    
    //read
    private void instRead(ISrcInstruction instructionOwner) throws PascalException{
        throw new PascalException("Non supportée !",
                                   " instruction 'read' non supportée dans cette version du compilateur : ",
                                   this.sourceParser.getCurrentLineNumber());
    }
    
    //register instruction
    private void registerInstruction(ISrcInstruction instructionToRegister, ISrcInstruction instructionOwner){
        if(instructionOwner != null) {
            instructionOwner.registerWardsInstruction(instructionToRegister);//owner owns all its wards instructions
        }else{
            this.srcInstructionsList.addInstruction(instructionToRegister);
        }
    }

}
