/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pascal.icodegeneration.label;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import pascal.PascalException;
import pascal.parser.ISourceParser;
import pascal.parser.SourceParser;

/**
 *
 * @author laurent
 */
public class GenerateLabelTest {
    
    final private String srcIf = "program srcIf; \nconst\n\t myConst =10 ; \nvar\n\t myVar : byte;\n  begin \n \t if myVar = myConst then myVar := 1 ; \nend.";
    final private String srcWhile = "program srcIf; \nconst\n\t myConst =10 ; \nvar\n\t myVar : byte;\n  begin \n \t while myVar = myConst do myVar := myVar + 1 ; \nend.";
    private ISourceParser sp;
    
    public GenerateLabelTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testIfLabelCreation() throws PascalException{
        System.out.println("test if label generation");
        this.sp = new SourceParser(srcIf);
        assertEquals("labels differs","l0_if_8",GenerateLabel.getIfLabel());
    }
    
    @Test
    public void testWhileLabelCreation() throws PascalException{
        System.out.println("test while label generation");
        this.sp = new SourceParser(srcWhile);
        assertEquals("labels differs","l1_while_8",GenerateLabel.getWhileLabel());
    }
}