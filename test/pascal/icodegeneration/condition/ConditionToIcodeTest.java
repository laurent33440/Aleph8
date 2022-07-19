/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pascal.icodegeneration.condition;

import java.util.Iterator;
import java.util.Vector;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import pascal.PascalException;
import pascal.frontanalyse.ConditionAnalyse;
import pascal.icodegeneration.EqlIcode;
import pascal.icodegeneration.AIcode;
import pascal.icodegeneration.IcodeList;
import pascal.icodegeneration.LdaIcode;
import pascal.icodegeneration.LdiIcode;
import pascal.icodegeneration.LdrIcode;
import pascal.icodegeneration.LssIcode;
import pascal.icodegeneration.NeqIcode;
import pascal.parser.ISourceParser;
import pascal.parser.SourceParser;
import pascal.symboltable.ConstanteSymbol;
import pascal.symboltable.ISymbolTable;
import pascal.symboltable.SymbolTable;
import pascal.symboltable.VariableSymbol;
import pascal.tokenfactory.Token;

/**
 *
 * @author laurent
 */
public class ConditionToIcodeTest {
    private ISourceParser sp;
    private ISymbolTable st ;
    private final Vector<Token.TokenValue> RELOP_TOKENS = new Vector<Token.TokenValue>();
    private final Vector<Token.TokenValue> EXP_DELIMITERS = new Vector<Token.TokenValue>();
    private IcodeList iclRef;
    
    final private String cond1 = "  2  = 3   do ";
    final private String cond2 = "  4 <> 5  then";
    final private String cond3= "   2 < var1  do";
    
    public ConditionToIcodeTest() {
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
    public void testEqualValueCondition() throws PascalException{
        System.out.println("test génération egalité de valeurs : "+this.cond1);
        this.sp = new SourceParser(this.cond1);
        ConditionAnalyse ca = new ConditionAnalyse(this.sp, this.RELOP_TOKENS, this.EXP_DELIMITERS);
        ConditionToIcode condIcode = new ConditionToIcode(ca);
        this.iclRef = new IcodeList();
        iclRef.add(new LdiIcode((byte)2));
        iclRef.add(new LdiIcode((byte)3));
        iclRef.add(new EqlIcode());
        assertNotNull("conditionToIcode null",condIcode);
        IcodeList eic = condIcode.getIcodeList();
        assertNotNull("condition icode list null!",eic);
        Iterator<AIcode> ir = iclRef.getIterator();
        Iterator<AIcode> it = eic.getIterator();
        assertNotNull("iterator",it);
        if(it.hasNext()){
            while(ir.hasNext()){
                assertEquals("icodeList items ",ir.next().toString(), it.next().toString());
            }
            //print
            eic.print();
        }else
            fail("Icode list empty");
       
    }
    
    @Test
    public void testDiffValueCondition() throws PascalException{
        System.out.println("test génération différent de valeurs : "+this.cond2);
        this.sp = new SourceParser(this.cond2);
        ConditionAnalyse ca = new ConditionAnalyse(this.sp, this.RELOP_TOKENS, this.EXP_DELIMITERS);
        ConditionToIcode condIcode = new ConditionToIcode(ca);
        this.iclRef = new IcodeList();
        iclRef.add(new LdiIcode((byte)4));
        iclRef.add(new LdiIcode((byte)5));
        iclRef.add(new NeqIcode());
        assertNotNull("conditionToIcode null",condIcode);
        IcodeList eic = condIcode.getIcodeList();
        assertNotNull("condition icode list null!",eic);
        Iterator<AIcode> ir = iclRef.getIterator();
        Iterator<AIcode> it = eic.getIterator();
        assertNotNull("iterator",it);
        if(it.hasNext()){
            while(ir.hasNext()){
                assertEquals("icodeList items ",ir.next().toString(), it.next().toString());
            }
            //print
            eic.print();
        }else
            fail("Icode list empty");
       
    }
    
    @Test
    public void testLessValueNameCondition() throws PascalException{
        System.out.println("test génération less de valeur et nom : "+this.cond3);
        this.sp = new SourceParser(this.cond3);
        ConditionAnalyse ca = new ConditionAnalyse(this.sp, this.RELOP_TOKENS, this.EXP_DELIMITERS);
        ConditionToIcode condIcode = new ConditionToIcode(ca);
        this.iclRef = new IcodeList();
        iclRef.add(new LdiIcode((byte)2));
        iclRef.add(new LdaIcode("var1"));
        iclRef.add(new LdrIcode());
        iclRef.add(new LssIcode());
        assertNotNull("conditionToIcode null",condIcode);
        IcodeList eic = condIcode.getIcodeList();
        assertNotNull("condition icode list null!",eic);
        Iterator<AIcode> ir = iclRef.getIterator();
        Iterator<AIcode> it = eic.getIterator();
        assertNotNull("iterator",it);
        if(it.hasNext()){
            while(ir.hasNext()){
                assertEquals("icodeList items ",ir.next().toString(), it.next().toString());
            }
            //print
            eic.print();
        }else
            fail("Icode list empty");
       
    }
    
}