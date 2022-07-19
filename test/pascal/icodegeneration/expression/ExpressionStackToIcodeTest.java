/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pascal.icodegeneration.expression;

import java.util.Iterator;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import pascal.frontanalyse.expressionstack.AExpressionSymbol;
import pascal.frontanalyse.expressionstack.ExpressionStack;
import pascal.frontanalyse.expressionstack.IExpressionList;
import pascal.frontanalyse.expressionstack.NameSymbol;
import pascal.frontanalyse.expressionstack.OperationSymbol;
import pascal.frontanalyse.expressionstack.ValueSymbol;
import pascal.icodegeneration.AddIcode;
import pascal.icodegeneration.AIcode;
import pascal.icodegeneration.IcodeList;
import pascal.icodegeneration.LdaIcode;
import pascal.icodegeneration.LdiIcode;
import pascal.icodegeneration.LdrIcode;
import pascal.icodegeneration.SubIcode;

/**
 *
 * @author laurent
 */
public class ExpressionStackToIcodeTest {
    private IExpressionList es1;
    private IExpressionList es2;
    private IExpressionList es3;
    private IExpressionList es4;
    private IExpressionList es5;
    private IExpressionList es6;
    private IExpressionList es7;
    private ExpressionStackToIcode esti;
    private IcodeList iclRef;
    
    
    public ExpressionStackToIcodeTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        this.es1 = new ExpressionStack();
        this.es2 = new ExpressionStack();
        this.es3 = new ExpressionStack();
        this.es4 = new ExpressionStack();
        this.es5 = new ExpressionStack();
        this.es6 = new ExpressionStack();
        this.es7 = new ExpressionStack();
        //es1 value
        this.es1.pushSymbol( new ValueSymbol(2));
        //es2 add values
        this.es2.pushSymbol(new OperationSymbol(AExpressionSymbol.Operator.PLUS));
        this.es2.pushSymbol(new ValueSymbol(3));
        this.es2.pushSymbol(new ValueSymbol(4));
        //es3 substract values
        this.es3.pushSymbol(new OperationSymbol(AExpressionSymbol.Operator.MINUS));
        this.es3.pushSymbol(new ValueSymbol(5));
        this.es3.pushSymbol(new ValueSymbol(6));
        //es4 name
        this.es4.pushSymbol(new NameSymbol("myName"));
        //es5 add : name + value
        this.es5.pushSymbol(new OperationSymbol(AExpressionSymbol.Operator.PLUS));
        this.es5.pushSymbol(new ValueSymbol(7));
        this.es5.pushSymbol(new NameSymbol("myName2"));
        //es6 sub : name - value
        this.es6.pushSymbol(new OperationSymbol(AExpressionSymbol.Operator.MINUS));
        this.es6.pushSymbol(new NameSymbol("myName3"));
        this.es6.pushSymbol(new ValueSymbol(8));
        //es7 add : name + name
        this.es7.pushSymbol(new OperationSymbol(AExpressionSymbol.Operator.PLUS));
        this.es7.pushSymbol(new NameSymbol("myName4"));
        this.es7.pushSymbol(new NameSymbol("myName5"));
        
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testValueIcodeGeneration() {
        System.out.println("test simple value icode generation");
        this.iclRef = new IcodeList();
        iclRef.add(new LdiIcode((byte)2));
        this.es1.print();
        this.esti = new ExpressionStackToIcode(es1);
        IcodeList eic = this.esti.getIcodeList();
        assertNotNull("expression icode list",eic);
        Iterator<AIcode> ir = iclRef.getIterator();
        Iterator<AIcode> it = eic.getIterator();
        assertNotNull("iterator",it);
        if(it.hasNext()){
            while(ir.hasNext()){
                assertEquals("icodeList items ",ir.next().toString(), it.next().toString());
            }
            //print
            eic.print();
        }else
            fail("Icode list empty");
    }
    
    @Test
    public void testAddValueIcodeGeneration() {
        System.out.println("test add value icode generation");
        this.iclRef = new IcodeList();
        iclRef.add(new LdiIcode((byte)4));
        iclRef.add(new LdiIcode((byte)3));
        iclRef.add(new AddIcode());
        this.es2.print();
        this.esti = new ExpressionStackToIcode(es2);
        IcodeList eic = this.esti.getIcodeList();
        assertNotNull("expression icode list",eic);
        Iterator<AIcode> ir = iclRef.getIterator();
        Iterator<AIcode> it = eic.getIterator();
        assertNotNull("iterator",it);
        if(it.hasNext()){
            while(ir.hasNext()){
                assertEquals("icodeList items ",ir.next().toString(), it.next().toString());
            }
            //print
            eic.print();
        }else
            fail("Icode list empty");
    }
    
