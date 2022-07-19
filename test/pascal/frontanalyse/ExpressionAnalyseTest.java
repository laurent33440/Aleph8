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
import pascal.PascalException;
import pascal.frontanalyse.expressionstack.AExpressionSymbol;
import pascal.frontanalyse.expressionstack.ExpressionStack;
import pascal.frontanalyse.expressionstack.IExpressionList;
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
public class ExpressionAnalyseTest {

    private ISourceParser sp;
    private ISymbolTable st ;
    private final Vector<Token.TokenValue> RELOP_TOKENS = new Vector<Token.TokenValue>();
    private final Vector<Token.TokenValue> EXP_DELIMITERS = new Vector<Token.TokenValue>();
    final private String value = "  2   ;";
    final private String add = "  3 + 4 ; ";
    final private String minus = "  10 - 6  ; ";
    final private String addOps = "  3 + 4 + 8 -7 -2  ;";
    final private String err1 = "  +  +  4 ;";
    final private String err2 = "  3  4 ;";
    final private String justVar = " var1 ;";
    final private String addOpsCst1 = "  3  + cte1 ;";
    final private String err3 = "  3  + unknown ;";
    final private String err4 = "  3  + var3 ";
    final private String addOpsVar1 = "  3  + cte1 - var2 ;";
    //final private String mul = "  5 * 7   ";
    //final private String div = "  12 / 4   ";
    //final private String div0 = "  25 / 0   ";
    //final private String mulDivOps = " 4 * 8 / 7 * 5 / 2";
    //final private String multiOps1 = " 3 + 4 * 8 / 7 - 5";
    //final private String multiOps = "  3 + 4 + 8 -7 -2+2 *8 /12 +4 *45 -9  ";
    
    

    public ExpressionAnalyseTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
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
        this.st.addPascalSymbol(new ConstanteSymbol("cte1", "1"));
        this.st.addPascalSymbol(new VariableSymbol("var1", "byte"));
        this.st.addPascalSymbol(new VariableSymbol("var2", "byte"));
        this.st.addPascalSymbol(new VariableSymbol("var3", "byte"));
    }

    @After
    public void tearDown() {
    }

//    @Test //@Ignore
//    public void testValue() throws PascalException{
//        System.out.println("testExpressionAnalyse_value : "+value);
//        this.sp = new SourceParser(value);
//        ExpressionAnalyse ea = new ExpressionAnalyse(this.sp);
//        AExpressionSymbol es = ea.getExprStack();
//        es.print();    
//    }
    
    @Test //@Ignore
    public void testValueToken() throws PascalException{
        System.out.println("testExpressionAnalyseToken_value : "+value);
        this.sp = new SourceParser(value);
        ExpressionAnalyse ea = new ExpressionAnalyse(this.sp,this.EXP_DELIMITERS);
        IExpressionList es = ea.getExprStack();
        es.print();    
    }

//    @Test //@Ignore
//    public void testAdd() throws PascalException{
//        System.out.println("testExpressionAnalyse_add : "+add);
//        this.sp = new SourceParser(add);
//        ExpressionAnalyse ea = new ExpressionAnalyse(this.sp);
//        AExpressionSymbol es = ea.getExprStack();
//        es.print();
//    }
    
    @Test //@Ignore
    public void testAddToken() throws PascalException{
        System.out.println("testExpressionAnalyse_addToken : "+add);
        this.sp = new SourceParser(add);
        ExpressionAnalyse ea = new ExpressionAnalyse(this.sp,this.EXP_DELIMITERS);
        IExpressionList es = ea.getExprStack();
        es.print();
    }

//    @Test //@Ignore
//    public void testMinus() throws PascalException{
//        System.out.println("testExpressionAnalyse_minus : "+minus);
//        this.sp = new SourceParser(minus);
//        ExpressionAnalyse ea = new ExpressionAnalyse(this.sp);
//        AExpressionSymbol es = ea.getExprStack();
//        es.print();
//    }
    
    @Test //@Ignore
    public void testMinusToken() throws PascalException{
        System.out.println("testExpressionAnalyse_minusToken : "+minus);
        this.sp = new SourceParser(minus);
        ExpressionAnalyse ea = new ExpressionAnalyse(this.sp,this.EXP_DELIMITERS);
        IExpressionList es = ea.getExprStack();
        es.print();
    }


