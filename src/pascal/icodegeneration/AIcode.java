/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pascal.icodegeneration;

/**
 *
 * @author laurent
 */
public abstract class AIcode {
    //associated label
    protected String associatedLabel;
    
    /**
     * 
     */
    public AIcode() {
        this.associatedLabel = "";
    } 

    /**
     * 
     * @param associatedLabel 
     */
    public final void setAssociatedLabel(String associatedLabel) {
        this.associatedLabel = associatedLabel;
    }

    /**
     * 
     * @return 
     */
    public final String getAssociatedLabel() {
        return associatedLabel;
    }
    
    /**
     * print icode
     */
    
    protected final void print(){
        System.out.println(toString());
    }
    
    protected final String toString(String icodeName, Object argument){
        String s = "\t\t"+icodeName+"\t"+argument;
        if(!this.associatedLabel.equals("")){
            s = this.associatedLabel+s;
        }
        return s;
    }
    
    
}