    @Test
    public void testSubstractValueIcodeGeneration() {
        System.out.println("test substract value icode generation");
        this.iclRef = new IcodeList();
        iclRef.add(new LdiIcode((byte)6));
        iclRef.add(new LdiIcode((byte)5));
        iclRef.add(new SubIcode());
        this.es3.print();
        this.esti = new ExpressionStackToIcode(es3);
        IcodeList eic = this.esti.getIcodeList();
        assertNotNull("expression icode list",eic);
        Iterator<AIcode> ir = iclRef.getIterator();
        Iterator<AIcode> it = eic.getIterator();
        assertNotNull("iterator",it);
        if(it.hasNext()){
            while(ir.hasNext()){
                assertEquals("icodeList items ",ir.next().toString(), it.next().toString());
            }
            //print
            eic.print();
        }else
            fail("Icode list empty");
    }
    
    @Test
    public void testNameIcodeGeneration() {
        System.out.println("test simple Name icode generation");
        this.iclRef = new IcodeList();
        iclRef.add(new LdaIcode("myName"));
        iclRef.add(new LdrIcode());
        this.es4.print();
        this.esti = new ExpressionStackToIcode(es4);
        IcodeList eic = this.esti.getIcodeList();
        assertNotNull("expression icode list",eic);
        Iterator<AIcode> ir = iclRef.getIterator();
        Iterator<AIcode> it = eic.getIterator();
        assertNotNull("iterator",it);
        if(it.hasNext()){
            while(ir.hasNext()){
                assertEquals("icodeList items ",ir.next().toString(), it.next().toString());
            }
            //print
            eic.print();
        }else
            fail("Icode list empty");
    }
    
    @Test
    public void testAddNameValueIcodeGeneration() {
        System.out.println("test add Name value icode generation");
        this.iclRef = new IcodeList();
        iclRef.add(new LdaIcode("myName2"));
        iclRef.add(new LdrIcode());
        iclRef.add(new LdiIcode((byte)7));
        iclRef.add(new AddIcode());
        this.es5.print();
        this.esti = new ExpressionStackToIcode(es5);
        IcodeList eic = this.esti.getIcodeList();
        assertNotNull("expression icode list",eic);
        Iterator<AIcode> ir = iclRef.getIterator();
        Iterator<AIcode> it = eic.getIterator();
        assertNotNull("iterator",it);
        if(it.hasNext()){
            while(ir.hasNext()){
                assertEquals("icodeList items ",ir.next().toString(), it.next().toString());
            }
            //print
            eic.print();
        }else
            fail("Icode list empty");
    }
    
    @Test 
    public void testSubstractNameValueIcodeGeneration() {
        System.out.println("test substract Name value icode generation");
        this.iclRef = new IcodeList();
        iclRef.add(new LdiIcode((byte)8));
        iclRef.add(new LdaIcode("myName3"));
        iclRef.add(new LdrIcode());
        iclRef.add(new SubIcode());
        this.es6.print();
        this.esti = new ExpressionStackToIcode(es6);
        IcodeList eic = this.esti.getIcodeList();
        assertNotNull("expression icode list",eic);
        Iterator<AIcode> ir = iclRef.getIterator();
        Iterator<AIcode> it = eic.getIterator();
        assertNotNull("iterator",it);
        if(it.hasNext()){
            while(ir.hasNext()){
                assertEquals("icodeList items ",ir.next().toString(), it.next().toString());
            }
            //print
            eic.print();
        }else
            fail("Icode list empty");
    }
    
    @Test
    public void testAddNameNameIcodeGeneration() {
        System.out.println("test add Name Name icode generation");
        this.iclRef = new IcodeList();
        iclRef.add(new LdaIcode("myName5"));
        iclRef.add(new LdrIcode());
        iclRef.add(new LdaIcode("myName4"));
        iclRef.add(new LdrIcode());
        iclRef.add(new AddIcode());
        this.es7.print();
        this.esti = new ExpressionStackToIcode(es7);
        IcodeList eic = this.esti.getIcodeList();
        assertNotNull("expression icode list",eic);
        Iterator<AIcode> ir = iclRef.getIterator();
        Iterator<AIcode> it = eic.getIterator();
        assertNotNull("iterator",it);
        if(it.hasNext()){
            while(ir.hasNext()){
                assertEquals("icodeList items ",ir.next().toString(), it.next().toString());
            }
            //print
            eic.print();
        }else
            fail("Icode list empty");
    }
    
}