//    @Test
//    public void testAddOps() throws PascalException{
//        System.out.println("testExpressionAnalyse_add_minus_operations : "+addOps);
//        this.sp = new SourceParser(addOps);
//        ExpressionAnalyse ea = new ExpressionAnalyse(this.sp);
//        AExpressionSymbol es = ea.getExprStack();
//        es.print();
//    }
    
    @Test
    public void testAddOpsToken() throws PascalException{
        System.out.println("testExpressionAnalyse_add_minus_operationsToken : "+addOps);
        this.sp = new SourceParser(addOps);
        ExpressionAnalyse ea = new ExpressionAnalyse(this.sp,this.EXP_DELIMITERS);
        IExpressionList es = ea.getExprStack();
        es.print();
    }
    
    // tests with constantes and vars
    
//    @Test
//    public void testVar() throws PascalException{
//        System.out.println("testExpressionAnalyse_just_var : "+justVar);
//        this.sp = new SourceParser(justVar);
//        ExpressionAnalyse ea = new ExpressionAnalyse(this.sp);
//        AExpressionSymbol es = ea.getExprStack();
//        es.print();
//    }
    
    @Test
    public void testVarToken() throws PascalException{
        System.out.println("testExpressionAnalyse_just_varToken : "+justVar);
        this.sp = new SourceParser(justVar);
        ExpressionAnalyse ea = new ExpressionAnalyse(this.sp,this.EXP_DELIMITERS);
        IExpressionList es = ea.getExprStack();
        es.print();
    }
    
//    @Test
//    public void testAddOpsCst() throws PascalException{
//        System.out.println("testExpressionAnalyse_add_operations_constant : "+addOpsCst1);
//        this.sp = new SourceParser(addOpsCst1);
//        ExpressionAnalyse ea = new ExpressionAnalyse(this.sp);
//        AExpressionSymbol es = ea.getExprStack();
//        es.print();
//    }
    
     @Test
    public void testAddOpsCstToken() throws PascalException{
        System.out.println("testExpressionAnalyse_add_operations_constantToken : "+addOpsCst1);
        this.sp = new SourceParser(addOpsCst1);
        ExpressionAnalyse ea = new ExpressionAnalyse(this.sp,this.EXP_DELIMITERS);
        IExpressionList es = ea.getExprStack();
        es.print();
    }
    
//    @Test
//    public void testAddOpsVar() throws PascalException{
//        System.out.println("testExpressionAnalyse_add_operations_constant : "+addOpsVar1);
//        this.sp = new SourceParser(addOpsVar1);
//        ExpressionAnalyse ea = new ExpressionAnalyse(this.sp);
//        AExpressionSymbol es = ea.getExprStack();
//        es.print();
//    }
    
    @Test
    public void testAddOpsVarToken() throws PascalException{
        System.out.println("testExpressionAnalyse_add_operations_constantToken : "+addOpsVar1);
        this.sp = new SourceParser(addOpsVar1);
        ExpressionAnalyse ea = new ExpressionAnalyse(this.sp,this.EXP_DELIMITERS);
        IExpressionList es = ea.getExprStack();
        es.print();
    }
    
    //tests syntax errors
    
//    @Test
//    public void testNoPrimary() throws PascalException{
//        System.out.println("testExpressionAnalyse_no_primary : "+err1);
//        this.sp = new SourceParser(err1);
//        ExpressionAnalyse ea = new ExpressionAnalyse(this.sp);
//        AExpressionSymbol es = ea.getExprStack();
//        es.print();
//    }
    
    @Test
    public void testNoPrimaryToken() throws PascalException{
        System.out.println("testExpressionAnalyse_no_primaryToken : "+err1);
        this.sp = new SourceParser(err1);
        ExpressionAnalyse ea = new ExpressionAnalyse(this.sp,this.EXP_DELIMITERS);
        IExpressionList es = ea.getExprStack();
        es.print();
    }
    
//    @Test
//    public void testNoAddOpp() throws PascalException{
//        System.out.println("testExpressionAnalyse_no_add_opp : "+err2);
//        this.sp = new SourceParser(err2);
//        ExpressionAnalyse ea = new ExpressionAnalyse(this.sp);
//        AExpressionSymbol es = ea.getExprStack();
//        es.print();
//    }
    
    @Test
    public void testNoAddOppToken() throws PascalException{
        System.out.println("testExpressionAnalyse_no_add_oppToken : "+err2);
        this.sp = new SourceParser(err2);
        ExpressionAnalyse ea = new ExpressionAnalyse(this.sp,this.EXP_DELIMITERS);
        IExpressionList es = ea.getExprStack();
        es.print();
    }
    
