/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pascal.parser;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import pascal.PascalException;
import static org.junit.Assert.*;
import pascal.tokenfactory.ITokenDef.TokenValue;
import pascal.tokenfactory.Token;

/**
 *
 * @author prog
 */
public class SourceParserTest {

    final private String source1 = "program src1; \nconst\n\t myConst = 10; \n var\n\t myVar : byte;\nbegin\n\t myVar :=myConst;\nend.";
    final private String sourceFail = "program srcFail; \n @@@@@@";
    final private String sourceNT = "program srcNextToken; \n begin \n ; \n end.";
    final private String sourceVide = "\t\t\n\n\t";

    public SourceParserTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of nextToken method, of class SourceParser.
     */
    @Test
    public void testNextToken() {
        System.out.println("nextToken some");
        try{
            SourceParser instance = new SourceParser(source1);
            Token t = instance.getCurrToken();
            assertNotNull(t);
            TokenValue expResult = TokenValue.PROGRAM;
            TokenValue result = t.getTokenValue();
            assertEquals(expResult, result);
            t = instance.nextToken();
            assertNotNull(t);
            expResult = TokenValue.NAME;
            result = t.getTokenValue();
            assertEquals(expResult, result);
            t = instance.nextToken();
            assertNotNull(t);
            expResult = TokenValue.SEMICOLON;
            result = t.getTokenValue();
            assertEquals(expResult, result);
            t = instance.nextToken();
            assertNotNull(t);
            expResult = TokenValue.CONST;
            result = t.getTokenValue();
            assertEquals(expResult, result);
            t = instance.nextToken();//
            assertNotNull(t);
            t = instance.nextToken();//
            assertNotNull(t);
            t = instance.nextToken();//
            assertNotNull(t);
            t = instance.nextToken();//
            assertNotNull(t);
            t = instance.nextToken();
            assertNotNull(t);
            expResult = TokenValue.VAR;
            result = t.getTokenValue();
            assertEquals(expResult, result);
        }catch(PascalException pe){}
        
    }

    /**
     * Test of nextToken method, of class SourceParser.
     */
    @Test
    public void testNextTokenAll() {
        System.out.println("nextToken All");
        try{
            SourceParser instance = new SourceParser(sourceNT);
            Token t = instance.getCurrToken();
            assertNotNull(t);
            TokenValue expResult = TokenValue.PROGRAM;
            TokenValue result = t.getTokenValue();
            assertEquals(expResult, result);
            t = instance.nextToken();
            assertNotNull(t);
            expResult = TokenValue.NAME;
            result = t.getTokenValue();
            assertEquals(expResult, result);
            t = instance.nextToken();
            assertNotNull(t);
            expResult = TokenValue.SEMICOLON;
            result = t.getTokenValue();
            assertEquals(expResult, result);
            t = instance.nextToken();
            assertNotNull(t);
            expResult = TokenValue.BEGIN;
            result = t.getTokenValue();
            assertEquals(expResult, result);
            t = instance.nextToken();
            assertNotNull(t);
            expResult = TokenValue.SEMICOLON;
            result = t.getTokenValue();
            assertEquals(expResult, result);
            t = instance.nextToken();
            assertNotNull(t);
            expResult = TokenValue.END;
            result = t.getTokenValue();
            assertEquals(expResult, result);
            t = instance.nextToken();
            assertNotNull(t);
            expResult = TokenValue.DOT;
            result = t.getTokenValue();
            assertEquals(expResult, result);
        }catch(PascalException pe){}
    }

    /**
     * Test of getTokenSymbol method, of class SourceParser.
     */
    @Test
    public void testGetTokenSymbol() {
        System.out.println("getTokenSymbol");
        try{
            SourceParser instance = new SourceParser(source1);
            TokenValue expResult = TokenValue.PROGRAM;
            TokenValue result = instance.getTokenSymbol();
            assertEquals(expResult, result);
            Token t = instance.nextToken();
            assertNotNull(t);
            expResult = TokenValue.NAME;
            result = instance.getTokenSymbol();
            assertEquals(expResult, result);
            t = instance.nextToken();
            assertNotNull(t);
            expResult = TokenValue.SEMICOLON;
            result = instance.getTokenSymbol();
            assertEquals(expResult, result);
            t = instance.nextToken();
            assertNotNull(t);
            expResult = TokenValue.CONST;
            result = instance.getTokenSymbol();
            assertEquals(expResult, result);
        }catch(PascalException pe){}

    }

    /**
     * Test of getTokenMeaning method, of class SourceParser.
     */
    @Test
    public void testGetTokenMeaning() {
        System.out.println("getTokenMeaning");
        try{
            SourceParser instance = new SourceParser(source1);
            Token t = instance.nextToken();//src1
            assertNotNull(t);
            String expResult = "src1";
            String result = instance.getTokenMeaning();
            assertEquals(expResult, result);
            t = instance.nextToken();// ;
            assertNotNull(t);
            t = instance.nextToken();//const
            assertNotNull(t);
            t = instance.nextToken();//myConst
            assertNotNull(t);
            expResult = "myConst";
            result = instance.getTokenMeaning();
            assertEquals(expResult, result);
            t = instance.nextToken();//=
            assertNotNull(t);
            t = instance.nextToken();// 10
            assertNotNull(t);
            expResult = "10";
            result = instance.getTokenMeaning();
            assertEquals(expResult, result);
        }catch(PascalException pe){}
    }

    /**
     * Test of exception, of class SourceParser.
     */
    @Test(expected=PascalException.class)
    public void testException() throws PascalException{
        System.out.println("testException");
        SourceParser instance = new SourceParser(sourceFail);
        assertTrue(true);//normaly, never reach
    }

    /**
     * Test of exception, of class SourceParser.
     */
    @Test(expected=PascalException.class)
    public void testExceptionEmptySrc() throws PascalException{
        System.out.println("testException_empty_source");
        SourceParser instance = new SourceParser(sourceVide);
        assertTrue(true);//normaly, never reach
    }

}