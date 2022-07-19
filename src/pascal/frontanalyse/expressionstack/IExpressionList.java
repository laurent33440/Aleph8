/******************************************************************************
 * By SILIBRE - www.silibre.com - 2009
 * contact@silibre.com
 ******************************************************************************
 * IExpressionList.java
 * Interface for expression symbols list
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

/**
 *
 * @author laurent
 */
public interface IExpressionList {

    /**
     * Visit symbol top of stack
     * @param v : visitor of symbol
     */
    void accept(ISymbolVisitor v);

    /**
     * Test if Expression stack is empty
     * @return TRUE if empty
     */
    boolean isEmpty();

    /**
     * Get top of the stack. Don't pop symbol
     * @return top symbol
     */
    AExpressionSymbol peekSymbol();

    /**
     * Pop Expression symbol
     * @return Expression symbol or null if empty
     */
    AExpressionSymbol popSymbol();

    /**
     * Print on std output expression stack elements, top first.
     */
    void print();

    /**
     * Push Expression symbol to the head of stack
     * @param elt to add
     */
    void pushSymbol(AExpressionSymbol elt);
    
}
