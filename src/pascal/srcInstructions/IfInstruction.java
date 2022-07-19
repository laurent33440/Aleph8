/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pascal.srcInstructions;

import java.util.Iterator;
import java.util.Vector;
import pascal.frontanalyse.ConditionAnalyse;
import pascal.icodegeneration.BzeIcode;
import pascal.icodegeneration.IcodeList;
import pascal.icodegeneration.condition.ConditionToIcode;
import pascal.icodegeneration.label.GenerateLabel;

/**
 *
 * @author laurent
 */
public class IfInstruction implements ISrcInstruction{
    private ConditionAnalyse condAnalyse;
    private SrcInstructionsList wardsInstructions;
    private IcodeList iCodeList;

    public IfInstruction(ConditionAnalyse condAnalyse) {
        this.wardsInstructions = new SrcInstructionsList();
        this.condAnalyse = condAnalyse;
    }

    public void registerWardsInstruction(ISrcInstruction inst) {
        this.wardsInstructions.addInstruction(inst);
    }

    public IcodeList getIcodeList() {
        this.iCodeList = new IcodeList();
        ConditionToIcode condIcode = new ConditionToIcode(this.condAnalyse);
        this.iCodeList.add(condIcode.getIcodeList());
        String label = GenerateLabel.getIfLabel();
        this.iCodeList.add(new BzeIcode(label));
        this.iCodeList.add(this.wardsInstructions.generateIcode());
        this.iCodeList.registerLabelForNextIcode(label);
        return this.iCodeList;
    }
    
}
