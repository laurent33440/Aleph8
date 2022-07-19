/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pascal.frontanalyse.expressionstack;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author prog
 */
public class ExpressionStackTest {

    public ExpressionStackTest() {
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
     * Test of pushSymbol method, of class ExpressionStack.
     */
    @Test
    public void testPushPopSymbol() {
        System.out.println("Test pushPopSymbol");
//        IExpressionList instance = new ExpressionStack();//LIFO
        IExpressionList instance = new ExpressionQueue();//FIFO
        AExpressionSymbol elt1 = new OperationSymbol(AExpressionSymbol.Operator.PLUS);
        AExpressionSymbol elt2 = new ValueSymbol(3);
        AExpressionSymbol elt3 = new ValueSymbol(2);
        instance.pushSymbol(elt3);
        instance.pushSymbol(elt2);
        instance.pushSymbol(elt1);
        instance.print();
        AExpressionSymbol result = instance.popSymbol();
        assertEquals(elt3, result);
        result = instance.popSymbol();
        assertEquals(elt2, result);
        result = instance.popSymbol();
        assertEquals(elt1, result);
    }
    
    /**
     * Test of isEmpty method, of class ExpressionStack.
     */
    @Test
    public void testIsEmpty() {
        System.out.println("Test isEmpty method");
        IExpressionList instance = new ExpressionStack();
        assertTrue("empty expression stack",instance.isEmpty());
    }

}