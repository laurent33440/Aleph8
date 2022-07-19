/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pascal.srcInstructions;

import java.util.Iterator;
import java.util.Vector;
import pascal.icodegeneration.IcodeList;

/**
 *
 * @author laurent
 */
public class SrcInstructionsList { 

    private Vector<ISrcInstruction> allInstructions; //consider using ArrayList that is not synchronized (faster)

    /**
     * 
     */
    public SrcInstructionsList() {
        this.allInstructions = new Vector<ISrcInstruction>();
    }
    
    /**
     * 
     * @param inst 
     */
    public void addInstruction(ISrcInstruction inst){
        this.allInstructions.add(inst);
    }
    
    /**
     * 
     * @return 
     */
    public Iterator<ISrcInstruction> getIterator(){
        return this.allInstructions.iterator();
    }
    
    /**
     * 
     * @return 
     */
    public IcodeList generateIcode(){
        IcodeList iList = new IcodeList();
        Iterator<ISrcInstruction> srcIt = getIterator();
        while(srcIt.hasNext()){
            iList.add(srcIt.next().getIcodeList());
        }
        return iList;
    }
}
