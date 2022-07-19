/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pascal.icodegeneration;

import java.util.Iterator;
import java.util.Vector;

/**
 *
 * @author laurent
 */
public class IcodeList {
    private Vector<AIcode> icodeList;
    private String labelToAssignForNextIcode;
    
    public IcodeList(){
        this.icodeList = new Vector<AIcode>();
        this.labelToAssignForNextIcode="";
    }
    
    public void add(AIcode icode){
        if(!this.labelToAssignForNextIcode.equals("")){
            icode.setAssociatedLabel(labelToAssignForNextIcode);
            this.labelToAssignForNextIcode="";
        }
        this.icodeList.add(icode);
    }
    
    // FIXME : tests for cascadable if, while ...
    public void add(IcodeList iList){
        Iterator<AIcode> it = iList.getIterator();
        while(it.hasNext()){
            add(it.next());
        }
        if(iList.isLabelRegisteredForNextIcode()){
            this.labelToAssignForNextIcode = iList.getLabelToAssignForNextIcode();
        }
    }
    
    public Iterator<AIcode> getIterator(){
        return this.icodeList.iterator();
    }
    
    public void registerLabelForNextIcode(String label){
        this.labelToAssignForNextIcode=label;
    }
    
    public boolean isLabelRegisteredForNextIcode(){
        return !this.labelToAssignForNextIcode.equals("");
    }

    public String getLabelToAssignForNextIcode() {
        return labelToAssignForNextIcode;
    }

    public Vector<AIcode> getIcodeList() {
        return icodeList;
    }
    
    /**
     * Print on std output icodes list 
     */
    public void print(){
        System.out.println("Icode List : ");
        Iterator<AIcode> it = icodeList.iterator();
        while(it.hasNext()){
            AIcode icode = it.next();
            icode.print();
        }
    }
    
}
