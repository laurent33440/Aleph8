/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pascal.srcInstructions;

import pascal.frontanalyse.ExpressionAnalyse;
import pascal.icodegeneration.IcodeList;
import pascal.icodegeneration.LdaIcode;
import pascal.icodegeneration.StoIcode;
import pascal.icodegeneration.expression.ExpressionStackToIcode;

/**
 *
 * @author laurent
 */
public class LetInstruction implements ISrcInstruction{
    private String identifier;
    private ExpressionAnalyse ea;
    private IcodeList iCodeList;

    public LetInstruction(String identifier, ExpressionAnalyse ea) {
        this.identifier = identifier;
        this.ea = ea;
    }

    public void registerWardsInstruction(ISrcInstruction inst) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public IcodeList getIcodeList() {
        this.iCodeList = new IcodeList();
        this.iCodeList.add(new LdaIcode(identifier));
        ExpressionStackToIcode expIcode = new ExpressionStackToIcode(ea.getExprStack());
        this.iCodeList.add(expIcode.getIcodeList());
        this.iCodeList.add(new StoIcode());
        return iCodeList;
    }
    
}
