/******************************************************************************
 * By SILIBRE - www.silibre.com - 2009
 * contact@silibre.com
 ******************************************************************************
 * AExpressionSymbol.java
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
 *
 */
public abstract class AExpressionSymbol {
    protected Type type;
    
    public enum Operator{
        PLUS("+"), MINUS("-"), MULT("*"), DIV("/");

        private String value;

        Operator(String val){
            this.value = val;
        }

        public String getOperator(){
            return this.value;
        }
    }
    
    public enum Type{
        NAME("name"), OPERATOR("operator"), VALUE("value");

        private String value;

        Type(String val){
            this.value = val;
        }

        public String getType(){
            return this.value;
        }
    }
    
    public Type getType() {
        return type;
    }

    public abstract void pushSymbol(AExpressionSymbol elt);

    public abstract AExpressionSymbol popSymbol();

    public abstract AExpressionSymbol peekSymbol();

    public abstract void print();

    public abstract void accept(ISymbolVisitor v);

}
