/******************************************************************************
 * By SILIBRE - www.silibre.com - 2009
 * contact@silibre.com
 ******************************************************************************
 * ValueSymbol.java
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
 *  0.1         prog         25 janv. 2012                 Creation
 */

package pascal.frontanalyse.expressionstack;

/**
 * Value symbol : WARNING only Byte value allowed
 */
public class ValueSymbol extends ExpressionAdaptater{
    private int value;
  
    /**
     * Constructor
     * @param value
     */
    public ValueSymbol(int value) {
        this.value = value;
        this.type = Type.VALUE;
    }

    public int getValue() {
        return value;
    }

    @Override
    public void print() {
        System.out.println("ValueSymbol : " + new Integer(value).toString());
    }

    @Override
    public void accept(ISymbolVisitor v) {
        v.visit(this);
    }
    
    @Override
    public String toString(){
        return new Integer(this.value).toString();
    }

}
