/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pascal.frontanalyse.expressionstack;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author prog
 */
public class ISymbolVisitorTest {
    private IExpressionList es;
    private VisitValueSymbol  v;

    public ISymbolVisitorTest() {
        this.es = new ExpressionStack();
        this.v = new VisitValueSymbol();
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

    @Test
    public void testGetValue_visitValueSymbol() {
        System.out.println("testGetValue_visitValueSymbol");
        //1st method
        es.pushSymbol(new ValueSymbol(5));
        AExpressionSymbol vs = es.peekSymbol();
        vs.accept(v);
        assertEquals(5,v.getValue());
        //2nd 
        es.accept(v); //inspect top of stack
        assertEquals(5,v.getValue());
    }

    @Test
    public void testIsNul_visitValueSymbol() {
        System.out.println("testIsNul_visitValueSymbol");
        //1st method
        es.pushSymbol(new ValueSymbol(0));
        AExpressionSymbol vs = es.peekSymbol();
        vs.accept(v);
        assertTrue(v.isNul());
        es.pushSymbol(new ValueSymbol(10));
        vs = es.peekSymbol();
        vs.accept(v);
        assertFalse(v.isNul());
        //2nd
        es.accept(v);
        assertFalse(v.isNul());
        es.popSymbol();
        es.accept(v);
        assertTrue(v.isNul());
    }

}