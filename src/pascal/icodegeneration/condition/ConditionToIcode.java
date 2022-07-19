/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pascal.icodegeneration.condition;

import pascal.frontanalyse.ConditionAnalyse;
import pascal.icodegeneration.EqlIcode;
import pascal.icodegeneration.GeqIcode;
import pascal.icodegeneration.GtrIcode;
import pascal.icodegeneration.IcodeList;
import pascal.icodegeneration.LeqIcode;
import pascal.icodegeneration.LssIcode;
import pascal.icodegeneration.NeqIcode;
import pascal.icodegeneration.expression.ExpressionStackToIcode;

/**
 *
 * @author laurent
 */
public class ConditionToIcode {
    private IcodeList iCodeList;
    
    public ConditionToIcode(ConditionAnalyse ca){
        this.iCodeList = new IcodeList();
        ExpressionStackToIcode iExp= new ExpressionStackToIcode(ca.getLeftExpressionAnalyse().getExprStack());
        this.iCodeList.add(iExp.getIcodeList());//exp left
        iExp= new ExpressionStackToIcode(ca.getRightExpressionAnalyse().getExprStack());
        this.iCodeList.add(iExp.getIcodeList());//exp right
        switch(ca.getRelOpToken().getTokenValue()){
            case EQUAL:
                this.iCodeList.add(new EqlIcode());
                break;
            case DIFF:
                this.iCodeList.add(new NeqIcode());
                break;
            case LEQ:
                this.iCodeList.add(new LeqIcode());
                break;
            case LESS:
                this.iCodeList.add(new LssIcode());
                break;
            case GEQ:
                this.iCodeList.add(new GeqIcode());
                break;
            case GREAT:
                this.iCodeList.add(new GtrIcode());
                break;
            default:
                break;
        }
        
    }

    public IcodeList getIcodeList() {
        return iCodeList;
    }
   
    
    
    
}
