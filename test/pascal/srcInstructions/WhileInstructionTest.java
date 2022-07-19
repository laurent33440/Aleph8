/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pascal.srcInstructions;

import java.util.Iterator;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;
import pascal.PascalException;
import pascal.frontanalyse.FrontAnalyse;
import pascal.icodegeneration.AIcode;
import pascal.icodegeneration.IcodeList;
import pascal.icodegeneration.simulate.SimulateIcode;
import pascal.parser.ISourceParser;
import pascal.parser.SourceParser;
import pascal.symboltable.SymbolTable;

/**
 *
 * @author laurent
 */
public class WhileInstructionTest {
    private ISourceParser sp;
    final private String srcWhile1 = "program srcWhile1; \nconst\n\t myConst =10 ; \nvar\n\t myVar : byte;\n  begin \n \t while myVar = myConst do myVar := myVar + 1 ;\n myVar := myConst ;\nend.";
    final private String srcWhile2 = "program srcWhile1; \nconst\n\t myConst =10 ; \nvar\n\t myVar : byte;\n  begin \n \t while myVar = myConst do while myVar <> myConst do myVar := myVar + 1 ;\n myVar := myConst ;\nend.";
    final private String srcWhile3 = "program srcWhile1; \nconst\n\t myConst =10 ; \nvar\n\t myVar : byte;\n  begin \n \t while myVar = myConst do if myVar <> 10 then myVar := myVar + 1 ;\n myVar := myConst ;\nend.";
    
    public WhileInstructionTest() {
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

    /**
     * Test of registerWardsInstruction method, of class WhileInstruction.
     */
    @Test @Ignore
    public void testRegisterWardsInstruction() {
        System.out.println("registerWardsInstruction");
        ISrcInstruction inst = null;
        WhileInstruction instance = null;
        instance.registerWardsInstruction(inst);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getIcodeList method, of class WhileInstruction.
     */
    @Test @Ignore
    public void testGetIcodeList() {
        System.out.println("getIcodeList");
        WhileInstruction instance = null;
        IcodeList expResult = null;
        IcodeList result = instance.getIcodeList();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
     @Test
    public void testIcodeList1() throws PascalException{
        System.out.println("getIcodeList : while1");
        this.sp = new SourceParser(srcWhile1);
        FrontAnalyse fa = new FrontAnalyse(sp);
        SrcInstructionsList sil = fa.getSourceInstructionsList();
        IcodeList iCodeList = sil.generateIcode();
        assertNotNull("icode list is null!",iCodeList);
        Iterator<AIcode> iti = iCodeList.getIterator();
        assertNotNull("iterator list is null!",iti);
        iCodeList.print();
    
     }
     
      @Test
    public void testIcodeList2() throws PascalException{
        System.out.println("getIcodeList : while2");
        this.sp = new SourceParser(srcWhile2);
        FrontAnalyse fa = new FrontAnalyse(sp);
        SrcInstructionsList sil = fa.getSourceInstructionsList();
        IcodeList iCodeList = sil.generateIcode();
        assertNotNull("icode list is null!",iCodeList);
        Iterator<AIcode> iti = iCodeList.getIterator();
        assertNotNull("iterator list is null!",iti);
        iCodeList.print();
    }
      
        @Test
    public void testIcodeList3() throws PascalException{
        System.out.println("getIcodeList : while3");
        this.sp = new SourceParser(srcWhile3);
        FrontAnalyse fa = new FrontAnalyse(sp);
        SrcInstructionsList sil = fa.getSourceInstructionsList();
        IcodeList iCodeList = sil.generateIcode();
        assertNotNull("icode list is null!",iCodeList);
        Iterator<AIcode> iti = iCodeList.getIterator();
        assertNotNull("iterator list is null!",iti);
        iCodeList.print();
        new SimulateIcode(iCodeList.getIcodeList(), SymbolTable.getInstance(), true);
    }
     
     
}