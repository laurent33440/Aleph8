/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pascal.icodegeneration.simulate;

import java.util.Vector;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;
import pascal.icodegeneration.AIcode;
import pascal.icodegeneration.AddIcode;
import pascal.icodegeneration.BraIcode;
import pascal.icodegeneration.BzeIcode;
import pascal.icodegeneration.EqlIcode;
import pascal.icodegeneration.HltIcode;
import pascal.icodegeneration.LdaIcode;
import pascal.icodegeneration.LdiIcode;
import pascal.icodegeneration.LdrIcode;
import pascal.icodegeneration.NeqIcode;
import pascal.icodegeneration.StoIcode;
import pascal.icodegeneration.SubIcode;
import pascal.symboltable.ConstanteSymbol;
import pascal.symboltable.ISymbolTable;
import pascal.symboltable.ProgramSymbol;
import pascal.symboltable.SymbolTable;
import pascal.symboltable.VariableSymbol;

/**
 *
 * @author prog
 */
public class StackProcessorTest {
    Vector<AIcode> vr1;
    Vector<AIcode> vrTst=new Vector<AIcode>();
    static ISymbolTable st;
    
    public StackProcessorTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        st = SymbolTable.getInstance();
        st.addPascalSymbol(new ProgramSymbol("test_prg_name"));
        st.addPascalSymbol(new ConstanteSymbol("constName1", "10"));
        st.addPascalSymbol(new ConstanteSymbol("constName2", "20"));
        st.addPascalSymbol(new VariableSymbol("varName1","byte"));
        st.addPascalSymbol(new VariableSymbol("varName2","byte"));
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        //some complex
        vr1 = new Vector<AIcode>();
        vr1.add(new LdaIcode("constName2"));
        vr1.add(new LdrIcode());
        vr1.add(new LdiIcode((byte)33));
        vr1.add(new EqlIcode());
        vr1.add(new BzeIcode("l1_if_9"));
        vr1.add(new LdaIcode("varName2"));
        vr1.add(new LdiIcode((byte)1));
        vr1.add(new StoIcode());
        AIcode ic = new LdaIcode("varName1");
        ic.setAssociatedLabel("l1_if_9");
        vr1.add(ic);
        vr1.add(new LdiIcode((byte)2));
        vr1.add(new StoIcode());
        vr1.add(new HltIcode());
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of execIcode method, of class StackProcessor.
     * Load /store 
     */
    @Test
    public void testExecIcodeLDI() {
        System.out.println("execIcode -- LDI");
        Memory mem = new Memory();
        vrTst.add(new LdiIcode((byte)0xaa));//-86
        vrTst.add(new HltIcode());
        mem.loadProg(vrTst);
        mem.loadData(st.getAllSymbol());
        StackProcessor sproc = new StackProcessor();
        sproc.attachMem(mem);
        AIcode icode = mem.getCurrentIcode();
        String icodeName = "";
        while(icode!=null&&!icodeName.equals("HltIcode")){
            icodeName = icode.getClass().getSimpleName();
            System.out.println("IcodeName : "+icodeName);
            sproc.execIcode(icode);
            sproc.print();
            icode = mem.getCurrentIcode();
        }
    }
    
    @Test
    public void testExecIcodeLDA() {
        System.out.println("execIcode -- LDA");
        Memory mem = new Memory();
        vrTst.clear();
        vrTst.add(new LdaIcode("varName1"));
        vrTst.add(new HltIcode());
        mem.loadProg(vrTst);
        mem.loadData(st.getAllSymbol());
        StackProcessor sproc = new StackProcessor();
        sproc.attachMem(mem);
        AIcode icode = mem.getCurrentIcode();
        String icodeName = "";
        while(icode!=null&&!icodeName.equals("HltIcode")){
            icodeName = icode.getClass().getSimpleName();
            System.out.println("IcodeName : "+icodeName);
            sproc.execIcode(icode);
            sproc.print();
            icode = mem.getCurrentIcode();
        }//proc stack contains 4 (2icodes+2csts) @ of varName1
    }
    
    @Test
    public void testExecIcodeLDR() {
        System.out.println("execIcode -- LDR");
        Memory mem = new Memory();
        vrTst.clear();
        vrTst.add(new LdaIcode("constName1"));
        vrTst.add(new LdrIcode());
        vrTst.add(new HltIcode());
        mem.loadProg(vrTst);
        mem.loadData(st.getAllSymbol());
        StackProcessor sproc = new StackProcessor();
        sproc.attachMem(mem);
        AIcode icode = mem.getCurrentIcode();
        String icodeName = "";
        while(icode!=null&&!icodeName.equals("HltIcode")){
            icodeName = icode.getClass().getSimpleName();
            System.out.println("IcodeName : "+icodeName);
            sproc.execIcode(icode);
            sproc.print();
            icode = mem.getCurrentIcode();
        }//proc stack contains 10 (constname1 value)
    }
    
