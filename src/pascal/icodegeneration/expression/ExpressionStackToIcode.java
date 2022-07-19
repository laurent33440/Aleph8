/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pascal.icodegeneration.expression;

import pascal.frontanalyse.expressionstack.AExpressionSymbol;
import pascal.frontanalyse.expressionstack.IExpressionList;
import pascal.frontanalyse.expressionstack.VisitNameSymbol;
import pascal.frontanalyse.expressionstack.VisitOperationSymbol;
import pascal.frontanalyse.expressionstack.VisitValueSymbol;
import pascal.icodegeneration.AddIcode;
import pascal.icodegeneration.IcodeList;
import pascal.icodegeneration.LdaIcode;
import pascal.icodegeneration.LdiIcode;
import pascal.icodegeneration.LdrIcode;
import pascal.icodegeneration.SubIcode;

/**
 *
 * @author laurent
 */
public class ExpressionStackToIcode {
    private final IExpressionList es;
    private IcodeList iCodeList;

    public ExpressionStackToIcode(IExpressionList es) {
        this.es = es;
        if(!this.es.isEmpty()){
            this.iCodeList = new IcodeList();
            AExpressionSymbol element;
            while(!es.isEmpty()){
                element = es.popSymbol();
                switch(element.getType()){
                    case NAME:
                        VisitNameSymbol vn = new VisitNameSymbol();
                        element.accept(vn);
                        this.iCodeList.add(new LdaIcode(vn.getName()));
                        this.iCodeList.add(new LdrIcode());
                        break;
                    case OPERATOR:
                        VisitOperationSymbol vo = new VisitOperationSymbol();
                        element.accept(vo);
                        switch (vo.getOperator()){
                            case PLUS:
                                this.iCodeList.add(new AddIcode());
                                break;
                            case MINUS:
                                this.iCodeList.add(new SubIcode());
                                break;
                            default: //operator unknown
                                break;
                        }
                        break;
                    case VALUE:
                        VisitValueSymbol vv = new VisitValueSymbol();
                        element.accept(vv);
                        this.iCodeList.add(new LdiIcode((byte)vv.getValue()));
                        break;
                    default: //never reach
                        break;
                }
            }
        }
    }
    
    public IcodeList getIcodeList(){
        return iCodeList;
    }
}
