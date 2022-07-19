/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pascal.parser;

import java.util.Iterator;
import java.util.TreeMap;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import pascal.PascalException;
import pascal.tokenfactory.Token;
import static org.junit.Assert.*;

/**
 *
 * @author prog
 */
public class LineParserTest {
    private TreeMap<Integer, Token> allToken ;
    private Iterator<Integer> indexIterator;
    private Token currToken;

    public LineParserTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        this.allToken = new TreeMap<Integer, Token>();
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of parseLine method, of class LineParser.
     */
    @Test
    public void testParseLine_ponctuation() throws PascalException{
        System.out.println("parseLine ponctuation");
        String line = "   . ;   ,  :   ";
        Integer LineNumber = 1;
        LineParser instance = new LineParser(this.allToken);
        instance.parseLine(line, LineNumber);
        this.indexIterator=this.allToken.keySet().iterator();
        if(this.indexIterator.hasNext()){
            this.currToken = this.allToken.get(this.indexIterator.next());
            assertEquals((Token.TokenValue.DOT), this.currToken.getTokenValue());
            this.currToken = this.allToken.get(this.indexIterator.next());
            assertEquals((Token.TokenValue.SEMICOLON),this.currToken.getTokenValue());
            this.currToken = this.allToken.get(this.indexIterator.next());
            assertEquals((Token.TokenValue.COMMA), this.currToken.getTokenValue());
            this.currToken = this.allToken.get(this.indexIterator.next());
            assertEquals((Token.TokenValue.COLON), this.currToken.getTokenValue());
        }else
            assertFalse(true);
    }

    /**
     * Test of parseLine method, of class LineParser.
     */
    @Test
    public void testParseLine_simple_expression() throws PascalException{
        System.out.println("parseLine number");
        String line = " =  2 ";
        Integer LineNumber = 1;
        LineParser instance = new LineParser(this.allToken);
        instance.parseLine(line, LineNumber);
        this.indexIterator=this.allToken.keySet().iterator();
        if(this.indexIterator.hasNext()){
            this.currToken = this.allToken.get(this.indexIterator.next());
            assertEquals((Token.TokenValue.EQUAL),this.currToken.getTokenValue());
            this.currToken = this.allToken.get(this.indexIterator.next());
            assertEquals((Token.TokenValue.NUMBER),this.currToken.getTokenValue());
            assertEquals( "2",this.currToken.getMeaning());
        }else
            assertFalse(true);
    }

