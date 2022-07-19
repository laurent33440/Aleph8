/******************************************************************************
 * By SILIBRE - www.silibre.com - 2009
 * contact@silibre.com
 ******************************************************************************
 * IdConflict.java
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

import java.util.Enumeration;
import java.util.Vector;

/**
** Class IdConflict : hold Id label conflict & try to resolve
**/
class IdConflict {
        private IAssemblerServices services;
        private Vector<Conflict> conflicts;
        private boolean existConflict;

        IdConflict(IAssemblerServices services){
                this.services = services;
                conflicts = new Vector<Conflict>();
                existConflict = false;
        }
        
        void newConflict(int ist, int adr, int lNum, String name ){
                existConflict = true;
                conflicts.addElement(new Conflict(ist,adr,lNum,name));
        }
        
        void resolveConflicts() throws MyException {
                Enumeration enConf = conflicts.elements();
                while (enConf.hasMoreElements()){
                        Conflict curConf = (Conflict)(enConf.nextElement());
                        int i=services.getSymbolsTable().findSymbol(curConf.idName);
                        if (i > 0){
                                services.getAleph8Code().gen(curConf.adress, curConf.inst,services.getSymbolsTable().getVal(i));
                        }
                        else{
                                throw new MyException("Resolution de l'identificateur impossible !!", curConf.idName, curConf.lineNum);
                        }
                }
        }
        
        boolean exist(){
                return existConflict;
        }
        
        /**
        ** Class Conflict : define a conflict Id
        **/
        private class Conflict {
                private int inst;
                private int adress;
                private int lineNum;
                private String idName;

                Conflict(){
                        inst=0;
                        adress=0;
                        lineNum=0;
                        idName="NONE";
                }
                Conflict(int ist, int adr, int lNum, String name ){
                        inst=ist;
                        adress=adr;
                        lineNum=lNum;
                        idName=name;
                }
        }
}
