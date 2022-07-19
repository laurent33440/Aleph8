/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pascal.symboltable;

import java.util.Iterator;
import java.util.Vector;
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
public class SymbolTableTest {
     private  ISymbolTable tableSymbol;

    public SymbolTableTest() {
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

    @Test
    public void addPascalSymbol() {
        System.out.println("add Symbol");
        this.tableSymbol = SymbolTable.getInstance();
        ASymbol symbol = new ProgramSymbol("test_name");
        assertNotNull(symbol);
        this.tableSymbol.addPascalSymbol(symbol);
        symbol = this.tableSymbol.getSymbol(0);
        assertNotNull(symbol);

    }

    @Test
    public void getIteratorSymbol() {
        System.out.println("get Iterator symbol");
        this.tableSymbol = SymbolTable.getInstance();
        this.tableSymbol.addPascalSymbol(new ProgramSymbol("test_name"));
        Iterator<ASymbol> it = this.tableSymbol.getIteratorSymbol();
        assertNotNull(it);

    }

    @Test
    public void iterateOnSymbols() {
        System.out.println("Iterate on symbol");
        this.tableSymbol = SymbolTable.getInstance();
        this.tableSymbol.addPascalSymbol(new ProgramSymbol("test_name"));
        this.tableSymbol.addPascalSymbol(new ProgramSymbol("test_name2"));
        this.tableSymbol.addPascalSymbol(new ProgramSymbol("test_name3"));
        Iterator<ASymbol> it = this.tableSymbol.getIteratorSymbol();
        while(it.hasNext()){
            assertNotNull(it.next());
        }
    }
    
    @Test
    public void addAllSymbols() {
        System.out.println("add all symbols");
        this.tableSymbol = SymbolTable.getInstance();
        this.tableSymbol.addPascalSymbol(new ProgramSymbol("test_name"));
        this.tableSymbol.addPascalSymbol(new ConstanteSymbol("constName", "10"));
        this.tableSymbol.addPascalSymbol(new VariableSymbol("varName","byte"));
        Iterator<ASymbol> it = this.tableSymbol.getIteratorSymbol();
        while(it.hasNext()){
            assertNotNull(it.next());
        }
    }
    
    @Test
    public void isSymbolExist() {
        System.out.println("is symbols exists");
        this.tableSymbol = SymbolTable.getInstance();
        this.tableSymbol.addPascalSymbol(new ProgramSymbol("test_name"));
        this.tableSymbol.addPascalSymbol(new ConstanteSymbol("constName", "10"));
        this.tableSymbol.addPascalSymbol(new VariableSymbol("varName","byte"));
        assertTrue(this.tableSymbol.isSymbolExist("test_name"));
        assertTrue(this.tableSymbol.isSymbolExist("constName"));
        assertTrue(this.tableSymbol.isSymbolExist("varName"));
        assertFalse(this.tableSymbol.isSymbolExist("unknomwn"));
        
    }
    
    @Test
    public void getSymbolByName() {
        System.out.println("get symbol by name");
        this.tableSymbol = SymbolTable.getInstance();
        this.tableSymbol.addPascalSymbol(new ProgramSymbol("test_name"));
        this.tableSymbol.addPascalSymbol(new ConstanteSymbol("constName", "10"));
        this.tableSymbol.addPascalSymbol(new VariableSymbol("varName","byte"));
        assertNotNull(this.tableSymbol.getSymbol("test_name"));
        assertNotNull(this.tableSymbol.getSymbol("constName"));
        assertNotNull(this.tableSymbol.getSymbol("varName"));
        assertNull(this.tableSymbol.getSymbol("unknomwn"));
        
    }
    
    @Test
    public void getAllSymbols() {
        System.out.println("get all symbols");
        ISymbolTable st= SymbolTable.getInstance();
        st.addPascalSymbol(new ProgramSymbol("test_name"));
        st.addPascalSymbol(new ConstanteSymbol("constName", "10"));
        st.addPascalSymbol(new VariableSymbol("varName","byte"));
        assertNotNull(st.getAllSymbol());
        Vector<ASymbol> v = st.getAllSymbol();
        assertEquals("symbol table size mismatch!", 14+3,v.size());
        assertNotNull(st.getSymbol("varName"));
        assertNull(st.getSymbol("unknomwn"));
        
    }
    

}