//    @Test
//    public void testNoSymbol() throws PascalException{
//        System.out.println("testExpressionAnalyse_no_symbol_name : "+err3);
//        this.sp = new SourceParser(err3);
//        ExpressionAnalyse ea = new ExpressionAnalyse(this.sp);
//        AExpressionSymbol es = ea.getExprStack();
//        es.print();
//    }
    
    @Test
    public void testNoSymbolToken() throws PascalException{
        System.out.println("testExpressionAnalyse_no_symbol_nameToken : "+err3);
        this.sp = new SourceParser(err3);
        ExpressionAnalyse ea = new ExpressionAnalyse(this.sp,this.EXP_DELIMITERS);
        IExpressionList es = ea.getExprStack();
        es.print();
    }
    
    @Test
    public void testNoTerminal() throws PascalException{
        System.out.println("testExpressionAnalyse_no_delimiter : "+err4);
        this.sp = new SourceParser(err4);
        ExpressionAnalyse ea = new ExpressionAnalyse(this.sp,this.EXP_DELIMITERS);
        IExpressionList es = ea.getExprStack();
        es.print();
    }

    
    // mul / div tests : futur use
    
//    @Test //@Ignore
//    public void testMul() throws PascalException{
//        System.out.println("testExpressionAnalyse_mul : "+mul);
//        this.sp = new SourceParser(mul);
//        ExpressionAnalyse ea = new ExpressionAnalyse(this.sp);
//        AExpressionSymbol es = ea.getExprStack();
//        es.print();
//    }

//    @Test //@Ignore
//    public void testDiv() throws PascalException{
//        System.out.println("testExpressionAnalyse_div : "+div);
//        this.sp = new SourceParser(div);
//        ExpressionAnalyse ea = new ExpressionAnalyse(this.sp);
//        AExpressionSymbol es = ea.getExprStack();
//        es.print();
//    }

//    @Test(expected=pascal.PascalException.class) @Ignore
//    public void testDiv0() throws PascalException{
//        System.out.println("testExpressionAnalyse_div_by_0 : "+div0);
//        this.sp = new SourceParser(div0);
//        ExpressionAnalyse ea = new ExpressionAnalyse(this.sp);
//        AExpressionSymbol es = ea.getExprStack();
//        es.print();
//        //assertTrue(false);
//    }
    
//    @Test @Ignore
//    public void testMulDivOps1() throws PascalException{
//        System.out.println("testExpressionAnalyse_mul_div_operations : "+mulDivOps);
//        this.sp = new SourceParser(mulDivOps);
//        ExpressionAnalyse ea = new ExpressionAnalyse(this.sp);
//        AExpressionSymbol es = ea.getExprStack();
//        es.print();
//    }
//
//    @Test @Ignore
//    public void testMultiOps1() throws PascalException{
//        System.out.println("testExpressionAnalyse_multi_operations1 : "+multiOps1);
//        this.sp = new SourceParser(multiOps1);
//        ExpressionAnalyse ea = new ExpressionAnalyse(this.sp);
//        AExpressionSymbol es = ea.getExprStack();
//        es.print();
//    }
//    
//    @Test @Ignore
//    public void testMultiOps() throws PascalException{
//        System.out.println("testExpressionAnalyse_multi_operations");
//        this.sp = new SourceParser(multiOps);
//        ExpressionAnalyse ea = new ExpressionAnalyse(this.sp);
//        AExpressionSymbol es = ea.getExprStack();
//        es.print();
//    }
    
    //extract expression tokens from source parser. Ends tokens list with "NOTOKEN"  
//    private Vector<Token> extractExpressionFromSourceParser(){
//        Vector<Token> expressionToken = new Vector<Token>();
//        while(!this.EXP_DELIMITERS.contains(this.sp.getTokenSymbol())){
//            expressionToken.add(this.sp.getCurrToken());
//            this.sp.nextToken();
//        }
//        expressionToken.add(new Token(ITokenDef.TokenValue.NOTOKEN));// ends expression
//        return expressionToken;
//    }
    
    
}