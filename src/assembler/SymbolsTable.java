/******************************************************************************
 * By SILIBRE - www.silibre.com - 2009
 * contact@silibre.com
 ******************************************************************************
 * SymbolsTable.java
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
 *  0.1         prog         28 oct. 2010                 Creation
 */

package assembler;

/**
** Class SymbolsTable : handle symbols
**/
class SymbolsTable implements IAssDef{
        private IAssemblerServices services;
        private Symbol [] table;
        private int index;

        SymbolsTable(IAssemblerServices services){
                this.services = services;
                int i;
                table = new Symbol[MAXSYMBOLS];
                for(i = 0; i<MAXSYMBOLS; i++){
                        table[i] = new Symbol();
                }
                index = 0;
        }
        
        void addSymbol(String name, int value, int clType, int numLine) throws MyException{
                //debug("add symbol");
                int r;
                if ((r = findSymbol(name)) > 0 )//check if already exists
                        throw new MyException("Identificateur déja utilisé !!",name,numLine);
                if (index == MAXSYMBOLS)
                        throw new MyException("nombre maximum d'identificateurs atteint !!");
                switch(clType){
                        case VAR :
                                table[index].ad = services.getMalloc().getAdData();//get next free adress
                                table[index].val = value;
                        break;
                        case JUMP :
                                table[index].val = services.getMalloc().curCodeAd();
                                table[index].ad  = 0; // jump class has no adress
                        break;
                }
                table[index].name = name;
                table[index].classType = clType;
                index++;
        }
        
        int findSymbol(String symbName){
                int idx=0;
                boolean found = false;
                while((idx < index) && (!found)) {
                        if(table[idx++].name.equals(symbName))
                                found = true;
                }
                if(!found)
                        return -1;
                else
                        return --idx;
        }
        
        int getVal(int idx){
                return table[idx].val;
        }
        
        int getVal(String symbName) throws MyException{
                int r = findSymbol(symbName);
                if (r<0)
                        throw new MyException("Valeur du symbol : "+symbName+" non trouv�e !!");
                else
                        return table[r].val;
        }
        
        int getAd(int idx){
                return table[idx].ad;
        }
        
        int getAd(String symbName) throws MyException{
                int r = findSymbol(symbName);
                if (r<0)
                        throw new MyException("Adresse du symbol : "+symbName+" non trouv�e !!");
                else
                        return table[r].ad;
        }
        
        void print(){
                int i;
                System.out.println("Table des symbols : ");
                System.out.print("nombre de symbols : ");
                System.out.println(index);
                System.out.println("Nom\tClasse\tValeur\tAdresse : ");
                for(i = 0; i<index ; i++){
                        System.out.print(table[i].name);System.out.print("\t");
                        System.out.print(table[i].classType);System.out.print("\t");
                        System.out.print(table[i].val);System.out.print("\t");
                        System.out.print(table[i].ad);
                        System.out.println();
                }
        }
        
        /**
        ** Class Symbol : define a symbol
        **/
        private class Symbol {
                private String name;
                private int classType;
                private int val;
                private int ad;

                Symbol(){
                        name="unknown";
                        classType = UNKNOWN;
                        val=0;
                        ad=0;
                }
        }
}

