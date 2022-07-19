/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pascal.icodegeneration.simulate;

import java.util.Iterator;
import java.util.Vector;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import pascal.icodegeneration.AIcode;
import pascal.icodegeneration.BraIcode;
import pascal.icodegeneration.BzeIcode;
import pascal.icodegeneration.EqlIcode;
import pascal.icodegeneration.LdaIcode;
import pascal.icodegeneration.LdiIcode;
import pascal.icodegeneration.LdrIcode;
import pascal.icodegeneration.StoIcode;
import pascal.symboltable.ConstanteSymbol;
import pascal.symboltable.ISymbolTable;
import pascal.symboltable.ProgramSymbol;
import pascal.symboltable.SymbolTable;
import pascal.symboltable.VariableSymbol;

/**
 *
 * @author laurent
 */
public class MemoryTest {
    Vector<AIcode> vr1;
    static ISymbolTable st;
    
    public MemoryTest() {
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
        vr1 = new Vector<AIcode>();
        AIcode ic1 = new LdaIcode("varName1");
        ic1.setAssociatedLabel("l_begin");
        vr1.add(ic1);
        vr1.add(new LdrIcode());
        vr1.add(new LdiIcode((byte)33));
        vr1.add(new EqlIcode());
        vr1.add(new BzeIcode("l_next"));
        vr1.add(new LdaIcode("varName2"));
        vr1.add(new LdiIcode((byte)1));
        vr1.add(new StoIcode());
        AIcode ic2 = new LdaIcode("constName1");
        ic2.setAssociatedLabel("l_next");
        vr1.add(ic2);
        vr1.add(new LdiIcode((byte)2));
        vr1.add(new StoIcode());
        vr1.add(new BraIcode("l_next"));
        
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of loadProg method, of class Memory.
     */
    @Test
    public void testLoadProg() {
        System.out.println("loadProg");
        Memory mem = new Memory();
        mem.loadProg(vr1);
        mem.printCode();
    }

    /**
     * Test of loadData method, of class Memory.
     */
    @Test
    public void testLoadData() {
        System.out.println("loadData");
        Memory mem = new Memory();
        mem.loadData(st.getAllSymbol());
        assertEquals("bad address!!",(byte)0,(byte)mem.getDataAdressFromName("constName1"));
        assertEquals("bad address!!",(byte)1,(byte)mem.getDataAdressFromName("constName2"));
        assertEquals("bad address!!",(byte)2,(byte)mem.getDataAdressFromName("varName1"));
        assertEquals("bad address!!",(byte)3,(byte)mem.getDataAdressFromName("varName2"));
        assertNull("Unattended data load!!",mem.getDataAdressFromName("unknown") );
        mem.printData();
    }
    
    /**
     * Test of getCurrentICode method, of class Memory.
     */
    @Test
    public void testGetCurrentICode() {
        System.out.println("getCurrentICode");
        Memory mem = new Memory();
        mem.loadProg(vr1);
        AIcode result = mem.getCurrentIcode();
        AIcode expResult = new LdaIcode("varName1");
        expResult.setAssociatedLabel("l_begin");
        assertEquals(expResult.toString(), result.toString());
    }

    /**
     * Test of getNextICode method, of class Memory.
     */
    @Test
    public void testGetNextICode() {
        System.out.println("getNextICode");
        Memory mem = new Memory();
        mem.loadProg(vr1);
        AIcode result = mem.getCurrentIcode();
        AIcode expResult = new LdaIcode("varName1");
        expResult.setAssociatedLabel("l_begin");
        assertEquals(expResult.toString(), result.toString());
        result = mem.getNextICode();
        expResult = new LdrIcode();
        assertEquals(expResult.toString(), result.toString());
    }

    /**
     * Test of jumpIteratorToKey method, of class Memory.
     */
    @Test
    public void testJumpIteratorToLabel() {
        System.out.println("jumpIteratorToLabel");
        Memory mem = new Memory();
        mem.loadProg(vr1);
        Iterator<Byte> it = mem.jumpIteratorToLabel("l_next");
        assertNotNull("iterator null!!",it);
        AIcode result = mem.getCurrentIcode();
        AIcode ic = new LdaIcode("constName1");
        ic.setAssociatedLabel("l_next");
        assertEquals(ic.toString(), result.toString());
        it = mem.jumpIteratorToLabel("l_begin");
        assertNotNull("iterator null!!",it);
        result = mem.getCurrentIcode();
        ic = new LdaIcode("varName1");
        ic.setAssociatedLabel("l_begin");
        assertEquals(ic.toString(), result.toString());
    }

    /**
     * Test of getDataAdressFromName method, of class Memory.
     */
    @Test
    public void testGetDataAdressFromName() {
        System.out.println("getDataAddressFromName");
        Memory mem = new Memory();
        mem.loadData(st.getAllSymbol());
        assertEquals("bad address!!",(byte)0,(byte)mem.getDataAdressFromName("constName1"));
        assertEquals("bad address!!",(byte)1,(byte)mem.getDataAdressFromName("constName2"));
        assertEquals("bad address!!",(byte)2,(byte)mem.getDataAdressFromName("varName1"));
        assertEquals("bad address!!",(byte)3,(byte)mem.getDataAdressFromName("varName2"));
        mem.printData();
    }

    /**
     * Test of getDataValueFromName method, of class Memory.
     */
    @Test
    public void testGetDataValueFromName() {
        System.out.println("getDataValueFromName");
        Memory mem = new Memory();
        mem.loadData(st.getAllSymbol());
        assertEquals("bad value!!",(byte)10,(byte)mem.getDataValueFromName("constName1"));
        assertEquals("bad value!!",(byte)20,(byte)mem.getDataValueFromName("constName2"));
        assertEquals("bad value!!",(byte)0,(byte)mem.getDataValueFromName("varName1"));
        assertEquals("bad value!!",(byte)0,(byte)mem.getDataValueFromName("varName2"));
        mem.printData();
    }

    /**
     * Test of getDataValueFromAddress method, of class Memory.
     */
    @Test
    public void testGetDataValueFromAddress() {
        System.out.println("getDataValueFromAddress");
        Memory mem = new Memory();
        mem.loadData(st.getAllSymbol());
        assertEquals("bad value!!",(byte)10,(byte)mem.getDataValueFromAddress((byte)0));
        assertEquals("bad value!!",(byte)20,(byte)mem.getDataValueFromAddress((byte)1));
        assertEquals("bad value!!",(byte)0,(byte)mem.getDataValueFromAddress((byte)2));
        assertEquals("bad value!!",(byte)0,(byte)mem.getDataValueFromAddress((byte)3));
        assertNull("unattended data!!",mem.getDataValueFromAddress((byte)4));
        mem.printData();
    }

    /**
     * Test of setDataValueFromAddress method, of class Memory.
     */
    @Test
    public void testSetDataValueFromAddress() {
        System.out.println("setDataValueFromAddress");
        Memory mem = new Memory();
        mem.loadData(st.getAllSymbol());
        assertEquals("bad value!!",(byte)0,(byte)mem.getDataValueFromAddress((byte)2));
        assertEquals("bad value!!",(byte)0,(byte)mem.getDataValueFromAddress((byte)3));
        mem.printData();
        mem.setDataValueFromAddress((byte)19, (byte)2);
        mem.setDataValueFromAddress((byte)67, (byte)3);
        assertEquals("bad value!!",(byte)19,(byte)mem.getDataValueFromAddress((byte)2));
        assertEquals("bad value!!",(byte)67,(byte)mem.getDataValueFromAddress((byte)3));
        mem.printData();
    }

    /**
     * Test of printData method, of class Memory.
     */
    @Test
    public void testPrintData() {
        System.out.println("printData");
        Memory mem = new Memory();
        mem.loadData(st.getAllSymbol());
        mem.printData();
    }
}