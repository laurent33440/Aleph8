/*
 * Composite pattern
 */
package pascal.srcInstructions;

import pascal.icodegeneration.IcodeList;

/**
 *
 * @author laurent
 */
public interface ISrcInstruction {
    public void registerWardsInstruction(ISrcInstruction inst);
    public IcodeList getIcodeList();
    
}
