/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pascal.icodegeneration;

/**
 *
 * @author laurent
 */
public class BraIcode extends AIcode{
    private final String branchName;

    public BraIcode(String symbolicAddress) {
        this.branchName = symbolicAddress;
    }

    public String getBranchName() {
        return branchName;
    }
    
    public String toString() {
        return toString("BRA",this.branchName);
    }
    
}
