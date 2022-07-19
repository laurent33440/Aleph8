/******************************************************************************
 * By SILIBRE - www.silibre.com - 2009
 * contact@silibre.com
 ******************************************************************************
 * ExpressionStack.java
 * FIFO stack for expression symbols
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

import java.util.LinkedList;
import java.util.Queue;

/**
 * ExpressionStack FIFO of expression symbol (invert polonia notation)
 * composite pattern
 */
public class ExpressionQueue implements IExpressionList {
    private LinkedList<AExpressionSymbol> queue;

    /**
     * Construct empty stack of expression symbol
     */
    public ExpressionQueue() {
        this.queue = new LinkedList<AExpressionSymbol>();
    }
    
    /**
     * Test if Expression stack is empty
     * @return TRUE if empty
     */
    @Override
    public boolean isEmpty(){
        return this.queue.isEmpty();
    }

    /**
     * Push Expression symbol to the head of stack
     * @param elt to add
     */
    @Override
    public void pushSymbol(AExpressionSymbol elt){
        this.queue.add(elt);
    }

    /**
     * Pop Expression symbol
     * @return Expression symbol or null if empty
     */
    @Override
    public AExpressionSymbol popSymbol(){
        return this.queue.poll();
    }

    /**
     * Get top of the stack. Don't pop symbol
     * @return top symbol
     */
    @Override
    public AExpressionSymbol peekSymbol(){
        return this.queue.peek();
    }

    /**
     * Print on std output expression stack elements, top first.
     */
    @Override
    public void print(){
        Queue<AExpressionSymbol> stack2 = (LinkedList<AExpressionSymbol>)this.queue.clone();
        System.out.println("Expression Queue : ");
        while(!stack2.isEmpty()){
            AExpressionSymbol symb = stack2.poll();
            symb.print();
        }
    }

    /**
     * Visit symbol top of stack
     * @param v : visitor of symbol
     */
    @Override
    public void accept(ISymbolVisitor v) {
        AExpressionSymbol sym = this.peekSymbol();
        sym.accept(v);
    }

    
}
