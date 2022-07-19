/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pascal.icodegeneration;

/**
 *
 * @author laurent
 */
public class LdaIcode extends AIcode{
    private final String symbolicAdress;

    public LdaIcode(String symbolicAdress) {
        this.symbolicAdress = symbolicAdress;
    }

    public String getSymbolicAdress() {
        return symbolicAdress;
    }
    
    public String toString() {
        return toString("LDA",this.symbolicAdress);
    }
    
    
}