    @Test
    public void testExecIcodeSTO() {
        System.out.println("execIcode -- STO");
        Memory mem = new Memory();
        vrTst.clear();
        vrTst.add(new LdaIcode("varName1"));
        vrTst.add(new LdrIcode());
        vrTst.add(new LdaIcode("varName1"));
        vrTst.add(new LdiIcode((byte)33));
        vrTst.add(new StoIcode());
        vrTst.add(new LdaIcode("varName1"));
        vrTst.add(new LdrIcode());
        vrTst.add(new HltIcode());
        mem.loadProg(vrTst);
        mem.loadData(st.getAllSymbol());
        StackProcessor sproc = new StackProcessor();
        sproc.attachMem(mem);
        AIcode icode = mem.getCurrentIcode();
        String icodeName = "";
        while(icode!=null&&!icodeName.equals("HltIcode")){
            icodeName = icode.getClass().getSimpleName();
            System.out.println("IcodeName : "+icodeName);
            sproc.execIcode(icode);
            sproc.print();
            icode = mem.getCurrentIcode();
        }//top proc stack contains 33 (varName1 value)
    }
    
    
    /**
     * Test of execIcode method, of class StackProcessor.
     *  Arithmetics & tests 
     */
    @Test
    public void testExecIcodeADD() {
        System.out.println("execIcode -- ADD");
        Memory mem = new Memory();
        vrTst.clear();
        vrTst.add(new LdiIcode((byte)33));
        vrTst.add(new LdiIcode((byte)33));
        vrTst.add(new AddIcode());
        vrTst.add(new HltIcode());
        mem.loadProg(vrTst);
        mem.loadData(st.getAllSymbol());
        StackProcessor sproc = new StackProcessor();
        sproc.attachMem(mem);
        AIcode icode = mem.getCurrentIcode();
        String icodeName = "";
        while(icode!=null&&!icodeName.equals("HltIcode")){
            icodeName = icode.getClass().getSimpleName();
            System.out.println("IcodeName : "+icodeName);
            sproc.execIcode(icode);
            sproc.print();
            icode = mem.getCurrentIcode();
        }//top proc stack contains 66 (varName1 value)
    }
    
    @Test
    public void testExecIcodeSUB() {
        System.out.println("execIcode -- SUB");
        Memory mem = new Memory();
        vrTst.clear();
        vrTst.add(new LdiIcode((byte)33));
        vrTst.add(new LdiIcode((byte)20));
        vrTst.add(new SubIcode());
        vrTst.add(new HltIcode());
        mem.loadProg(vrTst);
        mem.loadData(st.getAllSymbol());
        StackProcessor sproc = new StackProcessor();
        sproc.attachMem(mem);
        AIcode icode = mem.getCurrentIcode();
        String icodeName = "";
        while(icode!=null&&!icodeName.equals("HltIcode")){
            icodeName = icode.getClass().getSimpleName();
            System.out.println("IcodeName : "+icodeName);
            sproc.execIcode(icode);
            sproc.print();
            icode = mem.getCurrentIcode();
        }//top proc stack contains -13 (varName1 value)
    }
    
    @Test
    public void testExecIcodeEQL() {
        System.out.println("execIcode -- EQL");
        Memory mem = new Memory();
        vrTst.clear();
        vrTst.add(new LdiIcode((byte)33));
        vrTst.add(new LdiIcode((byte)33));
        vrTst.add(new EqlIcode());
        vrTst.add(new LdiIcode((byte)66));
        vrTst.add(new LdiIcode((byte)33));
        vrTst.add(new EqlIcode());
        vrTst.add(new HltIcode());
        mem.loadProg(vrTst);
        mem.loadData(st.getAllSymbol());
        StackProcessor sproc = new StackProcessor();
        sproc.attachMem(mem);
        AIcode icode = mem.getCurrentIcode();
        String icodeName = "";
        while(icode!=null&&!icodeName.equals("HltIcode")){
            icodeName = icode.getClass().getSimpleName();
            System.out.println("IcodeName : "+icodeName);
            sproc.execIcode(icode);
            sproc.print();
            icode = mem.getCurrentIcode();
        }// proc stack contains 0 33 66 1 33 33 
    }
    
    @Test
    public void testExecIcodeNEQ() {
        System.out.println("execIcode -- NEQ");
        Memory mem = new Memory();
        vrTst.clear();
        vrTst.add(new LdiIcode((byte)33));
        vrTst.add(new LdiIcode((byte)33));
        vrTst.add(new NeqIcode());
        vrTst.add(new LdiIcode((byte)66));
        vrTst.add(new LdiIcode((byte)33));
        vrTst.add(new NeqIcode());
        vrTst.add(new HltIcode());
        mem.loadProg(vrTst);
        mem.loadData(st.getAllSymbol());
        StackProcessor sproc = new StackProcessor();
        sproc.attachMem(mem);
        AIcode icode = mem.getCurrentIcode();
        String icodeName = "";
        while(icode!=null&&!icodeName.equals("HltIcode")){
            icodeName = icode.getClass().getSimpleName();
            System.out.println("IcodeName : "+icodeName);
            sproc.execIcode(icode);
            sproc.print();
            icode = mem.getCurrentIcode();
        }// proc stack contains 1 33 66 0 33 33 
    }
    
