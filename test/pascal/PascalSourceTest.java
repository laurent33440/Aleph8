/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pascal;

import pascal.parser.PascalSource;
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
public class PascalSourceTest {
    private String source;

    public PascalSourceTest() {
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
     * Test of getCurrentLineNumber method, of class PascalSource.
     */
    @Test
    public void testGetCurrentLineNumberEmptySrc() {
        System.out.println("getCurrentLineNumberEmptySrc");
        this.source = new String("");
        PascalSource instance = new PascalSource(source);;
        Integer expResult = 1;
        Integer result = instance.getCurrentLineNumber();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetCurrentLineNumber() {
        System.out.println("getCurrentLineNumber");
        this.source = new String("first\nsecond");
        PascalSource instance = new PascalSource(source);
        Integer expResult = 1;
        Integer result = instance.getCurrentLineNumber();
        assertEquals(expResult, result);
        String line = instance.getLine();
        expResult = 2;
        result = instance.getCurrentLineNumber();
        assertEquals(expResult, result);
    }

    /**
     * Test of isEndSource method, of class PascalSource.
     */
    @Test
    public void testIsEndSource() {
        System.out.println("isEndSource");
        this.source = new String("first\nsecond\nthird");
        PascalSource instance = new PascalSource(source);
        String line = instance.getLine();
        line = instance.getLine();
        boolean expResult = false;
        boolean result = instance.isEndSource();
        assertEquals(expResult, result);
        line = instance.getLine();
        expResult = true;
        result = instance.isEndSource();
        assertEquals(expResult, result);
    }

    /**
     * Test of isSourceEmpty method, of class PascalSource.
     */
    @Test
    public void testIsSourceEmpty() {
        System.out.println("isEmpty");
        this.source = new String("first\nsecond");
        PascalSource instance = new PascalSource(source);
        boolean expResult = false;
        boolean result = instance.isSourceEmpty();
        assertEquals(expResult, result);
    }

    /**
     * Test of getLine method, of class PascalSource.
     */
    @Test
    public void testgetLine() {
        System.out.println("getLine");
        this.source = new String("first line\nsecond line");
        PascalSource instance = new PascalSource(source);
        String expResult = "first line";
        String result = instance.getLine();
        assertEquals(expResult, result);
    }

    /**
     * Test of multiple getLine method, of class PascalSource.
     */
    @Test
    public void testgetLines() {
        System.out.println("getLines");
        this.source = new String("first line\nsecond line\nthird line");
        PascalSource instance = new PascalSource(source);
        String expResult = "first line";
        String result = instance.getLine();
        assertEquals(expResult, result);
        expResult = "second line";
        result = instance.getLine();
        assertEquals(expResult, result);
        expResult = "third line";
        result = instance.getLine();
        assertEquals(expResult, result);
        assertTrue(instance.isEndSource());
    }


}