    /**
     * Test of parseLine method, of class LineParser.
     */
    @Test
    public void testParseLine_expression() throws PascalException{
        System.out.println("parseLine expression");
        String line = "  =2 * 3 + 5 - 6 / 7 + (12 -2) = 7 > 8 < 45";
        Integer LineNumber = 1;
        LineParser instance = new LineParser(this.allToken);
        instance.parseLine(line, LineNumber);
        this.indexIterator=this.allToken.keySet().iterator();
        if(this.indexIterator.hasNext()){
            this.currToken = this.allToken.get(this.indexIterator.next());
            assertEquals((Token.TokenValue.EQUAL),this.currToken.getTokenValue());
            this.currToken = this.allToken.get(this.indexIterator.next());
            assertEquals((Token.TokenValue.NUMBER),this.currToken.getTokenValue());
            assertEquals("2",this.currToken.getMeaning());
            this.currToken = this.allToken.get(this.indexIterator.next());
            assertEquals(this.currToken.getTokenValue(),(Token.TokenValue.MUL));
            this.currToken = this.allToken.get(this.indexIterator.next());
            assertEquals(this.currToken.getTokenValue(),(Token.TokenValue.NUMBER));
            assertEquals("3",this.currToken.getMeaning());
            this.currToken = this.allToken.get(this.indexIterator.next());
            assertEquals(this.currToken.getTokenValue(),(Token.TokenValue.PLUS));
            this.currToken = this.allToken.get(this.indexIterator.next());
            assertEquals(this.currToken.getTokenValue(),(Token.TokenValue.NUMBER));
            assertEquals("5",this.currToken.getMeaning());
            this.currToken = this.allToken.get(this.indexIterator.next());
            assertEquals(this.currToken.getTokenValue(),(Token.TokenValue.MINUS));
            this.currToken = this.allToken.get(this.indexIterator.next());
            assertEquals(this.currToken.getTokenValue(),(Token.TokenValue.NUMBER));
            assertEquals("6",this.currToken.getMeaning());
            this.currToken = this.allToken.get(this.indexIterator.next());
            assertEquals(this.currToken.getTokenValue(),(Token.TokenValue.DIV));
            this.currToken = this.allToken.get(this.indexIterator.next());
            assertEquals(this.currToken.getTokenValue(),(Token.TokenValue.NUMBER));
            assertEquals("7",this.currToken.getMeaning());
            this.currToken = this.allToken.get(this.indexIterator.next());
            assertEquals(this.currToken.getTokenValue(),(Token.TokenValue.PLUS));
            this.currToken = this.allToken.get(this.indexIterator.next());
            assertEquals(this.currToken.getTokenValue(),(Token.TokenValue.LP));
            this.currToken = this.allToken.get(this.indexIterator.next());
            assertEquals(this.currToken.getTokenValue(),(Token.TokenValue.NUMBER));
            assertEquals("12",this.currToken.getMeaning());
            this.currToken = this.allToken.get(this.indexIterator.next());
            assertEquals(this.currToken.getTokenValue(),(Token.TokenValue.MINUS));
            this.currToken = this.allToken.get(this.indexIterator.next());
            assertEquals((Token.TokenValue.NUMBER),this.currToken.getTokenValue());
            this.currToken = this.allToken.get(this.indexIterator.next());
            assertEquals(this.currToken.getTokenValue(),(Token.TokenValue.RP));
            this.currToken = this.allToken.get(this.indexIterator.next());
            assertEquals(this.currToken.getTokenValue(),(Token.TokenValue.EQUAL));
            this.currToken = this.allToken.get(this.indexIterator.next());
            assertEquals(this.currToken.getTokenValue(),(Token.TokenValue.NUMBER));
            this.currToken = this.allToken.get(this.indexIterator.next());
            assertEquals(this.currToken.getTokenValue(),(Token.TokenValue.GREAT));
            this.currToken = this.allToken.get(this.indexIterator.next());
            assertEquals(this.currToken.getTokenValue(),(Token.TokenValue.NUMBER));
            this.currToken = this.allToken.get(this.indexIterator.next());
            assertEquals(this.currToken.getTokenValue(),(Token.TokenValue.LESS));
            this.currToken = this.allToken.get(this.indexIterator.next());
            assertEquals(this.currToken.getTokenValue(),(Token.TokenValue.NUMBER));
        }else
            assertFalse(true);
        
    }

     /**
     * Test of parseLine method, of class LineParser.
     */
    @Test
    public void testParseLine_type() throws PascalException{
        System.out.println("parseLine type");
        String line = "   byte \n";
        Integer LineNumber = 1;
        LineParser instance = new LineParser(this.allToken);
        instance.parseLine(line, LineNumber);
        this.indexIterator=this.allToken.keySet().iterator();
        if(this.indexIterator.hasNext()){
            this.currToken = this.allToken.get(this.indexIterator.next());
            assertEquals((Token.TokenValue.TYPE),this.currToken.getTokenValue());
        }else
            assertFalse(true);
    }

