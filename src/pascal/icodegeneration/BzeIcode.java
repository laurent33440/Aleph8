/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pascal.icodegeneration;

/**
 *
 * @author prog
 */
public class BzeIcode extends AIcode{
    private final String branchName;
    

    public BzeIcode(String symbolicAddress) {
        this.branchName = symbolicAddress;
    }

    public String getBranchName() {
        return branchName;
    }
    
    public String toString() {
        return toString("BZE",this.branchName);
    }
    
    
}
