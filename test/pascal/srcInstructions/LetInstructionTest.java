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
import pascal.PascalException;
import pascal.frontanalyse.FrontAnalyse;
import pascal.icodegeneration.AddIcode;
import pascal.icodegeneration.AIcode;
import pascal.icodegeneration.IcodeList;
import pascal.icodegeneration.LdaIcode;
import pascal.icodegeneration.LdiIcode;
import pascal.icodegeneration.LdrIcode;
import pascal.icodegeneration.StoIcode;
import pascal.icodegeneration.simulate.SimulateIcode;
import pascal.parser.ISourceParser;
import pascal.parser.SourceParser;
import pascal.symboltable.SymbolTable;

/**
 *
 * @author laurent
 */
public class LetInstructionTest {
    private ISourceParser sp;
    Vector<AIcode> vr1;
    Vector<AIcode> vr2;
    Vector<AIcode> vr3;
    
    final private String srcLet1 = "program srcLet1; \nconst\n\t myConst =10 ; \nvar\n\t myVar : byte;\n  begin \n \t myVar := 15 ; \nend.";
    final private String srcLet2 = "program srcLet2; \nconst\n\t myConst =10 ; \nvar\n\t myVar : byte;\n  begin \n \t myVar := myConst ; \nend.";
    final private String srcLet3 = "program srcLet3; \nconst\n\t myConst =10 ; \nvar\n\t myVar : byte;\n  begin \n \t myVar := 19+67 ; \nend.";
//    final private String srcLetKo1 = "program srcLetKo1; \nconst\n\t myConst =10 ; \nvar\n\t myVar : byte;\n  begin \n \t unKnownVar := myConst ; \nend.";
//    final private String srcLetKo2 = "program srcLetKo2; \nconst\n\t myConst =10 ; \nvar\n\t myVar : byte;\n  begin \n \t unKnownVar :  myConst ; \nend.";
    
    public LetInstructionTest() {
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
        vr1.add(new LdiIcode((byte)15));
        vr1.add(new StoIcode());
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

    @Test
    public void testLetInstructionCreation() throws PascalException{
        System.out.println("test_letInstruction  id := value");
        this.sp = new SourceParser(srcLet1);
        FrontAnalyse fa = new FrontAnalyse(sp);
        SrcInstructionsList sil = fa.getSourceInstructionsList();
        Iterator<ISrcInstruction> it = sil.getIterator();
        assertTrue(" let instruction in list ", it.hasNext());
        ISrcInstruction let = it.next();
        assertTrue(" let instruction instance ", let instanceof LetInstruction);
        IcodeList iCodeList = sil.generateIcode();
        assertNotNull("icode list is null!",iCodeList);
        Iterator<AIcode> iti = iCodeList.getIterator();
        assertNotNull("iterator list is null!",iti);
        Iterator<AIcode> itr = vr1.iterator();
        while(itr.hasNext()){
            assertEquals(itr.next().toString(),iti.next().toString());
        }
        iCodeList.print();
        new SimulateIcode(iCodeList.getIcodeList(), SymbolTable.getInstance(), true);
    }
    
    @Test
    public void testLetInstructionName() throws PascalException{
        System.out.println("test_letInstruction  id := name");
        this.sp = new SourceParser(srcLet2);
        FrontAnalyse fa = new FrontAnalyse(sp);
        SrcInstructionsList sil = fa.getSourceInstructionsList();
        Iterator<ISrcInstruction> it = sil.getIterator();
        assertTrue(" let instruction in list ", it.hasNext());
        ISrcInstruction let = it.next();
        assertTrue(" let instruction instance ", let instanceof LetInstruction);
        IcodeList iCodeList = sil.generateIcode();
        assertNotNull("icode list is null!",iCodeList);
        Iterator<AIcode> iti = iCodeList.getIterator();
        assertNotNull("iterator list is null!",iti);
        Iterator<AIcode> itr = vr2.iterator();
        while(itr.hasNext()){
            assertEquals(itr.next().toString(),iti.next().toString());
        }
        iCodeList.print();
    }
    
    @Test
    public void testLetInstructionAddValue() throws PascalException{
        System.out.println("test_letInstruction  id := val1 + val2");
        this.sp = new SourceParser(srcLet3);
        FrontAnalyse fa = new FrontAnalyse(sp);
        SrcInstructionsList sil = fa.getSourceInstructionsList();
        Iterator<ISrcInstruction> it = sil.getIterator();
        assertTrue(" let instruction in list ", it.hasNext());
        ISrcInstruction let = it.next();
        assertTrue(" let instruction instance ", let instanceof LetInstruction);
        IcodeList iCodeList = sil.generateIcode();
        assertNotNull("icode list is null!",iCodeList);
        Iterator<AIcode> iti = iCodeList.getIterator();
        assertNotNull("iterator list is null!",iti);
        Iterator<AIcode> itr = vr3.iterator();
        while(itr.hasNext()){
            assertEquals(itr.next().toString(),iti.next().toString());
        }
        iCodeList.print();
    }
    
    
}