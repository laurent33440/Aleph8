/******************************************************************************
 * By SILIBRE - www.silibre.com - 2009
 * contact@silibre.com
 ******************************************************************************
 * ExpressionAdaptater.java
 *
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
 *  0.1         prog         4 févr. 2012                 Creation
 */

package pascal.frontanalyse.expressionstack;

/**
 * Just an adaptater to implements empty methods of AExpressionSymbol
 * that concern composite class ExpressionStack
 */
public class ExpressionAdaptater extends AExpressionSymbol{

    @Override
    public AExpressionSymbol popSymbol() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void pushSymbol(AExpressionSymbol elt) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public AExpressionSymbol peekSymbol() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void print() {
    }

    @Override
    public void accept(ISymbolVisitor v) {
    }






}
