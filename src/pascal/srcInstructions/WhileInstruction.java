/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pascal.srcInstructions;

import pascal.frontanalyse.ConditionAnalyse;
import pascal.icodegeneration.BraIcode;
import pascal.icodegeneration.BzeIcode;
import pascal.icodegeneration.IcodeList;
import pascal.icodegeneration.condition.ConditionToIcode;
import pascal.icodegeneration.label.GenerateLabel;

/**
 *
 * @author laurent
 */
public class WhileInstruction implements ISrcInstruction{
    private ConditionAnalyse condAnalyse;
    private SrcInstructionsList wardsInstructions;
    private IcodeList iCodeList;

    public WhileInstruction(ConditionAnalyse condAnalyse) {
        this.wardsInstructions = new SrcInstructionsList();
        this.condAnalyse = condAnalyse;
    }

    public void registerWardsInstruction(ISrcInstruction inst) {
        this.wardsInstructions.addInstruction(inst);
    }

    public IcodeList getIcodeList() {
        this.iCodeList = new IcodeList();
        IcodeList iCodeListCondWithLabel = new IcodeList();
        String label_repeat = GenerateLabel.getWhileLabel();
        iCodeListCondWithLabel.registerLabelForNextIcode(label_repeat);
        ConditionToIcode condIcode = new ConditionToIcode(this.condAnalyse);
        IcodeList iCodeListCond = condIcode.getIcodeList();
        iCodeListCondWithLabel.add(iCodeListCond);
        this.iCodeList.add(iCodeListCondWithLabel);
        String label_out = GenerateLabel.getWhileLabel();
        this.iCodeList.add(new BzeIcode(label_out));
        this.iCodeList.add(this.wardsInstructions.generateIcode());
        this.iCodeList.add(new BraIcode(label_repeat));
        this.iCodeList.registerLabelForNextIcode(label_out);
        return this.iCodeList;
    }
    
    
}