    /**
     * Test of parseLine method, of class LineParser.
     */
    @Test
    public void testParseLine_keywords() throws PascalException{
        System.out.println("parseLine keywords");
        String line = " program     const    var  begin :=  if then while do write  read         end  ";
        Integer LineNumber = 1;
        LineParser instance = new LineParser(this.allToken);
        instance.parseLine(line, LineNumber);
        this.indexIterator=this.allToken.keySet().iterator();
        if(this.indexIterator.hasNext()){
            this.currToken = this.allToken.get(this.indexIterator.next());
            assertEquals((Token.TokenValue.PROGRAM),this.currToken.getTokenValue());
            this.currToken = this.allToken.get(this.indexIterator.next());
            assertEquals((Token.TokenValue.CONST),this.currToken.getTokenValue());
            this.currToken = this.allToken.get(this.indexIterator.next());
            assertEquals((Token.TokenValue.VAR),this.currToken.getTokenValue());
            this.currToken = this.allToken.get(this.indexIterator.next());
            assertEquals((Token.TokenValue.BEGIN),this.currToken.getTokenValue());
            this.currToken = this.allToken.get(this.indexIterator.next());
            assertEquals((Token.TokenValue.LET),this.currToken.getTokenValue());
            this.currToken = this.allToken.get(this.indexIterator.next());
            assertEquals((Token.TokenValue.IF),this.currToken.getTokenValue());
            this.currToken = this.allToken.get(this.indexIterator.next());
            assertEquals((Token.TokenValue.THEN),this.currToken.getTokenValue());
            this.currToken = this.allToken.get(this.indexIterator.next());
            assertEquals((Token.TokenValue.WHILE),this.currToken.getTokenValue());
            this.currToken = this.allToken.get(this.indexIterator.next());
            assertEquals((Token.TokenValue.DO),this.currToken.getTokenValue());
            this.currToken = this.allToken.get(this.indexIterator.next());
            assertEquals((Token.TokenValue.WRITE),this.currToken.getTokenValue());
            this.currToken = this.allToken.get(this.indexIterator.next());
            assertEquals((Token.TokenValue.READ),this.currToken.getTokenValue());
            this.currToken = this.allToken.get(this.indexIterator.next());
            assertEquals((Token.TokenValue.END),this.currToken.getTokenValue());
        }else
            assertFalse(true);
        //this.indexIterator=this.allToken.keySet().iterator();//raz iterator
    }

    /**
     * Test of parseLine method, of class LineParser.
     */
    @Test
    public void testParseLine_name() throws PascalException{
        System.out.println("parseLine name");
        String line = "  begin myname end name2 var othername  var2 const4  begin9 \n";
        Integer LineNumber = 1;
        LineParser instance = new LineParser(this.allToken);
        instance.parseLine(line, LineNumber);
        this.indexIterator=this.allToken.keySet().iterator();
        if(this.indexIterator.hasNext()){
            this.currToken = this.allToken.get(this.indexIterator.next());
            assertEquals((Token.TokenValue.BEGIN), this.currToken.getTokenValue());
            this.currToken = this.allToken.get(this.indexIterator.next());
            assertEquals((Token.TokenValue.NAME),this.currToken.getTokenValue());
            assertEquals(this.currToken.getMeaning(), "myname");
            this.currToken = this.allToken.get(this.indexIterator.next());
            assertEquals((Token.TokenValue.END),this.currToken.getTokenValue());
            this.currToken = this.allToken.get(this.indexIterator.next());
            assertEquals((Token.TokenValue.NAME),this.currToken.getTokenValue());
            assertEquals(this.currToken.getMeaning(), "name2");
            this.currToken = this.allToken.get(this.indexIterator.next());
            assertEquals((Token.TokenValue.VAR), this.currToken.getTokenValue());
            this.currToken = this.allToken.get(this.indexIterator.next());
            assertEquals((Token.TokenValue.NAME), this.currToken.getTokenValue());
            assertEquals(this.currToken.getMeaning(), "othername");
            this.currToken = this.allToken.get(this.indexIterator.next());
            assertEquals((Token.TokenValue.NAME), this.currToken.getTokenValue());
            assertEquals(this.currToken.getMeaning(), "var2");
            this.currToken = this.allToken.get(this.indexIterator.next());
            assertEquals((Token.TokenValue.NAME), this.currToken.getTokenValue());
            assertEquals(this.currToken.getMeaning(), "const4");
            this.currToken = this.allToken.get(this.indexIterator.next());
            assertEquals((Token.TokenValue.NAME), this.currToken.getTokenValue());
            assertEquals(this.currToken.getMeaning(), "begin9");
        }else
            assertFalse(true);

    }
    
