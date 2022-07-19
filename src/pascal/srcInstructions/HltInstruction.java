/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pascal.srcInstructions;

import pascal.icodegeneration.HltIcode;
import pascal.icodegeneration.IcodeList;

/**
 *
 * @author laurent
 */
public class HltInstruction implements ISrcInstruction{
    
    public void registerWardsInstruction(ISrcInstruction inst) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public IcodeList getIcodeList() {
        IcodeList iCodeList = new IcodeList();
        iCodeList.add(new HltIcode());
        return iCodeList;
    }
    
}
