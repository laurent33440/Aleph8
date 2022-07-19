/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pascal.icodegeneration;

/**
 *
 * @author laurent
 */
public class LdiIcode extends AIcode{
    private final Byte value;

    public LdiIcode(byte value) {
        this.value = value;
    }

    public Byte getValue() {
        return value;
    }
    
    public String toString() {
        return toString("LDI",this.value);
    }
    
    
}
