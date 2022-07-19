/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pascal.frontanalyse;

import java.util.Vector;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import pascal.PascalException;
import pascal.parser.ISourceParser;
import pascal.parser.SourceParser;
import pascal.symboltable.ConstanteSymbol;
import pascal.symboltable.ISymbolTable;
import pascal.symboltable.SymbolTable;
import pascal.symboltable.VariableSymbol;
import pascal.tokenfactory.Token;

/**
 *
 * @author prog
 */
public class ConditionAnalyseTest {
    private ISourceParser sp;
    private ISymbolTable st ;
    private final Vector<Token.TokenValue> RELOP_TOKENS = new Vector<Token.TokenValue>();
    private final Vector<Token.TokenValue> EXP_DELIMITERS = new Vector<Token.TokenValue>();
    
    final private String cond1 = "  2 < 3   do ";
    final private String cond2 = "  1+1 = 2  then";
    final private String cond3= "   2 = 1+1  do";
    final private String cond4= "   cst1 = 1+1  then";
    final private String cond5= "   var1 = 2  do";
    final private String cond6= "   2 <>  3  then";
    final private String condKo1= "   2 !!  3  do";
    final private String condKo2= "   2 =  3 ! 4 x 5  then";
    final private String condKo3= "   unknown <>  3  do";
    final private String condKo4= "   4 <>  3  "; //no delimiter
    
    
    public ConditionAnalyseTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        this.st = SymbolTable.getInstance();
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
        this.st.addPascalSymbol(new ConstanteSymbol("cst1", "2"));
        this.st.addPascalSymbol(new VariableSymbol("var1", "byte"));
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testgetLeftExpressionAnalyse() throws PascalException{
        System.out.println("testgetLeftExpressionAnalyse : "+this.cond3);
        this.sp = new SourceParser(this.cond3);
        ConditionAnalyse ca = new ConditionAnalyse(this.sp, this.RELOP_TOKENS, this.EXP_DELIMITERS);
        ExpressionAnalyse ea = ca.getLeftExpressionAnalyse();
        assertNotNull("ea not null",ea);
        assertEquals("2", ea.getExprStack().peekSymbol().toString());
        
    }
    
    @Test
    public void testgetRighttExpressionAnalyse() throws PascalException{
        System.out.println("testgetRightExpressionAnalyse : "+this.cond1);
        this.sp = new SourceParser(this.cond1);
        ConditionAnalyse ca = new ConditionAnalyse(this.sp, this.RELOP_TOKENS, this.EXP_DELIMITERS);
        ExpressionAnalyse ea = ca.getRightExpressionAnalyse();
        assertNotNull("ea not null",ea);
        assertEquals("3", ea.getExprStack().peekSymbol().toString());
        
    }
    
    @Test
    public void testgetRelOp() throws PascalException{
        System.out.println("testgeRelOpToken : "+this.cond1);
        this.sp = new SourceParser(this.cond1);
        ConditionAnalyse ca = new ConditionAnalyse(this.sp, this.RELOP_TOKENS, this.EXP_DELIMITERS);
        Token relOp = ca.getRelOpToken();
        assertNotNull("relOp not null",relOp);
        assertEquals("<", relOp.getTokenValue().getVal());
    }
    
    //errors
    @Test
    public void testErrNoRelOp() throws PascalException{
        System.out.println("testErrNoRelOpToken : "+this.condKo1);
        this.sp = new SourceParser(this.condKo1);
        ConditionAnalyse ca = new ConditionAnalyse(this.sp, this.RELOP_TOKENS, this.EXP_DELIMITERS);
    }
    
    @Test
    public void testErrLeftExp() throws PascalException{
        System.out.println("testErrLeftExp : "+this.condKo2);
        this.sp = new SourceParser(this.condKo2);
        ConditionAnalyse ca = new ConditionAnalyse(this.sp, this.RELOP_TOKENS, this.EXP_DELIMITERS);
    }
    
    @Test
    public void testErrUnknownExp() throws PascalException{
        System.out.println("testErrUnknownExp : "+this.condKo3);
        this.sp = new SourceParser(this.condKo3);
        ConditionAnalyse ca = new ConditionAnalyse(this.sp, this.RELOP_TOKENS, this.EXP_DELIMITERS);
    }
    
    @Test
    public void testErrNoDelimiter() throws PascalException{
        System.out.println("testErrNoDelimiter : "+this.condKo4);
        this.sp = new SourceParser(this.condKo4);
        ConditionAnalyse ca = new ConditionAnalyse(this.sp, this.RELOP_TOKENS, this.EXP_DELIMITERS);
    }
    
}