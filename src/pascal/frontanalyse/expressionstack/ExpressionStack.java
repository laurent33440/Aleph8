/******************************************************************************
 * By SILIBRE - www.silibre.com - 2009
 * contact@silibre.com
 ******************************************************************************
 * ExpressionStack.java
 * LIFO stack for expression symbols
 *
 ******************************************************************************
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 ******************************************************************************
 *  Ver         Who             When                    What
 *  0.1         prog         25 janv. 2012                 Creation
 */

package pascal.frontanalyse.expressionstack;

import java.util.Stack;

/**
 * ExpressionStack LIFO of expression symbol (invert polonia notation)
 * composite pattern
 */
public class ExpressionStack implements IExpressionList{
    private Stack<AExpressionSymbol> stack;

    /**
     * Construct empty stack of expression symbol
     */
    public ExpressionStack() {
        this.stack = new Stack<AExpressionSymbol>();
    }
    
    /**
     * Test if Expression stack is empty
     * @return TRUE if empty
     */
    public boolean isEmpty(){
        return this.stack.empty();
    }

    /**
     * Push Expression symbol to the head of stack
     * @param elt to add
     */
    public void pushSymbol(AExpressionSymbol elt){
        this.stack.push(elt);
    }

    /**
     * Pop Expression symbol
     * @return Expression symbol or null if empty
     */
    public AExpressionSymbol popSymbol(){
        return this.stack.pop();
    }

    /**
     * Get top of the stack. Don't pop symbol
     * @return top symbol
     */
    public AExpressionSymbol peekSymbol(){
        return this.stack.peek();
    }

    /**
     * Print on std output expression stack elements, top first.
     */
    public void print(){
        Stack<AExpressionSymbol> stack2 = (Stack<AExpressionSymbol>)this.stack.clone();
        System.out.println("Expression stack : ");
        while(!stack2.empty()){
            AExpressionSymbol symb = stack2.pop();
            symb.print();
        }
    }

    /**
     * Visit symbol top of stack
     * @param v : visitor of symbol
     */
    public void accept(ISymbolVisitor v) {
        AExpressionSymbol sym = this.peekSymbol();
        sym.accept(v);
    }


}
