/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pascal.srcInstructions;

import java.util.Iterator;
import java.util.Vector;
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
import pascal.icodegeneration.AddIcode;
import pascal.icodegeneration.BzeIcode;
import pascal.icodegeneration.EqlIcode;
import pascal.icodegeneration.IcodeList;
import pascal.icodegeneration.LdaIcode;
import pascal.icodegeneration.LdiIcode;
import pascal.icodegeneration.LdrIcode;
import pascal.icodegeneration.StoIcode;
import pascal.parser.ISourceParser;
import pascal.parser.SourceParser;

/**
 *
 * @author laurent
 */
public class IfInstructionTest {
    private ISourceParser sp;
    Vector<AIcode> vr1;
    Vector<AIcode> vr2;
    Vector<AIcode> vr3;
    
     final private String srcIf1 = "program srcIf; \nconst\n\t myConst =10 ; \nvar\n\t myVar : byte;\n  begin \n \t if myVar = 33 then myVar := 1 ; \n myVar := 2; \nend.";
     final private String srcIf2 = "program srcIf; \nconst\n\t myConst =10 ; \nvar\n\t myVar : byte;\n  begin \n \t if myVar <> myConst then myVar := 1 ; \n myVar := 2; \nend.";
     final private String srcIf3 = "program srcIf; \nconst\n\t myConst =10 ; \nvar\n\t myVar : byte;\n  begin \n \t if myVar = myConst then \n if myVar <> 2 then myVar := 1 ; \n myVar := 2; \nend.";
     
    public IfInstructionTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        vr1 = new Vector<AIcode>();
        vr1.add(new LdaIcode("myVar"));
        vr1.add(new LdrIcode());
        vr1.add(new LdiIcode((byte)33));
        vr1.add(new EqlIcode());
        vr1.add(new BzeIcode("l_if_9"));
        vr1.add(new LdaIcode("myVar"));
        vr1.add(new LdiIcode((byte)1));
        vr1.add(new StoIcode());
        vr1.add(new LdaIcode("myVar"));
        vr1.add(new LdiIcode((byte)2));
        vr1.add(new StoIcode());
        //
        vr2 = new Vector<AIcode>();
        vr2.add(new LdaIcode("myVar"));
        vr2.add(new LdaIcode("myConst"));
        vr2.add(new LdrIcode());
        vr2.add(new StoIcode());
        vr3 = new Vector<AIcode>();
        vr3.add(new LdaIcode("myVar"));
        vr3.add(new LdiIcode((byte)19));
        vr3.add(new LdiIcode((byte)67));
        vr3.add(new AddIcode());
        vr3.add(new StoIcode());
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of registerWardsInstruction method, of class IfInstruction.
     */
    @Test @Ignore
    public void testRegisterWardsInstruction() {
        System.out.println("registerWardsInstruction");
        ISrcInstruction inst = null;
        IfInstruction instance = null;
        instance.registerWardsInstruction(inst);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getIcodeList method, of class IfInstruction.
     */
    @Test
    public void testIcodeList1() throws PascalException{
        System.out.println("getIcodeList : if1");
        this.sp = new SourceParser(srcIf1);
        FrontAnalyse fa = new FrontAnalyse(sp);
        SrcInstructionsList sil = fa.getSourceInstructionsList();
        IcodeList iCodeList = sil.generateIcode();
        assertNotNull("icode list is null!",iCodeList);
        Iterator<AIcode> iti = iCodeList.getIterator();
        assertNotNull("iterator list is null!",iti);
        Iterator<AIcode> itr = vr1.iterator();
//        while(itr.hasNext()){
//            AIcode icode = iti.next();
////            assertEquals(itr.next().print(),icode.print());
//            if(!icode.getAssociatedLabel().equals("")){
//                //assertEquals("label not correct ! ", "BZE l_if_9", icode.print());
//                System.out.println("label found for : "+icode.print());
//            }
//        }
        iCodeList.print();
    }
    
    @Test
    public void testIcodeList2() throws PascalException{
        System.out.println("getIcodeList : if2");
        this.sp = new SourceParser(srcIf2);
        FrontAnalyse fa = new FrontAnalyse(sp);
        SrcInstructionsList sil = fa.getSourceInstructionsList();
        IcodeList iCodeList = sil.generateIcode();
        assertNotNull("icode list is null!",iCodeList);
        Iterator<AIcode> iti = iCodeList.getIterator();
        assertNotNull("iterator list is null!",iti);
//        Iterator<AIcode> itr = vr1.iterator();
        iCodeList.print();
    }
    
    @Test
    public void testIcodeList3() throws PascalException{
        System.out.println("getIcodeList : if3");
        this.sp = new SourceParser(srcIf3);
        FrontAnalyse fa = new FrontAnalyse(sp);
        SrcInstructionsList sil = fa.getSourceInstructionsList();
        IcodeList iCodeList = sil.generateIcode();
        assertNotNull("icode list is null!",iCodeList);
        Iterator<AIcode> iti = iCodeList.getIterator();
        assertNotNull("iterator list is null!",iti);
        iCodeList.print();
    }
    
}