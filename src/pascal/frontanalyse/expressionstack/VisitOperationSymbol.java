/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pascal.frontanalyse.expressionstack;

/**
 *
 * @author laurent
 */
public class VisitOperationSymbol implements ISymbolVisitor{
    private AExpressionSymbol.Operator op; 

    public VisitOperationSymbol() {
    }

    public void visit(OperationSymbol opS) {
        this.op = opS.getOp();
    }

    public void visit(NameSymbol opS) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visit(ValueSymbol opS) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public AExpressionSymbol.Operator getOperator(){
        return this.op;
    }
    
}
