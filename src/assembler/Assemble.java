/******************************************************************************
 * By SILIBRE - www.silibre.com - 2009
 * contact@silibre.com
 ******************************************************************************
 * Assemble.java
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

import java.io.File;
import javax.swing.JOptionPane;

/*
** Class Assemble : assembler for aleph8
** meta rules :	comments begins with ";" and end with new line.
**		separators are : space, tabulation, new line.
		each key words(Id, Num, or instructions) must be separated by at least one separator.
		separators can be inserted anywhere except in a keyword
** Grammar  : key words are in minus. Id is an identificator (any alpha followed by number). Num is numerical.
**
** ASM::= 	var Num BLOCK end
** BLOCK::=	DCB INSTS
** DCB::=	Id <= Num {Id <= Num}
** INSTS::=	begin Num INST end
** INST::=	NOR | ADD | STA | JCC
** NOR::=	{Id}	nor Id
** ADD::=	{Id}	add Id
** STA::=	{Id}	sta Id
** JCC::=	{Id}	jcc Id
 * -----------------------------------------------------------------------------
 * WIP : We use composite pattern for assembler?
 *
*/

public class Assemble implements IAssemblerServices{
    //private List<IAssComponent> allComp;
    private MyFile myFile;
    private String asmSrc; // asm source
    private AnyError anyError; // error handler
    private final SymbolsTable symbolsTable; //
    private final Malloc malloc; // memory mangement
    private final Aleph8Code aleph8Code; // hex code generator
    private final IdConflict idConflict; // hold Id Label conflict
    private final ParseAsm codeToBuild; // parser & logic

    public Assemble(File asmFile){
        anyError = new AnyError();
        try{
                myFile = new MyFile(this, asmFile);
                asmSrc = myFile.readFile();
        }
        catch (MyException me){
        }
        symbolsTable = new SymbolsTable(this);
        malloc = new Malloc();
        aleph8Code = new Aleph8Code();
        idConflict = new IdConflict(this);
        codeToBuild = new ParseAsm(this);
        if (!anyError.existError()){
                myFile.writeFile();
                showMess("Génération du binaire réussi", "Assembleur");
        }
    }

    //composite
    /*
    public IAssComponent getComponent(Integer i){
        return null;
    }

    public void addComponent(IAssComponent component){

    }

    public void removeComponent(IAssComponent component){

    }
    */

    private void showMess(String message, String title) {
            JOptionPane.showMessageDialog(null, message,title, JOptionPane.INFORMATION_MESSAGE);
    }

    public Aleph8Code getAleph8Code() {
        return this.aleph8Code;
    }

    public AnyError getAnyError() {
        return this.anyError;
    }

    public String getAsmSrc() {
        return this.asmSrc;
    }

    public IdConflict getIdConflict() {
        return this.idConflict;
    }

    public Malloc getMalloc() {
        return this.malloc;
    }

    public SymbolsTable getSymbolsTable() {
        return this.symbolsTable;
    }

}
