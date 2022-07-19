/******************************************************************************
 * By SILIBRE - www.silibre.com - 2009
 * contact@silibre.com
 ******************************************************************************
 * SymbolTable.java
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
 *  0.1         prog         22 janv. 2012                 Creation
 */

package pascal.symboltable;

import java.util.Iterator;
import java.util.Vector;

/**
 * Symbol Table Class : holds symbols
 * Singleton + composite pattern
 */
public class SymbolTable implements ISymbolTable {
    private final Vector<ASymbol> allSymbol;
    private static ISymbolTable uniqueInstance = null;
 
    private SymbolTable() {
        this.allSymbol = new Vector<ASymbol>();
    }

    /**
     * Acces to the unique symbols table
     * @return Symbol table
     */
    public static ISymbolTable getInstance(){
        if(uniqueInstance == null)
            uniqueInstance = new SymbolTable();
        return uniqueInstance;
    }

    /**
     * Add a symbol to the table
     * @param symbol
     */
    public void addPascalSymbol(ASymbol symbol){
        if(!symbol.equals(null))
            this.allSymbol.add(symbol);
    }

    /**
     * OBSOLETE get a symbol from the table by index.
     * WARNING no check on index
     * @param index
     * @return Symbol
     */
    public ASymbol getSymbol(Integer index){
        return this.allSymbol.elementAt(index);
    }
    
    /**
     * Get symbol from his name
     * @param symbolName
     * @return symbol if exist, null else
     */
    public ASymbol getSymbol(String symbolName){
        Iterator<ASymbol> it = this.getIteratorSymbol();
        while(it.hasNext()){
            ASymbol symb = it.next();
            if(symb.getName().equals(symbolName))
                return symb;
        }
        return null;
    }
    
    /**
     * Test if symbol exist
     * @param symbolName
     * @return TRUE if exist, FALSE else
     */
    public boolean isSymbolExist(String symbolName){
        Iterator<ASymbol> it = this.getIteratorSymbol();
        while(it.hasNext()){
            if(it.next().getName().equals(symbolName))
                return true;
        }
        return false;
    }

    /**
     * Get iterator over the table
     * @return iterator
     */
    public Iterator getIteratorSymbol(){
        return this.allSymbol.iterator();
    }

    /**
     * Get all symbols of table
     * @return vector of symbols
     */
    public Vector<ASymbol> getAllSymbol() {
       return this.allSymbol;
    }



}
