/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pascal.icodegeneration.simulate;

import java.util.Vector;
import pascal.icodegeneration.AIcode;
import pascal.symboltable.ISymbolTable;

/**
 *
 * @author prog
 */
public class SimulateIcode {
    private final Vector<AIcode> allIcode;
    private final ISymbolTable st;
    private final boolean traceOn;

    public SimulateIcode(Vector<AIcode> allIcode, ISymbolTable st, boolean traceOn) {
        this.allIcode = allIcode;
        this.st = st;
        this.traceOn=traceOn;
        Memory mem = new Memory();
        mem.loadProg(this.allIcode);
        mem.loadData(st.getAllSymbol());
        StackProcessor sproc = new StackProcessor();
        sproc.attachMem(mem);
        sproc.execProg(this.traceOn);
    }
}
