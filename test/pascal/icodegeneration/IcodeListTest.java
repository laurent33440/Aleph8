/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pascal.icodegeneration;

import java.util.Iterator;
import java.util.Vector;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import pascal.icodegeneration.label.GenerateLabel;

/**
 *
 * @author laurent
 */
public class IcodeListTest {
    private IcodeList ilist;
    private AIcode ic1;
    private AIcode ic2;
    private AIcode ic3;
    private AIcode ic4;
    private String label = "label0";
    
    
    public IcodeListTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        this.ic1 = new LdaIcode("identifier");
        this.ic2 = new LdiIcode((byte)5);
        this.ic3 = new StoIcode();
        this.ic4 = new BzeIcode("label1");
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testAddSomeICode() {
        System.out.println("test adding icode");
        this.ilist = new IcodeList();
        this.ilist.add(ic1);
        this.ilist.add(ic2);
        this.ilist.add(ic3);
        this.ilist.add(ic4);
        Vector vr = new Vector<AIcode>();
        vr.add(ic1);vr.add(ic2);vr.add(ic3);vr.add(ic4);
        Iterator<AIcode> ir = vr.iterator();
        Iterator<AIcode> it = this.ilist.getIterator();
        assertNotNull("iterator",it);
        while(it.hasNext()){
            assertEquals("icodeList items ",ir.next(), it.next());
        }
        //print
        this.ilist.print();
    }
    
    @Test
    public void testAddICodeList() {
        System.out.println("test adding icode list");
        this.ilist = new IcodeList();
        this.ilist.add(ic1);
        this.ilist.add(ic2);
        this.ilist.add(ic3);
        IcodeList ilist2  = new IcodeList();
        ilist2.add(ic3);
        ilist2.add(ic2);
        ilist2.add(ic1);
        this.ilist.add(ilist2);
        Vector vr = new Vector<AIcode>();
        vr.add(ic1);vr.add(ic2);vr.add(ic3);vr.add(ic3);vr.add(ic2);vr.add(ic1);
        Iterator<AIcode> ir = vr.iterator();
        Iterator<AIcode> it = this.ilist.getIterator();
        assertNotNull("iterator",it);
        while(ir.hasNext()){
            assertEquals("icodeList items ",ir.next(), it.next());
        }
        //print
        this.ilist.print();
    }
    
    @Test
    public void testAddICodeWhithLabelToNextIcode() {
        System.out.println("test adding icode whith registered label to next icode");
        this.ilist = new IcodeList();
        this.ilist.add(ic1);
        this.ilist.add(ic2);
        this.ilist.add(ic3);
        this.ilist.registerLabelForNextIcode(label);
        this.ilist.add(ic4);
        Vector vr = new Vector<AIcode>();
        vr.add(ic1);vr.add(ic2);vr.add(ic3);vr.add(ic4);
        Iterator<AIcode> ir = vr.iterator();
        Iterator<AIcode> it = this.ilist.getIterator();
        assertNotNull("iterator",it);
        while(it.hasNext()){
            assertEquals("icodeList items ",ir.next(), it.next());
        }
        //print
        this.ilist.print();
    }
    
    @Test
    public void testAddICodeListWithLabelToNextIcode() {
        System.out.println("test adding icode list with label to next icode (first icode of added list)");
        this.ilist = new IcodeList();
        this.ilist.add(ic1);
        this.ilist.add(ic2);
        this.ilist.add(ic3);
        this.ilist.registerLabelForNextIcode(label);
        this.ilist.add(new BzeIcode("label99"));
        this.ilist.registerLabelForNextIcode("label_1stIcodeAdded");
        IcodeList ilist2  = new IcodeList();
        ilist2.add(ic4);
        ilist2.add(ic3);
        ilist2.add(ic2);
        ilist2.add(ic1);
        this.ilist.add(ilist2);
//        Vector vr = new Vector<AIcode>();
//        vr.add(ic1);vr.add(ic2);vr.add(ic3);vr.add(ic4);vr.add(ic4);vr.add(ic3);vr.add(ic2);vr.add(ic1);
//        Iterator<AIcode> ir = vr.iterator();
        Iterator<AIcode> it = this.ilist.getIterator();
        assertNotNull("iterator",it);
        this.ilist.print();
       
    }
    
    
}