/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pascal.frontanalyse.expressionstack;

/**
 *
 * @author laurent
 */
public class VisitNameSymbol implements ISymbolVisitor{
    private String name;

    public void visit(OperationSymbol opS) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visit(NameSymbol opS) {
       this.name = opS.getName();
    }

    public void visit(ValueSymbol opS) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getName() {
        return name;
    }
    
    
}