    /**
     * Test of execIcode method, of class StackProcessor.
     *  Branch
     */
    @Test
    public void testExecIcodeBRA() {
        System.out.println("execIcode -- BRA");
        Memory mem = new Memory();
        vrTst.clear();
        vrTst.add(new LdiIcode((byte)1));
        vrTst.add(new LdiIcode((byte)2));
        vrTst.add(new BraIcode("l_always"));
        vrTst.add(new LdiIcode((byte)3));
        vrTst.add(new LdiIcode((byte)4));
        AIcode c = new LdiIcode((byte)5);
        c.setAssociatedLabel("l_always");
        vrTst.add(c);
        vrTst.add(new LdiIcode((byte)6));
        vrTst.add(new LdiIcode((byte)7));
        vrTst.add(new HltIcode());
        mem.loadProg(vrTst);
        mem.loadData(st.getAllSymbol());
        StackProcessor sproc = new StackProcessor();
        sproc.attachMem(mem);
        AIcode icode = mem.getCurrentIcode();
        String icodeName = "";
        while(icode!=null&&!icodeName.equals("HltIcode")){
            icodeName = icode.getClass().getSimpleName();
            System.out.println("IcodeName : "+icodeName);
            sproc.execIcode(icode);
            sproc.print();
            icode = mem.getCurrentIcode();
        }// proc stack contains 7 6 2 1 
    }
    
    @Test
    public void testExecIcodeBZE() {
        System.out.println("execIcode -- BZE");
        Memory mem = new Memory();
        vrTst.clear();
        vrTst.add(new LdiIcode((byte)1));
        vrTst.add(new LdiIcode((byte)2));
        vrTst.add(new EqlIcode());
        vrTst.add(new BzeIcode("l_always"));
        vrTst.add(new LdiIcode((byte)3));
        vrTst.add(new LdiIcode((byte)4));
        AIcode c1 = new LdiIcode((byte)5);
        c1.setAssociatedLabel("l_always");
        vrTst.add(c1);
        vrTst.add(new LdiIcode((byte)6));
        vrTst.add(new LdiIcode((byte)7));
        vrTst.add(new NeqIcode());
        vrTst.add(new BzeIcode("l_never"));
        AIcode c2 = new LdiIcode((byte)8);
        c2.setAssociatedLabel("l_never");
        vrTst.add(c2);
        vrTst.add(new LdiIcode((byte)9));
        vrTst.add(new LdiIcode((byte)10));
        vrTst.add(new HltIcode());
        mem.loadProg(vrTst);
        mem.loadData(st.getAllSymbol());
        StackProcessor sproc = new StackProcessor();
        sproc.attachMem(mem);
        AIcode icode = mem.getCurrentIcode();
        String icodeName = "";
        while(icode!=null&&!icodeName.equals("HltIcode")){
            icodeName = icode.getClass().getSimpleName();
            System.out.println("IcodeName : "+icodeName);
            sproc.execIcode(icode);
            sproc.print();
            icode = mem.getCurrentIcode();
        }// proc stack contains 10 9 8 7 6 5 2 1 
    }
    
    
    /**
     * Test of execIcode method, of class StackProcessor.
     */
    @Test
    public void testExecIcodeComplex() {
        System.out.println("execIcode -- Complex");
        Memory mem = new Memory();
        mem.loadProg(vr1);
        mem.loadData(st.getAllSymbol());
        StackProcessor sproc = new StackProcessor();
        sproc.attachMem(mem);
        AIcode icode = mem.getCurrentIcode();
        String icodeName = "";
        while(icode!=null&&!icodeName.equals("HltIcode")){
            icodeName = icode.getClass().getSimpleName();
            System.out.println("IcodeName : "+icodeName);
            sproc.execIcode(icode);
            sproc.print();
            icode = mem.getNextICode();
        }
    }
    
    /**
     * Test of execProg method, of class StackProcessor.
     */
    @Test
    public void testExecProgTraceComplex() {
        System.out.println("execProg with trace -- Complex");
        Memory mem = new Memory();
        mem.loadProg(vr1);
        mem.loadData(st.getAllSymbol());
        StackProcessor sproc = new StackProcessor();
        sproc.attachMem(mem);
        sproc.execProg(true);
    }
    
    /**
     * Test of execProg method, of class StackProcessor.
     */
    @Test
    public void testExecProgComplex() {
        System.out.println("execProg without trace -- Complex");
        Memory mem = new Memory();
        mem.loadProg(vr1);
        mem.loadData(st.getAllSymbol());
        StackProcessor sproc = new StackProcessor();
        sproc.attachMem(mem);
        sproc.execProg(false);
    }
    

    /**
     * Test of print method, of class StackProcessor.
     */
    @Test @Ignore
    public void testPrint() {
        System.out.println("print");
        StackProcessor instance = new StackProcessor();
        instance.print();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}