    /**
     * Test of parseLine method, of class LineParser.
     */
    @Test
    public void testParseLine_rel_op() throws PascalException{
        System.out.println("parseLine relation operators");
        //String line = "  =   <>   <   >        ";
        String line = "  =   <>   <   >   <=   <  >= ";
        Integer LineNumber = 1;
        LineParser instance = new LineParser(this.allToken);
        instance.parseLine(line, LineNumber);
        this.indexIterator=this.allToken.keySet().iterator();
        if(this.indexIterator.hasNext()){
            this.currToken = this.allToken.get(this.indexIterator.next());
            assertEquals((Token.TokenValue.EQUAL),this.currToken.getTokenValue());
            this.currToken = this.allToken.get(this.indexIterator.next());
            assertEquals((Token.TokenValue.DIFF),this.currToken.getTokenValue());
            this.currToken = this.allToken.get(this.indexIterator.next());
            assertEquals((Token.TokenValue.LESS),this.currToken.getTokenValue());
            this.currToken = this.allToken.get(this.indexIterator.next());
            assertEquals((Token.TokenValue.GREAT),this.currToken.getTokenValue());
            this.currToken = this.allToken.get(this.indexIterator.next());
            assertEquals((Token.TokenValue.LEQ),this.currToken.getTokenValue());
            this.currToken = this.allToken.get(this.indexIterator.next());
            assertEquals((Token.TokenValue.LESS),this.currToken.getTokenValue());
            this.currToken = this.allToken.get(this.indexIterator.next());
            assertEquals((Token.TokenValue.GEQ),this.currToken.getTokenValue());
            this.currToken = this.allToken.get(this.indexIterator.next());
        }else
            assertFalse(true);
        //this.indexIterator=this.allToken.keySet().iterator();//raz iterator
    }

    /**
     * Test of parseLine method, of class LineParser.
     */
    @Test
    public void testParseLine_mix() throws PascalException{
        System.out.println("parseLine mix");
        String line = "  begin myname end name2 var; othername.  \n";
        Integer LineNumber = 1;
        LineParser instance = new LineParser(this.allToken);
        instance.parseLine(line, LineNumber);
        this.indexIterator=this.allToken.keySet().iterator();
        if(this.indexIterator.hasNext()){
            this.currToken = this.allToken.get(this.indexIterator.next());
            assertEquals((Token.TokenValue.BEGIN),this.currToken.getTokenValue());
            this.currToken = this.allToken.get(this.indexIterator.next());
            assertEquals((Token.TokenValue.NAME),this.currToken.getTokenValue());
            assertEquals(this.currToken.getMeaning(), "myname");
            this.currToken = this.allToken.get(this.indexIterator.next());
            assertEquals((Token.TokenValue.END),this.currToken.getTokenValue());
            this.currToken = this.allToken.get(this.indexIterator.next());
            assertEquals((Token.TokenValue.NAME),this.currToken.getTokenValue());
            assertEquals(this.currToken.getMeaning(), "name2");
            this.currToken = this.allToken.get(this.indexIterator.next());
            assertEquals((Token.TokenValue.VAR),this.currToken.getTokenValue());
            this.currToken = this.allToken.get(this.indexIterator.next());
            assertEquals((Token.TokenValue.SEMICOLON), this.currToken.getTokenValue());
            this.currToken = this.allToken.get(this.indexIterator.next());
            assertEquals((Token.TokenValue.NAME),this.currToken.getTokenValue());
            assertEquals(this.currToken.getMeaning(), "othername");
            this.currToken = this.allToken.get(this.indexIterator.next());
            assertEquals((Token.TokenValue.DOT),this.currToken.getTokenValue());
        }else
            assertFalse(true);

    }

     /**
     * Test of parseLine method, of class LineParser.
     */
    @Test(expected=PascalException.class)
    public void testParseLine_Fail() throws PascalException{
        System.out.println("parseLine Fail");
        String line = "  @@@@@@@ ####### \n";
        Integer LineNumber = 1;
        LineParser instance = new LineParser(this.allToken);
        if(!instance.parseLine(line, LineNumber)){
            assertTrue(true);
            throw new PascalException("Token not found in "+line," Line number : ",LineNumber);
        }else
            assertTrue(false);
    }

}